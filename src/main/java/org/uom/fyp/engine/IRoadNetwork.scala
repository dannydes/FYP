package org.uom.fyp.engine

import org.jgrapht.util.VertexPair

/**
 * Created by Daniel on 11/30/2014.
 */
trait IRoadNetwork {
  def applyDiversion() : Unit
  def findAverageTime(nodes : VertexPair[Node]) : Double

  private def shockwave(qb : Double, qa : Double, kb : Double, ka : Double) : Double = (qb - qa) / (kb - ka)

  def createLane(properties: AnyRef): Lane = NetworkUtils.createLane()
}
