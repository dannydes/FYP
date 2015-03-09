package org.uom.fyp.engine

import org.jgrapht.DirectedGraph

/**
 * Created by Desira Daniel on 2/11/2015.
 */
trait GraphFactory {
  def createGraph : DirectedGraph[Node, Lane]
}
