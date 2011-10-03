package org.kevoree.serializer
class ModelSerializer extends ContainerRootSerializer {
def serialize(o : Object) : String = {
o match {
case o : org.kevoree.ContainerRoot => {
val context = getContainerRootXmiAddr(o,"/")
ContainerRoottoXmi(o,context).toString()
}
case _ => null
}
}
}
