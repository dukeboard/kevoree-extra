package org.kevoree;

/**
 * Created by Ecore Model Generator.
 * @author: Gregory NAIN
 * Date: 04 oct. 11 Time: 13:29
 * Meta-Model:NS_URI=http://kevoree/1.0
 */
trait Operation extends KevoreeContainer with NamedElement {
		private var parameters : List[Parameter] = List[Parameter]()

		private var returnType : Option[TypedElement] = None


		def getParameters : List[Parameter] = {
				parameters
		}

		def setParameters(parameters : List[Parameter] ) {
				this.parameters = parameters
		}

		def addParameters(parameters : Parameter) {
				this.parameters = this.parameters ++ List(parameters)
		}

		def removeParameters(parameters : Parameter) {
				if(this.parameters.size != 0 ) {
						var nList = List[Parameter]()
						this.parameters.foreach(e => if(!e.equals(parameters)) nList = nList ++ List(e))
						this.parameters = nList
				}
		}


		def getReturnType : Option[TypedElement] = {
				returnType
		}

		def setReturnType(returnType : TypedElement ) {
				returnType match {
						case l : TypedElement => this.returnType = Some(returnType)
						case _ => this.returnType = None
				}

		}

}
