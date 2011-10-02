package org.kevoree.serializer
import org.kevoree._
trait DictionaryTypeSerializer 
 extends DictionaryAttributeSerializer with DictionaryValueSerializer {
def DictionaryTypetoXmi(selfObject : DictionaryType,refNameInParent : String) : scala.xml.Node = {
new scala.xml.Node {
  def label = refNameInParent
    def child = {        
       var subresult: List[scala.xml.Elem] = List()  
subresult = subresult ++ List(selfObject.getAttributes.DictionaryAttributetoXmi(attributes))
subresult = subresult ++ List(selfObject.getDefaultValues.DictionaryValuetoXmi(defaultValues))
      subresult                                      
    }                                                
  }                                                  
}
}
