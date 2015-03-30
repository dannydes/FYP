package org.uom.fyp.engine

import org.jgrapht.graph.DefaultEdge

/**
 * Applies operations over lanes.
 * @param l Lane length.
 * @param w Lane width.
 * @param s Street to which lane belongs.
 */
class Lane(l: Double, w: Double, s: Street) extends DefaultEdge {

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

  def populate(noOfVehicles: Double) = {}

  def vehicleLeave = {}

  def veicleEnter = {}

}
