package org.kevoree.serializer
import org.kevoree._
trait PortSerializer 
{
def getPortXmiAddr(selfObject : Port,previousAddr : String): Map[Object,String] = {
var subResult = Map[Object,String]()
subResult
}
def PorttoXmi(selfObject : Port,refNameInParent : String, addrs : Map[Object,String]) : scala.xml.Node = {
new scala.xml.Node {
  def label = refNameInParent
    def child = {        
       var subresult: List[scala.xml.Node] = List()  
      subresult    
    }              
override def attributes  : scala.xml.MetaData =  { 
new scala.xml.UnprefixedAttribute("portTypeRef",addrs.get(selfObject.getPortTypeRef).getOrElse{"wtf"},scala.xml.Null)}
  }                                                  
}
}
