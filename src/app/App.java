package app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import simulation.LogManager;
import simulation.SimulationConfiguration;
import simulation.World;
import simulation.beings.health.HealthState;


/**
 * The Application class.
 * It contains the main function.
 * @author Loïc GAILLARD
 */
public class App {
    private LogManager logManager;
    private World world;
    private boolean autoPlay ;
    private int autoPlayDuration ;
    
    /**
     * Entrance of the program.
     * @param args
     * @throws IOException
     * @throws InterruptedException
     * @author Loïc GAILLARD
     */
    public static void main(String[] args) throws IOException, InterruptedException{
        App app = new App();
        
        app.configure();
        app.run();
        app.end();
    }
    
    /**
     * The App constructor
     * @author Loïc GAILLARD
     */
    public App() {
        logManager = LogManager.getInstance();
        
        System.out.println(PrettyConsole.HeaderTextBox("Welcome !", "Welcome to Epidemics, the flu propagation\nsimulation !"));
    }
    
    /**
     * Start the configuration of the app.
     * @author Loïc GAILLARD
     */
    public void configure() {
        System.out.println("Initializing simulation...");
        
        SimulationConfiguration config = new SimulationConfiguration();
        if(!InputReader.readBooleanYN("Do you want to load the default configuration ?")) {
            userConfiguration(config);
        }
        config.setup();
        
        if(InputReader.readBooleanYN("\nDo you want to enable autoplay ?")) configureAutoPlay();
        
        world = World.getInstance();
        logManager.log("World created.");
        
        System.out.println(world);
    }
    
    /**
     * The main loop of the app.
     * @throws IOException
     * @throws InterruptedException
     * @author Loïc GAILLARD
     */
    public void run() throws IOException, InterruptedException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        
        while(!world.isFinished()){  
            if( autoPlay ){
                Thread.sleep(autoPlayDuration);
            }
            else {
                System.out.println("\n(Press enter for next step - or type 'a' to enable Auto-Play)");
                if(reader.readLine().equals("a")){
                    configureAutoPlay();
                }
            }
            System.out.println("\n\n");
            
            world.tick();
            
            System.out.println(world);
        }
    }
    
    /**
     * Print the ending message
     * @author Loïc GAILLARD
     */
    public void end() {
        System.out.println(PrettyConsole.HeaderTextBox("Info", "The simulation is now finished !\n"+world.getEntitiesWithHealth(HealthState.Dead).size()+" died from the flu."));    
    }
    
    /**
     * Let the user configures the simulation.
     * @return The SimulationConfiguration.
     * @author Tom GUILLERMIN
     */
    private void userConfiguration(SimulationConfiguration config){
        String neighbourhood = InputReader.readStringArray("\nSelect neighbourhood type :", new String[]{"four","eight"});
        config.setNeighbourhoodType(neighbourhood);
        
        int width = InputReader.readIntegerInRange("\nPlease enter the world width :", 2, 15);
        int height = InputReader.readIntegerInRange("\nPlease enter the world height :", 2, 15);        
        
        System.out.println("Creating a "+width+"x"+height+" world...");
        config.setWorldSize(width, height);
        
        // Number of entities in the world
        int maxEntitiesNum = width*height;
        int entitiesRatio = InputReader.readIntegerInRange("\nPlease enter the population density percentage in the world :", 0, 100);
        int entitiesNum = (int) Math.floor((entitiesRatio*1.0)/100 * maxEntitiesNum);
        
        //System.out.println(entitiesNum+" entities will be created.");
        
        // Get the ratio
        int humansRatio = InputReader.readInteger("Enter human ratio :");
        int chickensRatio = InputReader.readInteger("Enter chickens ratio :");
        int ducksRatio = InputReader.readInteger("Enter ducks ratio :");
        int pigsRatio = InputReader.readInteger("Enter pigs ratio :");
        
        int totalRatio = humansRatio + chickensRatio + ducksRatio + pigsRatio ;
        
        // Convert to number
        int humansNum = (int) Math.floor(humansRatio * 1.0 / totalRatio * entitiesNum) ;
        int chickensNum = (int) Math.floor(chickensRatio * 1.0 / totalRatio * entitiesNum) ;
        int ducksNum = (int) Math.floor(ducksRatio * 1.0 / totalRatio * entitiesNum) ;
        int pigsNum = (int) Math.floor(pigsRatio * 1.0 / totalRatio * entitiesNum) ;
        
        int totalEntities = humansNum+chickensNum+ducksNum+pigsNum;
        
        System.out.println(totalEntities+" entities were created.");
        config.setPopulation(humansNum, chickensNum, ducksNum, pigsNum);
        
        System.out.println("Initialization Done.");
    }
    
    /**
     * Configures and starts the auto play
     * @author Tom GUILLERMIN
     * @author Loïc GAILLARD
     */
    private void configureAutoPlay() {
        autoPlayDuration = InputReader.readPositiveInteger("Please choose the duration of a day for the AutoPlay mode : (in ms) ");
        autoPlay = true;
    }
}
