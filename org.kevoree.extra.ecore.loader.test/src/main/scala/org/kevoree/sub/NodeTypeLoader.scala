package org.kevoree.sub;

import xml.NodeSeq
import scala.collection.JavaConversions._
import org.kevoree._

trait NodeTypeLoader extends DictionaryTypeLoader {

		def loadNodeType(parentId: String, parentNode: NodeSeq, refNameInParent : String) : List[NodeType] = {
				var loadedElements = List[NodeType]()
				var i = 0
				val nodeTypeList = (parentNode \\ refNameInParent)
				nodeTypeList.foreach { xmiElem =>
						val currentElementId = parentId + "/@" + refNameInParent + "." + i
						loadedElements = loadedElements ++ List(loadNodeTypeElement(currentElementId,xmiElem))
						i += 1
				}
				loadedElements
		}

		def loadNodeTypeElement(elementId: String, elementNode: NodeSeq) : NodeType = {
		
				val modelElem = KevoreePackage.createNodeType
				ContainerRootLoadContext.map += elementId -> modelElem

				val dictionaryType = loadDictionaryType(elementId, elementNode, "dictionaryType")
				if(dictionaryType.size == 1) {
						val lm = dictionaryType.get(0)
						modelElem.setDictionaryType(lm)
						lm.eContainer = modelElem
				}

				modelElem
		}

		def resolveNodeType(parentId: String, parentNode: NodeSeq, refNameInParent : String) {
				var i = 0
				val nodeTypeList = (parentNode \\ refNameInParent)
				nodeTypeList.foreach { xmiElem =>
						val currentElementId = parentId + "/@" + refNameInParent + "." + i
						resolveNodeTypeElement(currentElementId,xmiElem)
						i += 1
				}
		}

		def resolveNodeTypeElement(elementId: String, elementNode: NodeSeq) {

		val modelElem = ContainerRootLoadContext.map(elementId).asInstanceOf[NodeType]

		val nameVal = (elementNode \\ "@name").text
		if(!nameVal.equals("")){
				modelElem.setName(java.lang.String.valueOf(nameVal))
		}

		val factoryBeanVal = (elementNode \\ "@factoryBean").text
		if(!factoryBeanVal.equals("")){
				modelElem.setFactoryBean(java.lang.String.valueOf(factoryBeanVal))
		}

		val beanVal = (elementNode \\ "@bean").text
		if(!beanVal.equals("")){
				modelElem.setBean(java.lang.String.valueOf(beanVal))
		}

		val startMethodVal = (elementNode \\ "@startMethod").text
		if(!startMethodVal.equals("")){
				modelElem.setStartMethod(java.lang.String.valueOf(startMethodVal))
		}

		val stopMethodVal = (elementNode \\ "@stopMethod").text
		if(!stopMethodVal.equals("")){
				modelElem.setStopMethod(java.lang.String.valueOf(stopMethodVal))
		}

		val updateMethodVal = (elementNode \\ "@updateMethod").text
		if(!updateMethodVal.equals("")){
				modelElem.setUpdateMethod(java.lang.String.valueOf(updateMethodVal))
		}


				(elementNode \ "@deployUnits").headOption match {
						case Some(head) => {
								head.text.split(" ").foreach {
										xmiRef =>
												ContainerRootLoadContext.map.get(xmiRef) match {
														case Some(s: DeployUnit) => modelElem.addDeployUnits(s)
														case None => System.out.println("DeployUnit not found in map ! xmiRef:" + xmiRef)
												}
										}
								}
						case None => //No subtype for this library
				}
				(elementNode \ "@dictionaryType").headOption match {
						case Some(head) => {
								head.text.split(" ").foreach {
										xmiRef =>
												ContainerRootLoadContext.map.get(xmiRef) match {
														case Some(s: DictionaryType) => modelElem.setDictionaryType(s)
														case None => System.out.println("DictionaryType not found in map ! xmiRef:" + xmiRef)
												}
										}
								}
						case None => //No subtype for this library
				}
				(elementNode \ "@superTypes").headOption match {
						case Some(head) => {
								head.text.split(" ").foreach {
										xmiRef =>
												ContainerRootLoadContext.map.get(xmiRef) match {
														case Some(s: TypeDefinition) => modelElem.addSuperTypes(s)
														case None => System.out.println("TypeDefinition not found in map ! xmiRef:" + xmiRef)
												}
										}
								}
						case None => //No subtype for this library
				}
				(elementNode \ "@managedPrimitiveTypes").headOption match {
						case Some(head) => {
								head.text.split(" ").foreach {
										xmiRef =>
												ContainerRootLoadContext.map.get(xmiRef) match {
														case Some(s: AdaptationPrimitiveType) => modelElem.addManagedPrimitiveTypes(s)
														case None => System.out.println("AdaptationPrimitiveType not found in map ! xmiRef:" + xmiRef)
												}
										}
								}
						case None => //No subtype for this library
				}
		}

}
