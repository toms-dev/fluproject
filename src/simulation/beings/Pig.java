package simulation.beings;

/**
 * This class represents a pig (gruik gruik)
 * @author Tom GUILLERMIN
 *
 */
public class Pig extends Animal {
	/**
	 * Pig constructor
	 * @param x The X coordinates of the pig position
	 * @param y The Y coordinates of the pig position
	 */
	public Pig(int x, int y) {
		super(Being.PIG, x, y);
		symbol = 'P';
		name = "Pig";
	}
}
