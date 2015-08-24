package org.uom.fyp.engine

import org.jgrapht.graph.{ClassBasedEdgeFactory, ClassBasedVertexFactory}

/**
 * Contains static methods for use upon road networks.
 */
object NetworkUtils {

  /**
   * Returns an edge that is created and attached to the node given to the parameter,
   * if provided.
   * @param start The starting node to which to attach the created edge is to be
   *              attached. When it is not provided, a new starting node is created
   *              and used in the creation of the new node instead.
   * @return The edge that has been created.
   */
  def createEdge(network: RoadNetwork, edgeType: RoadStructure.RoadStructure, start: Node, end: Node = null): Edge = {
    var vertexFactory: ClassBasedVertexFactory[Node] = new ClassBasedVertexFactory(classOf[Node])

    val v1: Node = if (start == null) vertexFactory.createVertex() else start

    if (edgeType == RoadStructure.TJunction) {
      vertexFactory = new ClassBasedVertexFactory(classOf[TJunction])
    } else if (edgeType == RoadStructure.Roadabout) {
      vertexFactory = new ClassBasedVertexFactory(classOf[Roundabout])
    } else if (edgeType == RoadStructure.Crossroads) {
      vertexFactory = new ClassBasedVertexFactory(classOf[Crossroads])
    }

    val v2: Node = if (end == null) vertexFactory.createVertex() else end
    network.addVertex(v1)
    network.addVertex(v2)
    val edgeFactor: ClassBasedEdgeFactory[Node, Edge] = new ClassBasedEdgeFactory(classOf[Edge])
    val edge: Edge = edgeFactor.createEdge(v1, v2)
    network.addEdge(v1, v2, edge)

    edge
  }

  def initEdgeProperties(e1: Edge, e2: Edge, lanes: Int, edgeNo: Int) = {
    e2.streetName_(e1.streetName)
    e2.length_(e1.length)
    e2.streetLanes_(lanes)
    e2.streetEdgeNo_(edgeNo)
  }

}
