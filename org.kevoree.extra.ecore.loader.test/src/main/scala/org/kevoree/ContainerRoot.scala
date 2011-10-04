package org.kevoree;

/**
 * Created by Ecore Model Generator.
 * @author: Gregory NAIN
 * Date: 04 oct. 11 Time: 09:49
 * Meta-Model:NS_URI=http://kevoree/1.0
 */
trait ContainerRoot extends KevoreeContainer {
		private var nodes : List[ContainerNode] = List[ContainerNode]()

		private var typeDefinitions : List[TypeDefinition] = List[TypeDefinition]()

		private var repositories : List[Repository] = List[Repository]()

		private var dataTypes : List[TypedElement] = List[TypedElement]()

		private var libraries : List[TypeLibrary] = List[TypeLibrary]()

		private var hubs : List[Channel] = List[Channel]()

		private var mBindings : List[MBinding] = List[MBinding]()

		private var deployUnits : List[DeployUnit] = List[DeployUnit]()

		private var nodeNetworks : List[NodeNetwork] = List[NodeNetwork]()

		private var groups : List[Group] = List[Group]()

		private var groupTypes : List[GroupType] = List[GroupType]()

		private var adaptationPrimitiveTypes : List[AdaptationPrimitiveType] = List[AdaptationPrimitiveType]()


		def getNodes : List[ContainerNode] = {
				nodes
		}

		def setNodes(nodes : List[ContainerNode] ) {
				this.nodes = nodes
		}

		def addNodes(nodes : ContainerNode) {
				this.nodes = this.nodes ++ List(nodes)
		}

		def removeNodes(nodes : ContainerNode) {
				if(this.nodes.size != 0 ) {
						var nList = List[ContainerNode]()
						this.nodes.foreach(e => if(!e.equals(nodes)) nList = nList ++ List(e))
						this.nodes = nList
				}
		}


		def getTypeDefinitions : List[TypeDefinition] = {
				typeDefinitions
		}

		def setTypeDefinitions(typeDefinitions : List[TypeDefinition] ) {
				this.typeDefinitions = typeDefinitions
		}

		def addTypeDefinitions(typeDefinitions : TypeDefinition) {
				this.typeDefinitions = this.typeDefinitions ++ List(typeDefinitions)
		}

		def removeTypeDefinitions(typeDefinitions : TypeDefinition) {
				if(this.typeDefinitions.size != 0 ) {
						var nList = List[TypeDefinition]()
						this.typeDefinitions.foreach(e => if(!e.equals(typeDefinitions)) nList = nList ++ List(e))
						this.typeDefinitions = nList
				}
		}


		def getRepositories : List[Repository] = {
				repositories
		}

		def setRepositories(repositories : List[Repository] ) {
				this.repositories = repositories
		}

		def addRepositories(repositories : Repository) {
				this.repositories = this.repositories ++ List(repositories)
		}

		def removeRepositories(repositories : Repository) {
				if(this.repositories.size != 0 ) {
						var nList = List[Repository]()
						this.repositories.foreach(e => if(!e.equals(repositories)) nList = nList ++ List(e))
						this.repositories = nList
				}
		}


		def getDataTypes : List[TypedElement] = {
				dataTypes
		}

		def setDataTypes(dataTypes : List[TypedElement] ) {
				this.dataTypes = dataTypes
		}

		def addDataTypes(dataTypes : TypedElement) {
				this.dataTypes = this.dataTypes ++ List(dataTypes)
		}

		def removeDataTypes(dataTypes : TypedElement) {
				if(this.dataTypes.size != 0 ) {
						var nList = List[TypedElement]()
						this.dataTypes.foreach(e => if(!e.equals(dataTypes)) nList = nList ++ List(e))
						this.dataTypes = nList
				}
		}


		def getLibraries : List[TypeLibrary] = {
				libraries
		}

		def setLibraries(libraries : List[TypeLibrary] ) {
				this.libraries = libraries
		}

		def addLibraries(libraries : TypeLibrary) {
				this.libraries = this.libraries ++ List(libraries)
		}

		def removeLibraries(libraries : TypeLibrary) {
				if(this.libraries.size != 0 ) {
						var nList = List[TypeLibrary]()
						this.libraries.foreach(e => if(!e.equals(libraries)) nList = nList ++ List(e))
						this.libraries = nList
				}
		}


		def getHubs : List[Channel] = {
				hubs
		}

		def setHubs(hubs : List[Channel] ) {
				this.hubs = hubs
		}

