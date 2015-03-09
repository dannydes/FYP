package org.uom.fyp.engine

import org.jgrapht.graph.{ClassBasedEdgeFactory, ClassBasedVertexFactory}
import org.uom.fyp.engine.Lane

/**
 * Static methods for use upon road networks
 */
object NetworkUtils {
  def createLane(start: Node = null): Lane = {
    val vertexFactory: ClassBasedVertexFactory[Node] = new ClassBasedVertexFactory(classOf[Node])
    var v1: Node = null
    if (start == null) {
      v1 = vertexFactory.createVertex()
    } else {
      v1 = start
    }
    val v2: Node = vertexFactory.createVertex()
    val edgeFactor: ClassBasedEdgeFactory[Node, Lane] = new ClassBasedEdgeFactory(classOf[Lane])
    edgeFactor.createEdge(v1, v2)
  }
}
