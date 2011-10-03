package org.kevoree.serializer
import org.kevoree._
trait GroupTypeSerializer 
 extends DictionaryTypeSerializer {
def getGroupTypeXmiAddr(selfObject : GroupType,previousAddr : String): Map[Object,String] = {
var subResult = Map[Object,String]()
selfObject.getDictionaryType.map{ sub =>
subResult +=  sub -> (previousAddr+"/@dictionaryType" ) 
subResult = subResult ++ getDictionaryTypeXmiAddr(sub,previousAddr+"/@dictionaryType")
}
subResult
}
def GroupTypetoXmi(selfObject : GroupType,refNameInParent : String, addrs : Map[Object,String]) : scala.xml.Node = {
new scala.xml.Node {
  def label = refNameInParent
    def child = {        
       var subresult: List[scala.xml.Node] = List()  
selfObject.getDictionaryType.map { so => 
subresult = subresult ++ List(DictionaryTypetoXmi(so,"dictionaryType",addrs))
}
      subresult    
    }              
override def attributes  : scala.xml.MetaData =  { 
new scala.xml.UnprefixedAttribute("name",selfObject.getName.toString,new scala.xml.UnprefixedAttribute("factoryBean",selfObject.getFactoryBean.toString,new scala.xml.UnprefixedAttribute("bean",selfObject.getBean.toString,new scala.xml.UnprefixedAttribute("startMethod",selfObject.getStartMethod.toString,new scala.xml.UnprefixedAttribute("stopMethod",selfObject.getStopMethod.toString,new scala.xml.UnprefixedAttribute("updateMethod",selfObject.getUpdateMethod.toString,new scala.xml.UnprefixedAttribute("deployUnits",addrs.get(selfObject.getDeployUnits).getOrElse{"wtf"},new scala.xml.UnprefixedAttribute("superTypes",addrs.get(selfObject.getSuperTypes).getOrElse{"wtf"},scala.xml.Null))))))))}
  }                                                  
}
}
