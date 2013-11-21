package simulation.beings;

/**
 * This class represent a Human.
 * @author Tom GUILLERMIN
 *
 */
public class Human extends Being {

	/**
	 * Human constructor
	 * @param x The X coordinates of the human position
	 * @param y The Y coordinates of the human position
	 */
	public Human(int x, int y) {
		super(Being.HUMAN, x, y);
		canDie = true ;
		symbol = '@';
		name = "Human";
	}	
}
