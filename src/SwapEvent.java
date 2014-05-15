public class SwapEvent extends Event {

	public final Intersection intersection;

	public SwapEvent(Intersection intersection) {
		this.intersection = intersection;
		this.x = intersection.x;
		this.type = EventType.SWAP;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Event) {
			Event other = (Event) obj;
			if (this.getType() == other.getType()) {
				return this.intersection.equals(((SwapEvent) other).intersection);
			}
		}
		return false;
	}

	@Override
	public double getY() {
		return intersection.y;
	}

}
