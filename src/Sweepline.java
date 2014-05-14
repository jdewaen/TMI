import java.util.TreeSet;

public class Sweepline {
	public TreeSet<LineElement> edges;
	private double x;

	public Sweepline() {
		edges = new TreeSet<LineElement>();
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getX() {
		return x;
	}

	public Edge above(Edge edge) {
		if (edges.size() > 1) {
			LineElement result = edges.higher(edge.parent);
			if(result != null){
				return result.edge;
			}else{
				return null;
			}
		} else {
			return null;
		}
	}

	public Edge below(Edge edge) {
		if (edges.size() > 1) {
			LineElement result = edges.lower(edge.parent);
			if(result != null){
				return result.edge;
			}else{
				return null;
			}
		} else {
			return null;
		}
	}

	public void addEdge(Edge edge) {
		edges.add(new LineElement(edge));
	}

	//public void addAllEdges(Collection<Edge> newedges) {
		
		//edges.addAll(newedges);
	//}

	public void removeEdge(Edge edge) {
		edges.remove(edge.parent);
	}

	//public void removeAllEdges(Collection<Edge> newedges) {
		//edges.removeAll(newedges);
	//}

}
