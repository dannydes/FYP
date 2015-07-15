package org.uom.fyp.engine

/**
 * Created by Daniel on 7/14/2015.
 */
case class TJunction(priority: Edge, c1: Edge, c2: Edge) extends Node {
  val tPriority = priority
  val converging1 = c1
  val converging2 = c2
}
