package org.uom.fyp.engine

/**
 * Defines operations over vehicles.
 * @param l Length of vehicle.
 * @param w Width of vehicle.
 */
class Vehicle(l: Double, w: Double) {

  /**
   * Stores speed.
   */
  var s: Int = 0

  /**
   * Returns length of vehicle.
   */
  def len: Double = l

  /**
   * Returns width of vehicle.
   */
  def width: Double = w

  /**
   * Returns speed of vehicle.
   */
  def speed = s

  /**
   * Sets speed of vehicle.
   * @param s Speed of vehicle.
   */
  def speed_=(s: Int) {
    this.s = s
  }
  
}
