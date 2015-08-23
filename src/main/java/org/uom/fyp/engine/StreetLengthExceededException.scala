package org.uom.fyp.engine

/**
 * Exception to be thrown if a street is attached at a position that exceeds the length of the street to which the former
 * street is attached.
 * @param point The position that has been passed to the street that was to be attached.
 */
class StreetLengthExceededException(point: Double) extends Exception {

  /**
   * Returns an error message.
   */
  override def getMessage = "A street has been attached at point that exceeds the length of the original. Point: " + point

}
