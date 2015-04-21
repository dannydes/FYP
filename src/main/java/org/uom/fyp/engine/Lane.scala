
package org.uom.fyp.engine

import com.perfdynamics.pdq._
import org.jgrapht.graph.DefaultEdge

/**
 * Applies operations over lanes.
 */
class Lane extends DefaultEdge {

  /**
   * Stores the number of vehicles in lane.
   */
  private var vehicles: Int = 0

  /**
   * Stores lane length.
   */
  private var l = 0.0

  /**
   * Stores lane width.
   */
  private var w = 0.0

  /**
   * Stores the arrival rate for this lane.
   */
  private var lambda = 0.0

  /**
   * Returns lane length.
   */
  def lLen: Double = l

  /**
   * Sets lane length.
   * @param l Lane length.
   */
  def lLen_(l: Double) = {
    this.l = l
  }

  /**
   * Returns lane width.
   */
  def width: Double = w

  /**
   * Sets lane width.
   * @param w Lane width.
   */
  def width_(w: Double) = {
    this.w = w
  }

  /**
   * Returns the number of vehicles in lane.
   */
  def noOfVehicles = vehicles

  /**
   * Sets the number of vehicles in lane.
   * @param vehicles The number of vehicles in lane.
   */
  def noOfVehicles_(vehicles: Int) = {
    this.vehicles = vehicles
  }

  /**
   * Attaches and returns a new lane to the context lane.
   * @param properties
   * @return The lane just attached.
   */
  def attachLane(network: RoadNetwork, properties: AnyRef): Lane = NetworkUtils.createLane(network, this.getTarget)

  /**
   * Blocks the context lane.
   * @param network The network from which to remove the lane.
   */
  def block(network: RoadNetwork): Unit = network.removeEdge(this)

  /**
   * Returns the lane's arrival rate.
   */
  private def arrivalRate = lambda

  /**
   * Sets the lane's arrival rate.
   * @param lambda The lane's arrival rate.
   */
  def arrivalRate_(lambda: Double) = {
    this.lambda = lambda
  }

  /**
   * Returns the lane's departure rate.
   */
  def departureRate = lambda / (1 - noOfVehicles)

  /**
   * Returns the time taken for one job (vehicle) to be serviced.
   */
  def time: Double = {
    1 / (departureRate - lambda)
  }

  /**
   * Simulates traffic going through the lane.
   */
  def simulate() = {
    val pdq: PDQ = new PDQ
    pdq.CreateNode(toString, defs.CEN, defs.FCFS)
    pdq.CreateOpen(toString + "load", lambda)
    pdq.SetVisits(toString, toString + "load", noOfVehicles, time)
    pdq.SetTUnit("Minutes")
    pdq.SetWUnit("Vehicles")
    pdq.Solve(defs.CANON)
    pdq.Report()
  }

  override def getSource = {
    super.getSource.asInstanceOf[Node]
  }

  override def getTarget = {
    super.getTarget.asInstanceOf[Node]
  }

}
