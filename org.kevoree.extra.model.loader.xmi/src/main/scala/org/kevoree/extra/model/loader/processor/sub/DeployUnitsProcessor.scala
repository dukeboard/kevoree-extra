package org.kevoree.extra.model.loader.processor.sub

import org.kevoree.extra.model.loader.processor.ProcessingContext
import org.kevoree.{NodeType, DeployUnit, KevoreePackage}

/**
 * Created by IntelliJ IDEA.
 * User: Gregory NAIN
 * Date: 24/09/11
 * Time: 16:06
 */

trait DeployUnitsProcessor {

  def loadDeployUnits(): Int = {
    val deployUnitList = (ProcessingContext.xmiContent \\ "deployUnits")
    var i = 0
    deployUnitList.foreach {
      du =>
        val duElem = KevoreePackage.createDeployUnit
        ProcessingContext.map += "//@" + du.label + "." + i -> duElem
        i += 1
    }
    i
  }

  def resolveDeployUnits() {
    val deployUnitList = (ProcessingContext.xmiContent \\ "deployUnits")
    var i = 0
    deployUnitList.foreach {
      du =>
        val duElem = ProcessingContext.map("//@" + du.label + "." + i).asInstanceOf[DeployUnit]
        duElem.eContainer = ProcessingContext.containerRoot
        duElem.setGroupName((du \ "@groupName").text)
        duElem.setUnitName((du \ "@unitName").text)
        duElem.setVersion((du \ "@version").text)
        duElem.setHashcode((du \ "@hashcode").text)
        duElem.setUrl((du \ "@url").text)
        duElem.setName((du \ "@name").text)

        val tnt = (du \ "@targetNodeType").text
        ProcessingContext.map.get(tnt) match {
          case Some(s: NodeType) => duElem.setTargetNodeType(s)
          case None => System.out.println("TargetNodeType not found in map ! du:" + duElem.getUrl + " target:" + tnt)
        }

        (du \ "@requiredLibs").headOption match {
          case Some(head) => {
            head.text.split(" ").foreach {
              reqLib =>
                ProcessingContext.map.get(reqLib) match {
                  case Some(s: DeployUnit) => duElem.addRequiredLibs(s)
                  case None => System.out.println("DeployUnit not found in map ! du:" + duElem.getUrl + " req:" + reqLib)
                }
            }
          }
          case None => //No deployUnit for this repository
        }
        ProcessingContext.containerRoot.addDeployUnits(duElem)
        i += 1
    }
  }

}