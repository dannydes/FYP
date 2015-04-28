package org.uom.fyp.engine

import org.jgrapht.alg.HamiltonianCycle
import org.jgrapht.graph.DefaultDirectedGraph
import java.util

/**
 * Organises and simulates road networks.
 */
class RoadNetwork extends DefaultDirectedGraph[Node, Lane](classOf[Lane]) with IRoadNetwork {

  private var streets = List()

  private def simulate(vehicles: Int, arrivalRate: Double, lane: Lane): Unit = {
    lane.noOfVehicles_(vehicles)
    lane.arrivalRate_(arrivalRate)
    lane.simulate()

    val outgoingLanes: util.Set[Lane] = outgoingEdgesOf(lane.getTarget)
    if (outgoingLanes.size > 0) {
      val newArrivalRate = lane.departureRate / outgoingLanes.size
      val newNoOfVehicles = vehicles / outgoingLanes.size
      val outgoingLanesIterator = outgoingLanes.iterator

      while (outgoingLanesIterator.hasNext) {
        simulate(newNoOfVehicles, newArrivalRate, outgoingLanesIterator.next)
      }
    }
  }

  def initSimulation(vehicles: Int) = {
    val lanes = edgeSet().toArray
    simulate(vehicles, vehicles, lanes(0).asInstanceOf[Lane])
  }

  /**
   *Deprecated.
   * @param vehicles Number of vehicles in system.
   * @return
   */
  override def findTime(vehicles: Int): Double = {
    var time = 0.0
    /*val nodes: util.Set[Node] = vertexSet
    println(nodes.size)
    val nodeIterator = nodes.iterator()
    while (nodeIterator.hasNext) {
      val node = nodeIterator.next

      val inDegrees: util.Set[Lane] = incomingEdgesOf(node)
      val incoming = inDegrees.toArray()
      //val firstLane: Lane = incoming(0).asInstanceOf[Lane]
      //firstLane.arrivalRate_(vehicles)

      var priorLaneDeptRate = 10//firstLane.departureRate
      /*var firstInDegree = false
      val inDegreeIterator = inDegrees.iterator()
      while (inDegreeIterator.hasNext) {
        val lane = inDegreeIterator.next
        if (! firstInDegree) {
          lane.arrivalRate_(vehicles)
          firstInDegree = true
        }
      }*/

      val outDegrees: util.Set[Lane] = outgoingEdgesOf(node)
      val outDegreeIterator = outDegrees.iterator()
      //var outDegreeVehicles =
      while (outDegreeIterator.hasNext) {
        val lane = outDegreeIterator.next
        lane.arrivalRate_(priorLaneDeptRate)
        //priorLaneDeptRate = lane.departureRate
        lane.simulate()
      }
    }*/
    /*val network: util.List[Node] = HamiltonianCycle.getApproximateOptimalForCompleteGraph(this)
    var source: Node = null
    var target: Node = null
    var lane: Lane = null

    //Access the first lane and initialize it with the initial conditions.
    if (network.size > 1) {
      source = network.get(0)
      target = network.get(1)
      lane = getEdge(source, target)

      lane.noOfVehicles_(vehicles)
      lane.arrivalRate_(vehicles)
    }

    var i = 1
    for (i <- 2 to network.size) {
      //vehicles /= outgoingEdgesOf(target).

      source = target
      target = network.get(i)
      lane = getEdge(source, target)

      if (lane != null) {
        //lane.arrivalRate_(prevLane.departureRate)
        //prevLane = lane

        lane.simulate()
      }
    }*/

    time
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
