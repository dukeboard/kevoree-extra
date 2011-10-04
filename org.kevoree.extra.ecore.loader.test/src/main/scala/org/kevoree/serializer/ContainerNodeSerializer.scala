package org.kevoree.serializer
import org.kevoree._
trait ContainerNodeSerializer 
 extends DictionarySerializer with ComponentInstanceSerializer {
def getContainerNodeXmiAddr(selfObject : ContainerNode,previousAddr : String): Map[Object,String] = {
var subResult = Map[Object,String]()
var i = 0
selfObject.getDictionary.map{ sub =>
subResult +=  sub -> (previousAddr+"/@dictionary" ) 
subResult = subResult ++ getDictionaryXmiAddr(sub,previousAddr+"/@dictionary")
}
i=0
selfObject.getComponents.foreach{ sub => 
subResult +=  sub -> (previousAddr+"/@components."+i) 
subResult = subResult ++ getComponentInstanceXmiAddr(sub,previousAddr+"/@components."+i)
i=i+1
}
subResult
}
def ContainerNodetoXmi(selfObject : ContainerNode,refNameInParent : String, addrs : Map[Object,String]) : scala.xml.Node = {
new scala.xml.Node {
  def label = refNameInParent
    def child = {        
       var subresult: List[scala.xml.Node] = List()  
selfObject.getDictionary.map { so => 
subresult = subresult ++ List(DictionarytoXmi(so,"dictionary",addrs))
}
selfObject.getComponents.foreach { so => 
subresult = subresult ++ List(ComponentInstancetoXmi(so,"components",addrs))
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
var subadrshosts : List[String] = List()
selfObject.getHosts.foreach{sub =>
subadrshosts = subadrshosts ++ List(addrs.get(sub).getOrElse{"wtf"})
}
if(subadrshosts.size > 0){
subAtts= subAtts.append(new scala.xml.UnprefixedAttribute("hosts",subadrshosts.mkString(" "),scala.xml.Null))
}
subAtts}
  }                                                  
}
}
