package org.kevoree;

/**
 * Created by Ecore Model Generator.
 * @author: Gregory NAIN
 * Date: 04 oct. 11 Time: 20:58
 * Meta-Model:NS_URI=http://kevoree/1.0
 */
trait Dictionary extends KevoreeContainer {
		private var values : List[DictionaryValue] = List[DictionaryValue]()


		def getValues : List[DictionaryValue] = {
				values
		}

		def setValues(values : List[DictionaryValue] ) {
				this.values = values
				values.foreach{e=>e.eContainer = this}

		}

		def addValues(values : DictionaryValue) {
				values.eContainer = this
				this.values = this.values ++ List(values)
		}

		def addAllValues(values : List[DictionaryValue]) {
				values.foreach{ elem => addValues(elem)}
		}

		def removeValues(values : DictionaryValue) {
				if(this.values.size != 0 ) {
						var nList = List[DictionaryValue]()
						this.values.foreach(e => if(!e.equals(values)) nList = nList ++ List(e))
						this.values = nList
				}
		}

		def removeAllValues() {
				this.values = List[DictionaryValue]()
		}


}
