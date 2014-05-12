public class Event implements Comparable<Event> {

	EventType type;
	Circle circle;

	public Event(EventType type, Circle circle) {
		this.type = type;
		this.circle = circle;
	}

	public Circle getCircle() {
		return circle;
	}

	public double getValue() {
		if (type == EventType.ADD) {
			return circle.getX() - circle.getRadius();
		} else {
			return circle.getX() + circle.getRadius();
		}
	}

	public EventType getType() {
		return type;
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
