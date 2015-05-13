package org.uom.fyp.engine

/**
 *
 * @param l Lane length.
 * @param w Lane width.
 */
class OneWayLaneSlice(l: Double, w: Double, lambda: Double) extends LaneSlice {

  super.length_(l)
  super.width_(w)
  super.arrivalRate_(lambda)

}
