package org.uom.fyp.engine

import com.perfdynamics.pdq.{defs, PDQ}
import org.jgrapht.graph.DefaultDirectedGraph
import java.util

import org.uom.fyp.engine.StreetType.StreetType

/**
 * Organises and simulates road networks.
 * @param name The name given to the network.
 */
class RoadNetwork(name: String) extends DefaultDirectedGraph[Node, Edge](classOf[Edge]) with IRoadNetwork {

  /**
   * Stores a list of streets/roads within the network.
   */
  private var streets: Array[Street] = Array()

  /**
   * Returns the name of the network.
   */
  def networkName = name

  def streetList = streets

  /**
   * Simulates every edge in the graph recursively until there are no more
   * outgoing edges.
   * @param vehicles Number of vehicles in system.
   * @param arrivalRate The arrival rate of the current edge.
   * @param edge The current edge.
   * @param pdq PDQ object.
   */
  private def simulate(vehicles: Int, arrivalRate: Double, edge: Edge, pdq: PDQ): Unit = {
    edge.noOfVehicles_(vehicles)
    edge.arrivalRate_(arrivalRate)
    processNode(edge.getTarget)
    edge.simulate(pdq)

    val outgoingLanes: util.Set[Edge] = outgoingEdgesOf(edge.getTarget)
    if (outgoingLanes.size > 0) {
      val newArrivalRate = edge.departureRate / outgoingLanes.size
      val newNoOfVehicles = vehicles / outgoingLanes.size
      val outgoingLanesIterator = outgoingLanes.iterator

      while (outgoingLanesIterator.hasNext) {
        simulate(newNoOfVehicles, newArrivalRate, outgoingLanesIterator.next, pdq)
      }
    }
  }

  /**
   * Initialises simulation upon the road network.
   * @param vehicles Number of vehicles in system.
   */
  override def initSimulation(vehicles: Int) = {
    val edges = edgeSet().toArray
    val pdq: PDQ = new PDQ
    pdq.Init(name)
    simulate(vehicles, vehicles, edges(0).asInstanceOf[Edge], pdq)
    pdq.Solve(defs.CANON)
    pdq.Report()
    //PDQProperties.pdqNodesToRoadNetwork(pdq, this)
  }

  /**
   * Returns the lane found in a street with the given name and the given
   * lane number.
   * @param streetName Street name.
   * @return Lane found in the given street.
   */
  def getStreet(streetName: String): Street = {
    streets.filter((street: Street) => street.name == streetName)(0)
  }

  /**
   * Creates a lane and returns a reference to the object created.
   * @param streetName The name of the street to which the lane belongs.
   * @param streetType The type of the street to which the lane belongs. Can be <b>StreetType.PRIMARY</b>
   *                   or <b>StreetType.SECONDARY</b>.
   * @param length The lane's length.
   * @param vehicles
   * @param lanes
   * @return Reference to the lane object just created.
   */
  override def createStreet(streetName: String, streetType: StreetType, length: Double, vehicles: Int, lanes: Int = 1): Street = {
    addStreet(streetName, streetType, length, vehicles, lanes)
  }

  /**
   * Block lane found in the street with the given name.
   * @param streetName Street name.
   */
  override def blockStreet(streetName: String) = {
    val street = getStreet(streetName)
    street.block(this)
  }

  /**
   * Returns the propagation velocity of a shock wave.
   * @param qb Flow before change in conditions.
   * @param qa Flow after change in conditions.
   * @param kb Traffic density before change in conditions.
   * @param ka Traffic density after change in conditions.
   */
  override def shockwave(qb : Double, qa : Double, kb : Double, ka : Double): Double = (qb - qa) / (kb - ka)

  /**
   * Creates a <b>Street</b> object, appends it to the <b>streets</b> list and
   * returns it.
   * @param streetName Name of the street.
   * @param streetType Type of the street (i.e. <b>StreetType.PRIMARY</b> or <b>StreetType.SECONDARY</b>).
   * @return The <b>Street</b> object created.
   */
  override def addStreet(streetName: String, streetType: StreetType, length: Double, vehicles: Int, lanes: Int = 1): Street = {
    //First, we must check if the street has already been defined.
    val matches: Array[Street] = streets.filter((street: Street) => street.name == streetName)
    var street: Street = null

    if (matches.size == 0) {
      street = new Street(streetName, streetType, length, vehicles, lanes)
      streets = streets ++ List(street)
    } else {
      street = matches(0)
    }

    street
  }

  /**
   * Creates the last edges for the remaining roads. This method is to be called
   * after all roads have been attached.
   */
  def completeEdgeList() = {
    streets.foreach((street: Street) => {
      street.createLastEdge()
    })
  }

  /**
   * Builds the graph declared in the model by recursively going through the ficticious
   * edge list created by the precedent stages. Hence, this method should be called after
   * all the other processes have took place.<br/>
   * For the algorithm to work as it should, all arguments should be omitted so as to pass
   * their respective default values.
   * @param street The current lane. Starts from the first occurring lane in the first occurring
   *               street if omitted.
   * @param countStart The index at which the intersecting edge is found in the list. Is
   *                   assigned 0 if omitted.
   * @param source The source where to attach the next edge in the graph. Starts as null if
   *               omitted.
   */
  override def buildGraph(street: Street = streets(0), countStart: Int = 0, source: Node = null): Unit = {
    var s: Node = source
    for (i <- countStart until street.edges.size) {
      val laneSlice: Edge = street.edges(i)
      val edge: Edge = NetworkUtils.createLaneSlice(this, s, laneSlice.edgeT)
      street.edges(i) = edge
      s = edge.getTarget

      if (laneSlice.streetAtTarget != null) {
        val cStart = street.edges.indexOf(street.getEdge(laneSlice.otherIntersectionPoint))
        buildGraph(laneSlice.streetAtTarget, cStart, s)
      }
    }
  }

  /**
   * Provides nodes within the graph with their respective markers, depending on the
   * markers assigned to their incoming edges.
   */
  override def markGraphNodes() = {
    val nodeIterator = vertexSet.iterator
    while (nodeIterator.hasNext) {
      val node: Node = nodeIterator.next
      val edges: util.Set[Edge] = incomingEdgesOf(node)
      val edgeIterator = edges.iterator
      var edgeType = false
      while (edgeIterator.hasNext && ! edgeType) {
        val edge: Edge = edgeIterator.next
        if (edge.edgeT != RoadStructure.Default) {
          node.nodeType_(edge.edgeT)
          edgeType = true
        }
      }
    }
  }

  override def processNode(node: Node) = node match {
    //case PedestrianCrossing(t) => _
    case TJunction(p, c1, c2) => p.speed = c1.speed + c2.speed
    case _ => None
  }

}
