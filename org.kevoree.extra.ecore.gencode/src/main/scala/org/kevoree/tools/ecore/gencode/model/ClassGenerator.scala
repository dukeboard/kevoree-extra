package org.kevoree.tools.ecore.gencode.model

import java.io.{File, FileOutputStream, PrintWriter}
import org.kevoree.tools.ecore.gencode.ProcessorHelper._
import org.kevoree.tools.ecore.gencode.ProcessorHelper
import scala.collection.JavaConversions._
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore._
import xmi.impl.XMIResourceImpl
import org.eclipse.emf.common.util.URI

/**
 * Created by IntelliJ IDEA.
 * User: Gregory NAIN
 * Date: 23/09/11
 * Time: 13:35
 */

trait ClassGenerator {

  def generateCompanion(location: String, pack: String, cls: EClass, packElement: EPackage) {
    val pr = new PrintWriter(new FileOutputStream(new File(location + "/impl/" + cls.getName + "Impl.scala")))
    //System.out.println("Classifier class:" + cls.getClass)

    pr.println("package " + pack + ".impl;")
    pr.println()
    pr.println("import " + pack + "._;")
    pr.println()

    pr.print("case class " + cls.getName + "Impl(")

    pr.println(") extends " + cls.getName + " {")

    //pr.println("")

    pr.println("\n\n}")

    pr.flush()
    pr.close()

  }

  private def resolveCrossRefTypeDef(cls:EClass, ref: EReference, pack: String): String = {

    //TODO : NETOYER :-)
    //URI d'exemple:
    //System.out.println("RefTypeInstanceType:" + ref.getEReferenceType)
    //    System.out.println("RefType:" + ref.getEReferenceType.eIsProxy())
    val uri = ref.getEReferenceType.asInstanceOf[InternalEObject].eProxyURI()
    val uriString = uri.toString
    System.out.println("Tricky part")
    System.out.println("Uri:" + uriString)
    System.out.println("Trying to load URI:" + uriString)
    val resource = new XMIResourceImpl(uri);
    resource.load(null);

    //System.out.println("Resolved Ref:" + resolvedRef)
    val packa = resource.getAllContents.next().asInstanceOf[EPackage]
    System.out.println("Package Name:" + packa.getName)

    //System.out.println("RefTypeInstanceTypeUri:" + uri)
    val typName = uriString.substring(uriString.lastIndexOf("#//") + 3)
    //System.out.println("RefTypeInstanceTypeName:" + typName)
    //throw new UnsupportedOperationException("Reference type of ref:" + ref.getName + " in class:" + cls.getName + " is null.\n")
    pack.substring(0,pack.lastIndexOf(".")) + "." + packa.getName + "." + typName
  }

