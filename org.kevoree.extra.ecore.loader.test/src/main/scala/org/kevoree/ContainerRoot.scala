package org.kevoree;

/**
 * Created by Ecore Model Generator.
 * @author: Gregory NAIN
 * Date: 04 oct. 11 Time: 20:58
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
				nodes.foreach{e=>e.eContainer = this}

		}

		def addNodes(nodes : ContainerNode) {
				nodes.eContainer = this
				this.nodes = this.nodes ++ List(nodes)
		}

		def addAllNodes(nodes : List[ContainerNode]) {
				nodes.foreach{ elem => addNodes(elem)}
		}

		def removeNodes(nodes : ContainerNode) {
				if(this.nodes.size != 0 ) {
						var nList = List[ContainerNode]()
						this.nodes.foreach(e => if(!e.equals(nodes)) nList = nList ++ List(e))
						this.nodes = nList
				}
		}

		def removeAllNodes() {
				this.nodes = List[ContainerNode]()
		}


		def getTypeDefinitions : List[TypeDefinition] = {
				typeDefinitions
		}

		def setTypeDefinitions(typeDefinitions : List[TypeDefinition] ) {
				this.typeDefinitions = typeDefinitions
				typeDefinitions.foreach{e=>e.eContainer = this}

		}

		def addTypeDefinitions(typeDefinitions : TypeDefinition) {
				typeDefinitions.eContainer = this
				this.typeDefinitions = this.typeDefinitions ++ List(typeDefinitions)
		}

		def addAllTypeDefinitions(typeDefinitions : List[TypeDefinition]) {
				typeDefinitions.foreach{ elem => addTypeDefinitions(elem)}
		}

		def removeTypeDefinitions(typeDefinitions : TypeDefinition) {
				if(this.typeDefinitions.size != 0 ) {
						var nList = List[TypeDefinition]()
						this.typeDefinitions.foreach(e => if(!e.equals(typeDefinitions)) nList = nList ++ List(e))
						this.typeDefinitions = nList
				}
		}

		def removeAllTypeDefinitions() {
				this.typeDefinitions = List[TypeDefinition]()
		}


		def getRepositories : List[Repository] = {
				repositories
		}

		def setRepositories(repositories : List[Repository] ) {
				this.repositories = repositories
				repositories.foreach{e=>e.eContainer = this}

		}

		def addRepositories(repositories : Repository) {
				repositories.eContainer = this
				this.repositories = this.repositories ++ List(repositories)
		}

		def addAllRepositories(repositories : List[Repository]) {
				repositories.foreach{ elem => addRepositories(elem)}
		}

		def removeRepositories(repositories : Repository) {
				if(this.repositories.size != 0 ) {
						var nList = List[Repository]()
						this.repositories.foreach(e => if(!e.equals(repositories)) nList = nList ++ List(e))
						this.repositories = nList
				}
		}

		def removeAllRepositories() {
				this.repositories = List[Repository]()
		}


		def getDataTypes : List[TypedElement] = {
				dataTypes
		}

		def setDataTypes(dataTypes : List[TypedElement] ) {
				this.dataTypes = dataTypes
				dataTypes.foreach{e=>e.eContainer = this}

		}

		def addDataTypes(dataTypes : TypedElement) {
				dataTypes.eContainer = this
				this.dataTypes = this.dataTypes ++ List(dataTypes)
		}

		def addAllDataTypes(dataTypes : List[TypedElement]) {
				dataTypes.foreach{ elem => addDataTypes(elem)}
		}

		def removeDataTypes(dataTypes : TypedElement) {
				if(this.dataTypes.size != 0 ) {
						var nList = List[TypedElement]()
						this.dataTypes.foreach(e => if(!e.equals(dataTypes)) nList = nList ++ List(e))
						this.dataTypes = nList
				}
		}

		def removeAllDataTypes() {
				this.dataTypes = List[TypedElement]()
		}


		def getLibraries : List[TypeLibrary] = {
				libraries
		}

		def setLibraries(libraries : List[TypeLibrary] ) {
				this.libraries = libraries
				libraries.foreach{e=>e.eContainer = this}

		}

		def addLibraries(libraries : TypeLibrary) {
				libraries.eContainer = this
				this.libraries = this.libraries ++ List(libraries)
		}

		def addAllLibraries(libraries : List[TypeLibrary]) {
				libraries.foreach{ elem => addLibraries(elem)}
		}

		def removeLibraries(libraries : TypeLibrary) {
				if(this.libraries.size != 0 ) {
						var nList = List[TypeLibrary]()
						this.libraries.foreach(e => if(!e.equals(libraries)) nList = nList ++ List(e))
						this.libraries = nList
				}
		}

		def removeAllLibraries() {
				this.libraries = List[TypeLibrary]()
		}


		def getHubs : List[Channel] = {
				hubs
		}

		def setHubs(hubs : List[Channel] ) {
				this.hubs = hubs
				hubs.foreach{e=>e.eContainer = this}

		}

		def addHubs(hubs : Channel) {
				hubs.eContainer = this
				this.hubs = this.hubs ++ List(hubs)
		}

		def addAllHubs(hubs : List[Channel]) {
				hubs.foreach{ elem => addHubs(elem)}
		}

		def removeHubs(hubs : Channel) {
				if(this.hubs.size != 0 ) {
						var nList = List[Channel]()
						this.hubs.foreach(e => if(!e.equals(hubs)) nList = nList ++ List(e))
						this.hubs = nList
				}
		}

		def removeAllHubs() {
				this.hubs = List[Channel]()
		}


		def getMBindings : List[MBinding] = {
				mBindings
		}

		def setMBindings(mBindings : List[MBinding] ) {
				this.mBindings = mBindings
				mBindings.foreach{e=>e.eContainer = this}

		}

		def addMBindings(mBindings : MBinding) {
				mBindings.eContainer = this
				this.mBindings = this.mBindings ++ List(mBindings)
		}

		def addAllMBindings(mBindings : List[MBinding]) {
				mBindings.foreach{ elem => addMBindings(elem)}
		}

		def removeMBindings(mBindings : MBinding) {
				if(this.mBindings.size != 0 ) {
						var nList = List[MBinding]()
						this.mBindings.foreach(e => if(!e.equals(mBindings)) nList = nList ++ List(e))
						this.mBindings = nList
				}
		}

		def removeAllMBindings() {
				this.mBindings = List[MBinding]()
		}


		def getDeployUnits : List[DeployUnit] = {
				deployUnits
		}

		def setDeployUnits(deployUnits : List[DeployUnit] ) {
				this.deployUnits = deployUnits
				deployUnits.foreach{e=>e.eContainer = this}

		}

		def addDeployUnits(deployUnits : DeployUnit) {
				deployUnits.eContainer = this
				this.deployUnits = this.deployUnits ++ List(deployUnits)
		}

		def addAllDeployUnits(deployUnits : List[DeployUnit]) {
				deployUnits.foreach{ elem => addDeployUnits(elem)}
		}

		def removeDeployUnits(deployUnits : DeployUnit) {
				if(this.deployUnits.size != 0 ) {
						var nList = List[DeployUnit]()
						this.deployUnits.foreach(e => if(!e.equals(deployUnits)) nList = nList ++ List(e))
						this.deployUnits = nList
				}
		}

		def removeAllDeployUnits() {
				this.deployUnits = List[DeployUnit]()
		}


		def getNodeNetworks : List[NodeNetwork] = {
				nodeNetworks
		}

		def setNodeNetworks(nodeNetworks : List[NodeNetwork] ) {
				this.nodeNetworks = nodeNetworks
				nodeNetworks.foreach{e=>e.eContainer = this}

		}

		def addNodeNetworks(nodeNetworks : NodeNetwork) {
				nodeNetworks.eContainer = this
				this.nodeNetworks = this.nodeNetworks ++ List(nodeNetworks)
		}

		def addAllNodeNetworks(nodeNetworks : List[NodeNetwork]) {
				nodeNetworks.foreach{ elem => addNodeNetworks(elem)}
		}

		def removeNodeNetworks(nodeNetworks : NodeNetwork) {
				if(this.nodeNetworks.size != 0 ) {
						var nList = List[NodeNetwork]()
						this.nodeNetworks.foreach(e => if(!e.equals(nodeNetworks)) nList = nList ++ List(e))
						this.nodeNetworks = nList
				}
		}

		def removeAllNodeNetworks() {
				this.nodeNetworks = List[NodeNetwork]()
		}


		def getGroups : List[Group] = {
				groups
		}

		def setGroups(groups : List[Group] ) {
				this.groups = groups
				groups.foreach{e=>e.eContainer = this}

		}

		def addGroups(groups : Group) {
				groups.eContainer = this
				this.groups = this.groups ++ List(groups)
		}

		def addAllGroups(groups : List[Group]) {
				groups.foreach{ elem => addGroups(elem)}
		}

		def removeGroups(groups : Group) {
				if(this.groups.size != 0 ) {
						var nList = List[Group]()
						this.groups.foreach(e => if(!e.equals(groups)) nList = nList ++ List(e))
						this.groups = nList
				}
		}

		def removeAllGroups() {
				this.groups = List[Group]()
		}


		def getGroupTypes : List[GroupType] = {
				groupTypes
		}

		def setGroupTypes(groupTypes : List[GroupType] ) {
				this.groupTypes = groupTypes
				groupTypes.foreach{e=>e.eContainer = this}

		}

		def addGroupTypes(groupTypes : GroupType) {
				groupTypes.eContainer = this
				this.groupTypes = this.groupTypes ++ List(groupTypes)
		}

		def addAllGroupTypes(groupTypes : List[GroupType]) {
				groupTypes.foreach{ elem => addGroupTypes(elem)}
		}

		def removeGroupTypes(groupTypes : GroupType) {
				if(this.groupTypes.size != 0 ) {
						var nList = List[GroupType]()
						this.groupTypes.foreach(e => if(!e.equals(groupTypes)) nList = nList ++ List(e))
						this.groupTypes = nList
				}
		}

		def removeAllGroupTypes() {
				this.groupTypes = List[GroupType]()
		}


		def getAdaptationPrimitiveTypes : List[AdaptationPrimitiveType] = {
				adaptationPrimitiveTypes
		}

		def setAdaptationPrimitiveTypes(adaptationPrimitiveTypes : List[AdaptationPrimitiveType] ) {
				this.adaptationPrimitiveTypes = adaptationPrimitiveTypes
				adaptationPrimitiveTypes.foreach{e=>e.eContainer = this}

		}

		def addAdaptationPrimitiveTypes(adaptationPrimitiveTypes : AdaptationPrimitiveType) {
				adaptationPrimitiveTypes.eContainer = this
				this.adaptationPrimitiveTypes = this.adaptationPrimitiveTypes ++ List(adaptationPrimitiveTypes)
		}

		def addAllAdaptationPrimitiveTypes(adaptationPrimitiveTypes : List[AdaptationPrimitiveType]) {
				adaptationPrimitiveTypes.foreach{ elem => addAdaptationPrimitiveTypes(elem)}
		}

		def removeAdaptationPrimitiveTypes(adaptationPrimitiveTypes : AdaptationPrimitiveType) {
				if(this.adaptationPrimitiveTypes.size != 0 ) {
						var nList = List[AdaptationPrimitiveType]()
						this.adaptationPrimitiveTypes.foreach(e => if(!e.equals(adaptationPrimitiveTypes)) nList = nList ++ List(e))
						this.adaptationPrimitiveTypes = nList
				}
		}

		def removeAllAdaptationPrimitiveTypes() {
				this.adaptationPrimitiveTypes = List[AdaptationPrimitiveType]()
		}


}
