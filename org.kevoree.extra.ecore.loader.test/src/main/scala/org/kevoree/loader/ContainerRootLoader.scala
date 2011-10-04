package org.kevoree.loader;

import xml.{XML,NodeSeq}
import java.io.{File, FileInputStream, InputStream}
import org.kevoree._
import org.kevoree.loader.sub._

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
				loadModel(new FileInputStream(file))
		}
		def loadModel(is: InputStream) : Option[ContainerRoot] = {
				val xmlStream = XML.load(is)
				val document = NodeSeq fromSeq xmlStream
				document.headOption match {
						case Some(rootNode) => Some(deserialize(rootNode))
						case None => System.out.println("ContainerRootLoader::Noting at the root !");None
				}
		}

		private def deserialize(rootNode: NodeSeq): ContainerRoot = {
				ContainerRootLoadContext.containerRoot = KevoreeFactory.createContainerRoot
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

				val typeDefinitions = loadTypeDefinition("/", rootNode, "typeDefinitions")
				ContainerRootLoadContext.containerRoot.setTypeDefinitions(typeDefinitions)

				val repositories = loadRepository("/", rootNode, "repositories")
				ContainerRootLoadContext.containerRoot.setRepositories(repositories)

				val dataTypes = loadTypedElement("/", rootNode, "dataTypes")
				ContainerRootLoadContext.containerRoot.setDataTypes(dataTypes)

				val libraries = loadTypeLibrary("/", rootNode, "libraries")
				ContainerRootLoadContext.containerRoot.setLibraries(libraries)

				val hubs = loadChannel("/", rootNode, "hubs")
				ContainerRootLoadContext.containerRoot.setHubs(hubs)

				val mBindings = loadMBinding("/", rootNode, "mBindings")
				ContainerRootLoadContext.containerRoot.setMBindings(mBindings)

				val deployUnits = loadDeployUnit("/", rootNode, "deployUnits")
				ContainerRootLoadContext.containerRoot.setDeployUnits(deployUnits)

				val nodeNetworks = loadNodeNetwork("/", rootNode, "nodeNetworks")
				ContainerRootLoadContext.containerRoot.setNodeNetworks(nodeNetworks)

				val groups = loadGroup("/", rootNode, "groups")
				ContainerRootLoadContext.containerRoot.setGroups(groups)

				val groupTypes = loadGroupType("/", rootNode, "groupTypes")
				ContainerRootLoadContext.containerRoot.setGroupTypes(groupTypes)

				val adaptationPrimitiveTypes = loadAdaptationPrimitiveType("/", rootNode, "adaptationPrimitiveTypes")
				ContainerRootLoadContext.containerRoot.setAdaptationPrimitiveTypes(adaptationPrimitiveTypes)

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
