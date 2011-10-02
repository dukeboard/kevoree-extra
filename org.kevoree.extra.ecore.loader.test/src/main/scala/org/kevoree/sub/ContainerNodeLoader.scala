package org.kevoree.sub;

import xml.NodeSeq
import scala.collection.JavaConversions._
import org.kevoree._


trait ContainerNodeLoader
		extends Dictionary
		with ComponentInstance {

		def loadContainerNodes(parentId: String, parentNode: NodeSeq): Int = {
				val nodesList = (parentNode \\ "nodes")
				var i = 0
				nodesList.foreach { xmiElem =>
						val modelElem = KevoreePackage.createContainerNode
						ContainerRootLoadContext.map += parentId + "/@" + xmiElem.label + "." + i -> modelElem
						i += 1
				}
				i
		}
		def resolveContainerNodes(parentId: String, parentNode: NodeSeq, owner: ContainerRoot) {
				val nodesList = (parentNode \\ "nodes")
				var i = 0
				nodesList.foreach { xmiElem =>
						val modelElem = ContainerRootLoadContext.map(parentId + "/@" + xmiElem.label + "." + i).asInstanceOf[ContainerNode]
						modelElem.eContainer = owner
						modelElem.setName(java.lang.String.valueOf((xmiElem \ "@name").text))
						modelElem.setMetaData(java.lang.String.valueOf((xmiElem \ "@metaData").text))

						(xmiElem \ "@typeDefinition").headOption match {
								case Some(head) => {
										head.text.split(" ").foreach {
												xmiRef =>
														ContainerRootLoadContext.map.get(xmiRef) match {
																case Some(s: TypeDefinition) => modelElem.setTypeDefinition(s)
																case None => System.out.println("TypeDefinition not found in map ! xmiRef:" + xmiRef)
														}
												}
										}
								case None => //No subtype for this library
						}
						(xmiElem \ "@dictionary").headOption match {
								case Some(head) => {
										head.text.split(" ").foreach {
												xmiRef =>
														ContainerRootLoadContext.map.get(xmiRef) match {
																case Some(s: Dictionary) => modelElem.setDictionary(s)
																case None => System.out.println("Dictionary not found in map ! xmiRef:" + xmiRef)
														}
												}
										}
								case None => //No subtype for this library
						}
						(xmiElem \ "@components").headOption match {
								case Some(head) => {
										head.text.split(" ").foreach {
												xmiRef =>
														ContainerRootLoadContext.map.get(xmiRef) match {
																case Some(s: ComponentInstance) => modelElem.addComponents(s)
																case None => System.out.println("ComponentInstance not found in map ! xmiRef:" + xmiRef)
														}
												}
										}
								case None => //No subtype for this library
						}

						owner.addNodes(modelElem)
						i += 1
				}
		}
}
