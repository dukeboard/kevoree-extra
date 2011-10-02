package org.kevoree.sub;

import xml.NodeSeq
import scala.collection.JavaConversions._
import org.kevoree._


trait NodeNetworkLoader
		extends NodeLink {

		def loadNodeNetworks(parentId: String, parentNode: NodeSeq): Int = {
				val nodeNetworksList = (parentNode \\ "nodeNetworks")
				var i = 0
				nodeNetworksList.foreach { xmiElem =>
						val modelElem = KevoreePackage.createNodeNetwork
						ContainerRootLoadContext.map += parentId + "/@" + xmiElem.label + "." + i -> modelElem
						i += 1
				}
				i
		}
		def resolveNodeNetworks(parentId: String, parentNode: NodeSeq, owner: ContainerRoot) {
				val nodeNetworksList = (parentNode \\ "nodeNetworks")
				var i = 0
				nodeNetworksList.foreach { xmiElem =>
						val modelElem = ContainerRootLoadContext.map(parentId + "/@" + xmiElem.label + "." + i).asInstanceOf[NodeNetwork]
						modelElem.eContainer = owner

						(xmiElem \ "@link").headOption match {
								case Some(head) => {
										head.text.split(" ").foreach {
												xmiRef =>
														ContainerRootLoadContext.map.get(xmiRef) match {
																case Some(s: NodeLink) => modelElem.addLink(s)
																case None => System.out.println("NodeLink not found in map ! xmiRef:" + xmiRef)
														}
												}
										}
								case None => //No subtype for this library
						}
						(xmiElem \ "@initBy").headOption match {
								case Some(head) => {
										head.text.split(" ").foreach {
												xmiRef =>
														ContainerRootLoadContext.map.get(xmiRef) match {
																case Some(s: ContainerNode) => modelElem.setInitBy(s)
																case None => System.out.println("ContainerNode not found in map ! xmiRef:" + xmiRef)
														}
												}
										}
								case None => //No subtype for this library
						}
						(xmiElem \ "@target").headOption match {
								case Some(head) => {
										head.text.split(" ").foreach {
												xmiRef =>
														ContainerRootLoadContext.map.get(xmiRef) match {
																case Some(s: ContainerNode) => modelElem.setTarget(s)
																case None => System.out.println("ContainerNode not found in map ! xmiRef:" + xmiRef)
														}
												}
										}
								case None => //No subtype for this library
						}

						owner.addNodeNetworks(modelElem)
						i += 1
				}
		}
}
