package org.kevoree.serializer
import org.kevoree._
trait NodeLinkSerializer 
 extends NetworkPropertySerializer {
def getNodeLinkXmiAddr(selfObject : NodeLink,previousAddr : String): Map[Object,String] = {
var subResult = Map[Object,String]()
selfObject.getNetworkProperties.foreach{ sub => 
subResult +=  sub -> (previousAddr+"/@networkProperties."+selfObject.getNetworkProperties.indexOf(sub) ) 
subResult = subResult ++ getNetworkPropertyXmiAddr(sub,previousAddr+"/@networkProperties."+selfObject.getNetworkProperties.indexOf(sub))
}
subResult
}
def NodeLinktoXmi(selfObject : NodeLink,refNameInParent : String, addrs : Map[Object,String]) : scala.xml.Node = {
new scala.xml.Node {
  def label = refNameInParent
    def child = {        
       var subresult: List[scala.xml.Node] = List()  
selfObject.getNetworkProperties.foreach { so => 
subresult = subresult ++ List(NetworkPropertytoXmi(so,"networkProperties",addrs))
}
      subresult    
    }              
override def attributes  : scala.xml.MetaData =  { 
new scala.xml.UnprefixedAttribute("networkType",selfObject.getNetworkType.toString,new scala.xml.UnprefixedAttribute("estimatedRate",selfObject.getEstimatedRate.toString,new scala.xml.UnprefixedAttribute("lastCheck",selfObject.getLastCheck.toString,scala.xml.Null)))}
  }                                                  
}
}
