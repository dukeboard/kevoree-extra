package org.kevoree.serializer
import org.kevoree._
trait DeployUnitSerializer 
{
def DeployUnittoXmi(selfObject : DeployUnit,refNameInParent : String) : scala.xml.Node = {
new scala.xml.Node {
  def label = refNameInParent
    def child = {        
       var subresult: List[scala.xml.Elem] = List()  
      subresult                                      
    }                                                
  }                                                  
}
}
