package org.kevoree.serializer
import org.kevoree._
trait ContainerRootSerializer 
 extends TypedElementSerializer with RepositorySerializer with TypeLibrarySerializer with AdaptationPrimitiveTypeSerializer with ChannelSerializer with MBindingSerializer with DeployUnitSerializer with GroupTypeSerializer with GroupSerializer with ContainerNodeSerializer with TypeDefinitionSerializer with NodeNetworkSerializer {
def ContainerRoottoXmi(selfObject : ContainerRoot) : scala.xml.Node = {
new scala.xml.Node {
  def label = "kevoree:ContainerRoot"
    def child = {        
       var subresult: List[scala.xml.Node] = List()  
selfObject.getNodes.foreach { so => 
subresult = subresult ++ List(ContainerNodetoXmi(so,"nodes"))
}
selfObject.getTypeDefinitions.foreach { so => 
subresult = subresult ++ List(TypeDefinitiontoXmi(so,"typeDefinitions"))
}
selfObject.getRepositories.foreach { so => 
subresult = subresult ++ List(RepositorytoXmi(so,"repositories"))
}
selfObject.getDataTypes.foreach { so => 
subresult = subresult ++ List(TypedElementtoXmi(so,"dataTypes"))
}
selfObject.getLibraries.foreach { so => 
subresult = subresult ++ List(TypeLibrarytoXmi(so,"libraries"))
}
selfObject.getHubs.foreach { so => 
subresult = subresult ++ List(ChanneltoXmi(so,"hubs"))
}
selfObject.getMBindings.foreach { so => 
subresult = subresult ++ List(MBindingtoXmi(so,"mBindings"))
}
selfObject.getDeployUnits.foreach { so => 
subresult = subresult ++ List(DeployUnittoXmi(so,"deployUnits"))
}
selfObject.getNodeNetworks.foreach { so => 
subresult = subresult ++ List(NodeNetworktoXmi(so,"nodeNetworks"))
}
selfObject.getGroups.foreach { so => 
subresult = subresult ++ List(GrouptoXmi(so,"groups"))
}
selfObject.getGroupTypes.foreach { so => 
subresult = subresult ++ List(GroupTypetoXmi(so,"groupTypes"))
}
selfObject.getAdaptationPrimitiveTypes.foreach { so => 
subresult = subresult ++ List(AdaptationPrimitiveTypetoXmi(so,"adaptationPrimitiveTypes"))
}
      subresult                                      
    }                                                
  }                                                  
}
}
