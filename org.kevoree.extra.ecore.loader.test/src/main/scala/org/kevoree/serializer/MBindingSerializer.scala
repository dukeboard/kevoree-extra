package org.kevoree.serializer
import org.kevoree._
trait MBindingSerializer 
{
def getMBindingXmiAddr(selfObject : MBinding,previousAddr : String): Map[Object,String] = {
var subResult = Map[Object,String]()
subResult
}
def MBindingtoXmi(selfObject : MBinding,refNameInParent : String, addrs : Map[Object,String]) : scala.xml.Node = {
new scala.xml.Node {
  def label = refNameInParent
    def child = {        
       var subresult: List[scala.xml.Node] = List()  
      subresult    
    }              
override def attributes  : scala.xml.MetaData =  { 
new scala.xml.UnprefixedAttribute("port",addrs.get(selfObject.getPort).getOrElse{"wtf"},new scala.xml.UnprefixedAttribute("hub",addrs.get(selfObject.getHub).getOrElse{"wtf"},scala.xml.Null))}
  }                                                  
}
}
