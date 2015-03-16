package org.uom.fyp.engine

import org.jgrapht.graph.DefaultDirectedGraph
import org.jgrapht.util.VertexPair

/**
 * Organises and simulates road networks.
 */
class RoadNetwork extends DefaultDirectedGraph[Node, Lane](classOf[Lane]) with IRoadNetwork {
  override def findAverageTime(nodes: VertexPair[Node]): Double = ???

  override def applyDiversion(): Unit = ???

  /**
   * Returns the lane found in a street with the given name.
   * @param streetName Street name.
   * @return Lane found in the given street.
   */
  def getLane(streetName: String): Lane = {
    val lanes = edgeSet
    val iterator = lanes.iterator
    while (iterator.hasNext) {
      val lane = iterator.next
      if (lane.street.name == streetName) {
        lane
      }
    }
    null
  }

  /**
   * Block lane found in the street with the given name.
   * @param streetName Street name.
   */
  def blockLane(streetName: String) = {
    val lane = getLane(streetName)
    lane.block(this)
  }

  /**
   * Returns the union of this road network to the one passed as a parameter.
   * @param other The road network to join to this network.
   * @return The union of the two road networks.
   */
  def join(other: RoadNetwork) = new RoadNetworkUnion(this, other)
}
