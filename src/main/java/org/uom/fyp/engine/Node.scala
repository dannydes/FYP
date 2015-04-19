package org.uom.fyp.engine

/**
 * Node class
 */
class Node {

  private var nType = NodeType.ROAD_ENDING

  def nodeType: NodeType = nType

  def nodeType_(nType: NodeType) = {
    this.nType = nType
  }

}
