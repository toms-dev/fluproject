import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;


public abstract class LivingEntity implements PropagationNode {
	protected boolean canDie ;
	public final static int 
		ANY = 0,
		HUMAN = 1,
		CHICKEN = 2,
		DUCK = 3,
		PIG = 4;
	
	public char symbol = 'X';
	protected String name ;
	private HealthState health, nextHealth;
	private Point position ;
	private int type ;
	private Illness illness = null;
	private List<Vaccine> vaccines = new ArrayList<Vaccine>();
	private List<Habit> habits = new ArrayList<Habit>();
	//private HashMap<String, List<Propagable>> propagables = new HashMap<String, List<Propagable>>();

	private List<Propagable> propagables = new ArrayList<Propagable>();
	private boolean stuborn;
	private double resistanceBase = 0.5;
	
	/**
	 * Constructor for LivingEntity.
	 * @param type Type of the entity.
	 * @param x X Coordinates
	 * @param y Y Coordinates
	 */
	public LivingEntity(int type, int x, int y){
		this.type = type ;
		health = HealthStateFactory.Healty();
		position = new Point(x, y);
	}
	
	/**
	 * Sets the position of the entity to <i>x</i>,<i>y</i>.
	 * @param x 
	 * @param y
	 */
	public void setPosition(int x, int y){
		position.setLocation(x, y);
	}
	
	/**
	 * Returns the X coordinates of the entity.
	 * @return
	 */
	public int getPosX() {
		return position.x;
	}
	
	/**
	 * Returns the Y coordinates of the entity.
	 * @return
	 */
	public int getPosY() {
		return position.y;
	}
	
	/**
	 * Returns the current health state of the entity.
	 * @return
	 */
	public HealthState getHealth(){
		return health;
	}

	/**
	 * Returns the type of the LivingEntity.
	 * @return
	 */
	public int getType() {
		return this.type;
	}
	
	/**
	 * Returns true if the entity is Contagious and may propagate its illness to neighobouring entities.
	 * @return
	 */
	public boolean isContagious(){
		return health.isContagious();
	}
	
	/**
	 * Returns true if the entity has an illness.
	 * @return
	 */
	public boolean hasIllness(){
		return illness != null ;
	}

	/**
	 * Updates the entity to its next health state.
	 * If the entity was Sick, it gets Contagious.
	 * If the entity was Contagious, it may die from its illness or go to Recover mode.
	 * If the entity was Recovering, it gets healthy again.
	 */
	public void updateToNextHealthState(){
		Random r = new Random();
		int type = health.getType(); 
		if( type == HealthState.Sick ){
			setContagious();
		}
		else if ( type == HealthState.Contagious ) {
			if( r.nextBoolean() || ! canDie ){
				setRecovering();
			} else {
				die();
			}
		}
		else if ( type == HealthState.Recovering ) {
			setHealthy();
		}
	}
	
	/**
	 * Do a tick. Returns true if the entity's health state has to be changed.
	 * @return True if the entity's health state has to be changed.
	 */
	public boolean tick(){
		health.tick();
		if (health.mustBeUpdated()){
			updateToNextHealthState();
			return true ;
		}
		return false;
	}
	
	/**
	 * Called at the end of the tick.
	 * Finish updating the entity state.
	 */
	public void endOfTick(){
		if( this.nextHealth != this.health ){
			this.health = this.nextHealth;
		}
	}
	
	/**
	 * Set the illness of the entity.
	 * @param i
	 */
	private void setIllness(Illness i){
		this.removePropagable(illness);
		this.illness = i ;
		if (i != null) this.addPropagable(i);
	}
	
	/**
	 * Set the entity to be healthy.
	 */
	public void setHealthy(){
		setIllness(null);
		this.health = HealthStateFactory.Healty();
	}
	
	/**
	 * Set the entity to be sick with a given illness/
	 * @param illness
	 */
	public void setSick(Illness illness) {
		setIllness(illness);
		this.health = HealthStateFactory.Sick();
	}
	
	/**
	 * Set the entity to contagious state.
	 */
	public void setContagious() {
		this.health = HealthStateFactory.Contagious();
	}
	
