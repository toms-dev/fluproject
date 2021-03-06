package simulation.propagation;

import java.util.HashMap;
import java.util.Map;

import simulation.propagation.illness.Illness;


/**
 * This class represents a bonus of protection against a given Propagable.
 * @author Tom GUILLERMIN
 *
 */
public abstract class PropagableResistanceBonus extends Propagable {

	private Map<Illness, Double> resistanceBonuses = new HashMap<Illness, Double>();
	private double globalResistanceBonus = 0;
	
	/**
	 * Sets the global resistance bonus.
	 * @param d The value of the resistance
	 */
	public void setGlobalResistanceBonus(double d){
		globalResistanceBonus = d ;
	}
	
	/**
	 * Returns the global resistance bonus.
	 * @return The global resistance bonus.
	 */
	public double getGlobalResistanceBonus() {
		return globalResistanceBonus;
	}
	
	/**
	 * Adds a resistance bonus for a given Illness.
	 * @param i The illness
	 * @param bonus The value of the bonus (between 0.0 and 1.1)
	 */
	public void addResistanceBonus(Illness i, double bonus){
		resistanceBonuses.put(i, bonus);
	}
	
	/**
	 * Returns the value of the resistance bonus against the provided Illness.
	 * @param i The illness.
	 * @return The value of the resistance bonus against the provided Illness.
	 */
	public double getResistanceBonusFor(Illness i) {
		// Default value
		if( !resistanceBonuses.containsKey(i)) return globalResistanceBonus ;
		// Specific value
		return resistanceBonuses.get(i);
	}

}
