import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Edge implements Comparable<Edge> {

	private double y = 0;
	private Circle circle;
	EdgeType type;
	Sweepline line;

	public Edge(EdgeType type, Circle circle, Sweepline line) throws Exception {
		if (type == EdgeType.POINT)
			throw new Exception("Tried to use TOP/BOTTOM Edge constructor with POINT type");
		this.circle = circle;
		this.type = type;
		this.line = line;
	}

	public Edge(EdgeType type, double y, Sweepline line) throws Exception {
		if (type != EdgeType.POINT)
			throw new Exception("Tried to use POINT Edge constructor without correct type");
		this.y = y;
		this.type = type;
		this.line = line;
	}

	public double getY(double lineX) {
		if (type == EdgeType.TOP || type == EdgeType.BOTTOM) {
			List<Intersection> temp = circle.intersects(lineX);
			if (temp.size() == 0) {
				return circle.getY();
			} else if (type == EdgeType.TOP) {
				return circle.intersects(lineX).get(0).getY();
			} else {

				return circle.intersects(lineX).get(1).getY();
			}
		} else {
			return y;
		}
	}

	public List<Intersection> intersects(Edge other) {
		if (this.getCircle() == other.getCircle())
			return new ArrayList<Intersection>();
		List<Intersection> raw = circle.intersects(other.getCircle());
		List<Intersection> solution = new ArrayList<Intersection>();
		Iterator<Intersection> iter = raw.iterator();
		Intersection current;
		while (iter.hasNext()) {
			current = iter.next();
			if (current.getX() > line.getX())
				solution.add(current);
		}
		return solution;
	}

	public Circle getCircle() {
		return circle;
	}

	public EdgeType getType() {
		return type;
	}

	@Override
	public boolean equals(Object obj) {
		if (this.getClass() == obj.getClass()) {
			Edge other = (Edge) obj;
			boolean result = (other.circle.equals(circle) && other.getType() == type);
			return result;
		}
		return false;

	}

	@Override
	public int hashCode() {
		String prehash = type.toString() + circle.number;
		return prehash.hashCode();
	}

	public int compareTo(Edge other) {
		if (other.circle.equals(circle)) {
			if (other.getType() == type) {
				return 0;
			} else if (type == EdgeType.TOP) {
				return 1;
			} else {
				return -1;
			}
		}
		if (other.getY(line.getX()) >= getY(line.getX())) {
			return -1;
		} else if (other.getY(line.getX()) < getY(line.getX())) {
			return 1;
		} else {
			return 0;
		}
	}

}
