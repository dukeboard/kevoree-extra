package org.kevoree.extra.model;

import org.kevoree.ContainerRoot;
import scala.Option;

import java.io.File;

/**
 * Created by IntelliJ IDEA.
 * User: Gregory NAIN
 * Date: 24/09/11
 * Time: 09:23
 */
public interface ModelLoader {

    public Option<ContainerRoot> loadModel(File file);

}
