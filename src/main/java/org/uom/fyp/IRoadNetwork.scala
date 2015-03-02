package org.uom.fyp

import org.jgrapht.DirectedGraph
import org.jgrapht.util.VertexPair

/**
 * Created by Daniel on 11/30/2014.
 */
trait IRoadNetwork {
  var graph : DirectedGraph[Node, Lane]

  def applyDiversion() : Unit
  def findAverageTime(nodes : VertexPair[Node]) : Double

  private def shockwave(qb : Double, qa : Double, kb : Double, ka : Double) : Double = (qb - qa) / (kb - ka)

  def createLane(properties: AnyRef): Lane = NetworkUtils.createLane()
}
