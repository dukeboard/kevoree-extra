package org.kevoree;

/**
 * Created by Ecore Model Generator.
 * @author: Gregory NAIN
 * Date: 04 oct. 11 Time: 20:58
 * Meta-Model:NS_URI=http://kevoree/1.0
 */
trait ExtraFonctionalProperty extends KevoreeContainer {
		private var portTypes : List[PortTypeRef] = List[PortTypeRef]()


		def getPortTypes : List[PortTypeRef] = {
				portTypes
		}

		def setPortTypes(portTypes : List[PortTypeRef] ) {
				this.portTypes = portTypes

		}

		def addPortTypes(portTypes : PortTypeRef) {
				this.portTypes = this.portTypes ++ List(portTypes)
		}

		def addAllPortTypes(portTypes : List[PortTypeRef]) {
				portTypes.foreach{ elem => addPortTypes(elem)}
		}

		def removePortTypes(portTypes : PortTypeRef) {
				if(this.portTypes.size != 0 ) {
						var nList = List[PortTypeRef]()
						this.portTypes.foreach(e => if(!e.equals(portTypes)) nList = nList ++ List(e))
						this.portTypes = nList
				}
		}

		def removeAllPortTypes() {
				this.portTypes = List[PortTypeRef]()
		}


}
