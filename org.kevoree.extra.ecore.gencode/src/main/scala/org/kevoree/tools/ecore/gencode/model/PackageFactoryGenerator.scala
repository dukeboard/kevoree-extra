package org.kevoree.tools.ecore.gencode.model

import java.io.{File, FileOutputStream, PrintWriter}
import org.kevoree.tools.ecore.gencode.ProcessorHelper
import scala.collection.JavaConversions._
import org.eclipse.emf.ecore.{EClass, EPackage}

/**
 * Created by IntelliJ IDEA.
 * User: Gregory NAIN
 * Date: 23/09/11
 * Time: 13:35
 */

trait PackageFactoryGenerator {


    def generatePackageFactory(location: String, pack: String, packElement: EPackage) {
    var formatedFactoryName: String = packElement.getName.substring(0, 1).toUpperCase
    formatedFactoryName += packElement.getName.substring(1)
    formatedFactoryName += "Package"

    val pr = new PrintWriter(new FileOutputStream(new File(location + "/" + formatedFactoryName + ".scala")))


    pr.println("package " + pack + ";")
    pr.println()
    pr.println("import " + pack + ".impl._;")
    pr.println()
    pr.println(ProcessorHelper.generateHeader(packElement))
    //case class name
    pr.println("object " + formatedFactoryName + " {")
    pr.println()
    pr.println("\t def eInstance = " + formatedFactoryName)
    pr.println()
    packElement.getEClassifiers.filter(cls=>cls.isInstanceOf[EClass]).foreach {
      cls =>
        val methodName = "create" + cls.getName
        pr.println("\t def " + methodName + " : " + cls.getName + " = new " + cls.getName + "Impl")
    }
    pr.println()
    pr.println("}")

    pr.flush()
    pr.close()
  }


}