package org.uom.fyp.engine

/**
 * Node class
 */
class Node {

  private var nType = NodeType.ROAD_ENDING

  /**
   * Returns the node's type. (i.e. <b>NodeType.ROAD_ENDING</b>..
   */
  def nodeType: NodeType = nType

  /**
   * Sets the node's type.
   * @param nType The node's type.
   */
  def nodeType_(nType: NodeType) = {
    this.nType = nType
  }

}
