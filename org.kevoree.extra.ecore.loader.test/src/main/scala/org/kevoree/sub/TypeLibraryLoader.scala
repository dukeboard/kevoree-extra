package org.kevoree.sub;

import xml.NodeSeq
import scala.collection.JavaConversions._
import org.kevoree._


trait TypeLibraryLoader
		extends  {

		def loadTypeLibrarys(parentId: String, parentNode: NodeSeq): Int = {
				val librariesList = (parentNode \\ "libraries")
				var i = 0
				librariesList.foreach { xmiElem =>
						val modelElem = KevoreePackage.createTypeLibrary
						ContainerRootLoadContext.map += parentId + "/@" + xmiElem.label + "." + i -> modelElem
						i += 1
				}
				i
		}
		def resolveTypeLibrarys(parentId: String, parentNode: NodeSeq, owner: ContainerRoot) {
				val librariesList = (parentNode \\ "libraries")
				var i = 0
				librariesList.foreach { xmiElem =>
						val modelElem = ContainerRootLoadContext.map(parentId + "/@" + xmiElem.label + "." + i).asInstanceOf[TypeLibrary]
						modelElem.eContainer = owner
						modelElem.setName(java.lang.String.valueOf((xmiElem \ "@name").text))

						(xmiElem \ "@subTypes").headOption match {
								case Some(head) => {
										head.text.split(" ").foreach {
												xmiRef =>
														ContainerRootLoadContext.map.get(xmiRef) match {
																case Some(s: TypeDefinition) => modelElem.addSubTypes(s)
																case None => System.out.println("TypeDefinition not found in map ! xmiRef:" + xmiRef)
														}
												}
										}
								case None => //No subtype for this library
						}

						owner.addLibraries(modelElem)
						i += 1
				}
		}
}
