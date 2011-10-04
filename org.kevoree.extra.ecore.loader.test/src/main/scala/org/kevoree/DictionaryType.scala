package org.kevoree;

/**
 * Created by Ecore Model Generator.
 * @author: Gregory NAIN
 * Date: 04 oct. 11 Time: 20:58
 * Meta-Model:NS_URI=http://kevoree/1.0
 */
trait DictionaryType extends KevoreeContainer {
		private var attributes : List[DictionaryAttribute] = List[DictionaryAttribute]()

		private var defaultValues : List[DictionaryValue] = List[DictionaryValue]()


		def getAttributes : List[DictionaryAttribute] = {
				attributes
		}

		def setAttributes(attributes : List[DictionaryAttribute] ) {
				this.attributes = attributes
				attributes.foreach{e=>e.eContainer = this}

		}

		def addAttributes(attributes : DictionaryAttribute) {
				attributes.eContainer = this
				this.attributes = this.attributes ++ List(attributes)
		}

		def addAllAttributes(attributes : List[DictionaryAttribute]) {
				attributes.foreach{ elem => addAttributes(elem)}
		}

		def removeAttributes(attributes : DictionaryAttribute) {
				if(this.attributes.size != 0 ) {
						var nList = List[DictionaryAttribute]()
						this.attributes.foreach(e => if(!e.equals(attributes)) nList = nList ++ List(e))
						this.attributes = nList
				}
		}

		def removeAllAttributes() {
				this.attributes = List[DictionaryAttribute]()
		}


		def getDefaultValues : List[DictionaryValue] = {
				defaultValues
		}

		def setDefaultValues(defaultValues : List[DictionaryValue] ) {
				this.defaultValues = defaultValues
				defaultValues.foreach{e=>e.eContainer = this}

		}

		def addDefaultValues(defaultValues : DictionaryValue) {
				defaultValues.eContainer = this
				this.defaultValues = this.defaultValues ++ List(defaultValues)
		}

		def addAllDefaultValues(defaultValues : List[DictionaryValue]) {
				defaultValues.foreach{ elem => addDefaultValues(elem)}
		}

		def removeDefaultValues(defaultValues : DictionaryValue) {
				if(this.defaultValues.size != 0 ) {
						var nList = List[DictionaryValue]()
						this.defaultValues.foreach(e => if(!e.equals(defaultValues)) nList = nList ++ List(e))
						this.defaultValues = nList
				}
		}

		def removeAllDefaultValues() {
				this.defaultValues = List[DictionaryValue]()
		}


}
