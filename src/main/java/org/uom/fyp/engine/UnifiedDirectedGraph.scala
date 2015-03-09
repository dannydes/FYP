package org.uom.fyp.engine

import java.util.HashSet

import org.jgrapht.graph.DefaultDirectedGraph

/**
 * Created by Daniel on 3/6/2015.
 */
class UnifiedDirectedGraph(g1: DefaultDirectedGraph[Node, Lane], g2: DefaultDirectedGraph[Node, Lane]) extends DefaultDirectedGraph[Node, Lane](classOf[Lane]) {
  jSetUnion(g1.edgeSet, g2.edgeSet)
  jSetUnion(g2.vertexSet, g2.vertexSet)

  private def jSetUnion(s1: java.util.Set, s2: java.util.Set): HashSet = {
    val res = new HashSet
    res.addAll(s1)
    res.addAll(s2)
    res
  }
}
