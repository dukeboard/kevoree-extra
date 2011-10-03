package org.kevoree.sub;

import xml.NodeSeq
import scala.collection.JavaConversions._
import org.kevoree._

trait IntegrationPatternLoader extends ExtraFonctionalPropertyLoader {

		def loadIntegrationPattern(parentId: String, parentNode: NodeSeq, refNameInParent : String) : List[IntegrationPattern] = {
				var loadedElements = List[IntegrationPattern]()
				var i = 0
				val integrationPatternList = (parentNode \\ refNameInParent)
				integrationPatternList.foreach { xmiElem =>
						val currentElementId = parentId + "/@" + refNameInParent + "." + i
						loadedElements = loadedElements ++ List(loadIntegrationPatternElement(currentElementId,xmiElem))
						i += 1
				}
				loadedElements
		}

		def loadIntegrationPatternElement(elementId: String, elementNode: NodeSeq) : IntegrationPattern = {
		
				val modelElem = KevoreePackage.createIntegrationPattern
				ContainerRootLoadContext.map += elementId -> modelElem

				val extraFonctionalProperties = loadExtraFonctionalProperty(elementId, elementNode, "extraFonctionalProperties")
				modelElem.setExtraFonctionalProperties(extraFonctionalProperties)
				extraFonctionalProperties.foreach{ e => e.eContainer = modelElem }

				modelElem
		}

		def resolveIntegrationPattern(parentId: String, parentNode: NodeSeq, refNameInParent : String) {
				var i = 0
				val integrationPatternList = (parentNode \\ refNameInParent)
				integrationPatternList.foreach { xmiElem =>
						val currentElementId = parentId + "/@" + refNameInParent + "." + i
						resolveIntegrationPatternElement(currentElementId,xmiElem)
						i += 1
				}
		}

		def resolveIntegrationPatternElement(elementId: String, elementNode: NodeSeq) {

		val modelElem = ContainerRootLoadContext.map(elementId).asInstanceOf[IntegrationPattern]

		val nameVal = (elementNode \ "@name").text
		if(!nameVal.equals("")){
				modelElem.setName(java.lang.String.valueOf(nameVal))
		}


				resolveExtraFonctionalProperty(elementId, elementNode, "extraFonctionalProperties")

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
