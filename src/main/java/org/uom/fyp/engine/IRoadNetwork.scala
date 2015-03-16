package org.uom.fyp.engine

import org.jgrapht.util.VertexPair

/**
 * Created by Daniel on 11/30/2014.
 */
trait IRoadNetwork {
  /**
   *
   */
  def applyDiversion() : Unit

  /**
   *
   * @param nodes
   * @return
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
   *
   * @param properties
   * @return
   */
  def createLane(properties: AnyRef): Lane = NetworkUtils.createLane()

}
