package org.uom.fyp

/**
 * Vehicle class
 */
class Vehicle(l: Double, w: Double) {
  var s: Int = 0

  def len: Double = l
  def width: Double = w

  def speed = s
  def speed_=(s: Int) {
    this.s = s
  }
}
