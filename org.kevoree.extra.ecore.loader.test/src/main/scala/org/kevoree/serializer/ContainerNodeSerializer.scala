package org.kevoree.serializer
import org.kevoree._
trait ContainerNodeSerializer 
 extends DictionarySerializer with ComponentInstanceSerializer {
def ContainerNodetoXmi(selfObject : ContainerNode,refNameInParent : String) : scala.xml.Node = {
new scala.xml.Node {
  def label = refNameInParent
    def child = {        
       var subresult: List[scala.xml.Node] = List()  
selfObject.getDictionary.map { so => 
subresult = subresult ++ List(DictionarytoXmi(so,"dictionary"))
}
selfObject.getComponents.foreach { so => 
subresult = subresult ++ List(ComponentInstancetoXmi(so,"components"))
}
      subresult    
    }              
override def attributes  : scala.xml.MetaData =  { 
new scala.xml.UnprefixedAttribute("name",selfObject.getName.toString,new scala.xml.UnprefixedAttribute("metaData",selfObject.getMetaData.toString,new scala.xml.UnprefixedAttribute("typeDefinition","//HELLO",new scala.xml.UnprefixedAttribute("hosts","//HELLO",scala.xml.Null))))}
  }                                                  
}
}
