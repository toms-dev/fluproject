import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;


public class ResistancesSet {
	private Propagable propagable ;
	private double resistanceBase ;
	private List<PropagableResistanceBonus> resistances = new ArrayList<PropagableResistanceBonus>();
	
	public ResistancesSet(double base) {
		resistanceBase = base ;
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
	
	public String getProtectionSuccessMessage(Illness i, LivingEntity source, LivingEntity target) {
		return (new StringBuilder()).append(target.toString()).append(" was protected from ")
				.append(i.toString()).append(" given by ").append(source.toString()).toString();
	}
}
