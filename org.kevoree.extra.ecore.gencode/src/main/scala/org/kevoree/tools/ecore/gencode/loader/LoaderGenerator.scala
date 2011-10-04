package org.kevoree.tools.ecore.gencode.loader

//EClass, EClassifier,

import scala.collection.JavaConversions._
import org.eclipse.emf.ecore.{EClassifier, EClass, EPackage}
import collection.mutable.Buffer
import org.kevoree.tools.ecore.gencode.ProcessorHelper

/**
 * Created by IntelliJ IDEA.
 * User: Gregory NAIN
 * Date: 24/09/11
 * Time: 18:09
 */

object LoaderGenerator {
  var rootXmiPackage : EPackage = null
}

class LoaderGenerator(location: String, rootPackage: String, rootXmiPackage: EPackage) {

  LoaderGenerator.rootXmiPackage = rootXmiPackage

  def generateLoader() {
    ProcessorHelper.lookForRootElement(rootXmiPackage) match {
      case cls : EClass => {
        val el = new RootLoader(location+ "/"+ rootXmiPackage.getName + "/loader", rootPackage + "."+ rootXmiPackage.getName + ".loader", rootXmiPackage.getName+ ":" + cls.getName, cls, rootXmiPackage, rootPackage)
        el.generateLoader()
      }
      case _@e => throw new UnsupportedOperationException("Root container not found. Returned:" + e)
    }
  }



}