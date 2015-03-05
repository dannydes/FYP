package org.uom.fyp

import org.jgrapht.graph.{DirectedGraphUnion, DefaultDirectedGraph}
import org.jgrapht.util.VertexPair

/**
 * RoadNetwork class
 */
class RoadNetwork extends DefaultDirectedGraph(classOf[Lane]) with IRoadNetwork {
  override def findAverageTime(nodes: VertexPair[Node]): Double = ???

  override def applyDiversion(): Unit = ???

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

  def blockLane(streetName: String) = {
    val lane = getLane(streetName)
    lane.block(this)
  }

  //def join(other: RoadNetwork) = new DirectedGraphUnion(this, other)
}
