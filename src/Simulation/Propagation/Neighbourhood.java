package Simulation.Propagation;
import java.awt.Point;

public class Neighbourhood {
	public static Point[] EIGHT = { new Point(-1, 1),
			new Point(0, 1), new Point(1, 1),
			new Point(-1, 0), // new Point(0, 0),
			new Point(1, 0), new Point(-1, -1), new Point(0, -1),
			new Point(1, -1), };

	public static Point[] FOUR = { new Point(0, 1),
			new Point(-1, 0), new Point(1, 0), new Point(0, -1), };
}
