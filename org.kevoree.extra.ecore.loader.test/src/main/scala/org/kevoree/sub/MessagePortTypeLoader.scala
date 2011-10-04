package org.kevoree.sub;

import xml.NodeSeq
import scala.collection.JavaConversions._
import org.kevoree._

trait MessagePortTypeLoader extends DictionaryTypeLoader {

		def loadMessagePortType(parentId: String, parentNode: NodeSeq, refNameInParent : String) : List[MessagePortType] = {
				var loadedElements = List[MessagePortType]()
				var i = 0
				val messagePortTypeList = (parentNode \\ refNameInParent)
				messagePortTypeList.foreach { xmiElem =>
						val currentElementId = parentId + "/@" + refNameInParent + "." + i
						loadedElements = loadedElements ++ List(loadMessagePortTypeElement(currentElementId,xmiElem))
						i += 1
				}
				loadedElements
		}

		def loadMessagePortTypeElement(elementId: String, elementNode: NodeSeq) : MessagePortType = {
		
				val modelElem = KevoreeFactory.createMessagePortType
				ContainerRootLoadContext.map += elementId -> modelElem

				(elementNode \ "dictionaryType").headOption.map{head =>
						val dictionaryTypeElementId = elementId + "/@dictionaryType"
						val dictionaryType = loadDictionaryTypeElement(dictionaryTypeElementId, head)
						modelElem.setDictionaryType(dictionaryType)
						dictionaryType.eContainer = modelElem
				}

				modelElem
		}

		def resolveMessagePortType(parentId: String, parentNode: NodeSeq, refNameInParent : String) {
				var i = 0
				val messagePortTypeList = (parentNode \\ refNameInParent)
				messagePortTypeList.foreach { xmiElem =>
						val currentElementId = parentId + "/@" + refNameInParent + "." + i
						resolveMessagePortTypeElement(currentElementId,xmiElem)
						i += 1
				}
		}

		def resolveMessagePortTypeElement(elementId: String, elementNode: NodeSeq) {

		val modelElem = ContainerRootLoadContext.map(elementId).asInstanceOf[MessagePortType]

		val nameVal = (elementNode \ "@name").text
		if(!nameVal.equals("")){
				modelElem.setName(java.lang.String.valueOf(nameVal))
		}

		val factoryBeanVal = (elementNode \ "@factoryBean").text
		if(!factoryBeanVal.equals("")){
				modelElem.setFactoryBean(java.lang.String.valueOf(factoryBeanVal))
		}

		val beanVal = (elementNode \ "@bean").text
		if(!beanVal.equals("")){
				modelElem.setBean(java.lang.String.valueOf(beanVal))
		}

		val synchroneVal = (elementNode \ "@synchrone").text
		if(!synchroneVal.equals("")){
				modelElem.setSynchrone(java.lang.Boolean.valueOf(synchroneVal))
		}


				(elementNode \ "@dictionaryType").headOption.map{head => 
						resolveDictionaryTypeElement(elementId + "/@dictionaryType", head)
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
				(elementNode \ "@filters").headOption match {
						case Some(head) => {
								head.text.split(" ").foreach {
										xmiRef =>
												ContainerRootLoadContext.map.get(xmiRef) match {
														case Some(s: TypedElement) => modelElem.addFilters(s)
														case None => System.out.println("TypedElement not found in map ! xmiRef:" + xmiRef)
												}
										}
								}
						case None => //No subtype for this library
				}
		}

}
