package org.uom.fyp.engine

/**
 * Exception to be thrown if a lane is attached at a position that exceeds the length of the lane to which the former
 * lane is attached.
 * @param point The position that has been passed to the lane that was to be attached.
 */
class StreetLengthExceededException(point: Double) extends Exception {

  /**
   * Returns an error message.
   */
  override def getMessage = "A lane has been attached at point that exceeds the length of the original. Point: " + point

}
