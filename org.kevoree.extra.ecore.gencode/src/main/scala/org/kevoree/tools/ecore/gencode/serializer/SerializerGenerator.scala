package org.kevoree.tools.ecore.gencode.serializer

import scala.collection.JavaConversions._
import org.kevoree.tools.ecore.gencode.ProcessorHelper
import java.io.{File, FileOutputStream, PrintWriter}
import org.kevoree.tools.ecore.gencode.loader.RootLoader
import org.eclipse.emf.ecore.{EReference, EAttribute, EPackage, EClass}

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
        generateSerializer(location + "/" + rootXmiPackage.getName, rootPackage + "." + rootXmiPackage.getName, rootXmiPackage.getName + ":" + cls.getName, cls, rootXmiPackage, true)
        generateDefaultSerializer(location + "/" + rootXmiPackage.getName,rootPackage + "." + rootXmiPackage.getName,cls,rootXmiPackage)
      }
      case _@e => throw new UnsupportedOperationException("Root container not found. Returned:" + e)
    }
  }


  def generateDefaultSerializer(genDir: String, packageName: String, root: EClass, rootXmiPackage: EPackage) {
    ProcessorHelper.checkOrCreateFolder(genDir + "/serializer")
    val pr = new PrintWriter(new FileOutputStream(new File(genDir + "/serializer/" + "ModelSerializer.scala")))
    pr.println("package " + packageName + ".serializer")
    pr.println("class ModelSerializer extends "+root.getName+"Serializer {")

    pr.println("def serialize(o : Object) : String = {")

    pr.println("o match {")
    pr.println("case o : "+packageName+"."+root.getName+" => "+root.getName+"toXmi(o).toString()")
    pr.println("case _ => null")
    pr.println("}") //END MATCH
    pr.println("}") //END serialize method
    pr.println("}") //END TRAIT
    pr.flush()
    pr.close()
  }


  def generateSerializer(genDir: String, packageName: String, refNameInParent: String, root: EClass, rootXmiPackage: EPackage, isRoot: Boolean = false): Unit = {
    ProcessorHelper.checkOrCreateFolder(genDir + "/serializer")
    //PROCESS SELF
    val pr = new PrintWriter(new FileOutputStream(new File(genDir + "/serializer/" + root.getName + "Serializer.scala")))
    pr.println("package " + packageName + ".serializer")
    generateToXmiMethod(root, pr, rootXmiPackage.getName + ":" + root.getName, isRoot)
    pr.flush()
    pr.close()

    //PROCESS SUB
    root.getEAllContainments.foreach {
      sub =>
        val subpr = new PrintWriter(new FileOutputStream(new File(genDir + "/serializer/" + sub.getEReferenceType.getName + "Serializer.scala")))
        subpr.println("package " + packageName + ".serializer")
        generateToXmiMethod(sub.getEReferenceType, subpr, sub.getName)
        subpr.flush()
        subpr.close()

        generateSerializer(genDir, packageName, sub.getName, sub.getEReferenceType, rootXmiPackage)

    }


  }

  private def getGetter(name: String): String = {
    "get" + name.charAt(0).toUpper + name.substring(1)
  }

  /*
private def generateGetAddrMethod(cls: EClass, buffer: PrintWriter, refNameInParent: String, parentClassName : String) {
 buffer.println("def get"+cls.getName+"Addr(selfObject : "+cls.getName+",refNameInParent: String) : String = {")
 buffer.println("val buffer = new StringBuffer")

 //CALL PARENT
 buffer.println("buffer.append(getAddr(selfObject.eContainer))")


 buffer.println("getAddr(selfObject.eContainer)")
 buffer.println("buffer.append(\"/@\")")
 buffer.println("buffer.append(\""+refNameInParent+"\")")
 buffer.println("selfObject.eContainer."+refNameInParent+".indexOf(selfObject)")

 buffer.println("buffer.toString")
 buffer.println("}")
}   */

  private def generateToXmiMethod(cls: EClass, buffer: PrintWriter, refNameInParent: String, isRoot: Boolean = false) = {
    buffer.println("import org.kevoree._")
    buffer.println("trait " + cls.getName + "Serializer ")
    val subTraits = cls.getEAllContainments.map(sub => sub.getEReferenceType.getName + "Serializer").toSet

    if (subTraits.size >= 1) {
      buffer.print(subTraits.mkString(" extends ", " with ", " "))
    }

    buffer.println("{")

    // generateGetAddrMethod(cls,buffer,refNameInParent)
    if (isRoot) {
      buffer.println("def " + cls.getName + "toXmi(selfObject : " + cls.getName + ") : scala.xml.Node = {")
    } else {
      buffer.println("def " + cls.getName + "toXmi(selfObject : " + cls.getName + ",refNameInParent : String) : scala.xml.Node = {")
    }

    buffer.println("new scala.xml.Node {")
    if (!isRoot) {
      buffer.println("  def label = refNameInParent")
    } else {
      buffer.println("  def label = \"" + refNameInParent + "\"")
    }


    buffer.println("    def child = {        ")
    buffer.println("       var subresult: List[scala.xml.Node] = List()  ")

    cls.getEAllContainments.foreach {
      subClass =>

        subClass.getUpperBound match {
          case 1 => {
            buffer.println("selfObject." + getGetter(subClass.getName) + ".map { so => ")
            buffer.println("subresult = subresult ++ List(" + subClass.getEReferenceType.getName + "toXmi(so,\"" + subClass.getName + "\"))")
            buffer.println("}")
          }
          case -1 => {
            buffer.println("selfObject." + getGetter(subClass.getName) + ".foreach { so => ")
            buffer.println("subresult = subresult ++ List(" + subClass.getEReferenceType.getName + "toXmi(so,\"" + subClass.getName + "\"))")
            buffer.println("}")
          }

        }

    }

    buffer.println("      subresult                                      ")
    buffer.println("    }                                                ")


    if (cls.getEAllAttributes.size() > 0 || cls.getEAllReferences.filter(eref => !cls.getEAllContainments.contains(eref)).size > 0) {
      buffer.println("override def attributes  : scala.xml.MetaData =  { ")
      cls.getEAllAttributes.foreach {
        att =>
          buffer.print("new scala.xml.UnprefixedAttribute(\"" + att.getName + "\",selfObject." + getGetter(att.getName) + ".toString,")
      }
      cls.getEAllReferences.filter(eref => !cls.getEAllContainments.contains(eref)).foreach {
        ref =>
          buffer.print("new scala.xml.UnprefixedAttribute(\"" + ref.getName + "\",\"//HELLO\",")
      }
      buffer.print("scala.xml.Null")
      for (i <- 0 until cls.getEAllAttributes.size() + cls.getEAllReferences.filter(eref => !cls.getEAllContainments.contains(eref)).size) {
        buffer.print(")")
      }
      buffer.println("}")
    }

    buffer.println("  }                                                  ")
    buffer.println("}") //END TO XMI
    buffer.println("}") //END TRAIT
  }


}