package Simulation.Propagation.Illness;
import java.util.ArrayList;
//import java.util.HashMap;
import java.util.List;
import java.util.Random;

import Simulation.Beings.Being;
import Simulation.Beings.PropagationNode;
import Simulation.Propagation.Propagable;
import Simulation.Propagation.PropagableResistanceBonus;
import Simulation.Propagation.PropagationEvent;
import Simulation.Propagation.ResistancesSet;

public class Illness extends Propagable {
	private List<Integer> targetSpecies = new ArrayList<Integer>();
	private String name;
	//private HashMap<Integer, HashMap<Integer, Double>> contaminationRates = new HashMap<Integer, HashMap<Integer, Double>>(); 

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
	
	public double getPropagationRate(Being source, Being target) {
		if (! targetSpecies.contains(target)) return 0;
		return getPropagationRate(source.getType(), target.getType());
	}

	public String toString() {
		return name;
	}

	public String getPropagatedMessage(PropagationNode source,
			PropagationNode target) {
		return new StringBuilder().append(target.toString())
				.append(" was infected by ").append(this.toString())
				.append(" from ").append(source.toString()).toString();
	}
}
