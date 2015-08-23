package org.uom.fyp.engine

/**
 * Represents a generic node.
 */
class Node {

  private var lambda: Double = _

  private var congested: Boolean = _

  def arrivalRate_(lambda: Double) = {
    this.lambda = lambda
  }

  def arrivalRate = lambda

  def departureRate = ???

  def speed = ???

  def isCongested_(congested: Boolean = true) = {
    this.congested = congested
  }

  def isCongested = congested

}
