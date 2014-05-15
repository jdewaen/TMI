public class SweepFast extends Algorithm {
	private Sweepline line = new Sweepline();

	// public InteractiveDisplay display;

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
		if (display != null)
			line.display = display;
		for (int i = 0; i < circles.length; i++) {
			circles[i].number = i;
			CircleSegment top = new CircleSegment(circles[i].getX(), circles[i].getY(), circles[i].getRadius(), i, CircleHalf.TOP, line);
			CircleSegment bottom = new CircleSegment(circles[i].getX(), circles[i].getY(), circles[i].getRadius(), i, CircleHalf.BOTTOM, line);
			Event topa = new SegmentEvent(EventType.ADD, top);
			Event bottoma = new SegmentEvent(EventType.ADD, bottom);
			Event topr = new SegmentEvent(EventType.REMOVE, top);
			Event bottomr = new SegmentEvent(EventType.REMOVE, bottom);
			line.addEvent(topa);
			line.addEvent(bottoma);
			line.addEvent(topr);
			line.addEvent(bottomr);
			if (display != null) {
				display.add.add(topa);
				display.add.add(bottoma);
				display.remove.add(topr);
				display.remove.add(bottomr);
			}
		}
		while (line.hasEvent()) {
			Event event = line.nextEvent();
			if (display != null) {
				display.active = event;
				display.line = line;
				display.frame.getContentPane().validate();
				display.frame.getContentPane().repaint();
				Thread.sleep(200); // ZET BREAKPOINT HIER OM DE VISUALITATIE TE PAUZEREN
			}
			switch (event.type) {
			case ADD:
				line.addSegment(((SegmentEvent) event).segment);
				break;
			case REMOVE:
				line.removeSegment(((SegmentEvent) event).segment);
				break;
			case SWAP:
				SwapEvent sevent = (SwapEvent) event;
				intersections.add(sevent.intersection);
				line.processIntersection(sevent.intersection);
				break;
			}
		}

		time = (int) (System.currentTimeMillis() - startTime);
	}

	@Override
	public boolean isImplemented() {
		return true;
	}

}
