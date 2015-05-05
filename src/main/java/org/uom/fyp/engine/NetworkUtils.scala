package org.uom.fyp.engine

import org.jgrapht.graph.{ClassBasedEdgeFactory, ClassBasedVertexFactory}

/**
 * Contains static methods for use upon road networks.
 */
object NetworkUtils {

  /**
   * Returns a lane that is created and attached to the node given to the parameter,
   * if provided.
   * @param start The starting node to which to attach the created lane is to be
   *              attached. When it is not provided, a new starting node is created
   *              and used in the creation of the new node instead.
   * @return The lane that has been created.
   */
  def createLane(network: RoadNetwork, start: Node = null): PartialLane = {
    val vertexFactory: ClassBasedVertexFactory[Node] = new ClassBasedVertexFactory(classOf[Node])
    var v1: Node = null
    if (start == null) {
      v1 = vertexFactory.createVertex()
    } else {
      v1 = start
    }
    val v2: Node = vertexFactory.createVertex()
    network.addVertex(v1)
    network.addVertex(v2)
    val edgeFactor: ClassBasedEdgeFactory[Node, PartialLane] = new ClassBasedEdgeFactory(classOf[PartialLane])
    val lane: PartialLane = edgeFactor.createEdge(v1, v2)
    network.addEdge(v1, v2, lane)

    lane
  }

}
