package org.kevoree.loader.sub;

import xml.NodeSeq
import scala.collection.JavaConversions._
import org.kevoree._
import org.kevoree.loader._

trait PortTypeRefLoader extends PortTypeMappingLoader {

		def loadPortTypeRef(parentId: String, parentNode: NodeSeq, refNameInParent : String) : List[PortTypeRef] = {
				var loadedElements = List[PortTypeRef]()
				var i = 0
				val portTypeRefList = (parentNode \\ refNameInParent)
				portTypeRefList.foreach { xmiElem =>
						val currentElementId = parentId + "/@" + refNameInParent + "." + i
						loadedElements = loadedElements ++ List(loadPortTypeRefElement(currentElementId,xmiElem))
						i += 1
				}
				loadedElements
		}

		def loadPortTypeRefElement(elementId: String, elementNode: NodeSeq) : PortTypeRef = {
		
				val modelElem = KevoreeFactory.createPortTypeRef
				ContainerRootLoadContext.map += elementId -> modelElem

				val mappings = loadPortTypeMapping(elementId, elementNode, "mappings")
				modelElem.setMappings(mappings)

				modelElem
		}

		def resolvePortTypeRef(parentId: String, parentNode: NodeSeq, refNameInParent : String) {
				var i = 0
				val portTypeRefList = (parentNode \\ refNameInParent)
				portTypeRefList.foreach { xmiElem =>
						val currentElementId = parentId + "/@" + refNameInParent + "." + i
						resolvePortTypeRefElement(currentElementId,xmiElem)
						i += 1
				}
		}

		def resolvePortTypeRefElement(elementId: String, elementNode: NodeSeq) {

				val modelElem = ContainerRootLoadContext.map(elementId).asInstanceOf[PortTypeRef]

				val nameVal = (elementNode \ "@name").text
				if(!nameVal.equals("")){
						modelElem.setName(java.lang.String.valueOf(nameVal))
				}

				val optionalVal = (elementNode \ "@optional").text
				if(!optionalVal.equals("")){
						modelElem.setOptional(java.lang.Boolean.valueOf(optionalVal))
				}

				val noDependencyVal = (elementNode \ "@noDependency").text
				if(!noDependencyVal.equals("")){
						modelElem.setNoDependency(java.lang.Boolean.valueOf(noDependencyVal))
				}


				resolvePortTypeMapping(elementId, elementNode, "mappings")

				(elementNode \ "@ref").headOption match {
						case Some(head) => {
								head.text.split(" ").foreach {
										xmiRef =>
												ContainerRootLoadContext.map.get(xmiRef) match {
														case Some(s: PortType) => modelElem.setRef(s)
														case None => System.out.println("PortType not found in map ! xmiRef:" + xmiRef)
												}
										}
								}
						case None => //No subtype for this library
				}
		}

}
