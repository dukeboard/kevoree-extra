package org.kevoree.sub;

import xml.NodeSeq
import scala.collection.JavaConversions._
import org.kevoree._


trait CompositeTypeLoader
		extends DictionaryType
		with PortTypeRef
		with IntegrationPattern
		with ExtraFonctionalProperty
		with Wire {

		def loadCompositeTypes(parentId: String, parentNode: NodeSeq): Int = {
				val typeDefinitionsList = (parentNode \\ "typeDefinitions")
				var i = 0
				typeDefinitionsList.foreach { xmiElem =>
						val modelElem = KevoreePackage.createCompositeType
						ContainerRootLoadContext.map += parentId + "/@" + xmiElem.label + "." + i -> modelElem
						i += 1
				}
				i
		}
		def resolveCompositeTypes(parentId: String, parentNode: NodeSeq, owner: ContainerRoot) {
				val typeDefinitionsList = (parentNode \\ "typeDefinitions")
				var i = 0
				typeDefinitionsList.foreach { xmiElem =>
						val modelElem = ContainerRootLoadContext.map(parentId + "/@" + xmiElem.label + "." + i).asInstanceOf[CompositeType]
						modelElem.eContainer = owner
						modelElem.setName(java.lang.String.valueOf((xmiElem \ "@name").text))
						modelElem.setFactoryBean(java.lang.String.valueOf((xmiElem \ "@factoryBean").text))
						modelElem.setBean(java.lang.String.valueOf((xmiElem \ "@bean").text))
						modelElem.setStartMethod(java.lang.String.valueOf((xmiElem \ "@startMethod").text))
						modelElem.setStopMethod(java.lang.String.valueOf((xmiElem \ "@stopMethod").text))
						modelElem.setUpdateMethod(java.lang.String.valueOf((xmiElem \ "@updateMethod").text))

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
						(xmiElem \ "@required").headOption match {
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
						(xmiElem \ "@integrationPatterns").headOption match {
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
						(xmiElem \ "@extraFonctionalProperties").headOption match {
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
						(xmiElem \ "@provided").headOption match {
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
						(xmiElem \ "@childs").headOption match {
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
						(xmiElem \ "@wires").headOption match {
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

						owner.addTypeDefinitions(modelElem)
						i += 1
				}
		}
}
