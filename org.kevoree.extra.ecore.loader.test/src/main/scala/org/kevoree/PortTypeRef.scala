package org.kevoree;

/**
 * Created by Ecore Model Generator.
 * @author: Gregory NAIN
 * Date: 04 oct. 11 Time: 09:47
 * Meta-Model:NS_URI=http://kevoree/1.0
 */
trait PortTypeRef extends KevoreeContainer with NamedElement {
		private var optional : java.lang.Boolean = false

		private var noDependency : java.lang.Boolean = false

		private var ref : PortType = _

		private var mappings : List[PortTypeMapping] = List[PortTypeMapping]()


		def getOptional : java.lang.Boolean = {
				optional
		}

		def setOptional(optional : java.lang.Boolean) {
				this.optional = optional
		}

		def getNoDependency : java.lang.Boolean = {
				noDependency
		}

		def setNoDependency(noDependency : java.lang.Boolean) {
				this.noDependency = noDependency
		}

		def getRef : PortType = {
				ref
		}

		def setRef(ref : PortType ) {
				this.ref = ref
		}

		def getMappings : List[PortTypeMapping] = {
				mappings
		}

		def setMappings(mappings : List[PortTypeMapping] ) {
				this.mappings = mappings
		}

		def addMappings(mappings : PortTypeMapping) {
				this.mappings = this.mappings ++ List(mappings)
		}

		def removeMappings(mappings : PortTypeMapping) {
				if(this.mappings.size != 0 ) {
						var nList = List[PortTypeMapping]()
						this.mappings.foreach(e => if(!e.equals(mappings)) nList = nList ++ List(e))
						this.mappings = nList
				}
		}


}
