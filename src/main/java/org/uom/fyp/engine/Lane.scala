package org.uom.fyp.engine

import com.perfdynamics.pdq._
import org.jgrapht.graph.DefaultEdge

/**
 * Applies operations over lanes.
 * @param l Lane length.
 * @param w Lane width.
 * @param s Street to which lane belongs.
 */
class Lane(l: Double, w: Double, s: Street, lambda: Double) extends DefaultEdge {

  private var vehicles: Int = 0

  /**
   * Returns lane length.
   */
  def lLen: Double = l

  /**
   * Returns lane width.
   */
  def width: Double = w

  /**
   * Returns street to which lane belongs.
   */
  def street = s

  def noOfVehicles = vehicles

  def noOfVehicles_(vehicles: Int) = {
    this.vehicles = vehicles
  }

  /**
   * Attaches and returns a new lane to the context lane.
   * @param properties
   * @return The lane just attached.
   */
  def attachLane(properties: AnyRef): Lane = NetworkUtils.createLane(this.getTarget.asInstanceOf[Node])

  /**
   * Blocks the context lane.
   * @param network The network from which to remove the lane.
   */
  def block(network: RoadNetwork): Unit = network.removeEdge(this)

  private def arrivalRate = lambda

  private def departureRate = lambda / (1 - noOfVehicles)

  def time: Double = {
    1 / (departureRate - lambda)
  }

  def simulate = {
    val pdq: PDQ = new PDQ
    pdq.CreateNode(s.name + toString, defs.CEN, defs.FCFS)
    pdq.CreateOpen(s.name + toString + "load", 0.0)
    pdq.SetVisits(s.name + toString, s.name + toString + "load", noOfVehicles, 0.1)
  }

}
