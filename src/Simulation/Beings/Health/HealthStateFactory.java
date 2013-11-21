package Simulation.Beings.Health;

public class HealthStateFactory {
	public static int SickDuration = 2,
			ContagiousDuration = 3,
			RecoveringDuration = 3;
	
	public static HealthState Healty(){
		return new HealthState(HealthState.Healthy, false, true);
	}
	
	public static HealthState Sick(){
		return new HealthState(HealthState.Sick, false, SickDuration);
	}
	
	public static HealthState Contagious(){
		return new HealthState(HealthState.Contagious, true, ContagiousDuration);
	}
	
	public static HealthState Recovering(){
		return new HealthState(HealthState.Recovering, false, RecoveringDuration);
	}
	
	public static HealthState Dead(){
		return new HealthState(HealthState.Dead, false, false);
	}
}
