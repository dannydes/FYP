package org.uom.fyp.engine

/**
 *
 * @param l Lane length.
 * @param w Lane width.
 * @param s Street to which lane belongs.
 */
class OneWayLane(l: Double, w: Double, s: Street, lambda: Double) extends Lane {

  super.lLen_(l)
  super.width_(w)
  super.street_(s)
  super.arrivalRate_(lambda)

}
