package org.kevoree;

/**
 * Created by Ecore Model Generator.
 * @author: Gregory NAIN
 * Date: 04 oct. 11 Time: 13:29
 * Meta-Model:NS_URI=http://kevoree/1.0
 */
trait PortType extends KevoreeContainer with TypeDefinition {
		private var synchrone : java.lang.Boolean = false


		def getSynchrone : java.lang.Boolean = {
				synchrone
		}

		def setSynchrone(synchrone : java.lang.Boolean) {
				this.synchrone = synchrone
		}

}
