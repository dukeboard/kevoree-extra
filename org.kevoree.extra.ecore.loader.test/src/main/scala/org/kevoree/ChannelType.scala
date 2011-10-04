package org.kevoree;

/**
 * Created by Ecore Model Generator.
 * @author: Gregory NAIN
 * Date: 04 oct. 11 Time: 20:58
 * Meta-Model:NS_URI=http://kevoree/1.0
 */
trait ChannelType extends KevoreeContainer with LifeCycleTypeDefinition {
		private var lowerBindings : java.lang.Integer = 0

		private var upperBindings : java.lang.Integer = 0

		private var lowerFragments : java.lang.Integer = 0

		private var upperFragments : java.lang.Integer = 0


		def getLowerBindings : java.lang.Integer = {
				lowerBindings
		}

		def setLowerBindings(lowerBindings : java.lang.Integer) {
				this.lowerBindings = lowerBindings
		}

		def getUpperBindings : java.lang.Integer = {
				upperBindings
		}

		def setUpperBindings(upperBindings : java.lang.Integer) {
				this.upperBindings = upperBindings
		}

		def getLowerFragments : java.lang.Integer = {
				lowerFragments
		}

		def setLowerFragments(lowerFragments : java.lang.Integer) {
				this.lowerFragments = lowerFragments
		}

		def getUpperFragments : java.lang.Integer = {
				upperFragments
		}

		def setUpperFragments(upperFragments : java.lang.Integer) {
				this.upperFragments = upperFragments
		}

}
