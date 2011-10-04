package org.kevoree.sub;

import xml.NodeSeq
import scala.collection.JavaConversions._
import org.kevoree._

trait ChannelLoader extends DictionaryLoader {

		def loadChannel(parentId: String, parentNode: NodeSeq, refNameInParent : String) : List[Channel] = {
				var loadedElements = List[Channel]()
				var i = 0
				val channelList = (parentNode \\ refNameInParent)
				channelList.foreach { xmiElem =>
						val currentElementId = parentId + "/@" + refNameInParent + "." + i
						loadedElements = loadedElements ++ List(loadChannelElement(currentElementId,xmiElem))
						i += 1
				}
				loadedElements
		}

		def loadChannelElement(elementId: String, elementNode: NodeSeq) : Channel = {
		
				val modelElem = KevoreeFactory.createChannel
				ContainerRootLoadContext.map += elementId -> modelElem

				(elementNode \ "dictionary").headOption.map{head =>
						val dictionaryElementId = elementId + "/@dictionary"
						val dictionary = loadDictionaryElement(dictionaryElementId, head)
						modelElem.setDictionary(dictionary)
						dictionary.eContainer = modelElem
				}

				modelElem
		}

		def resolveChannel(parentId: String, parentNode: NodeSeq, refNameInParent : String) {
				var i = 0
				val channelList = (parentNode \\ refNameInParent)
				channelList.foreach { xmiElem =>
						val currentElementId = parentId + "/@" + refNameInParent + "." + i
						resolveChannelElement(currentElementId,xmiElem)
						i += 1
				}
		}

		def resolveChannelElement(elementId: String, elementNode: NodeSeq) {

		val modelElem = ContainerRootLoadContext.map(elementId).asInstanceOf[Channel]

		val nameVal = (elementNode \ "@name").text
		if(!nameVal.equals("")){
				modelElem.setName(java.lang.String.valueOf(nameVal))
		}

		val metaDataVal = (elementNode \ "@metaData").text
		if(!metaDataVal.equals("")){
				modelElem.setMetaData(java.lang.String.valueOf(metaDataVal))
		}


				(elementNode \ "@dictionary").headOption.map{head => 
						resolveDictionaryElement(elementId + "/@dictionary", head)
				}

				(elementNode \ "@typeDefinition").headOption match {
						case Some(head) => {
								head.text.split(" ").foreach {
										xmiRef =>
												ContainerRootLoadContext.map.get(xmiRef) match {
														case Some(s: TypeDefinition) => modelElem.setTypeDefinition(s)
														case None => System.out.println("TypeDefinition not found in map ! xmiRef:" + xmiRef)
												}
										}
								}
						case None => //No subtype for this library
				}
		}

}
