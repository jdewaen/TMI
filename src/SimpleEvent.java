public class SimpleEvent extends Event {

	Circle circle;
	EventType type;

	public SimpleEvent(EventType type, Circle circle) {
		this.circle = circle;
		this.type = type;
	}

	public Circle getCircle() {
		return circle;
	}

	@Override
	public double getY() {
		return circle.getY();
	}

	@Override
	public boolean equals(Object obj) {
		if (this.getClass() == obj.getClass()) {
			SimpleEvent other = (SimpleEvent) obj;
			return (this.circle.equals(other.getCircle()) && this.type == other.type);
		}
		return false;
	}

}
