package org.kevoree.serializer
import org.kevoree._
trait MBindingSerializer 
{
def MBindingtoXmi(selfObject : MBinding,refNameInParent : String) : scala.xml.Node = {
new scala.xml.Node {
  def label = refNameInParent
    def child = {        
       var subresult: List[scala.xml.Node] = List()  
      subresult                                      
    }                                                
override def attributes  : scala.xml.MetaData =  { 
new scala.xml.UnprefixedAttribute("port","//HELLO",new scala.xml.UnprefixedAttribute("hub","//HELLO",scala.xml.Null))}
  }                                                  
}
}
