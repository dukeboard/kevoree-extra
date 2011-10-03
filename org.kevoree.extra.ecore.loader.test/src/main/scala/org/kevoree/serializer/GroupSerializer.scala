package org.kevoree.serializer
import org.kevoree._
trait GroupSerializer 
 extends DictionarySerializer {
def GrouptoXmi(selfObject : Group,refNameInParent : String) : scala.xml.Node = {
new scala.xml.Node {
  def label = refNameInParent
    def child = {        
       var subresult: List[scala.xml.Node] = List()  
selfObject.getDictionary.map { so => 
subresult = subresult ++ List(DictionarytoXmi(so,"dictionary"))
}
      subresult    
    }              
override def attributes  : scala.xml.MetaData =  { 
new scala.xml.UnprefixedAttribute("name",selfObject.getName.toString,new scala.xml.UnprefixedAttribute("metaData",selfObject.getMetaData.toString,new scala.xml.UnprefixedAttribute("typeDefinition","//HELLO",new scala.xml.UnprefixedAttribute("subNodes","//HELLO",scala.xml.Null))))}
  }                                                  
}
}
