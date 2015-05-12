package org.uom.fyp.engine

import com.perfdynamics.pdq.PDQ
import org.jgrapht.graph.DefaultDirectedGraph
import java.util

/**
 * Organises and simulates road networks.
 */
class RoadNetwork(name: String) extends DefaultDirectedGraph[Node, LaneSlice](classOf[LaneSlice]) with IRoadNetwork {

  /**
   * Stores a list of streets/roads within the network.
   */
  private var streets: List[Street] = List()

  /**
   * Returns the name of the network.
   */
  def networkName = name

  /**
   *
   * @param vehicles Number of vehicles in system.
   * @param arrivalRate
   * @param lane
   * @param pdq
   */
  private def simulate(vehicles: Int, arrivalRate: Double, lane: LaneSlice, pdq: PDQ): Unit = {
    lane.noOfVehicles_(vehicles)
    lane.arrivalRate_(arrivalRate)
    lane.simulate(pdq)

    val outgoingLanes: util.Set[LaneSlice] = outgoingEdgesOf(lane.getTarget)
    if (outgoingLanes.size > 0) {
      val newArrivalRate = lane.departureRate / outgoingLanes.size
      val newNoOfVehicles = vehicles / outgoingLanes.size
      val outgoingLanesIterator = outgoingLanes.iterator

      while (outgoingLanesIterator.hasNext) {
        simulate(newNoOfVehicles, newArrivalRate, outgoingLanesIterator.next, pdq)
      }
    }
  }

  /**
   *
   * @param vehicles Number of vehicles in system.
   */
  override def initSimulation(vehicles: Int) = {
    val lanes = edgeSet().toArray
    val pdq: PDQ = new PDQ
    pdq.Init(name)
    simulate(vehicles, vehicles, lanes(0).asInstanceOf[LaneSlice], pdq)
    pdq.Report()
  }

  /**
   * Returns the lane found in a street with the given name and the given
   * lane number.(untested)
   * @param streetName Street name.
   * @param laneNo The lane's number relative to other lanes in that given street.
   * @return Lane found in the given street.
   */
  def getLane(streetName: String, laneNo: Int): LaneSlice = {
    val street: Street = streets.find((street: Street) => street.name == streetName).asInstanceOf[Street]
    street.lanes.find((lane: Lane) => lane.no == laneNo).asInstanceOf[LaneSlice]
  }

  /**
   * Creates a lane.
   * @param streetName The name of the street to which the lane belongs.
   * @param streetType The type of the street to which the lane belongs. Can be StreetType.PRIMARY or
   *                   StreetType.SECONDARY.
   * @param length The lane's length.
   */
  override def createLane(streetName: String, streetType: StreetType, length: Double): Lane = {
    val street: Street = addStreet(streetName, streetType)
    val lane: Lane = new Lane(street.lanes.length, length)
    street.addLane(lane)

    lane
  }

  /**
   * Block lane found in the street with the given name.
   * @param streetName Street name.
   */
  override def blockLane(streetName: String, laneNo: Int) = {
    val lane = getLane(streetName, laneNo)
    lane.block(this)
  }

  /**
   * Returns the union of this road network to the one passed as a parameter.
   * @param other The road network to join to this network.
   * @return The union of the two road networks.
   */
  def join(other: RoadNetwork) = new RoadNetworkUnion(this, other)

  /**
   * Returns the propagation velocity of a shock wave.
   * @param qb Flow before change in conditions.
   * @param qa Flow after change in conditions.
   * @param kb Traffic density before change in conditions.
   * @param ka Traffic density after change in conditions.
   */
  override def shockwave(qb : Double, qa : Double, kb : Double, ka : Double): Double = (qb - qa) / (kb - ka)

  /**
   * untested
   */
  override def addStreet(streetName: String, streetType: StreetType): Street = {
    //First, we must check if the street has already been defined .
    val matches: List[Street] = streets.filter((street: Street) => street.name == streetName)
    var street: Street = null

    if (matches.size == 0) {
      street = new Street(streetName, streetType)
      streets = streets ++ List(street)
    } else {
      street = matches(0)
    }

    street
  }

  private def completeEdgeList() = {
    streets.foreach((street: Street) => {
      street.lanes.foreach((lane: Lane) => {
        lane.createLastEdge()
      })
    })
  }

  //under construction!!
  override def buildGraph(lane: Lane = streets(0).lanes(0), intersectionPt: Double = 0, source: Node = null): Unit = {
    completeEdgeList()

    for (i <- 0 until lane.edges.size) {
      val laneSlice: LaneSlice = lane.edges(i)
      val edge: LaneSlice = NetworkUtils.createLaneSlice(this, source)
      if (laneSlice.laneAtTarget != null) {
        buildGraph(laneSlice.laneAtTarget, laneSlice.intersectionPoint, edge.getTarget)
      }
    }
    /*streets.foreach((street: Street) => {
      street.lanes.foreach((lane: Lane) => {
        lane.edges.foreach((slice: LaneSlice) => {
          if (slice. != null) {
            source = slice.intersectingNode
          }
          println(source)

          val edge: LaneSlice = NetworkUtils.createLaneSlice(this, source)
          source = edge.getTarget
        })
        source = null
      })*/
  }

}
