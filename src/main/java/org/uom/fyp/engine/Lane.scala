package org.uom.fyp.engine

import com.perfdynamics.pdq._
import org.jgrapht.graph.DefaultEdge

/**
 * Applies operations over lanes.
 * @param l Lane length.
 * @param w Lane width.
 * @param s Street to which lane belongs.
 */
class Lane(l: Double, w: Double, s: Street) extends DefaultEdge {

  private val pdq = new PDQ()

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

  private def createServer = {
    pdq.CreateNode(s.name + toString, defs.CEN, defs.FCFS)
    //pdq.CreateOpen(s.name + toString + "load", 0.0)
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

  def populate(noOfVehicles: Double) = {
    pdq.SetVisits(s.name + toString, s.name + toString + "load", noOfVehicles, )
  }

}
