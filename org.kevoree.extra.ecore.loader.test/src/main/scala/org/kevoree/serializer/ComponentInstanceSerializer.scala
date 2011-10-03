package org.kevoree.serializer
import org.kevoree._
trait ComponentInstanceSerializer 
 extends DictionarySerializer with PortSerializer with PortSerializer {
def ComponentInstancetoXmi(selfObject : ComponentInstance,refNameInParent : String) : scala.xml.Node = {
new scala.xml.Node {
  def label = refNameInParent
    def child = {        
       var subresult: List[scala.xml.Elem] = List()  
subresult = subresult ++ List(selfObject.getDictionary.DictionarytoXmi(dictionary))
subresult = subresult ++ List(selfObject.getProvided.PorttoXmi(provided))
subresult = subresult ++ List(selfObject.getRequired.PorttoXmi(required))
      subresult                                      
    }                                                
  }                                                  
}
}
