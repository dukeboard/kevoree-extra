package org.kevoree;

/**
 * Created by Ecore Model Generator.
 * @author: Gregory NAIN
 * Date: 04 oct. 11 Time: 20:58
 * Meta-Model:NS_URI=http://kevoree/1.0
 */
trait NetworkProperty extends KevoreeContainer with NamedElement {
		private var value : java.lang.String = ""

		private var lastCheck : java.lang.String = ""


		def getValue : java.lang.String = {
				value
		}

		def setValue(value : java.lang.String) {
				this.value = value
		}

		def getLastCheck : java.lang.String = {
				lastCheck
		}

		def setLastCheck(lastCheck : java.lang.String) {
				this.lastCheck = lastCheck
		}

}
