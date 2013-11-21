package simulation.propagation;
import java.awt.Point;

/**
 * This class represents a neighborhood.
 * It is stored as a list of vectors. 
 * @author Loïc GAILLARD
 *
 */
public class Neighbourhood {
	private Point[] vectors;
	
	private final static Point[] EIGHT = { 
	        new Point(-1, -1), new Point(0, -1), new Point(1, -1),
			new Point(-1, 0),                    new Point(1, 0),
			new Point(-1, 1),  new Point(0, 1),  new Point(1, 1),
	        };

	public final static Point[] FOUR = { 
	                            new Point(0, -1),
			new Point(-1, 0),                     new Point(1, 0),
			                    new Point(0, 1)
	        };
	
	public Neighbourhood(String type) {
	    if("eight".equals(type)) {
	        vectors = EIGHT;
	    } else if("four".equals(type)) {
	        vectors = FOUR;
	    }
	}
	
	public Point[] getVectors() {
	    return vectors;
	}
}
