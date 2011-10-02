package org.kevoree.sub;

import xml.NodeSeq
import scala.collection.JavaConversions._
import org.kevoree._


trait PortLoader
		extends  {

		def loadPorts(parentId: String, parentNode: NodeSeq): Int = {
				val providedList = (parentNode \\ "provided")
				var i = 0
				providedList.foreach { xmiElem =>
						val modelElem = KevoreePackage.createPort
						ContainerRootLoadContext.map += parentId + "/@" + xmiElem.label + "." + i -> modelElem
						i += 1
				}
				i
		}
		def resolvePorts(parentId: String, parentNode: NodeSeq, owner: ComponentInstance) {
				val providedList = (parentNode \\ "provided")
				var i = 0
				providedList.foreach { xmiElem =>
						val modelElem = ContainerRootLoadContext.map(parentId + "/@" + xmiElem.label + "." + i).asInstanceOf[Port]
						modelElem.eContainer = owner

						(xmiElem \ "@portTypeRef").headOption match {
								case Some(head) => {
										head.text.split(" ").foreach {
												xmiRef =>
														ContainerRootLoadContext.map.get(xmiRef) match {
																case Some(s: PortTypeRef) => modelElem.setPortTypeRef(s)
																case None => System.out.println("PortTypeRef not found in map ! xmiRef:" + xmiRef)
														}
												}
										}
								case None => //No subtype for this library
						}

						owner.addProvided(modelElem)
						i += 1
				}
		}
}
