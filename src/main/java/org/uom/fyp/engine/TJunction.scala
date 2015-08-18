package org.uom.fyp.engine

/**
 * Represents nodes in the middle of T-Junctions.
 */
case class TJunction() extends Node {

  private var p: Edge = _
  private var c1: Edge = _
  private var c2: Edge = _

  /**
   * Returns the priority edge.
   */
  def priority = p

  /**
   * Sets the priority edge to the edge specified.
   * @param p New priority edge.
   */
  def priority_(p: Edge) = {
    this.p = p
  }

  /**
   * Returns the first converging edge found by <b>findEdges()</b> or the edge set
   * through <b>converging1_()</b>.
   */
  def converging1 = c1

  /**
   * Sets one of the converging edges.
   * @param c1 A new converging edge.
   */
  def converging1_(c1: Edge) = {
    this.c1 = c1
  }

  /**
   * Returns the second converging edge found by <b>findEdges()</b> or the edge set
   * through <b>converging2_()</b>.
   */
  def converging2 = c2

  /**
   * Sets the other converging edge.
   * @param c2 A new converging edge.
   */
  def converging2_(c2: Edge) = {
    this.c2 = c2
  }

  /**
   * Finds the converging and priority edges for the context T-Junction node and
   * sets the respective edge objects to the ones found.
   * @param network Network object where the node is to be found.
   */
  def findEdges(network: RoadNetwork) = {
    var edges: Array[Edge] = Array()
    edges = network.edgesOf(this).toArray(edges)

    val test = edges.filter((e: Edge) => e.streetName == edges(0).streetName)

    if (test.size == 2) {
      c1 = test(0)
      c2 = test(1)
      p = edges.filter((e: Edge) => e.streetName != edges(0).streetName)(0)
    } else {
      c1 = edges(1)
      c2 = edges(2)
      p = edges(0)
    }
  }

}
