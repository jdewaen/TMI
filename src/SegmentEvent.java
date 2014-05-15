public class SegmentEvent extends Event {

	public final CircleSegment segment;

	public SegmentEvent(EventType type, CircleSegment segment) throws Exception {
		if (type != EventType.ADD && type != EventType.REMOVE)
			throw new Exception("Wrong event type");
		this.segment = segment;
		this.type = type;
		if (type == EventType.ADD) {
			x = segment.getLeft();
		} else {
			x = segment.getRight();
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Event) {
			Event other = (Event) obj;
			if (this.getType() == other.getType()) {
				return this.segment.equals(((SegmentEvent) other).segment);
			}
		}
		return false;
	}

	@Override
	public double getY() {
		return segment.y;
	}

}
