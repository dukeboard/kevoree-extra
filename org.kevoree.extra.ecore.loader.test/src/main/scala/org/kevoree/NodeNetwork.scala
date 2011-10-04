package org.kevoree;

/**
 * Created by Ecore Model Generator.
 * @author: Gregory NAIN
 * Date: 04 oct. 11 Time: 20:58
 * Meta-Model:NS_URI=http://kevoree/1.0
 */
trait NodeNetwork extends KevoreeContainer {
		private var link : List[NodeLink] = List[NodeLink]()

		private var initBy : Option[ContainerNode] = None

		private var target : ContainerNode = _


		def getLink : List[NodeLink] = {
				link
		}

		def setLink(link : List[NodeLink] ) {
				this.link = link
				link.foreach{e=>e.eContainer = this}

		}

		def addLink(link : NodeLink) {
				link.eContainer = this
				this.link = this.link ++ List(link)
		}

		def addAllLink(link : List[NodeLink]) {
				link.foreach{ elem => addLink(elem)}
		}

		def removeLink(link : NodeLink) {
				if(this.link.size != 0 ) {
						var nList = List[NodeLink]()
						this.link.foreach(e => if(!e.equals(link)) nList = nList ++ List(e))
						this.link = nList
				}
		}

		def removeAllLink() {
				this.link = List[NodeLink]()
		}


		def getInitBy : Option[ContainerNode] = {
				initBy
		}

		def setInitBy(initBy : ContainerNode ) {
				initBy match {
						case l : ContainerNode => {
								this.initBy = Some(initBy)
						}
						case _ => this.initBy = None
				}

		}

		def getTarget : ContainerNode = {
				target
		}

		def setTarget(target : ContainerNode ) {
				this.target = target

		}

}
