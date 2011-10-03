package org.kevoree.serializer
import org.kevoree._
trait DeployUnitSerializer 
{
def DeployUnittoXmi(selfObject : DeployUnit,refNameInParent : String) : scala.xml.Node = {
new scala.xml.Node {
  def label = refNameInParent
    def child = {        
       var subresult: List[scala.xml.Node] = List()  
      subresult    
    }              
override def attributes  : scala.xml.MetaData =  { 
new scala.xml.UnprefixedAttribute("name",selfObject.getName.toString,new scala.xml.UnprefixedAttribute("groupName",selfObject.getGroupName.toString,new scala.xml.UnprefixedAttribute("unitName",selfObject.getUnitName.toString,new scala.xml.UnprefixedAttribute("version",selfObject.getVersion.toString,new scala.xml.UnprefixedAttribute("url",selfObject.getUrl.toString,new scala.xml.UnprefixedAttribute("hashcode",selfObject.getHashcode.toString,new scala.xml.UnprefixedAttribute("requiredLibs","//HELLO",new scala.xml.UnprefixedAttribute("targetNodeType","//HELLO",scala.xml.Null))))))))}
  }                                                  
}
}
