import java.util.List;

public class Event implements Comparable<Event> {

	EventType type;
	Circle circle;
	public final Intersection intersection;
	boolean done = false;
	double x;

	public Event(EventType type, Intersection intersection) {
		this.type = type;
		this.intersection = intersection;
		this.x = intersection.getX();
	}

	public Event(EventType type, Circle circle) {
		this.type = type;
		this.circle = circle;
		if (type == EventType.ADD) {
			x = circle.getX() - circle.getRadius();
		} else if (type == EventType.REMOVE) {
			x = circle.getX() + circle.getRadius();
		}
		intersection = null;
	}

	public Circle getCircle() {
		return circle;
	}

	public List<Edge> getEdges() {
		if (type == EventType.SWITCH) {
			return intersection.getEdges();
		} else {
			return circle.getEdges();
		}
	}

	public double getY() {
		if (type == EventType.ADD || type == EventType.REMOVE) {
			return circle.getY();
		} else {
			return intersection.getY();
		}
	}

	public double getValue() {
		return x;
	}

	public EventType getType() {
		return type;
	}

	@Override
	public boolean equals(Object obj) {
		if (this.getClass() == obj.getClass()) {
			Event other = (Event) obj;
			if (this.getType() == other.getType() && this.getType() == EventType.ADD || this.getType() == EventType.REMOVE) {
				if (this.getCircle().equals(other.getCircle()))
					return true;
			} else if (this.getType() == EventType.SWITCH && other.getType() == EventType.SWITCH) {
				return (this.intersection.equals(other.intersection));
			}

		}
		return false;
	}

	@Override
	public int compareTo(Event other) {
		if (other.getValue() > getValue()) {
			return -1;
		} else if (other.getValue() < getValue()) {
			return 1;
		} else {
			return 0;
		}
	}

}
