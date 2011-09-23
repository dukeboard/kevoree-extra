package org.kevoree.tools.ecore.gencode

import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import org.eclipse.emf.ecore.{EClass, EPackage}
import scala.collection.JavaConversions._

/**
 * Created by IntelliJ IDEA.
 * User: Gregory NAIN
 * Date: 22/09/11
 * Time: 09:49
 */

object ProcessorHelper {

  def checkOrCreateFolder(path: String) {
    val file = new File(path)
    if (!file.exists) file.mkdirs
  }


  def convertType(theType: String): String = {
    theType match {
      case "bool" | "boolean" | "java.lang.Boolean" => "java.lang.Boolean"
      case "java.lang.String" | "String" => "java.lang.String"
      case "java.lang.Integer" => "java.lang.Integer"
      case _ => throw new UnsupportedOperationException("ProcessorHelper::convertType::No matching found for type: " + theType); null
    }
  }

  def protectReservedWords(word: String): String = {
    word match {
      case "type" => "`type`"
      case _ => word //throw new UnsupportedOperationException("ProcessorHelper::protectReservedWords::No matching found for word: " + word);null
    }
  }

  def generateHeader(packElement: EPackage): String = {
    var header = "";
    val formateur = new SimpleDateFormat("'Date:' dd MMM yy 'Time:' HH:mm")
    header += "/**\n"
    header += " * Created by Kevoree Ecore Model Generator.\n"
    header += " * @author: Gregory NAIN\n"
    header += " * " + formateur.format(new Date) + "\n"
    header += " * Kevoree NS_URI: " + packElement.getNsURI + "\n"
    header += " */"
    header
  }


  def generateSuperTypes(cls: EClass, packElement: EPackage): Option[String] = {
    var superTypeList: Option[String] = None
    superTypeList = Some("with " + packElement.getName.substring(0, 1).toUpperCase + packElement.getName.substring(1) + "Container")
    // superTypeList = Some(superTypeList.get + "with " + packElement.getName.substring(0, 1).toUpperCase + packElement.getName.substring(1) + "Mutable")
    cls.getESuperTypes.foreach {
      superType => superTypeList = Some(superTypeList.get + " with " + superType.getName)
    }
    superTypeList
  }

}