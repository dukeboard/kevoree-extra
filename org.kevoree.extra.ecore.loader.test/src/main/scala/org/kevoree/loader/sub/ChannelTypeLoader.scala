package org.kevoree.loader.sub;

import xml.NodeSeq
import scala.collection.JavaConversions._
import org.kevoree._
import org.kevoree.loader._

trait ChannelTypeLoader extends DictionaryTypeLoader {

		def loadChannelType(parentId: String, parentNode: NodeSeq, refNameInParent : String) : List[ChannelType] = {
				var loadedElements = List[ChannelType]()
				var i = 0
				val channelTypeList = (parentNode \\ refNameInParent)
				channelTypeList.foreach { xmiElem =>
						val currentElementId = parentId + "/@" + refNameInParent + "." + i
						loadedElements = loadedElements ++ List(loadChannelTypeElement(currentElementId,xmiElem))
						i += 1
				}
				loadedElements
		}

		def loadChannelTypeElement(elementId: String, elementNode: NodeSeq) : ChannelType = {
		
				val modelElem = KevoreeFactory.createChannelType
				ContainerRootLoadContext.map += elementId -> modelElem

				(elementNode \ "dictionaryType").headOption.map{head =>
						val dictionaryTypeElementId = elementId + "/@dictionaryType"
						val dictionaryType = loadDictionaryTypeElement(dictionaryTypeElementId, head)
						modelElem.setDictionaryType(dictionaryType)
				}

				modelElem
		}

		def resolveChannelType(parentId: String, parentNode: NodeSeq, refNameInParent : String) {
				var i = 0
				val channelTypeList = (parentNode \\ refNameInParent)
				channelTypeList.foreach { xmiElem =>
						val currentElementId = parentId + "/@" + refNameInParent + "." + i
						resolveChannelTypeElement(currentElementId,xmiElem)
						i += 1
				}
		}

		def resolveChannelTypeElement(elementId: String, elementNode: NodeSeq) {

				val modelElem = ContainerRootLoadContext.map(elementId).asInstanceOf[ChannelType]

				val nameVal = (elementNode \ "@name").text
				if(!nameVal.equals("")){
						modelElem.setName(java.lang.String.valueOf(nameVal))
				}

				val factoryBeanVal = (elementNode \ "@factoryBean").text
				if(!factoryBeanVal.equals("")){
						modelElem.setFactoryBean(java.lang.String.valueOf(factoryBeanVal))
				}

				val beanVal = (elementNode \ "@bean").text
				if(!beanVal.equals("")){
						modelElem.setBean(java.lang.String.valueOf(beanVal))
				}

				val startMethodVal = (elementNode \ "@startMethod").text
				if(!startMethodVal.equals("")){
						modelElem.setStartMethod(java.lang.String.valueOf(startMethodVal))
				}

				val stopMethodVal = (elementNode \ "@stopMethod").text
				if(!stopMethodVal.equals("")){
						modelElem.setStopMethod(java.lang.String.valueOf(stopMethodVal))
				}

				val updateMethodVal = (elementNode \ "@updateMethod").text
				if(!updateMethodVal.equals("")){
						modelElem.setUpdateMethod(java.lang.String.valueOf(updateMethodVal))
				}

				val lowerBindingsVal = (elementNode \ "@lowerBindings").text
				if(!lowerBindingsVal.equals("")){
						modelElem.setLowerBindings(java.lang.Integer.valueOf(lowerBindingsVal))
				}

				val upperBindingsVal = (elementNode \ "@upperBindings").text
				if(!upperBindingsVal.equals("")){
						modelElem.setUpperBindings(java.lang.Integer.valueOf(upperBindingsVal))
				}

				val lowerFragmentsVal = (elementNode \ "@lowerFragments").text
				if(!lowerFragmentsVal.equals("")){
						modelElem.setLowerFragments(java.lang.Integer.valueOf(lowerFragmentsVal))
				}

				val upperFragmentsVal = (elementNode \ "@upperFragments").text
				if(!upperFragmentsVal.equals("")){
						modelElem.setUpperFragments(java.lang.Integer.valueOf(upperFragmentsVal))
				}


				(elementNode \ "dictionaryType").headOption.map{head => 
						resolveDictionaryTypeElement(elementId + "/@dictionaryType", head)
				}

				(elementNode \ "@deployUnits").headOption match {
						case Some(head) => {
								head.text.split(" ").foreach {
										xmiRef =>
												ContainerRootLoadContext.map.get(xmiRef) match {
														case Some(s: DeployUnit) => modelElem.addDeployUnits(s)
														case None => System.out.println("DeployUnit not found in map ! xmiRef:" + xmiRef)
												}
										}
								}
						case None => //No subtype for this library
				}
				(elementNode \ "@superTypes").headOption match {
						case Some(head) => {
								head.text.split(" ").foreach {
										xmiRef =>
												ContainerRootLoadContext.map.get(xmiRef) match {
														case Some(s: TypeDefinition) => modelElem.addSuperTypes(s)
														case None => System.out.println("TypeDefinition not found in map ! xmiRef:" + xmiRef)
												}
										}
								}
						case None => //No subtype for this library
				}
		}

}
