package org.kevoree.sub;

import xml.NodeSeq
import scala.collection.JavaConversions._
import org.kevoree._

trait PortTypeMappingLoader{

		def loadPortTypeMapping(parentId: String, parentNode: NodeSeq, refNameInParent : String) : List[PortTypeMapping] = {
				var loadedElements = List[PortTypeMapping]()
				var i = 0
				val portTypeMappingList = (parentNode \\ refNameInParent)
				portTypeMappingList.foreach { xmiElem =>
						val currentElementId = parentId + "/@" + refNameInParent + "." + i
						loadedElements = loadedElements ++ List(loadPortTypeMappingElement(currentElementId,xmiElem))
						i += 1
				}
				loadedElements
		}

		def loadPortTypeMappingElement(elementId: String, elementNode: NodeSeq) : PortTypeMapping = {
		
				val modelElem = KevoreePackage.createPortTypeMapping
				ContainerRootLoadContext.map += elementId -> modelElem

				modelElem
		}

		def resolvePortTypeMapping(parentId: String, parentNode: NodeSeq, refNameInParent : String) {
				var i = 0
				val portTypeMappingList = (parentNode \\ refNameInParent)
				portTypeMappingList.foreach { xmiElem =>
						val currentElementId = parentId + "/@" + refNameInParent + "." + i
						resolvePortTypeMappingElement(currentElementId,xmiElem)
						i += 1
				}
		}

		def resolvePortTypeMappingElement(elementId: String, elementNode: NodeSeq) {

		val modelElem = ContainerRootLoadContext.map(elementId).asInstanceOf[PortTypeMapping]

		val beanMethodNameVal = (elementNode \\ "@beanMethodName").text
		if(!beanMethodNameVal.equals("")){
				modelElem.setBeanMethodName(java.lang.String.valueOf(beanMethodNameVal))
		}

		val serviceMethodNameVal = (elementNode \\ "@serviceMethodName").text
		if(!serviceMethodNameVal.equals("")){
				modelElem.setServiceMethodName(java.lang.String.valueOf(serviceMethodNameVal))
		}


		}

}
