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
var subAtts : scala.xml.MetaData = scala.xml.Null
if(selfObject.getName.toString != ""){
subAtts= subAtts.append(new scala.xml.UnprefixedAttribute("name",selfObject.getName.toString,scala.xml.Null))
}
var subadrssubTypes : List[String] = List()
selfObject.getSubTypes.foreach{sub =>
subadrssubTypes = subadrssubTypes ++ List(addrs.get(sub).getOrElse{"wtf"})
}
subAtts= subAtts.append(new scala.xml.UnprefixedAttribute("subTypes",subadrssubTypes.mkString(" "),scala.xml.Null))
subAtts}
  }                                                  
}
}
