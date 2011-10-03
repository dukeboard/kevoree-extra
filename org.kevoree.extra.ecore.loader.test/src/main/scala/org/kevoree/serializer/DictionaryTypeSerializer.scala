package org.kevoree.serializer
import org.kevoree._
trait DictionaryTypeSerializer 
 extends DictionaryAttributeSerializer with DictionaryValueSerializer {
def DictionaryTypetoXmi(selfObject : DictionaryType,refNameInParent : String) : scala.xml.Node = {
new scala.xml.Node {
  def label = refNameInParent
    def child = {        
       var subresult: List[scala.xml.Node] = List()  
selfObject.getAttributes.foreach { so => 
subresult = subresult ++ List(DictionaryAttributetoXmi(so,"attributes"))
}
selfObject.getDefaultValues.foreach { so => 
subresult = subresult ++ List(DictionaryValuetoXmi(so,"defaultValues"))
}
      subresult                                      
    }                                                
  }                                                  
}
}
