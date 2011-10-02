package org.kevoree.sub;

import xml.NodeSeq
import scala.collection.JavaConversions._
import org.kevoree._


trait MBindingLoader
		extends  {

		def loadMBindings(parentId: String, parentNode: NodeSeq): Int = {
				val mBindingsList = (parentNode \\ "mBindings")
				var i = 0
				mBindingsList.foreach { xmiElem =>
						val modelElem = KevoreePackage.createMBinding
						ContainerRootLoadContext.map += parentId + "/@" + xmiElem.label + "." + i -> modelElem
						i += 1
				}
				i
		}
		def resolveMBindings(parentId: String, parentNode: NodeSeq, owner: ContainerRoot) {
				val mBindingsList = (parentNode \\ "mBindings")
				var i = 0
				mBindingsList.foreach { xmiElem =>
						val modelElem = ContainerRootLoadContext.map(parentId + "/@" + xmiElem.label + "." + i).asInstanceOf[MBinding]
						modelElem.eContainer = owner

						(xmiElem \ "@port").headOption match {
								case Some(head) => {
										head.text.split(" ").foreach {
												xmiRef =>
														ContainerRootLoadContext.map.get(xmiRef) match {
																case Some(s: Port) => modelElem.setPort(s)
																case None => System.out.println("Port not found in map ! xmiRef:" + xmiRef)
														}
												}
										}
								case None => //No subtype for this library
						}
						(xmiElem \ "@hub").headOption match {
								case Some(head) => {
										head.text.split(" ").foreach {
												xmiRef =>
														ContainerRootLoadContext.map.get(xmiRef) match {
																case Some(s: Channel) => modelElem.setHub(s)
																case None => System.out.println("Channel not found in map ! xmiRef:" + xmiRef)
														}
												}
										}
								case None => //No subtype for this library
						}

						owner.addMBindings(modelElem)
						i += 1
				}
		}
}
