package org.kevoree.serializer
import org.kevoree._
trait DeployUnitSerializer 
{
def getDeployUnitXmiAddr(selfObject : DeployUnit,previousAddr : String): Map[Object,String] = {
var subResult = Map[Object,String]()
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
new scala.xml.UnprefixedAttribute("name",selfObject.getName.toString,new scala.xml.UnprefixedAttribute("groupName",selfObject.getGroupName.toString,new scala.xml.UnprefixedAttribute("unitName",selfObject.getUnitName.toString,new scala.xml.UnprefixedAttribute("version",selfObject.getVersion.toString,new scala.xml.UnprefixedAttribute("url",selfObject.getUrl.toString,new scala.xml.UnprefixedAttribute("hashcode",selfObject.getHashcode.toString,new scala.xml.UnprefixedAttribute("requiredLibs",addrs.get(selfObject.getRequiredLibs).getOrElse{"wtf"},new scala.xml.UnprefixedAttribute("targetNodeType",addrs.get(selfObject.getTargetNodeType).getOrElse{"wtf"},scala.xml.Null))))))))}
  }                                                  
}
}
