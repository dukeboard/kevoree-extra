package org.kevoree;

import org.kevoree.impl._;

/**
 * Created by Ecore Model Generator.
 * @author: Gregory NAIN
 * Date: 04 oct. 11 Time: 20:58
 * Meta-Model:NS_URI=http://kevoree/1.0
 */
object KevoreeFactory {

	 def eINSTANCE = KevoreeFactory

	 def createComponentInstance : ComponentInstance = new ComponentInstanceImpl
	 def createComponentType : ComponentType = new ComponentTypeImpl
	 def createContainerNode : ContainerNode = new ContainerNodeImpl
	 def createContainerRoot : ContainerRoot = new ContainerRootImpl
	 def createPortType : PortType = new PortTypeImpl
	 def createPort : Port = new PortImpl
	 def createNamespace : Namespace = new NamespaceImpl
	 def createDictionary : Dictionary = new DictionaryImpl
	 def createDictionaryType : DictionaryType = new DictionaryTypeImpl
	 def createDictionaryAttribute : DictionaryAttribute = new DictionaryAttributeImpl
	 def createDictionaryValue : DictionaryValue = new DictionaryValueImpl
	 def createCompositeType : CompositeType = new CompositeTypeImpl
	 def createPortTypeRef : PortTypeRef = new PortTypeRefImpl
	 def createWire : Wire = new WireImpl
	 def createServicePortType : ServicePortType = new ServicePortTypeImpl
	 def createOperation : Operation = new OperationImpl
	 def createParameter : Parameter = new ParameterImpl
	 def createTypedElement : TypedElement = new TypedElementImpl
	 def createMessagePortType : MessagePortType = new MessagePortTypeImpl
	 def createRepository : Repository = new RepositoryImpl
	 def createDeployUnit : DeployUnit = new DeployUnitImpl
	 def createTypeLibrary : TypeLibrary = new TypeLibraryImpl
	 def createNamedElement : NamedElement = new NamedElementImpl
	 def createIntegrationPattern : IntegrationPattern = new IntegrationPatternImpl
	 def createExtraFonctionalProperty : ExtraFonctionalProperty = new ExtraFonctionalPropertyImpl
	 def createPortTypeMapping : PortTypeMapping = new PortTypeMappingImpl
	 def createChannel : Channel = new ChannelImpl
	 def createMBinding : MBinding = new MBindingImpl
	 def createNodeNetwork : NodeNetwork = new NodeNetworkImpl
	 def createNodeLink : NodeLink = new NodeLinkImpl
	 def createNetworkProperty : NetworkProperty = new NetworkPropertyImpl
	 def createChannelType : ChannelType = new ChannelTypeImpl
	 def createTypeDefinition : TypeDefinition = new TypeDefinitionImpl
	 def createInstance : Instance = new InstanceImpl
	 def createLifeCycleTypeDefinition : LifeCycleTypeDefinition = new LifeCycleTypeDefinitionImpl
	 def createGroup : Group = new GroupImpl
	 def createGroupType : GroupType = new GroupTypeImpl
	 def createNodeType : NodeType = new NodeTypeImpl
	 def createAdaptationPrimitiveType : AdaptationPrimitiveType = new AdaptationPrimitiveTypeImpl

}
