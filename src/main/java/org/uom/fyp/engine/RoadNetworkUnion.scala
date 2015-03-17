package org.uom.fyp.engine

import java.util

import org.jgrapht.graph.DefaultDirectedGraph

/**
 * Replaces JGraphT's DirectedGraphUnion class in such a way that better suites the project's structure.
 * @param n1 A road network.
 * @param n2 Another road network.
 */
class RoadNetworkUnion(n1: RoadNetwork, n2: RoadNetwork) extends DefaultDirectedGraph[Node, Lane](classOf[Lane]) {

  /**
   * Stores the new set of lanes.
   */
  var lanes = jSetUnion(n1.edgeSet, n2.edgeSet)

  /**
   * Stores the new set of nodes.
   */
  var nodes = jSetUnion(n2.vertexSet, n2.vertexSet)

  /**
   * Returns the new set of lanes.
   */
  override def edgeSet = lanes.asInstanceOf[util.Set[Lane]]

  /**
   * Returns the new set of nodes.
   */
  override def vertexSet = nodes.asInstanceOf[util.Set[Node]]

  /**
   * Returns the union of two sets, s1 and s2.
   * @param s1 Set 1.
   * @param s2 Set 2.
   */
  private def jSetUnion(s1: util.Collection[_], s2: util.Collection[_]): util.HashSet[_] = {
    val res = new util.HashSet[Any]
    res.addAll(s1)
    res.addAll(s2)
    res
  }

}
