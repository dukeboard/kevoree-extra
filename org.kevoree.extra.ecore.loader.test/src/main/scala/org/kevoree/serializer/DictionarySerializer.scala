package org.kevoree.serializer
import org.kevoree._
trait DictionarySerializer 
 extends DictionaryValueSerializer {
def getDictionaryXmiAddr(selfObject : Dictionary,previousAddr : String): Map[Object,String] = {
var subResult = Map[Object,String]()
var i = 0
i=0
selfObject.getValues.foreach{ sub => 
subResult +=  sub -> (previousAddr+"/@values."+i) 
subResult = subResult ++ getDictionaryValueXmiAddr(sub,previousAddr+"/@values."+i)
i=i+1
}
subResult
}
def DictionarytoXmi(selfObject : Dictionary,refNameInParent : String, addrs : Map[Object,String]) : scala.xml.Node = {
new scala.xml.Node {
  def label = refNameInParent
    def child = {        
       var subresult: List[scala.xml.Node] = List()  
selfObject.getValues.foreach { so => 
subresult = subresult ++ List(DictionaryValuetoXmi(so,"values",addrs))
}
      subresult    
    }              
  }                                                  
}
}
