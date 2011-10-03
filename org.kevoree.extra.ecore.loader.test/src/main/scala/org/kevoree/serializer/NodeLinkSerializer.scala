package org.kevoree.serializer
import org.kevoree._
trait NodeLinkSerializer 
 extends NetworkPropertySerializer {
def NodeLinktoXmi(selfObject : NodeLink,refNameInParent : String) : scala.xml.Node = {
new scala.xml.Node {
  def label = refNameInParent
    def child = {        
       var subresult: List[scala.xml.Elem] = List()  
subresult = subresult ++ List(selfObject.getNetworkProperties.NetworkPropertytoXmi(networkProperties))
      subresult                                      
    }                                                
  }                                                  
}
}
