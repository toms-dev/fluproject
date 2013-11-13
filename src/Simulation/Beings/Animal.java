package Simulation.Beings;

public class Animal extends LivingEntity {
	
	public Animal(int type, int x, int y) {
		super(type, x, y);
		canDie = false;
	}
	
}
