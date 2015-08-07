package org.uom.fyp.engine

/**
 * Created by Daniel on 7/14/2015.
 */
case class Roundabout() extends Node {

  private var exitR: Double = _

  def exitRate = exitR

  def exitRate_(exitR: Double) = {
    this.exitR = exitR
  }

}
