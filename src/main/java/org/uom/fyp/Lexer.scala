package org.uom.fyp

/**
 * Created by Desira Daniel on 12/5/2014.
 */
class Lexer(source: String) {
  def produceGenericTokens = source.split(" ").filter((x: String) => x != "")
                                    .map((x: String) => new Token(x, 0, 0))

  def specializeTokens = {
    produceGenericTokens.map((token: Token) => {
      if (token.isKeyword) {
        new KeywordToken(token.getLexeme, token.getRow, token.getCol)
      }
    })
  }
}
