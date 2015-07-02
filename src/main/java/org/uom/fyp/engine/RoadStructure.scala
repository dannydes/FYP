package org.uom.fyp.engine

/**
 * Created by Daniel on 6/29/2015.
 */
object RoadStructure {

  sealed trait EnumVal
  case object Default extends EnumVal
  case object TJunction extends EnumVal
  case object Roadabout extends EnumVal

}
