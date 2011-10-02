package org.kevoree.sub;

import xml.NodeSeq
import scala.collection.JavaConversions._
import org.kevoree._


trait ServicePortTypeLoader
		extends DictionaryType
		with Operation {

		def loadServicePortTypes(parentId: String, parentNode: NodeSeq): Int = {
				val typeDefinitionsList = (parentNode \\ "typeDefinitions")
				var i = 0
				typeDefinitionsList.foreach { xmiElem =>
						val modelElem = KevoreePackage.createServicePortType
						ContainerRootLoadContext.map += parentId + "/@" + xmiElem.label + "." + i -> modelElem
						i += 1
				}
				i
		}
		def resolveServicePortTypes(parentId: String, parentNode: NodeSeq, owner: ContainerRoot) {
				val typeDefinitionsList = (parentNode \\ "typeDefinitions")
				var i = 0
				typeDefinitionsList.foreach { xmiElem =>
						val modelElem = ContainerRootLoadContext.map(parentId + "/@" + xmiElem.label + "." + i).asInstanceOf[ServicePortType]
						modelElem.eContainer = owner
						modelElem.setName(java.lang.String.valueOf((xmiElem \ "@name").text))
						modelElem.setFactoryBean(java.lang.String.valueOf((xmiElem \ "@factoryBean").text))
						modelElem.setBean(java.lang.String.valueOf((xmiElem \ "@bean").text))
						modelElem.setSynchrone(java.lang.Boolean.valueOf((xmiElem \ "@synchrone").text))
						modelElem.setInterface(java.lang.String.valueOf((xmiElem \ "@interface").text))

						(xmiElem \ "@deployUnits").headOption match {
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
						(xmiElem \ "@dictionaryType").headOption match {
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
						(xmiElem \ "@operations").headOption match {
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

						owner.addTypeDefinitions(modelElem)
						i += 1
				}
		}
}
