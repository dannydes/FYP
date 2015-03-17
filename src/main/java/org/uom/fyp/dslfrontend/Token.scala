package org.uom.fyp.dslfrontend

/**
 * Defines operations over tokens.
 * @param lex The lexeme.
 * @param r The row at which token is found.
 * @param c The column at which token is found.
 */
class Token(lex: String, r: Int, c: Int) {

  /**
   * Returns the token's lexeme.
   */
  def lexeme = lex

  /**
   * Returns the row at which the token is found.
   */
  def row = r

  /**
   * Returns the column at which the token is found.
   */
  def column = c

  /**
   * Holds an array of keywords.
   */
  val keywords = Array("find", "if", "road", "is", "closed", "open", "when", "density")

  /**
   * Returns whether the lexeme is a keyword.
   */
  def isKeyword = keywords.contains(lexeme)

  /**
   * Returns whether the lexeme is a double literal.
   */
  def isDoubleLit = ! lexeme.contains("a-z".r)

}
