package App;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import Simulation.LogManager;
import Simulation.World;
import Simulation.Beings.Health.HealthState;
import Simulation.Configuration.DefaultSimulationConfiguration;
import Simulation.Configuration.SimulationConfiguration;

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
        
        SimulationConfiguration config ;
        if(readBooleanYN("Do you want to load the default configuration ?")) {
            config = new DefaultSimulationConfiguration();
        }
        else {
            config = userConfiguration();
        }
        config.setup();
        
        if(readBooleanYN("\nDo you want to enable autoplay ?")) configureAutoPlay();
        
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
    private SimulationConfiguration userConfiguration(){
        SimulationConfiguration config = new SimulationConfiguration();
        String neighbourhood = readStringArray("\nSelect neighbourhood type :", new String[]{"four","eight"});
        config.setNeighbourhoodType(neighbourhood);
        
        int width = readIntegerInRange("\nPlease enter the world width :", 2, 15);
        int height = readIntegerInRange("\nPlease enter the world height :", 2, 15);        
        
        System.out.println("Creating a "+width+"x"+height+" world...");
        config.setWorldSize(width, height);
        config.setupWorld(width, height);
        
        // Number of entities in the world
        int maxEntitiesNum = config.world.getCellsNum();
        int entitiesRatio = readIntegerInRange("\nPlease enter the population density percentage in the world :", 0, 100);
        int entitiesNum = (int) Math.floor((entitiesRatio*1.0)/100 * maxEntitiesNum);
        
        //System.out.println(entitiesNum+" entities will be created.");
        
        // Get the ratio
        int humansRatio = readInteger("Enter human ratio :");
        int chickensRatio = readInteger("Enter chickens ratio :");
        int ducksRatio = readInteger("Enter ducks ratio :");
        int pigsRatio = readInteger("Enter pigs ratio :");
        
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
        return config ;
    }
    
    /**
     * Configures and starts the auto play
     * @author Tom GUILLERMIN
     * @author Loïc GAILLARD
     */
    private void configureAutoPlay() {
        autoPlayDuration = readInteger("Please choose the duration of a day for the AutoPlay mode : (in ms) ");
        if( autoPlayDuration < 0) {
            System.out.println(PrettyConsole.HeaderTextBox("Error", "Please enter a positive duration."));
            configureAutoPlay();
        }
        autoPlay = true;
    }
    
    /**
     * Displays a message and reads an integer on the System input.
     * @author Tom GUILLERMIN
     * @param message The message displayed to the user.
     * @return The input value
     */
    private int readInteger(String message){
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        
        while(true){
            System.out.println(message);
            try {
                return Integer.parseInt(reader.readLine());
            } catch (NumberFormatException e){
                System.out.println(PrettyConsole.HeaderTextBox("Error", "Please enter a valid value !"));
            } catch (IOException e){}
        }
    }
    
    /**
     * Displays a message and reads a positive integer on the System input.
     * The user input will be asked until the entered value is positive.
     * @author Loïc GAILLARD
     * @param message The message displayed to the user.
     * @return The input value
     */
    private int readPositiveInteger(String message) {
        int input;
        while((input = readInteger(message)) < 0) {
            System.out.println(PrettyConsole.HeaderTextBox("Error", "Please enter a positive number !"));
        }
        return input;
    }
    
    /**
     * Displays a message and reads an integer in a range on the System input.
     * The user's input will be asked until the entered value is in range.
     * @author Loïc GAILLARD
     * @param message The message displayed to the user.
     * @param min The minimum value of the input.
     * @param max The maximum value of the input.
     * @return The input value.
     */
    private int readIntegerInRange(String message, int min, int max){
        int input = readInteger(message);
        while(min <= input && input <= max){
            System.out.println(PrettyConsole.HeaderTextBox("Error", "You must enter a value between "+min+" and "+max+"."));
            input = readInteger(message);
        }
        return input ;
    }
    
    /**
     * Displays a message and reads a boolean on the System input.
     * @author Loïc GAILLARD
     * @param message The message displayed to the user.
     * @return The input value
     */
    private boolean readBooleanYN(String message) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            System.out.println(message+" (Y/n)");
            try {
                String input = reader.readLine();
                if("Y".equals(input) || "y".equals(input)) return true;
                if("N".equals(input) || "n".equals(input)) return false;
            } catch(IOException e){}
        }
    }
    
    /**
     * Displays a message and reads a valid String on the System input.
     * The user's input will be asked until the String is valid.
     * @param message The message displayed to the user.
     * @param validStrings The array of valid Strings
     * @return The valid user's String
     */
    private String readStringArray(String message, String[] validStrings){
        String input ;
        
        String join = "" ;
        for(int i = 0 ; i < validStrings.length ; i++ ){
            join += validStrings[i] ;
            if( i < validStrings.length-1 ) join += ", ";
        }
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        
        while(true){
            System.out.println(message+" (Accepted values : "+join+")");
            try {
                input = reader.readLine();
                for(String s : validStrings){
                    if (s.equals(input)) {
                        return input ;
                    }
                }
                System.out.println(PrettyConsole.HeaderTextBox("Error", "Please enter one of these value : "+join));
            } catch(IOException e){}
        }
    }
}
