package org.kevoree.sub;

import xml.NodeSeq
import scala.collection.JavaConversions._
import org.kevoree._


trait TypeDefinitionLoader
		extends ComponentType
		with CompositeType
		with ServicePortType
		with MessagePortType
		with ChannelType
		with GroupType
		with NodeType {

		def loadTypeDefinitions(parentId: String, parentNode: NodeSeq): Int = {
				val typeDefinitionsList = (parentNode \\ "typeDefinitions")
				var i = 0
				typeDefinitionsList.foreach { xmiElem =>
				val typeDefId = parentId + "/@typeDefinitions." + i
				xmiElem.attributes.find(att => att.key.equals("type")) match {
						case Some(s) => {
								s.value.text match {
										case "kevoree:ComponentType" => {
													val modelElem = KevoreePackage.createComponentType
													ContainerRootLoadContext.map += typeDefId -> modelElem
											}
										case "kevoree:CompositeType" => {
													val modelElem = KevoreePackage.createCompositeType
													ContainerRootLoadContext.map += typeDefId -> modelElem
											}
										case "kevoree:ServicePortType" => {
													val modelElem = KevoreePackage.createServicePortType
													ContainerRootLoadContext.map += typeDefId -> modelElem
											}
										case "kevoree:MessagePortType" => {
													val modelElem = KevoreePackage.createMessagePortType
													ContainerRootLoadContext.map += typeDefId -> modelElem
											}
										case "kevoree:ChannelType" => {
													val modelElem = KevoreePackage.createChannelType
													ContainerRootLoadContext.map += typeDefId -> modelElem
											}
										case "kevoree:GroupType" => {
													val modelElem = KevoreePackage.createGroupType
													ContainerRootLoadContext.map += typeDefId -> modelElem
											}
										case "kevoree:NodeType" => {
													val modelElem = KevoreePackage.createNodeType
													ContainerRootLoadContext.map += typeDefId -> modelElem
											}
										case _@e => throw new UnsupportedOperationException("Processor for TypeDefinitions has no mapping for type:" + e)
								}
						}
						case None => System.out.println("No 'type' attribute.")
				}
						i += 1
				}
				i
		}
		def resolveTypeDefinitions(parentId: String, parentNode: NodeSeq, owner: ContainerRoot) {
				val typeDefinitionsList = (parentNode \\ "typeDefinitions")
				var i = 0
				typeDefinitionsList.foreach { xmiElem =>
						val modelElem = ContainerRootLoadContext.map(parentId + "/@" + xmiElem.label + "." + i).asInstanceOf[TypeDefinition]
						modelElem.eContainer = owner
						modelElem.setName(java.lang.String.valueOf((xmiElem \ "@name").text))
						modelElem.setFactoryBean(java.lang.String.valueOf((xmiElem \ "@factoryBean").text))
						modelElem.setBean(java.lang.String.valueOf((xmiElem \ "@bean").text))

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

						owner.addTypeDefinitions(modelElem)
						i += 1
				}
		}
}
