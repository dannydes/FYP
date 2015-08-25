package org.uom.fyp.engine

import org.jgrapht.graph.{ClassBasedEdgeFactory, ClassBasedVertexFactory}

/**
 * Contains static methods for use upon road networks.
 */
object NetworkUtils {

  /**
   * Returns an edge that is created and attached to the node given to the parameter,
   * if provided.
   * @param network The road network where the edge is to be created.
   * @param edgeType The type to be reflected on the edge's target.
   * @param start The starting node to which to attach the created edge is to be
   *              attached. When it is not provided, a new starting node is created
   *              and used in the creation of the new node instead.
   * @param end The target node, to be provided in case of a crossroad.
   * @return The edge that has been created.
   */
  def createEdge(network: RoadNetwork, edgeType: RoadStructure.RoadStructure, start: Node, end: Node = null): Edge = {
    var vertexFactory: ClassBasedVertexFactory[Node] = new ClassBasedVertexFactory(classOf[Node])

    val v1: Node = if (start == null) vertexFactory.createVertex() else start

    vertexFactory = if (edgeType == RoadStructure.TJunction) {
      new ClassBasedVertexFactory(classOf[TJunction])
    } else if (edgeType == RoadStructure.Roadabout) {
      new ClassBasedVertexFactory(classOf[Roundabout])
    } else if (edgeType == RoadStructure.Crossroads) {
      new ClassBasedVertexFactory(classOf[Crossroads])
    } else {
      new ClassBasedVertexFactory(classOf[Node])
    }

    val v2: Node = if (end == null) vertexFactory.createVertex() else end
    network.addVertex(v1)
    network.addVertex(v2)
    val edgeFactor: ClassBasedEdgeFactory[Node, Edge] = new ClassBasedEdgeFactory(classOf[Edge])
    val edge: Edge = edgeFactor.createEdge(v1, v2)
    network.addEdge(v1, v2, edge)

    edge
  }

  /**
   * Intialises the new edge's properties.
   * @param e1 The actaul edge from the array.
   * @param e2 The new edge.
   * @param lanes The number of lanes of a street.
   * @param edgeNo The number that the edge is to be assigned.
   */
  def initEdgeProperties(e1: Edge, e2: Edge, lanes: Int, edgeNo: Int) = {
    e2.streetName_(e1.streetName)
    e2.length_(e1.length)
    e2.streetLanes_(lanes)
    e2.streetEdgeNo_(edgeNo)
  }

}
