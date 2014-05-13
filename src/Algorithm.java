import java.util.Collection;
import java.util.HashSet;
import java.util.TreeSet;

public abstract class Algorithm {
	protected Circle[] circles;
	protected int time = 0;
	protected Collection<Intersection> intersections = new HashSet<Intersection>();

	public Algorithm() {

	};

	public Algorithm(Circle[] circles) {
		this.circles = circles;
	}

	public void setCircles(Circle[] circles) {
		this.circles = circles;
	}

	public abstract void solve() throws Exception;

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
