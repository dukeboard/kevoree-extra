package org.kevoree.web

import org.kevoree.ContainerRoot
import org.kevoree.merger.Merger
import org.kevoree.merger.RootMerger
import org.kevoree.cloner.ModelCloner
import java.util.ArrayList
import org.kevoree.KevoreeFactory
import org.kevoree.impl.DefaultKevoreeFactory
import java.io.File
import java.util.jar.JarFile
import java.util.jar.JarEntry
import org.kevoree.tools.aether.framework.AetherUtil
import org.kevoree.framework.KevoreeXmiHelper
import java.util.zip.ZipEntry

/*
* Author : Gregory Nain (developer.name@uni.lu)
* Date : 13/02/13
* (c) 2013 University of Luxembourg – Interdisciplinary Centre for Security Reliability and Trust (SnT)
* All rights reserved
*/


class ModelHandler() {

    val merger : RootMerger = RootMerger()
    val cloner : ModelCloner = ModelCloner()
    var baseModel : ContainerRoot? = null


    {
        try {
            val repos = ArrayList<String>()
            repos.add("http://maven.kevoree.org/release")
            repos.add("http://maven.kevoree.org/snapshots")
            val factory = DefaultKevoreeFactory()
            val allModel =  AetherUtil.resolveMavenArtifact("org.kevoree.library.model.all", "org.kevoree.corelibrary.model", factory.getVersion(), repos)
            val jar = JarFile(allModel)
            val entry = jar.getJarEntry("KEV-INF/lib.kev")
            val newmodel = KevoreeXmiHelper.loadStream(jar.getInputStream(entry as ZipEntry)!!)
            if (newmodel != null) {
                mergeIncomingModel(newmodel)
            }
        }catch (e : Exception) {
            e.printStackTrace()
        }
    }


    fun getCurrentModel() : ContainerRoot? {
        return cloner.clone(baseModel!!, true)
    }

    fun mergeIncomingModel(incomingModel : ContainerRoot) {
        if(baseModel != null) {
           merger.merge(baseModel, incomingModel)
        } else {
            baseModel = incomingModel
        }

    }


}