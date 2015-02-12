package org.uom.fyp

import org.jgrapht.DirectedGraph

/**
 * Created by Desira Daniel on 2/11/2015.
 */
trait GraphFactory {
  def createGraph : DirectedGraph[Node, Lane]
}
