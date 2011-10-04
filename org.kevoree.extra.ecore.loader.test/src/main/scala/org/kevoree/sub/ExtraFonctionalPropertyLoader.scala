package org.kevoree.sub;

import xml.NodeSeq
import scala.collection.JavaConversions._
import org.kevoree._

trait ExtraFonctionalPropertyLoader{

		def loadExtraFonctionalProperty(parentId: String, parentNode: NodeSeq, refNameInParent : String) : List[ExtraFonctionalProperty] = {
				var loadedElements = List[ExtraFonctionalProperty]()
				var i = 0
				val extraFonctionalPropertyList = (parentNode \\ refNameInParent)
				extraFonctionalPropertyList.foreach { xmiElem =>
						val currentElementId = parentId + "/@" + refNameInParent + "." + i
						loadedElements = loadedElements ++ List(loadExtraFonctionalPropertyElement(currentElementId,xmiElem))
						i += 1
				}
				loadedElements
		}

		def loadExtraFonctionalPropertyElement(elementId: String, elementNode: NodeSeq) : ExtraFonctionalProperty = {
		
				val modelElem = KevoreeFactory.createExtraFonctionalProperty
				ContainerRootLoadContext.map += elementId -> modelElem

				modelElem
		}

		def resolveExtraFonctionalProperty(parentId: String, parentNode: NodeSeq, refNameInParent : String) {
				var i = 0
				val extraFonctionalPropertyList = (parentNode \\ refNameInParent)
				extraFonctionalPropertyList.foreach { xmiElem =>
						val currentElementId = parentId + "/@" + refNameInParent + "." + i
						resolveExtraFonctionalPropertyElement(currentElementId,xmiElem)
						i += 1
				}
		}

		def resolveExtraFonctionalPropertyElement(elementId: String, elementNode: NodeSeq) {

		val modelElem = ContainerRootLoadContext.map(elementId).asInstanceOf[ExtraFonctionalProperty]


				(elementNode \ "@portTypes").headOption match {
						case Some(head) => {
								head.text.split(" ").foreach {
										xmiRef =>
												ContainerRootLoadContext.map.get(xmiRef) match {
														case Some(s: PortTypeRef) => modelElem.addPortTypes(s)
														case None => System.out.println("PortTypeRef not found in map ! xmiRef:" + xmiRef)
												}
										}
								}
						case None => //No subtype for this library
				}
		}

}
