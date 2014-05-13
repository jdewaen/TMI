import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.TreeSet;

public class SweepFast extends Algorithm {

	public SweepFast() {
		// TODO Auto-generated constructor stub
	}

	public SweepFast(Circle[] circles) {
		super(circles);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void solve() throws Exception {
		long startTime = System.currentTimeMillis();
		PriorityQueue<Event> events = new PriorityQueue<Event>();
		Sweepline line = new Sweepline();
		for (int i = 0; i < circles.length; i++) {
			events.add(new Event(EventType.ADD, circles[i]));
			events.add(new Event(EventType.REMOVE, circles[i]));
			circles[i].addSweepLine(line);
			circles[i].setNumber(i);
			circles[i].addEdge(new Edge(EdgeType.TOP, circles[i], line));
			circles[i].addEdge(new Edge(EdgeType.BOTTOM, circles[i], line));
		}
		List<Edge> addNext = new ArrayList<Edge>();
		while (!events.isEmpty()) {
			Event currentEvent = events.poll();
			line.setX(currentEvent.getValue());
			line.addAllEdges(addNext);
			addNext.clear();
			if (currentEvent.getType() == EventType.ADD) {
				System.out.println("STARTED ADDING: " + currentEvent.getCircle().number);
				Circle currentCircle = currentEvent.getCircle();
				Edge tempEdge = new Edge(EdgeType.POINT, currentCircle.getY(), line);
				line.addEdge(tempEdge);
				Edge above = line.above(tempEdge);
				Edge below = line.below(tempEdge);
				line.removeEdge(tempEdge);
				List<Intersection> inter;
				if (above != null) {
					inter = currentCircle.top.intersects(above);
					Iterator<Intersection> iter = inter.iterator();
					while (iter.hasNext()) {
						events.add(new Event(EventType.SWITCH, iter.next()));
					}
					intersections.addAll(inter);
					inter.clear();
				}
				if (below != null) {
					inter = currentCircle.bottom.intersects(below);
					Iterator<Intersection> iter = inter.iterator();
					while (iter.hasNext()) {
						events.add(new Event(EventType.SWITCH, iter.next()));
					}
					intersections.addAll(inter);
				}
				line.addAllEdges(currentCircle.getEdges());

			} else if (currentEvent.getType() == EventType.REMOVE) {
				System.out.println("STARTED REMOVING: " + currentEvent.getCircle().number);
				List<Edge> edges = currentEvent.getCircle().getEdges();
				Edge top = null;
				Edge bottom = null;
				Iterator<Edge> iter = edges.iterator();
				while (iter.hasNext()) {
					Edge current = iter.next();
					if (current.getType() == EdgeType.TOP) {
						top = current;
					} else {
						bottom = current;
					}
				}
				List<Intersection> inter = top.getCircle().intersects(bottom.getCircle());
				Iterator<Intersection> iter2 = inter.iterator();
				while (iter.hasNext()) {
					events.add(new Event(EventType.SWITCH, iter2.next()));
				}
				intersections.addAll(inter);
				line.removeAllEdges(edges);
			} else if (currentEvent.getType() == EventType.SWITCH) {
				System.out.println("STARTED SWITCHING");
				addNext.addAll(currentEvent.getEdges());
				Edge top = currentEvent.getEdges().get(0);
				Edge bottom = currentEvent.getEdges().get(1);
				Edge above = line.above(top);
				Edge below = line.below(bottom);
				if (above != null)
					intersections.addAll(above.intersects(bottom));
				if (below != null)
					intersections.addAll(below.intersects(top));
				line.removeAllEdges(addNext);
			}
		}

		time = (int) (System.currentTimeMillis() - startTime);

	}

	@Override
	public boolean isImplemented() {
		return true;
	}

}
