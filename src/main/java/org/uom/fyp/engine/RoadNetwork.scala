package org.uom.fyp.engine

import org.jgrapht.graph.DefaultDirectedGraph
import java.util

/**
 * Organises and simulates road networks.
 */
class RoadNetwork extends DefaultDirectedGraph[Node, Lane](classOf[Lane]) with IRoadNetwork {

  private var streets = List()

  /**
   *
   * @param vehicles Number of vehicles in system.
   * @return
   */
  override def findTime(vehicles: Int): Double = {
    var time = 0.0
    val nodes: util.Set[Node] = vertexSet
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
      while (outDegreeIterator.hasNext) {
        val lane = outDegreeIterator.next
        lane.arrivalRate_(priorLaneDeptRate)
        //priorLaneDeptRate = lane.departureRate
        lane.simulate()
      }
    }
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
