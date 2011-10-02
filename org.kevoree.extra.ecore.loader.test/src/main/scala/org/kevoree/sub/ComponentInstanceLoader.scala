package org.kevoree.sub;

import xml.NodeSeq
import scala.collection.JavaConversions._
import org.kevoree._


trait ComponentInstanceLoader
		extends Dictionary
		with Port {

		def loadComponentInstances(parentId: String, parentNode: NodeSeq): Int = {
				val componentsList = (parentNode \\ "components")
				var i = 0
				componentsList.foreach { xmiElem =>
						val modelElem = KevoreePackage.createComponentInstance
						ContainerRootLoadContext.map += parentId + "/@" + xmiElem.label + "." + i -> modelElem
						i += 1
				}
				i
		}
		def resolveComponentInstances(parentId: String, parentNode: NodeSeq, owner: ContainerNode) {
				val componentsList = (parentNode \\ "components")
				var i = 0
				componentsList.foreach { xmiElem =>
						val modelElem = ContainerRootLoadContext.map(parentId + "/@" + xmiElem.label + "." + i).asInstanceOf[ComponentInstance]
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
						(xmiElem \ "@provided").headOption match {
								case Some(head) => {
										head.text.split(" ").foreach {
												xmiRef =>
														ContainerRootLoadContext.map.get(xmiRef) match {
																case Some(s: Port) => modelElem.addProvided(s)
																case None => System.out.println("Port not found in map ! xmiRef:" + xmiRef)
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
																case Some(s: Port) => modelElem.addRequired(s)
																case None => System.out.println("Port not found in map ! xmiRef:" + xmiRef)
														}
												}
										}
								case None => //No subtype for this library
						}
						(xmiElem \ "@namespace").headOption match {
								case Some(head) => {
										head.text.split(" ").foreach {
												xmiRef =>
														ContainerRootLoadContext.map.get(xmiRef) match {
																case Some(s: Namespace) => modelElem.setNamespace(s)
																case None => System.out.println("Namespace not found in map ! xmiRef:" + xmiRef)
														}
												}
										}
								case None => //No subtype for this library
						}

						owner.addComponents(modelElem)
						i += 1
				}
		}
}
