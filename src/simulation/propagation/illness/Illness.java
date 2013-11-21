package simulation.propagation.illness;
import java.util.ArrayList;
//import java.util.HashMap;
import java.util.List;
import java.util.Random;

import simulation.beings.Being;
import simulation.beings.PropagationNode;
import simulation.propagation.Propagable;
import simulation.propagation.PropagableResistanceBonus;
import simulation.propagation.PropagationEvent;
import simulation.propagation.ResistancesSet;


public class Illness extends Propagable {
	private List<Integer> targetSpecies = new ArrayList<Integer>();
	private String name;
	//private HashMap<Integer, HashMap<Integer, Double>> contaminationRates = new HashMap<Integer, HashMap<Integer, Double>>(); 

	/**
	 * Illness constructor.
	 * @param name The name of the Illness.
	 */
	public Illness(String name) {
		this.name = name;
	}

	/**
	 * Add a species that the illness can infect, with a given contamination
	 * rate.
	 * 
	 * @param speciesID
	 *            The type of the species.
	 * @param contaminationRate
	 *            The contamination rate.
	 */
	public void addTargetSpecies(int speciesID) {
		targetSpecies.add(speciesID);
	}

	/**
	 * //@deprecated Returns true if the illness can infect an entity.
	 * 
	 * @param entity
	 *            The entity to be infected.
	 * @return True if the illness can infect the entity.
	 */
	@Override
	public boolean canPropagateFromTo(PropagationNode source, PropagationNode target) {
		// Do some casting... (bad)
		Being targetEntity = (Being) target;
		return targetSpecies.contains(targetEntity.getType());
	}

	/**
	 * Tries to infect the entity.
	 * 
	 * @param source
	 *            The source entity.
	 * @param target
	 *            The target entity.
	 * @return True if the entity was infected.
	 */
	@Override
	public PropagationEvent tryToPropagateTo(PropagationNode source,
			PropagationNode target) {
		PropagationEvent event = new PropagationEvent();
		event.setLogged(false);
		
		Being sourceEntity = (Being) source, targetEntity = (Being) target;
				
		if (canPropagateFromTo(sourceEntity, targetEntity)) {
			Random r = new Random();
			double attempt = r.nextDouble();
			double resistanceBase = targetEntity.getResistanceBase();
			
			if (attempt >= resistanceBase) {
				ResistancesSet resistances = new ResistancesSet();
				resistances.addAllResistances(targetEntity.getResistanceBonuses());
				
				PropagableResistanceBonus appliedResistance = resistances.applyResistancesToAttemp(this);
				
				if (appliedResistance != null) {
					event.setSuccessful(false);
					event.setLogged(true);
					event.setMessage("Thanks to " + appliedResistance.toString() + ", " + targetEntity + " was preserved from " + this.toString() + " from "+sourceEntity+" !");
				}
				else {
					event.setSuccessful(true);
					event.setLogged(true);
					event.setMessage(getPropagatedMessage(source, target));
					targetEntity.setSick(this);
				}
			}
		}
		return event;
	}
	
	/**
	 * Returns the propagation rate between a source and a target entity.
	 * @param source The entity propagating.
	 * @param target The entity that might receive the illness.
	 * @return The rate of propagation, between 0 and 1.
	 */
	public double getPropagationRate(Being source, Being target) {
		if (! targetSpecies.contains(target)) return 0;
		return getPropagationRate(source.getType(), target.getType());
	}

	public String toString() {
		return name;
	}

	/**
	 * Returns the relevant string when an entity has propagated the Illness to an other.  
	 * @param source
	 * @param target
	 * @return
	 */
	public String getPropagatedMessage(PropagationNode source,
			PropagationNode target) {
		return new StringBuilder().append(target.toString())
				.append(" was infected by ").append(this.toString())
				.append(" from ").append(source.toString()).toString();
	}
}
