package org.uom.fyp.engine

import org.jgrapht.util.VertexPair

/**
 * Provides signatures to methods that handle road networks.
 */
trait IRoadNetwork {

  /**
   * Returns the time expected to be taken for a vehicle to go from one
   * point to another.
   * @param vehicles Number of vehicles in system.
   */
  def initSimulation(vehicles: Int): Unit

  /**
   * Returns the propagation velocity of a shock wave.
   * @param qb Flow before change in conditions.
   * @param qa Flow after change in conditions.
   * @param kb Traffic density before change in conditions.
   * @param ka Traffic density after change in conditions.
   */
  def shockwave(qb : Double, qa : Double, kb : Double, ka : Double): Double

  /**
   * Creates and returns a lane auto-defining both vertices.
   * @param properties
   * @return Lane just created.
   */
  def createLane(properties: AnyRef): Lane

  def blockLane(streetName: String, laneNo: Int)

}
