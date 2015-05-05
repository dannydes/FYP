package org.uom.fyp.engine

/**
 * Created by Daniel on 5/4/2015.
 */
class Lane(laneNo: Int, l: Double) {

  private var partialLanes: List[PartialLane] = List()
  private var len = l

  def no = laneNo

  def length = len

  def length_(l: Double) = {
    len = l
  }

  def addEdge(otherAttachedAt: Double, nextOtherAttachedAt: Double = len) = {
    if (nextOtherAttachedAt > len) {
      throw new Exception("")
    }

    val partialLane: PartialLane = new PartialLane
    partialLane.lLen_(nextOtherAttachedAt - otherAttachedAt)
    partialLanes = partialLanes ++ List(partialLane)
  }

  def edges = partialLanes

  def initialize(vehicles: Int, arrivalRate: Double) = {
    val pFlowInOut = vehicles / partialLanes.length
    var v = vehicles
    partialLanes.foreach((edge: PartialLane) => {
      edge.noOfVehicles_(v)
      v -= pFlowInOut
    })
  }

}
