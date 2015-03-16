package org.uom.fyp.engine

import java.util

import org.jgrapht.graph.DefaultDirectedGraph

/**
 * Replaces JGraphT's DirectedGraphUnion class in such a way that better suites the project's structure.
 */
class UnifiedDirectedGraph(g1: DefaultDirectedGraph[_, _], g2: DefaultDirectedGraph[_, _]) extends DefaultDirectedGraph[Node, Lane](classOf[Lane]) {

  /**
   * Stores the new set of lanes.
   */
  var lanes = jSetUnion(g1.edgeSet, g2.edgeSet)

  /**
   * Stores the new set of nodes.
   */
  var nodes = jSetUnion(g2.vertexSet, g2.vertexSet)

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