	/**
	 * Set the entity to be recovering.
	 */
	public void setRecovering() {
		this.health = HealthStateFactory.Recovering();
	}
	
	/**
	 * Make the entity dies.
	 */
	public void die(){
		this.health = HealthStateFactory.Dead();
	}
	
	/**
	 * Returns true if the entity is dead.
	 * @return
	 */
	public boolean isDead() {
		return health.getType() == HealthState.Dead ;
	}
	
	/**
	 * Returns the current Illness of the entity.
	 * If the entity is not ill, it returns null.
	 * @return
	 */
	public Illness getIllness(){
		return illness;
	}
	
	/**
	 * Vaccinates the entity with a Vaccine
	 * @param v The vaccine
	 */
	public void addVaccine(Vaccine v){
		vaccines.add(v);
		addPropagable(v);
	}
	
	/**
	 * Returns true if the entity is vaccined against a provided Illness
	 * @param i The illness
	 * @return
	 */
	public boolean isVaccinedAgainst(Illness i){
		for(Vaccine v : vaccines){
			if (v.protectsAgainst(i)) {
				return true ;
			}
		}
		return false;
	}
	
	/**
	 * Returns true if the entity has a given vaccine.
	 * @param v The vaccine.
	 * @return The entity has the vaccine.
	 */
	public boolean hasVaccine(Vaccine v){
		return vaccines.contains(v);
	}
	
	/**
	 * Returns the list of the vaccine the entity has.
	 * @return
	 */
	public List<Vaccine> getVaccines(){
		return vaccines;
	}
	
	/**
	 * Gets a random vaccine of the entity.
	 * @return A random vaccine of the entity.
	 */
	public Vaccine getRandomVaccine(){
		Random r = new Random();
		if( vaccines.size() == 0 ) return null ;
		return vaccines.get(r.nextInt(vaccines.size()));
	}
	
	public void addHabit(Habit h){
		habits.add(h);
		//System.out.println("Habit "+h+" added to "+this+". Habits = "+habits);
		addPropagable(h);
	}
	
	/**
	 * Returns true if this LivingEntity has a given habit.
	 * @param h
	 * @return
	 */
	public boolean hasHabit(Habit h) {
		return habits.contains(h);
	}
	
	/**
	 * Sets if the entity is stuborn and can't learn Habits.
	 * @param b
	 */
	public void setStuborn(boolean b) {
		stuborn = b ;
	}
	
	/**
	 * Returns true if the entity is stuborn.
	 * In this case, he won't be able to receive an Habit from a neighbour. 
	 * @return
	 */
	public boolean isStubborn() {
		return stuborn ;
	}
	
	public String toString(){
		return name+" @ ("+position.x+", "+position.y+")";
	}

	/*
	 		Implementation of
	 		PropagationElement methods
	 */
	@Override
	public void addPropagable(Propagable p) {
		this.propagables.add(p);
	}
	
	@Override
	public void removePropagable(Propagable p){
		this.propagables.remove(p);
	}
	
	@Override
	public List<Propagable> getPropagables(){
		return this.propagables ;
	}
	
	public List<PropagableResistanceBonus> getResistanceBonuses() {
		List<PropagableResistanceBonus> resistances = new ArrayList<PropagableResistanceBonus>();
		for(Propagable r : getPropagables()){
			if( r instanceof PropagableResistanceBonus) {
				resistances.add((PropagableResistanceBonus) r);
			}
		}
		return resistances ;
	}
	
	@Override
	// TODO : is this really needed ?
	public boolean canReceive(Propagable p) {
		if (p instanceof Habit) return !hasHabit((Habit) p) && ((Habit) p).appliesTo(getType()) && !isDead();
		if (p instanceof Illness) return !hasIllness() && !isDead();
		if (p instanceof Vaccine) return !hasVaccine((Vaccine) p) && !isDead();
		
		return true ;
	}
	
	@Override
	public boolean isPropagating(Propagable p) {
		if (p instanceof Illness) return isContagious();
		//if (p instanceof Habit) return isStuborn();
		// Default
		return true ;
	}

	public List<Habit> getHabits() {
		return habits;
	}

	public double getResistanceBase() {
		return resistanceBase ;
	}
}
