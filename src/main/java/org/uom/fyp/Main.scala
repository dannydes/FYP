package org.uom.fyp

import org.uom.fyp.dslfrontend.Lexer

/**
 * Calls parser and prediction engine so as to make a workable language.
 */
object Main {

  def main(args: Array[String]): Unit = {
    val lexer = new Lexer("scripts/density_if_closed.tal")

  }

}
