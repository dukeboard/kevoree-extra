package org.kevoree;

/**
 * Created by Ecore Model Generator.
 * @author: Gregory NAIN
 * Date: 04 oct. 11 Time: 09:47
 * Meta-Model:NS_URI=http://kevoree/1.0
 */
trait ComponentType extends KevoreeContainer with LifeCycleTypeDefinition {
		private var required : List[PortTypeRef] = List[PortTypeRef]()

		private var integrationPatterns : List[IntegrationPattern] = List[IntegrationPattern]()

		private var extraFonctionalProperties : Option[ExtraFonctionalProperty] = None

		private var provided : List[PortTypeRef] = List[PortTypeRef]()


		def getRequired : List[PortTypeRef] = {
				required
		}

		def setRequired(required : List[PortTypeRef] ) {
				this.required = required
		}

		def addRequired(required : PortTypeRef) {
				this.required = this.required ++ List(required)
		}

		def removeRequired(required : PortTypeRef) {
				if(this.required.size != 0 ) {
						var nList = List[PortTypeRef]()
						this.required.foreach(e => if(!e.equals(required)) nList = nList ++ List(e))
						this.required = nList
				}
		}


		def getIntegrationPatterns : List[IntegrationPattern] = {
				integrationPatterns
		}

		def setIntegrationPatterns(integrationPatterns : List[IntegrationPattern] ) {
				this.integrationPatterns = integrationPatterns
		}

		def addIntegrationPatterns(integrationPatterns : IntegrationPattern) {
				this.integrationPatterns = this.integrationPatterns ++ List(integrationPatterns)
		}

		def removeIntegrationPatterns(integrationPatterns : IntegrationPattern) {
				if(this.integrationPatterns.size != 0 ) {
						var nList = List[IntegrationPattern]()
						this.integrationPatterns.foreach(e => if(!e.equals(integrationPatterns)) nList = nList ++ List(e))
						this.integrationPatterns = nList
				}
		}


		def getExtraFonctionalProperties : Option[ExtraFonctionalProperty] = {
				extraFonctionalProperties
		}

		def setExtraFonctionalProperties(extraFonctionalProperties : ExtraFonctionalProperty ) {
				extraFonctionalProperties match {
						case l : ExtraFonctionalProperty => this.extraFonctionalProperties = Some(extraFonctionalProperties)
						case _ => this.extraFonctionalProperties = None
				}

		}

		def getProvided : List[PortTypeRef] = {
				provided
		}

		def setProvided(provided : List[PortTypeRef] ) {
				this.provided = provided
		}

		def addProvided(provided : PortTypeRef) {
				this.provided = this.provided ++ List(provided)
		}

		def removeProvided(provided : PortTypeRef) {
				if(this.provided.size != 0 ) {
						var nList = List[PortTypeRef]()
						this.provided.foreach(e => if(!e.equals(provided)) nList = nList ++ List(e))
						this.provided = nList
				}
		}


}
