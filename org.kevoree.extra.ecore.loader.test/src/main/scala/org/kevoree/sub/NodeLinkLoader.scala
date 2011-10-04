package org.kevoree.sub;

import xml.NodeSeq
import scala.collection.JavaConversions._
import org.kevoree._

trait NodeLinkLoader extends NetworkPropertyLoader {

		def loadNodeLink(parentId: String, parentNode: NodeSeq, refNameInParent : String) : List[NodeLink] = {
				var loadedElements = List[NodeLink]()
				var i = 0
				val nodeLinkList = (parentNode \\ refNameInParent)
				nodeLinkList.foreach { xmiElem =>
						val currentElementId = parentId + "/@" + refNameInParent + "." + i
						loadedElements = loadedElements ++ List(loadNodeLinkElement(currentElementId,xmiElem))
						i += 1
				}
				loadedElements
		}

		def loadNodeLinkElement(elementId: String, elementNode: NodeSeq) : NodeLink = {
		
				val modelElem = KevoreeFactory.createNodeLink
				ContainerRootLoadContext.map += elementId -> modelElem

				val networkProperties = loadNetworkProperty(elementId, elementNode, "networkProperties")
				modelElem.setNetworkProperties(networkProperties)
				networkProperties.foreach{ e => e.eContainer = modelElem }

				modelElem
		}

		def resolveNodeLink(parentId: String, parentNode: NodeSeq, refNameInParent : String) {
				var i = 0
				val nodeLinkList = (parentNode \\ refNameInParent)
				nodeLinkList.foreach { xmiElem =>
						val currentElementId = parentId + "/@" + refNameInParent + "." + i
						resolveNodeLinkElement(currentElementId,xmiElem)
						i += 1
				}
		}

		def resolveNodeLinkElement(elementId: String, elementNode: NodeSeq) {

		val modelElem = ContainerRootLoadContext.map(elementId).asInstanceOf[NodeLink]

		val networkTypeVal = (elementNode \ "@networkType").text
		if(!networkTypeVal.equals("")){
				modelElem.setNetworkType(java.lang.String.valueOf(networkTypeVal))
		}

		val estimatedRateVal = (elementNode \ "@estimatedRate").text
		if(!estimatedRateVal.equals("")){
				modelElem.setEstimatedRate(java.lang.Integer.valueOf(estimatedRateVal))
		}

		val lastCheckVal = (elementNode \ "@lastCheck").text
		if(!lastCheckVal.equals("")){
				modelElem.setLastCheck(java.lang.String.valueOf(lastCheckVal))
		}


				resolveNetworkProperty(elementId, elementNode, "networkProperties")

		}

}
