package org.uom.fyp

/**
 * Created by Desira Daniel on 2/10/2015.
 */
class Vehicle(l: Double, w: Double) {
  var s: Int = 0

  def len: Double = l
  def width: Double = w

  def speed = s
  def speed_(s: Int): Unit = {}
}
