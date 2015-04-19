package org.uom.fyp.engine

/**
 * Created by Desira Daniel on 2/10/2015.
 */
class TwoWayLane(l: Double, w: Double, s: Street, lambda: Double) extends Lane {

  super.lLen_(l)
  super.width_(w)
  super.street_(s)
  super.arrivalRate_(lambda)

  /**var lSide: Boolean = true

  def leftSide = lSide

  def changeSide(): Unit = {
    lSide = ! lSide
    super.reverse
  }**/
}
