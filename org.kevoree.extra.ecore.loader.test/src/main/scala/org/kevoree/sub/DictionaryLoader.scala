package org.kevoree.sub;

import xml.NodeSeq
import scala.collection.JavaConversions._
import org.kevoree._


trait DictionaryLoader
		extends DictionaryValue {

		def loadDictionarys(parentId: String, parentNode: NodeSeq): Int = {
				val dictionaryList = (parentNode \\ "dictionary")
				var i = 0
				dictionaryList.foreach { xmiElem =>
						val modelElem = KevoreePackage.createDictionary
						ContainerRootLoadContext.map += parentId + "/@" + xmiElem.label + "." + i -> modelElem
						i += 1
				}
				i
		}
		def resolveDictionarys(parentId: String, parentNode: NodeSeq, owner: ContainerNode) {
				val dictionaryList = (parentNode \\ "dictionary")
				var i = 0
				dictionaryList.foreach { xmiElem =>
						val modelElem = ContainerRootLoadContext.map(parentId + "/@" + xmiElem.label + "." + i).asInstanceOf[Dictionary]
						modelElem.eContainer = owner

						(xmiElem \ "@values").headOption match {
								case Some(head) => {
										head.text.split(" ").foreach {
												xmiRef =>
														ContainerRootLoadContext.map.get(xmiRef) match {
																case Some(s: DictionaryValue) => modelElem.addValues(s)
																case None => System.out.println("DictionaryValue not found in map ! xmiRef:" + xmiRef)
														}
												}
										}
								case None => //No subtype for this library
						}

						owner.setDictionary(modelElem)
						i += 1
				}
		}
}
