package org.kevoree.loader.sub;

import xml.NodeSeq
import scala.collection.JavaConversions._
import org.kevoree._
import org.kevoree.loader._

trait TypeDefinitionLoader extends ComponentTypeLoader with CompositeTypeLoader with ServicePortTypeLoader with MessagePortTypeLoader with ChannelTypeLoader with GroupTypeLoader with NodeTypeLoader {

		def loadTypeDefinition(parentId : String, parentNode : NodeSeq, refNameInParent : String) : List[TypeDefinition] = {
				var loadedElements = List[TypeDefinition]()
				var i = 0
				val typeDefinitionList = (parentNode \\ refNameInParent)
				typeDefinitionList.foreach { xmiElem =>
				val currentElementId = parentId + "/@" + refNameInParent + "." + i
						xmiElem.attributes.find(att => att.key.equals("type")) match {
								case Some(s) => {
												s.value.text match {
												case "kevoree:ComponentType" => {
														loadedElements = loadedElements ++ List(loadComponentTypeElement(currentElementId,xmiElem))
													}
												case "kevoree:CompositeType" => {
														loadedElements = loadedElements ++ List(loadCompositeTypeElement(currentElementId,xmiElem))
													}
												case "kevoree:ServicePortType" => {
														loadedElements = loadedElements ++ List(loadServicePortTypeElement(currentElementId,xmiElem))
													}
												case "kevoree:MessagePortType" => {
														loadedElements = loadedElements ++ List(loadMessagePortTypeElement(currentElementId,xmiElem))
													}
												case "kevoree:ChannelType" => {
														loadedElements = loadedElements ++ List(loadChannelTypeElement(currentElementId,xmiElem))
													}
												case "kevoree:GroupType" => {
														loadedElements = loadedElements ++ List(loadGroupTypeElement(currentElementId,xmiElem))
													}
												case "kevoree:NodeType" => {
														loadedElements = loadedElements ++ List(loadNodeTypeElement(currentElementId,xmiElem))
													}
												case _@e => throw new UnsupportedOperationException("Processor for TypeDefinitions has no mapping for type:" + e)
										}
								}
								case None => System.out.println("No 'type' attribute.")
						}
						i += 1
				}
				loadedElements
		}

		def resolveTypeDefinition(parentId : String, parentNode : NodeSeq, refNameInParent : String) {
				var i = 0
				val typeDefinitionList = (parentNode \\ refNameInParent)
				typeDefinitionList.foreach { xmiElem =>
						val currentElementId = parentId + "/@" + refNameInParent + "." + i
						xmiElem.attributes.find(att => att.key.equals("type")) match {
										case Some(s) => {
												s.value.text match {
												case "kevoree:ComponentType" => {
														resolveComponentTypeElement(currentElementId,xmiElem)
													}
												case "kevoree:CompositeType" => {
														resolveCompositeTypeElement(currentElementId,xmiElem)
													}
												case "kevoree:ServicePortType" => {
														resolveServicePortTypeElement(currentElementId,xmiElem)
													}
												case "kevoree:MessagePortType" => {
														resolveMessagePortTypeElement(currentElementId,xmiElem)
													}
												case "kevoree:ChannelType" => {
														resolveChannelTypeElement(currentElementId,xmiElem)
													}
												case "kevoree:GroupType" => {
														resolveGroupTypeElement(currentElementId,xmiElem)
													}
												case "kevoree:NodeType" => {
														resolveNodeTypeElement(currentElementId,xmiElem)
													}
												case _@e => throw new UnsupportedOperationException("Processor for TypeDefinitions has no mapping for type:" + e)
										}
								}
								case None => System.out.println("No 'type' attribute.")
						}
						i += 1
				}
		}

}
