package org.uom.fyp.engine

/**
 * Represents a generic node.
 */
class Node {

  private var congested: Boolean = _

  /**
   * Sets whether the node is congested.
   * @param congested Flag showing whether node is congested.
   */
  def isCongested_(congested: Boolean = true) = {
    this.congested = congested
  }

  /**
   * Returns whether the node is congested.
   */
  def isCongested = congested

}
