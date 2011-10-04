package org.kevoree;

/**
 * Created by Ecore Model Generator.
 * @author: Gregory NAIN
 * Date: 04 oct. 11 Time: 09:49
 * Meta-Model:NS_URI=http://kevoree/1.0
 */
trait Namespace extends KevoreeContainer with NamedElement {
		private var childs : List[Namespace] = List[Namespace]()

		private var parent : Option[Namespace] = None


		def getChilds : List[Namespace] = {
				childs
		}

		def setChilds(childs : List[Namespace] ) {
				this.childs = childs
		}

		def addChilds(childs : Namespace) {
				this.childs = this.childs ++ List(childs)
		}

		def removeChilds(childs : Namespace) {
				if(this.childs.size != 0 ) {
						var nList = List[Namespace]()
						this.childs.foreach(e => if(!e.equals(childs)) nList = nList ++ List(e))
						this.childs = nList
				}
		}


		def getParent : Option[Namespace] = {
				parent
		}

		def setParent(parent : Namespace ) {
				parent match {
						case l : Namespace => this.parent = Some(parent)
						case _ => this.parent = None
				}

		}

}
