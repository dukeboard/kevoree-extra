package org.kevoree.serializer
import org.kevoree._
trait DictionarySerializer 
 extends DictionaryValueSerializer {
def DictionarytoXmi(selfObject : Dictionary,refNameInParent : String) : scala.xml.Node = {
new scala.xml.Node {
  def label = refNameInParent
    def child = {        
       var subresult: List[scala.xml.Node] = List()  
selfObject.getValues.foreach { so => 
subresult = subresult ++ List(DictionaryValuetoXmi(so,"values"))
}
      subresult                                      
    }                                                
  }                                                  
}
}
