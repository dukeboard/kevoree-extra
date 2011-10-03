package org.kevoree.serializer
import org.kevoree._
trait NodeLinkSerializer 
 extends NetworkPropertySerializer {
def NodeLinktoXmi(selfObject : NodeLink,refNameInParent : String) : scala.xml.Node = {
new scala.xml.Node {
  def label = refNameInParent
    def child = {        
       var subresult: List[scala.xml.Node] = List()  
selfObject.getNetworkProperties.foreach { so => 
subresult = subresult ++ List(NetworkPropertytoXmi(so,"networkProperties"))
}
      subresult    
    }              
override def attributes  : scala.xml.MetaData =  { 
new scala.xml.UnprefixedAttribute("networkType",selfObject.getNetworkType.toString,new scala.xml.UnprefixedAttribute("estimatedRate",selfObject.getEstimatedRate.toString,new scala.xml.UnprefixedAttribute("lastCheck",selfObject.getLastCheck.toString,scala.xml.Null)))}
  }                                                  
}
}
