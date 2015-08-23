package org.uom.fyp

import org.uom.fyp.dslfrontend.Parser
import org.uom.fyp.engine.{StreetType, RoadNetwork}

/**
 * Calls parser and prediction engine so as to make a workable language.
 */
object Main {

  def main(args: Array[String]): Unit = {
    try {
      Parser.parse("C:\\Users\\Daniel\\OneDrive\\Documents\\zabbar_primaries.rpl")
    } catch {
      case e: Exception => println(e.getMessage + "\n" + e.getStackTrace)
    }
  }

}
