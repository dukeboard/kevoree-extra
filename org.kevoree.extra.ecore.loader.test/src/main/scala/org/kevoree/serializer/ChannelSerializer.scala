package org.kevoree.serializer
import org.kevoree._
trait ChannelSerializer 
 extends DictionarySerializer {
def getChannelXmiAddr(selfObject : Channel,previousAddr : String): Map[Object,String] = {
var subResult = Map[Object,String]()
selfObject.getDictionary.map{ sub =>
subResult +=  sub -> (previousAddr+"/@dictionary" ) 
subResult = subResult ++ getDictionaryXmiAddr(sub,previousAddr+"/@dictionary")
}
subResult
}
def ChanneltoXmi(selfObject : Channel,refNameInParent : String, addrs : Map[Object,String]) : scala.xml.Node = {
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
new scala.xml.UnprefixedAttribute("name",selfObject.getName.toString,new scala.xml.UnprefixedAttribute("metaData",selfObject.getMetaData.toString,new scala.xml.UnprefixedAttribute("typeDefinition",addrs.get(selfObject.getTypeDefinition).getOrElse{"wtf"},scala.xml.Null)))}
  }                                                  
}
}
