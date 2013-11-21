package simulation.beings.health;

/**
 * This class represents a health state. It may evolve in the time.
 * It can be set to be contagious or the change automatically after a certain duration.
 * @author Tom GUILLERMIN
 *
 */
public class HealthState {
	public static int Healthy = 0;
	public static int Sick = 1;
	public static int Contagious = 2;
	public static int Recovering = 3;
	public static int Dead = 4;
	
	private int type ;
	
	private boolean isContagious;
	
	private boolean autoExpires = false;
	private int remainingDays ;
	
	/**
	 * Health constructor
	 * @param type The ID of the Health type
	 * @param isContagious True if this HealthState is contagious to other neighboring beings.
	 * @param canChange True if it can change by itself.
	 */
	public HealthState(int type, boolean isContagious, boolean canChange){
		this(type, isContagious, -1);
	}
	
	/**
	 * Health constructor
	 * @param type The ID of the Health type
	 * @param isContagious True if this HealthState is contagious to other neighboring beings.
	 * @param duration The duration of this health state.
	 */
	public HealthState(int type, boolean isContagious, int duration){
		this.type = type ;
		this.isContagious = isContagious;
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
	
	/**
	 * Returns the number of remaining days before the Health State ends.
	 * @return The number of remaining days before the Health State ends.
	 */
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
	
	/**
	 * Performs a logic update.
	 */
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
