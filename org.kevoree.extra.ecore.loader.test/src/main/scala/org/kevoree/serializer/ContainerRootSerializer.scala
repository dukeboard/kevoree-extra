package org.kevoree.serializer
import org.kevoree._
trait ContainerRootSerializer 
 extends ContainerNodeSerializer with TypeDefinitionSerializer with RepositorySerializer with TypedElementSerializer with TypeLibrarySerializer with ChannelSerializer with MBindingSerializer with DeployUnitSerializer with NodeNetworkSerializer with GroupSerializer with GroupTypeSerializer with AdaptationPrimitiveTypeSerializer {
def ContainerRoottoXmi(selfObject : ContainerRoot,refNameInParent : String) : scala.xml.Node = {
new scala.xml.Node {
  def label = refNameInParent
    def child = {        
       var subresult: List[scala.xml.Elem] = List()  
subresult = subresult ++ List(selfObject.getNodes.ContainerNodetoXmi(nodes))
subresult = subresult ++ List(selfObject.getTypeDefinitions.TypeDefinitiontoXmi(typeDefinitions))
subresult = subresult ++ List(selfObject.getRepositories.RepositorytoXmi(repositories))
subresult = subresult ++ List(selfObject.getDataTypes.TypedElementtoXmi(dataTypes))
subresult = subresult ++ List(selfObject.getLibraries.TypeLibrarytoXmi(libraries))
subresult = subresult ++ List(selfObject.getHubs.ChanneltoXmi(hubs))
subresult = subresult ++ List(selfObject.getMBindings.MBindingtoXmi(mBindings))
subresult = subresult ++ List(selfObject.getDeployUnits.DeployUnittoXmi(deployUnits))
subresult = subresult ++ List(selfObject.getNodeNetworks.NodeNetworktoXmi(nodeNetworks))
subresult = subresult ++ List(selfObject.getGroups.GrouptoXmi(groups))
subresult = subresult ++ List(selfObject.getGroupTypes.GroupTypetoXmi(groupTypes))
subresult = subresult ++ List(selfObject.getAdaptationPrimitiveTypes.AdaptationPrimitiveTypetoXmi(adaptationPrimitiveTypes))
      subresult                                      
    }                                                
  }                                                  
}
}
