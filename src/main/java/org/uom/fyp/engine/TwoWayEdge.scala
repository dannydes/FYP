package org.uom.fyp.engine

/**
 * Created by Desira Daniel on 2/10/2015.
 */
class TwoWayEdge(len: Double, lambda: Double) extends Edge {

  super.length_(len)
  super.arrivalRate_(lambda)

  /**var lSide: Boolean = true

  def leftSide = lSide

  def changeSide(): Unit = {
    lSide = ! lSide
    super.reverse
  }**/
}
