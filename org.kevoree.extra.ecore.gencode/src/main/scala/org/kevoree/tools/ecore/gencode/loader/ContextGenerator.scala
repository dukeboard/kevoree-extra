package org.kevoree.tools.ecore.gencode.loader

import java.io.{File, FileOutputStream, PrintWriter}
import org.eclipse.emf.ecore.EClass

/**
 * Created by IntelliJ IDEA.
 * User: Gregory NAIN
 * Date: 25/09/11
 * Time: 15:25
 */

class ContextGenerator(genDir: String, genPackage: String, elementType: EClass, modelPackage : String) {


  def generateContext() {
     val pr = new PrintWriter(new FileOutputStream(new File(genDir + "/" + elementType.getName + "LoadContext.scala")))
    //System.out.println("Classifier class:" + cls.getClass)

    pr.println("package " + genPackage + ";")
    pr.println()
    pr.println("import xml.NodeSeq")
    pr.println("import " + modelPackage + "._")
    pr.println()

    pr.println("object " + elementType.getName + "LoadContext {")

    pr.println()
    pr.println("\t\tvar xmiContent : NodeSeq = null")
    pr.println()
    pr.println("\t\tvar "+elementType.getName.substring(0,1).toLowerCase + elementType.getName.substring(1)+" : "+elementType.getName+" = null")
    pr.println()
    pr.println("\t\tvar map : Map[String, Any] = null")
    pr.println()
    pr.println("\t\tvar stats : Map[String, Int] = null")
    pr.println()

    pr.println("}")

    pr.flush()
    pr.close()
  }

}