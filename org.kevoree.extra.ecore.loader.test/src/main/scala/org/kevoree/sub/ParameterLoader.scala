package org.kevoree.sub;

import xml.NodeSeq
import scala.collection.JavaConversions._
import org.kevoree._

trait ParameterLoader{

		def loadParameter(parentId: String, parentNode: NodeSeq, refNameInParent : String) : List[Parameter] = {
				var loadedElements = List[Parameter]()
				var i = 0
				val parameterList = (parentNode \\ refNameInParent)
				parameterList.foreach { xmiElem =>
						val currentElementId = parentId + "/@" + refNameInParent + "." + i
						loadedElements = loadedElements ++ List(loadParameterElement(currentElementId,xmiElem))
						i += 1
				}
				loadedElements
		}

		def loadParameterElement(elementId: String, elementNode: NodeSeq) : Parameter = {
		
				val modelElem = KevoreePackage.createParameter
				ContainerRootLoadContext.map += elementId -> modelElem

				modelElem
		}

		def resolveParameter(parentId: String, parentNode: NodeSeq, refNameInParent : String) {
				var i = 0
				val parameterList = (parentNode \\ refNameInParent)
				parameterList.foreach { xmiElem =>
						val currentElementId = parentId + "/@" + refNameInParent + "." + i
						resolveParameterElement(currentElementId,xmiElem)
						i += 1
				}
		}

		def resolveParameterElement(elementId: String, elementNode: NodeSeq) {

		val modelElem = ContainerRootLoadContext.map(elementId).asInstanceOf[Parameter]

		val nameVal = (elementNode \\ "@name").text
		if(!nameVal.equals("")){
				modelElem.setName(java.lang.String.valueOf(nameVal))
		}


				(elementNode \ "@type").headOption match {
						case Some(head) => {
								head.text.split(" ").foreach {
										xmiRef =>
												ContainerRootLoadContext.map.get(xmiRef) match {
														case Some(s: TypedElement) => modelElem.setType(s)
														case None => System.out.println("TypedElement not found in map ! xmiRef:" + xmiRef)
												}
										}
								}
						case None => //No subtype for this library
				}
		}

}
