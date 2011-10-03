package org.kevoree.serializer
import org.kevoree._
trait TypeDefinitionSerializer 
 extends DictionaryTypeSerializer {
def getTypeDefinitionXmiAddr(selfObject : TypeDefinition,previousAddr : String): Map[Object,String] = {
var subResult = Map[Object,String]()
selfObject.getDictionaryType.map{ sub =>
subResult +=  sub -> (previousAddr+"/@dictionaryType" ) 
subResult = subResult ++ getDictionaryTypeXmiAddr(sub,previousAddr+"/@dictionaryType")
}
subResult
}
def TypeDefinitiontoXmi(selfObject : TypeDefinition,refNameInParent : String, addrs : Map[Object,String]) : scala.xml.Node = {
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
var subAtts : scala.xml.MetaData = scala.xml.Null
if(selfObject.getName.toString != ""){
subAtts= subAtts.append(new scala.xml.UnprefixedAttribute("name",selfObject.getName.toString,scala.xml.Null))
}
if(selfObject.getFactoryBean.toString != ""){
subAtts= subAtts.append(new scala.xml.UnprefixedAttribute("factoryBean",selfObject.getFactoryBean.toString,scala.xml.Null))
}
if(selfObject.getBean.toString != ""){
subAtts= subAtts.append(new scala.xml.UnprefixedAttribute("bean",selfObject.getBean.toString,scala.xml.Null))
}
var subadrsdeployUnits : List[String] = List()
selfObject.getDeployUnits.foreach{sub =>
subadrsdeployUnits = subadrsdeployUnits ++ List(addrs.get(sub).getOrElse{"wtf"})
}
if(subadrsdeployUnits.size > 0){
subAtts= subAtts.append(new scala.xml.UnprefixedAttribute("deployUnits",subadrsdeployUnits.mkString(" "),scala.xml.Null))
}
var subadrssuperTypes : List[String] = List()
selfObject.getSuperTypes.foreach{sub =>
subadrssuperTypes = subadrssuperTypes ++ List(addrs.get(sub).getOrElse{"wtf"})
}
if(subadrssuperTypes.size > 0){
subAtts= subAtts.append(new scala.xml.UnprefixedAttribute("superTypes",subadrssuperTypes.mkString(" "),scala.xml.Null))
}
subAtts}
  }                                                  
}
}
