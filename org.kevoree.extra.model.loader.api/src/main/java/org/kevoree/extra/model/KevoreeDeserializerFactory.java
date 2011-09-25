package org.kevoree.extra.model;

import scala.xml.NodeSeq;

/**
 * Created by IntelliJ IDEA.
 * User: Gregory NAIN
 * Date: 24/09/11
 * Time: 12:39
 */
public interface KevoreeDeserializerFactory {

    public KevoreeDeserializer getDeserializer(String modelNamespace, NodeSeq nodeSequence);

}
