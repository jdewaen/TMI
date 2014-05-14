import java.util.ArrayList;
import java.util.List;

public class Intersection {

	private double x, y;
	public Edge top, bottom;
	boolean simple;

	public Intersection(double x, double y) {
		this.x = x;
		this.y = y;
		simple = true;
	}

	public Intersection(double x, double y, Edge top, Edge bottom) {
		this.x = x;
		this.y = y;
		this.top = top;
		this.bottom = bottom;
		simple = false;
	}

	public List<Edge> getEdges() {
		ArrayList<Edge> result = new ArrayList<Edge>();
		result.add(top);
		result.add(bottom);
		return result;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public boolean contains(Edge edge) {
		return (top == edge || bottom == edge);
	}

	@Override
	public boolean equals(Object obj) {
		if (this.getClass() == obj.getClass()) {
			Intersection other = (Intersection) obj;
			if (simple) {
				if (Util.fuzzyEquals(x, other.x) && Util.fuzzyEquals(y, other.y)) {
					return true;
				}
			} else {
				if (Util.fuzzyEquals(x, other.x) && Util.fuzzyEquals(y, other.y) && top.equals(other.top) && bottom.equals(other.bottom)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		String preHash;
		if (simple) {
			preHash = String.format("%9.8f", x) + String.format("%9.8f", y);
		} else {
			preHash = String.format("%9.8f", x) + String.format("%9.8f", y) + top.getType().toString() + top.getCircle().number + bottom.getType().toString()
					+ bottom.getCircle().number;
		}
		return preHash.hashCode();
	}

}
