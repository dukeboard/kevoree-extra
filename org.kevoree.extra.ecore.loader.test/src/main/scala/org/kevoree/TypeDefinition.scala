package org.kevoree;

/**
 * Created by Ecore Model Generator.
 * @author: Gregory NAIN
 * Date: 04 oct. 11 Time: 09:47
 * Meta-Model:NS_URI=http://kevoree/1.0
 */
trait TypeDefinition extends KevoreeContainer with NamedElement {
		private var factoryBean : java.lang.String = ""

		private var bean : java.lang.String = ""

		private var deployUnits : List[DeployUnit] = List[DeployUnit]()

		private var dictionaryType : Option[DictionaryType] = None

		private var superTypes : List[TypeDefinition] = List[TypeDefinition]()


		def getFactoryBean : java.lang.String = {
				factoryBean
		}

		def setFactoryBean(factoryBean : java.lang.String) {
				this.factoryBean = factoryBean
		}

		def getBean : java.lang.String = {
				bean
		}

		def setBean(bean : java.lang.String) {
				this.bean = bean
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


		def getDictionaryType : Option[DictionaryType] = {
				dictionaryType
		}

		def setDictionaryType(dictionaryType : DictionaryType ) {
				dictionaryType match {
						case l : DictionaryType => this.dictionaryType = Some(dictionaryType)
						case _ => this.dictionaryType = None
				}

		}

		def getSuperTypes : List[TypeDefinition] = {
				superTypes
		}

		def setSuperTypes(superTypes : List[TypeDefinition] ) {
				this.superTypes = superTypes
		}

		def addSuperTypes(superTypes : TypeDefinition) {
				this.superTypes = this.superTypes ++ List(superTypes)
		}

		def removeSuperTypes(superTypes : TypeDefinition) {
				if(this.superTypes.size != 0 ) {
						var nList = List[TypeDefinition]()
						this.superTypes.foreach(e => if(!e.equals(superTypes)) nList = nList ++ List(e))
						this.superTypes = nList
				}
		}


}
