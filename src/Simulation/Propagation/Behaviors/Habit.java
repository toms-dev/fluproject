package Simulation.Propagation.Behaviors;

import java.util.Random;

import Simulation.Beings.LivingEntity;
import Simulation.Beings.PropagationNode;
import Simulation.Propagation.PropagableResistanceBonus;
import Simulation.Propagation.PropagationEvent;


public class Habit extends PropagableResistanceBonus {
	protected String name ;
	protected double propagationRate = 0;
	//protected Map<Illness, Integer> specificResistanceBonuses = new HashMap<Illness, Integer>();
	
	public Habit(String name, int species, double globalResistance, double propagationRate){
		this.name = name ;
		setGlobalResistanceBonus(globalResistance);
		
		this.propagationRate = propagationRate;
		
		this.getPropagationMatrix().addPropagationLink(LivingEntity.ANY, LivingEntity.ANY, 0);
		// We consider that an habit can only be passed from a member of the same species.
		this.getPropagationMatrix().addPropagationLink(species, species, propagationRate);
	}
	
	public String getName(){
		return name;
	}

	@Override
	public PropagationEvent tryToPropagateTo(PropagationNode source, PropagationNode target) {
		Random r = new Random();
		
		PropagationEvent event = new PropagationEvent();
		event.setLogged(false);
		
		// Some casting
		LivingEntity sourceEntity = (LivingEntity) source,
				targetEntity = (LivingEntity) target;
		
		if (!targetEntity.hasHabit(this)) {
			if (targetEntity.isStubborn()) {
				event.setLogged(true);
				event.setMessage(getStubornMessage(sourceEntity, targetEntity));
			}
			else if( r.nextInt(10) >= 8) {
				event.setLogged(true);
				event.setMessage(getPropagatedMessage(sourceEntity, targetEntity));
				event.setSuccessful(true);
				targetEntity.addHabit(this);
			}
		}
		return event;
	}

	@Override
	public boolean canPropagateFromTo(PropagationNode source, PropagationNode target) {
		// The only situation in which an habit can propagate is that the source and/or target is dead.
		LivingEntity sourceEntity = (LivingEntity) source,
				targetEntity = (LivingEntity) target ;
		return ! targetEntity.isDead() && ! sourceEntity.isDead();
	}
	
	
	/**
	 * The success message when the habit propagated successfully.
	 * @param source
	 * @param target
	 * @return
	 */
	private String getPropagatedMessage(LivingEntity source, LivingEntity target){
		return new StringBuilder().append(target.toString()).append(" learned  ")
				.append(this.toString())
				.append(" from ").append(source).toString();
	}
	
	/**
	 * The failure message when the habit did not propagate because of the stubbornness of the target.
	 * @param source
	 * @param target
	 * @return
	 */
	private String getStubornMessage(LivingEntity source, LivingEntity target){
		return new StringBuilder().append(target.toString()).append(" was too stuborn to learn ")
				.append(this.toString())
				.append(" from ").append(source).toString();
	}
	
	public String toString() {
		return name;
	}

	public boolean appliesTo(int type) {
		return getPropagationRate(type, type) != 0;
	}
}
