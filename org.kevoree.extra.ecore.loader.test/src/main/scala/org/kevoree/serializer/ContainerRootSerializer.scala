package org.kevoree.serializer
import org.kevoree._
trait ContainerRootSerializer 
 extends TypedElementSerializer with RepositorySerializer with TypeLibrarySerializer with AdaptationPrimitiveTypeSerializer with ChannelSerializer with MBindingSerializer with DeployUnitSerializer with GroupTypeSerializer with GroupSerializer with ContainerNodeSerializer with TypeDefinitionSerializer with NodeNetworkSerializer {
def getContainerRootXmiAddr(selfObject : ContainerRoot,previousAddr : String): Map[Object,String] = {
var subResult = Map[Object,String]()
selfObject.getNodes.foreach{ sub => 
subResult +=  sub -> (previousAddr+"/@nodes."+selfObject.getNodes.indexOf(sub) ) 
subResult = subResult ++ getContainerNodeXmiAddr(sub,previousAddr+"/@nodes."+selfObject.getNodes.indexOf(sub))
}
selfObject.getTypeDefinitions.foreach{ sub => 
subResult +=  sub -> (previousAddr+"/@typeDefinitions."+selfObject.getTypeDefinitions.indexOf(sub) ) 
subResult = subResult ++ getTypeDefinitionXmiAddr(sub,previousAddr+"/@typeDefinitions."+selfObject.getTypeDefinitions.indexOf(sub))
}
selfObject.getRepositories.foreach{ sub => 
subResult +=  sub -> (previousAddr+"/@repositories."+selfObject.getRepositories.indexOf(sub) ) 
subResult = subResult ++ getRepositoryXmiAddr(sub,previousAddr+"/@repositories."+selfObject.getRepositories.indexOf(sub))
}
selfObject.getDataTypes.foreach{ sub => 
subResult +=  sub -> (previousAddr+"/@dataTypes."+selfObject.getDataTypes.indexOf(sub) ) 
subResult = subResult ++ getTypedElementXmiAddr(sub,previousAddr+"/@dataTypes."+selfObject.getDataTypes.indexOf(sub))
}
selfObject.getLibraries.foreach{ sub => 
subResult +=  sub -> (previousAddr+"/@libraries."+selfObject.getLibraries.indexOf(sub) ) 
subResult = subResult ++ getTypeLibraryXmiAddr(sub,previousAddr+"/@libraries."+selfObject.getLibraries.indexOf(sub))
}
selfObject.getHubs.foreach{ sub => 
subResult +=  sub -> (previousAddr+"/@hubs."+selfObject.getHubs.indexOf(sub) ) 
subResult = subResult ++ getChannelXmiAddr(sub,previousAddr+"/@hubs."+selfObject.getHubs.indexOf(sub))
}
selfObject.getMBindings.foreach{ sub => 
subResult +=  sub -> (previousAddr+"/@mBindings."+selfObject.getMBindings.indexOf(sub) ) 
subResult = subResult ++ getMBindingXmiAddr(sub,previousAddr+"/@mBindings."+selfObject.getMBindings.indexOf(sub))
}
selfObject.getDeployUnits.foreach{ sub => 
subResult +=  sub -> (previousAddr+"/@deployUnits."+selfObject.getDeployUnits.indexOf(sub) ) 
subResult = subResult ++ getDeployUnitXmiAddr(sub,previousAddr+"/@deployUnits."+selfObject.getDeployUnits.indexOf(sub))
}
selfObject.getNodeNetworks.foreach{ sub => 
subResult +=  sub -> (previousAddr+"/@nodeNetworks."+selfObject.getNodeNetworks.indexOf(sub) ) 
subResult = subResult ++ getNodeNetworkXmiAddr(sub,previousAddr+"/@nodeNetworks."+selfObject.getNodeNetworks.indexOf(sub))
}
selfObject.getGroups.foreach{ sub => 
subResult +=  sub -> (previousAddr+"/@groups."+selfObject.getGroups.indexOf(sub) ) 
subResult = subResult ++ getGroupXmiAddr(sub,previousAddr+"/@groups."+selfObject.getGroups.indexOf(sub))
}
selfObject.getGroupTypes.foreach{ sub => 
subResult +=  sub -> (previousAddr+"/@groupTypes."+selfObject.getGroupTypes.indexOf(sub) ) 
subResult = subResult ++ getGroupTypeXmiAddr(sub,previousAddr+"/@groupTypes."+selfObject.getGroupTypes.indexOf(sub))
}
selfObject.getAdaptationPrimitiveTypes.foreach{ sub => 
subResult +=  sub -> (previousAddr+"/@adaptationPrimitiveTypes."+selfObject.getAdaptationPrimitiveTypes.indexOf(sub) ) 
subResult = subResult ++ getAdaptationPrimitiveTypeXmiAddr(sub,previousAddr+"/@adaptationPrimitiveTypes."+selfObject.getAdaptationPrimitiveTypes.indexOf(sub))
}
subResult
}
def ContainerRoottoXmi(selfObject : ContainerRoot, addrs : Map[Object,String]) : scala.xml.Node = {
new scala.xml.Node {
  def label = "kevoree:ContainerRoot"
    def child = {        
       var subresult: List[scala.xml.Node] = List()  
selfObject.getNodes.foreach { so => 
subresult = subresult ++ List(ContainerNodetoXmi(so,"nodes",addrs))
}
selfObject.getTypeDefinitions.foreach { so => 
subresult = subresult ++ List(TypeDefinitiontoXmi(so,"typeDefinitions",addrs))
}
selfObject.getRepositories.foreach { so => 
subresult = subresult ++ List(RepositorytoXmi(so,"repositories",addrs))
}
selfObject.getDataTypes.foreach { so => 
subresult = subresult ++ List(TypedElementtoXmi(so,"dataTypes",addrs))
}
selfObject.getLibraries.foreach { so => 
subresult = subresult ++ List(TypeLibrarytoXmi(so,"libraries",addrs))
}
selfObject.getHubs.foreach { so => 
subresult = subresult ++ List(ChanneltoXmi(so,"hubs",addrs))
}
selfObject.getMBindings.foreach { so => 
subresult = subresult ++ List(MBindingtoXmi(so,"mBindings",addrs))
}
selfObject.getDeployUnits.foreach { so => 
subresult = subresult ++ List(DeployUnittoXmi(so,"deployUnits",addrs))
}
selfObject.getNodeNetworks.foreach { so => 
subresult = subresult ++ List(NodeNetworktoXmi(so,"nodeNetworks",addrs))
}
selfObject.getGroups.foreach { so => 
subresult = subresult ++ List(GrouptoXmi(so,"groups",addrs))
}
selfObject.getGroupTypes.foreach { so => 
subresult = subresult ++ List(GroupTypetoXmi(so,"groupTypes",addrs))
}
selfObject.getAdaptationPrimitiveTypes.foreach { so => 
subresult = subresult ++ List(AdaptationPrimitiveTypetoXmi(so,"adaptationPrimitiveTypes",addrs))
}
      subresult    
    }              
  }                                                  
}
}
