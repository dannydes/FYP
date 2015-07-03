package org.uom.fyp.engine

import org.uom.fyp.engine.StreetType.StreetType

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
   * Creates a lane.
   * @param streetName
   * @param streetType
   * @param length
   */
  def createStreet(streetName: String, streetType: StreetType, length: Double, lanes: Int = 1): Street

  /**
   * Block lane found in the street with the given name.
   * @param streetName Street name.
   */
  def blockStreet(streetName: String)

  def addStreet(streetName: String, streetType: StreetType, length: Double, lanes: Int = 1): Street

  def buildGraph(street: Street, countStart: Int, source: Node): Unit

}
