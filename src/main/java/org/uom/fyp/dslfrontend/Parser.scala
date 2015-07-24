package org.uom.fyp.dslfrontend

import org.uom.fyp.engine.{Street, StreetType, RoadNetwork, RoadNetworkUnion}

import scala.util.parsing.combinator.JavaTokenParsers
import scala.io.Source

/**
 * Forms a parse tree and initiates actions responding to the structure of the program being
 * parsed.
 */
object Parser extends JavaTokenParsers {

  private var networks: List[RoadNetwork] = List()

  private var structuresInCurrentNetwork: List[AnyRef] = List()

  private def createNetwork(n: String): RoadNetwork = {
    val network: RoadNetwork = new RoadNetwork(n)
    networks = networks ++ List(network)

    structuresInCurrentNetwork.foreach((obj: AnyRef) => {
      if (obj.isInstanceOf[List]) {
        val street: Street = obj.asInstanceOf[List](0).asInstanceOf[Street]
        network.streetList(0).attachStreet(network, street.name, street.streetType, street.length,
          obj.asInstanceOf[List](1).asInstanceOf[Double], street.flow)
      } else {
        val street: Street = obj.asInstanceOf[Street]
        network.createStreet(street.name, street.streetType, street.length, street.flow, street.noOfLanes)
      }
    })

    network
  }

  /**
   * Parses a whole statement.
   * @return Parser for statement.
   */
  def statement = constructNetwork.+ ~ joinNetworks.? ~ given ~ runSimulation

  /**
   * Parses the <b>construct network</b> construct, as well as starts creating
   * the network.
   * @return Parser for network construction.
   */
  def constructNetwork = "construct" ~ "network" ~ ident ~ "(" ~ definitions ~ ")" ^^
    { case "construct" ~ "network" ~ network ~ "(" ~ _ ~ ")" => createNetwork(network) }

  /**
   * Parses the <b>join</b> construct, taking the names of two road networks.
   * @return Parser for joining two networks.
   */
  def joinNetworks = "join" ~ ident ~ "," ~ ident ^^
    { case "join" ~ n1 ~ "," ~ n2 => new RoadNetworkUnion(null, null) }

  /**
   * Parses the <b>given</b> construct.
   * @return Parser for the "given" construct.
   */
  def given = "given" ~ ident ~ ("," ~ ident).*

  /**
   * Parses the <b>run simulation</b> construct.
   * @return Parser to run the simulation.
   */
  def runSimulation = "run" ~ "simulation"

  /**
   * Parses actions related to road network construction, such as <b>create road</b> and
   * <b>block road</b>.
   * @return Parser for the actions related to road network construction.
   */
  def definitions = createRoad ~ (createRoad | attachRoad | blockRoad).*

  /**
   * Parses the <b>create primary road</b> construct, together with its length.
   * @return Parser for lane creation.
   */
  def createRoad = "create" ~ "primary" ~ "road" ~ ident ~ "with" ~ "length" ~ floatingPointNumber ~ "flow" ~ floatingPointNumber ^^ {
    case "create" ~ "primary" ~ "road" ~ road ~ "with" ~ "length" ~ len ~ "flow" ~ flow => {
      structuresInCurrentNetwork = List()
      structuresInCurrentNetwork = structuresInCurrentNetwork ++ List(new Street(road, StreetType.PRIMARY, len.toDouble, flow.toDouble))
    }
  }

  /**
   * Parses the <b>attach primary/secondary road</b> construct, together with its length.
   * @return Parser for lane attachment.
   */
  def attachRoad = "attach" ~ ("primary" | "secondary") ~ "road" ~ ident ~ "with" ~ "length" ~ floatingPointNumber ~ "at" ~ floatingPointNumber ^^
    {
      case "attach" ~ "primary" ~ "road" ~ road ~ "with" ~ "length" ~ len ~ "at" ~ pos ~ "flow" ~ flow => structuresInCurrentNetwork = structuresInCurrentNetwork ++ List(List(new Street(road.toString, StreetType.PRIMARY, len.toDouble, flow.toDouble), pos))
      case "attach" ~ "secondary" ~ "road" ~ road ~ "with" ~ "length" ~ len ~ "at" ~ pos ~ "flow" ~ flow => structuresInCurrentNetwork = structuresInCurrentNetwork ++ List(List(new Street(road.toString, StreetType.SECONDARY, len.toDouble, flow.toDouble), pos))
    }

  /**
   * Parses the <b>block</b> construct.
   * @return Parser for blocking.
   */
  def blockRoad = "block" ~ ident

  /**
   * Initializes the parsing process for some source file with the given
   * filename.
   * @param filename The filename of the file to be parsed.
   */
  def parse(filename: String) = {
    parseAll(statement, Source.fromFile(filename).mkString) match {
      case Success(query, _) => println("Yeah!")
      case Failure(msg, _) => println(msg)
      case Error(msg, _) => println(msg)
    }
  }

}
