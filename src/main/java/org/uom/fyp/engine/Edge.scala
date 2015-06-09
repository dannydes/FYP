package org.uom.fyp.engine

import com.perfdynamics.pdq._
import org.jgrapht.graph.DefaultEdge

/**
 * Applies operations over edges.</br>
 * An edge is essentially a part of a street, whereby the source
 * and target are marked on intersection with other streets.
 */
class Edge extends DefaultEdge {

  private var sName = ""

  private var lNo = 0

  /**
   * Stores the number of vehicles in edge.
   */
  private var vehicles: Int = 0

  /**
   * Stores edge length.
   */
  private var len = 0.0

  /**
   * Stores edge width.
   */
  private var w = 0.0

  /**
   * Stores the arrival rate for this edge.
   */
  private var lambda = 0.0

  /**
   * Stores the point in which the edge exists, in relation to the
   * street in which it is found. Depends on the point of interaction of
   * the two streets.
   */
  private var point = 0.0

  private var otherPoint = 0.0

  /**
   * Stores a reference to the <b>Street</b> object representing the physical
   * street that intersects with the street on which the edge is found. Used in
   * the graph building process.
   */
  private var sat: Street = null

  /**
   * Returns the name of the street.
   */
  def streetName = sName

  /**
   * Sets the name of the street.
   * @param sName The name of the street.
   */
  def streetName_(sName: String) = {
    this.sName = sName
  }

  /**
   * Returns the number of the lane on which the edge is found, relative to the other lanes
   * found on the given street.
   */
  def laneNo = lNo

  /**
   * Sets the number of the lane on which the edge is found, relative to the other lanes found
   * on the given street.
   * @param lNo The number of the lane relative to the other lanes found on the given street.
   */
  def laneNo_(lNo: Int) = {
    this.lNo = lNo
  }

  /**
   * Returns edge length.
   */
  def length: Double = len

  /**
   * Sets edge length.
   * @param len Edge length.
   */
  def length_(len: Double) = {
    this.len = len
  }

  /**
   * Returns edge width.
   */
  def width: Double = w

  /**
   * Sets edge width.
   * @param w Edge width.
   */
  def width_(w: Double) = {
    this.w = w
  }

  /**
   * Returns the point in which the edge exists, in relation to the
   * street in which it is found. Depends on the point of interaction of
   * the two streets.
   */
  def intersectionPoint = point

  /**
   * Sets the point in which the edge exists, in relation to the street in
   * which it is found.
   * @param point The point in which the edge exists, in relation to
   *              the street in which it is found. Depends on the point of
   *              intersection of the two streets.
   */
  def intersectionPoint_(point: Double) = {
    this.point = point
  }

  /**
   * Returns the position where the intersection occurs upon the other road.
   */
  def otherIntersectionPoint = otherPoint

  /**
   * Sets the position where the intersection occurs upon the other road.
   * @param otherPoint The position where the intersection occurs upon the
   *                   other road.
   */
  def otherIntersectionPoint_(otherPoint: Double) = {
    this.otherPoint = otherPoint
  }

  /**
   * Returns a reference to the <b>Street</b> object representing the physical
   * lane that intersects with the street on which the edge is found. Used in
   * the graph building process.
   */
  def streetAtTarget = sat

  /**
   * Sets a reference to the <b>Street</b> object representing the physical
   * lane that intersects with the street on which the edge is found.
   * @param sat <b>Street</b> object intersecting with the street from which the
   *            edge is taken to create the edge.
   */
  def streetAtTarget_(sat: Street) = {
    this.sat = sat
  }

  /**
   * Returns the number of vehicles in the edge.
   */
  def noOfVehicles = vehicles

  /**
   * Sets the number of vehicles in the edge.
   * @param vehicles The number of vehicles in the edge.
   */
  def noOfVehicles_(vehicles: Int) = {
    this.vehicles = vehicles
  }

  /**
   * Blocks the context edge.
   * @param network The network from which to remove the edge.
   */
  def block(network: RoadNetwork): Unit = network.removeEdge(this)

  /**
   * Returns the street's arrival rate.
   */
  private def arrivalRate = lambda

  /**
   * Sets the street's arrival rate.
   * @param lambda The street's arrival rate.
   */
  def arrivalRate_(lambda: Double) = {
    this.lambda = lambda
  }

  /**
   * Returns the street's departure rate.
   */
  def departureRate = lambda / (1 - noOfVehicles)

  /**
   * Returns the time taken for one job (vehicle) to be serviced.
   */
  def time: Double = {
    1 / (departureRate - lambda)
  }

  /**
   * Simulates traffic going from the starting point to the ending point of the
   * particular edge.
   * @param pdq PDQ object.
   */
  def simulate(pdq: PDQ) = {
    pdq.CreateNode(toString, defs.CEN, defs.FCFS)
    pdq.CreateOpen(toString + "load", lambda)
    pdq.SetVisits(toString, toString + "load", noOfVehicles, time)
    pdq.SetTUnit("Minutes")
    pdq.SetWUnit("Vehicles")
    //pdq.Solve(defs.CANON)
  }

  /**
   * Returns the edge's source.
   */
  override def getSource = {
    super.getSource.asInstanceOf[Node]
  }

  /**
   * Returns the edge's target.
   */
  override def getTarget = {
    super.getTarget.asInstanceOf[Node]
  }

  //override def toString = sName + " " + lNo

}
