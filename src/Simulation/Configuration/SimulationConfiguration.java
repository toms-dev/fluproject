package Simulation.Configuration;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import Simulation.World;
import Simulation.Beings.Chicken;
import Simulation.Beings.Duck;
import Simulation.Beings.Human;
import Simulation.Beings.Being;
import Simulation.Beings.Pig;
import Simulation.Propagation.Neighbourhood;
import Simulation.Propagation.Propagable;
import Simulation.Propagation.Behaviors.Habit;
import Simulation.Propagation.Illness.Illness;
import Simulation.Propagation.Illness.Vaccine;


/**
 * This class is used to store the configuration of the Simulation.
 * @author Tom GUILLERMIN
 */
public class SimulationConfiguration {
	public Point[] neighbourhoodVectors = null;
	
	public Map<String, Illness> Illnesses ;
	
	public Map<String, Vaccine> Vaccines ;
	
	public Map<String, Habit> Habits ;
	
	public int humansNum, chickensNum, ducksNum, pigsNum ;
	public int worldWidth, worldHeight;
	
	protected World world ;
	
	/**
	 * Defines the number of entity of each species in the population.
	 * @param humans The number of humans in the population.
	 * @param chickens The number of chickens in the population.
	 * @param ducks The number of ducks in the population.
	 * @param pigs The number of pigs in the population.
	 */
	public void setPopulation(int humans, int chickens, int ducks, int pigs){
		humansNum = humans;
		chickensNum = chickens;
		ducksNum = ducks ;
		pigsNum = pigs;
	}
	
	/**
	 * Sets the size of the World, in cells.
	 * @param width The width of the world.
	 * @param height The height of the world.
	 */
	public void setWorldSize(int width, int height){
		worldWidth = width ;
		worldHeight = height ;
	}
	
	/**
	 * Initialize the World instance.
	 * @param width The width of the world.
	 * @param height The height of the world.
	 * @author Loïc GAILLARD
	 */
	public void setupWorld(int width, int height){
		world = World.getInstance();
		world.setup(width, height);
	}
	
	/**
	 * Performs the initial configuration of the World.
	 * @author Tom GUILLERMIN
     * @author Loïc GAILLARD
	 */
	public void setup(){
		setupWorld(worldWidth, worldHeight);
		generateBaseIllnesses();
		Dimension size = world.getSize();
		System.out.println("World size : "+size.width+"x"+size.height);
		generatePopulation();
	}
	
	/**
	 * Creates the classic illnesses of the project. (H5N1, H1N1)
	 */
	public void generateBaseIllnesses(){
		Illnesses = new HashMap<String, Illness>();
		Vaccines = new HashMap<String, Vaccine>();
		Habits = new HashMap<String, Habit>();
		
		Illness flu = new Illness("flu");
		flu.addTargetSpecies(Being.HUMAN);
		
		Illness H5N1 = new Illness("H5N1");
		
		H5N1.addTargetSpecies(Being.CHICKEN);
		H5N1.addTargetSpecies(Being.DUCK);
		H5N1.addTargetSpecies(Being.HUMAN);
		
		H5N1.addPropagationLink(Being.ANY, Being.CHICKEN, 0.4);
		H5N1.addPropagationLink(Being.ANY, Being.DUCK, 0.3);
		H5N1.addPropagationLink(Being.ANY, Being.HUMAN, 0.2);
		H5N1.addPropagationLink(Being.HUMAN, Being.ANY, 0.1); // Very rare ;)
		H5N1.addPropagationLink(Being.HUMAN, Being.HUMAN, 0.4);
		
		Vaccine H5N1Vaccine = new Vaccine("H5N1 Human Vaccine");
		H5N1Vaccine.addProtectionAgainst(H5N1);
		
		Illness H1N1 = new Illness("H1N1");
		H1N1.addPropagationLink(Being.PIG, Being.HUMAN, 0.1);
		H1N1.addPropagationLink(Being.HUMAN, Being.HUMAN, 0.5);
		
		Vaccine H1N1Vaccine = new Vaccine("H1N1 Human Vaccine");
		H5N1Vaccine.addProtectionAgainst(H5N1);
		
		Illnesses.put("H5N1",H5N1);
		Illnesses.put("H1N1",H1N1);
		
		Vaccine miracleVaccine = new Vaccine("The Miracle Vaccine");
		miracleVaccine.addProtectionAgainst(Illnesses.values());
		
		Vaccines.put("H5N1", H5N1Vaccine);
		Vaccines.put("H5N5", H1N1Vaccine);
		Vaccines.put("Miracle", miracleVaccine);
		
		Habit handWashing = new Habit("Handwashing", Being.HUMAN, 0.25, 0.2),
				mudBath = new Habit("MudBath", Being.PIG, 0.1, 0.3);
		
		Habits.put("MudBath", mudBath);
		Habits.put("Handwashing", handWashing);
	}
	
