package org.uom.fyp.engine

import org.jgrapht.graph.DefaultEdge

/**
 * Lane class
 */
class Lane(l: Double, w: Double, s: Street) extends DefaultEdge {
  def lLen: Double = l
  def width: Double = w
  def street = s

  def attachLane(properties: AnyRef): Lane = NetworkUtils.createLane(this.getTarget.asInstanceOf[Node])
  def block(network: RoadNetwork) = {
    network.removeEdge(this)
  }
}
