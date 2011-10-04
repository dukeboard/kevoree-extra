package org.kevoree;

/**
 * Created by Ecore Model Generator.
 * @author: Gregory NAIN
 * Date: 04 oct. 11 Time: 13:29
 * Meta-Model:NS_URI=http://kevoree/1.0
 */
trait TypeLibrary extends KevoreeContainer with NamedElement {
		private var subTypes : List[TypeDefinition] = List[TypeDefinition]()


		def getSubTypes : List[TypeDefinition] = {
				subTypes
		}

		def setSubTypes(subTypes : List[TypeDefinition] ) {
				this.subTypes = subTypes
		}

		def addSubTypes(subTypes : TypeDefinition) {
				this.subTypes = this.subTypes ++ List(subTypes)
		}

		def removeSubTypes(subTypes : TypeDefinition) {
				if(this.subTypes.size != 0 ) {
						var nList = List[TypeDefinition]()
						this.subTypes.foreach(e => if(!e.equals(subTypes)) nList = nList ++ List(e))
						this.subTypes = nList
				}
		}


}
