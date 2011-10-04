package org.kevoree;

/**
 * Created by Ecore Model Generator.
 * @author: Gregory NAIN
 * Date: 04 oct. 11 Time: 20:58
 * Meta-Model:NS_URI=http://kevoree/1.0
 */
trait IntegrationPattern extends KevoreeContainer with NamedElement {
		private var extraFonctionalProperties : List[ExtraFonctionalProperty] = List[ExtraFonctionalProperty]()

		private var portTypes : List[PortTypeRef] = List[PortTypeRef]()


		def getExtraFonctionalProperties : List[ExtraFonctionalProperty] = {
				extraFonctionalProperties
		}

		def setExtraFonctionalProperties(extraFonctionalProperties : List[ExtraFonctionalProperty] ) {
				this.extraFonctionalProperties = extraFonctionalProperties
				extraFonctionalProperties.foreach{e=>e.eContainer = this}

		}

		def addExtraFonctionalProperties(extraFonctionalProperties : ExtraFonctionalProperty) {
				extraFonctionalProperties.eContainer = this
				this.extraFonctionalProperties = this.extraFonctionalProperties ++ List(extraFonctionalProperties)
		}

		def addAllExtraFonctionalProperties(extraFonctionalProperties : List[ExtraFonctionalProperty]) {
				extraFonctionalProperties.foreach{ elem => addExtraFonctionalProperties(elem)}
		}

		def removeExtraFonctionalProperties(extraFonctionalProperties : ExtraFonctionalProperty) {
				if(this.extraFonctionalProperties.size != 0 ) {
						var nList = List[ExtraFonctionalProperty]()
						this.extraFonctionalProperties.foreach(e => if(!e.equals(extraFonctionalProperties)) nList = nList ++ List(e))
						this.extraFonctionalProperties = nList
				}
		}

		def removeAllExtraFonctionalProperties() {
				this.extraFonctionalProperties = List[ExtraFonctionalProperty]()
		}


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
