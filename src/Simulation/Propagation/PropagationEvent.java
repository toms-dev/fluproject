package Simulation.Propagation;

/**
 * This class represents an event of propagation.
 * It might be successful or failed. 
 * @author Tom GUILLERMIN
 *
 */
public class PropagationEvent {
	
	private boolean log,
		successful,
		important;
	
	private String message;

	/**
	 * Creates a new Propagation Event
	 * @param log Whether if the event should be logged or not.
	 * @param message The message of the event.
	 * @param important Whether if the event counts as a real event. 
	 */
	public PropagationEvent(boolean log, String message, boolean successful, boolean important){
		this.log = log ;
		this.message = message ;
		this.important = important ;
		this.successful = successful ;
	}
	
	/**
	 * Creates an empty event. By default, the event is logged, failed, and does not count as a real event.
	 */
	public PropagationEvent(){
		this(true, "Default event message", false, false);
	}
	
	/**
	 * Sets the event to be successful or not.
	 * @param b
	 */
	public void setSuccessful(boolean b) {
		successful = b ;
	}
	
	/**
	 * Returns true if the event is considered as successful.
	 * @return Whether if the event is considered as successful.
	 */
	public boolean isSuccessful() {
		return successful ;
	}
	
	/**
	 * Set if the event should be logged or not.
	 * @param b Whether if the event should be logged or not.
	 */
	public void setLogged(boolean b) {
		log = b ;
	}
	
	/**
	 * Returns true if the event should be logged.
	 * @return True if the event should be logged.
	 */
	public boolean isLogged() {
		return log ;
	}
	
	/**
	 * Sets whether the event is important.
	 * @param b The event is important
	 */
	public void setImportant(boolean b) {
		important = b ;
	}
	
	/**
	 * Returns true if the event is important and should count.
	 * @return True if the event is important and should count.
	 */
	public boolean isImportant() {
		return important ;
	}
	
	/**
	 * Sets the message of the event.
	 * @param s The message of the event.
	 */
	public void setMessage(String s) {
		message = s;
	}
	
	/**
	 * Returns the message of the event.
	 * @return The message of the event.
	 */
	public String getMessage() {
		return message ;
	}
}
