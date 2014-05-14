public abstract class Util {

	public static final double e = 1e-8;
	public static boolean fuzzyEquals(double x1, double x2) {
		return (Math.abs(x1 - x2) < e);
	}

}
