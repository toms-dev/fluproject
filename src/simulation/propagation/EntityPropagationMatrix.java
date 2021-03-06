package simulation.propagation;
import simulation.beings.Being;

/**
 * This class represents the propagation matrix for the entities in the simulation. 
 * @author Tom GUILLERMIN
 *
 */
public class EntityPropagationMatrix extends PropagationMatrix<Integer>{
	public EntityPropagationMatrix() {
		super(Being.ANY);
	}
}
