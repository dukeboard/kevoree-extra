package org.kevoree;

/**
 * Created by Ecore Model Generator.
 * @author: Gregory NAIN
 * Date: 04 oct. 11 Time: 09:49
 * Meta-Model:NS_URI=http://kevoree/1.0
 */
trait CompositeType extends KevoreeContainer with ComponentType {
		private var childs : List[ComponentType] = List[ComponentType]()

		private var wires : List[Wire] = List[Wire]()


		def getChilds : List[ComponentType] = {
				childs
		}

		def setChilds(childs : List[ComponentType] ) {
				this.childs = childs
		}

		def addChilds(childs : ComponentType) {
				this.childs = this.childs ++ List(childs)
		}

		def removeChilds(childs : ComponentType) {
				if(this.childs.size != 0 ) {
						var nList = List[ComponentType]()
						this.childs.foreach(e => if(!e.equals(childs)) nList = nList ++ List(e))
						this.childs = nList
				}
		}


		def getWires : List[Wire] = {
				wires
		}

		def setWires(wires : List[Wire] ) {
				this.wires = wires
		}

		def addWires(wires : Wire) {
				this.wires = this.wires ++ List(wires)
		}

		def removeWires(wires : Wire) {
				if(this.wires.size != 0 ) {
						var nList = List[Wire]()
						this.wires.foreach(e => if(!e.equals(wires)) nList = nList ++ List(e))
						this.wires = nList
				}
		}


}
