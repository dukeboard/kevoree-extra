package org.kevoree;

/**
 * Created by Ecore Model Generator.
 * @author: Gregory NAIN
 * Date: 04 oct. 11 Time: 09:49
 * Meta-Model:NS_URI=http://kevoree/1.0
 */
trait Port extends KevoreeContainer {
		private var portTypeRef : PortTypeRef = _


		def getPortTypeRef : PortTypeRef = {
				portTypeRef
		}

		def setPortTypeRef(portTypeRef : PortTypeRef ) {
				this.portTypeRef = portTypeRef
		}

}
