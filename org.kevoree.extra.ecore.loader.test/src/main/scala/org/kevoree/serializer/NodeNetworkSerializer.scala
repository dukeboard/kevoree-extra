package org.kevoree.serializer
import org.kevoree._
trait NodeNetworkSerializer 
 extends NodeLinkSerializer {
def NodeNetworktoXmi(selfObject : NodeNetwork,refNameInParent : String) : scala.xml.Node = {
new scala.xml.Node {
  def label = refNameInParent
    def child = {        
       var subresult: List[scala.xml.Node] = List()  
selfObject.getLink.foreach { so => 
subresult = subresult ++ List(NodeLinktoXmi(so,"link"))
}
      subresult    
    }              
override def attributes  : scala.xml.MetaData =  { 
new scala.xml.UnprefixedAttribute("initBy","//HELLO",new scala.xml.UnprefixedAttribute("target","//HELLO",scala.xml.Null))}
  }                                                  
}
}
