package org.kevoree.sub;

import xml.NodeSeq
import scala.collection.JavaConversions._
import org.kevoree._

trait DeployUnitLoader{

		def loadDeployUnit(parentId: String, parentNode: NodeSeq, refNameInParent : String) : List[DeployUnit] = {
				var loadedElements = List[DeployUnit]()
				var i = 0
				val deployUnitList = (parentNode \\ refNameInParent)
				deployUnitList.foreach { xmiElem =>
						val currentElementId = parentId + "/@" + refNameInParent + "." + i
						loadedElements = loadedElements ++ List(loadDeployUnitElement(currentElementId,xmiElem))
						i += 1
				}
				loadedElements
		}

		def loadDeployUnitElement(elementId: String, elementNode: NodeSeq) : DeployUnit = {
		
				val modelElem = KevoreePackage.createDeployUnit
				ContainerRootLoadContext.map += elementId -> modelElem

				modelElem
		}

		def resolveDeployUnit(parentId: String, parentNode: NodeSeq, refNameInParent : String) {
				var i = 0
				val deployUnitList = (parentNode \\ refNameInParent)
				deployUnitList.foreach { xmiElem =>
						val currentElementId = parentId + "/@" + refNameInParent + "." + i
						resolveDeployUnitElement(currentElementId,xmiElem)
						i += 1
				}
		}

		def resolveDeployUnitElement(elementId: String, elementNode: NodeSeq) {

		val modelElem = ContainerRootLoadContext.map(elementId).asInstanceOf[DeployUnit]

		val nameVal = (elementNode \\ "@name").text
		if(!nameVal.equals("")){
				modelElem.setName(java.lang.String.valueOf(nameVal))
		}

		val groupNameVal = (elementNode \\ "@groupName").text
		if(!groupNameVal.equals("")){
				modelElem.setGroupName(java.lang.String.valueOf(groupNameVal))
		}

		val unitNameVal = (elementNode \\ "@unitName").text
		if(!unitNameVal.equals("")){
				modelElem.setUnitName(java.lang.String.valueOf(unitNameVal))
		}

		val versionVal = (elementNode \\ "@version").text
		if(!versionVal.equals("")){
				modelElem.setVersion(java.lang.String.valueOf(versionVal))
		}

		val urlVal = (elementNode \\ "@url").text
		if(!urlVal.equals("")){
				modelElem.setUrl(java.lang.String.valueOf(urlVal))
		}

		val hashcodeVal = (elementNode \\ "@hashcode").text
		if(!hashcodeVal.equals("")){
				modelElem.setHashcode(java.lang.String.valueOf(hashcodeVal))
		}


				(elementNode \ "@requiredLibs").headOption match {
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
				(elementNode \ "@targetNodeType").headOption match {
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
		}

}
