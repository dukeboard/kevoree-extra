package org.kevoree;

/**
 * Created by Ecore Model Generator.
 * @author: Gregory NAIN
 * Date: 04 oct. 11 Time: 20:58
 * Meta-Model:NS_URI=http://kevoree/1.0
 */
trait Group extends KevoreeContainer with Instance {
		private var subNodes : List[ContainerNode] = List[ContainerNode]()


		def getSubNodes : List[ContainerNode] = {
				subNodes
		}

		def setSubNodes(subNodes : List[ContainerNode] ) {
				this.subNodes = subNodes

		}

		def addSubNodes(subNodes : ContainerNode) {
				this.subNodes = this.subNodes ++ List(subNodes)
		}

		def addAllSubNodes(subNodes : List[ContainerNode]) {
				subNodes.foreach{ elem => addSubNodes(elem)}
		}

		def removeSubNodes(subNodes : ContainerNode) {
				if(this.subNodes.size != 0 ) {
						var nList = List[ContainerNode]()
						this.subNodes.foreach(e => if(!e.equals(subNodes)) nList = nList ++ List(e))
						this.subNodes = nList
				}
		}

		def removeAllSubNodes() {
				this.subNodes = List[ContainerNode]()
		}


}
