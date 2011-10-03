package org.kevoree.serializer
import org.kevoree._
trait RepositorySerializer 
{
def getRepositoryXmiAddr(selfObject : Repository,previousAddr : String): Map[Object,String] = {
var subResult = Map[Object,String]()
subResult
}
def RepositorytoXmi(selfObject : Repository,refNameInParent : String, addrs : Map[Object,String]) : scala.xml.Node = {
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
if(selfObject.getUrl.toString != ""){
subAtts= subAtts.append(new scala.xml.UnprefixedAttribute("url",selfObject.getUrl.toString,scala.xml.Null))
}
var subadrsunits : List[String] = List()
selfObject.getUnits.foreach{sub =>
subadrsunits = subadrsunits ++ List(addrs.get(sub).getOrElse{"wtf"})
}
if(subadrsunits.size > 0){
subAtts= subAtts.append(new scala.xml.UnprefixedAttribute("units",subadrsunits.mkString(" "),scala.xml.Null))
}
subAtts}
  }                                                  
}
}