  def generateClass(location: String, pack: String, cls: EClass, packElement: EPackage) {
    val pr = new PrintWriter(new FileOutputStream(new File(location + "/" + cls.getName + ".scala")))
    System.out.println("Generating class:" + cls.getName)

    pr.println("package " + pack + ";")
    pr.println()
    pr.println("import " + pack + ".impl._;")
    pr.println()
    pr.println(generateHeader(packElement))
    //case class name
    pr.print("trait " + cls.getName)

    pr.println((generateSuperTypes(cls, packElement) match {
      case None => "{"
      case Some(s) => s + " {"
    }))


    cls.getEAttributes.foreach {
      att =>

        pr.print("\t\tprivate var " + protectReservedWords(att.getName) + " : ")
        ProcessorHelper.convertType(att.getEAttributeType.getInstanceClassName) match {
          case "java.lang.String" => pr.println("java.lang.String = \"\"\n")
          case "java.lang.Integer" => pr.println("java.lang.Integer = 0\n")
          case "java.lang.Boolean" => pr.println("java.lang.Boolean = false\n")
            case "java.lang.Object" => pr.println("java.lang.Object = null\n")
          case _@e => throw new UnsupportedOperationException("ClassGenerator:: Attribute type: " + att.getEAttributeType.getInstanceClassName + " has net been converted in a known type. Can not initialize.")
        }

    }


    cls.getEReferences.foreach {
      ref =>
        val typeRefName = (
          if (ref.getEReferenceType.getName == null) {
            resolveCrossRefTypeDef(cls,ref,pack)
          } else {
            ref.getEReferenceType.getName
          }
          )

        if (ref.getLowerBound == 0 && ref.getUpperBound == -1) {
          //pr.println("\n\t\t@scala.reflect.BeanProperty");
          pr.println("\t\tprivate var " + protectReservedWords(ref.getName) + " : Option[List[" + typeRefName + "]] = None\n")
        } else if (ref.getLowerBound == 0 && ref.getUpperBound != -1) {
          //pr.println("\n\t\t@scala.reflect.BeanProperty");
          pr.println("\t\tprivate var " + protectReservedWords(ref.getName) + " : Option[" + typeRefName + "] = None\n")
        } else if (ref.getLowerBound == 1 && ref.getUpperBound == -1) {
          //pr.println("\n\t\t@scala.reflect.BeanProperty");
          pr.println("\t\tprivate var " + protectReservedWords(ref.getName) + " : List[" + typeRefName + "] = List[" + typeRefName + "]()\n")
        } else if (ref.getLowerBound == 1 && ref.getUpperBound != -1) {
          //pr.println("\n\t\t@scala.reflect.BeanProperty");
          pr.println("\t\tprivate var " + protectReservedWords(ref.getName) + " : " + typeRefName + " = new " + typeRefName + "Impl\n")
        } else if (ref.getLowerBound > 1) {
          //pr.println("\n\t\t@scala.reflect.BeanProperty");
          pr.println("\t\tprivate var " + protectReservedWords(ref.getName) + " : List[" + typeRefName + "] = List[" + typeRefName + "]()\n")
        } else {
          throw new UnsupportedOperationException("GenDefConsRef::None standard arrity: " + cls.getName + "->" + typeRefName + "[" + ref.getLowerBound + "," + ref.getUpperBound + "]. Not implemented yet !")
        }
    }


    // Getters and Setters Generation


    cls.getEAttributes.foreach {
      att =>
      //Generate getter
        pr.print("\n\t\tdef get" + att.getName.substring(0, 1).toUpperCase + att.getName.substring(1) + " : " +
          ProcessorHelper.convertType(att.getEAttributeType.getInstanceClassName) + " = {\n")
        pr.println("\t\t\t\t" + protectReservedWords(att.getName) + "\n\t\t}")

        //generate setter
        pr.print("\n\t\tdef set" + att.getName.substring(0, 1).toUpperCase + att.getName.substring(1))
        pr.print("(" + protectReservedWords(att.getName) + " : " + ProcessorHelper.convertType(att.getEAttributeType.getInstanceClassName) + ") {\n")
        pr.println("\t\t\t\tthis." + protectReservedWords(att.getName) + " = " + protectReservedWords(att.getName) + "\n\t\t}")
    }


    cls.getEReferences.foreach {
      ref =>
        val typeRefName = (
          if (ref.getEReferenceType.getName == null) {
            resolveCrossRefTypeDef(cls,ref,pack)
          } else {
            ref.getEReferenceType.getName
          }
          )

        if (ref.getLowerBound == 0 && ref.getUpperBound == -1) {


          //Generate getter
          pr.print("\n\t\tdef get" + ref.getName.substring(0, 1).toUpperCase + ref.getName.substring(1))
          pr.print(" : Option[List[" + typeRefName + "]] = {\n")
          pr.println("\t\t\t\t" + protectReservedWords(ref.getName) + "\n\t\t}")

          //generate setter
          pr.print("\n\t\tdef set" + ref.getName.substring(0, 1).toUpperCase + ref.getName.substring(1))
          pr.print("(" + protectReservedWords(ref.getName) + " : List[" + typeRefName + "]) {\n")
          pr.print("\t\t\t\t" + protectReservedWords(ref.getName) + " match {\n")
          pr.print("\t\t\t\t\t\tcase l : List[" + typeRefName + "] => this." + protectReservedWords(ref.getName) + " = Some(" + protectReservedWords(ref.getName) + ")\n")
          pr.print("\t\t\t\t\t\tcase _ => this." + protectReservedWords(ref.getName) + " = None\n")
          pr.print("\t\t\t\t}\n")
          pr.println("\t\t}")

          //generate add
          pr.print("\n\t\tdef add" + ref.getName.substring(0, 1).toUpperCase + ref.getName.substring(1))
          pr.print("(" + protectReservedWords(ref.getName) + " : " + typeRefName + ") {\n")
          pr.print("\t\t\t\tthis." + protectReservedWords(ref.getName) + " match {\n")
          pr.print("\t\t\t\t\t\tcase Some(l) => this." + protectReservedWords(ref.getName) + " = Some(l ++ List(" + protectReservedWords(ref.getName) + "))\n")
          pr.print("\t\t\t\t\t\tcase None => this." + protectReservedWords(ref.getName) + " = Some(List(" + protectReservedWords(ref.getName) + "))\n")
          pr.print("\t\t\t\t}\n")
          pr.println("\t\t}")

          //generate remove
          pr.print("\n\t\tdef remove" + ref.getName.substring(0, 1).toUpperCase + ref.getName.substring(1))
          pr.print("(" + protectReservedWords(ref.getName) + " : " + typeRefName + ") {\n")
          pr.print("\t\t\t\tthis." + protectReservedWords(ref.getName) + " match {\n")
          pr.print("\t\t\t\t\t\tcase Some(l) => {\n")
          pr.print("\t\t\t\t\t\t\t\tif(l.size == 1) {\n")
          pr.print("\t\t\t\t\t\t\t\t\t\tthis." + protectReservedWords(ref.getName) + " = None\n")
          pr.print("\t\t\t\t\t\t\t\t} else {\n")
          pr.print("\t\t\t\t\t\t\t\t\t\tvar nList = List[" + typeRefName + "]()\n")
          pr.print("\t\t\t\t\t\t\t\t\t\tl.foreach(e => if(!e.equals(" + protectReservedWords(ref.getName) + ")) nList = nList ++ List(e))\n")
          pr.print("\t\t\t\t\t\t\t\t\t\tthis." + protectReservedWords(ref.getName) + " = Some(nList)\n")
          pr.print("\t\t\t\t\t\t\t\t}\n")
          pr.print("\t\t\t\t\t\t}\n")
          pr.print("\t\t\t\t\t\tcase None => \n")
          pr.print("\t\t\t\t}\n")
          pr.println("\t\t}\n")

        } else if (ref.getLowerBound == 0 && ref.getUpperBound != -1) {
          //Generate getter
          pr.print("\n\t\tdef get" + ref.getName.substring(0, 1).toUpperCase + ref.getName.substring(1))
          pr.print(" : Option[" + typeRefName + "] = {\n")
          pr.println("\t\t\t\t" + protectReservedWords(ref.getName) + "\n\t\t}")

          //generate setter
          pr.print("\n\t\tdef set" + ref.getName.substring(0, 1).toUpperCase + ref.getName.substring(1))
          pr.print("(" + protectReservedWords(ref.getName) + " : " + typeRefName + ") {\n")
          pr.print("\t\t\t\t" + protectReservedWords(ref.getName) + " match {\n")
          pr.print("\t\t\t\t\t\tcase l : " + typeRefName + " => this." + protectReservedWords(ref.getName) + " = Some(" + protectReservedWords(ref.getName) + ")\n")
          pr.print("\t\t\t\t\t\tcase _ => this." + protectReservedWords(ref.getName) + " = None\n")
          pr.print("\t\t\t\t}\n")
          pr.println("\t\t}")

        } else if (ref.getLowerBound == 1 && ref.getUpperBound == -1) {
          //Generate getter
          pr.print("\n\t\tdef get" + ref.getName.substring(0, 1).toUpperCase + ref.getName.substring(1))
          pr.print(" : List[" + typeRefName + "] = {\n")
          pr.println("\t\t\t\t" + protectReservedWords(ref.getName) + "\n\t\t}")

          //generate setter
          pr.print("\n\t\tdef set" + ref.getName.substring(0, 1).toUpperCase + ref.getName.substring(1))
          pr.print("(" + protectReservedWords(ref.getName) + " : List[" + typeRefName + "]) {\n")
          pr.print("\t\t\t\t" + protectReservedWords(ref.getName) + " match {\n")
          pr.print("\t\t\t\t\t\tcase l : List[" + ref.getEReferenceType.getName + "] => this." + protectReservedWords(ref.getName) + " = " + protectReservedWords(ref.getName) + "\n")
          pr.print("\t\t\t\t\t\tcase _ => throw new IllegalArgumentException(\"The parameter " + protectReservedWords(ref.getName) + " : List[" + typeRefName + "] in " + cls.getName + " only admits List[" + typeRefName + "].\")\n")
          pr.print("\t\t\t\t}\n")
          pr.println("\t\t}")

          //generate add
          pr.print("\n\t\tdef add" + ref.getName.substring(0, 1).toUpperCase + ref.getName.substring(1))
          pr.print("(" + protectReservedWords(ref.getName) + " : " + typeRefName + ") {\n")
          pr.print("\t\t\t\tthis." + protectReservedWords(ref.getName) + " = this." + protectReservedWords(ref.getName) + " ++ List(" + protectReservedWords(ref.getName) + ")\n")
          pr.println("\t\t}")

          //generate remove
          pr.print("\n\t\tdef remove" + ref.getName.substring(0, 1).toUpperCase + ref.getName.substring(1))
          pr.print("(" + protectReservedWords(ref.getName) + " : " + typeRefName + ") {\n")
          pr.print("\t\t\t\tif(this." + protectReservedWords(ref.getName) + ".size == 1) {\n")
          pr.print("\t\t\t\t\t\tthrow new UnsupportedOperationException(\"The list of " + protectReservedWords(ref.getName) + " must contain at least " + ref.getLowerBound + " element. Connot remove sizeof(" + protectReservedWords(ref.getName) + ")=\"+this." + protectReservedWords(ref.getName) + ".size)\n")
          pr.print("\t\t\t\t} else {\n")
          pr.print("\t\t\t\t\t\tvar nList = List[" + typeRefName + "]()\n")
          pr.print("\t\t\t\t\t\tthis." + protectReservedWords(ref.getName) + ".foreach(e => if(!e.equals(" + protectReservedWords(ref.getName) + ")) nList = nList ++ List(e))\n")
          pr.print("\t\t\t\t\t\tthis." + protectReservedWords(ref.getName) + " = nList\n")
          pr.print("\t\t\t\t}\n")
          pr.println("\t\t}\n")

        } else if (ref.getLowerBound == 1 && ref.getUpperBound != -1) {

          //Generate getter
          pr.print("\n\t\tdef get" + ref.getName.substring(0, 1).toUpperCase + ref.getName.substring(1))
          pr.print(" : " + typeRefName + " = {\n")
          pr.println("\t\t\t\t" + protectReservedWords(ref.getName) + "\n\t\t}")

          //generate setter
          pr.print("\n\t\tdef set" + ref.getName.substring(0, 1).toUpperCase + ref.getName.substring(1))
          pr.print("(" + protectReservedWords(ref.getName) + " : " + typeRefName + ") {\n")
          pr.print("\t\t\t\t" + protectReservedWords(ref.getName) + " match {\n")
          pr.print("\t\t\t\t\t\tcase l : " + typeRefName + " => this." + protectReservedWords(ref.getName) + " = " + protectReservedWords(ref.getName) + "\n")
          pr.print("\t\t\t\t\t\tcase _ => throw new IllegalArgumentException(\"The parameter " + protectReservedWords(ref.getName) + " : " + typeRefName + " in " + cls.getName + " only admits " + typeRefName + ".\")\n")
          pr.print("\t\t\t\t}\n")
          pr.println("\t\t}\n")

        } else if (ref.getLowerBound > 1) {
          //Generate getter
          pr.print("\n\t\tdef get" + ref.getName.substring(0, 1).toUpperCase + ref.getName.substring(1))
          pr.print(" : List[" + typeRefName + "] = {\n")
          pr.println("\t\t\t\t" + protectReservedWords(ref.getName) + "\n\t\t}")

          //generate setter
          pr.print("\n\t\tdef set" + ref.getName.substring(0, 1).toUpperCase + ref.getName.substring(1))
          pr.print("(" + protectReservedWords(ref.getName) + " : List[" + typeRefName + "]) {\n")
          pr.print("\t\t\t\t" + protectReservedWords(ref.getName) + " match {\n")
          pr.print("\t\t\t\t\t\tcase l : List[" + typeRefName + "] => this." + protectReservedWords(ref.getName) + " = " + protectReservedWords(ref.getName) + "\n")
          pr.print("\t\t\t\t\t\tcase _ => throw new IllegalArgumentException(\"The parameter " + protectReservedWords(ref.getName) + " : List[" + typeRefName + "] in " + cls.getName + " only admits List[" + typeRefName + "].\")\n")
          pr.print("\t\t\t\t}\n")
          pr.println("\t\t}")

          //generate add
          pr.print("\n\t\tdef add" + ref.getName.substring(0, 1).toUpperCase + ref.getName.substring(1))
          pr.print("(" + protectReservedWords(ref.getName) + " : " + typeRefName + ") {\n")
          pr.print("\t\t\t\tthis." + protectReservedWords(ref.getName) + " = this." + protectReservedWords(ref.getName) + " ++ List(" + protectReservedWords(ref.getName) + ")\n")
          pr.println("\t\t}")


          //generate remove
          pr.print("\n\t\tdef remove" + ref.getName.substring(0, 1).toUpperCase + ref.getName.substring(1))
          pr.print("(" + protectReservedWords(ref.getName) + " : " + typeRefName + ") {\n")

          pr.print("\t\t\t\tif(this." + protectReservedWords(ref.getName) + ".size == " + ref.getLowerBound + ") {\n")
          pr.print("\t\t\t\t\t\tthrow new UnsupportedOperationException(\"The list of " + protectReservedWords(ref.getName) + " must contain at least " + ref.getLowerBound + " element. Connot remove sizeof(" + protectReservedWords(ref.getName) + ")=\"+this." + protectReservedWords(ref.getName) + ".size)\n")
          pr.print("\t\t\t\t} else {\n")
          pr.print("\t\t\t\t\t\tvar nList = List[" + typeRefName + "]()\n")
          pr.print("\t\t\t\t\t\tthis." + protectReservedWords(ref.getName) + ".foreach(e => if(!e.equals(" + protectReservedWords(ref.getName) + ")) nList = nList ++ List(e))\n")
          pr.print("\t\t\t\t\t\tthis." + protectReservedWords(ref.getName) + " = nList\n")
          pr.print("\t\t\t\t}\n")

          pr.println("\t\t}\n")

        } else {
          throw new UnsupportedOperationException("GenDefConsRef::None standard arrity: " + cls.getName + "->" + typeRefName + "[" + ref.getLowerBound + "," + ref.getUpperBound + "]. Not implemented yet !")
        }
    }




    pr.println("")
    pr.println("}")

    pr.flush()
    pr.close()

  }


}