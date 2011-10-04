package org.kevoree;

/**
 * Created by Ecore Model Generator.
 * @author: Gregory NAIN
 * Date: 04 oct. 11 Time: 09:49
 * Meta-Model:NS_URI=http://kevoree/1.0
 */
trait Wire extends KevoreeContainer {
		private var ports : List[PortTypeRef] = List[PortTypeRef]()


		def getPorts : List[PortTypeRef] = {
				ports
		}

		def setPorts(ports : List[PortTypeRef] ) {
				this.ports = ports
		}

		def addPorts(ports : PortTypeRef) {
				this.ports = this.ports ++ List(ports)
		}

		def removePorts(ports : PortTypeRef) {
				if(this.ports.size == 2) {
						throw new UnsupportedOperationException("The list of ports must contain at least 2 element. Connot remove sizeof(ports)="+this.ports.size)
				} else {
						var nList = List[PortTypeRef]()
						this.ports.foreach(e => if(!e.equals(ports)) nList = nList ++ List(e))
						this.ports = nList
				}
		}


}
