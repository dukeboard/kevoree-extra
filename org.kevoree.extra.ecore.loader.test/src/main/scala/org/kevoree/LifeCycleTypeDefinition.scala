package org.kevoree;

/**
 * Created by Ecore Model Generator.
 * @author: Gregory NAIN
 * Date: 04 oct. 11 Time: 09:49
 * Meta-Model:NS_URI=http://kevoree/1.0
 */
trait LifeCycleTypeDefinition extends KevoreeContainer with TypeDefinition {
		private var startMethod : java.lang.String = ""

		private var stopMethod : java.lang.String = ""

		private var updateMethod : java.lang.String = ""


		def getStartMethod : java.lang.String = {
				startMethod
		}

		def setStartMethod(startMethod : java.lang.String) {
				this.startMethod = startMethod
		}

		def getStopMethod : java.lang.String = {
				stopMethod
		}

		def setStopMethod(stopMethod : java.lang.String) {
				this.stopMethod = stopMethod
		}

		def getUpdateMethod : java.lang.String = {
				updateMethod
		}

		def setUpdateMethod(updateMethod : java.lang.String) {
				this.updateMethod = updateMethod
		}

}
