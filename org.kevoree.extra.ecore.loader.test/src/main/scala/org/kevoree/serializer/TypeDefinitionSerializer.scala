package org.kevoree.serializer
import org.kevoree._
trait TypeDefinitionSerializer 
 extends DictionaryTypeSerializer {
def TypeDefinitiontoXmi(selfObject : TypeDefinition,refNameInParent : String) : scala.xml.Node = {
new scala.xml.Node {
  def label = refNameInParent
    def child = {        
       var subresult: List[scala.xml.Elem] = List()  
subresult = subresult ++ List(selfObject.getDictionaryType.DictionaryTypetoXmi(dictionaryType))
      subresult                                      
    }                                                
  }                                                  
}
}
