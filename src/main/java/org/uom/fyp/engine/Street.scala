package org.uom.fyp.engine

/**
 * Applies operations over streets.
 * @param streetName The name of the street.
 * @param sType The type of the street.
 */
class Street(streetName: String, sType: StreetType, len: Double, lanes: Int = 1) {

  /**
   * Stores lane slices that are generated by the class.
   */
  private var laneSlices: List[LaneSlice] = List()

  /**
   * Returns the name of the street.
   */
  def name = streetName

  /**
   * Returns the type of the street.
   */
  def streetType = sType

  /**
   * Returns the lane's length.
   */
  def length = len

  def noOfLanes = lanes

  /**
   * Adds a lane slice to be added to the graph later on.
   * @param otherAttachedAt The point where the attachment takes place.
   * @param nextOtherAttachedAt The next point where the attachment takes place.
   *                            Defaults to the length of the context length if
   *                            omitted.
   * @return The edge that has been created and added to the graph.
   */
  def addEdge(otherAttachedAt: Double, nextOtherAttachedAt: Double = len): LaneSlice = {
    if (nextOtherAttachedAt > len) {
      throw new LaneLengthExceededException(nextOtherAttachedAt)
    }

    val laneSlice: LaneSlice = new LaneSlice
    laneSlice.length_(nextOtherAttachedAt - otherAttachedAt)
    laneSlice.streetName_(streetName)
    laneSlices = laneSlices ++ List(laneSlice)

    laneSlice
  }

  /**
   * Returns a list containing the slices sliced off the lane.
   */
  def edges = laneSlices

  /**
   * Initializes values to be used during simulation.
   * @param vehicles The number of vehicles fed into the lane.
   * @param arrivalRate The lane's arrival rate.
   */
  def initialize(vehicles: Int, arrivalRate: Double) = {
    val pFlowInOut = vehicles / laneSlices.length
    var v = vehicles
    laneSlices.foreach((edge: LaneSlice) => {
      edge.noOfVehicles_(v)
      v -= pFlowInOut
    })
  }

  /**
   * Creates and attaches an outgoing lane to the context lane.
   * @param network The network where the lane will be attached.
   * @param streetName The name of the street to which the lane belongs.
   * @param streetType The type of the street to which the lane belongs.
   * @param length The length the lane should have.
   * @param point The point where the lane is to be attached.
   * @return The lane that has been created.
   */
  def attachStreet(network: RoadNetwork, streetName: String, streetType: StreetType,
                 length: Double, point: Double): Street = {
    val street: Street = network.addStreet(streetName, streetType, length)

    var otherAt = 0.0
    if (this.laneSlices.length != 0) {
      val prev: LaneSlice = this.laneSlices(this.laneSlices.length - 1)
      otherAt = prev.intersectionPoint + prev.length
    }

    val edge: LaneSlice = addEdge(otherAt, point)
    edge.streetAtTarget_(street)
    edge.otherIntersectionPoint_(0)

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
    if (laneSlices.length == 0) {
      addEdge(0, length)
    } else {
      val lastEdge: LaneSlice = laneSlices(laneSlices.length - 1)
      val point = lastEdge.intersectionPoint + lastEdge.length
      if (point < length) {
        val edge: LaneSlice = addEdge(point, length)
      }
    }
  }

  /**
   * Returns the edge at a given intersection point on the other road.
   * @param otherIntersectionPoint Intersection point on the other road.
   */
  def getEdge(otherIntersectionPoint: Double): LaneSlice = {
    laneSlices.filter((edge: LaneSlice) => edge.intersectionPoint == otherIntersectionPoint)(0)
  }

  def block(network: RoadNetwork): Unit = {

  }

}
