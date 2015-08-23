package org.uom.fyp.engine

/**
 * Exception thrown when either of intersection points is equal to 0 or the length of the respective
 * street, as such a condition invalidates the crossroad.
 */
class NotCrossroadsException extends Exception {

  /**
   * Returns the error message.
   */
  override def getMessage = "An intersection at the edges of a street invalidate crossroad."

}
