package org.uom.fyp.dslfrontend

import org.uom.fyp.engine.{RoadNetwork, RoadNetworkUnion}

import scala.util.parsing.combinator.JavaTokenParsers
import scala.io.Source

/**
 * Forms a parse tree from an array of tokens, depending on their type and
 * content.
 */
object Parser extends JavaTokenParsers {

  def statement = constructNetwork.+ ~ joinNetworks.? ~ given ~ runSimulation

  def constructNetwork = "construct" ~ "network" ~ stringLiteral ~ "(" ~ roads ~ ")" ^^
    { case "construct" ~ "network" ~ network ~ "(" ~ _ ~ ")" => new RoadNetwork(network) }

  def joinNetworks = "join" ~ stringLiteral ~ "," ~ stringLiteral ^^
    { case "join" ~ n1 ~ "," ~ n2 => new RoadNetworkUnion(null, null) }

  def given = "given" ~ stringLiteral ~ ("," ~ stringLiteral).*

  def runSimulation = "run" ~ "simulation"

  def roads = createRoad ~ (attachRoad | blockRoad).*

  def createRoad = "create" ~ "primary" ~ "road" ~ stringLiteral ~ "with" ~ "length" ~ floatingPointNumber

  def attachRoad = "attach" ~ ("primary" | "secondary") ~ "road" ~ stringLiteral ~ "with" ~ "length" ~ floatingPointNumber

  def blockRoad = "block" ~ stringLiteral

  def parse(filename: String) = {
    parseAll(statement, Source.fromFile(filename).mkString) match {
      case Success(query, _) => println("Yeah!")
      case Failure(msg, _) => println(msg)
      case Error(msg, _) => println(msg)
    }
  }

}
