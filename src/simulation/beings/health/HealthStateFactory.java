package simulation.beings.health;

/**
 * This class is used to generate HealthState instance.
 * @author Tom GUILLERMIN
 *
 */
public class HealthStateFactory {
	// TODO : move the HealthState types here !
	
	public static int SickDuration = 2,
			ContagiousDuration = 3,
			RecoveringDuration = 3;
	
	/**
	 * Generates a Healthy state.
	 * @return
	 */
	public static HealthState Healty(){
		return new HealthState(HealthState.Healthy, false, true);
	}
	
	/**
	 * Generates a Sick state.
	 * @return
	 */
	public static HealthState Sick(){
		return new HealthState(HealthState.Sick, false, SickDuration);
	}
	
	/**
	 * Generates a Contagious state.
	 * @return
	 */
	public static HealthState Contagious(){
		return new HealthState(HealthState.Contagious, true, ContagiousDuration);
	}
	
	/**
	 * Generates a Recovering state.
	 * @return
	 */
	public static HealthState Recovering(){
		return new HealthState(HealthState.Recovering, false, RecoveringDuration);
	}
	
	/**
	 * Generates a Dead state.
	 * @return
	 */
	public static HealthState Dead(){
		return new HealthState(HealthState.Dead, false, false);
	}
}
