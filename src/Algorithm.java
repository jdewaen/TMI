import java.util.ArrayList;

public abstract class Algorithm {
	protected Circle[] circles;
	protected int time = 0;
	protected ArrayList<Intersection> intersections = new ArrayList<Intersection>();

	public Algorithm() {

	};

	public Algorithm(Circle[] circles) {
		this.circles = circles;
	}

	public void setCircles(Circle[] circles) {
		this.circles = circles;
	}

	public abstract void solve();

	public int getTime() {
		return time;
	}

	public int getN() {
		return circles.length;
	}

	public Iterable<Intersection> getIntersections() {
		return intersections;
	}

	public boolean isImplemented() {
		return false;
	}
}
