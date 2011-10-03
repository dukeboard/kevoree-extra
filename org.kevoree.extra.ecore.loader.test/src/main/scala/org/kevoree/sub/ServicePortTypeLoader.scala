package org.kevoree.sub;

import xml.NodeSeq
import scala.collection.JavaConversions._
import org.kevoree._

trait ServicePortTypeLoader extends DictionaryTypeLoader with OperationLoader {

		def loadServicePortType(parentId: String, parentNode: NodeSeq, refNameInParent : String) : List[ServicePortType] = {
				var loadedElements = List[ServicePortType]()
				var i = 0
				val servicePortTypeList = (parentNode \\ refNameInParent)
				servicePortTypeList.foreach { xmiElem =>
						val currentElementId = parentId + "/@" + refNameInParent + "." + i
						loadedElements = loadedElements ++ List(loadServicePortTypeElement(currentElementId,xmiElem))
						i += 1
				}
				loadedElements
		}

		def loadServicePortTypeElement(elementId: String, elementNode: NodeSeq) : ServicePortType = {
		
				val modelElem = KevoreePackage.createServicePortType
				ContainerRootLoadContext.map += elementId -> modelElem

				val dictionaryType = loadDictionaryType(elementId, elementNode, "dictionaryType")
				if(dictionaryType.size == 1) {
						val lm = dictionaryType.get(0)
						modelElem.setDictionaryType(lm)
						lm.eContainer = modelElem
				}

				val operations = loadOperation(elementId, elementNode, "operations")
				modelElem.setOperations(operations)
				operations.foreach{ e => e.eContainer = modelElem }

				modelElem
		}

		def resolveServicePortType(parentId: String, parentNode: NodeSeq, refNameInParent : String) {
				var i = 0
				val servicePortTypeList = (parentNode \\ refNameInParent)
				servicePortTypeList.foreach { xmiElem =>
						val currentElementId = parentId + "/@" + refNameInParent + "." + i
						resolveServicePortTypeElement(currentElementId,xmiElem)
						i += 1
				}
		}

		def resolveServicePortTypeElement(elementId: String, elementNode: NodeSeq) {

		val modelElem = ContainerRootLoadContext.map(elementId).asInstanceOf[ServicePortType]

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

		val synchroneVal = (elementNode \\ "@synchrone").text
		if(!synchroneVal.equals("")){
				modelElem.setSynchrone(java.lang.Boolean.valueOf(synchroneVal))
		}

		val interfaceVal = (elementNode \\ "@interface").text
		if(!interfaceVal.equals("")){
				modelElem.setInterface(java.lang.String.valueOf(interfaceVal))
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
				(elementNode \ "@operations").headOption match {
						case Some(head) => {
								head.text.split(" ").foreach {
										xmiRef =>
												ContainerRootLoadContext.map.get(xmiRef) match {
														case Some(s: Operation) => modelElem.addOperations(s)
														case None => System.out.println("Operation not found in map ! xmiRef:" + xmiRef)
												}
										}
								}
						case None => //No subtype for this library
				}
		}

}
