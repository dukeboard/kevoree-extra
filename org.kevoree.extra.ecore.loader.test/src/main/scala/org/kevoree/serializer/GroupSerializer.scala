package org.kevoree.serializer
import org.kevoree._
trait GroupSerializer 
 extends DictionarySerializer {
def getGroupXmiAddr(selfObject : Group,previousAddr : String): Map[Object,String] = {
var subResult = Map[Object,String]()
selfObject.getDictionary.map{ sub =>
subResult +=  sub -> (previousAddr+"/@dictionary" ) 
subResult = subResult ++ getDictionaryXmiAddr(sub,previousAddr+"/@dictionary")
}
subResult
}
def GrouptoXmi(selfObject : Group,refNameInParent : String, addrs : Map[Object,String]) : scala.xml.Node = {
new scala.xml.Node {
  def label = refNameInParent
    def child = {        
       var subresult: List[scala.xml.Node] = List()  
selfObject.getDictionary.map { so => 
subresult = subresult ++ List(DictionarytoXmi(so,"dictionary",addrs))
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
var subadrssubNodes : List[String] = List()
selfObject.getSubNodes.foreach{sub =>
subadrssubNodes = subadrssubNodes ++ List(addrs.get(sub).getOrElse{"wtf"})
}
if(subadrssubNodes.size > 0){
subAtts= subAtts.append(new scala.xml.UnprefixedAttribute("subNodes",subadrssubNodes.mkString(" "),scala.xml.Null))
}
subAtts}
  }                                                  
}
}
