package org.uom.fyp

import scala.io.Source

/**
 * Created by Desira Daniel on 12/5/2014.
 */
class Lexer(sourcefile: String) {
  var lines: Array[String] = Array()

  def produceGenericTokens = {
    Source.fromFile("scripts/density_if_closed.tal").getLines().copyToArray(lines)
    var i = 0
    for (i <- 0 to lines.length)
    {
      lines(i).split(" ").filter((x: String) => x != "")
        .map((x: String) => new Token(x, 0, i))
    }
  }

  def specializeTokens = {
    produceGenericTokens.map((token: Token) => {
      if (token.isKeyword) {
        new KeywordToken(token.getLexeme, token.getRow, token.getCol)
      }
    })
  }
}
