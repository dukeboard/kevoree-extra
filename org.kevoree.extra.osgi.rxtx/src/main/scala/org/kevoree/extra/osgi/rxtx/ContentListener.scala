package org.kevoree.extra.osgi.rxtx

/**
 * User: ffouquet
 * Date: 12/08/11
 * Time: 16:48
 */

trait ContentListener {
  def recContent(content : String)
}