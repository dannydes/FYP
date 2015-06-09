package org.uom.fyp.engine

/**
 * Created by Desira Daniel on 2/10/2015.
 */
class TwoWayEdge(l: Double, w: Double, lambda: Double) extends Edge {

  super.length_(l)
  super.width_(w)
  super.arrivalRate_(lambda)

  /**var lSide: Boolean = true

  def leftSide = lSide

  def changeSide(): Unit = {
    lSide = ! lSide
    super.reverse
  }**/
}
