package org.kevoree.serializer
import org.kevoree._
trait NodeNetworkSerializer 
 extends NodeLinkSerializer {
def NodeNetworktoXmi(selfObject : NodeNetwork,refNameInParent : String) : scala.xml.Node = {
new scala.xml.Node {
  def label = refNameInParent
    def child = {        
       var subresult: List[scala.xml.Elem] = List()  
subresult = subresult ++ List(selfObject.getLink.NodeLinktoXmi(link))
      subresult                                      
    }                                                
  }                                                  
}
}
