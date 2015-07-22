package org.uom.fyp

import org.uom.fyp.dslfrontend.Parser
import org.uom.fyp.engine.{StreetType, RoadNetwork}

/**
 * Calls parser and prediction engine so as to make a workable language.
 */
object Main {

  def main(args: Array[String]): Unit = {
    /*//Construct sample road network.
    val zabbarPrimaries = new RoadNetwork("Zabbar Primaries")
    val parishStreet = zabbarPrimaries.createStreet("Santwarju", StreetType.PRIMARY, 10, 400)

    parishStreet.attachStreet(zabbarPrimaries, "", StreetType.PRIMARY, 200, 3, 100)
    parishStreet.attachStreet(zabbarPrimaries, "", StreetType.PRIMARY, 200, 2, 250)
    parishStreet.attachStreet(zabbarPrimaries, "", StreetType.PRIMARY, 200, 2, 320)

    zabbarPrimaries.completeEdgeList()
    zabbarPrimaries.buildGraph()
     zabbarPrimaries.markGraphNodes()

    //Find the time taken to go from one node to another and print it!
    zabbarPrimaries.initSimulation(10)*/

    Parser.parse("C:\\Users\\Daniel\\OneDrive\\Documents\\zabbar_primaries.rdl")
  }

}
