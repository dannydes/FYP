package org.uom.fyp.engine

/**
 * RoadStructure enum.
 */
object RoadStructure {

  sealed trait RoadStructure
  case object Default extends RoadStructure
  case object TJunction extends RoadStructure
  case object Roadabout extends RoadStructure {
    var exitRates = List()
  }
  case object Crossroads extends RoadStructure
  case object PedestrianCrossing extends RoadStructure

}
