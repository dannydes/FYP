package org.uom.fyp.engine

/**
 * RoadStructure enum.
 */
object RoadStructure {

  sealed trait EnumVal
  case object Default extends EnumVal
  case object TJunction extends EnumVal
  case object Roadabout extends EnumVal
  case object Crossroads extends EnumVal

}
