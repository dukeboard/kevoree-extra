package org.kevoree.sub;

import xml.NodeSeq
import scala.collection.JavaConversions._
import org.kevoree._

trait GroupLoader extends DictionaryLoader {

		def loadGroup(parentId: String, parentNode: NodeSeq, refNameInParent : String) : List[Group] = {
				var loadedElements = List[Group]()
				var i = 0
				val groupList = (parentNode \\ refNameInParent)
				groupList.foreach { xmiElem =>
						val currentElementId = parentId + "/@" + refNameInParent + "." + i
						loadedElements = loadedElements ++ List(loadGroupElement(currentElementId,xmiElem))
						i += 1
				}
				loadedElements
		}

		def loadGroupElement(elementId: String, elementNode: NodeSeq) : Group = {
		
				val modelElem = KevoreeFactory.createGroup
				ContainerRootLoadContext.map += elementId -> modelElem

				(elementNode \ "dictionary").headOption.map{head =>
						val dictionaryElementId = elementId + "/@dictionary"
						val dictionary = loadDictionaryElement(dictionaryElementId, head)
						modelElem.setDictionary(dictionary)
						dictionary.eContainer = modelElem
				}

				modelElem
		}

		def resolveGroup(parentId: String, parentNode: NodeSeq, refNameInParent : String) {
				var i = 0
				val groupList = (parentNode \\ refNameInParent)
				groupList.foreach { xmiElem =>
						val currentElementId = parentId + "/@" + refNameInParent + "." + i
						resolveGroupElement(currentElementId,xmiElem)
						i += 1
				}
		}

		def resolveGroupElement(elementId: String, elementNode: NodeSeq) {

				val modelElem = ContainerRootLoadContext.map(elementId).asInstanceOf[Group]

				val nameVal = (elementNode \ "@name").text
				if(!nameVal.equals("")){
						modelElem.setName(java.lang.String.valueOf(nameVal))
				}

				val metaDataVal = (elementNode \ "@metaData").text
				if(!metaDataVal.equals("")){
						modelElem.setMetaData(java.lang.String.valueOf(metaDataVal))
				}


				(elementNode \ "dictionary").headOption.map{head => 
						resolveDictionaryElement(elementId + "/@dictionary", head)
				}

				(elementNode \ "@typeDefinition").headOption match {
						case Some(head) => {
								head.text.split(" ").foreach {
										xmiRef =>
												ContainerRootLoadContext.map.get(xmiRef) match {
														case Some(s: TypeDefinition) => modelElem.setTypeDefinition(s)
														case None => System.out.println("TypeDefinition not found in map ! xmiRef:" + xmiRef)
												}
										}
								}
						case None => //No subtype for this library
				}
				(elementNode \ "@subNodes").headOption match {
						case Some(head) => {
								head.text.split(" ").foreach {
										xmiRef =>
												ContainerRootLoadContext.map.get(xmiRef) match {
														case Some(s: ContainerNode) => modelElem.addSubNodes(s)
														case None => System.out.println("ContainerNode not found in map ! xmiRef:" + xmiRef)
												}
										}
								}
						case None => //No subtype for this library
				}
		}

}
