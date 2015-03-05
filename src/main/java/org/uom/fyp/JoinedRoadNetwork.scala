package org.uom.fyp

import org.jgrapht.Graph
import org.jgrapht.graph.{GraphUnion, DirectedGraphUnion}
/**
 * Created by Daniel on 3/5/2015.
 */
class JoinedRoadNetwork(n1: RoadNetwork, n2: RoadNetwork) extends RoadNetwork with Graph { }
