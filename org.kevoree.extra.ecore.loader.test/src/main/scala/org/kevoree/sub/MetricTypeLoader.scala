package org.kevoree.sub;

import xml.NodeSeq
import scala.collection.JavaConversions._
import org.kevoree._


trait MetricTypeLoader
		extends  {

		def loadMetricTypes(parentId: String, parentNode: NodeSeq): Int = {
				val metricTypesList = (parentNode \\ "metricTypes")
				var i = 0
				metricTypesList.foreach { xmiElem =>
						val modelElem = KevoreePackage.createMetricType
						ContainerRootLoadContext.map += parentId + "/@" + xmiElem.label + "." + i -> modelElem
						i += 1
				}
				i
		}
		def resolveMetricTypes(parentId: String, parentNode: NodeSeq, owner: ContainerRoot) {
				val metricTypesList = (parentNode \\ "metricTypes")
				var i = 0
				metricTypesList.foreach { xmiElem =>
						val modelElem = ContainerRootLoadContext.map(parentId + "/@" + xmiElem.label + "." + i).asInstanceOf[MetricType]
						modelElem.eContainer = owner
						modelElem.setName(java.lang.String.valueOf((xmiElem \ "@name").text))


						owner.addMetricTypes(modelElem)
						i += 1
				}
		}
}
