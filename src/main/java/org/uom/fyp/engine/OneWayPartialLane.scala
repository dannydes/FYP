package org.uom.fyp.engine

/**
 *
 * @param l Lane length.
 * @param w Lane width.
 */
class OneWayPartialLane(l: Double, w: Double, lambda: Double) extends PartialLane {

  super.lLen_(l)
  super.width_(w)
  super.arrivalRate_(lambda)

}
