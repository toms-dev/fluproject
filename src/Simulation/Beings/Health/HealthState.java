package Simulation.Beings.Health;

public class HealthState {
	public static int Healthy = 0;
	public static int Sick = 1;
	public static int Contagious = 2;
	public static int Recovering = 3;
	public static int Dead = 4;
	
	private int type ;
	
	private boolean isContagious, canChange;
	private int stateDuration ;
	
	private boolean autoExpires = false;
	private int remainingDays ;
	
	public HealthState(int type, boolean isContagious, boolean canChange){
		this(type, isContagious, canChange, -1);
	}
	
	public HealthState(int type, boolean isContagious, boolean canChange, int duration){
		this.type = type ;
		this.isContagious = isContagious;
		this.canChange = canChange;
		if( duration != -1 ){
			autoExpires = true ;
			this.remainingDays = duration;
		}
	}

	/**
	 * Returns true if the health state is contagious to others.
	 * @return
	 */
	public boolean isContagious(){
		return isContagious;
	}
	
	/**
	 * Returns the type of health state.
	 * @return
	 */
	public int getType(){
		return type;
	}
	
	public int getRemainingDays(){
		return remainingDays;
	}
	
	/**
	 * Returns true if the health state has to be updated to the consequent one.
	 * @return
	 */
	public boolean mustBeUpdated(){
		return autoExpires && remainingDays <= 0;
	}
	
	public void tick(){
		if( autoExpires ){
			remainingDays--;	
		}
	}
	
	/**
	 * Returns a single character, representing this health state (for grid drawing purposes).
	 * @return
	 */
	public char toReducedString() {
		char c = ' ';
		if( type == Healthy ) c = ' ' ; //'☺';
		else if ( type == Sick ) c = '!';
		else if( type == Contagious ) c = '*';
		else if ( type == Recovering ) c = '~';
		else if( type == Dead ) c = '✝';
		
		return c;
	}
}
