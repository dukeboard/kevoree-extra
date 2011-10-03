package org.kevoree.extra.ecore.gencode;

import org.junit.Test;
import org.kevoree.tools.ecore.gencode.Generator;
import org.kevoree.tools.ecore.gencode.serializer.SerializerGenerator;

import java.io.File;

/**
 * Created by IntelliJ IDEA.
 * User: Gregory NAIN
 * Date: 21/09/11
 * Time: 23:04
 */
public class GenerateCodeTest {

    // @Test
    public void generate() {
        File rootDir = new File("target/generated-sources/kevoree");
        String rootPackage = "org";
        File ecoreFile = new File(getClass().getResource("/kevoree.ecore").getPath());
        Generator gen = new Generator(rootDir, rootPackage);
        gen.generateModel(ecoreFile);

    }

    //@Test
    public void generateLoader() {
        File rootDir = new File("../org.kevoree.extra.ecore.loader.test/src/main/scala/");

        String rootPackage = "org";
        File ecoreFile = new File(getClass().getResource("/kevoree.ecore").getPath());
        Generator gen = new Generator(rootDir, rootPackage);
        gen.generateLoader(ecoreFile);

    }

    @Test
    public void generateSerializer() {
        File rootDir = new File("../org.kevoree.extra.ecore.loader.test/src/main/scala/");
        String rootPackage = "org";
        File ecoreFile = new File(getClass().getResource("/kevoree.ecore").getPath());
        Generator gen = new Generator(rootDir, rootPackage);
        gen.generateSerializer(ecoreFile);

    }


}
