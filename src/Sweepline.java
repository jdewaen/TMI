import java.util.Collection;
import java.util.TreeSet;

public class Sweepline {
	private TreeSet<Edge> edges;
	private double x;

	public Sweepline() {
		edges = new TreeSet<Edge>();
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getX() {
		return x;
	}

	public Edge above(Edge edge) {
		return edges.higher(edge);
	}

	public Edge below(Edge edge) {
		return edges.lower(edge);
	}

	public void addEdge(Edge edge) {
		edges.add(edge);
	}

	public void addAllEdges(Collection<Edge> newedges) {
		edges.addAll(newedges);
	}
	
	public void removeEdge(Edge edge) {
		edges.remove(edge);
	}
	
	public void removeAllEdges(Collection<Edge> newedges) {
		edges.removeAll(newedges);
	}

}
