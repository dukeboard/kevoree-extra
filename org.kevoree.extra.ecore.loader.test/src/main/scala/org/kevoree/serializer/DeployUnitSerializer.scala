package org.kevoree.serializer
import org.kevoree._
trait DeployUnitSerializer 
{
def getDeployUnitXmiAddr(selfObject : DeployUnit,previousAddr : String): Map[Object,String] = {
var subResult = Map[Object,String]()
var i = 0
subResult
}
def DeployUnittoXmi(selfObject : DeployUnit,refNameInParent : String, addrs : Map[Object,String]) : scala.xml.Node = {
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
if(selfObject.getGroupName.toString != ""){
subAtts= subAtts.append(new scala.xml.UnprefixedAttribute("groupName",selfObject.getGroupName.toString,scala.xml.Null))
}
if(selfObject.getUnitName.toString != ""){
subAtts= subAtts.append(new scala.xml.UnprefixedAttribute("unitName",selfObject.getUnitName.toString,scala.xml.Null))
}
if(selfObject.getVersion.toString != ""){
subAtts= subAtts.append(new scala.xml.UnprefixedAttribute("version",selfObject.getVersion.toString,scala.xml.Null))
}
if(selfObject.getUrl.toString != ""){
subAtts= subAtts.append(new scala.xml.UnprefixedAttribute("url",selfObject.getUrl.toString,scala.xml.Null))
}
if(selfObject.getHashcode.toString != ""){
subAtts= subAtts.append(new scala.xml.UnprefixedAttribute("hashcode",selfObject.getHashcode.toString,scala.xml.Null))
}
var subadrsrequiredLibs : List[String] = List()
selfObject.getRequiredLibs.foreach{sub =>
subadrsrequiredLibs = subadrsrequiredLibs ++ List(addrs.get(sub).getOrElse{"wtf"})
}
if(subadrsrequiredLibs.size > 0){
subAtts= subAtts.append(new scala.xml.UnprefixedAttribute("requiredLibs",subadrsrequiredLibs.mkString(" "),scala.xml.Null))
}
selfObject.getTargetNodeType.map{sub =>
subAtts= subAtts.append(new scala.xml.UnprefixedAttribute("targetNodeType",addrs.get(sub).getOrElse{"wtf"},scala.xml.Null))
}
subAtts}
  }                                                  
}
}
