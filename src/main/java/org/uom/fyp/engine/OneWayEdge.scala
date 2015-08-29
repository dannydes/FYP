package org.uom.fyp.engine

/**
 *
 * @param len Edge length.
 */
class OneWayEdge(len: Double, lambda: Double) extends Edge {

  super.length_(len)
  super.arrivalRateL_(lambda)

}
