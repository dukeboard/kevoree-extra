package org.kevoree.sub;

import xml.NodeSeq
import scala.collection.JavaConversions._
import org.kevoree._


trait MetricValueLoader
		extends  {

		def loadMetricValues(parentId: String, parentNode: NodeSeq): Int = {
				val valuesList = (parentNode \\ "values")
				var i = 0
				valuesList.foreach { xmiElem =>
						val modelElem = KevoreePackage.createMetricValue
						ContainerRootLoadContext.map += parentId + "/@" + xmiElem.label + "." + i -> modelElem
						i += 1
				}
				i
		}
		def resolveMetricValues(parentId: String, parentNode: NodeSeq, owner: Metric) {
				val valuesList = (parentNode \\ "values")
				var i = 0
				valuesList.foreach { xmiElem =>
						val modelElem = ContainerRootLoadContext.map(parentId + "/@" + xmiElem.label + "." + i).asInstanceOf[MetricValue]
						modelElem.eContainer = owner


						owner.addValues(modelElem)
						i += 1
				}
		}
}
