package org.kevoree.serializer
import org.kevoree._
trait DictionaryTypeSerializer 
 extends DictionaryAttributeSerializer with DictionaryValueSerializer {
def getDictionaryTypeXmiAddr(selfObject : DictionaryType,previousAddr : String): Map[Object,String] = {
var subResult = Map[Object,String]()
selfObject.getAttributes.foreach{ sub => 
subResult +=  sub -> (previousAddr+"/@attributes."+selfObject.getAttributes.indexOf(sub) ) 
subResult = subResult ++ getDictionaryAttributeXmiAddr(sub,previousAddr+"/@attributes."+selfObject.getAttributes.indexOf(sub))
}
selfObject.getDefaultValues.foreach{ sub => 
subResult +=  sub -> (previousAddr+"/@defaultValues."+selfObject.getDefaultValues.indexOf(sub) ) 
subResult = subResult ++ getDictionaryValueXmiAddr(sub,previousAddr+"/@defaultValues."+selfObject.getDefaultValues.indexOf(sub))
}
subResult
}
def DictionaryTypetoXmi(selfObject : DictionaryType,refNameInParent : String, addrs : Map[Object,String]) : scala.xml.Node = {
new scala.xml.Node {
  def label = refNameInParent
    def child = {        
       var subresult: List[scala.xml.Node] = List()  
selfObject.getAttributes.foreach { so => 
subresult = subresult ++ List(DictionaryAttributetoXmi(so,"attributes",addrs))
}
selfObject.getDefaultValues.foreach { so => 
subresult = subresult ++ List(DictionaryValuetoXmi(so,"defaultValues",addrs))
}
      subresult    
    }              
  }                                                  
}
}
