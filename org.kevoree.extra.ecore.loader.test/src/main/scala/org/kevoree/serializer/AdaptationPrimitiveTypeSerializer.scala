package org.kevoree.serializer
import org.kevoree._
trait AdaptationPrimitiveTypeSerializer 
{
def getAdaptationPrimitiveTypeXmiAddr(selfObject : AdaptationPrimitiveType,previousAddr : String): Map[Object,String] = {
var subResult = Map[Object,String]()
var i = 0
subResult
}
def AdaptationPrimitiveTypetoXmi(selfObject : AdaptationPrimitiveType,refNameInParent : String, addrs : Map[Object,String]) : scala.xml.Node = {
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
subAtts}
  }                                                  
}
}
