import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

public class CircleIntersector {
	public Algorithm algorithm;
	public int N;
	public static boolean saveImage = false;
	public static final double RADIUS = 0.4;

	public CircleIntersector(Algorithm algorithm, int N, Circle[] circles) {
		this.algorithm = algorithm;
		this.N = N;
		algorithm.setCircles(circles);
		if (saveImage)
			try {
				algorithm.setDisplay(new InteractiveDisplay(circles, 600));
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		//Algorithm test = new SweepSlow(circles);
		try {
			algorithm.solve();
			//test.solve();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Time elapsed: " + algorithm.getTime());
		System.out.println("Intersections: "
				+ algorithm.getAmountofIntersections());
		//System.out.println("Fast: " + algorithm.getAmountofIntersections()
		//		+ " Slow: " + test.getAmountofIntersections());
		writeToOutput("output.txt");
		if (saveImage) {
			ResultWindow result = new ResultWindow(circles,
					algorithm.intersections, 600, 4);
			result.save("output.png");
		}
	}

	public static void main(String[] args) {
		if (args.length >= 3 && args[0].equals("generate")) {
			writeToInput(Integer.parseInt(args[1]), Integer.parseInt(args[2]));
		} else {
			BufferedReader br;
			String filename = "input.txt";
			if (args.length > 0 && !args[0].equals("stat")) {
				filename = args[0];
			} else if (args.length > 1 && args[0].equals("stat")) {
				generateStats(Integer.valueOf(args[1]));
				System.exit(0);
			}
			if (args.length > 1 && args[1].equals("image")) {
				saveImage = true;
			}
			try {
				br = new BufferedReader(new FileReader(filename));
				String line;
				Algorithm algorithm = null;
				int N = 0;
				int linenumber = 1;
				Circle[] circles = new Circle[1];
				while ((line = br.readLine()) != null) {
					switch (linenumber) {
					case 1:
						if (line.equals("1")) {
							algorithm = new BruteForce(circles);
						} else if (line.equals("2")) {
							algorithm = new SweepSlow();
						} else if (line.equals("3")) {
							algorithm = new SweepFast();
						} else {
							System.out
									.println("Invalid algorithm in first line of input.txt. Must be 1, 2 or 3");
							System.exit(0);
						}
						// algorithm = new SweepSlow();
						break;
					case 2:
						N = Integer.parseInt(line);
						circles = new Circle[N];
						break;

					default:
						if (linenumber - 2 <= N) {
							String[] values = line.split(" ");
							circles[linenumber - 3] = new Circle(
									Double.parseDouble(values[0]),
									Double.parseDouble(values[1]),
									Double.parseDouble(values[2]));
						}
						break;
					}

					linenumber++;
				}
				br.close();
				new CircleIntersector(algorithm, N, circles);
			} catch (FileNotFoundException e) {
				System.out.println(filename + " not found");
				System.exit(0);

			} catch (IOException e) {
				e.printStackTrace();
				System.exit(0);
			}
		}

	}

	public void writeToOutput(String name) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(name));
			String newline = System.getProperty("line.separator");
			if (!algorithm.isImplemented()) {
				writer.write("0");
			} else {
				Iterator<Intersection> iter = algorithm.getIntersections()
						.iterator();
				while (iter.hasNext()) {
					Intersection current = iter.next();
					writer.write(current.getX() + " " + current.getY()
							+ newline);
				}
				writer.write(newline);
				writer.write(String.valueOf(algorithm.getTime()));
				writer.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void writeToInput(int amount, int algorithm) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(
					"input.txt"));
			String newline = System.getProperty("line.separator");
			writer.write(algorithm + newline);
			writer.write(String.valueOf(amount));
			for (int i = 0; i < amount; i++) {
				writer.write(newline);
				writer.write(Math.random() + " " + Math.random() + " "
						+ (Math.random() * RADIUS));
			}
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void generateStats(int maxDepth) {
		Circle[] circles;
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(
					"stat.txt"));
			// int maxAmount = 1 * (int) Math.pow(2, maxDepth);
			int maxAmount = 100 * maxDepth;
			circles = new Circle[maxAmount];
			for (int i = 0; i < maxAmount; i++) {
				circles[i] = new Circle(Math.random(), Math.random(),
						Math.random() * RADIUS);
			}
			for (int i = 1; i <= maxDepth; i++) {
				int amount = 100 * i;
				Circle[] subcircles = new Circle[amount];
				System.arraycopy(circles, 0, subcircles, 0, amount);
				Algorithm one = new BruteForce(subcircles);
				Algorithm two = new SweepSlow(subcircles);
				Algorithm three = new SweepFast(subcircles);
				try {
					one.solve();
					two.solve();
					// three.solve();
				} catch (Exception e) {
					e.printStackTrace();
				}

				if (one.getAmountofIntersections() != two
						.getAmountofIntersections()) {
					System.out.println(String.valueOf(amount) + " FOUT : "
							+ one.getAmountofIntersections() + " "
							+ two.getAmountofIntersections() + " "
							+ three.getAmountofIntersections());
				}

				String newline = System.getProperty("line.separator");
				System.out.println((String.valueOf(amount) + " "
						+ String.valueOf(one.getTime()) + " "
						+ String.valueOf(two.getTime()) + " " + String
						.valueOf(three.getTime())));
				writer.write(String.valueOf(String.valueOf(amount) + " "
						+ String.valueOf(one.getTime()) + " "
						+ String.valueOf(two.getTime()) + " "
						+ String.valueOf(three.getTime()) + newline));

			}
			System.out.println("done");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}