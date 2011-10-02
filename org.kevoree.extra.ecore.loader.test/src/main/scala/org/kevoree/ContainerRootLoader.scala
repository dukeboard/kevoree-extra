package org.kevoree;

import xml.{XML,NodeSeq}
import java.io.File
import org.kevoree._
import org.kevoree.sub._

object ContainerRootLoader
		 extends ContainerNodeLoader
		 with TypeDefinitionLoader
		 with RepositoryLoader
		 with TypedElementLoader
		 with TypeLibraryLoader
		 with ChannelLoader
		 with MBindingLoader
		 with DeployUnitLoader
		 with NodeNetworkLoader
		 with MetricLoader
		 with MetricTypeLoader
		 with PhysicalNodeLoader
		 with GroupLoader
		 with GroupTypeLoader {

		def loadModel(file: File) : Option[ContainerRoot] = {
				val xmlStream = XML.loadFile(file)
				val document = NodeSeq fromSeq xmlStream
				document.headOption match {
						case Some(rootNode) => Some(deserialize(rootNode))
						case None => System.out.println("ContainerRootLoader::Noting at the root !");None
				}
		}
		private def deserialize(rootNode: NodeSeq): ContainerRoot = {
ContainerRootLoadContext.containerRoot = KevoreePackage.createContainerRoot
ContainerRootLoadContext.xmiContent = rootNode
ContainerRootLoadContext.map = Map[String, Any]()
ContainerRootLoadContext.stats = Map[String, Int]()
				loadElements(rootNode)
				resolveElements(rootNode)
				ContainerRootLoadContext.containerRoot
		}
		private def loadElements(rootNode: NodeSeq) {
				loadContainerNodes("/", rootNode)
				loadTypeDefinitions("/", rootNode)
				loadRepositorys("/", rootNode)
				loadTypedElements("/", rootNode)
				loadTypeLibrarys("/", rootNode)
				loadChannels("/", rootNode)
				loadMBindings("/", rootNode)
				loadDeployUnits("/", rootNode)
				loadNodeNetworks("/", rootNode)
				loadMetrics("/", rootNode)
				loadMetricTypes("/", rootNode)
				loadPhysicalNodes("/", rootNode)
				loadGroups("/", rootNode)
				loadGroupTypes("/", rootNode)
		}
		private def resolveElements(rootNode: NodeSeq) {
				resolveContainerNodes("/", rootNode,ContainerRootLoadContext.containerRoot)
				resolveTypeDefinitions("/", rootNode,ContainerRootLoadContext.containerRoot)
				resolveRepositorys("/", rootNode,ContainerRootLoadContext.containerRoot)
				resolveTypedElements("/", rootNode,ContainerRootLoadContext.containerRoot)
				resolveTypeLibrarys("/", rootNode,ContainerRootLoadContext.containerRoot)
				resolveChannels("/", rootNode,ContainerRootLoadContext.containerRoot)
				resolveMBindings("/", rootNode,ContainerRootLoadContext.containerRoot)
				resolveDeployUnits("/", rootNode,ContainerRootLoadContext.containerRoot)
				resolveNodeNetworks("/", rootNode,ContainerRootLoadContext.containerRoot)
				resolveMetrics("/", rootNode,ContainerRootLoadContext.containerRoot)
				resolveMetricTypes("/", rootNode,ContainerRootLoadContext.containerRoot)
				resolvePhysicalNodes("/", rootNode,ContainerRootLoadContext.containerRoot)
				resolveGroups("/", rootNode,ContainerRootLoadContext.containerRoot)
				resolveGroupTypes("/", rootNode,ContainerRootLoadContext.containerRoot)
		}

}
