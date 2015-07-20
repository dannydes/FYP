package org.uom.fyp.engine

/**
 * Created by Daniel on 7/14/2015.
 */
case class TJunction(p: Edge, c1: Edge, c2: Edge) extends Node {
  val priority = p
  val converging1 = c1
  val converging2 = c2
}
