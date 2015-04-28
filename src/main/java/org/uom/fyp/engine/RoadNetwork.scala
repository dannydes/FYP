package org.uom.fyp.engine

import com.perfdynamics.pdq.PDQ
import org.jgrapht.graph.DefaultDirectedGraph
import java.util

/**
 * Organises and simulates road networks.
 */
class RoadNetwork(name: String) extends DefaultDirectedGraph[Node, Lane](classOf[Lane]) with IRoadNetwork {

  private var streets = List()

  def networkName = name

  private def simulate(vehicles: Int, arrivalRate: Double, lane: Lane, pdq: PDQ): Unit = {
    lane.noOfVehicles_(vehicles)
    lane.arrivalRate_(arrivalRate)
    lane.simulate(pdq)

    val outgoingLanes: util.Set[Lane] = outgoingEdgesOf(lane.getTarget)
    if (outgoingLanes.size > 0) {
      val newArrivalRate = lane.departureRate / outgoingLanes.size
      val newNoOfVehicles = vehicles / outgoingLanes.size
      val outgoingLanesIterator = outgoingLanes.iterator

      while (outgoingLanesIterator.hasNext) {
        simulate(newNoOfVehicles, newArrivalRate, outgoingLanesIterator.next, pdq)
      }
    }
  }

  override def initSimulation(vehicles: Int) = {
    val lanes = edgeSet().toArray
    val pdq: PDQ = new PDQ
    pdq.Init(name)
    simulate(vehicles, vehicles, lanes(0).asInstanceOf[Lane], pdq)
    pdq.Report()
  }

  /**
   * Returns the lane found in a street with the given name and the given
   * lane number.
   * @param streetName Street name.
   * @param laneNo The lane's number relative to other lanes in that given street.
   * @return Lane found in the given street.
   */
  def getLane(streetName: String, laneNo: Int): Lane = {
  /*TO BE CHANGED!  val lanes = edgeSet
    val iterator = lanes.iterator
    while (iterator.hasNext) {
      val lane = iterator.next
      if (lane.street.name == streetName) {
        lane
      }
    }*/
    null
  }

  /**
   * Creates and returns a lane auto-defining both vertices.
   * @param properties
   * @return Lane just created.
   */
  override def createLane(properties: AnyRef): Lane = NetworkUtils.createLane(this)

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


}
