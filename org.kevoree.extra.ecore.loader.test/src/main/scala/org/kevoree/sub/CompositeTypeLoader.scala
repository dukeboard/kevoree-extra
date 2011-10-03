package org.kevoree.sub;

import xml.NodeSeq
import scala.collection.JavaConversions._
import org.kevoree._

trait CompositeTypeLoader extends DictionaryTypeLoader with PortTypeRefLoader with IntegrationPatternLoader with ExtraFonctionalPropertyLoader with WireLoader {

		def loadCompositeType(parentId: String, parentNode: NodeSeq, refNameInParent : String) : List[CompositeType] = {
				var loadedElements = List[CompositeType]()
				var i = 0
				val compositeTypeList = (parentNode \\ refNameInParent)
				compositeTypeList.foreach { xmiElem =>
						val currentElementId = parentId + "/@" + refNameInParent + "." + i
						loadedElements = loadedElements ++ List(loadCompositeTypeElement(currentElementId,xmiElem))
						i += 1
				}
				loadedElements
		}

		def loadCompositeTypeElement(elementId: String, elementNode: NodeSeq) : CompositeType = {
		
				val modelElem = KevoreePackage.createCompositeType
				ContainerRootLoadContext.map += elementId -> modelElem

				val dictionaryType = loadDictionaryType(elementId, elementNode, "dictionaryType")
				if(dictionaryType.size == 1) {
						val lm = dictionaryType.get(0)
						modelElem.setDictionaryType(lm)
						lm.eContainer = modelElem
				}

				val required = loadPortTypeRef(elementId, elementNode, "required")
				modelElem.setRequired(required)
				required.foreach{ e => e.eContainer = modelElem }

				val integrationPatterns = loadIntegrationPattern(elementId, elementNode, "integrationPatterns")
				modelElem.setIntegrationPatterns(integrationPatterns)
				integrationPatterns.foreach{ e => e.eContainer = modelElem }

				val extraFonctionalProperties = loadExtraFonctionalProperty(elementId, elementNode, "extraFonctionalProperties")
				if(extraFonctionalProperties.size == 1) {
						val lm = extraFonctionalProperties.get(0)
						modelElem.setExtraFonctionalProperties(lm)
						lm.eContainer = modelElem
				}

				val provided = loadPortTypeRef(elementId, elementNode, "provided")
				modelElem.setProvided(provided)
				provided.foreach{ e => e.eContainer = modelElem }

				val wires = loadWire(elementId, elementNode, "wires")
				modelElem.setWires(wires)
				wires.foreach{ e => e.eContainer = modelElem }

				modelElem
		}

		def resolveCompositeType(parentId: String, parentNode: NodeSeq, refNameInParent : String) {
				var i = 0
				val compositeTypeList = (parentNode \\ refNameInParent)
				compositeTypeList.foreach { xmiElem =>
						val currentElementId = parentId + "/@" + refNameInParent + "." + i
						resolveCompositeTypeElement(currentElementId,xmiElem)
						i += 1
				}
		}

		def resolveCompositeTypeElement(elementId: String, elementNode: NodeSeq) {

		val modelElem = ContainerRootLoadContext.map(elementId).asInstanceOf[CompositeType]

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
				(elementNode \ "@required").headOption match {
						case Some(head) => {
								head.text.split(" ").foreach {
										xmiRef =>
												ContainerRootLoadContext.map.get(xmiRef) match {
														case Some(s: PortTypeRef) => modelElem.addRequired(s)
														case None => System.out.println("PortTypeRef not found in map ! xmiRef:" + xmiRef)
												}
										}
								}
						case None => //No subtype for this library
				}
				(elementNode \ "@integrationPatterns").headOption match {
						case Some(head) => {
								head.text.split(" ").foreach {
										xmiRef =>
												ContainerRootLoadContext.map.get(xmiRef) match {
														case Some(s: IntegrationPattern) => modelElem.addIntegrationPatterns(s)
														case None => System.out.println("IntegrationPattern not found in map ! xmiRef:" + xmiRef)
												}
										}
								}
						case None => //No subtype for this library
				}
				(elementNode \ "@extraFonctionalProperties").headOption match {
						case Some(head) => {
								head.text.split(" ").foreach {
										xmiRef =>
												ContainerRootLoadContext.map.get(xmiRef) match {
														case Some(s: ExtraFonctionalProperty) => modelElem.setExtraFonctionalProperties(s)
														case None => System.out.println("ExtraFonctionalProperty not found in map ! xmiRef:" + xmiRef)
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
														case Some(s: PortTypeRef) => modelElem.addProvided(s)
														case None => System.out.println("PortTypeRef not found in map ! xmiRef:" + xmiRef)
												}
										}
								}
						case None => //No subtype for this library
				}
				(elementNode \ "@childs").headOption match {
						case Some(head) => {
								head.text.split(" ").foreach {
										xmiRef =>
												ContainerRootLoadContext.map.get(xmiRef) match {
														case Some(s: ComponentType) => modelElem.addChilds(s)
														case None => System.out.println("ComponentType not found in map ! xmiRef:" + xmiRef)
												}
										}
								}
						case None => //No subtype for this library
				}
				(elementNode \ "@wires").headOption match {
						case Some(head) => {
								head.text.split(" ").foreach {
										xmiRef =>
												ContainerRootLoadContext.map.get(xmiRef) match {
														case Some(s: Wire) => modelElem.addWires(s)
														case None => System.out.println("Wire not found in map ! xmiRef:" + xmiRef)
												}
										}
								}
						case None => //No subtype for this library
				}
		}

}
