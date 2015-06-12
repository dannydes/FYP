package org.uom.fyp.graphvisual

import javax.swing.{JScrollPane, JFrame}

import org.jgraph.JGraph
import org.uom.fyp.engine.{StreetType, RoadNetwork}
import org.jgrapht.ext.JGraphModelAdapter

/**
 * Created by Daniel on 6/12/2015.
 */
object RoadNetworkVisualiser {

  def main(args: Array[String]): Unit = {
    val zabbarPrimaries: RoadNetwork = new RoadNetwork("zabbarPrimaries")
    val parishStreet = zabbarPrimaries.createStreet("Santwarju", StreetType.PRIMARY, 400)

    parishStreet.attachStreet(zabbarPrimaries, "", StreetType.PRIMARY, 200, 100)
    parishStreet.attachStreet(zabbarPrimaries, "", StreetType.PRIMARY, 200, 250)
    parishStreet.attachStreet(zabbarPrimaries, "", StreetType.PRIMARY, 200, 320)

    zabbarPrimaries.completeEdgeList()
    zabbarPrimaries.buildGraph()

    val graph: JGraph = new JGraph(new JGraphModelAdapter(zabbarPrimaries))

    val frame: JFrame = new JFrame
    frame.getContentPane.add(new JScrollPane(graph))
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
    frame.pack()
    frame.setVisible(true)
  }

}
