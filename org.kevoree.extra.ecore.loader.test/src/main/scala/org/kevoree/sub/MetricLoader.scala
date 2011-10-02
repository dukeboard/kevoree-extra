package org.kevoree.sub;

import xml.NodeSeq
import scala.collection.JavaConversions._
import org.kevoree._


trait MetricLoader
		extends MetricValue {

		def loadMetrics(parentId: String, parentNode: NodeSeq): Int = {
				val metricsList = (parentNode \\ "metrics")
				var i = 0
				metricsList.foreach { xmiElem =>
						val modelElem = KevoreePackage.createMetric
						ContainerRootLoadContext.map += parentId + "/@" + xmiElem.label + "." + i -> modelElem
						i += 1
				}
				i
		}
		def resolveMetrics(parentId: String, parentNode: NodeSeq, owner: ContainerRoot) {
				val metricsList = (parentNode \\ "metrics")
				var i = 0
				metricsList.foreach { xmiElem =>
						val modelElem = ContainerRootLoadContext.map(parentId + "/@" + xmiElem.label + "." + i).asInstanceOf[Metric]
						modelElem.eContainer = owner
						modelElem.setNbValueMax(java.lang.Integer.valueOf((xmiElem \ "@nbValueMax").text))

						(xmiElem \ "@values").headOption match {
								case Some(head) => {
										head.text.split(" ").foreach {
												xmiRef =>
														ContainerRootLoadContext.map.get(xmiRef) match {
																case Some(s: MetricValue) => modelElem.addValues(s)
																case None => System.out.println("MetricValue not found in map ! xmiRef:" + xmiRef)
														}
												}
										}
								case None => //No subtype for this library
						}
						(xmiElem \ "@type").headOption match {
								case Some(head) => {
										head.text.split(" ").foreach {
												xmiRef =>
														ContainerRootLoadContext.map.get(xmiRef) match {
																case Some(s: MetricType) => modelElem.setType(s)
																case None => System.out.println("MetricType not found in map ! xmiRef:" + xmiRef)
														}
												}
										}
								case None => //No subtype for this library
						}

						owner.addMetrics(modelElem)
						i += 1
				}
		}
}
