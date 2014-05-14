public class LineElement implements Comparable<LineElement> {
	public Edge edge;

	public LineElement(Edge edge) {
		this.edge = edge;
		edge.parent = this;
	}

	public int compareTo(LineElement other) {
		return edge.compareTo(other.edge);
	}

	public void swap(LineElement other) {
		Edge temp = edge;
		edge = other.edge;
		other.edge = temp;
	}

	public boolean equals(Object obj) {
		if (this.getClass() == obj.getClass()) {
			LineElement other = (LineElement) obj;
			return edge.equals(other.edge);
		}
		return false;
	}

}
