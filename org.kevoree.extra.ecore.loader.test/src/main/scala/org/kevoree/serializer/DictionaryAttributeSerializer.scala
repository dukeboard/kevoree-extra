package org.kevoree.serializer
import org.kevoree._
trait DictionaryAttributeSerializer 
{
def getDictionaryAttributeXmiAddr(selfObject : DictionaryAttribute,previousAddr : String): Map[Object,String] = {
var subResult = Map[Object,String]()
subResult
}
def DictionaryAttributetoXmi(selfObject : DictionaryAttribute,refNameInParent : String, addrs : Map[Object,String]) : scala.xml.Node = {
new scala.xml.Node {
  def label = refNameInParent
    def child = {        
       var subresult: List[scala.xml.Node] = List()  
      subresult    
    }              
override def attributes  : scala.xml.MetaData =  { 
var subAtts : scala.xml.MetaData = scala.xml.Null
if(selfObject.getName.toString != ""){
subAtts= subAtts.append(new scala.xml.UnprefixedAttribute("name",selfObject.getName.toString,scala.xml.Null))
}
if(selfObject.getOptional.toString != ""){
subAtts= subAtts.append(new scala.xml.UnprefixedAttribute("optional",selfObject.getOptional.toString,scala.xml.Null))
}
if(selfObject.getState.toString != ""){
subAtts= subAtts.append(new scala.xml.UnprefixedAttribute("state",selfObject.getState.toString,scala.xml.Null))
}
if(selfObject.getDatatype.toString != ""){
subAtts= subAtts.append(new scala.xml.UnprefixedAttribute("datatype",selfObject.getDatatype.toString,scala.xml.Null))
}
var subadrsgenericTypes : List[String] = List()
selfObject.getGenericTypes.foreach{sub =>
subadrsgenericTypes = subadrsgenericTypes ++ List(addrs.get(sub).getOrElse{"wtf"})
}
subAtts= subAtts.append(new scala.xml.UnprefixedAttribute("genericTypes",subadrsgenericTypes.mkString(" "),scala.xml.Null))
subAtts}
  }                                                  
}
}
