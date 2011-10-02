package org.kevoree.serializer
import org.kevoree._
trait ContainerNodeSerializer 
 extends DictionarySerializer with ComponentInstanceSerializer {
def ContainerNodetoXmi(selfObject : ContainerNode,refNameInParent : String) : scala.xml.Node = {
new scala.xml.Node {
  def label = refNameInParent
    def child = {        
       var subresult: List[scala.xml.Elem] = List()  
subresult = subresult ++ List(DictionarytoXmi(dictionary))
subresult = subresult ++ List(selfObject.getComponents.ComponentInstancetoXmi(components))
      subresult                                      
    }                                                
  }                                                  
}
}
