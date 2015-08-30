package org.uom.fyp.engine

import org.uom.fyp.engine.StreetType.StreetType

/**
 * Applies operations over streets.
 * @param streetName The name of the street.
 * @param sType The type of the street.
 * @param len The length of the street.
 * @param vehiclePopL The number of vehicles in the street coming from the left.
 * @param lambdaL The street's arrival rate from the left.
 * @param vehiclePopR The number of vehicles in the street coming from the right.
 * @param lambdaR The street's arrival rate from the right.
 * @param lanes The number of lanes the street has.
 */
class Street(streetName: String, sType: StreetType, len: Double, vehiclePopL: Int, lambdaL: Double, vehiclePopR: Int, lambdaR: Double, lanes: Int = 1) {

  private var e: Array[Edge] = Array()

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

  /**
   * Returns the number of vehicles in the street coming from the left.
   */
  def vehiclesL = vehiclePopL

  /**
   * Returns the street's arrival rate from the left.
   */
  def arrivalRateL = lambdaL

  /**
   * Returns the number of vehicles in the street coming from the right.
   */
  def vehiclesR = vehiclePopR

  /**
   * Returns the street's arrival rate from the right.
   */
  def arrivalRateR = lambdaR

  /**
   * Returns the number of lanes the street has.
   */
  def noOfLanes = lanes

  /**
   * Sets the number of lanes the street and by extension its edges have.
   * @param lanes Number of lanes.
   */
  def noOfLanes_(lanes: Int): Unit = {
    e.foreach((edge: Edge) => edge.streetLanes_(lanes))
  }

  /**
   * Returns the average speed in this street from the left.
   */
  def speedL = {
    edges.map((edge: Edge) => edge.length).sum / edges.map((edge: Edge) => edge.residenceTimeL).sum
  }

  /**
   * Returns the average speed in this street from the right.
   */
  def speedR = {
    edges.map((edge: Edge) => edge.length).sum / edges.map((edge: Edge) => edge.residenceTimeR).sum
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
    if (nextOtherAttachedAt < 0 || nextOtherAttachedAt > len) {
      throw new StreetLengthExceededException(nextOtherAttachedAt)
    }

    val edge: Edge = new Edge
    edge.intersectionPoint_(nextOtherAttachedAt)
    edge.length_((nextOtherAttachedAt - otherAttachedAt).abs)
    edge.streetName_(streetName)
    e = e ++ Array(edge)

    edge
  }

  /**
   * Returns a list containing the edges in this street (generated due to
   * intersections).
   */
  def edges = e

  /**
   * Initialises values to be used during simulation.
   */
  def initialise() = {
    var arrRate = lambdaL
    e.foreach((edge: Edge) => {
      edge.vehiclesL_((vehiclePopL * (edge.length / len)).ceil.toInt)
      edge.arrivalRateL_(arrRate)
      arrRate = edge.departureRateL
    })

    arrRate = lambdaR
    e.reverse.foreach((edge: Edge) => {
      edge.vehiclesR_((vehiclePopR * (edge.length / len)).ceil.toInt)
      edge.arrivalRateR_(arrRate)
      arrRate = edge.departureRateR
    })
  }

  /**
   * Prepares for the creation of a crossroad with the specified street, at the specified points of
   * intersection.
   * @param thisAt Point on the context street where the intersection occurs.
   * @param that The other street object to be involved in this crossroads intersection.
   * @param thatAt Point on the other street where the intersection occurs.
   */
  def createCrossroads(thisAt: Double, that: Street, thatAt: Double) = {
    if (thisAt == 0 || thisAt == len || thatAt == 0 || thatAt == that.length) {
      throw new NotCrossroadsException
    }

    val thisPrev = getEdge(thisAt)
    if (thisPrev != null) {
      thisPrev.length_(if (thisAt > thisPrev.intersectionPoint) thisPrev.length else thisAt - thisPrev.intersectionPoint)
      val thisEdge = addEdge(thisPrev.intersectionPoint, thisAt)
      thisEdge.intersectionPoint_(thisAt)
      thisEdge.otherIntersectionPoint_(thatAt)
      thisEdge.edgeT_(RoadStructure.Crossroads)
      thisEdge.streetAtTarget_(that)
    } else {
      val thisEdge = addEdge(thisAt, 0)
      thisEdge.otherIntersectionPoint_(thatAt)
      thisEdge.edgeT_(RoadStructure.Crossroads)
      thisEdge.streetAtTarget_(that)
    }

    val thatPrev = that.getEdge(thatAt)
    if (thatPrev != null) {
      thatPrev.length_(thatAt - thatPrev.intersectionPoint)
      val thatEdge = that.addEdge(thatPrev.intersectionPoint, thatAt)
      thatEdge.intersectionPoint_(thatAt)
      thatEdge.otherIntersectionPoint_(thisAt)
      thatPrev.edgeT_(RoadStructure.Crossroads)
    } else {
      val thatEdge = that.addEdge(0, thatAt)
      thatEdge.otherIntersectionPoint_(thisAt)
      thatEdge.edgeT_(RoadStructure.Crossroads)
    }
  }

