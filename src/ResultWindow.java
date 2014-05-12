import java.awt.Color;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JFrame;

public class ResultWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	private Circle[] circles;
	private ArrayList<Intersection> intersections;
	private int size;
	private int dotSize;

	public ResultWindow(Circle[] circles, ArrayList<Intersection> intersections, int size, int dotSize) throws HeadlessException {
		this.size = size;
		this.circles = circles;
		this.intersections = intersections;
		this.dotSize = dotSize;
		setSize(size, size);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public void paint(Graphics g) {
		for (int i = 0; i < circles.length; i++) {
			g.drawOval(
					(int) ((circles[i].getX() - circles[i].getRadius()) * size),
					(int) ((circles[i].getY() - circles[i].getRadius()) * size),
					(int) (circles[i].getRadius() * size * 2),
					(int) (circles[i].getRadius() * size * 2));
		}
		g.setColor(Color.RED);
		Iterator<Intersection> iter = intersections.iterator();
		while(iter.hasNext()){
			Intersection current = iter.next();
			g.fillRect((int)(current.getX()*size - dotSize/2),(int) (current.getY()*size - dotSize/2), dotSize, dotSize);
		}
	}
}
