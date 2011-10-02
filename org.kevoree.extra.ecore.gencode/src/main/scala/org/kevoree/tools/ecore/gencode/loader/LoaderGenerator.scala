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

  def getConcreteSubTypes(iface : EClass) : List[EClass] = {
    var res = List[EClass]()
    rootXmiPackage.getEClassifiers.filter(cl => cl.isInstanceOf[EClass]).foreach{cls=>
      if(!cls.asInstanceOf[EClass].isInterface
        && !cls.asInstanceOf[EClass].isAbstract
        && cls.asInstanceOf[EClass].getEAllSuperTypes.contains(iface)) {
        res = res ++ List(cls.asInstanceOf[EClass])
      }
    }
    res
  }
}

class LoaderGenerator(location: String, rootPackage: String, rootXmiPackage: EPackage) {

  LoaderGenerator.rootXmiPackage = rootXmiPackage

  def generateLoader() {
    ProcessorHelper.lookForRootElement(rootXmiPackage) match {
      case cls : EClass => {
        val el = new RootLoader(location+ "/"+ rootXmiPackage.getName, rootPackage + "."+ rootXmiPackage.getName, rootXmiPackage.getName+ ":" + cls.getName, cls,rootXmiPackage)
        el.generateLoader()
      }
      case _@e => throw new UnsupportedOperationException("Root container not found. Returned:" + e)
    }
  }



}