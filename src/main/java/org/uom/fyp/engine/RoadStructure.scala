package org.uom.fyp.engine

/**
 * RoadStructure enum.
 */
object RoadStructure {

  /**
   * Value type.
   */
  sealed trait RoadStructure

  /**
   * Value for a generic node.
   */
  case object Default extends RoadStructure

  /**
   * Value for a T-junction.
   */
  case object TJunction extends RoadStructure

  /**
   * Value for a roundabout.
   */
  case object Roundabout extends RoadStructure {

    private var exitRates: List[(String, Double, Double)] = List()

    /**
     * Adds an exit rate for a roundabout node, so that it can be copied to the
     * graph vertex representing the roundabout after the graph is built.
     * @param road The name of the road where the roundabout is found.
     * @param pos The relative position of the roundabout within the road.
     * @param rate The exit rate of the roundabout.
     */
    def addExitRate(road: String, pos: Double, rate: Double) = {
      exitRates = exitRates ++ List((road, pos, rate))
    }

    /**
     * Returns the exit rate of the roundabout in the given road and the given relative position.
     * @param road The name of the road.
     * @param pos The relative position of the roundabout within the road.
     */
    def lookupExitRate(road: String, pos: Double) = {
      exitRates.filter((el: (String, Double, Double)) => el._1 == road && el._2 == pos)(0)._3
    }

  }

  /**
   * Value for a crossroads intersection.
   */
  case object Crossroads extends RoadStructure {

    private var timings: List[(String, String, Double, Double)] = List()

    /**
     * Adds a timing pair for a crossroad node, so that it can be copied to the
     * graph vertex representing the crossroad after the graph is built.
     * @param r1 The name of the first road.
     * @param r2 The name of the second road.
     * @param t1 The timing of the first road.
     * @param t2 The timing of the second road.
     */
    def addTimingPair(r1: String, r2: String, t1: Double, t2: Double) = {
      timings = timings ++ List((r1, r2, t1, t2))
    }

    /**
     * Returns the tuple from where the access the waiting times for the roads meeting at the
     * crossroads intersection for the names of the later roads.
     * @param r1 The name of the first road.
     * @param r2 The name of the second road.
     */
    def lookupTimingPair(r1: String, r2: String) = {
      timings.filter((el: (String, String, Double, Double)) => el._1 == r1 && el._2 == r2)(0)
    }

  }

  /**
   * Value for a pedestrian crossing.
   */
  case object PedestrianCrossing extends RoadStructure

}
