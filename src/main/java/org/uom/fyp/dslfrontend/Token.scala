package org.uom.fyp.dslfrontend

/**
 * Token class
 */
class Token(lex: String, r: Int, c: Int) {
  def lexeme = lex
  def row = r
  def column = c

  val keywords = Array("find", "if", "road", "is", "closed", "open", "when", "density")

  def isKeyword = keywords.contains(lexeme)
  def isDoubleLit = ! lexeme.contains("a-z".r)
}
