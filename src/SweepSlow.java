import java.util.ArrayList;
import java.util.Iterator;
import java.util.PriorityQueue;

public class SweepSlow extends Algorithm {

	public SweepSlow() {
		// TODO Auto-generated constructor stub
	}

	public SweepSlow(Circle[] circles) {
		super(circles);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void solve() {
		long startTime = System.currentTimeMillis();
		PriorityQueue<SimpleEvent> events = new PriorityQueue<SimpleEvent>();
		ArrayList<Circle> active = new ArrayList<Circle>();
		for (int i = 0; i < circles.length; i++) {
			events.add(new SimpleEvent(EventType.ADD, circles[i]));
			events.add(new SimpleEvent(EventType.REMOVE, circles[i]));
		}
		while (!events.isEmpty()) {
			SimpleEvent currentEvent = events.poll();
			if (currentEvent.type == EventType.ADD) {
				Iterator<Circle> iter = active.iterator();
				while (iter.hasNext()) {
					ArrayList<Intersection> result;
					result = currentEvent.getCircle().intersects(iter.next());
					if (result != null)
						intersections.addAll(result);
				}
				active.add(currentEvent.getCircle());
			} else {
				active.remove(currentEvent.getCircle());
			}
		}

		time = (int) (System.currentTimeMillis() - startTime);

	}

	@Override
	public boolean isImplemented() {
		return true;
	}

}
