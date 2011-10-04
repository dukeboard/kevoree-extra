package org.kevoree;

/**
 * Created by Ecore Model Generator.
 * @author: Gregory NAIN
 * Date: 04 oct. 11 Time: 09:47
 * Meta-Model:NS_URI=http://kevoree/1.0
 */
trait Parameter extends KevoreeContainer with NamedElement {
		private var `type` : Option[TypedElement] = None


		def getType : Option[TypedElement] = {
				`type`
		}

		def setType(`type` : TypedElement ) {
				`type` match {
						case l : TypedElement => this.`type` = Some(`type`)
						case _ => this.`type` = None
				}

		}

}
