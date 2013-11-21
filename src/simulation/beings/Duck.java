package simulation.beings;

/**
 * This class represents a Duck. There's no much more to say. Maybe just "KWAK !".
 * @author Tom GUILLERMIN.
 *
 */
public class Duck extends Animal {
	
	/**
	 * Duck constructor
	 * @param x The X coordinates of the duck position
	 * @param y The Y coordinates of the duck position
	 */
	public Duck(int x, int y) {
		super(Being.DUCK, x, y);
		symbol = 'D';
		name = "Duck";
	}
	
}
