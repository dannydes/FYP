package org.uom.fyp.engine

/**
 * Represents a roundabout.
 */
case class Roundabout() extends Node {

  private var exitR: Double = _

  /**
   * Returns the roundabout's exit rate.
   */
  def exitRate = exitR

  /**
   * Sets the roundabout's exit rate.
   * @param exitR Exit rate.
   */
  def exitRate_(exitR: Double) = {
    this.exitR = exitR
  }

}
