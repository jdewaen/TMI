public class Intersection {

	public final double x, y;
	public final CircleSegment top, bottom;

	public Intersection(double x, double y) {
		this.x = x;
		this.y = y;
		top = null;
		bottom = null;
	}

	public Intersection(double x, double y, CircleSegment top, CircleSegment bottom) {
		this.x = x;
		this.y = y;
		this.top = top;
		this.bottom = bottom;
	}

	@Override
	public boolean equals(Object obj) {
		if (this.getClass() == obj.getClass()) {
			Intersection other = (Intersection) obj;
			if (Util.fuzzyEquals(x, other.x) && Util.fuzzyEquals(y, other.y) && top.equals(other.top) && bottom.equals(other.bottom)) {
				return true;
			}
		}
		return false;
	}
}
