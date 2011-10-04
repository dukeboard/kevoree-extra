package org.kevoree;

/**
 * Created by Ecore Model Generator.
 * @author: Gregory NAIN
 * Date: 04 oct. 11 Time: 14:00
 * Meta-Model:NS_URI=http://kevoree/1.0
 */
trait NodeLink extends KevoreeContainer {
		private var networkType : java.lang.String = ""

		private var estimatedRate : java.lang.Integer = 0

		private var lastCheck : java.lang.String = ""

		private var networkProperties : List[NetworkProperty] = List[NetworkProperty]()


		def getNetworkType : java.lang.String = {
				networkType
		}

		def setNetworkType(networkType : java.lang.String) {
				this.networkType = networkType
		}

		def getEstimatedRate : java.lang.Integer = {
				estimatedRate
		}

		def setEstimatedRate(estimatedRate : java.lang.Integer) {
				this.estimatedRate = estimatedRate
		}

		def getLastCheck : java.lang.String = {
				lastCheck
		}

		def setLastCheck(lastCheck : java.lang.String) {
				this.lastCheck = lastCheck
		}

		def getNetworkProperties : List[NetworkProperty] = {
				networkProperties
		}

		def setNetworkProperties(networkProperties : List[NetworkProperty] ) {
				this.networkProperties = networkProperties
		}

		def addNetworkProperties(networkProperties : NetworkProperty) {
				this.networkProperties = this.networkProperties ++ List(networkProperties)
		}

		def removeNetworkProperties(networkProperties : NetworkProperty) {
				if(this.networkProperties.size != 0 ) {
						var nList = List[NetworkProperty]()
						this.networkProperties.foreach(e => if(!e.equals(networkProperties)) nList = nList ++ List(e))
						this.networkProperties = nList
				}
		}


}
