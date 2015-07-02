package org.uom.fyp.engine

/**
 * Node class
 */
class Node {

  private var nType: RoadStructure.EnumVal = RoadStructure.Default

  /**
   * Returns the node's type. (i.e. <b>NodeType.ROAD_ENDING</b>..
   */
  def nodeType: RoadStructure.EnumVal = nType

  /**
   * Sets the node's type.
   * @param nType The node's type.
   */
  def nodeType_(nType: RoadStructure.EnumVal) = {
    this.nType = nType
  }

}
