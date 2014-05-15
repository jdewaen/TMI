import java.util.ArrayList;

public class Circle {

	private double x, y, r;
	public int number;

	public Circle(double x, double y, double r) {
		this.x = x;
		this.y = y;
		this.r = r;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getRadius() {
		return r;
	}

	// http://stackoverflow.com/questions/3349125/circle-circle-intersection-points
	public ArrayList<Intersection> intersects(Circle other) {
		if (other == null)
			return new ArrayList<Intersection>();
		double centerDistance = Math.sqrt(Math.pow(x - other.getX(), 2) + Math.pow(y - other.getY(), 2));
		double or = other.getRadius();
		double ox = other.getX();
		double oy = other.getY();
		if (centerDistance > r + or) {
			return new ArrayList<Intersection>();
		}
		if (centerDistance + r < or || centerDistance + or < r) {
			return new ArrayList<Intersection>();
		} else {
			double a = (Math.pow(r, 2) - Math.pow(or, 2) + Math.pow(centerDistance, 2)) / (2 * centerDistance);
			double h = Math.sqrt(Math.pow(r, 2) - Math.pow(a, 2));
			double middleX = x + a * (ox - x) / centerDistance;
			double middleY = y + a * (oy - y) / centerDistance;
			ArrayList<Intersection> solution = new ArrayList<Intersection>();
			double x1, y1, x2, y2;
			x1 = middleX + h * (oy - y) / centerDistance;
			y1 = middleY - h * (ox - x) / centerDistance;
			x2 = middleX - h * (oy - y) / centerDistance;
			y2 = middleY + h * (ox - x) / centerDistance;
			solution.add(new Intersection(x1, y1));
			solution.add(new Intersection(x2, y2));
			return solution;
		}
	}

	public ArrayList<Intersection> intersects(double lineX) {
		if (x - r > lineX || x + r < lineX) {
			return new ArrayList<Intersection>();
		} else if (x - r > lineX) {
			ArrayList<Intersection> result = new ArrayList<Intersection>();
			result.add(new Intersection(x + r, y));
			result.add(new Intersection(x + r, y));
			return result;
		} else if (x + r < lineX) {
			ArrayList<Intersection> result = new ArrayList<Intersection>();
			result.add(new Intersection(x - r, y));
			result.add(new Intersection(x - r, y));
			return result;
		} else {
			double relX = lineX - x;
			double relY = Math.sqrt(Math.pow(r, 2) + Math.pow(relX, 2));
			ArrayList<Intersection> result = new ArrayList<Intersection>();
			result.add(new Intersection(x + relX, y + relY));
			result.add(new Intersection(x + relX, y - relY));
			return result;
		}
	}
}
