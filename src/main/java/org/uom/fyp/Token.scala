package org.uom.fyp

/**
 * Created by Desira Daniel on 12/6/2014.
 */
abstract class Token(lexeme: String, row: Integer, col: Integer) {
  val keywords = Array("find", "if", "road", "is", "closed", "open", "when", "density")

  def isKeyword = keywords.contains(lexeme)
  def isDoubleLit = ! lexeme.contains("a-z")
}
