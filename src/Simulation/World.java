package Simulation;
import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Simulation.Beings.Being;
import Simulation.Beings.Health.HealthState;
import Simulation.Propagation.Neighbourhood;
import Simulation.Propagation.Propagable;
import Simulation.Propagation.PropagationEvent;

/**
 * This class represents the world in which all the beings of the
 * simulation lives.
 * It has to be used as a Singleton.
 * @author Tom GUILLERMIN
 *
 */
public class World {
    private static World instance;
	private Dimension size;
	private Being[][] grid;
	private List<Being> entities;
	
	private int day = 1 ;

	/**
	 * Constructor
	 * @param width The width of the world
	 * @param height The height of the world
	 */
	public World(int width, int height) {
		entities = new ArrayList<Being>();
		size = new Dimension(width, height);
		grid = new Being[width][height];
		instance = this;
	}
	
	/**
	 * Returns the instance of World
	 * @return The World instance
	 */
	public static World getInstance() {
	    return instance;
	}

	/**
	 * Returns the Being at the <i>x,y</i> coordinates. Returns null if
	 * nothing here.
	 * 
	 * @param x
	 *            First coordinate of the entity
	 * @param y
	 *            Second coordinate of the entity
	 * @return The Being at this position.
	 */
	public Being getEntityAt(int x, int y) {
		return grid[x][y];
	}

	/**
	 * Returns a list of the neighbors for a given cell coordinates.
	 * 
	 * @param x
	 *            First coordinate of the source cell.
	 * @param y
	 *            Second coordinate fo the source cell.
	 * @return The list of the neighboring entities.
	 */
	public List<Being> getNeighbors(int x, int y) {
		List<Being> neighbors = new ArrayList<Being>();
		Point[] neighboringVectors = Neighbourhood.EIGHT;
		int length = neighboringVectors.length;
		for (int i = 0; i < length; i++) {
			Point neighboringVector = neighboringVectors[i];
			int x1 = x + neighboringVector.x, y1 = y + neighboringVector.y;
			if (isInGrid(x1, y1)) {
				Being entity = getEntityAt(x1, y1);
				if (entity != null) {
					neighbors.add(entity);
				}
			}
		}
		return neighbors;
	}

	/**
	 * Returns true if a point at given coordinates is is the grid or outside.
	 * @param x		The X coordinates of the point
	 * @param y		The Y coordinates of the point
	 * @return	If the point is in the grid.
	 */
	private boolean isInGrid(int x, int y) {
		return 0 <= x && x < grid.length && 0 <= y && y < grid[0].length;
	}

	/**
	 * Returns the list of the neighbors of a given entity.
	 * @param entity The entity to look around.
	 * @return The entity's neighbors.
	 */
	public List<Being> getNeighbors(Being entity) {
		return getNeighbors(entity.getPosX(), entity.getPosY());
	}

