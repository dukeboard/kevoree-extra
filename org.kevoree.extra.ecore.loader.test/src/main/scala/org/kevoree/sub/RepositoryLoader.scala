package org.kevoree.sub;

import xml.NodeSeq
import scala.collection.JavaConversions._
import org.kevoree._


trait RepositoryLoader
		extends  {

		def loadRepositorys(parentId: String, parentNode: NodeSeq): Int = {
				val repositoriesList = (parentNode \\ "repositories")
				var i = 0
				repositoriesList.foreach { xmiElem =>
						val modelElem = KevoreePackage.createRepository
						ContainerRootLoadContext.map += parentId + "/@" + xmiElem.label + "." + i -> modelElem
						i += 1
				}
				i
		}
		def resolveRepositorys(parentId: String, parentNode: NodeSeq, owner: ContainerRoot) {
				val repositoriesList = (parentNode \\ "repositories")
				var i = 0
				repositoriesList.foreach { xmiElem =>
						val modelElem = ContainerRootLoadContext.map(parentId + "/@" + xmiElem.label + "." + i).asInstanceOf[Repository]
						modelElem.eContainer = owner
						modelElem.setName(java.lang.String.valueOf((xmiElem \ "@name").text))
						modelElem.setUrl(java.lang.String.valueOf((xmiElem \ "@url").text))

						(xmiElem \ "@units").headOption match {
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

						owner.addRepositories(modelElem)
						i += 1
				}
		}
}
