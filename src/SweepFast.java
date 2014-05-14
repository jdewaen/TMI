import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.TreeSet;

public class SweepFast extends Algorithm {
	private PriorityQueue<Event> events = new PriorityQueue<Event>();

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
		Sweepline line = new Sweepline();
		for (int i = 0; i < circles.length; i++) {
			Event adding = new Event(EventType.ADD, circles[i]);
			Event removing = new Event(EventType.REMOVE, circles[i]);
			events.add(adding);
			events.add(removing);
			if (showDisplay) {
				display.add.add(adding);
				display.remove.add(removing);
			}
			circles[i].addSweepLine(line);
			circles[i].setNumber(i);
			circles[i].addEdge(new Edge(EdgeType.TOP, circles[i], line));
			circles[i].addEdge(new Edge(EdgeType.BOTTOM, circles[i], line));
		}
		while (!events.isEmpty()) {
			Event currentEvent = events.poll();
			line.setX(currentEvent.getValue());

			if (currentEvent.getType() == EventType.ADD) {
				// System.out.println("STARTED ADDING: " +
				// currentEvent.getCircle().number);
				Circle currentCircle = currentEvent.getCircle();
				LineElement tempEdge = new LineElement(new Edge(EdgeType.POINT,
						currentCircle.getY(), line));
				Edge above = line.above(tempEdge.edge);
				Edge below = line.below(tempEdge.edge);
				setNeighbors(above, below, line.getX());
				if (above != null) {
					addAllSwitchEvents(currentCircle.top.intersects(above));
				}
				if (below != null) {
					addAllSwitchEvents(currentCircle.bottom.intersects(below));

				}
				line.addEdge(currentCircle.top);
				line.addEdge(currentCircle.bottom);

			} else if (currentEvent.getType() == EventType.REMOVE) {
				// System.out.println("STARTED REMOVING: " +
				// currentEvent.getCircle().number);
				Edge top = currentEvent.circle.top;
				Edge bottom = currentEvent.circle.bottom;
				Edge above = line.above(top);
				Edge below = line.below(bottom);
				setNeighbors(above, below, line.getX());
				if (above != null) {
					addAllSwitchEvents(top.intersects(above));
				}
				if (below != null) {
					addAllSwitchEvents(bottom.intersects(below));
				}
				line.removeEdge(top);
				line.removeEdge(bottom);
			} else if (currentEvent.getType() == EventType.SWITCH) {
				// System.out.println("STARTED SWITCHING");
				Edge top = currentEvent.getEdges().get(0);
				Edge bottom = currentEvent.getEdges().get(1);
				// if (line.above(bottom) != top || line.below(top) != bottom)
				// System.out.println("TOP NOT ABOVE BOTTOM");
				Edge above = line.above(top);
				Edge below = line.below(bottom);
				setNeighbors(above, below, line.getX());
				if (above != null)
					addAllSwitchEvents(above.intersects(bottom));
				if (below != null)
					addAllSwitchEvents(below.intersects(top));
				top.parent.swap(bottom.parent);
			}
			currentEvent.done = true;
			if (showDisplay) {
				display.line = line;
				display.active = currentEvent;
				display.frame.getContentPane().validate();
				display.frame.getContentPane().repaint();
			}
			if (showDisplay)
				Thread.sleep(200);
		}

		time = (int) (System.currentTimeMillis() - startTime);

	}

	private void addSwitchEvent(Intersection inter) {
		Event toAdd = new Event(EventType.SWITCH, inter);
		if (!events.contains(toAdd)) {
			events.add(toAdd);
			if (showDisplay)
				display.swap.add(toAdd);
		}
	}

	private void setNeighbors(Edge above, Edge below, double lineX) {
		if (above != null)
			display.above = above.getY(lineX);
		if (below != null)
			display.below = below.getY(lineX);
	}

	private void addAllSwitchEvents(Collection<Intersection> inter) {
		Iterator<Intersection> iter = inter.iterator();
		while (iter.hasNext()) {
			addSwitchEvent(iter.next());
		}
		intersections.addAll(inter);
	}

	@Override
	public boolean isImplemented() {
		return true;
	}

}
