package org.uom.fyp.dslfrontend

import org.uom.fyp.engine.StreetType.StreetType
import org.uom.fyp.engine.{Street, StreetType, RoadNetwork}

import scala.util.parsing.combinator.JavaTokenParsers
import scala.io.Source

/**
 * Forms a parse tree and initiates actions responding to the structure of the program being
 * parsed.
 */
object Parser extends JavaTokenParsers {

  private var network: RoadNetwork = _

  private def attachRoadHelper(road: String, streetType: StreetType, len: String, vehicles: String, pos: String, lanes: String) = {
    network.streetList(0).attachStreet(network, road, streetType, parseDouble(len).toList(0), parseDouble(pos).toList(0), parseInt(vehicles).toList(0), parseInt(lanes).toList(0))
  }

  private def parseDouble(s: String) = try { Some(s.toDouble) } catch { case _: Throwable => None }

  private def parseInt(s: String) = try { Some(s.toInt) } catch { case _: Throwable => None }

  /**
   * Parses a whole statement.
   * @return Parser for statement.
   */
  def statement = constructNetwork ~ definitions ~ runSimulation

  /**
   * Parses the <b>construct network</b> construct, as well as starts creating
   * the network.
   * @return Parser for network construction.
   */
  def constructNetwork = ("construct" ~> "network" ~> ident ~> "(") ^^
    { case n => network = new RoadNetwork(n) }

  /**
   * Parses the <b>run simulation</b> construct.
   * @return Parser to run the simulation.
   */
  def runSimulation = "run" ~ "simulation" ^^ {
    case _ => network.initSimulation(0)
  }

  /**
   * Parses actions related to road network construction, such as <b>create road</b> and
   * <b>block road</b>.
   * @return Parser for the actions related to road network construction.
   */
  def definitions = createRoad ~ (attachRoad | blockRoad | intersection).* ~ ")" ^^ {
    case _ => {
      network.completeEdgeList()
      network.buildGraph()
    }
  }

  /**
   * Parses the <b>create primary road</b> construct, together with its length.
   * @return Parser for lane creation.
   */
  def createRoad = ("create" ~> "primary") ~ ("road" ~> ident) ~ ("with" ~> "length" ~> floatingPointNumber) ~ ("vehicles" ~> wholeNumber) ~
    ("lanes" ~> wholeNumber).? ^^ {
    case "primary" ~ road ~  len ~ vehicles ~ lanes => {
      network.createStreet(road, StreetType.PRIMARY, parseDouble(len).toList(0), parseInt(vehicles).toList(0), parseInt(lanes.getOrElse("1")).toList(0))
    }
  }

  /**
   * Parses the <b>attach primary/secondary road</b> construct, together with its length.
   * @return Parser for lane attachment.
   */
  def attachRoad = ("attach" ~> ("primary" | "secondary")) ~ ("road" ~> ident) ~ ("with" ~> "length" ~> floatingPointNumber) ~ ("at" ~>
    floatingPointNumber) ~ ("vehicles" ~> wholeNumber) ~ ("lanes" ~> wholeNumber).? ^^
    {
      case "primary" ~ road ~ len ~ pos ~ vehicles ~ lanes => attachRoadHelper(road, StreetType.PRIMARY, len, vehicles, pos, lanes.getOrElse("1"))
      case "secondary" ~ road ~ len ~ pos ~ vehicles ~ lanes => attachRoadHelper(road, StreetType.SECONDARY, len, vehicles, pos, lanes.getOrElse("1"))
    }

  def intersection = ("interection" ~> ident) ~ ("at" ~> floatingPointNumber) ~ ("with" ~> ident) ~ ("at" ~ floatingPointNumber)

  /**
   * Parses the <b>block</b> construct.
   * @return Parser for blocking.
   */
  def blockRoad = ("block" ~> ident) ^^ { case road => network.blockStreet(road) }

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
