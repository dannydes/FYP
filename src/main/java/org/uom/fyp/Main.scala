package org.uom.fyp

import org.uom.fyp.engine.RoadNetwork

/**
 * Calls parser and prediction engine so as to make a workable language.
 */
object Main {

  def main(args: Array[String]): Unit = {
    //Construct sample road network.
    val zabbarPrimaries = new RoadNetwork("Zabbar Primaries")
    val parishStreet = zabbarPrimaries.createLane(null)

    parishStreet.attachLane(zabbarPrimaries, null)
    parishStreet.attachLane(zabbarPrimaries, null)
    parishStreet.attachLane(zabbarPrimaries, null)

    //Find the time taken to go from one node to another and print it!
    println(zabbarPrimaries.initSimulation(10))
  }

}
