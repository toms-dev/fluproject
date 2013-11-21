package simulation.propagation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import simulation.beings.Being;
import simulation.propagation.illness.Illness;



public class ResistancesSet {
	private List<PropagableResistanceBonus> resistances = new ArrayList<PropagableResistanceBonus>();
	
	public ResistancesSet() {
		
	}
	
	public void addResistance(PropagableResistanceBonus resistance) {
		resistances.add(resistance);
	}
	
	public void addAllResistances(Collection<PropagableResistanceBonus> collection) {
		resistances.addAll(collection);
	}
	
	/**
	 * Applies the Resistances Set to a propagation attempt of an illness.
	 * Returns the Resistance that prevented the Illness from propagating, if any. 
	 * Returns null if none of the Resistance could stop propagation.
	 * @param i The illness trying to propagate
	 * @return The protection result
	 */
	public PropagableResistanceBonus applyResistancesToAttemp(Illness i){
		Random random = new Random();
		for(PropagableResistanceBonus res : resistances){
			double bonus =  res.getResistanceBonusFor(i);
			//System.out.println("Bonus of "+res.toString()+" : "+bonus);
			if (bonus >= random.nextDouble()) {
				return res ;
			}
		}
		
		return null ;
	}
	
	public String getProtectionSuccessMessage(Illness i, Being source, Being target) {
		return (new StringBuilder()).append(target.toString()).append(" was protected from ")
				.append(i.toString()).append(" given by ").append(source.toString()).toString();
	}
	
	public  List<PropagableResistanceBonus> getResistance() {
		return this.resistances;
	}
}
