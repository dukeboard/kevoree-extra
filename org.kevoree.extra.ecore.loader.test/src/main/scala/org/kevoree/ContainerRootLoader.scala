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
		with GroupLoader
		with GroupTypeLoader
		with AdaptationPrimitiveTypeLoader {

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
				loadContainerRoot(rootNode)
				resolveElements(rootNode)
				ContainerRootLoadContext.containerRoot
		}

		private def loadContainerRoot(rootNode: NodeSeq) {

				val nodes = loadContainerNode("/", rootNode, "nodes")
				ContainerRootLoadContext.containerRoot.setNodes(nodes)
				nodes.foreach{e=>e.eContainer=ContainerRootLoadContext.containerRoot }

				val typeDefinitions = loadTypeDefinition("/", rootNode, "typeDefinitions")
				ContainerRootLoadContext.containerRoot.setTypeDefinitions(typeDefinitions)
				typeDefinitions.foreach{e=>e.eContainer=ContainerRootLoadContext.containerRoot }

				val repositories = loadRepository("/", rootNode, "repositories")
				ContainerRootLoadContext.containerRoot.setRepositories(repositories)
				repositories.foreach{e=>e.eContainer=ContainerRootLoadContext.containerRoot }

				val dataTypes = loadTypedElement("/", rootNode, "dataTypes")
				ContainerRootLoadContext.containerRoot.setDataTypes(dataTypes)
				dataTypes.foreach{e=>e.eContainer=ContainerRootLoadContext.containerRoot }

				val libraries = loadTypeLibrary("/", rootNode, "libraries")
				ContainerRootLoadContext.containerRoot.setLibraries(libraries)
				libraries.foreach{e=>e.eContainer=ContainerRootLoadContext.containerRoot }

				val hubs = loadChannel("/", rootNode, "hubs")
				ContainerRootLoadContext.containerRoot.setHubs(hubs)
				hubs.foreach{e=>e.eContainer=ContainerRootLoadContext.containerRoot }

				val mBindings = loadMBinding("/", rootNode, "mBindings")
				ContainerRootLoadContext.containerRoot.setMBindings(mBindings)
				mBindings.foreach{e=>e.eContainer=ContainerRootLoadContext.containerRoot }

				val deployUnits = loadDeployUnit("/", rootNode, "deployUnits")
				ContainerRootLoadContext.containerRoot.setDeployUnits(deployUnits)
				deployUnits.foreach{e=>e.eContainer=ContainerRootLoadContext.containerRoot }

				val nodeNetworks = loadNodeNetwork("/", rootNode, "nodeNetworks")
				ContainerRootLoadContext.containerRoot.setNodeNetworks(nodeNetworks)
				nodeNetworks.foreach{e=>e.eContainer=ContainerRootLoadContext.containerRoot }

				val groups = loadGroup("/", rootNode, "groups")
				ContainerRootLoadContext.containerRoot.setGroups(groups)
				groups.foreach{e=>e.eContainer=ContainerRootLoadContext.containerRoot }

				val groupTypes = loadGroupType("/", rootNode, "groupTypes")
				ContainerRootLoadContext.containerRoot.setGroupTypes(groupTypes)
				groupTypes.foreach{e=>e.eContainer=ContainerRootLoadContext.containerRoot }

				val adaptationPrimitiveTypes = loadAdaptationPrimitiveType("/", rootNode, "adaptationPrimitiveTypes")
				ContainerRootLoadContext.containerRoot.setAdaptationPrimitiveTypes(adaptationPrimitiveTypes)
				adaptationPrimitiveTypes.foreach{e=>e.eContainer=ContainerRootLoadContext.containerRoot }

		}

		private def resolveElements(rootNode: NodeSeq) {
				resolveContainerNode("/", rootNode, "nodes")
				resolveTypeDefinition("/", rootNode, "typeDefinitions")
				resolveRepository("/", rootNode, "repositories")
				resolveTypedElement("/", rootNode, "dataTypes")
				resolveTypeLibrary("/", rootNode, "libraries")
				resolveChannel("/", rootNode, "hubs")
				resolveMBinding("/", rootNode, "mBindings")
				resolveDeployUnit("/", rootNode, "deployUnits")
				resolveNodeNetwork("/", rootNode, "nodeNetworks")
				resolveGroup("/", rootNode, "groups")
				resolveGroupType("/", rootNode, "groupTypes")
				resolveAdaptationPrimitiveType("/", rootNode, "adaptationPrimitiveTypes")
		}

}
