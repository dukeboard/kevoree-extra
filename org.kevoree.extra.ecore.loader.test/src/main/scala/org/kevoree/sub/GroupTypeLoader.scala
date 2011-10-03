package org.kevoree.sub;

import xml.NodeSeq
import scala.collection.JavaConversions._
import org.kevoree._

trait GroupTypeLoader extends DictionaryTypeLoader {

		def loadGroupType(parentId: String, parentNode: NodeSeq, refNameInParent : String) : List[GroupType] = {
				var loadedElements = List[GroupType]()
				var i = 0
				val groupTypeList = (parentNode \\ refNameInParent)
				groupTypeList.foreach { xmiElem =>
						val currentElementId = parentId + "/@" + refNameInParent + "." + i
						loadedElements = loadedElements ++ List(loadGroupTypeElement(currentElementId,xmiElem))
						i += 1
				}
				loadedElements
		}

		def loadGroupTypeElement(elementId: String, elementNode: NodeSeq) : GroupType = {
		
				val modelElem = KevoreePackage.createGroupType
				ContainerRootLoadContext.map += elementId -> modelElem

				(elementNode \ "dictionaryType").headOption.map{head =>
						val dictionaryTypeElementId = elementId + "/@dictionaryType"
						val dictionaryType = loadDictionaryTypeElement(dictionaryTypeElementId, head)
						modelElem.setDictionaryType(dictionaryType)
						dictionaryType.eContainer = modelElem
				}

				modelElem
		}

		def resolveGroupType(parentId: String, parentNode: NodeSeq, refNameInParent : String) {
				var i = 0
				val groupTypeList = (parentNode \\ refNameInParent)
				groupTypeList.foreach { xmiElem =>
						val currentElementId = parentId + "/@" + refNameInParent + "." + i
						resolveGroupTypeElement(currentElementId,xmiElem)
						i += 1
				}
		}

		def resolveGroupTypeElement(elementId: String, elementNode: NodeSeq) {

		val modelElem = ContainerRootLoadContext.map(elementId).asInstanceOf[GroupType]

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

		val startMethodVal = (elementNode \ "@startMethod").text
		if(!startMethodVal.equals("")){
				modelElem.setStartMethod(java.lang.String.valueOf(startMethodVal))
		}

		val stopMethodVal = (elementNode \ "@stopMethod").text
		if(!stopMethodVal.equals("")){
				modelElem.setStopMethod(java.lang.String.valueOf(stopMethodVal))
		}

		val updateMethodVal = (elementNode \ "@updateMethod").text
		if(!updateMethodVal.equals("")){
				modelElem.setUpdateMethod(java.lang.String.valueOf(updateMethodVal))
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
		}

}
