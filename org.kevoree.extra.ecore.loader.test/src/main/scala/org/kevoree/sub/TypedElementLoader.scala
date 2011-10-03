package org.kevoree.sub;

import xml.NodeSeq
import scala.collection.JavaConversions._
import org.kevoree._

trait TypedElementLoader{

		def loadTypedElement(parentId: String, parentNode: NodeSeq, refNameInParent : String) : List[TypedElement] = {
				var loadedElements = List[TypedElement]()
				var i = 0
				val typedElementList = (parentNode \\ refNameInParent)
				typedElementList.foreach { xmiElem =>
						val currentElementId = parentId + "/@" + refNameInParent + "." + i
						loadedElements = loadedElements ++ List(loadTypedElementElement(currentElementId,xmiElem))
						i += 1
				}
				loadedElements
		}

		def loadTypedElementElement(elementId: String, elementNode: NodeSeq) : TypedElement = {
		
				val modelElem = KevoreePackage.createTypedElement
				ContainerRootLoadContext.map += elementId -> modelElem

				modelElem
		}

		def resolveTypedElement(parentId: String, parentNode: NodeSeq, refNameInParent : String) {
				var i = 0
				val typedElementList = (parentNode \\ refNameInParent)
				typedElementList.foreach { xmiElem =>
						val currentElementId = parentId + "/@" + refNameInParent + "." + i
						resolveTypedElementElement(currentElementId,xmiElem)
						i += 1
				}
		}

		def resolveTypedElementElement(elementId: String, elementNode: NodeSeq) {

		val modelElem = ContainerRootLoadContext.map(elementId).asInstanceOf[TypedElement]

		val nameVal = (elementNode \ "@name").text
		if(!nameVal.equals("")){
				modelElem.setName(java.lang.String.valueOf(nameVal))
		}


				(elementNode \ "@genericTypes").headOption match {
						case Some(head) => {
								head.text.split(" ").foreach {
										xmiRef =>
												ContainerRootLoadContext.map.get(xmiRef) match {
														case Some(s: TypedElement) => modelElem.addGenericTypes(s)
														case None => System.out.println("TypedElement not found in map ! xmiRef:" + xmiRef)
												}
										}
								}
						case None => //No subtype for this library
				}
		}

}
