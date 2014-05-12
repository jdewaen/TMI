import java.util.ArrayList;

public class BruteForce extends Algorithm {

	public BruteForce() {
	}

	public BruteForce(Circle[] circles) {
		super(circles);
	}

	@Override
	public void solve() {
		long startTime = System.currentTimeMillis();

		for (int i = 0; i < circles.length; i++) {
			for (int j = i + 1; j < circles.length; j++) {
				ArrayList<Intersection> newIntersections = circles[i].intersects(circles[j]);
				if (newIntersections != null) {
					intersections.addAll(newIntersections);
				}
			}
		}
		time = (int) (System.currentTimeMillis() - startTime);
	}

	@Override
	public boolean isImplemented() {
		return true;
	}
}
