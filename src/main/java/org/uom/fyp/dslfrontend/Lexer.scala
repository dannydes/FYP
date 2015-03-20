package org.uom.fyp.dslfrontend

import scala.io.Source

/**
 * Generates an array of tokens from a the contents of the source file whose filename
 * is provided as a constructor parameter.
 * @param sourcefile Filename of the file containing source code.
 */
class Lexer(sourcefile: String) {

  var tokenArray = produceGenericTokens
  specializeTokens

  /**
   * Returns an array of tokens which occur in the source file.
   */
  def tokens = tokenArray

  /**
   * Returns an array of tokens of no type by splitting the text within <b>sourcefile</b>
   * over whitespaces.
   */
  private def produceGenericTokens = {
    val lines: Array[String] = Array()

    //Load file content into the array.
    Source.fromFile(sourcefile).getLines().copyToArray(lines)

    var i = 0
    var tokens: Array[Token] = Array()
    var tokenRow = 0

    for (i <- 0 to lines.length)
    {
      val lineTokens: Array[Token] = lines(i).split(" ").filter((x: String) => x != "")
        .map((x: String) => {
          tokenRow = lines(i).indexOf(x, tokenRow)
          new Token(x, tokenRow, i)
        })
      tokens = Array.concat(tokens, lineTokens)
    }

    tokens
  }

  /**
   * Handles token specialization.
   */
  private def specializeTokens = {
    tokenArray.map((token: Token) => {
      if (token.isKeyword) {
        new KeywordToken(token.lexeme, token.row, token.column)
      } else if (token.isDoubleLit) {
        new DoubleLitToken(token.lexeme, token.row, token.column)
      }
    })
  }

}
