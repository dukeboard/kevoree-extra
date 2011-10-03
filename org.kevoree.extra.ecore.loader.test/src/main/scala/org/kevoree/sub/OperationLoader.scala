package org.kevoree.sub;

import xml.NodeSeq
import scala.collection.JavaConversions._
import org.kevoree._

trait OperationLoader extends ParameterLoader {

		def loadOperation(parentId: String, parentNode: NodeSeq, refNameInParent : String) : List[Operation] = {
				var loadedElements = List[Operation]()
				var i = 0
				val operationList = (parentNode \\ refNameInParent)
				operationList.foreach { xmiElem =>
						val currentElementId = parentId + "/@" + refNameInParent + "." + i
						loadedElements = loadedElements ++ List(loadOperationElement(currentElementId,xmiElem))
						i += 1
				}
				loadedElements
		}

		def loadOperationElement(elementId: String, elementNode: NodeSeq) : Operation = {
		
				val modelElem = KevoreePackage.createOperation
				ContainerRootLoadContext.map += elementId -> modelElem

				val parameters = loadParameter(elementId, elementNode, "parameters")
				modelElem.setParameters(parameters)
				parameters.foreach{ e => e.eContainer = modelElem }

				modelElem
		}

		def resolveOperation(parentId: String, parentNode: NodeSeq, refNameInParent : String) {
				var i = 0
				val operationList = (parentNode \\ refNameInParent)
				operationList.foreach { xmiElem =>
						val currentElementId = parentId + "/@" + refNameInParent + "." + i
						resolveOperationElement(currentElementId,xmiElem)
						i += 1
				}
		}

		def resolveOperationElement(elementId: String, elementNode: NodeSeq) {

		val modelElem = ContainerRootLoadContext.map(elementId).asInstanceOf[Operation]

		val nameVal = (elementNode \ "@name").text
		if(!nameVal.equals("")){
				modelElem.setName(java.lang.String.valueOf(nameVal))
		}


				(elementNode \ "@parameters").headOption match {
						case Some(head) => {
								head.text.split(" ").foreach {
										xmiRef =>
												ContainerRootLoadContext.map.get(xmiRef) match {
														case Some(s: Parameter) => modelElem.addParameters(s)
														case None => System.out.println("Parameter not found in map ! xmiRef:" + xmiRef)
												}
										}
								}
						case None => //No subtype for this library
				}
				(elementNode \ "@returnType").headOption match {
						case Some(head) => {
								head.text.split(" ").foreach {
										xmiRef =>
												ContainerRootLoadContext.map.get(xmiRef) match {
														case Some(s: TypedElement) => modelElem.setReturnType(s)
														case None => System.out.println("TypedElement not found in map ! xmiRef:" + xmiRef)
												}
										}
								}
						case None => //No subtype for this library
				}
		}

}
