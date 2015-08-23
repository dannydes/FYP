package org.uom.fyp.engine

/**
 * Created by Daniel on 7/15/2015.
 */
case class PedestrianCrossing() extends Node {

  private var t: Double = _

  def timing = t

  def timing_(t: Double) = {
    this.t = t
  }
}
