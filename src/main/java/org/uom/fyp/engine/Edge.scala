package org.uom.fyp.engine

import com.perfdynamics.pdq._
import org.jgrapht.graph.DefaultEdge

/**
 * Applies operations over edges.</br>
 * An edge is essentially a part of a street, whereby the source
 * and target are marked on intersection with other streets.
 */
class Edge extends DefaultEdge {

  private var sName: String = _

  private var vehiclePopL: Int = _

  private var vehiclePopR: Int = _

  private var len: Double = _

  private var lambdaL: Double = _

  private var lambdaR: Double = _

  private var point: Double = _

  private var otherPoint: Double = _

  private var sat: Street = _

  private var sas: Street = _

  private var edgeType: RoadStructure.RoadStructure = RoadStructure.Default

  private var minSim: Double = _

  private var sLanes: Int = _

  private var sEdgeNo: Int = _

  private var segmentSpeedL: Double = _

  private var segmentSpeedR: Double = _

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
   * Returns the number of vehicles in the edge coming from the left.
   */
  def vehiclesL = vehiclePopL

  /**
   * Sets the number of vehicles in the edge coming from the left.
   * @param vehiclePop The number of vehicles in the edge coming from the left.
   */
  def vehiclesL_(vehiclePop: Int) = {
    vehiclePopL = vehiclePop
  }

  /**
   * Returns the number of vehicles in the edge coming from the right.
   */
  def vehiclesR = vehiclePopR

  /**
   * Sets the number of vehicles in the edge coming from the right.
   * @param vehiclePop The number of vehicles in the edge coming from the right.
   */
  def vehiclesR_(vehiclePop: Int) = {
    vehiclePopR = vehiclePop
  }

  /**
   * Returns the edge's arrival rate from the left.
   */
  def arrivalRateL = lambdaL

  /**
   * Sets the edge's arrival rate from the left.
   * @param lambda The edge's arrival rate from the left.
   */
  def arrivalRateL_(lambda: Double) = {
    lambdaL = lambda
  }

  /**
   * Returns the edge's arrival rate from the right.
   */
  def arrivalRateR = lambdaR

  /**
   * Sets the edge's arrival rate from the right.
   * @param lambda The edge's arrival rate from the right.
   */
  def arrivalRateR_(lambda: Double) = {
    lambdaR = lambdaR
  }

  /**
   * Returns the edge's departure rate from the left.
   */
  def departureRateL = lambdaL / (1 - vehiclePopL)

  /**
   * Returns the edge's departure rate from the right.
   */
  def departureRateR = lambdaR / (1 - vehiclePopR)

  /**
   * Returns the edge's servicing time for vehicles from the left.
   */
  def serviceTimeL = vehiclePopL / lambdaL * (1 + vehiclePopL)

  /**
   * Returns the edge's servicing time for vehicles from the right.
   */
  def serviceTimeR = vehiclePopR / lambdaR * (1 + vehiclePopR)

  /**
   * Returns the density within the edge for vehicles from the left.
   */
  def densityL = vehiclePopL / len

  /**
   * Returns the density within the edge for vehicles from the right.
   */
  def densityR = vehiclePopR / len

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
   * Returns the flow within the edge from the left.
   */
  def flowL = vehiclePopL / minSim

  /**
   * Returns the flow within the edge from the right.
   */
  def flowR = vehiclePopR / minSim

  /**
   * Returns the traffic speed within the edge from the left.
   */
  def speedL = if (segmentSpeedL == 0) flowL / densityL else segmentSpeedL

  /**
   * Sets the traffic speed within the edge from the left.
   * @param segmentSpeed Traffic speed from the left.
   */
  def speedL_(segmentSpeed: Double) = {
    segmentSpeedL = segmentSpeed
  }

  /**
   * Returns the traffic speed within the edge from the right.
   */
  def speedR = if (segmentSpeedR == 0) flowR / densityR else segmentSpeedR

  /**
   * Sets the traffic speed within the edge from the right.
   * @param segmentSpeed Traffic speed from the right.
   */
  def speedR_(segmentSpeed: Double) = {
    segmentSpeedR = segmentSpeed
  }

  /**
   * Returns the time taken for one job (vehicle) from the left spends in a road segment.
   */
  def residenceTimeL = 1 / (departureRateL - lambdaL)

  /**
   * Returns the time taken for one job (vehicle) from the right spends in a road segment.
   */
  def residenceTimeR = 1 / (departureRateR - lambdaR)

  /**
   * Returns the type of the edge's target node to-be.
   */
  def edgeT = edgeType

  /**
   * Sets the type of the edge's target node to-be.
   * @param edgeType The type of the edge's target node to-be.
   */
  def edgeT_(edgeType: RoadStructure.RoadStructure) = {
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
   * particular edge and the other way round.
   * @param pdq PDQ object.
   */
  def simulate(pdq: PDQ) = {
    var l: Int = 0
    for (l <- 0 until sLanes) {
      val node = toString + "l" + l
      val workload = node + "w"

      pdq.CreateClosed(workload + "left", Job.TERM, vehiclePopL / sLanes, 1 / (lambdaL / sLanes))
      pdq.CreateNode(node + "left", defs.CEN, defs.FCFS)
      pdq.SetDemand(node + "left", workload + "left", serviceTimeL)

      pdq.CreateClosed(workload + "right", Job.TERM, vehiclePopR / sLanes, 1 / (lambdaR / sLanes))
      pdq.CreateNode(node + "right", defs.CEN, defs.FCFS)
      pdq.SetDemand(node + "right", workload + "right", serviceTimeR)
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
