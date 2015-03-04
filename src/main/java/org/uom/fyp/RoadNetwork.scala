package org.uom.fyp

import java.util.Set

import org.jgrapht.DirectedGraph
import org.jgrapht.graph.DefaultDirectedGraph
import org.jgrapht.util.VertexPair

/**
 * Created by Daniel on 3/2/2015.
 */
class RoadNetwork extends IRoadNetwork {
  override var graph: DirectedGraph[Node, Lane] = new DefaultDirectedGraph(classOf[Lane])

  override def findAverageTime(nodes: VertexPair[Node]): Double = ???

  override def applyDiversion(): Unit = ???

  def getLane(streetName: String): Lane = {
    val lanes = graph.edgeSet.toArray
    for (lane <- lanes) {

    }
    return null
  }
}
