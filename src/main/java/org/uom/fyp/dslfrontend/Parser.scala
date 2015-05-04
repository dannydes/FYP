package org.uom.fyp.dslfrontend

import org.uom.fyp.engine.{RoadNetwork, RoadNetworkUnion}

import scala.util.parsing.combinator.JavaTokenParsers
import scala.io.Source

/**
 * Forms a parse tree and initiates actions responding to the structure of the program being
 * parsed.
 */
object Parser extends JavaTokenParsers {

  /**
   * Parses a whole statement.
   * @return
   */
  def statement = constructNetwork.+ ~ joinNetworks.? ~ given ~ runSimulation

  /**
   * Parses the "construct network" construct, as well as starts creating
   * the network.
   * @return
   */
  def constructNetwork = "construct" ~ "network" ~ stringLiteral ~ "(" ~ roads ~ ")" ^^
    { case "construct" ~ "network" ~ network ~ "(" ~ _ ~ ")" => new RoadNetwork(network) }

  /**
   * Parses the join construct, taking the names of two road networks.
   * @return
   */
  def joinNetworks = "join" ~ stringLiteral ~ "," ~ stringLiteral ^^
    { case "join" ~ n1 ~ "," ~ n2 => new RoadNetworkUnion(null, null) }

  /**
   * Parses the given construct.
   * @return
   */
  def given = "given" ~ stringLiteral ~ ("," ~ stringLiteral).*

  /**
   * Parses the run simulation construct.
   * @return
   */
  def runSimulation = "run" ~ "simulation"

  /**
   * Parses actions related to road network construction, such as "create road" and
   * "block road".
   * @return
   */
  def roads = createRoad ~ (attachRoad | blockRoad).*

  /**
   * Parses the create primary road construct, together with its length.
   * @return
   */
  def createRoad = "create" ~ "primary" ~ "road" ~ stringLiteral ~ "with" ~ "length" ~ floatingPointNumber

  /**
   * Parses the attach primary/secondary road construct, together with its length.
   * @return
   */
  def attachRoad = "attach" ~ ("primary" | "secondary") ~ "road" ~ stringLiteral ~ "with" ~ "length" ~ floatingPointNumber

  /**
   * Parses the block construct.
   * @return
   */
  def blockRoad = "block" ~ stringLiteral

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
