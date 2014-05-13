import java.util.ArrayList;
import java.util.List;

public class Intersection {

	private double x, y;
	public Edge top, bottom;

	public Intersection(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Intersection(double x, double y, Edge top, Edge bottom) {
		this.x = x;
		this.y = y;
		this.top = top;
		this.bottom = bottom;
	}
	public List<Edge> getEdges(){
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
	public boolean contains(Edge edge){
		return (top == edge || bottom == edge);
	}

}
