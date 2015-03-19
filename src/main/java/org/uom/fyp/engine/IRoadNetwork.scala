package org.uom.fyp.engine

import org.jgrapht.util.VertexPair

/**
 *
 */
trait IRoadNetwork {

  /**
   * Returns the time expected to be taken for a vehicle to go from one
   * point to another.
   * @param nodes
   * @return Time to be taken to travel from a node to the other.
   */
  def findAverageTime(nodes : VertexPair[Node]) : Double

  /**
   * Returns the propagation velocity of a shock wave.
   * @param qb Flow before change in conditions.
   * @param qa Flow after change in conditions.
   * @param kb Traffic density before change in conditions.
   * @param ka Traffic density after change in conditions.
   */
  private def shockwave(qb : Double, qa : Double, kb : Double, ka : Double) : Double = (qb - qa) / (kb - ka)

  /**
   * Creates and returns a lane auto-defining both vertices.
   * @param properties
   * @return Lane just created.
   */
  def createLane(properties: AnyRef): Lane = NetworkUtils.createLane()

}
