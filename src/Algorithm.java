import java.util.Collection;
import java.util.HashSet;
import java.util.TreeSet;

public abstract class Algorithm {
	protected Circle[] circles;
	protected int time = 0;
	protected Collection<Intersection> intersections = new HashSet<Intersection>();
	protected InteractiveDisplay display;

	public Algorithm() {

	};

	public Algorithm(Circle[] circles) {
		this.circles = circles;
	}

	public void setDisplay(InteractiveDisplay display) {
		this.display = display;
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

	public int getAmountofIntersections() {
		return intersections.size();
	}
}
