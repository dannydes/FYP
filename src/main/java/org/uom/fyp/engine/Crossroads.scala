package org.uom.fyp.engine

/**
 * Represents a crossroad intersection.
 */
case class Crossroads() extends Node {

  private var r1: String = _
  private var r2: String = _
  private var ts1: Double = _
  private var ts2: Double = _
  private var pe: (Edge, Edge) = _

  /**
   * Sets the name of one of the roads involved.
   * @param r1 The name of one of the roads.
   */
  def road1_(r1: String) = {
    this.r1 = r1
  }

  /**
   * Returns the name of one of the roads.
   */
  def road1 = r1

  /**
   * Sets the other road's name.
   * @param r2 The other road's name.
   */
  def road2_(r2: String) = {
    this.r2 = r2
  }

  /**
   * Returns the other road's name.
   */
  def road2 = r2

  /**
   * Sets the timing for one of the streets.
   * @param ts1 The time traffic will have to wait at the crossroads.
   */
  def timingStreet1_(ts1: Double) = {
    this.ts1 = ts1
  }

  /**
   * Returns the time traffic will have to wait at the crossroad on one of the streets.
   */
  def timingStreet1 = ts1

  /**
   * Sets the timing for the other street.
   * @param ts2 The time traffic will have to wait at the crossroads.
   */
  def timingStreet2_(ts2: Double) = {
    this.ts2 = ts2
  }

  /**
   * Returns the time traffic will have to wait at the crossroads on the other street.
   */
  def timingStreet2 = ts2

  /**
   * Finds and sets the crossroads' priority edges depending on waiting times.
   * @param n Network where this crossroads node may be found.
   */
  def findPriorityEdges(n: RoadNetwork) = {
    val road = if (ts1 < ts2) r1 else r2
    val edges = n.edgesOf(this).toArray(Array(new Edge))
    val res = edges.filter((e: Edge) => e.streetName == road)
    pe = (res(0), res(1))
  }

  /**
   * Returns the crossroads' priority edges.
   */
  def priorityEdges = pe

}
