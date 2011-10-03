package org.kevoree.serializer
class ModelSerializer extends ContainerRootSerializer {
def serialize(o : Object) : String = {
o match {
case o : org.kevoree.ContainerRoot => ContainerRoottoXmi(o).toString()
case _ => null
}
}
}
