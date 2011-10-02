package org.kevoree.sub;

import xml.NodeSeq
import scala.collection.JavaConversions._
import org.kevoree._


trait DictionaryValueLoader
		extends  {

		def loadDictionaryValues(parentId: String, parentNode: NodeSeq): Int = {
				val valuesList = (parentNode \\ "values")
				var i = 0
				valuesList.foreach { xmiElem =>
						val modelElem = KevoreePackage.createDictionaryValue
						ContainerRootLoadContext.map += parentId + "/@" + xmiElem.label + "." + i -> modelElem
						i += 1
				}
				i
		}
		def resolveDictionaryValues(parentId: String, parentNode: NodeSeq, owner: Dictionary) {
				val valuesList = (parentNode \\ "values")
				var i = 0
				valuesList.foreach { xmiElem =>
						val modelElem = ContainerRootLoadContext.map(parentId + "/@" + xmiElem.label + "." + i).asInstanceOf[DictionaryValue]
						modelElem.eContainer = owner
						modelElem.setValue(java.lang.String.valueOf((xmiElem \ "@value").text))

						(xmiElem \ "@attribute").headOption match {
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

						owner.addValues(modelElem)
						i += 1
				}
		}
}
