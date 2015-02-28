package org.uom.fyp

import org.jgrapht.graph.{ClassBasedEdgeFactory, ClassBasedVertexFactory}

/**
 * Created by Daniel on 2/26/2015.
 */
object NetworkUtils {
  def createLane(start: Node = null): Lane = {
    val vertexFactory: ClassBasedVertexFactory[Node] = new ClassBasedVertexFactory("Node".getClass)
    var v1: Node = null
    if (start == null) {
      v1 = vertexFactory.createVertex()
    } else {
      v1 = start
    }
    val v2: Node = vertexFactory.createVertex()
    val edgeFactor: ClassBasedEdgeFactory[Node, Lane] = new ClassBasedEdgeFactory("Lane".getClass)
    val lane: Lane = edgeFactor.createEdge(v1, v2)

    lane
  }
}
