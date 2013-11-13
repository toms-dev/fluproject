package App;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;

import Simulation.Beings.Health.HealthState;
import Simulation.Configuration.DefaultSimulationConfiguration;
import Simulation.Configuration.SimulationConfiguration;


public class App {
	private static boolean autoPlay ;
	private static int autoPlayDuration ;
	
	
	public static void main(String[] args) throws IOException, InterruptedException{
		System.out.println(PrettyConsole.HeaderTextBox("Welcome !", "Welcome to Epidemics, the flu propagation\nsimulation !"));

		System.out.println("Initializing simulation...");
		
		SimulationConfiguration config ;
		
		if(App.readBooleanYN("Do you want to load the default configuration ?")) {
			config = new DefaultSimulationConfiguration();
		}
		else {
			config = userConfiguration();
		}
		
		config.setup();
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		autoPlay = App.readBooleanYN("\nDo you want to enable autoplay ?");
		if (autoPlay) configureAutoPlay();
		
		System.out.println(config.world.toString("World created."));
		
		while(!config.world.isFinished()){	
			if( autoPlay ){
				Thread.sleep(autoPlayDuration);
			}
			else {
				System.out.println("\n(Press enter for next step - or type 'a' to enable Auto-Play)");
				if(reader.readLine().equals("a")){
					autoPlay = true ;
					configureAutoPlay();
				}
			}
			System.out.println("\n\n");
			String log = config.world.tick();
			System.out.println(config.world.toString(log));
		}
		
		System.out.println(PrettyConsole.HeaderTextBox("Info", "The simulation is now finished !\n"+config.world.getEntitiesWithHealth(HealthState.Dead).size()+" died from the flu."));	
	}
	
	private static SimulationConfiguration userConfiguration(){
		SimulationConfiguration config = new SimulationConfiguration();
		String neighbourhood = App.readStringArray("\nSelect neighbourhood type :", new String[]{"four","eight"});
		config.setNeighbourhoodType(neighbourhood);
		
		int width = App.readIntegerInRange("\nPlease enter the world width :", 2, 15);
		int height = App.readIntegerInRange("\nPlease enter the world height :", 2, 15);		
		
		System.out.println("Creating a "+width+"x"+height+" world...");
		config.setWorldSize(width, height);
		config.setupWorld(width, height);
		
		// Number of entities in the world
		int maxEntitiesNum = config.world.getCellsNum();
		int entitiesRatio = App.readIntegerInRange("\nPlease enter the population density percentage in the world :", 0, 100);
		int entitiesNum = (int) Math.floor((entitiesRatio*1.0)/100 * maxEntitiesNum);
		
		//System.out.println(entitiesNum+" entities will be created.");
		
		// Get the ratio
		int humansRatio = App.readInteger("Enter human ratio :");
		int chickensRatio = App.readInteger("Enter chickens ratio :");
		int ducksRatio = App.readInteger("Enter ducks ratio :");
		int pigsRatio = App.readInteger("Enter pigs ratio :");
		
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
	
	
	private static void configureAutoPlay() {
		autoPlayDuration = readInteger("Please choose the duration of a day for the AutoPlay mode : (in ms) ");
		if( autoPlayDuration < 0) {
			System.out.println(PrettyConsole.HeaderTextBox("Error", "Please enter a positive duration."));
			configureAutoPlay();
		}
	}
	
	/**
	 * Display a message and reads an integer on the System input.
	 * @param message The message diplayed to the user.
	 * @return The input value
	 */
	public static int readInteger(String message){
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
	 * Display a message and reads an integer in a range on the System input.
	 * The user input will be asked until the entered value is in range. 
	 * @param message The message displayed to the user.
	 * @param min The minimum value of the input.
	 * @param max The maximum value of the input.
	 * @return The input value.
	 */
	public static int readIntegerInRange(String message, int min, int max){
		int input ;
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		while(true){
			System.out.println(message);
			try {
				input = Integer.parseInt(reader.readLine());
				if (min <= input && input <= max) {
					break;
				}
			} catch (NumberFormatException e){
				System.out.println(PrettyConsole.HeaderTextBox("Error", "Please enter a valid value"));
			} catch (IOException e){}
			System.out.println(PrettyConsole.HeaderTextBox("Error", "You must enter a value between "+min+" and "+max+"."));
		}
		return input ;
	}
	
	/**
	 * Display a message and reads a boolean on the System input.
	 * @param message The message diplayed to the user.
	 * @return The input value
	 */
	public static boolean readBooleanYN(String message) {
		String input ;
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		while(true) {
			System.out.println(message+" (Y/n)");
			try {
				input = reader.readLine();
				
				if( input.equals("Y")) {
					return true ;
				}
				else if (input.equals("n")) {
					return false;
				}
			} catch(IOException e){}
		}
	}
	
	public static String readStringArray(String message, String[] validStrings){
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
