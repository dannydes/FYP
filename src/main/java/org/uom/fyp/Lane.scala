package org.uom.fyp

import org.jgrapht.DirectedGraph
import org.jgrapht.graph.DefaultEdge

/**
 * Lane class
 */
class Lane(l: Double, w: Double, s: Street) extends DefaultEdge {
  def lLen: Double = l
  def width: Double = w
  def street = s

  def attachLane(properties: AnyRef): Lane = NetworkUtils.createLane(this.getTarget.asInstanceOf[Node])
  def block(streetName: String, network: DirectedGraph) = {}
}
