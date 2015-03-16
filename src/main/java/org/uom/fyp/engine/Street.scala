package org.uom.fyp.engine

/**
 * Applies operations over streets.
 * @param streetName The name of the street.
 * @param streetType The type of the street.
 */
class Street(streetName: String, streetType: StreetType) {

  /**
   * Returns the name of the street.
   */
  def name = streetName

  /**
   * Returns the type of the street.
   */
  def sType = streetType

}
