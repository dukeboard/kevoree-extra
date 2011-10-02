package org.kevoree.sub;

import xml.NodeSeq
import scala.collection.JavaConversions._
import org.kevoree._


trait NodeLinkLoader
		extends NetworkProperty {

		def loadNodeLinks(parentId: String, parentNode: NodeSeq): Int = {
				val linkList = (parentNode \\ "link")
				var i = 0
				linkList.foreach { xmiElem =>
						val modelElem = KevoreePackage.createNodeLink
						ContainerRootLoadContext.map += parentId + "/@" + xmiElem.label + "." + i -> modelElem
						i += 1
				}
				i
		}
		def resolveNodeLinks(parentId: String, parentNode: NodeSeq, owner: NodeNetwork) {
				val linkList = (parentNode \\ "link")
				var i = 0
				linkList.foreach { xmiElem =>
						val modelElem = ContainerRootLoadContext.map(parentId + "/@" + xmiElem.label + "." + i).asInstanceOf[NodeLink]
						modelElem.eContainer = owner
						modelElem.setNetworkType(java.lang.String.valueOf((xmiElem \ "@networkType").text))
						modelElem.setEstimatedRate(java.lang.Integer.valueOf((xmiElem \ "@estimatedRate").text))
						modelElem.setLastCheck(java.lang.String.valueOf((xmiElem \ "@lastCheck").text))

						(xmiElem \ "@networkProperties").headOption match {
								case Some(head) => {
										head.text.split(" ").foreach {
												xmiRef =>
														ContainerRootLoadContext.map.get(xmiRef) match {
																case Some(s: NetworkProperty) => modelElem.addNetworkProperties(s)
																case None => System.out.println("NetworkProperty not found in map ! xmiRef:" + xmiRef)
														}
												}
										}
								case None => //No subtype for this library
						}

						owner.addLink(modelElem)
						i += 1
				}
		}
}
