package org.uom.fyp.engine

/**
 * Node class
 */
class Node {

  private var nType: RoadStructure.EnumVal = RoadStructure.Default

  private var timing: Double = _

  /**
   * Returns the node's type. (e.g.: <b>RoadStructure.Default</b>)
   */
  def nodeType: RoadStructure.EnumVal = nType

  /**
   * Sets the node's type.
   * @param nType The node's type.
   */
  def nodeType_(nType: RoadStructure.EnumVal) = {
    this.nType = nType
  }

  def lightTiming = timing

  def lightTiming_(timing: Double) = {
    this.timing = timing
  }

}
