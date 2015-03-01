package org.uom.fyp

import scala.io.Source

/**
 * DSL lexer
 */
class Lexer(sourcefile: String) {
  var lines: Array[String] = Array()

  def produceGenericTokens = {
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

  def specializeTokens = {
    produceGenericTokens.map((token: Token) => {
      if (token.isKeyword) {
        new KeywordToken(token.lexeme, token.row, token.column)
      } else if (token.isDoubleLit) {
        new DoubleLitToken(token.lexeme, token.row, token.column)
      }
    })
  }
}
