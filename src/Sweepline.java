import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Sweepline {
	public TreeSet<Edge> edges;
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
		if (edges.size() > 1) {
			return edges.higher(edge);
		} else {
			return null;
		}
	}

	public Edge below(Edge edge) {
		if (edges.size() > 1) {
			return edges.lower(edge);
		} else {
			return null;
		}
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
