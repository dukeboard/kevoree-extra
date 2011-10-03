package org.kevoree.sub;

import xml.NodeSeq
import scala.collection.JavaConversions._
import org.kevoree._

trait DictionaryTypeLoader extends DictionaryAttributeLoader with DictionaryValueLoader {

		def loadDictionaryType(parentId: String, parentNode: NodeSeq, refNameInParent : String) : List[DictionaryType] = {
				var loadedElements = List[DictionaryType]()
				var i = 0
				val dictionaryTypeList = (parentNode \\ refNameInParent)
				dictionaryTypeList.foreach { xmiElem =>
						val currentElementId = parentId + "/@" + refNameInParent + "." + i
						loadedElements = loadedElements ++ List(loadDictionaryTypeElement(currentElementId,xmiElem))
						i += 1
				}
				loadedElements
		}

		def loadDictionaryTypeElement(elementId: String, elementNode: NodeSeq) : DictionaryType = {
		
				val modelElem = KevoreePackage.createDictionaryType
				ContainerRootLoadContext.map += elementId -> modelElem

				val attributes = loadDictionaryAttribute(elementId, elementNode, "attributes")
				modelElem.setAttributes(attributes)
				attributes.foreach{ e => e.eContainer = modelElem }

				val defaultValues = loadDictionaryValue(elementId, elementNode, "defaultValues")
				modelElem.setDefaultValues(defaultValues)
				defaultValues.foreach{ e => e.eContainer = modelElem }

				modelElem
		}

		def resolveDictionaryType(parentId: String, parentNode: NodeSeq, refNameInParent : String) {
				var i = 0
				val dictionaryTypeList = (parentNode \\ refNameInParent)
				dictionaryTypeList.foreach { xmiElem =>
						val currentElementId = parentId + "/@" + refNameInParent + "." + i
						resolveDictionaryTypeElement(currentElementId,xmiElem)
						i += 1
				}
		}

		def resolveDictionaryTypeElement(elementId: String, elementNode: NodeSeq) {

		val modelElem = ContainerRootLoadContext.map(elementId).asInstanceOf[DictionaryType]


				(elementNode \ "@attributes").headOption match {
						case Some(head) => {
								head.text.split(" ").foreach {
										xmiRef =>
												ContainerRootLoadContext.map.get(xmiRef) match {
														case Some(s: DictionaryAttribute) => modelElem.addAttributes(s)
														case None => System.out.println("DictionaryAttribute not found in map ! xmiRef:" + xmiRef)
												}
										}
								}
						case None => //No subtype for this library
				}
				(elementNode \ "@defaultValues").headOption match {
						case Some(head) => {
								head.text.split(" ").foreach {
										xmiRef =>
												ContainerRootLoadContext.map.get(xmiRef) match {
														case Some(s: DictionaryValue) => modelElem.addDefaultValues(s)
														case None => System.out.println("DictionaryValue not found in map ! xmiRef:" + xmiRef)
												}
										}
								}
						case None => //No subtype for this library
				}
		}

}
