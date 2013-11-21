package Simulation.Propagation;
import Simulation.Beings.PropagationNode;


/**
 * This interface describes a propagable element.
 * @author tguiller
 *
 */
public abstract class Propagable {
	private EntityPropagationMatrix propagationMatrix = new EntityPropagationMatrix();
	
	@Deprecated
	public void setPropagationMatrix(EntityPropagationMatrix m){
		propagationMatrix = m ;
	}
	
	/**
	 * Returns the propagation matrix of the Propagable.
	 * @return The propagation matrix.
	 */
	public EntityPropagationMatrix getPropagationMatrix(){
		return propagationMatrix;
	}
	
	/**
	 * Returns the propagation rate from a source node to a target one.
	 * @param sourceType
	 * @param targetType
	 * @return The probability of propagation.
	 */
	public double getPropagationRate(Integer sourceType, Integer targetType) {
		return propagationMatrix.getPropagation(sourceType, targetType);
	}
	
	/**
	 * Add a link of propagation between two species.
	 * @param sourceType The emitting type.
	 * @param targetType The receiving type.
	 * @param rate The probability rate of propagation.
	 */
	public void addPropagationLink(int sourceType, int targetType, double rate) {
		propagationMatrix.addPropagationLink(sourceType, targetType, rate);
	}
	
	/**
	 * Makes an attempt to propagate to the provided Being.
	 * Returns true if the propagation was successful.
	 * @param e The target Being
	 * @return True if the propagation was successful.
	 */
	public abstract PropagationEvent tryToPropagateTo(PropagationNode source, PropagationNode target);
	
	/**
	 * Returns true if the Propagable can be propagate to the provided Being in a general way.
	 * Does not performs randomization for result.
	 * @param e The target Being 
	 * @return The Propagable can propagate to the entity;
	 */
	public abstract boolean canPropagateFromTo(PropagationNode source, PropagationNode target);
	
}
