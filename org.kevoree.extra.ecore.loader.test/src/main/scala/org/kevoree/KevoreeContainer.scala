package org.kevoree;


/**
 * Created by Ecore Model Generator.
 * @author: Gregory NAIN
 * Date: 04 oct. 11 Time: 13:29
 * Meta-Model:NS_URI=http://kevoree/1.0
 */
trait KevoreeContainer {

	 var eContainer : KevoreeContainer = null


		def setEContainer( container : KevoreeContainer) {
				this.eContainer = container
		}
}