  /**
   * Creates and attaches an outgoing street to the context street.
   * @param network The network where the street will be attached.
   * @param streetName The name of the street to be attached.
   * @param streetType The type of the street to be attached.
   * @param length The length the street to be attached.
   * @param point The point where the street is to be attached with respect to
   *              the starting point of the other.
   * @param vehiclesL The number of vehicles in the new street coming from the left.
   * @param arrivalRateL The new street's arrival rate from the left.
   * @param vehiclesR The number of vehicles in the new street coming from the right.
   * @param arrivalRateR The new street's arrival rate from the right.
   * @param lanes The number of lanes the new street will have.
   * @return The street that has been created.
   */
  def attachStreet(network: RoadNetwork, streetName: String, streetType: StreetType,
                 length: Double, point: Double, vehiclesL: Int, arrivalRateL: Double, vehiclesR: Int, arrivalRateR: Double, lanes: Int = 1): Street = {
    val street: Street = network.addStreet(streetName, streetType, length, vehiclesL, arrivalRateL, vehiclesR, arrivalRateR, lanes)

    var otherAt = 0.0
    val prev: Edge = getEdge(point)
    if (prev != null) {
      otherAt = if (prev.intersectionPoint < len) prev.intersectionPoint + prev.length else 0
      prev.length_((point - prev.intersectionPoint).abs)
    }

    if (point == len && this.e.length != 0) {
      val edge: Edge = addEdge(otherAt)
      edge.streetAtTarget_(street)
    } else if (point == len) {
      val edge: Edge = addEdge(0)
      edge.streetAtTarget_(street)
    } else if (point == 0 && prev != null) {
      prev.streetAtSource_(street)
    } else if (point == 0) {
      val edge: Edge = addEdge(0)
      edge.streetAtSource_(street)
    } else if (point > 0 && point < len) {
      val edge: Edge = addEdge(otherAt, point)
      edge.streetAtTarget_(street)
      edge.edgeT_(RoadStructure.TJunction)
    }

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
    //Sort edges in order of intersection point.
    e = e.sortWith((e1: Edge, e2: Edge) => e1.intersectionPoint < e2.intersectionPoint)

    if (e.length == 0) {
      addEdge(0, length)
    } else {
      val maxEdgeIntersectionPt = e.maxBy((edge: Edge) => edge.intersectionPoint)
      if (maxEdgeIntersectionPt.intersectionPoint < length) {
        val edge: Edge = addEdge(maxEdgeIntersectionPt.intersectionPoint, length)
      }
    }
  }

  /**
   * Returns the edge on which a given point is found.
   * @param point A point along the road.
   */
  def getEdge(point: Double): Edge = {
    if (e.size > 0) e.minBy((e: Edge) => (point - e.intersectionPoint).abs) else null
  }

  /**
   * Blocks the street.
   * @param network The road network in which the street is found.
   */
  def block(network: RoadNetwork): Unit = {
    e.foreach((edge: Edge) => network.removeEdge(edge))
  }

  /**
   * Prepares for the creation of a roundabout node.
   * @param at Position in the street where to place the roundabout.
   */
  def createRoundabout(at: Double) = {
    val edge: Edge = getEdge(at)
    if (edge != null) {
      //val oldLength = edge.length
      if (edge.intersectionPoint < len) edge.length_(at - edge.intersectionPoint)
      edge.edgeT_(RoadStructure.Roundabout)
      //val newEdge: Edge = addEdge(at, oldLength - edge.length)
    } else {
      addEdge(at, 0).edgeT_(RoadStructure.Roundabout)
    }
  }

}
