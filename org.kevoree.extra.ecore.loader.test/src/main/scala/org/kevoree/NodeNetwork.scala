package org.kevoree;

/**
 * Created by Ecore Model Generator.
 * @author: Gregory NAIN
 * Date: 04 oct. 11 Time: 13:29
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
		}

		def addLink(link : NodeLink) {
				this.link = this.link ++ List(link)
		}

		def removeLink(link : NodeLink) {
				if(this.link.size != 0 ) {
						var nList = List[NodeLink]()
						this.link.foreach(e => if(!e.equals(link)) nList = nList ++ List(e))
						this.link = nList
				}
		}


		def getInitBy : Option[ContainerNode] = {
				initBy
		}

		def setInitBy(initBy : ContainerNode ) {
				initBy match {
						case l : ContainerNode => this.initBy = Some(initBy)
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
