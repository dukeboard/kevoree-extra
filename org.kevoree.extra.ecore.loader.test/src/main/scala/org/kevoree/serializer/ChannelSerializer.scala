package org.kevoree.serializer
import org.kevoree._
trait ChannelSerializer 
 extends DictionarySerializer {
def ChanneltoXmi(selfObject : Channel,refNameInParent : String) : scala.xml.Node = {
new scala.xml.Node {
  def label = refNameInParent
    def child = {        
       var subresult: List[scala.xml.Elem] = List()  
subresult = subresult ++ List(selfObject.getDictionary.DictionarytoXmi(dictionary))
      subresult                                      
    }                                                
  }                                                  
}
}
