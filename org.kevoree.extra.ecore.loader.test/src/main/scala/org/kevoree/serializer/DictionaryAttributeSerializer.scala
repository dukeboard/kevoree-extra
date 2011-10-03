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
new scala.xml.UnprefixedAttribute("name",selfObject.getName.toString,new scala.xml.UnprefixedAttribute("optional",selfObject.getOptional.toString,new scala.xml.UnprefixedAttribute("state",selfObject.getState.toString,new scala.xml.UnprefixedAttribute("datatype",selfObject.getDatatype.toString,new scala.xml.UnprefixedAttribute("genericTypes",addrs.get(selfObject.getGenericTypes).getOrElse{"wtf"},scala.xml.Null)))))}
  }                                                  
}
}
