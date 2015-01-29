package org.uom.fyp

/**
 * Created by Desira Daniel on 12/6/2014.
 */
class Token(lex: String, r: Integer, c: Integer) {
  def lexeme = lex
  def row = r
  def column = c

  val keywords = Array("find", "if", "road", "is", "closed", "open", "when", "density")

  def isKeyword = keywords.contains(lexeme)
  def isDoubleLit = ! lexeme.contains("a-z".r)
}
