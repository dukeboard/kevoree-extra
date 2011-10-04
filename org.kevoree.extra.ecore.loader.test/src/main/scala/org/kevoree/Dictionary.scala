package org.kevoree;

/**
 * Created by Ecore Model Generator.
 * @author: Gregory NAIN
 * Date: 04 oct. 11 Time: 13:29
 * Meta-Model:NS_URI=http://kevoree/1.0
 */
trait Dictionary extends KevoreeContainer {
		private var values : List[DictionaryValue] = List[DictionaryValue]()


		def getValues : List[DictionaryValue] = {
				values
		}

		def setValues(values : List[DictionaryValue] ) {
				this.values = values
		}

		def addValues(values : DictionaryValue) {
				this.values = this.values ++ List(values)
		}

		def removeValues(values : DictionaryValue) {
				if(this.values.size != 0 ) {
						var nList = List[DictionaryValue]()
						this.values.foreach(e => if(!e.equals(values)) nList = nList ++ List(e))
						this.values = nList
				}
		}


}
