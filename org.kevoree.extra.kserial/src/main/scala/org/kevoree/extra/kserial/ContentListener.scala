package org.kevoree.extra.kserial

/**
 * User: ffouquet
 * Date: 12/08/11
 * Time: 16:48
 */

trait ContentListener {
  def recContent(content: String)
}