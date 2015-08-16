package org.uom.fyp.graphvisual

import javax.swing.{JScrollPane, JFrame}

import org.jgraph.JGraph
import org.jgrapht.ext.JGraphModelAdapter
import org.uom.fyp.dslfrontend.Parser

/**
 * Created by Daniel on 6/12/2015.
 */
object RoadNetworkVisualiser {

  def main(args: Array[String]): Unit = {
    Parser.parse("C:\\Users\\Daniel\\OneDrive\\Documents\\zabbar_primaries.rdl")

    val graph: JGraph = new JGraph(new JGraphModelAdapter(Parser.networkGraph))

    val frame: JFrame = new JFrame
    frame.getContentPane.add(new JScrollPane(graph))
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
    frame.pack()
    frame.setVisible(true)
  }

}
