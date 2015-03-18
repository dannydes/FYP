package org.uom.fyp.engine

import org.jgrapht.util.VertexPair

/**
 * Created by Daniel on 11/30/2014.
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
   *
   * @param qb
   * @param qa
   * @param kb
   * @param ka
   * @return
   */
  private def shockwave(qb : Double, qa : Double, kb : Double, ka : Double) : Double = (qb - qa) / (kb - ka)

  /**
   * Creates and returns a lane auto-defining both vertices.
   * @param properties
   * @return Lane just created.
   */
  def createLane(properties: AnyRef): Lane = NetworkUtils.createLane()

}
