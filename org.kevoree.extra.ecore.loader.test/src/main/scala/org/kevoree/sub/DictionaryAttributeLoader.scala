package org.kevoree.sub;

import xml.NodeSeq
import scala.collection.JavaConversions._
import org.kevoree._

trait DictionaryAttributeLoader{

		def loadDictionaryAttribute(parentId: String, parentNode: NodeSeq, refNameInParent : String) : List[DictionaryAttribute] = {
				var loadedElements = List[DictionaryAttribute]()
				var i = 0
				val dictionaryAttributeList = (parentNode \\ refNameInParent)
				dictionaryAttributeList.foreach { xmiElem =>
						val currentElementId = parentId + "/@" + refNameInParent + "." + i
						loadedElements = loadedElements ++ List(loadDictionaryAttributeElement(currentElementId,xmiElem))
						i += 1
				}
				loadedElements
		}

		def loadDictionaryAttributeElement(elementId: String, elementNode: NodeSeq) : DictionaryAttribute = {
		
				val modelElem = KevoreePackage.createDictionaryAttribute
				ContainerRootLoadContext.map += elementId -> modelElem

				modelElem
		}

		def resolveDictionaryAttribute(parentId: String, parentNode: NodeSeq, refNameInParent : String) {
				var i = 0
				val dictionaryAttributeList = (parentNode \\ refNameInParent)
				dictionaryAttributeList.foreach { xmiElem =>
						val currentElementId = parentId + "/@" + refNameInParent + "." + i
						resolveDictionaryAttributeElement(currentElementId,xmiElem)
						i += 1
				}
		}

		def resolveDictionaryAttributeElement(elementId: String, elementNode: NodeSeq) {

		val modelElem = ContainerRootLoadContext.map(elementId).asInstanceOf[DictionaryAttribute]

		val nameVal = (elementNode \\ "@name").text
		if(!nameVal.equals("")){
				modelElem.setName(java.lang.String.valueOf(nameVal))
		}

		val optionalVal = (elementNode \\ "@optional").text
		if(!optionalVal.equals("")){
				modelElem.setOptional(java.lang.Boolean.valueOf(optionalVal))
		}

		val stateVal = (elementNode \\ "@state").text
		if(!stateVal.equals("")){
				modelElem.setState(java.lang.Boolean.valueOf(stateVal))
		}

		val datatypeVal = (elementNode \\ "@datatype").text
		if(!datatypeVal.equals("")){
				modelElem.setDatatype(java.lang.String.valueOf(datatypeVal))
		}


				(elementNode \ "@genericTypes").headOption match {
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
		}

}
