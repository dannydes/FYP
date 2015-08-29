package org.uom.fyp.engine

/**
 * Represents a crossroad intersection.
 */
case class Crossroads() extends Node {

  private var ts1: Double = _
  private var ts2: Double = _

  /**
   * Sets the timing for one of the streets.
   * @param ts1 The time traffic will have to wait at the crossroad.
   */
  def timingStreet1_(ts1: Double) = {
    this.ts1 = ts1
  }

  /**
   * Returns the time traffic will have to wait at the crossroad on one of the streets.
   */
  def timingStreet1 = ts1

  /**
   * Sets the timing for the other street.
   * @param ts2 The time traffic will have to wait at the crossroad.
   */
  def timingStreet2_(ts2: Double) = {
    this.ts2 = ts2
  }

  /**
   * Returns the time traffic will have to wait at the crossroad on the other street.
   */
  def timingStreet2 = ts2

}
