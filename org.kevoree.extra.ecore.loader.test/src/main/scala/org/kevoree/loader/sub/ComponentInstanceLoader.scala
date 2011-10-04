package org.kevoree.loader.sub;

import xml.NodeSeq
import scala.collection.JavaConversions._
import org.kevoree._
import org.kevoree.loader._

trait ComponentInstanceLoader extends DictionaryLoader with PortLoader {

		def loadComponentInstance(parentId: String, parentNode: NodeSeq, refNameInParent : String) : List[ComponentInstance] = {
				var loadedElements = List[ComponentInstance]()
				var i = 0
				val componentInstanceList = (parentNode \\ refNameInParent)
				componentInstanceList.foreach { xmiElem =>
						val currentElementId = parentId + "/@" + refNameInParent + "." + i
						loadedElements = loadedElements ++ List(loadComponentInstanceElement(currentElementId,xmiElem))
						i += 1
				}
				loadedElements
		}

		def loadComponentInstanceElement(elementId: String, elementNode: NodeSeq) : ComponentInstance = {
		
				val modelElem = KevoreeFactory.createComponentInstance
				ContainerRootLoadContext.map += elementId -> modelElem

				(elementNode \ "dictionary").headOption.map{head =>
						val dictionaryElementId = elementId + "/@dictionary"
						val dictionary = loadDictionaryElement(dictionaryElementId, head)
						modelElem.setDictionary(dictionary)
				}

				val provided = loadPort(elementId, elementNode, "provided")
				modelElem.setProvided(provided)

				val required = loadPort(elementId, elementNode, "required")
				modelElem.setRequired(required)

				modelElem
		}

		def resolveComponentInstance(parentId: String, parentNode: NodeSeq, refNameInParent : String) {
				var i = 0
				val componentInstanceList = (parentNode \\ refNameInParent)
				componentInstanceList.foreach { xmiElem =>
						val currentElementId = parentId + "/@" + refNameInParent + "." + i
						resolveComponentInstanceElement(currentElementId,xmiElem)
						i += 1
				}
		}

		def resolveComponentInstanceElement(elementId: String, elementNode: NodeSeq) {

				val modelElem = ContainerRootLoadContext.map(elementId).asInstanceOf[ComponentInstance]

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

				resolvePort(elementId, elementNode, "provided")

				resolvePort(elementId, elementNode, "required")

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
				(elementNode \ "@namespace").headOption match {
						case Some(head) => {
								head.text.split(" ").foreach {
										xmiRef =>
												ContainerRootLoadContext.map.get(xmiRef) match {
														case Some(s: Namespace) => modelElem.setNamespace(s)
														case None => System.out.println("Namespace not found in map ! xmiRef:" + xmiRef)
												}
										}
								}
						case None => //No subtype for this library
				}
		}

}
