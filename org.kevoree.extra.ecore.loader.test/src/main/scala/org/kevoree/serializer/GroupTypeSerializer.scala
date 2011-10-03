package org.kevoree.serializer
import org.kevoree._
trait GroupTypeSerializer 
 extends DictionaryTypeSerializer {
def GroupTypetoXmi(selfObject : GroupType,refNameInParent : String) : scala.xml.Node = {
new scala.xml.Node {
  def label = refNameInParent
    def child = {        
       var subresult: List[scala.xml.Node] = List()  
selfObject.getDictionaryType.map { so => 
subresult = subresult ++ List(DictionaryTypetoXmi(so,"dictionaryType"))
}
      subresult                                      
    }                                                
override def attributes  : scala.xml.MetaData =  { 
new scala.xml.UnprefixedAttribute("name",selfObject.getName.toString,new scala.xml.UnprefixedAttribute("factoryBean",selfObject.getFactoryBean.toString,new scala.xml.UnprefixedAttribute("bean",selfObject.getBean.toString,new scala.xml.UnprefixedAttribute("startMethod",selfObject.getStartMethod.toString,new scala.xml.UnprefixedAttribute("stopMethod",selfObject.getStopMethod.toString,new scala.xml.UnprefixedAttribute("updateMethod",selfObject.getUpdateMethod.toString,new scala.xml.UnprefixedAttribute("deployUnits","//HELLO",new scala.xml.UnprefixedAttribute("superTypes","//HELLO",scala.xml.Null))))))))}
  }                                                  
}
}
