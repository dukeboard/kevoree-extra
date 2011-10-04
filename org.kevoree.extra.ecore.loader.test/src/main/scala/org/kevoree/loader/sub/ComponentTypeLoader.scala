package org.kevoree.loader.sub;

import xml.NodeSeq
import scala.collection.JavaConversions._
import org.kevoree._
import org.kevoree.loader._

trait ComponentTypeLoader extends DictionaryTypeLoader with PortTypeRefLoader with IntegrationPatternLoader with ExtraFonctionalPropertyLoader {

		def loadComponentType(parentId: String, parentNode: NodeSeq, refNameInParent : String) : List[ComponentType] = {
				var loadedElements = List[ComponentType]()
				var i = 0
				val componentTypeList = (parentNode \\ refNameInParent)
				componentTypeList.foreach { xmiElem =>
						val currentElementId = parentId + "/@" + refNameInParent + "." + i
						loadedElements = loadedElements ++ List(loadComponentTypeElement(currentElementId,xmiElem))
						i += 1
				}
				loadedElements
		}

		def loadComponentTypeElement(elementId: String, elementNode: NodeSeq) : ComponentType = {
		
				val modelElem = KevoreeFactory.createComponentType
				ContainerRootLoadContext.map += elementId -> modelElem

				(elementNode \ "dictionaryType").headOption.map{head =>
						val dictionaryTypeElementId = elementId + "/@dictionaryType"
						val dictionaryType = loadDictionaryTypeElement(dictionaryTypeElementId, head)
						modelElem.setDictionaryType(dictionaryType)
				}

				val required = loadPortTypeRef(elementId, elementNode, "required")
				modelElem.setRequired(required)

				val integrationPatterns = loadIntegrationPattern(elementId, elementNode, "integrationPatterns")
				modelElem.setIntegrationPatterns(integrationPatterns)

				(elementNode \ "extraFonctionalProperties").headOption.map{head =>
						val extraFonctionalPropertiesElementId = elementId + "/@extraFonctionalProperties"
						val extraFonctionalProperties = loadExtraFonctionalPropertyElement(extraFonctionalPropertiesElementId, head)
						modelElem.setExtraFonctionalProperties(extraFonctionalProperties)
				}

				val provided = loadPortTypeRef(elementId, elementNode, "provided")
				modelElem.setProvided(provided)

				modelElem
		}

		def resolveComponentType(parentId: String, parentNode: NodeSeq, refNameInParent : String) {
				var i = 0
				val componentTypeList = (parentNode \\ refNameInParent)
				componentTypeList.foreach { xmiElem =>
						val currentElementId = parentId + "/@" + refNameInParent + "." + i
						resolveComponentTypeElement(currentElementId,xmiElem)
						i += 1
				}
		}

		def resolveComponentTypeElement(elementId: String, elementNode: NodeSeq) {

				val modelElem = ContainerRootLoadContext.map(elementId).asInstanceOf[ComponentType]

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


				(elementNode \ "dictionaryType").headOption.map{head => 
						resolveDictionaryTypeElement(elementId + "/@dictionaryType", head)
				}

				resolvePortTypeRef(elementId, elementNode, "required")

				resolveIntegrationPattern(elementId, elementNode, "integrationPatterns")

				(elementNode \ "extraFonctionalProperties").headOption.map{head => 
						resolveExtraFonctionalPropertyElement(elementId + "/@extraFonctionalProperties", head)
				}

				resolvePortTypeRef(elementId, elementNode, "provided")

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
