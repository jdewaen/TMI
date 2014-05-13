public class SweepElement {

	private Edge edge;
	private SweepElement left;
	private SweepElement right;
	public boolean skip = false;

	public SweepElement(Edge edge) {
		this.edge = edge;
	}

	public void insert(SweepElement current, double lineX) {
		if (current.getY(lineX) < getY(lineX)) {
			if (left != null) {
				left.insert(current, lineX);
			} else {
				left = current;
			}
		} else {
			if (right != null) {
				right.insert(current, lineX);
			} else {
				right = current;
			}
		}
	}

	public void remove(Circle removeCircle) {
		if (getCircle() == removeCircle) {
			this.skip = true;
		} else {
			left.remove(removeCircle);
			right.remove(removeCircle);
		}
	}

	public SweepElement getLeft() {
		if(left.skip){
			return left.getRight();
		}
	}

	public SweepElement getRight() {
		return right;
	}

	public Edge getEdge() {
		return edge;
	}

	public Circle getCircle() {
		return edge.getCircle();
	}

	public double getY(double lineX) {
		return edge.getY(lineX);
	}

}
