package org.uom.fyp

/**
 * Created by Desira Daniel on 2/10/2015.
 */
class TwoWayLane(l: Double, w: Double) extends Lane(l, w) {
  var lSide: Boolean = true

  def leftSide = lSide
  def leftSide_=(lSide: Boolean) {
    this.lSide = lSide
  }

  def serve =
    if (leftSide) {

    } else {

    }
}
