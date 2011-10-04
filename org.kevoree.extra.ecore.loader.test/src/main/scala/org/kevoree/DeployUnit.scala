package org.kevoree;

/**
 * Created by Ecore Model Generator.
 * @author: Gregory NAIN
 * Date: 04 oct. 11 Time: 13:29
 * Meta-Model:NS_URI=http://kevoree/1.0
 */
trait DeployUnit extends KevoreeContainer with NamedElement {
		private var groupName : java.lang.String = ""

		private var unitName : java.lang.String = ""

		private var version : java.lang.String = ""

		private var url : java.lang.String = ""

		private var timestamp : java.lang.String = ""

		private var requiredLibs : List[DeployUnit] = List[DeployUnit]()

		private var targetNodeType : Option[NodeType] = None


		def getGroupName : java.lang.String = {
				groupName
		}

		def setGroupName(groupName : java.lang.String) {
				this.groupName = groupName
		}

		def getUnitName : java.lang.String = {
				unitName
		}

		def setUnitName(unitName : java.lang.String) {
				this.unitName = unitName
		}

		def getVersion : java.lang.String = {
				version
		}

		def setVersion(version : java.lang.String) {
				this.version = version
		}

		def getUrl : java.lang.String = {
				url
		}

		def setUrl(url : java.lang.String) {
				this.url = url
		}

		def getTimestamp : java.lang.String = {
				timestamp
		}

		def setTimestamp(timestamp : java.lang.String) {
				this.timestamp = timestamp
		}

		def getRequiredLibs : List[DeployUnit] = {
				requiredLibs
		}

		def setRequiredLibs(requiredLibs : List[DeployUnit] ) {
				this.requiredLibs = requiredLibs
		}

		def addRequiredLibs(requiredLibs : DeployUnit) {
				this.requiredLibs = this.requiredLibs ++ List(requiredLibs)
		}

		def removeRequiredLibs(requiredLibs : DeployUnit) {
				if(this.requiredLibs.size != 0 ) {
						var nList = List[DeployUnit]()
						this.requiredLibs.foreach(e => if(!e.equals(requiredLibs)) nList = nList ++ List(e))
						this.requiredLibs = nList
				}
		}


		def getTargetNodeType : Option[NodeType] = {
				targetNodeType
		}

		def setTargetNodeType(targetNodeType : NodeType ) {
				targetNodeType match {
						case l : NodeType => this.targetNodeType = Some(targetNodeType)
						case _ => this.targetNodeType = None
				}

		}

}
