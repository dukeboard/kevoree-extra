package org.kevoree;

/**
 * Created by Ecore Model Generator.
 * @author: Gregory NAIN
 * Date: 04 oct. 11 Time: 14:00
 * Meta-Model:NS_URI=http://kevoree/1.0
 */
trait Instance extends KevoreeContainer with NamedElement {
		private var metaData : java.lang.String = ""

		private var typeDefinition : TypeDefinition = _

		private var dictionary : Option[Dictionary] = None


		def getMetaData : java.lang.String = {
				metaData
		}

		def setMetaData(metaData : java.lang.String) {
				this.metaData = metaData
		}

		def getTypeDefinition : TypeDefinition = {
				typeDefinition
		}

		def setTypeDefinition(typeDefinition : TypeDefinition ) {
				this.typeDefinition = typeDefinition
		}

		def getDictionary : Option[Dictionary] = {
				dictionary
		}

		def setDictionary(dictionary : Dictionary ) {
				dictionary match {
						case l : Dictionary => this.dictionary = Some(dictionary)
						case _ => this.dictionary = None
				}

		}

}
