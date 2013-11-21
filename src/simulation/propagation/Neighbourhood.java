package simulation.propagation;
import java.awt.Point;

/**
 * An enum of the Neighbourhood possible.
 * It is stored as a list of vectors. 
 * @author Loïc GAILLARD
 *
 */
public enum Neighbourhood {
    EIGHT(new Point[] { 
        new Point(-1, -1), new Point(0, -1), new Point(1, -1),
        new Point(-1, 0),                    new Point(1, 0),
        new Point(-1, 1),  new Point(0, 1),  new Point(1, 1),
    }),
    FOUR(new Point[] { 
                            new Point(0, -1),
        new Point(-1, 0),                     new Point(1, 0),
                            new Point(0, 1)
    });
            
    private Point[] vectors;
    
    private Neighbourhood(Point[] vectors) {
        this.vectors = vectors;
    }
    
    /**
     * Gives the vectors of the right neighbourhood.
     * @return The vectors of the neighbourhood.
     */
    public Point[] getVectors() {
        return vectors;
    }
}

