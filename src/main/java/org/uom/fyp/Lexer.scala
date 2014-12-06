package org.uom.fyp

/**
 * Created by Desira Daniel on 12/5/2014.
 */
class Lexer(source: String) {
  def produceGenericTokens = source.split(" ").filter((x: String) => x != "")

  def specializeTokens =
}
