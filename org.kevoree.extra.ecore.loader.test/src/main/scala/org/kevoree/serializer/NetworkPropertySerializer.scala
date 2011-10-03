package org.kevoree.serializer
import org.kevoree._
trait NetworkPropertySerializer 
{
def getNetworkPropertyXmiAddr(selfObject : NetworkProperty,previousAddr : String): Map[Object,String] = {
var subResult = Map[Object,String]()
subResult
}
def NetworkPropertytoXmi(selfObject : NetworkProperty,refNameInParent : String, addrs : Map[Object,String]) : scala.xml.Node = {
new scala.xml.Node {
  def label = refNameInParent
    def child = {        
       var subresult: List[scala.xml.Node] = List()  
      subresult    
    }              
override def attributes  : scala.xml.MetaData =  { 
new scala.xml.UnprefixedAttribute("name",selfObject.getName.toString,new scala.xml.UnprefixedAttribute("value",selfObject.getValue.toString,new scala.xml.UnprefixedAttribute("lastCheck",selfObject.getLastCheck.toString,scala.xml.Null)))}
  }                                                  
}
}
