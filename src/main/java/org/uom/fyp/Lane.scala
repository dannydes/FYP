package org.uom.fyp

import scala.collection.mutable.Queue

/**
 * Created by Desira Daniel on 2/10/2015.
 */
class Lane(l: Double, w: Double) extends Queue[Vehicle] {
  def lLen: Double = l
  def width: Double = w
}
