import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class InteractiveDisplay extends JPanel {
	private static final long serialVersionUID = 1L;
	private BufferedImage image;
	private Circle[] circles;
	public Collection<Event> add = new ArrayList<Event>();
	public Collection<Event> remove = new ArrayList<Event>();
	public Collection<Event> swap = new ArrayList<Event>();
	public Event active;
	public JFrame frame;
	public Sweepline line = new Sweepline();
	public double above = 0;
	public double below = 0;
	private int size;
	private double dotSize = 6;

	public InteractiveDisplay(Circle[] circles, int size) throws Exception {
		this.size = size;
		this.circles = circles;
		image = new BufferedImage((int) (size * 1.25), (int) (size * 1.25),
				BufferedImage.TYPE_INT_RGB);
		setPreferredSize(new Dimension((int) (size * 1.25), (int) (size * 1.25)));
		JFrame frame = new JFrame();
		frame.add(this);
		this.frame = frame;
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		updatePaint();
	}

	public void updatePaint() throws Exception {
		Graphics2D g = image.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, (int) (size * 1.25), (int) (size * 1.25));
		g.setColor(Color.BLACK);
		g.drawRect((int) (size / 8), (int) (size / 8), size, size);
		for (int i = 0; i < circles.length; i++) {
			g.draw(new Ellipse2D.Double(size / 8
					+ (circles[i].getX() - circles[i].getRadius()) * size, size
					/ 8 + (circles[i].getY() - circles[i].getRadius()) * size,
					circles[i].getRadius() * size * 2, circles[i].getRadius()
							* size * 2));
			g.drawString(circles[i].number + "",
					(int) (size / 8 + circles[i].getX() * size),
					(int) (size / 8 + circles[i].getY() * size));
		}
		Iterator<Event> iter = add.iterator();
		while (iter.hasNext()) {
			Event current = iter.next();
			if (current.done) {
				g.setColor(Color.GRAY);
			} else {
				g.setColor(Color.GREEN);
			}
			g.fill(new Ellipse2D.Double(size / 8 + current.getValue() * size
					- dotSize / 2, size / 8 + current.getY() * size - dotSize
					/ 2, dotSize, dotSize));
		}
		iter = swap.iterator();
		while (iter.hasNext()) {
			Event current = iter.next();
			if (current.done) {
				g.setColor(Color.GRAY);
			} else {
				g.setColor(Color.BLUE);
			}
			g.fill(new Ellipse2D.Double(size / 8 + current.getValue() * size
					- dotSize / 2, size / 8 + current.getY() * size - dotSize
					/ 2, dotSize, dotSize));
		}
		iter = remove.iterator();
		while (iter.hasNext()) {
			Event current = iter.next();
			if (current.done) {
				g.setColor(Color.GRAY);
			} else {
				g.setColor(Color.RED);
			}
			g.fill(new Ellipse2D.Double(size / 8 + current.getValue() * size
					- dotSize / 2, size / 8 + current.getY() * size - dotSize
					/ 2, dotSize, dotSize));
		}
		if (active != null) {
			g.setColor(Color.BLACK);
			g.fill(new Ellipse2D.Double(size / 8 + active.getValue() * size
					- dotSize, size / 8 + active.getY() * size - dotSize,
					dotSize * 2, dotSize * 2));
		}
		g.setColor(Color.BLACK);
		g.draw(new Line2D.Double(size / 8 + line.getX() * size, 0, size / 8
				+ line.getX() * size, size * 1.25));
		if (active != null) {
			g.setColor(Color.ORANGE);
			g.fill(new Rectangle2D.Double(size / 8 + line.getX() * size
					- dotSize / 2, size / 8 + above * size - dotSize / 2,
					dotSize, dotSize));
			g.setColor(Color.PINK);
			g.fill(new Rectangle2D.Double(size / 8 + line.getX() * size
					- dotSize / 2, size / 8 + below * size - dotSize / 2,
					dotSize, dotSize));

		}

		g.dispose();
		repaint();

	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		try {
			updatePaint();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		g.drawImage(image, 0, 0, null);

	}

	public void save(String filename) {
		try {
			ImageIO.write(image, "PNG", new File(filename));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