	/**
	 * String representation of the world.
	 * @return A string representing the world.
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		LogManager logM = LogManager.getInstance();
		
		sb.append("Day ").append(day).append("\n");
		int xMax = grid.length, yMax = grid[0].length;
		for (int y = -2; y <= yMax + 1; y++) {
			for (int x = -2; x <= xMax + 1; x++) {
			    // Ordonnee
				if (x == -2) {
					if (y <= -1 || y >= yMax)
						sb.append("   ");
					else {
						if(y<10) sb.append(' ').append(y).append(' ');
						else sb.append(' ').append(y);
					}
				} else if ((x == -1 && y == -2) || (x == xMax && y == -2))
					sb.append("   ");
				// Abscisse
				else if (y == -2 && x >= 0 && x < xMax) {
					if(x<10) sb.append(' ').append(x).append(' ');
					else sb.append(' ').append(x);
				}else if (x == -1 && y == -1)
					sb.append("  ╭");
				else if (x == xMax && y == -1)
					sb.append("──╮");
				else if (x == -1 && y == yMax)
					sb.append("  ╰");
				else if (x == xMax && y == yMax)
					sb.append("──╯");
				else if (y >= 0 && y < yMax && (x == -1 || x == xMax))
					sb.append("  │");
				else if (x >= 0 && x < xMax && (y == -1 || y == yMax))
					sb.append("───");

				if (x <= -1 || y <= -1 || x >= xMax || y >= yMax)
					continue;

				Being entity = getEntityAt(x, y);
				if (entity == null)
					sb.append("   ");
				else {
					char health = entity.getHealth().toReducedString();
					if (health == ' ')
						sb.append(' ');
					sb.append(entity.symbol);
					if (health != ' ')
						sb.append(health);

					sb.append(' ');
				}
			}
			if(!logM.isEmpty()) sb.append(logM.poll());
			sb.append("\n");
		}
		while(!logM.isEmpty()) {
		    sb.append("   ").append(logM.poll()).append('\n');
		}
		
		return sb.toString();
	}

	/**
	 * Gets the coordinates of a random empty cell
	 * 
	 * @author Loïc GAILLARD
	 * @return The Point of a random empty cell
	 */
	public Point getRandomEmptyCell() {
		Random r = new Random();
		int xMax = size.width, yMax = size.height;
		Point candidate = new Point(r.nextInt(xMax), r.nextInt(yMax));
		
		while(grid[candidate.x][candidate.y] != null) {
		    if((++candidate.x) > xMax-1) {
		        candidate.x = candidate.x%(xMax);
		        candidate.y = (++candidate.y)%(yMax);
		    }
		}
		return candidate;
	}

	/**
	 * Returns the number of cells in the world.
	 * @return The number of cells in the world.
	 */
	public int getCellsNum() {
		return size.height * size.width;
	}

	/**
	 * Add an entity in the world at the entity's position.
	 * @param entity The entity to add in the world.
	 */
	public void addEntity(Being entity) {
		grid[entity.getPosX()][entity.getPosY()] = entity;
		entities.add(entity);
	}

	/**
	 * Performs a logic update.
	 */
	public void tick() {
	    LogManager logM = LogManager.getInstance();

		// Perform internal ticking of the entities
		for (Being entity : entities) {
			entity.tick();
		}
		
		// Go across all the entities of the world
		for (Being entity : entities) {
			// Get everything that the entity can spread around
			for (Propagable p : entity.getPropagables()) {
				// Check that the entity is able to propagate it
				if (entity.isPropagating(p) ) {
					List<Being> neighbors = getNeighbors(entity);
					for (Being neighbor : neighbors) {
						
						if(!neighbor.canReceive(p)) {
							continue ;
						}
						
						//if (p instanceof Habit) sb.append("Previous value of Habits : "+neighbor.getHabits()+"\n");
						
						//sb.append("Habits : "+neighbor.getHabits()+"\n");
						// Attempt to propagate
						PropagationEvent event = p.tryToPropagateTo(entity,
								neighbor);
						
						if (event.isLogged()) {
							logM.log(event.getMessage());
						}
					}
				}
			}
		}

		// TODO : clean this if ok to remove.
		/*
		// Final result of the world tick :
		
		if (!somethingHappened) {
			ticksWithoutEvents++;
		} else {
			ticksWithoutEvents = 0; // reset the counter
		}

		// End the simulation after 5 days without activity
		if (ticksWithoutEvents >= 5) {
			simulationFinished = true;
		}*/
		
		day++;
	}

	/**
	 * Marks that something happened
	 * @return
	 */
	public boolean isFinished() {
		for(Being entity : entities){
			if( entity.getHealth().getType() != HealthState.Healthy
					&& entity.getHealth().getType() != HealthState.Dead) {
				return false ;
			}
		}
		return true ;
		//return simulationFinished;
	}
	
	/**
	 * Returns the list of the entities with a given health.
	 * @param health The health that the returned entities must have.
	 * @return The list of the entities having this health.
	 */
	public List<Being> getEntitiesWithHealth(int health){
		List<Being> result = new ArrayList<Being>();
		for(Being entity : entities){
			if (entity.getHealth().getType() == health) {
				result.add(entity);
			}
		}
		return result ;
	}

	/**
	 * Returns the size of the world.
	 * @return The size of the world.
	 */
	public Dimension getSize() {
		return size;
	}
}
