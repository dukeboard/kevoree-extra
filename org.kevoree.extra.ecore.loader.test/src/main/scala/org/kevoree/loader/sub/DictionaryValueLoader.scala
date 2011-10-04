package org.kevoree.loader.sub;

import xml.NodeSeq
import scala.collection.JavaConversions._
import org.kevoree._
import org.kevoree.loader._

trait DictionaryValueLoader{

		def loadDictionaryValue(parentId: String, parentNode: NodeSeq, refNameInParent : String) : List[DictionaryValue] = {
				var loadedElements = List[DictionaryValue]()
				var i = 0
				val dictionaryValueList = (parentNode \\ refNameInParent)
				dictionaryValueList.foreach { xmiElem =>
						val currentElementId = parentId + "/@" + refNameInParent + "." + i
						loadedElements = loadedElements ++ List(loadDictionaryValueElement(currentElementId,xmiElem))
						i += 1
				}
				loadedElements
		}

		def loadDictionaryValueElement(elementId: String, elementNode: NodeSeq) : DictionaryValue = {
		
				val modelElem = KevoreeFactory.createDictionaryValue
				ContainerRootLoadContext.map += elementId -> modelElem

				modelElem
		}

		def resolveDictionaryValue(parentId: String, parentNode: NodeSeq, refNameInParent : String) {
				var i = 0
				val dictionaryValueList = (parentNode \\ refNameInParent)
				dictionaryValueList.foreach { xmiElem =>
						val currentElementId = parentId + "/@" + refNameInParent + "." + i
						resolveDictionaryValueElement(currentElementId,xmiElem)
						i += 1
				}
		}

		def resolveDictionaryValueElement(elementId: String, elementNode: NodeSeq) {

				val modelElem = ContainerRootLoadContext.map(elementId).asInstanceOf[DictionaryValue]

				val valueVal = (elementNode \ "@value").text
				if(!valueVal.equals("")){
						modelElem.setValue(java.lang.String.valueOf(valueVal))
				}


				(elementNode \ "@attribute").headOption match {
						case Some(head) => {
								head.text.split(" ").foreach {
										xmiRef =>
												ContainerRootLoadContext.map.get(xmiRef) match {
														case Some(s: DictionaryAttribute) => modelElem.setAttribute(s)
														case None => System.out.println("DictionaryAttribute not found in map ! xmiRef:" + xmiRef)
												}
										}
								}
						case None => //No subtype for this library
				}
		}

}
