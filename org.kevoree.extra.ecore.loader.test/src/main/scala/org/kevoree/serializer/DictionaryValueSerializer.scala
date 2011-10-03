package org.kevoree.serializer
import org.kevoree._
trait DictionaryValueSerializer 
{
def DictionaryValuetoXmi(selfObject : DictionaryValue,refNameInParent : String) : scala.xml.Node = {
new scala.xml.Node {
  def label = refNameInParent
    def child = {        
       var subresult: List[scala.xml.Node] = List()  
      subresult                                      
    }                                                
override def attributes  : scala.xml.MetaData =  { 
new scala.xml.UnprefixedAttribute("value",selfObject.getValue.toString,new scala.xml.UnprefixedAttribute("attribute","//HELLO",scala.xml.Null))}
  }                                                  
}
}
