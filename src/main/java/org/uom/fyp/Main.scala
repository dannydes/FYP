package org.uom.fyp

/**
 * Created by Desira Daniel on 1/27/2015.
 */
object Main {
  def main(args: Array[String]): Unit = {
    val lexer = new Lexer("scripts/density_if_closed.tal")
    lexer.specializeTokens
  }
}
