package org.kevoree.sub;

import xml.NodeSeq
import scala.collection.JavaConversions._
import org.kevoree._

trait MBindingLoader{

		def loadMBinding(parentId: String, parentNode: NodeSeq, refNameInParent : String) : List[MBinding] = {
				var loadedElements = List[MBinding]()
				var i = 0
				val mBindingList = (parentNode \\ refNameInParent)
				mBindingList.foreach { xmiElem =>
						val currentElementId = parentId + "/@" + refNameInParent + "." + i
						loadedElements = loadedElements ++ List(loadMBindingElement(currentElementId,xmiElem))
						i += 1
				}
				loadedElements
		}

		def loadMBindingElement(elementId: String, elementNode: NodeSeq) : MBinding = {
		
				val modelElem = KevoreePackage.createMBinding
				ContainerRootLoadContext.map += elementId -> modelElem

				modelElem
		}

		def resolveMBinding(parentId: String, parentNode: NodeSeq, refNameInParent : String) {
				var i = 0
				val mBindingList = (parentNode \\ refNameInParent)
				mBindingList.foreach { xmiElem =>
						val currentElementId = parentId + "/@" + refNameInParent + "." + i
						resolveMBindingElement(currentElementId,xmiElem)
						i += 1
				}
		}

		def resolveMBindingElement(elementId: String, elementNode: NodeSeq) {

		val modelElem = ContainerRootLoadContext.map(elementId).asInstanceOf[MBinding]


				(elementNode \ "@port").headOption match {
						case Some(head) => {
								head.text.split(" ").foreach {
										xmiRef =>
												ContainerRootLoadContext.map.get(xmiRef) match {
														case Some(s: Port) => modelElem.setPort(s)
														case None => System.out.println("Port not found in map ! xmiRef:" + xmiRef)
												}
										}
								}
						case None => //No subtype for this library
				}
				(elementNode \ "@hub").headOption match {
						case Some(head) => {
								head.text.split(" ").foreach {
										xmiRef =>
												ContainerRootLoadContext.map.get(xmiRef) match {
														case Some(s: Channel) => modelElem.setHub(s)
														case None => System.out.println("Channel not found in map ! xmiRef:" + xmiRef)
												}
										}
								}
						case None => //No subtype for this library
				}
		}

}
