package Simulation.Beings;

public class Human extends LivingEntity {

	public Human(int x, int y) {
		super(LivingEntity.HUMAN, x, y);
		canDie = true ;
		symbol = '@';
		name = "Human";
	}	
}
