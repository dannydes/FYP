package org.uom.fyp

import org.jgrapht.graph.DefaultDirectedGraph

/**
 * Created by Daniel on 3/6/2015.
 */
class UnifiedDirectedGraph(g1: DefaultDirectedGraph[Node, Lane], g2: DefaultDirectedGraph[Node, Lane]) extends DefaultDirectedGraph[Node, Lane](classOf[Lane]) {

}
