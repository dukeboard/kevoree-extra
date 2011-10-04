package org.kevoree.serializer
import org.kevoree._
trait ContainerRootSerializer 
 extends TypedElementSerializer with RepositorySerializer with TypeLibrarySerializer with AdaptationPrimitiveTypeSerializer with ChannelSerializer with MBindingSerializer with DeployUnitSerializer with GroupTypeSerializer with GroupSerializer with ContainerNodeSerializer with TypeDefinitionSerializer with NodeNetworkSerializer {
def getContainerRootXmiAddr(selfObject : ContainerRoot,previousAddr : String): Map[Object,String] = {
var subResult = Map[Object,String]()
var i = 0
i=0
selfObject.getNodes.foreach{ sub => 
subResult +=  sub -> (previousAddr+"/@nodes."+i) 
subResult = subResult ++ getContainerNodeXmiAddr(sub,previousAddr+"/@nodes."+i)
i=i+1
}
i=0
selfObject.getTypeDefinitions.foreach{ sub => 
subResult +=  sub -> (previousAddr+"/@typeDefinitions."+i) 
subResult = subResult ++ getTypeDefinitionXmiAddr(sub,previousAddr+"/@typeDefinitions."+i)
i=i+1
}
i=0
selfObject.getRepositories.foreach{ sub => 
subResult +=  sub -> (previousAddr+"/@repositories."+i) 
subResult = subResult ++ getRepositoryXmiAddr(sub,previousAddr+"/@repositories."+i)
i=i+1
}
i=0
selfObject.getDataTypes.foreach{ sub => 
subResult +=  sub -> (previousAddr+"/@dataTypes."+i) 
subResult = subResult ++ getTypedElementXmiAddr(sub,previousAddr+"/@dataTypes."+i)
i=i+1
}
i=0
selfObject.getLibraries.foreach{ sub => 
subResult +=  sub -> (previousAddr+"/@libraries."+i) 
subResult = subResult ++ getTypeLibraryXmiAddr(sub,previousAddr+"/@libraries."+i)
i=i+1
}
i=0
selfObject.getHubs.foreach{ sub => 
subResult +=  sub -> (previousAddr+"/@hubs."+i) 
subResult = subResult ++ getChannelXmiAddr(sub,previousAddr+"/@hubs."+i)
i=i+1
}
i=0
selfObject.getMBindings.foreach{ sub => 
subResult +=  sub -> (previousAddr+"/@mBindings."+i) 
subResult = subResult ++ getMBindingXmiAddr(sub,previousAddr+"/@mBindings."+i)
i=i+1
}
i=0
selfObject.getDeployUnits.foreach{ sub => 
subResult +=  sub -> (previousAddr+"/@deployUnits."+i) 
subResult = subResult ++ getDeployUnitXmiAddr(sub,previousAddr+"/@deployUnits."+i)
i=i+1
}
i=0
selfObject.getNodeNetworks.foreach{ sub => 
subResult +=  sub -> (previousAddr+"/@nodeNetworks."+i) 
subResult = subResult ++ getNodeNetworkXmiAddr(sub,previousAddr+"/@nodeNetworks."+i)
i=i+1
}
i=0
selfObject.getGroups.foreach{ sub => 
subResult +=  sub -> (previousAddr+"/@groups."+i) 
subResult = subResult ++ getGroupXmiAddr(sub,previousAddr+"/@groups."+i)
i=i+1
}
i=0
selfObject.getGroupTypes.foreach{ sub => 
subResult +=  sub -> (previousAddr+"/@groupTypes."+i) 
subResult = subResult ++ getGroupTypeXmiAddr(sub,previousAddr+"/@groupTypes."+i)
i=i+1
}
i=0
selfObject.getAdaptationPrimitiveTypes.foreach{ sub => 
subResult +=  sub -> (previousAddr+"/@adaptationPrimitiveTypes."+i) 
subResult = subResult ++ getAdaptationPrimitiveTypeXmiAddr(sub,previousAddr+"/@adaptationPrimitiveTypes."+i)
i=i+1
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
