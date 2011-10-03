package org.kevoree.sub;

import xml.NodeSeq
import scala.collection.JavaConversions._
import org.kevoree._

trait TypeLibraryLoader{

		def loadTypeLibrary(parentId: String, parentNode: NodeSeq, refNameInParent : String) : List[TypeLibrary] = {
				var loadedElements = List[TypeLibrary]()
				var i = 0
				val typeLibraryList = (parentNode \\ refNameInParent)
				typeLibraryList.foreach { xmiElem =>
						val currentElementId = parentId + "/@" + refNameInParent + "." + i
						loadedElements = loadedElements ++ List(loadTypeLibraryElement(currentElementId,xmiElem))
						i += 1
				}
				loadedElements
		}

		def loadTypeLibraryElement(elementId: String, elementNode: NodeSeq) : TypeLibrary = {
		
				val modelElem = KevoreePackage.createTypeLibrary
				ContainerRootLoadContext.map += elementId -> modelElem

				modelElem
		}

		def resolveTypeLibrary(parentId: String, parentNode: NodeSeq, refNameInParent : String) {
				var i = 0
				val typeLibraryList = (parentNode \\ refNameInParent)
				typeLibraryList.foreach { xmiElem =>
						val currentElementId = parentId + "/@" + refNameInParent + "." + i
						resolveTypeLibraryElement(currentElementId,xmiElem)
						i += 1
				}
		}

		def resolveTypeLibraryElement(elementId: String, elementNode: NodeSeq) {

		val modelElem = ContainerRootLoadContext.map(elementId).asInstanceOf[TypeLibrary]

		val nameVal = (elementNode \\ "@name").text
		if(!nameVal.equals("")){
				modelElem.setName(java.lang.String.valueOf(nameVal))
		}


				(elementNode \ "@subTypes").headOption match {
						case Some(head) => {
								head.text.split(" ").foreach {
										xmiRef =>
												ContainerRootLoadContext.map.get(xmiRef) match {
														case Some(s: TypeDefinition) => modelElem.addSubTypes(s)
														case None => System.out.println("TypeDefinition not found in map ! xmiRef:" + xmiRef)
												}
										}
								}
						case None => //No subtype for this library
				}
		}

}
