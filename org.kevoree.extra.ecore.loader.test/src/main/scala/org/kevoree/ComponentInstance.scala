package org.kevoree;

/**
 * Created by Ecore Model Generator.
 * @author: Gregory NAIN
 * Date: 04 oct. 11 Time: 13:29
 * Meta-Model:NS_URI=http://kevoree/1.0
 */
trait ComponentInstance extends KevoreeContainer with NamedElement with Instance {
		private var provided : List[Port] = List[Port]()

		private var required : List[Port] = List[Port]()

		private var namespace : Option[Namespace] = None


		def getProvided : List[Port] = {
				provided
		}

		def setProvided(provided : List[Port] ) {
				this.provided = provided
		}

		def addProvided(provided : Port) {
				this.provided = this.provided ++ List(provided)
		}

		def removeProvided(provided : Port) {
				if(this.provided.size != 0 ) {
						var nList = List[Port]()
						this.provided.foreach(e => if(!e.equals(provided)) nList = nList ++ List(e))
						this.provided = nList
				}
		}


		def getRequired : List[Port] = {
				required
		}

		def setRequired(required : List[Port] ) {
				this.required = required
		}

		def addRequired(required : Port) {
				this.required = this.required ++ List(required)
		}

		def removeRequired(required : Port) {
				if(this.required.size != 0 ) {
						var nList = List[Port]()
						this.required.foreach(e => if(!e.equals(required)) nList = nList ++ List(e))
						this.required = nList
				}
		}


		def getNamespace : Option[Namespace] = {
				namespace
		}

		def setNamespace(namespace : Namespace ) {
				namespace match {
						case l : Namespace => this.namespace = Some(namespace)
						case _ => this.namespace = None
				}

		}

}
