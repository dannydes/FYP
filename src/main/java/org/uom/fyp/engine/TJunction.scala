package org.uom.fyp.engine

/**
 * Created by Daniel on 7/14/2015.
 */
case class TJunction() extends Node {

  private var p: Edge = _
  private var c1: Edge = _
  private var c2: Edge = _

  def priority = p

  def priority_(p: Edge) = {
    this.p = p
  }

  def converging1 = c1

  def converging1_(c1: Edge) = {
    this.c1 = c1
  }

  def converging2 = c2

  def converging2_(c2: Edge) = {
    this.c2 = c2
  }

}
