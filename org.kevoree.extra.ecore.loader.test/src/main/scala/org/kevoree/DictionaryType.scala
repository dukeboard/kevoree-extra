package org.kevoree;

/**
 * Created by Ecore Model Generator.
 * @author: Gregory NAIN
 * Date: 04 oct. 11 Time: 09:49
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
		}

		def addAttributes(attributes : DictionaryAttribute) {
				this.attributes = this.attributes ++ List(attributes)
		}

		def removeAttributes(attributes : DictionaryAttribute) {
				if(this.attributes.size != 0 ) {
						var nList = List[DictionaryAttribute]()
						this.attributes.foreach(e => if(!e.equals(attributes)) nList = nList ++ List(e))
						this.attributes = nList
				}
		}


		def getDefaultValues : List[DictionaryValue] = {
				defaultValues
		}

		def setDefaultValues(defaultValues : List[DictionaryValue] ) {
				this.defaultValues = defaultValues
		}

		def addDefaultValues(defaultValues : DictionaryValue) {
				this.defaultValues = this.defaultValues ++ List(defaultValues)
		}

		def removeDefaultValues(defaultValues : DictionaryValue) {
				if(this.defaultValues.size != 0 ) {
						var nList = List[DictionaryValue]()
						this.defaultValues.foreach(e => if(!e.equals(defaultValues)) nList = nList ++ List(e))
						this.defaultValues = nList
				}
		}


}
