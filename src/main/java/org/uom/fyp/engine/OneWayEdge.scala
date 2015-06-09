package org.uom.fyp.engine

/**
 *
 * @param l Lane length.
 * @param w Lane width.
 */
class OneWayEdge(l: Double, w: Double, lambda: Double) extends Edge {

  super.length_(l)
  super.width_(w)
  super.arrivalRate_(lambda)

}
