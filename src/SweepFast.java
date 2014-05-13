import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
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
			Event adding = new Event(EventType.ADD, circles[i]);
			Event removing = new Event(EventType.REMOVE, circles[i]);
			events.add(adding);
			events.add(removing);
			display.add.add(adding);
			display.remove.add(removing);
			circles[i].addSweepLine(line);
			circles[i].setNumber(i);
			circles[i].addEdge(new Edge(EdgeType.TOP, circles[i], line));
			circles[i].addEdge(new Edge(EdgeType.BOTTOM, circles[i], line));
		}
		while (!events.isEmpty()) {
			Event currentEvent = events.poll();
			line.setX(currentEvent.getValue());
			display.line = line.getX();
			display.active = currentEvent;
			display.frame.getContentPane().validate();
			display.frame.getContentPane().repaint();

			if (currentEvent.getType() == EventType.ADD) {
				// System.out.println("STARTED ADDING: " + currentEvent.getCircle().number);
				Circle currentCircle = currentEvent.getCircle();
				Edge tempEdge = new Edge(EdgeType.POINT, currentCircle.getY(), line);
				Edge above = line.above(tempEdge);
				Edge below = line.below(tempEdge);
				List<Intersection> inter;
				if (above != null) {
					inter = currentCircle.top.intersects(above);
					Iterator<Intersection> iter = inter.iterator();
					while (iter.hasNext()) {
						Event toAdd = new Event(EventType.SWITCH, iter.next());
						events.add(toAdd);
						display.swap.add(toAdd);
					}
					intersections.addAll(inter);
					inter.clear();
				}
				if (below != null) {
					inter = currentCircle.bottom.intersects(below);
					Iterator<Intersection> iter = inter.iterator();
					while (iter.hasNext()) {
						Event toAdd = new Event(EventType.SWITCH, iter.next());
						events.add(toAdd);
						display.swap.add(toAdd);
					}
					intersections.addAll(inter);
				}
				line.addAllEdges(currentCircle.getEdges());

			} else if (currentEvent.getType() == EventType.REMOVE) {
				// System.out.println("STARTED REMOVING: " + currentEvent.getCircle().number);
				List<Edge> edges = currentEvent.getCircle().getEdges();
				Edge top = currentEvent.circle.top;
				Edge bottom = currentEvent.circle.bottom;
				Edge above = line.above(top);
				Edge below = line.below(bottom);
				List<Intersection> inter;
				if (above != null) {
					inter = top.intersects(above);
					Iterator<Intersection> iter = inter.iterator();
					while (iter.hasNext()) {
						Event toAdd = new Event(EventType.SWITCH, iter.next());
						events.add(toAdd);
						display.swap.add(toAdd);
					}
					intersections.addAll(inter);
					inter.clear();
				}
				if (below != null) {
					inter = bottom.intersects(below);
					Iterator<Intersection> iter = inter.iterator();
					while (iter.hasNext()) {
						Event toAdd = new Event(EventType.SWITCH, iter.next());
						events.add(toAdd);
						display.swap.add(toAdd);
					}
					intersections.addAll(inter);
				}
				line.removeAllEdges(edges);
			} else if (currentEvent.getType() == EventType.SWITCH) {
				// System.out.println("STARTED SWITCHING");
				Edge top = currentEvent.getEdges().get(0);
				Edge bottom = currentEvent.getEdges().get(1);
				Edge above = line.above(top);
				Edge below = line.below(bottom);
				if (above != null)
					intersections.addAll(above.intersects(bottom));
				if (below != null)
					intersections.addAll(below.intersects(top));
				line.removeEdge(top);
				line.removeEdge(bottom);
				line.addEdge(top);
				line.addEdge(bottom);
			}
			currentEvent.done = true;
			Thread.sleep(100);
		}

		time = (int) (System.currentTimeMillis() - startTime);

	}

	private void calculateAndAddIntersections() {

	}

	@Override
	public boolean isImplemented() {
		return true;
	}

}
