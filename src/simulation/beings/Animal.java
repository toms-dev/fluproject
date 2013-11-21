package simulation.beings;

/**
 * This abstract class represents an animal.
 * It can die. 
 * @author Tom GUILLERMIN
 */
public abstract class Animal extends Being {
	
	/**
	 * Constructor of Animal
	 * @param type The type of Being that it is.
	 * @param x		The X coordinate of the animal position
	 * @param y		The Y coordinate of the animal position
	 */
	public Animal(int type, int x, int y) {
		super(type, x, y);
		canDie = false;
	}
	
}
