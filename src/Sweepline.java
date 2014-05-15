import java.util.List;
import java.util.PriorityQueue;

public class Sweepline {
	private PriorityQueue<Event> events = new PriorityQueue<Event>();
	public BST<CircleSegment, CircleSegment> segments = new BST<CircleSegment, CircleSegment>();
	private double x = Double.NEGATIVE_INFINITY;
	public InteractiveDisplay display;

	public Sweepline() {

	}

	public void addEvent(Event event) {
		if (!events.contains(event) && event.x > x) {
			events.add(event);
			if (display != null && event.type == EventType.SWAP)
				display.swap.add(event);
		}
	}

	public void addSegment(CircleSegment segment) {
		segments.put(segment, segment);
		checkIntersections(segment);
	}

	public void removeSegment(CircleSegment segment) {
		CircleSegment above = getAbove(segment);
		CircleSegment below = getBelow(segment);
		segments.delete(segment);
		checkIntersections(above);
		checkIntersections(below);
	}

	public Event nextEvent() {
		Event next = events.poll();
		x = next.getValue();
		next.done = true;
		return next;
	}

	public void processIntersection(Intersection intersection) {
		CircleSegment segment1 = intersection.top;
		CircleSegment segment2 = intersection.bottom;
		segments.delete(segment1);
		segments.delete(segment2);
		addSegment(segment1);
		addSegment(segment2);
	}

	private void processIntersections(List<Intersection> list) {
		if (list == null)
			return;
		for (Intersection intersection : list) {
			Event swap = new SwapEvent(intersection);
			addEvent(swap);
		}
	}

	public void checkIntersections(CircleSegment segment) {
		if (segment == null)
			return;
		processIntersections(segment.intersects(getBelow(segment)));
		processIntersections(segment.intersects(getAbove(segment)));
	}

	public CircleSegment getAbove(CircleSegment segment) {
		int rank = segments.rank(segment);
		if (rank + 1 < segments.size()) {
			return segments.select(rank + 1);
		} else {
			return null;
		}
	}

	public CircleSegment getBelow(CircleSegment segment) {
		int rank = segments.rank(segment);
		if (rank > 0) {
			return segments.select(rank - 1);
		} else {
			return null;
		}
	}

	public boolean hasEvent() {
		return !events.isEmpty();
	}

	public double getX() {
		return x;
	}

}
