package edu.epam.fop.lambdas;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import static edu.epam.fop.lambdas.Point.p;
import static edu.epam.fop.lambdas.Point.p0;

public interface CompositeAreasChecker {

  static Predicate<Point> task1() {
    Set<Point> set = new HashSet<>(Set.of(p0(), p(-0.5, 0.5), p(0, 1), p(-1.5, 0), p(-1.75, 0), p(0.5, -0.5), p(0.25, 1.5)));
    return (set::contains);
  }

  static Predicate<Point> task2() {
    Set<Point> set = new HashSet<>(Set.of(p(0, 1.8), p(1, 1.75), p(0.75, 2.75), p(1.75, 2.75)));
    return (set::contains);
  }

  static Predicate<Point> task3() {
    Set<Point> set = new HashSet<>(Set.of( p(-1.25, 1.1), p(-1.75, 2), p(-1.25, 2.25), p(-0.25, 2.5), p(-1.15, 2.75), p(-1.65, 2.15), p(-0.85, 2.65), p(-0.6, 2.47), p(-0.6, 2.3), p(-1.25, 1.6), p(-0.75, 1.75)));
    return (set::contains);
  }
}
