package org.uom.fyp

import org.jgrapht.DirectedGraph
import org.jgrapht.graph.DefaultDirectedGraph
import org.jgrapht.util.VertexPair

/**
 * RoadNetwork class
 */
class RoadNetwork extends IRoadNetwork {
  override def findAverageTime(nodes: VertexPair[Node]): Double = ???

  override def applyDiversion(): Unit = ???

  def getLane(streetName: String): Lane = {
    val lanes = edgeSet.toArray
    for (lane: Lane <- lanes) {
      if (lane.street.name == streetName) {
        lane
      }
    }
    null
  }
}
