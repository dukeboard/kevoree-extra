package org.kevoree.sub;

import xml.NodeSeq
import scala.collection.JavaConversions._
import org.kevoree._

trait RepositoryLoader{

		def loadRepository(parentId: String, parentNode: NodeSeq, refNameInParent : String) : List[Repository] = {
				var loadedElements = List[Repository]()
				var i = 0
				val repositoryList = (parentNode \\ refNameInParent)
				repositoryList.foreach { xmiElem =>
						val currentElementId = parentId + "/@" + refNameInParent + "." + i
						loadedElements = loadedElements ++ List(loadRepositoryElement(currentElementId,xmiElem))
						i += 1
				}
				loadedElements
		}

		def loadRepositoryElement(elementId: String, elementNode: NodeSeq) : Repository = {
		
				val modelElem = KevoreeFactory.createRepository
				ContainerRootLoadContext.map += elementId -> modelElem

				modelElem
		}

		def resolveRepository(parentId: String, parentNode: NodeSeq, refNameInParent : String) {
				var i = 0
				val repositoryList = (parentNode \\ refNameInParent)
				repositoryList.foreach { xmiElem =>
						val currentElementId = parentId + "/@" + refNameInParent + "." + i
						resolveRepositoryElement(currentElementId,xmiElem)
						i += 1
				}
		}

		def resolveRepositoryElement(elementId: String, elementNode: NodeSeq) {

		val modelElem = ContainerRootLoadContext.map(elementId).asInstanceOf[Repository]

		val nameVal = (elementNode \ "@name").text
		if(!nameVal.equals("")){
				modelElem.setName(java.lang.String.valueOf(nameVal))
		}

		val urlVal = (elementNode \ "@url").text
		if(!urlVal.equals("")){
				modelElem.setUrl(java.lang.String.valueOf(urlVal))
		}


				(elementNode \ "@units").headOption match {
						case Some(head) => {
								head.text.split(" ").foreach {
										xmiRef =>
												ContainerRootLoadContext.map.get(xmiRef) match {
														case Some(s: DeployUnit) => modelElem.addUnits(s)
														case None => System.out.println("DeployUnit not found in map ! xmiRef:" + xmiRef)
												}
										}
								}
						case None => //No subtype for this library
				}
		}

}
