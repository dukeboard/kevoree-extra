package org.kevoree.serializer
import org.kevoree._
trait NodeNetworkSerializer 
 extends NodeLinkSerializer {
def getNodeNetworkXmiAddr(selfObject : NodeNetwork,previousAddr : String): Map[Object,String] = {
var subResult = Map[Object,String]()
var i = 0
i=0
selfObject.getLink.foreach{ sub => 
subResult +=  sub -> (previousAddr+"/@link."+i) 
subResult = subResult ++ getNodeLinkXmiAddr(sub,previousAddr+"/@link."+i)
i=i+1
}
subResult
}
def NodeNetworktoXmi(selfObject : NodeNetwork,refNameInParent : String, addrs : Map[Object,String]) : scala.xml.Node = {
new scala.xml.Node {
  def label = refNameInParent
    def child = {        
       var subresult: List[scala.xml.Node] = List()  
selfObject.getLink.foreach { so => 
subresult = subresult ++ List(NodeLinktoXmi(so,"link",addrs))
}
      subresult    
    }              
override def attributes  : scala.xml.MetaData =  { 
var subAtts : scala.xml.MetaData = scala.xml.Null
selfObject.getInitBy.map{sub =>
subAtts= subAtts.append(new scala.xml.UnprefixedAttribute("initBy",addrs.get(sub).getOrElse{"wtf"},scala.xml.Null))
}
subAtts= subAtts.append(new scala.xml.UnprefixedAttribute("target",addrs.get(selfObject.getTarget).getOrElse{"wtf"},scala.xml.Null))
subAtts}
  }                                                  
}
}
