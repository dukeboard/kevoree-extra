package org.kevoree.serializer
import org.kevoree._
trait AdaptationPrimitiveTypeSerializer 
{
def AdaptationPrimitiveTypetoXmi(selfObject : AdaptationPrimitiveType,refNameInParent : String) : scala.xml.Node = {
new scala.xml.Node {
  def label = refNameInParent
    def child = {        
       var subresult: List[scala.xml.Elem] = List()  
      subresult                                      
    }                                                
  }                                                  
}
}
