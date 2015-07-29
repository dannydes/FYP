package org.uom.fyp.engine

import org.uom.fyp.engine.StreetType.StreetType

/**
 * Applies operations over streets.
 * @param streetName The name of the street.
 * @param sType The type of the street.
 * @param len The length of the street.
 * @param lanes The number of lanes the street has.
 */
class Street(streetName: String, sType: StreetType, len: Double, vehicles: Int, lanes: Int = 1) {

  /**
   * Stores edges that are generated by <b>addStreet()</b>.
   */
  private var e: List[Edge] = List()

  /**
   * Returns the name of the street.
   */
  def name = streetName

  /**
   * Returns the type of the street.
   */
  def streetType = sType

  /**
   * Returns the length of the street.
   */
  def length = len

  def noOfVehicles = vehicles

  /**
   * Returns the number of lanes the street has.
   */
  def noOfLanes = lanes

  def speed = {
    edges.map((edge: Edge) => edge.length).sum / edges.map((edge: Edge) => edge.time).sum
  }

  /**
   * Adds an edge to the list in order to be added to the graph later on.
   * @param otherAttachedAt The point where the attachment takes place.
   * @param nextOtherAttachedAt The next point where the attachment takes place.
   *                            Defaults to the length of the context length if
   *                            omitted.
   * @return The edge that has been created and added to the graph.
   */
  def addEdge(otherAttachedAt: Double, nextOtherAttachedAt: Double = len): Edge = {
    if (nextOtherAttachedAt > len) {
      throw new StreetLengthExceededException(nextOtherAttachedAt)
    }

    val edge: Edge = new Edge
    edge.length_(nextOtherAttachedAt - otherAttachedAt)
    edge.streetName_(streetName)
    e = e ++ List(edge)

    edge
  }

  /**
   * Returns a list containing the edges in this street (generated due to
   * intersections).
   */
  def edges = e

  /**
   * Initializes values to be used during simulation.
   * @param vehicles The number of vehicles fed into the street.
   * @param arrivalRate The street's arrival rate.
   */
  def initialize(vehicles: Int, arrivalRate: Double) = {
    val pFlowInOut = vehicles / e.length
    var v = vehicles
    e.foreach((edge: Edge) => {
      edge.noOfVehicles_(v)
      v -= pFlowInOut
    })
  }

  /**
   * Creates and attaches an outgoing street to the context street.
   * @param network The network where the street will be attached.
   * @param streetName The name of the street to be attached.
   * @param streetType The type of the street to be attached.
   * @param length The length the street to be attached.
   * @param point The point where the street is to be attached with respect to
   *              the starting point of the other.
   * @return The street that has been created.
   */
  def attachStreet(network: RoadNetwork, streetName: String, streetType: StreetType,
                 length: Double, point: Double, flow: Double): Street = {
    val street: Street = network.addStreet(streetName, streetType, length, flow)

    var otherAt = 0.0
    if (this.e.length != 0) {
      val prev: Edge = this.e(this.e.length - 1)
      otherAt = prev.intersectionPoint + prev.length
      prev.edgeT_(RoadStructure.TJunction)
    }

    val edge: Edge = addEdge(otherAt, point)
    edge.streetAtTarget_(street)
    edge.otherIntersectionPoint_(0)
    edge.edgeT_(RoadStructure.TJunction)

    street
  }

  /**
   * Creates the last edge in a road by determining the position of the last
   * intersection on that road. This method should be called after all road
   * attachments in the model have taken place upon the context road. In case
   * no attachments have been carried out on the context road, a single edge
   * would be created.
   */
  def createLastEdge() = {
    if (e.length == 0) {
      addEdge(0, length)
    } else {
      val lastEdge: Edge = e(e.length - 1)
      val point = lastEdge.intersectionPoint + lastEdge.length
      if (point < length) {
        val edge: Edge = addEdge(point, length)
      }
    }
  }

  /**
   * Returns the edge on which a given point is found.
   * @param point A point along the road.
   */
  def getEdge(point: Double): Edge = {
    e.filter((edge: Edge) => edge.intersectionPoint <= point && edge.intersectionPoint + edge.length >= point)(0)
  }

  /**
   * Blocks the street.
   * @param network The road network in which the street is found.
   */
  def block(network: RoadNetwork): Unit = {
    e.foreach((edge: Edge) => network.removeEdge(edge))
  }

  /**
   * Prepares for he creation of a roundabout node.
   * @param at Position in the street where to place the roundabout.
   */
  def createRoundabout(at: Double) = {
    val edge: Edge = getEdge(at)
    val oldLength = edge.length
    edge.length_(oldLength - edge.intersectionPoint - at)
    edge.edgeT_(RoadStructure.Roadabout)
    val newEdge: Edge = addEdge(at, oldLength - edge.length)
  }

}
