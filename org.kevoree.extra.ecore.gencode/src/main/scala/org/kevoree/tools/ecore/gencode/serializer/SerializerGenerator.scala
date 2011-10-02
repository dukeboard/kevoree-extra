package org.kevoree.tools.ecore.gencode.serializer

import scala.collection.JavaConversions._
import org.kevoree.tools.ecore.gencode.ProcessorHelper
import java.io.{File, FileOutputStream, PrintWriter}
import org.eclipse.emf.ecore.{EPackage, EClass}
import org.kevoree.tools.ecore.gencode.loader.RootLoader

/**
 * Created by IntelliJ IDEA.
 * User: duke
 * Date: 02/10/11
 * Time: 20:55
 * To change this template use File | Settings | File Templates.
 */

class SerializerGenerator(location: String, rootPackage: String, rootXmiPackage: EPackage) {

  def generateSerializer() {
    ProcessorHelper.lookForRootElement(rootXmiPackage) match {
      case cls: EClass => {
        generateSerializer(location + "/" + rootXmiPackage.getName, rootPackage + "." + rootXmiPackage.getName, rootXmiPackage.getName + ":" + cls.getName, cls, rootXmiPackage)
      }
      case _@e => throw new UnsupportedOperationException("Root container not found. Returned:" + e)
    }
  }


  def generateSerializer(genDir: String, packageName: String, refNameInParent: String, root: EClass, rootXmiPackage: EPackage) : Unit = {

    ProcessorHelper.checkOrCreateFolder(genDir+"/serializer")

    //PROCESS SELF
    val pr = new PrintWriter(new FileOutputStream(new File(genDir + "/serializer/" + root.getName + "Serializer.scala")))
    pr.println("package "+packageName+".serializer")
    generateToXmiMethod(root, pr)
    pr.flush()
    pr.close()

    //PROCESS SUB
    root.getEAllContainments.foreach {
      sub =>
        val subpr = new PrintWriter(new FileOutputStream(new File(genDir + "/serializer/" + sub.getEReferenceType.getName + "Serializer.scala")))
        subpr.println("package "+packageName+".serializer")
        generateToXmiMethod(sub.getEReferenceType, subpr)
        subpr.flush()
        subpr.close()

        generateSerializer(genDir,packageName,refNameInParent,sub.getEReferenceType,rootXmiPackage)

    }


  }

  private def getGetter(name: String): String = {
    "get" + name.charAt(0).toUpper + name.substring(1)
  }

  private def generateToXmiMethod(cls: EClass, buffer: PrintWriter) = {
    buffer.println("import org.kevoree._")

    buffer.println("trait " + cls.getName + "Serializer ")

    val subTraits = cls.getEAllContainments.map(sub => sub.getEReferenceType.getName + "Serializer")

    if (subTraits.size >= 1) {
      buffer.print(subTraits.mkString(" extends ", " with ", " "))
    }

    buffer.println("{")

    buffer.println("def " + cls.getName + "toXmi(selfObject : " + cls.getName + ",refNameInParent : String) : scala.xml.Node = {")

    buffer.println("new scala.xml.Node {")
    buffer.println("  def label = refNameInParent")
    buffer.println("    def child = {        ")
    buffer.println("       var subresult: List[scala.xml.Elem] = List()  ")

    cls.getEAllContainments.foreach {
      subClass =>
        buffer.println("subresult = subresult ++ List(selfObject." + getGetter(subClass.getName) + "." + subClass.getEReferenceType.getName + "toXmi(" + subClass.getName + "))")
    }


    /*
       cls.getEAttributes.foreach {
     att =>
       buffer.print(att.getName)
       buffer.print("=\"TITI\"")
       buffer.print(" ")
   }

    */

    buffer.println("      subresult                                      ")
    buffer.println("    }                                                ")
    buffer.println("  }                                                  ")


    buffer.println("}")

    buffer.println("}") //END TRAIT
  }


}