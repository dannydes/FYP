package org.uom.fyp.engine

/**
 * Created by Daniel on 5/6/2015.
 */
class LaneLengthExceededException(point: Double) extends Exception {

  override def getMessage = "A lane has been attached at point that exceeds the length of the original. Point: " + point

}