		def addHubs(hubs : Channel) {
				this.hubs = this.hubs ++ List(hubs)
		}

		def removeHubs(hubs : Channel) {
				if(this.hubs.size != 0 ) {
						var nList = List[Channel]()
						this.hubs.foreach(e => if(!e.equals(hubs)) nList = nList ++ List(e))
						this.hubs = nList
				}
		}


		def getMBindings : List[MBinding] = {
				mBindings
		}

		def setMBindings(mBindings : List[MBinding] ) {
				this.mBindings = mBindings
		}

		def addMBindings(mBindings : MBinding) {
				this.mBindings = this.mBindings ++ List(mBindings)
		}

		def removeMBindings(mBindings : MBinding) {
				if(this.mBindings.size != 0 ) {
						var nList = List[MBinding]()
						this.mBindings.foreach(e => if(!e.equals(mBindings)) nList = nList ++ List(e))
						this.mBindings = nList
				}
		}


		def getDeployUnits : List[DeployUnit] = {
				deployUnits
		}

		def setDeployUnits(deployUnits : List[DeployUnit] ) {
				this.deployUnits = deployUnits
		}

		def addDeployUnits(deployUnits : DeployUnit) {
				this.deployUnits = this.deployUnits ++ List(deployUnits)
		}

		def removeDeployUnits(deployUnits : DeployUnit) {
				if(this.deployUnits.size != 0 ) {
						var nList = List[DeployUnit]()
						this.deployUnits.foreach(e => if(!e.equals(deployUnits)) nList = nList ++ List(e))
						this.deployUnits = nList
				}
		}


		def getNodeNetworks : List[NodeNetwork] = {
				nodeNetworks
		}

		def setNodeNetworks(nodeNetworks : List[NodeNetwork] ) {
				this.nodeNetworks = nodeNetworks
		}

		def addNodeNetworks(nodeNetworks : NodeNetwork) {
				this.nodeNetworks = this.nodeNetworks ++ List(nodeNetworks)
		}

		def removeNodeNetworks(nodeNetworks : NodeNetwork) {
				if(this.nodeNetworks.size != 0 ) {
						var nList = List[NodeNetwork]()
						this.nodeNetworks.foreach(e => if(!e.equals(nodeNetworks)) nList = nList ++ List(e))
						this.nodeNetworks = nList
				}
		}


		def getGroups : List[Group] = {
				groups
		}

		def setGroups(groups : List[Group] ) {
				this.groups = groups
		}

		def addGroups(groups : Group) {
				this.groups = this.groups ++ List(groups)
		}

		def removeGroups(groups : Group) {
				if(this.groups.size != 0 ) {
						var nList = List[Group]()
						this.groups.foreach(e => if(!e.equals(groups)) nList = nList ++ List(e))
						this.groups = nList
				}
		}


		def getGroupTypes : List[GroupType] = {
				groupTypes
		}

		def setGroupTypes(groupTypes : List[GroupType] ) {
				this.groupTypes = groupTypes
		}

		def addGroupTypes(groupTypes : GroupType) {
				this.groupTypes = this.groupTypes ++ List(groupTypes)
		}

		def removeGroupTypes(groupTypes : GroupType) {
				if(this.groupTypes.size != 0 ) {
						var nList = List[GroupType]()
						this.groupTypes.foreach(e => if(!e.equals(groupTypes)) nList = nList ++ List(e))
						this.groupTypes = nList
				}
		}


		def getAdaptationPrimitiveTypes : List[AdaptationPrimitiveType] = {
				adaptationPrimitiveTypes
		}

		def setAdaptationPrimitiveTypes(adaptationPrimitiveTypes : List[AdaptationPrimitiveType] ) {
				this.adaptationPrimitiveTypes = adaptationPrimitiveTypes
		}

		def addAdaptationPrimitiveTypes(adaptationPrimitiveTypes : AdaptationPrimitiveType) {
				this.adaptationPrimitiveTypes = this.adaptationPrimitiveTypes ++ List(adaptationPrimitiveTypes)
		}

		def removeAdaptationPrimitiveTypes(adaptationPrimitiveTypes : AdaptationPrimitiveType) {
				if(this.adaptationPrimitiveTypes.size != 0 ) {
						var nList = List[AdaptationPrimitiveType]()
						this.adaptationPrimitiveTypes.foreach(e => if(!e.equals(adaptationPrimitiveTypes)) nList = nList ++ List(e))
						this.adaptationPrimitiveTypes = nList
				}
		}


}
