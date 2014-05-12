import java.awt.Color;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ResultWindow extends JPanel {
	private static final long serialVersionUID = 1L;
	private BufferedImage image;
	private Circle[] circles;
	private ArrayList<Intersection> intersections;
	private int size;
	private int dotSize;

	public ResultWindow(Circle[] circles, ArrayList<Intersection> intersections, int size, int dotSize) throws HeadlessException {
		this.size = size;
		this.circles = circles;
		this.intersections = intersections;
		this.dotSize = dotSize;
		image = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
		JFrame frame = new JFrame("Result: " + circles.length + " circles with " + intersections.size() + " intersections");
		frame.add(this);
		frame.setSize(size, size);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		updatePaint();
	}

	public void updatePaint() {
		Graphics g = image.createGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, size, size);
		g.setColor(Color.BLACK);
		for (int i = 0; i < circles.length; i++) {
			g.drawOval((int) ((circles[i].getX() - circles[i].getRadius()) * size), (int) ((circles[i].getY() - circles[i].getRadius()) * size),
					(int) (circles[i].getRadius() * size * 2), (int) (circles[i].getRadius() * size * 2));
		}
		g.setColor(Color.RED);
		Iterator<Intersection> iter = intersections.iterator();
		while (iter.hasNext()) {
			Intersection current = iter.next();
			g.fillRect((int) (current.getX() * size - dotSize / 2), (int) (current.getY() * size - dotSize / 2), dotSize, dotSize);
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
