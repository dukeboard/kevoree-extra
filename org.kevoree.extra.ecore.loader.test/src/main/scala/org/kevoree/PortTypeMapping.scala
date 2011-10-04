package org.kevoree;

/**
 * Created by Ecore Model Generator.
 * @author: Gregory NAIN
 * Date: 04 oct. 11 Time: 20:58
 * Meta-Model:NS_URI=http://kevoree/1.0
 */
trait PortTypeMapping extends KevoreeContainer {
		private var beanMethodName : java.lang.String = ""

		private var serviceMethodName : java.lang.String = ""


		def getBeanMethodName : java.lang.String = {
				beanMethodName
		}

		def setBeanMethodName(beanMethodName : java.lang.String) {
				this.beanMethodName = beanMethodName
		}

		def getServiceMethodName : java.lang.String = {
				serviceMethodName
		}

		def setServiceMethodName(serviceMethodName : java.lang.String) {
				this.serviceMethodName = serviceMethodName
		}

}
