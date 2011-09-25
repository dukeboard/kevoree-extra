package org.kevoree.extra.model.loader.processor

import xml.NodeSeq
import org.kevoree.{KevoreeContainer, KevoreePackage, ContainerRoot}

/**
 * Created by IntelliJ IDEA.
 * User: Gregory NAIN
 * Date: 24/09/11
 * Time: 16:39
 */

object ProcessingContext {

  var xmiContent : NodeSeq = null

  var containerRoot : ContainerRoot = null

  var map : Map[String, Any] = null

}