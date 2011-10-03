package org.kevoree.serializer
import org.kevoree._
trait PortSerializer 
{
def PorttoXmi(selfObject : Port,refNameInParent : String) : scala.xml.Node = {
new scala.xml.Node {
  def label = refNameInParent
    def child = {        
       var subresult: List[scala.xml.Node] = List()  
      subresult                                      
    }                                                
override def attributes  : scala.xml.MetaData =  { 
new scala.xml.UnprefixedAttribute("portTypeRef","//HELLO",scala.xml.Null)}
  }                                                  
}
}