	/**
	 * Generates the population.
	 * @param humansNum Humans number
	 * @param chickensNum Chickens number
	 * @param ducksNum Ducks number
	 * @param pigsNum Pigs number
	 */
	private void generatePopulation(){
		Random r = new Random();
		
		// Number of sick entities to generate
		int sickChickens = (int) Math.round(ducksNum*(0.40 + 0.2*r.nextFloat())),
			sickDucks = (int) Math.round(ducksNum*(0.20 + 0.2*r.nextFloat())),
			sickPigs = (int) Math.round(pigsNum*(0.40 + 0.2*r.nextFloat()));
		
		
		int H5N1VaccinedHumans = (int) Math.round(humansNum*0.4*r.nextDouble()),
				H1N1VaccinedHumans = (int) Math.round(humansNum*0.4*r.nextDouble()),
				killersHumans = (int) Math.round(humansNum*0.15*r.nextDouble());
		
		System.out.println("   Adding "+humansNum+" humans...");
		List<Being> humans = new ArrayList<Being>();
		int stubbornHumans = 0;
		for(int i = 0; i < humansNum ; i++){
			Point pos = world.getRandomEmptyCell();
			Human e = new Human(pos.x, pos.y);
			
			// Set the stubbornness
			if(r.nextBoolean()) {
				e.setStuborn(true);
				stubbornHumans++;
			}
			humans.add(e);
			world.addEntity(e);
		}
		
		System.out.println("   "+stubbornHumans+" humans are stubborn and don't want to learn new habits.");
		
		// Propagate the vaccines in the population
		randomInitialPropagation(humans, Vaccines.get("H5N1"), H5N1VaccinedHumans);
		randomInitialPropagation(humans, Vaccines.get("H1N1"), H1N1VaccinedHumans);
		randomInitialPropagation(humans, Habits.get("HandWashing"), humansNum/2);
		
		System.out.println("     "+H5N1VaccinedHumans+" humans are now vaccined against H5N1");
		System.out.println("     "+H1N1VaccinedHumans+" humans are now vaccined against H1N1");
		
		System.out.println("   Adding "+chickensNum+" chickens...");
		for(int i = 0; i < chickensNum ; i++){
			Point pos = world.getRandomEmptyCell();
			Chicken e = new Chicken(pos.x, pos.y);
			if( sickChickens > 0 ){
				e.setSick(Illnesses.get("H5N1"));
				--sickChickens;
			}
			world.addEntity(e);
		}
		
		System.out.println("   Adding "+ducksNum+" ducks...");
		for(int i = 0; i < ducksNum ; i++){
			Point pos = world.getRandomEmptyCell();
			Duck e = new Duck(pos.x, pos.y);
			if( sickDucks > 0 ){
				e.setSick(Illnesses.get("H5N1"));
				--sickDucks;
			}
			world.addEntity(e);
		}
		
		System.out.println("   Adding "+pigsNum+" pigs...");
		List<Being> pigs = new ArrayList<Being>();
		for(int i = 0; i < pigsNum ; i++){
			Point pos = world.getRandomEmptyCell();
			Pig e = new Pig(pos.x, pos.y);
			pigs.add(e);
			if( sickPigs > 0 ){
				e.setSick(Illnesses.get("H5N1"));
				--sickPigs;
			}
			world.addEntity(e);
		}
		randomInitialPropagation(pigs, Habits.get("MudBath"), (int) (pigsNum*0.5));
		
		System.out.println("World populated successfully !");
	}
	
	/**
	 * Define the type of neighborhood used in the simulation.
	 * @param name Name of used neighborhood.
	 */
	public void setNeighbourhoodType(String name){
		if (name.equals("four")) {
			neighbourhoodVectors = Neighbourhood.FOUR;
		}
		else if( name.equals("eight") ){
			neighbourhoodVectors = Neighbourhood.EIGHT;
		}
	}
	
	/**
	 * Randomly vaccinates a population
	 * @param entities The population of entities that can be vaccinated
	 * @param v		The vaccine
	 * @param number Number of vaccines to apply on the population
	 */
	public static void randomVaccinatePopulation(List<Being> entities, Vaccine v, int number){
		if( number > entities.size() ){
			throw new RuntimeException("You can't vaccinate "+number+" entities in a population of only "+entities.size());
		}
		Random r = new Random();
		for(int i = 0; i < number ; i++){
			boolean done = false;
			Being entity ;
			while(!done){
				entity = entities.get(r.nextInt(entities.size()));
				System.out.println(entity+" | "+entity.getVaccines());

				if (!entity.hasVaccine(v)) {
					entity.addVaccine(v);
					done = true ;
				}
			}
		}
	}
	
	/**
	 * Randomly propagates a propagable in a given population.
	 * @param entities The population.
	 * @param p The propagable to spread.
	 * @param number The number of propagations
	 */
	public static void randomInitialPropagation(List<Being> entities, Propagable p, int number){
		if( number > entities.size() ){
			throw new RuntimeException("You can't propagate "+p+" to "+number+" entities in a population of only "+entities.size());
		}
		Random r = new Random();
		for(int i = 0; i < number ; i++){
			boolean done = false;
			Being entity ;
			while(!done){
				entity = entities.get(r.nextInt(entities.size()));

				if (entity.canReceive(p)) {
					if (p instanceof Habit) entity.addHabit((Habit) p);
					if (p instanceof Vaccine) entity.addVaccine((Vaccine) p);
					if (p instanceof Illness) entity.setSick((Illness) p);
					done = true ;
				}
			}
		}
	}

	
}

