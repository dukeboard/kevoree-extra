package org.kevoree;

/**
 * Created by Ecore Model Generator.
 * @author: Gregory NAIN
 * Date: 04 oct. 11 Time: 09:47
 * Meta-Model:NS_URI=http://kevoree/1.0
 */
trait ServicePortType extends KevoreeContainer with PortType {
		private var interface : java.lang.String = ""

		private var operations : List[Operation] = List[Operation]()


		def getInterface : java.lang.String = {
				interface
		}

		def setInterface(interface : java.lang.String) {
				this.interface = interface
		}

		def getOperations : List[Operation] = {
				operations
		}

		def setOperations(operations : List[Operation] ) {
				this.operations = operations
		}

		def addOperations(operations : Operation) {
				this.operations = this.operations ++ List(operations)
		}

		def removeOperations(operations : Operation) {
				if(this.operations.size != 0 ) {
						var nList = List[Operation]()
						this.operations.foreach(e => if(!e.equals(operations)) nList = nList ++ List(e))
						this.operations = nList
				}
		}


}
