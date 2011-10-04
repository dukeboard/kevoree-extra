package org.kevoree.loader.sub;

import xml.NodeSeq
import scala.collection.JavaConversions._
import org.kevoree._
import org.kevoree.loader._

trait NodeNetworkLoader extends NodeLinkLoader {

		def loadNodeNetwork(parentId: String, parentNode: NodeSeq, refNameInParent : String) : List[NodeNetwork] = {
				var loadedElements = List[NodeNetwork]()
				var i = 0
				val nodeNetworkList = (parentNode \\ refNameInParent)
				nodeNetworkList.foreach { xmiElem =>
						val currentElementId = parentId + "/@" + refNameInParent + "." + i
						loadedElements = loadedElements ++ List(loadNodeNetworkElement(currentElementId,xmiElem))
						i += 1
				}
				loadedElements
		}

		def loadNodeNetworkElement(elementId: String, elementNode: NodeSeq) : NodeNetwork = {
		
				val modelElem = KevoreeFactory.createNodeNetwork
				ContainerRootLoadContext.map += elementId -> modelElem

				val link = loadNodeLink(elementId, elementNode, "link")
				modelElem.setLink(link)
				link.foreach{ e => e.eContainer = modelElem }

				modelElem
		}

		def resolveNodeNetwork(parentId: String, parentNode: NodeSeq, refNameInParent : String) {
				var i = 0
				val nodeNetworkList = (parentNode \\ refNameInParent)
				nodeNetworkList.foreach { xmiElem =>
						val currentElementId = parentId + "/@" + refNameInParent + "." + i
						resolveNodeNetworkElement(currentElementId,xmiElem)
						i += 1
				}
		}

		def resolveNodeNetworkElement(elementId: String, elementNode: NodeSeq) {

				val modelElem = ContainerRootLoadContext.map(elementId).asInstanceOf[NodeNetwork]


				resolveNodeLink(elementId, elementNode, "link")

				(elementNode \ "@initBy").headOption match {
						case Some(head) => {
								head.text.split(" ").foreach {
										xmiRef =>
												ContainerRootLoadContext.map.get(xmiRef) match {
														case Some(s: ContainerNode) => modelElem.setInitBy(s)
														case None => System.out.println("ContainerNode not found in map ! xmiRef:" + xmiRef)
												}
										}
								}
						case None => //No subtype for this library
				}
				(elementNode \ "@target").headOption match {
						case Some(head) => {
								head.text.split(" ").foreach {
										xmiRef =>
												ContainerRootLoadContext.map.get(xmiRef) match {
														case Some(s: ContainerNode) => modelElem.setTarget(s)
														case None => System.out.println("ContainerNode not found in map ! xmiRef:" + xmiRef)
												}
										}
								}
						case None => //No subtype for this library
				}
		}

}
