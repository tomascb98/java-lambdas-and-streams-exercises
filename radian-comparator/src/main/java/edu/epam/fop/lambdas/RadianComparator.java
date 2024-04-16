package edu.epam.fop.lambdas;

import java.util.Comparator;

public final class RadianComparator {

  /*
   * Compares 2 angles (passed in radians). The angle counts from 0 up to 2π, the period must
   * be ignored, i.e. if angle is greater than 2π, then it must be converted to the range [0; 2π).
   * Precision must be 0.001 (delta), so if |a - b| < 0.001, then a == b.
   * 0 == 2π
   */
  public static Comparator<Double> get() {
    double DELTA = 0.001;
    return (angle1, angle2) -> {
      if(angle1 == null && angle2 == null) return 0;
      if(angle1 == null) return -1;
      if(angle2 == null) return 1;

      angle1 = angle1%(2*Math.PI);
      angle2 = angle2%(2*Math.PI);
      if(Math.abs(angle1-angle2) < DELTA) return 0;
      else if(angle1 > angle2) return 1;
      else return -1;
    };
  }
}
