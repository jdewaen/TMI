public abstract class Event implements Comparable<Event> {

	protected EventType type;
	protected double x;
	public boolean done = false;

	public double getValue() {
		return x;
	}

	public EventType getType() {
		return type;
	}
	public abstract double getY();

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

	@Override
	public abstract boolean equals(Object obj);
}