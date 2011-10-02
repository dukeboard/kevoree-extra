package org.kevoree.sub;

import xml.NodeSeq
import scala.collection.JavaConversions._
import org.kevoree._


trait GroupLoader
		extends Dictionary {

		def loadGroups(parentId: String, parentNode: NodeSeq): Int = {
				val groupsList = (parentNode \\ "groups")
				var i = 0
				groupsList.foreach { xmiElem =>
						val modelElem = KevoreePackage.createGroup
						ContainerRootLoadContext.map += parentId + "/@" + xmiElem.label + "." + i -> modelElem
						i += 1
				}
				i
		}
		def resolveGroups(parentId: String, parentNode: NodeSeq, owner: ContainerRoot) {
				val groupsList = (parentNode \\ "groups")
				var i = 0
				groupsList.foreach { xmiElem =>
						val modelElem = ContainerRootLoadContext.map(parentId + "/@" + xmiElem.label + "." + i).asInstanceOf[Group]
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
						(xmiElem \ "@subNodes").headOption match {
								case Some(head) => {
										head.text.split(" ").foreach {
												xmiRef =>
														ContainerRootLoadContext.map.get(xmiRef) match {
																case Some(s: ContainerNode) => modelElem.addSubNodes(s)
																case None => System.out.println("ContainerNode not found in map ! xmiRef:" + xmiRef)
														}
												}
										}
								case None => //No subtype for this library
						}

						owner.addGroups(modelElem)
						i += 1
				}
		}
}
