package simulation.beings;

/**
 * This class represents a chicken. COT COT.
 * @author Tom GUILLERMIN
 *
 */
public class Chicken extends Animal {
	
	/**
	 * Chicken constructor
	 * @param x The X coordinates of the chicken position
	 * @param y The Y coordinates of the chicken position
	 */
	public Chicken(int x, int y) {
		super(Being.CHICKEN, x, y);
		symbol = 'C';
		name = "Chicken";
	}

}
