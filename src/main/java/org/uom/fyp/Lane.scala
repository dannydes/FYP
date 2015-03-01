package org.uom.fyp

import org.jgrapht.graph.DefaultEdge

/**
 * Created by Desira Daniel on 2/10/2015.
 */
class Lane(l: Double, w: Double) extends DefaultEdge {
  def lLen: Double = l
  def width: Double = w

  def attachLane(properties: AnyRef): Lane = NetworkUtils.createLane(this.getTarget.asInstanceOf[Node])
}
