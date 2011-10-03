package org.kevoree.sub;

import xml.NodeSeq
import scala.collection.JavaConversions._
import org.kevoree._

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
		
				val modelElem = KevoreePackage.createComponentInstance
				ContainerRootLoadContext.map += elementId -> modelElem

				val dictionary = loadDictionary(elementId, elementNode, "dictionary")
				if(dictionary.size == 1) {
						val lm = dictionary.get(0)
						modelElem.setDictionary(lm)
						lm.eContainer = modelElem
				}

				val provided = loadPort(elementId, elementNode, "provided")
				modelElem.setProvided(provided)
				provided.foreach{ e => e.eContainer = modelElem }

				val required = loadPort(elementId, elementNode, "required")
				modelElem.setRequired(required)
				required.foreach{ e => e.eContainer = modelElem }

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
				(elementNode \ "@dictionary").headOption match {
						case Some(head) => {
								head.text.split(" ").foreach {
										xmiRef =>
												ContainerRootLoadContext.map.get(xmiRef) match {
														case Some(s: Dictionary) => modelElem.setDictionary(s)
														case None => System.out.println("Dictionary not found in map ! xmiRef:" + xmiRef)
												}
										}
								}
						case None => //No subtype for this library
				}
				(elementNode \ "@provided").headOption match {
						case Some(head) => {
								head.text.split(" ").foreach {
										xmiRef =>
												ContainerRootLoadContext.map.get(xmiRef) match {
														case Some(s: Port) => modelElem.addProvided(s)
														case None => System.out.println("Port not found in map ! xmiRef:" + xmiRef)
												}
										}
								}
						case None => //No subtype for this library
				}
				(elementNode \ "@required").headOption match {
						case Some(head) => {
								head.text.split(" ").foreach {
										xmiRef =>
												ContainerRootLoadContext.map.get(xmiRef) match {
														case Some(s: Port) => modelElem.addRequired(s)
														case None => System.out.println("Port not found in map ! xmiRef:" + xmiRef)
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
