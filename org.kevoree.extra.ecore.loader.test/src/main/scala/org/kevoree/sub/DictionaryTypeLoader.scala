package org.kevoree.sub;

import xml.NodeSeq
import scala.collection.JavaConversions._
import org.kevoree._


trait DictionaryTypeLoader
		extends DictionaryAttribute
		with DictionaryValue {

		def loadDictionaryTypes(parentId: String, parentNode: NodeSeq): Int = {
				val dictionaryTypeList = (parentNode \\ "dictionaryType")
				var i = 0
				dictionaryTypeList.foreach { xmiElem =>
						val modelElem = KevoreePackage.createDictionaryType
						ContainerRootLoadContext.map += parentId + "/@" + xmiElem.label + "." + i -> modelElem
						i += 1
				}
				i
		}
		def resolveDictionaryTypes(parentId: String, parentNode: NodeSeq, owner: TypeDefinition) {
				val dictionaryTypeList = (parentNode \\ "dictionaryType")
				var i = 0
				dictionaryTypeList.foreach { xmiElem =>
						val modelElem = ContainerRootLoadContext.map(parentId + "/@" + xmiElem.label + "." + i).asInstanceOf[DictionaryType]
						modelElem.eContainer = owner

						(xmiElem \ "@attributes").headOption match {
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
						(xmiElem \ "@defaultValues").headOption match {
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

						owner.setDictionaryType(modelElem)
						i += 1
				}
		}
}
