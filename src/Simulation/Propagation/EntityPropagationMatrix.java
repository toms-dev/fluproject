package Simulation.Propagation;
import Simulation.Beings.LivingEntity;


public class EntityPropagationMatrix extends PropagationMatrix<Integer>{
	public EntityPropagationMatrix() {
		super(LivingEntity.ANY);
	}
}
