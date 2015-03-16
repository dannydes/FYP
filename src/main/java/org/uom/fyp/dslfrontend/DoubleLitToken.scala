package org.uom.fyp.dslfrontend

/**
 * Created by Desira Daniel on 1/29/2015.
 * @param lex Lexeme.
 * @param r Row.
 * @param c Column.
 */
case class DoubleLitToken(lex: String, r: Int, c: Int) extends Token(lex, r, c) {

}
