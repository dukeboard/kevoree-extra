package org.kevoree;

/**
 * Created by Ecore Model Generator.
 * @author: Gregory NAIN
 * Date: 04 oct. 11 Time: 09:49
 * Meta-Model:NS_URI=http://kevoree/1.0
 */
trait MessagePortType extends KevoreeContainer with PortType {
		private var filters : List[TypedElement] = List[TypedElement]()


		def getFilters : List[TypedElement] = {
				filters
		}

		def setFilters(filters : List[TypedElement] ) {
				this.filters = filters
		}

		def addFilters(filters : TypedElement) {
				this.filters = this.filters ++ List(filters)
		}

		def removeFilters(filters : TypedElement) {
				if(this.filters.size != 0 ) {
						var nList = List[TypedElement]()
						this.filters.foreach(e => if(!e.equals(filters)) nList = nList ++ List(e))
						this.filters = nList
				}
		}


}
