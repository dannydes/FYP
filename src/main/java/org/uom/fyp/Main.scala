package org.uom.fyp

import org.uom.fyp.dslfrontend.Parser
import org.uom.fyp.engine.{StreetType, RoadNetwork}

/**
 * Calls parser and prediction engine so as to make a workable language.
 */
object Main {

  def main(args: Array[String]): Unit = {
    val filename = if (args.length > 0) {
      args(0)
    } else {
      println("Please enter the path of the script to run.")
      scala.io.StdIn.readLine()
    }
    try {
      Parser.parse("C:\\Users\\Daniel\\OneDrive\\Documents\\zabbar_primaries.rpl")
    } catch {
      case e: ArithmeticException => println("Vehicles cannot be 0!")
      //case e: ArrayIndexOutOfBoundsException => println("Something is wrong. Are you sure you referenced all your roads correctly?")
      case e: Exception => {
        println(e.getMessage)
        e.printStackTrace()
      }
    }
  }

}
