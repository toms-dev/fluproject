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
	
	public EntityPropagationMatrix getPropagationMatrix(){
		return propagationMatrix;
	}
	
	/**
	 * Returns the propagation rate from a source node to a target one.
	 * @param sourceType
	 * @param targetType
	 * @return
	 */
	public double getPropagationRate(Integer sourceType, Integer targetType) {
		return propagationMatrix.getPropagation(sourceType, targetType);
	}
	
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
