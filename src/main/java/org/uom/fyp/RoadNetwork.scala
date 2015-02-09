package org.uom.fyp

/**
 * Created by Daniel on 11/30/2014.
 */
trait RoadNetwork {
  def applyDiversion() : Any
  def findAverageTime() : Double

  private def shockwave(qb : Double, qa : Double, kb : Double, ka : Double) : Double = (qb - qa) / (kb - ka)
}
