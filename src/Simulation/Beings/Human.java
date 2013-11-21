package Simulation.Beings;

public class Human extends Being {

	public Human(int x, int y) {
		super(Being.HUMAN, x, y);
		canDie = true ;
		symbol = '@';
		name = "Human";
	}	
}
