package org.kevoree;

/**
 * Created by Ecore Model Generator.
 * @author: Gregory NAIN
 * Date: 04 oct. 11 Time: 14:00
 * Meta-Model:NS_URI=http://kevoree/1.0
 */
trait DictionaryAttribute extends KevoreeContainer with TypedElement {
		private var optional : java.lang.Boolean = false

		private var state : java.lang.Boolean = false

		private var datatype : java.lang.String = ""


		def getOptional : java.lang.Boolean = {
				optional
		}

		def setOptional(optional : java.lang.Boolean) {
				this.optional = optional
		}

		def getState : java.lang.Boolean = {
				state
		}

		def setState(state : java.lang.Boolean) {
				this.state = state
		}

		def getDatatype : java.lang.String = {
				datatype
		}

		def setDatatype(datatype : java.lang.String) {
				this.datatype = datatype
		}

}
