import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ResultWindow extends JPanel {
	private static final long serialVersionUID = 1L;
	private BufferedImage image;
	private Circle[] circles;
	private Collection<Intersection> intersections;
	private int size;
	private double dotSize;

	public ResultWindow(Circle[] circles, Collection<Intersection> intersections, int size, double dotSize) throws HeadlessException {
		this.size = size;
		this.circles = circles;
		this.intersections = intersections;
		this.dotSize = dotSize;
		image = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
		setPreferredSize(new Dimension(size,size));
		JFrame frame = new JFrame("Result: " + circles.length + " circles with " + intersections.size() + " intersections");
		frame.add(this);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		updatePaint();
	}

	public void updatePaint() {
		Graphics2D g = image.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, size, size);
		g.setColor(Color.BLACK);
		for (int i = 0; i < circles.length; i++) {
			g.draw(new Ellipse2D.Double((circles[i].getX() - circles[i].getRadius()) * size, (circles[i].getY() - circles[i].getRadius()) * size, circles[i]
					.getRadius() * size * 2, circles[i].getRadius() * size * 2));
		}
		g.setColor(Color.RED);
		Iterator<Intersection> iter = intersections.iterator();
		while (iter.hasNext()) {
			Intersection current = iter.next();
			g.fill(new Ellipse2D.Double(current.getX() * size - dotSize / 2, current.getY() * size - dotSize / 2, dotSize, dotSize));
		}
		g.dispose();
		repaint();

	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
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
