package org.kevoree;

/**
 * Created by Ecore Model Generator.
 * @author: Gregory NAIN
 * Date: 04 oct. 11 Time: 13:29
 * Meta-Model:NS_URI=http://kevoree/1.0
 */
trait NodeType extends KevoreeContainer with LifeCycleTypeDefinition {
		private var managedPrimitiveTypes : List[AdaptationPrimitiveType] = List[AdaptationPrimitiveType]()


		def getManagedPrimitiveTypes : List[AdaptationPrimitiveType] = {
				managedPrimitiveTypes
		}

		def setManagedPrimitiveTypes(managedPrimitiveTypes : List[AdaptationPrimitiveType] ) {
				this.managedPrimitiveTypes = managedPrimitiveTypes
		}

		def addManagedPrimitiveTypes(managedPrimitiveTypes : AdaptationPrimitiveType) {
				this.managedPrimitiveTypes = this.managedPrimitiveTypes ++ List(managedPrimitiveTypes)
		}

		def removeManagedPrimitiveTypes(managedPrimitiveTypes : AdaptationPrimitiveType) {
				if(this.managedPrimitiveTypes.size != 0 ) {
						var nList = List[AdaptationPrimitiveType]()
						this.managedPrimitiveTypes.foreach(e => if(!e.equals(managedPrimitiveTypes)) nList = nList ++ List(e))
						this.managedPrimitiveTypes = nList
				}
		}


}
