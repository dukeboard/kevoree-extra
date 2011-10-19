/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kevoree.extra.ecore.gencode.mavenplugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;

import java.io.File;

/**
 * Generates files based on grammar files with Antlr tool.
 *
 * @goal model
 * @phase generate-sources
 * @requiresDependencyResolution compile
 * @author <a href="mailto:ffouquet@irisa.fr">Fouquet François</a>
 * @version $Id$
 */
public class GenModelPlugin extends AbstractMojo {

    /**
     * Ecore file
     *
     * @parameter
     */
    private File ecore;
    /**
     * Source base directory
     *
     * @parameter
     */
    private File output;
    
  /**
     * code root package
     *
     * @parameter
     */
    private String rootPackage;


    /**
     * Clear output dir
     *
     * @parameter
     */
    private Boolean clearOutput=true;


    /**
     * The maven project.
     *
     * @parameter expression="${project}"
     * @required
     * @readonly
     */
    private MavenProject project;

   private boolean deleteDirectory(File path) {
        if (path.exists()) {
            File[] files = path.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    deleteDirectory(files[i]);
                } else {
                    files[i].delete();
                }
            }
        }
        return (path.delete());
    }

    @Override
    public void execute() throws MojoExecutionException {
        //File ecoreFile = new File(getClass().getResource("/kevoree.ecore").getPath());


        if(clearOutput) {
            deleteDirectory(output);
        }

        org.kevoree.tools.ecore.gencode.Generator gen = new org.kevoree.tools.ecore.gencode.Generator(output,rootPackage);//, getLog());
        gen.generateModel(ecore,project.getVersion());

        //Util.createGenModel(ecore, genmodel, output, getLog(),clearOutput);
    }
}
