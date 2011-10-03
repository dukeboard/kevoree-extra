package org.kevoree.serializer
import org.kevoree._
trait RepositorySerializer 
{
def RepositorytoXmi(selfObject : Repository,refNameInParent : String) : scala.xml.Node = {
new scala.xml.Node {
  def label = refNameInParent
    def child = {        
       var subresult: List[scala.xml.Node] = List()  
      subresult                                      
    }                                                
override def attributes  : scala.xml.MetaData =  { 
new scala.xml.UnprefixedAttribute("name",selfObject.getName.toString,new scala.xml.UnprefixedAttribute("url",selfObject.getUrl.toString,new scala.xml.UnprefixedAttribute("units","//HELLO",scala.xml.Null)))}
  }                                                  
}
}
