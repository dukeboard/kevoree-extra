package org.kevoree.sub;

import xml.NodeSeq
import scala.collection.JavaConversions._
import org.kevoree._

trait ContainerNodeLoader extends DictionaryLoader with ComponentInstanceLoader {

		def loadContainerNode(parentId: String, parentNode: NodeSeq, refNameInParent : String) : List[ContainerNode] = {
				var loadedElements = List[ContainerNode]()
				var i = 0
				val containerNodeList = (parentNode \\ refNameInParent)
				containerNodeList.foreach { xmiElem =>
						val currentElementId = parentId + "/@" + refNameInParent + "." + i
						loadedElements = loadedElements ++ List(loadContainerNodeElement(currentElementId,xmiElem))
						i += 1
				}
				loadedElements
		}

		def loadContainerNodeElement(elementId: String, elementNode: NodeSeq) : ContainerNode = {
		
				val modelElem = KevoreeFactory.createContainerNode
				ContainerRootLoadContext.map += elementId -> modelElem

				(elementNode \ "dictionary").headOption.map{head =>
						val dictionaryElementId = elementId + "/@dictionary"
						val dictionary = loadDictionaryElement(dictionaryElementId, head)
						modelElem.setDictionary(dictionary)
						dictionary.eContainer = modelElem
				}

				val components = loadComponentInstance(elementId, elementNode, "components")
				modelElem.setComponents(components)
				components.foreach{ e => e.eContainer = modelElem }

				modelElem
		}

		def resolveContainerNode(parentId: String, parentNode: NodeSeq, refNameInParent : String) {
				var i = 0
				val containerNodeList = (parentNode \\ refNameInParent)
				containerNodeList.foreach { xmiElem =>
						val currentElementId = parentId + "/@" + refNameInParent + "." + i
						resolveContainerNodeElement(currentElementId,xmiElem)
						i += 1
				}
		}

		def resolveContainerNodeElement(elementId: String, elementNode: NodeSeq) {

				val modelElem = ContainerRootLoadContext.map(elementId).asInstanceOf[ContainerNode]

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

				resolveComponentInstance(elementId, elementNode, "components")

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
				(elementNode \ "@hosts").headOption match {
						case Some(head) => {
								head.text.split(" ").foreach {
										xmiRef =>
												ContainerRootLoadContext.map.get(xmiRef) match {
														case Some(s: ContainerNode) => modelElem.addHosts(s)
														case None => System.out.println("ContainerNode not found in map ! xmiRef:" + xmiRef)
												}
										}
								}
						case None => //No subtype for this library
				}
		}

}
