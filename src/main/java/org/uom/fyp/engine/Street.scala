package org.uom.fyp.engine

/**
 * Applies operations over streets.
 * @param streetName The name of the street.
 * @param sType The type of the street.
 */
class Street(streetName: String, sType: StreetType) {

  /**
   * Stores a list of lanes found in this street.
   */
  private var lanesList: List[Lane] = List()

  /**
   * Returns the name of the street.
   */
  def name = streetName

  /**
   * Returns the type of the street.
   */
  def streetType = sType

  /**
   * Adds a new lane to this street object.
   */
  def addLane(lane: Lane) = {
    lanesList = lanesList ++ List(lane)
  }

  /**
   * Returns a list of lanes, that are found in this street.
   */
  def lanes = lanesList

}
