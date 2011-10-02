package org.kevoree.sub;

import xml.NodeSeq
import scala.collection.JavaConversions._
import org.kevoree._


trait TypedElementLoader
		extends  {

		def loadTypedElements(parentId: String, parentNode: NodeSeq): Int = {
				val dataTypesList = (parentNode \\ "dataTypes")
				var i = 0
				dataTypesList.foreach { xmiElem =>
						val modelElem = KevoreePackage.createTypedElement
						ContainerRootLoadContext.map += parentId + "/@" + xmiElem.label + "." + i -> modelElem
						i += 1
				}
				i
		}
		def resolveTypedElements(parentId: String, parentNode: NodeSeq, owner: ContainerRoot) {
				val dataTypesList = (parentNode \\ "dataTypes")
				var i = 0
				dataTypesList.foreach { xmiElem =>
						val modelElem = ContainerRootLoadContext.map(parentId + "/@" + xmiElem.label + "." + i).asInstanceOf[TypedElement]
						modelElem.eContainer = owner
						modelElem.setName(java.lang.String.valueOf((xmiElem \ "@name").text))

						(xmiElem \ "@genericTypes").headOption match {
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

						owner.addDataTypes(modelElem)
						i += 1
				}
		}
}
