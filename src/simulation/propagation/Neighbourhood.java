package simulation.propagation;
import java.awt.Point;

/**
 * This class represents a neighborhood.
 * It is stored as a list of vectors. 
 * @author Tom GUILLERMIN
 *
 */
public class Neighbourhood {
	
	public static Point[] EIGHT = { 
	        new Point(-1, -1), new Point(0, -1), new Point(1, -1),
			new Point(-1, 0),                    new Point(1, 0),
			new Point(-1, 1),  new Point(0, 1),  new Point(1, 1),
	        };

	public static Point[] FOUR = { 
	                            new Point(0, -1),
			new Point(-1, 0),                     new Point(1, 0),
			                    new Point(0, 1)
	        };
}
