package org.uom.fyp

import org.uom.fyp.engine.{StreetType, RoadNetwork}

/**
 * Calls parser and prediction engine so as to make a workable language.
 */
object Main {

  def main(args: Array[String]): Unit = {
    //Construct sample road network.
    val zabbarPrimaries = new RoadNetwork("Zabbar Primaries")
    val parishStreet = zabbarPrimaries.createStreet("Santwarju", StreetType.PRIMARY, 400)

    parishStreet.attachStreet(zabbarPrimaries, "", StreetType.PRIMARY, 200, 100)
    parishStreet.attachStreet(zabbarPrimaries, "", StreetType.PRIMARY, 200, 250)
    parishStreet.attachStreet(zabbarPrimaries, "", StreetType.PRIMARY, 200, 320)

    zabbarPrimaries.completeEdgeList()
    zabbarPrimaries.buildGraph()

    //Find the time taken to go from one node to another and print it!
    zabbarPrimaries.initSimulation(10)
  }

}
