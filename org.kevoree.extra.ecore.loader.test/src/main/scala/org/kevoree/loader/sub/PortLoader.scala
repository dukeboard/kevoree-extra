package org.kevoree.loader.sub;

import xml.NodeSeq
import scala.collection.JavaConversions._
import org.kevoree._
import org.kevoree.loader._

trait PortLoader{

		def loadPort(parentId: String, parentNode: NodeSeq, refNameInParent : String) : List[Port] = {
				var loadedElements = List[Port]()
				var i = 0
				val portList = (parentNode \\ refNameInParent)
				portList.foreach { xmiElem =>
						val currentElementId = parentId + "/@" + refNameInParent + "." + i
						loadedElements = loadedElements ++ List(loadPortElement(currentElementId,xmiElem))
						i += 1
				}
				loadedElements
		}

		def loadPortElement(elementId: String, elementNode: NodeSeq) : Port = {
		
				val modelElem = KevoreeFactory.createPort
				ContainerRootLoadContext.map += elementId -> modelElem

				modelElem
		}

		def resolvePort(parentId: String, parentNode: NodeSeq, refNameInParent : String) {
				var i = 0
				val portList = (parentNode \\ refNameInParent)
				portList.foreach { xmiElem =>
						val currentElementId = parentId + "/@" + refNameInParent + "." + i
						resolvePortElement(currentElementId,xmiElem)
						i += 1
				}
		}

		def resolvePortElement(elementId: String, elementNode: NodeSeq) {

				val modelElem = ContainerRootLoadContext.map(elementId).asInstanceOf[Port]


				(elementNode \ "@portTypeRef").headOption match {
						case Some(head) => {
								head.text.split(" ").foreach {
										xmiRef =>
												ContainerRootLoadContext.map.get(xmiRef) match {
														case Some(s: PortTypeRef) => modelElem.setPortTypeRef(s)
														case None => System.out.println("PortTypeRef not found in map ! xmiRef:" + xmiRef)
												}
										}
								}
						case None => //No subtype for this library
				}
		}

}
