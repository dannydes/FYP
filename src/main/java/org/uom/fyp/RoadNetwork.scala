package org.uom.fyp

import org.jgrapht.util.VertexPair

/**
 * Created by Daniel on 11/30/2014.
 */
trait RoadNetwork {

  def applyDiversion() : Any
  def findAverageTime(nodes : VertexPair[Node]) : Double

  private def shockwave(qb : Double, qa : Double, kb : Double, ka : Double) : Double = (qb - qa) / (kb - ka)
}
