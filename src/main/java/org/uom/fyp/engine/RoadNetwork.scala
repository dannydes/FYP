package org.uom.fyp.engine

import org.jgrapht.alg.HamiltonianCycle
import org.jgrapht.graph.DefaultDirectedWeightedGraph
import java.util

/**
 * Organises and simulates road networks.
 */
class RoadNetwork extends DefaultDirectedWeightedGraph[Node, Lane](classOf[Lane]) with IRoadNetwork {

  /**
   *
   * @param vehicles Number of vehicles in system.
   * @return
   */
  override def findTime(vehicles: Int): Double = {
    var time = 0.0
    val nodes: util.Set[Node] = vertexSet
    val nodeIterator = nodes.iterator()
    while (nodeIterator.hasNext) {
      val node = nodeIterator.next

      val inDegrees: util.Set[Lane] = incomingEdgesOf(node)
      val outDegrees: util.Set[Lane] = outgoingEdgesOf(node)
      val outDegreeIterator = outDegrees.iterator()
      while (outDegreeIterator.hasNext) {
        val lane = outDegreeIterator.next

      }
      //time += laneIterator.next().time
    }
    time
  }

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
