package org.kevoree.serializer
import org.kevoree._
trait TypeLibrarySerializer 
{
def getTypeLibraryXmiAddr(selfObject : TypeLibrary,previousAddr : String): Map[Object,String] = {
var subResult = Map[Object,String]()
subResult
}
def TypeLibrarytoXmi(selfObject : TypeLibrary,refNameInParent : String, addrs : Map[Object,String]) : scala.xml.Node = {
new scala.xml.Node {
  def label = refNameInParent
    def child = {        
       var subresult: List[scala.xml.Node] = List()  
      subresult    
    }              
override def attributes  : scala.xml.MetaData =  { 
new scala.xml.UnprefixedAttribute("name",selfObject.getName.toString,new scala.xml.UnprefixedAttribute("subTypes",addrs.get(selfObject.getSubTypes).getOrElse{"wtf"},scala.xml.Null))}
  }                                                  
}
}
