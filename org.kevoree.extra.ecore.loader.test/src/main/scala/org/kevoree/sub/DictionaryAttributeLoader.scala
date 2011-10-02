package org.kevoree.sub;

import xml.NodeSeq
import scala.collection.JavaConversions._
import org.kevoree._


trait DictionaryAttributeLoader
		extends  {

		def loadDictionaryAttributes(parentId: String, parentNode: NodeSeq): Int = {
				val attributesList = (parentNode \\ "attributes")
				var i = 0
				attributesList.foreach { xmiElem =>
						val modelElem = KevoreePackage.createDictionaryAttribute
						ContainerRootLoadContext.map += parentId + "/@" + xmiElem.label + "." + i -> modelElem
						i += 1
				}
				i
		}
		def resolveDictionaryAttributes(parentId: String, parentNode: NodeSeq, owner: DictionaryType) {
				val attributesList = (parentNode \\ "attributes")
				var i = 0
				attributesList.foreach { xmiElem =>
						val modelElem = ContainerRootLoadContext.map(parentId + "/@" + xmiElem.label + "." + i).asInstanceOf[DictionaryAttribute]
						modelElem.eContainer = owner
						modelElem.setName(java.lang.String.valueOf((xmiElem \ "@name").text))
						modelElem.setOptional(java.lang.Boolean.valueOf((xmiElem \ "@optional").text))
						modelElem.setState(java.lang.Boolean.valueOf((xmiElem \ "@state").text))
						modelElem.setDatatype(java.lang.String.valueOf((xmiElem \ "@datatype").text))

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

						owner.addAttributes(modelElem)
						i += 1
				}
		}
}
