package org.kevoree;

/**
 * Created by Ecore Model Generator.
 * @author: Gregory NAIN
 * Date: 04 oct. 11 Time: 14:00
 * Meta-Model:NS_URI=http://kevoree/1.0
 */
trait Repository extends KevoreeContainer with NamedElement {
		private var url : java.lang.String = ""

		private var units : List[DeployUnit] = List[DeployUnit]()


		def getUrl : java.lang.String = {
				url
		}

		def setUrl(url : java.lang.String) {
				this.url = url
		}

		def getUnits : List[DeployUnit] = {
				units
		}

		def setUnits(units : List[DeployUnit] ) {
				this.units = units
		}

		def addUnits(units : DeployUnit) {
				this.units = this.units ++ List(units)
		}

		def removeUnits(units : DeployUnit) {
				if(this.units.size != 0 ) {
						var nList = List[DeployUnit]()
						this.units.foreach(e => if(!e.equals(units)) nList = nList ++ List(e))
						this.units = nList
				}
		}


}
