package org.kevoree.extra.model.loader.processor.sub

import org.kevoree.extra.model.loader.processor.ProcessingContext
import org.kevoree.{DeployUnit, Repository, KevoreePackage}

/**
 * Created by IntelliJ IDEA.
 * User: Gregory NAIN
 * Date: 24/09/11
 * Time: 16:06
 */

trait RepositoryProcessor {

  def loadRepositories() : Int = {
    val repoList = (ProcessingContext.xmiContent \\ "repositories")
    var i = 0
    repoList.foreach {
      repo =>
        val repoElem = KevoreePackage.createRepository
        ProcessingContext.map += "//@" + repo.label + "." + i -> repoElem
        i += 1
    }
    i
  }

  def resolveRepositories() {
    val repoList = (ProcessingContext.xmiContent \\ "repositories")
    var i = 0
    repoList.foreach {
      repo =>
        val repoElem = ProcessingContext.map("//@" + repo.label + "." + i).asInstanceOf[Repository]
        repoElem.eContainer = ProcessingContext.containerRoot
        repoElem.setUrl((repo \ "@url").text)
        (repo \ "@units").headOption match {
          case Some(head) => {
            head.text.split(" ").foreach {
              du =>
                ProcessingContext.map.get(du) match {
                  case Some(s: DeployUnit) => repoElem.addUnits(s)
                  case None => System.out.println("DeployUnit not found in map ! repo:" + repoElem.getUrl + " du:" + du)
                }
            }
          }
          case None => //No deployUnit for this repository
        }
        ProcessingContext.containerRoot.addRepositories(repoElem)
        i += 1
    }

  }

}