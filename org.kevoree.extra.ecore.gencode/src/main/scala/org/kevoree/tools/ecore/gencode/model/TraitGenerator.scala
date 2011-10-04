package org.kevoree.tools.ecore.gencode.model

import org.eclipse.emf.ecore.EPackage
import java.io.{File, FileOutputStream, PrintWriter}
import org.kevoree.tools.ecore.gencode.ProcessorHelper

/**
 * Created by IntelliJ IDEA.
 * User: Gregory NAIN
 * Date: 23/09/11
 * Time: 13:35
 */

trait TraitGenerator {


  def generateContainerTrait(location: String, pack: String, packElement: EPackage) {
    var formatedFactoryName: String = packElement.getName.substring(0, 1).toUpperCase
    formatedFactoryName += packElement.getName.substring(1)
    formatedFactoryName += "Container"

    val pr = new PrintWriter(new FileOutputStream(new File(location + "/" + formatedFactoryName + ".scala")))


    pr.println("package " + pack + ";")
    pr.println()
    //pr.println("import " + pack + ".;")
    pr.println()

    pr.println(ProcessorHelper.generateHeader(packElement))

    //case class name
    pr.println("trait " + formatedFactoryName + " {")
    pr.println()
    //pr.println("\t var eContainer : " + formatedFactoryName + " = null")
    pr.println("\t var eContainer : Any = null")
    pr.println()
    //generate setter
    pr.print("\n\t\tdef setEContainer( container : " + formatedFactoryName + ") {\n")
    pr.println("\t\t\t\tthis.eContainer = container\n\t\t}")

    pr.println("}")

    pr.flush()
    pr.close()
  }

}