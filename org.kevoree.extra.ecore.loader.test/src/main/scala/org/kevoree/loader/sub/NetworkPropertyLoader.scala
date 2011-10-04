package org.kevoree.loader.sub;

import xml.NodeSeq
import scala.collection.JavaConversions._
import org.kevoree._
import org.kevoree.loader._

trait NetworkPropertyLoader{

		def loadNetworkProperty(parentId: String, parentNode: NodeSeq, refNameInParent : String) : List[NetworkProperty] = {
				var loadedElements = List[NetworkProperty]()
				var i = 0
				val networkPropertyList = (parentNode \\ refNameInParent)
				networkPropertyList.foreach { xmiElem =>
						val currentElementId = parentId + "/@" + refNameInParent + "." + i
						loadedElements = loadedElements ++ List(loadNetworkPropertyElement(currentElementId,xmiElem))
						i += 1
				}
				loadedElements
		}

		def loadNetworkPropertyElement(elementId: String, elementNode: NodeSeq) : NetworkProperty = {
		
				val modelElem = KevoreeFactory.createNetworkProperty
				ContainerRootLoadContext.map += elementId -> modelElem

				modelElem
		}

		def resolveNetworkProperty(parentId: String, parentNode: NodeSeq, refNameInParent : String) {
				var i = 0
				val networkPropertyList = (parentNode \\ refNameInParent)
				networkPropertyList.foreach { xmiElem =>
						val currentElementId = parentId + "/@" + refNameInParent + "." + i
						resolveNetworkPropertyElement(currentElementId,xmiElem)
						i += 1
				}
		}

		def resolveNetworkPropertyElement(elementId: String, elementNode: NodeSeq) {

				val modelElem = ContainerRootLoadContext.map(elementId).asInstanceOf[NetworkProperty]

				val nameVal = (elementNode \ "@name").text
				if(!nameVal.equals("")){
						modelElem.setName(java.lang.String.valueOf(nameVal))
				}

				val valueVal = (elementNode \ "@value").text
				if(!valueVal.equals("")){
						modelElem.setValue(java.lang.String.valueOf(valueVal))
				}

				val lastCheckVal = (elementNode \ "@lastCheck").text
				if(!lastCheckVal.equals("")){
						modelElem.setLastCheck(java.lang.String.valueOf(lastCheckVal))
				}


		}

}
