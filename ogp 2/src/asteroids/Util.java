package asteroids;

public class Util {
  public static final double EPSILON = 0.0001;

  public static boolean fuzzyEquals(double x, double y) {
    if (Double.isNaN(x) || Double.isNaN(y))
      return false;
    return Math.abs(x - y) <= EPSILON || Double.valueOf(x).equals(Double.valueOf(y));
  }

  public static boolean fuzzyLessThanOrEqualTo(double x, double y) {
    if (fuzzyEquals(x, y)) {
      return true;
    } else {
      return Double.compare(x, y) < 0;
    }
  }
  
  public static double absoluteError(double expected, double actual) {
    return Math.abs(expected - actual);
  }

  public static double relativeError(double expected, double actual) {
    return absoluteError(expected, actual) / Math.abs(expected);
  }
}