package Simulation.Propagation.Illness;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import Simulation.Beings.LivingEntity;
import Simulation.Beings.PropagationNode;
import Simulation.Propagation.PropagableResistanceBonus;
import Simulation.Propagation.PropagationEvent;


public class Vaccine extends PropagableResistanceBonus {
	protected String name ;
	protected List<Integer> species = new ArrayList<Integer>();
	//protected List<Illness> protections = new ArrayList<Illness>();
	
	public Vaccine(String name){
		this.name = name;
	}
	
	/**
	 * Add a protection against some illness to this vaccine.
	 * @param i The Illness
	 */
	public void addProtectionAgainst(Illness i){
		//protections.add(i);
		addResistanceBonus(i, 1);
	}
	
	/**
	 * Add a protection against some illnesses to this vaccine.
	 * @param l Illnesses collection.
	 */
	public void addProtectionAgainst(Collection<Illness> l){
		//protections.addAll(l);
		for (Illness i : l) {
			addResistanceBonus(i, 1);
		}
	}
	
	/**
	 * Returns the name of the vaccine.
	 * @return
	 */
	public String getName(){
		return name;
	}

	/**
	 * Returns true if the vaccine protects against a provided illness.
	 * @param i The illness.
	 * @return
	 */
	public boolean protectsAgainst(Illness i) {
		return getResistanceBonusFor(i) > 0 ;
		//return protections.contains(i);
	}
	
	/**
	 * Adds a species in the list of the compatible species.
	 * @param type
	 */
	public void addSpecies(Integer type) {
		species.add(type);
	}
	
	public String toString(){
		return name;
	}
	
	public String getVaccinatedMessage(LivingEntity source, LivingEntity target){	
		return (new StringBuilder()).append(target.toString()).append(" was vaccinated with ").append(this.toString()).append(" by ").append(source.toString()).toString();
	}
	
	public String getFailedVaccineMessage(LivingEntity source, LivingEntity target){	
		return (new StringBuilder()).append("Because of its stubborness, ").append(target.toString()).append(" was not vaccinated with ").append(this.toString()).append(" by ").append(source.toString()).toString();
	}

	@Override
	public PropagationEvent tryToPropagateTo(PropagationNode source,
			PropagationNode target) {
		PropagationEvent event = new PropagationEvent();
		event.setLogged(false);
		
		LivingEntity sourceEntity = (LivingEntity) source,
				targetEntity = (LivingEntity) target;
		if(species.contains(targetEntity)) {
			Random r = new Random();
			double attempt = r.nextDouble(),
					threshold = 0.3 ;
			// Stuborn people doesn't want vaccine !
			if(targetEntity.isStubborn()) {
				threshold -= 0.1 ;
			}
			
			// Success
			if (attempt >= threshold) {
				event.setSuccessful(true);
				event.setLogged(true);
				event.setMessage(getVaccinatedMessage(sourceEntity, targetEntity));
			}
			else {
				// The vaccination failed because of the target's stubbornness
				if (targetEntity.isStubborn() &&  attempt+0.1 >= threshold) {
					event.setLogged(true);
					event.setMessage(getFailedVaccineMessage(sourceEntity, targetEntity));
				}
			}
		}
		return event;
	}

	@Override
	public boolean canPropagateFromTo(PropagationNode source,
			PropagationNode target) {
		LivingEntity sourceEntity = (LivingEntity) source,
				targetEntity = (LivingEntity) target;
		return species.contains(targetEntity);
	}
}
