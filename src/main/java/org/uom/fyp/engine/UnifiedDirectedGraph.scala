package org.uom.fyp.engine

import java.util

import org.jgrapht.graph.DefaultDirectedGraph

/**
 * Created by Daniel on 3/6/2015.
 */
class UnifiedDirectedGraph(g1: DefaultDirectedGraph[Node, Lane], g2: DefaultDirectedGraph[Node, Lane]) extends DefaultDirectedGraph[Node, Lane](classOf[Lane]) {
  jSetUnion(g1.edgeSet, g2.edgeSet)
  jSetUnion(g2.vertexSet, g2.vertexSet)

  private def jSetUnion(s1: util.Collection[_], s2: util.Collection[_]): util.HashSet[_] = {
    val res = new util.HashSet[Any]
    res.addAll(s1)
    res.addAll(s2)
    res
  }
}
