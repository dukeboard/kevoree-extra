package org.kevoree.serializer
import org.kevoree._
trait ComponentInstanceSerializer 
 extends DictionarySerializer with PortSerializer {
def getComponentInstanceXmiAddr(selfObject : ComponentInstance,previousAddr : String): Map[Object,String] = {
var subResult = Map[Object,String]()
selfObject.getDictionary.map{ sub =>
subResult +=  sub -> (previousAddr+"/@dictionary" ) 
subResult = subResult ++ getDictionaryXmiAddr(sub,previousAddr+"/@dictionary")
}
selfObject.getProvided.foreach{ sub => 
subResult +=  sub -> (previousAddr+"/@provided."+selfObject.getProvided.indexOf(sub) ) 
subResult = subResult ++ getPortXmiAddr(sub,previousAddr+"/@provided."+selfObject.getProvided.indexOf(sub))
}
selfObject.getRequired.foreach{ sub => 
subResult +=  sub -> (previousAddr+"/@required."+selfObject.getRequired.indexOf(sub) ) 
subResult = subResult ++ getPortXmiAddr(sub,previousAddr+"/@required."+selfObject.getRequired.indexOf(sub))
}
subResult
}
def ComponentInstancetoXmi(selfObject : ComponentInstance,refNameInParent : String, addrs : Map[Object,String]) : scala.xml.Node = {
new scala.xml.Node {
  def label = refNameInParent
    def child = {        
       var subresult: List[scala.xml.Node] = List()  
selfObject.getDictionary.map { so => 
subresult = subresult ++ List(DictionarytoXmi(so,"dictionary",addrs))
}
selfObject.getProvided.foreach { so => 
subresult = subresult ++ List(PorttoXmi(so,"provided",addrs))
}
selfObject.getRequired.foreach { so => 
subresult = subresult ++ List(PorttoXmi(so,"required",addrs))
}
      subresult    
    }              
override def attributes  : scala.xml.MetaData =  { 
var subAtts : scala.xml.MetaData = scala.xml.Null
if(selfObject.getName.toString != ""){
subAtts= subAtts.append(new scala.xml.UnprefixedAttribute("name",selfObject.getName.toString,scala.xml.Null))
}
if(selfObject.getMetaData.toString != ""){
subAtts= subAtts.append(new scala.xml.UnprefixedAttribute("metaData",selfObject.getMetaData.toString,scala.xml.Null))
}
subAtts= subAtts.append(new scala.xml.UnprefixedAttribute("typeDefinition",addrs.get(selfObject.getTypeDefinition).getOrElse{"wtf"},scala.xml.Null))
selfObject.getNamespace.map{sub =>
subAtts= subAtts.append(new scala.xml.UnprefixedAttribute("namespace",addrs.get(sub).getOrElse{"wtf"},scala.xml.Null))
}
subAtts}
  }                                                  
}
}
