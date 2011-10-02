package org.kevoree.sub;

import xml.NodeSeq
import scala.collection.JavaConversions._
import org.kevoree._


trait DeployUnitLoader
		extends  {

		def loadDeployUnits(parentId: String, parentNode: NodeSeq): Int = {
				val deployUnitsList = (parentNode \\ "deployUnits")
				var i = 0
				deployUnitsList.foreach { xmiElem =>
						val modelElem = KevoreePackage.createDeployUnit
						ContainerRootLoadContext.map += parentId + "/@" + xmiElem.label + "." + i -> modelElem
						i += 1
				}
				i
		}
		def resolveDeployUnits(parentId: String, parentNode: NodeSeq, owner: ContainerRoot) {
				val deployUnitsList = (parentNode \\ "deployUnits")
				var i = 0
				deployUnitsList.foreach { xmiElem =>
						val modelElem = ContainerRootLoadContext.map(parentId + "/@" + xmiElem.label + "." + i).asInstanceOf[DeployUnit]
						modelElem.eContainer = owner
						modelElem.setName(java.lang.String.valueOf((xmiElem \ "@name").text))
						modelElem.setGroupName(java.lang.String.valueOf((xmiElem \ "@groupName").text))
						modelElem.setUnitName(java.lang.String.valueOf((xmiElem \ "@unitName").text))
						modelElem.setVersion(java.lang.String.valueOf((xmiElem \ "@version").text))
						modelElem.setUrl(java.lang.String.valueOf((xmiElem \ "@url").text))
						modelElem.setHashcode(java.lang.String.valueOf((xmiElem \ "@hashcode").text))

						(xmiElem \ "@requiredLibs").headOption match {
								case Some(head) => {
										head.text.split(" ").foreach {
												xmiRef =>
														ContainerRootLoadContext.map.get(xmiRef) match {
																case Some(s: DeployUnit) => modelElem.addRequiredLibs(s)
																case None => System.out.println("DeployUnit not found in map ! xmiRef:" + xmiRef)
														}
												}
										}
								case None => //No subtype for this library
						}
						(xmiElem \ "@targetNodeType").headOption match {
								case Some(head) => {
										head.text.split(" ").foreach {
												xmiRef =>
														ContainerRootLoadContext.map.get(xmiRef) match {
																case Some(s: NodeType) => modelElem.setTargetNodeType(s)
																case None => System.out.println("NodeType not found in map ! xmiRef:" + xmiRef)
														}
												}
										}
								case None => //No subtype for this library
						}

						owner.addDeployUnits(modelElem)
						i += 1
				}
		}
}
