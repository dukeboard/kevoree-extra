package org.kevoree.sub;

import xml.NodeSeq
import scala.collection.JavaConversions._
import org.kevoree._


trait ChannelTypeLoader
		extends DictionaryType {

		def loadChannelTypes(parentId: String, parentNode: NodeSeq): Int = {
				val typeDefinitionsList = (parentNode \\ "typeDefinitions")
				var i = 0
				typeDefinitionsList.foreach { xmiElem =>
						val modelElem = KevoreePackage.createChannelType
						ContainerRootLoadContext.map += parentId + "/@" + xmiElem.label + "." + i -> modelElem
						i += 1
				}
				i
		}
		def resolveChannelTypes(parentId: String, parentNode: NodeSeq, owner: ContainerRoot) {
				val typeDefinitionsList = (parentNode \\ "typeDefinitions")
				var i = 0
				typeDefinitionsList.foreach { xmiElem =>
						val modelElem = ContainerRootLoadContext.map(parentId + "/@" + xmiElem.label + "." + i).asInstanceOf[ChannelType]
						modelElem.eContainer = owner
						modelElem.setName(java.lang.String.valueOf((xmiElem \ "@name").text))
						modelElem.setFactoryBean(java.lang.String.valueOf((xmiElem \ "@factoryBean").text))
						modelElem.setBean(java.lang.String.valueOf((xmiElem \ "@bean").text))
						modelElem.setStartMethod(java.lang.String.valueOf((xmiElem \ "@startMethod").text))
						modelElem.setStopMethod(java.lang.String.valueOf((xmiElem \ "@stopMethod").text))
						modelElem.setUpdateMethod(java.lang.String.valueOf((xmiElem \ "@updateMethod").text))
						modelElem.setLowerBindings(java.lang.Integer.valueOf((xmiElem \ "@lowerBindings").text))
						modelElem.setUpperBindings(java.lang.Integer.valueOf((xmiElem \ "@upperBindings").text))
						modelElem.setLowerFragments(java.lang.Integer.valueOf((xmiElem \ "@lowerFragments").text))
						modelElem.setUpperFragments(java.lang.Integer.valueOf((xmiElem \ "@upperFragments").text))

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
