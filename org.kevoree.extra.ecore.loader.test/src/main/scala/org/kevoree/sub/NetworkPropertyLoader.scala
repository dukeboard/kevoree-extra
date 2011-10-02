package org.kevoree.sub;

import xml.NodeSeq
import scala.collection.JavaConversions._
import org.kevoree._


trait NetworkPropertyLoader
		extends  {

		def loadNetworkPropertys(parentId: String, parentNode: NodeSeq): Int = {
				val networkPropertiesList = (parentNode \\ "networkProperties")
				var i = 0
				networkPropertiesList.foreach { xmiElem =>
						val modelElem = KevoreePackage.createNetworkProperty
						ContainerRootLoadContext.map += parentId + "/@" + xmiElem.label + "." + i -> modelElem
						i += 1
				}
				i
		}
		def resolveNetworkPropertys(parentId: String, parentNode: NodeSeq, owner: NodeLink) {
				val networkPropertiesList = (parentNode \\ "networkProperties")
				var i = 0
				networkPropertiesList.foreach { xmiElem =>
						val modelElem = ContainerRootLoadContext.map(parentId + "/@" + xmiElem.label + "." + i).asInstanceOf[NetworkProperty]
						modelElem.eContainer = owner
						modelElem.setName(java.lang.String.valueOf((xmiElem \ "@name").text))
						modelElem.setValue(java.lang.String.valueOf((xmiElem \ "@value").text))
						modelElem.setLastCheck(java.lang.String.valueOf((xmiElem \ "@lastCheck").text))


						owner.addNetworkProperties(modelElem)
						i += 1
				}
		}
}
