import java.util.ArrayList;
import java.util.List;

public class CircleSegment implements Comparable<CircleSegment> {

	public final double x, y, r;
	public final int cid;
	public final CircleHalf type;
	private Sweepline line;

	public CircleSegment(double x, double y, double r, int cid, CircleHalf type, Sweepline line) {
		this.x = x;
		this.y = y;
		this.r = r;
		this.cid = cid;
		this.type = type;
		this.line = line;
	}

	public double getLeft() {
		return x - r;
	}

	public double getRight() {
		return x + r;
	}

	@Override
	public boolean equals(Object obj) {
		if (this.getClass() == obj.getClass()) {
			CircleSegment other = (CircleSegment) obj;
			return (this.cid == other.cid && this.type == other.type);
		}
		return false;
	}

	@Override
	public int compareTo(CircleSegment o) {
		/*
		 * if (cid == o.cid) {
		 * if (type == CircleHalf.TOP) {
		 * return 1;
		 * } else {
		 * return -1;
		 * }
		 * }
		 */
		double od = o.sweepIntersect();
		double td = this.sweepIntersect();
		if (td > od) {
			return -1;
		} else if (td < od) {
			return 1;
		} else {
			return 0;
		}
	}

	public List<Intersection> intersects(CircleSegment o) {
		List<Intersection> result = new ArrayList<Intersection>();
		if (o == null || o.cid == cid)
			return result;
		double centerDistance = Math.sqrt(Math.pow(x - o.x, 2) + Math.pow(y - o.y, 2));
		if (centerDistance > r + o.r)
			return result;
		if (centerDistance + r < o.r || centerDistance + o.r < r)
			return result;
		double a = (Math.pow(r, 2) - Math.pow(o.r, 2) + Math.pow(centerDistance, 2)) / (2 * centerDistance);
		double h = Math.sqrt(Math.pow(r, 2) - Math.pow(a, 2));
		double middleX = x + a * (o.x - x) / centerDistance;
		double middleY = y + a * (o.y - y) / centerDistance;
		double x1, y1, x2, y2;
		x1 = middleX + h * (o.y - y) / centerDistance;
		y1 = middleY - h * (o.x - x) / centerDistance;
		x2 = middleX - h * (o.y - y) / centerDistance;
		y2 = middleY + h * (o.x - x) / centerDistance;
		if (isOnSegment(x1, y1) && o.isOnSegment(x1, y1))
			result.add(new Intersection(x1, y1, this, o));
		if (isOnSegment(x2, y2) && o.isOnSegment(x2, y2))
			result.add(new Intersection(x2, y2, this, o));
		return result;
	}

	public boolean isOnSegment(double x, double y) {
		if (type == CircleHalf.TOP) {
			return y > this.y;
		} else {
			return y < this.y;
		}
	}

	public double sweepIntersect() {
		if (x - r >= line.getX() || x + r <= line.getX() || Util.fuzzyEquals(x - r, line.getX()) || Util.fuzzyEquals(x + r, line.getX())) {
			return y;
		} else {
			double relX = line.getX() - x;
			double relY = Math.sqrt(Math.pow(r, 2) + Math.pow(relX, 2));
			if (type == CircleHalf.TOP) {
				return y + relY;
			} else {
				return y - relY;
			}
		}
	}

}
