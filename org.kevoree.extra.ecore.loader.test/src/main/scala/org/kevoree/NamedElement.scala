package org.kevoree;

/**
 * Created by Ecore Model Generator.
 * @author: Gregory NAIN
 * Date: 04 oct. 11 Time: 14:00
 * Meta-Model:NS_URI=http://kevoree/1.0
 */
trait NamedElement extends KevoreeContainer {
		private var name : java.lang.String = ""


		def getName : java.lang.String = {
				name
		}

		def setName(name : java.lang.String) {
				this.name = name
		}

}
