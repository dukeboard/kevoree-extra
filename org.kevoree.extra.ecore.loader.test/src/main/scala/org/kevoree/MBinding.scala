package org.kevoree;

/**
 * Created by Ecore Model Generator.
 * @author: Gregory NAIN
 * Date: 04 oct. 11 Time: 09:47
 * Meta-Model:NS_URI=http://kevoree/1.0
 */
trait MBinding extends KevoreeContainer {
		private var port : Port = _

		private var hub : Channel = _


		def getPort : Port = {
				port
		}

		def setPort(port : Port ) {
				this.port = port
		}

		def getHub : Channel = {
				hub
		}

		def setHub(hub : Channel ) {
				this.hub = hub
		}

}
