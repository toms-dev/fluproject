import java.util.List;


/**
 * This interface describes an element to which a propagation may happen.
 * @author tguiller
 *
 */
public interface PropagationNode {
	/**
	 * Adds a Propagable element to the node.
	 * Then, the node may propagate the Propagable.
	 * @param p The Propagable to add.
	 */
	public void addPropagable(Propagable p);
	
	/**
	 * Removes a Propagable element from the node.
	 * Then, the node won't be able to propagate the Propagable anymore. 
	 * @param p The Propagable to remove.
	 */
	public void removePropagable(Propagable p);
	
	/**
	 * Returns true if the node can receive a given propagable. (compatibility)
	 * This is compatibility data.
	 * @param p The propagable.
	 * @return True if the node can receive the propagable.
	 */
	public boolean canReceive(Propagable p);
	
	/**
	 * Returns true if the node is propagating a Propagable.
	 * @param p The propagable.
	 * @return True if the node can propagate the Propagable.
	 */
	public boolean isPropagating(Propagable p);
	
	/**
	 * Returns all the propagables that the entity has.
	 * @return The list containing all the propagables;
	 */
	public List<Propagable> getPropagables();
}
