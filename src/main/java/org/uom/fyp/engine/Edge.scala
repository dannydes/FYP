package org.uom.fyp.engine

import com.perfdynamics.pdq._
import org.jgrapht.graph.DefaultEdge

/**
 * Applies operations over edges.</br>
 * An edge is essentially a part of a street, whereby the source
 * and target are marked on intersection with other streets.
 */
class Edge extends DefaultEdge {

  /**
   * Stores the name of the street to which the edge belongs.
   */
  private var sName = ""

  /**
   * Stores the number of vehicles in edge.
   */
  private var vehicles: Int = 0

  /**
   * Stores edge length.
   */
  private var len = 0.0

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

  /**
   * Stores the position where the intersection occurs upon the other road.
   */
  private var otherPoint = 0.0

  /**
   * Stores a reference to the <b>Street</b> object representing the physical
   * street that intersects with the street on which the edge is found. Used in
   * the graph building process.
   */
  private var sat: Street = null

  private var sas: Street = _

  private var edgeType: RoadStructure.EnumVal = RoadStructure.Default

  private var minSim: Double = _

  private var sLanes: Int = _

  private var sEdgeNo: Int = _

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
   * Returns a number depending on the edge's occurrence on the street
   * (order considered in ascending order of intersection point).
   */
  def streetEdgeNo = sEdgeNo

  /**
   * Sets a number depending on the edge's occurrence on the street
   * (order considered in ascending order of intersection point)
   * @param sEdgeNo The number.
   */
  def streetEdgeNo_(sEdgeNo: Int) = {
    this.sEdgeNo = sEdgeNo
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
   * street that intersects with the street on which the edge is found. Used in
   * the graph building process.
   */
  def streetAtTarget = sat

  /**
   * Sets a reference to the <b>Street</b> object representing the physical
   * street that intersects with the street on which the edge is found.
   * @param sat <b>Street</b> object intersecting with the street from which the
   *            edge is taken to create the edge.
   */
  def streetAtTarget_(sat: Street) = {
    this.sat = sat
  }

  /**
   * Returns a reference to the street that intersects the street on which this edge is
   * found. Used in the graph building process.
   */
  def streetAtSource = sas

  /**
   * Sets a reference to the street that intersects the street on which this edge is
   * found. To be called if and only if intersection point is 0.
   * @param sas Street intersecting street on which this edge is found.
   */
  def streetAtSource_(sas: Street) = {
    this.sas = sas
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
   * Returns the edge's arrival rate.
   */
  def arrivalRate = lambda

  /**
   * Sets the edges's arrival rate.
   * @param lambda The edge's arrival rate.
   */
  def arrivalRate_(lambda: Double) = {
    this.lambda = lambda
  }

  /**
   * Returns the edge's departure rate.
   */
  def departureRate = lambda / (1 - noOfVehicles)

  /**
   * Returns the edge's servicing time.
   */
  def serviceTime = vehicles / lambda * (1 + vehicles)

  /**
   * Returns the density within the edge.
   */
  def density = vehicles / len

  /**
   * Returns the time in minutes for the simulation to run.
   */
  def minutesForSimulation = minSim

  /**
   * Sets the time in minutes for the simulation to run at edge-level.
   * @param minSim The time in minutes.
   */
  def minutesForSimulation_(minSim: Double) = {
    this.minSim = minSim
  }

  /**
   * Returns the flow within the edge.
   */
  def flow = vehicles / minSim

  /**
   * Returns the traffic speed within the edge.
   */
  def speed = flow / density

  /**
   * Returns the time taken for one job (vehicle) spends in a road segment.
   */
  def residenceTime = 1 / (departureRate - lambda)

  /**
   * Returns the type of the edge's target node to-be.
   */
  def edgeT = edgeType

  /**
   * Sets the type of the edge's target node to-be.
   * @param edgeType The type of the edge's target node to-be.
   */
  def edgeT_(edgeType: RoadStructure.EnumVal) = {
    this.edgeType = edgeType
  }

  /**
   * Returns the number of lanes belonging to the street containing this edge.
   */
  def streetLanes = sLanes

  /**
   * Sets the number of lanes belonging to the street containing this edge.
   * @param sLanes Number of lanes.
   */
  def streetLanes_(sLanes: Int) = {
    this.sLanes = sLanes
  }

  /**
   * Simulates traffic going from the starting point to the ending point of the
   * particular edge.
   * @param pdq PDQ object.
   */
  def simulate(pdq: PDQ) = {
    var l: Int = 0
    for (l <- 0 until sLanes) {
      val node = toString + "l" + l
      val workload = node + "w"
      pdq.CreateClosed(workload, Job.TERM, vehicles / sLanes, 1 / lambda)
      pdq.CreateNode(node, defs.CEN, defs.FCFS)
      pdq.SetDemand(node, workload, serviceTime)
      pdq.SetTUnit("Minutes")
      pdq.SetWUnit("Vehicles")
    }
  }

  /**
   * Returns the edge's source.
   */
  override def getSource = super.getSource.asInstanceOf[Node]

  /**
   * Returns the edge's target.
   */
  override def getTarget = super.getTarget.asInstanceOf[Node]

  /**
   * Returns a string representation of the edge, consisting of the name of the
   * street to which the edge belongs, a number depending on the edge's occurrence
   * on the street (order considered in ascending order of intersection point) and
   * the edge's length.
   */
  override def toString = sName + " " + sEdgeNo + " " + len

}
