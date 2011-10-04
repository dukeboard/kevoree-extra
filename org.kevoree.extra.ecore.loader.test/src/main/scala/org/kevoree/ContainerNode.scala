package org.kevoree;

/**
 * Created by Ecore Model Generator.
 * @author: Gregory NAIN
 * Date: 04 oct. 11 Time: 14:00
 * Meta-Model:NS_URI=http://kevoree/1.0
 */
trait ContainerNode extends KevoreeContainer with NamedElement with Instance {
		private var components : List[ComponentInstance] = List[ComponentInstance]()

		private var hosts : List[ContainerNode] = List[ContainerNode]()


		def getComponents : List[ComponentInstance] = {
				components
		}

		def setComponents(components : List[ComponentInstance] ) {
				this.components = components
		}

		def addComponents(components : ComponentInstance) {
				this.components = this.components ++ List(components)
		}

		def removeComponents(components : ComponentInstance) {
				if(this.components.size != 0 ) {
						var nList = List[ComponentInstance]()
						this.components.foreach(e => if(!e.equals(components)) nList = nList ++ List(e))
						this.components = nList
				}
		}


		def getHosts : List[ContainerNode] = {
				hosts
		}

		def setHosts(hosts : List[ContainerNode] ) {
				this.hosts = hosts
		}

		def addHosts(hosts : ContainerNode) {
				this.hosts = this.hosts ++ List(hosts)
		}

		def removeHosts(hosts : ContainerNode) {
				if(this.hosts.size != 0 ) {
						var nList = List[ContainerNode]()
						this.hosts.foreach(e => if(!e.equals(hosts)) nList = nList ++ List(e))
						this.hosts = nList
				}
		}


}
