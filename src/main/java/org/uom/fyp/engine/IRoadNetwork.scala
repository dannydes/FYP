package org.uom.fyp.engine

import org.uom.fyp.engine.StreetType.StreetType

/**
 * Provides signatures to methods that handle road networks.
 */
trait IRoadNetwork {

  /**
   * Simulates the road network's operation according to the specified parameters.
   * @param minutes The time in minutes for which the simulation will be allowed to run.
   */
  def simulate(minutes: Double): Unit

  /**
   * Returns the propagation velocity of a shock wave.
   * @param qb Flow before change in conditions.
   * @param qa Flow after change in conditions.
   * @param kb Traffic density before change in conditions.
   * @param ka Traffic density after change in conditions.
   */
  def shockwave(qb : Double, qa : Double, kb : Double, ka : Double): Double

  /**
   * Creates a lane and returns a reference to the object created.
   * @param streetName The name of the street to which the lane belongs.
   * @param streetType The type of the street to which the lane belongs. Can be <b>StreetType.PRIMARY</b>
   *                   or <b>StreetType.SECONDARY</b>.
   * @param length The lane's length.
   * @param vehicles
   * @param lanes
   * @return Reference to the lane object just created.
   */
  def createStreet(streetName: String, streetType: StreetType, length: Double, vehicles: Int, arrivalRate: Double, lanes: Int = 1): Street

  /**
   * Block the street with the given name.
   * @param streetName Street name.
   */
  def blockStreet(streetName: String)

  /**
   * Creates a <b>Street</b> object, appends it to the <b>streets</b> list and
   * returns it.
   * @param streetName Name of the street.
   * @param streetType Type of the street (i.e. <b>StreetType.PRIMARY</b> or <b>StreetType.SECONDARY</b>).
   * @return The <b>Street</b> object created.
   */
  def addStreet(streetName: String, streetType: StreetType, length: Double, vehicles: Int, arrivalRate: Double, lanes: Int = 1): Street

  /**
   * Builds the graph declared in the model by recursively going through the ficticious
   * edge list created by the precedent stages. Hence, this method should be called after
   * all the other processes have took place.<br/>
   * For the algorithm to work as it should, all arguments should be omitted so as to pass
   * their respective default values.
   * @param street The current lane. Starts from the first occurring lane in the first occurring
   *               street if omitted.
   * @param countStart The index at which the intersecting edge is found in the list. Is
   *                   assigned 0 if omitted.
   * @param source The source where to attach the next edge in the graph. Starts as null if
   *               omitted.
   */
  def buildGraph(street: Street, countStart: Int, source: Node): Unit

  /**
   * Reacts to the type of the given node.
   * @param node Node to be processed.
   */
  def processNode(node: Node): Unit

}
