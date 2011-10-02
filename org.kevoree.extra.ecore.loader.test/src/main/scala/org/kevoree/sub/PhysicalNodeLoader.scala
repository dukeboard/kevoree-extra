package org.kevoree.sub;

import xml.NodeSeq
import scala.collection.JavaConversions._
import org.kevoree._


trait PhysicalNodeLoader
		extends  {

		def loadPhysicalNodes(parentId: String, parentNode: NodeSeq): Int = {
				val physicalNodesList = (parentNode \\ "physicalNodes")
				var i = 0
				physicalNodesList.foreach { xmiElem =>
						val modelElem = KevoreePackage.createPhysicalNode
						ContainerRootLoadContext.map += parentId + "/@" + xmiElem.label + "." + i -> modelElem
						i += 1
				}
				i
		}
		def resolvePhysicalNodes(parentId: String, parentNode: NodeSeq, owner: ContainerRoot) {
				val physicalNodesList = (parentNode \\ "physicalNodes")
				var i = 0
				physicalNodesList.foreach { xmiElem =>
						val modelElem = ContainerRootLoadContext.map(parentId + "/@" + xmiElem.label + "." + i).asInstanceOf[PhysicalNode]
						modelElem.eContainer = owner
						modelElem.setName(java.lang.String.valueOf((xmiElem \ "@name").text))

						(xmiElem \ "@hosts").headOption match {
								case Some(head) => {
										head.text.split(" ").foreach {
												xmiRef =>
														ContainerRootLoadContext.map.get(xmiRef) match {
																case Some(s: ContainerNode) => modelElem.addHosts(s)
																case None => System.out.println("ContainerNode not found in map ! xmiRef:" + xmiRef)
														}
												}
										}
								case None => //No subtype for this library
						}
						(xmiElem \ "@type").headOption match {
								case Some(head) => {
										head.text.split(" ").foreach {
												xmiRef =>
														ContainerRootLoadContext.map.get(xmiRef) match {
																case Some(s: PhysicalNodeType) => modelElem.setType(s)
																case None => System.out.println("PhysicalNodeType not found in map ! xmiRef:" + xmiRef)
														}
												}
										}
								case None => //No subtype for this library
						}

						owner.addPhysicalNodes(modelElem)
						i += 1
				}
		}
}
