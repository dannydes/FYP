package org.uom.fyp.engine

import com.perfdynamics.pdq.{defs, PDQ}
import org.jgrapht.graph.{SimpleDirectedGraph, DefaultDirectedGraph}
import java.util

import org.uom.fyp.engine.StreetType.StreetType

/**
 * Organises and simulates road networks.
 * @param name The name given to the network.
 */
class RoadNetwork(name: String) extends SimpleDirectedGraph[Node, Edge](classOf[Edge]) with IRoadNetwork {

  /**
   * Stores a list of streets/roads within the network.
   */
  private var streets: Array[Street] = Array()

  /**
   * Returns the name of the network.
   */
  def networkName = name

  /**
   * Returns an array of streets within the network.
   */
  def streetList = streets

  /**
   * Simulates the road network's operation according to the specified parameters.
   * @param minutes The time in minutes for which the simulation will be allowed to run.
   */
  override def simulate(minutes: Double = streets(0).edges(0).minutesForSimulation) = {
    val edges = edgeSet().toArray
    val pdq: PDQ = new PDQ
    pdq.Init(name)

    streets.foreach((s: Street) => s.edges.foreach((e: Edge) => e.minutesForSimulation_(minutes)))

    streets.foreach((street: Street) => {
      street.initialize()
      street.edges.foreach((edge: Edge) => {
        edge.simulate(pdq)
      })
    })

    pdq.Solve(defs.CANON)
    pdq.SetDebug(true)
    pdq.Report()
    //PDQProperties.pdqNodesToRoadNetwork(pdq, this)
  }

  /**
   * Returns the street with the given name.
   * @param streetName Street name.
   */
  def getStreet(streetName: String): Street = {
    streets.filter((street: Street) => street.name == streetName)(0)
  }

  /**
   * Creates a street and returns a reference to the object created.
   * @param streetName The name of the street.
   * @param streetType The type of the street. Can be <b>StreetType.PRIMARY</b>
   *                   or <b>StreetType.SECONDARY</b>.
   * @param length The street's length.
   * @param vehicles
   * @param lanes
   * @return Reference to the street object just created.
   */
  override def createStreet(streetName: String, streetType: StreetType, length: Double, vehicles: Int, arrivalRate: Double, lanes: Int = 1): Street = {
    addStreet(streetName, streetType, length, vehicles, arrivalRate, lanes)
  }

  /**
   * Block the street with the given name.
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
  override def addStreet(streetName: String, streetType: StreetType, length: Double, vehicles: Int, arrivalRate: Double, lanes: Int = 1): Street = {
    //First, we must check if the street has already been defined.
    val matches: Array[Street] = streets.filter((street: Street) => street.name == streetName)
    var street: Street = null

    if (matches.size == 0) {
      street = new Street(streetName, streetType, length, vehicles, arrivalRate, lanes)
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
   * @param street The current street. Starts from the first occurring street if omitted.
   * @param countStart The index at which the intersecting edge is found in the list. Is
   *                   assigned 0 if omitted.
   * @param source The source where to attach the next edge in the graph. Starts as null if
   *               omitted.
   */
  override def buildGraph(street: Street = streets(0), countStart: Int = 0, source: Node = null): Unit = {
    var s: Node = source
    for (i <- countStart until street.edges.size) {
      val actualEdge: Edge = street.edges(i)
      val edge: Edge = NetworkUtils.createEdge(this, s, actualEdge.edgeT)
      edge.streetName_(actualEdge.streetName)
      edge.streetLanes_(street.noOfLanes)
      edge.streetEdgeNo_(i)
      edge.length_(actualEdge.length)
      street.edges(i) = edge
      s = edge.getTarget

      if (actualEdge.streetAtSource != null) {
        val cStart = street.edges.indexOf(street.getEdge(actualEdge.otherIntersectionPoint))
        buildGraph(actualEdge.streetAtSource, cStart, edge.getSource)
      }

      if (actualEdge.streetAtTarget != null) {
        val cStart = street.edges.indexOf(street.getEdge(actualEdge.otherIntersectionPoint))
        buildGraph(actualEdge.streetAtTarget, cStart, s)
      }
    }
  }

  /**
   * Reacts to the type of the given node.
   * @param node Node to be processed.
   */
  override def processNode(node: Node) = node match {
    //case PedestrianCrossing() => None
    case TJunction() => {
      val junction = node.asInstanceOf[TJunction]
      junction.findEdges(this)
      junction.priority.speed_(junction.converging1.speed + junction.converging2.speed)
    }
    case Crossroads() => None
    case Roundabout() => if (node.asInstanceOf[Roundabout].exitRate < node.arrivalRate) node.isCongested_()
    case _ => None
  }

}
