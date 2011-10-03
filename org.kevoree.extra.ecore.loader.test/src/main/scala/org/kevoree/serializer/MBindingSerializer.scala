package org.kevoree.serializer
import org.kevoree._
trait MBindingSerializer 
{
def MBindingtoXmi(selfObject : MBinding,refNameInParent : String) : scala.xml.Node = {
new scala.xml.Node {
  def label = refNameInParent
    def child = {        
       var subresult: List[scala.xml.Elem] = List()  
      subresult                                      
    }                                                
  }                                                  
}
}
