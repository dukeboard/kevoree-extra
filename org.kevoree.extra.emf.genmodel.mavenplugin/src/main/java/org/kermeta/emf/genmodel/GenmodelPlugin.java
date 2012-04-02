/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kermeta.emf.genmodel;

import java.io.File;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;

/**
 * Generates files based on grammar files with Antlr tool.
 *
 * @goal generate
 * @phase generate-sources
 * @requiresDependencyResolution compile
 * @author <a href="mailto:ffouquet@irisa.fr">Fouquet François</a>
 * @version $Id$
 */
public class GenmodelPlugin extends AbstractMojo {

    /**
     * Ecore file
     *
     * @parameter
     */
    private File ecore;
    /**
     * Source base directory
     *
     * @parameter default-value="${project.build.directory}/generated-sources/emf"
     */
    private File output;
    
    /**
     * Genmodel file
     *
     * @parameter default-value="${project.build.directory}/generated-sources/model.genmodel"
     */
    private File genmodel;


    /**
     * Clear output dir
     *
     * @parameter
     */
    private Boolean clearOutput=true;
    
    
        /**
     * Base Package
     *
     * @parameter
     */
    private String basePackage="";
    


        /**
     * The maven project.
     *
     * @parameter expression="${project}"
     * @required
     * @readonly
     */
    private MavenProject project;
    
    @Override
    public void execute() throws MojoExecutionException {
        Util.createGenModel(ecore, genmodel, output, getLog(),clearOutput,basePackage);
        project.addCompileSourceRoot(output.getAbsolutePath());
    }
}
