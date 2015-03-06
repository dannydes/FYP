package org.uom.fyp

import org.jgrapht.graph.DefaultDirectedGraph

/**
 * Created by Daniel on 3/6/2015.
 */
class UnifiedDirectedGraph(g1: DefaultDirectedGraph[Node, Lane], g2: DefaultDirectedGraph[Node, Lane]) extends DefaultDirectedGraph[Node, Lane](classOf[Lane]) {
  val g1EdgeIterator = g1.edgeSet.iterator
  val g2EdgeIterator = g2.edgeSet.iterator
  while (g1EdgeIterator.hasNext || g2EdgeIterator.hasNext) {

  }

  val g1VertexIterator = g1.vertexSet.iterator
  val g2VertexIterator = g2.vertexSet.iterator
  while (g1VertexIterator.hasNext || g2VertexIterator.hasNext) {

  }
}
