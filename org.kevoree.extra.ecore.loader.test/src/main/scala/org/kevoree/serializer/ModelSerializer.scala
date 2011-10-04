package org.kevoree.serializer
class ModelSerializer extends ContainerRootSerializer {
def serialize(o : Object) : scala.xml.Node = {
o match {
case o : org.kevoree.ContainerRoot => {
val context = getContainerRootXmiAddr(o,"/")
ContainerRoottoXmi(o,context)
}
case _ => null
}
}
}
