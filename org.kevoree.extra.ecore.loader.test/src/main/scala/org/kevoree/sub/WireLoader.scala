package org.kevoree.sub;

import xml.NodeSeq
import scala.collection.JavaConversions._
import org.kevoree._

trait WireLoader{

		def loadWire(parentId: String, parentNode: NodeSeq, refNameInParent : String) : List[Wire] = {
				var loadedElements = List[Wire]()
				var i = 0
				val wireList = (parentNode \\ refNameInParent)
				wireList.foreach { xmiElem =>
						val currentElementId = parentId + "/@" + refNameInParent + "." + i
						loadedElements = loadedElements ++ List(loadWireElement(currentElementId,xmiElem))
						i += 1
				}
				loadedElements
		}

		def loadWireElement(elementId: String, elementNode: NodeSeq) : Wire = {
		
				val modelElem = KevoreePackage.createWire
				ContainerRootLoadContext.map += elementId -> modelElem

				modelElem
		}

		def resolveWire(parentId: String, parentNode: NodeSeq, refNameInParent : String) {
				var i = 0
				val wireList = (parentNode \\ refNameInParent)
				wireList.foreach { xmiElem =>
						val currentElementId = parentId + "/@" + refNameInParent + "." + i
						resolveWireElement(currentElementId,xmiElem)
						i += 1
				}
		}

		def resolveWireElement(elementId: String, elementNode: NodeSeq) {

		val modelElem = ContainerRootLoadContext.map(elementId).asInstanceOf[Wire]


				(elementNode \ "@ports").headOption match {
						case Some(head) => {
								head.text.split(" ").foreach {
										xmiRef =>
												ContainerRootLoadContext.map.get(xmiRef) match {
														case Some(s: PortTypeRef) => modelElem.addPorts(s)
														case None => System.out.println("PortTypeRef not found in map ! xmiRef:" + xmiRef)
												}
										}
								}
						case None => //No subtype for this library
				}
		}

}
