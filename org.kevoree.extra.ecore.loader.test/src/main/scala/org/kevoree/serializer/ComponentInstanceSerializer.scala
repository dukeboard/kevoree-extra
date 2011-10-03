package org.kevoree.serializer
import org.kevoree._
trait ComponentInstanceSerializer 
 extends DictionarySerializer with PortSerializer {
def ComponentInstancetoXmi(selfObject : ComponentInstance,refNameInParent : String) : scala.xml.Node = {
new scala.xml.Node {
  def label = refNameInParent
    def child = {        
       var subresult: List[scala.xml.Node] = List()  
selfObject.getDictionary.map { so => 
subresult = subresult ++ List(DictionarytoXmi(so,"dictionary"))
}
selfObject.getProvided.foreach { so => 
subresult = subresult ++ List(PorttoXmi(so,"provided"))
}
selfObject.getRequired.foreach { so => 
subresult = subresult ++ List(PorttoXmi(so,"required"))
}
      subresult    
    }              
override def attributes  : scala.xml.MetaData =  { 
new scala.xml.UnprefixedAttribute("name",selfObject.getName.toString,new scala.xml.UnprefixedAttribute("metaData",selfObject.getMetaData.toString,new scala.xml.UnprefixedAttribute("typeDefinition","//HELLO",new scala.xml.UnprefixedAttribute("namespace","//HELLO",scala.xml.Null))))}
  }                                                  
}
}
