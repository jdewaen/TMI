import java.util.ArrayList;

public class Circle {

	private double x, y, r;

	public Circle(double x, double y, double r) {
		this.x = x;
		this.y = y;
		this.r = r;
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
		double centerDistance = Math.sqrt(Math.pow(x - other.getX(), 2) + Math.pow(y - other.getY(), 2));
		double or = other.getRadius();
		double ox = other.getX();
		double oy = other.getY();
		if (centerDistance > r + or) {
			return null;
		}
		if (centerDistance + r < or || centerDistance + or < r) {
			return null;
		}else{
			double a = (Math.pow(r,2) - Math.pow(or,2) + Math.pow(centerDistance, 2))/(2*centerDistance);
			double h = Math.sqrt(Math.pow(r, 2) - Math.pow(a, 2));
			double middleX = x + a*(ox -x)/centerDistance;
			double middleY = y + a*(oy -y)/centerDistance;
			ArrayList<Intersection> solution = new ArrayList<Intersection>();
			solution.add(new Intersection(middleX + h*(oy - y)/centerDistance, middleY - h*(ox - x)/centerDistance));
			solution.add(new Intersection(middleX - h*(oy - y)/centerDistance, middleY + h*(ox - x)/centerDistance));
			return solution;
		}
	}
}
