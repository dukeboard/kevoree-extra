package org.kevoree;

/**
 * Created by Ecore Model Generator.
 * @author: Gregory NAIN
 * Date: 04 oct. 11 Time: 13:29
 * Meta-Model:NS_URI=http://kevoree/1.0
 */
trait TypedElement extends KevoreeContainer with NamedElement {
		private var genericTypes : List[TypedElement] = List[TypedElement]()


		def getGenericTypes : List[TypedElement] = {
				genericTypes
		}

		def setGenericTypes(genericTypes : List[TypedElement] ) {
				this.genericTypes = genericTypes
		}

		def addGenericTypes(genericTypes : TypedElement) {
				this.genericTypes = this.genericTypes ++ List(genericTypes)
		}

		def removeGenericTypes(genericTypes : TypedElement) {
				if(this.genericTypes.size != 0 ) {
						var nList = List[TypedElement]()
						this.genericTypes.foreach(e => if(!e.equals(genericTypes)) nList = nList ++ List(e))
						this.genericTypes = nList
				}
		}


}
