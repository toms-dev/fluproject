package Simulation;
import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import Simulation.Beings.LivingEntity;
import Simulation.Beings.Health.HealthState;
import Simulation.Propagation.Neighbourhood;
import Simulation.Propagation.Propagable;
import Simulation.Propagation.PropagationEvent;

public class World {
    private static World instance;
	private Dimension size;
	private LivingEntity[][] grid;
	private List<LivingEntity> entities;
	private int ticksWithoutEvents;
	private boolean simulationFinished;
	
	private int day = 1 ;

	public World(int width, int height) {
		entities = new ArrayList<LivingEntity>();
		size = new Dimension(width, height);
		grid = new LivingEntity[width][height];
		ticksWithoutEvents = 0;
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
	 * Returns the LivingEntity at the <i>x,y</i> coordinates. Returns null if
	 * nothing here.
	 * 
	 * @param x
	 *            First coordinate of the entity
	 * @param y
	 *            Second coordinate of the entity
	 * @return The LivingEntity at this position.
	 */
	public LivingEntity getEntityAt(int x, int y) {
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
	public List<LivingEntity> getNeighbors(int x, int y) {
		List<LivingEntity> neighbors = new ArrayList<LivingEntity>();
		Point[] neighboringVectors = Neighbourhood.EIGHT;
		int length = neighboringVectors.length;
		for (int i = 0; i < length; i++) {
			Point neighboringVector = neighboringVectors[i];
			int x1 = x + neighboringVector.x, y1 = y + neighboringVector.y;
			if (isInGrid(x1, y1)) {
				LivingEntity entity = getEntityAt(x1, y1);
				if (entity != null) {
					neighbors.add(entity);
				}
			}
		}
		return neighbors;
	}

	private boolean isInGrid(int x, int y) {
		return 0 <= x && x < grid.length && 0 <= y && y < grid[0].length;
	}

	public List<LivingEntity> getNeighbors(LivingEntity entity) {
		return getNeighbors(entity.getPosX(), entity.getPosY());
	}

	public String toString(String log) {
		StringBuilder sb = new StringBuilder();
		sb.append("Day ").append(day).append("\n");
		int xMax = grid.length, yMax = grid[0].length;
		String[] lines = log.split("\n");
		for (int y = -2; y <= yMax + 1; y++) {
			for (int x = -2; x <= xMax + 1; x++) {
				// sb.append('|');
				if (x == -2) {
					if (y <= -1 || y >= yMax)
						sb.append("   ");
					else {
						sb.append(' ').append(y).append(' ');
					}
				} else if ((x == -1 && y == -2) || (x == xMax && y == -2))
					sb.append("   ");
				// Ordonnee
				// if (x == -2 && y >= 0 && y < yMax)
				// sb.append(' ').append(y).append(' ');
				// Abscisse
				else if (y == -2 && x >= 0 && x < xMax)
					sb.append(' ').append(x).append(' ');
				// else if ((x == -2 && y <= -1) || (x == xMax+1 && y > yMax) )
				// sb.append("BBB");
				else if (x == -1 && y == -1)
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

				LivingEntity entity = getEntityAt(x, y);
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
			if (y + 2 < lines.length) {
				sb.append(lines[y + 2]);
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	public Point getRandomEmptyCell() {
		Random r = new Random();
		Point candidate;
		// boolean valid = false;
		while (true) {
			candidate = new Point(r.nextInt(size.width), r.nextInt(size.height));
			if (grid[candidate.x][candidate.y] == null) {
				return candidate;
			}
		}
	}

	public int getCellsNum() {
		return size.height * size.width;
	}

	public void addEntity(LivingEntity entity) {
		grid[entity.getPosX()][entity.getPosY()] = entity;
		entities.add(entity);
	}

	/**
	 * Performs a
	 * 
	 * @return
	 */
	public String tick() {
		StringBuilder sb = new StringBuilder();

		boolean somethingHappened = false;

		// Perform internal ticking of the entities
		for (LivingEntity entity : entities) {
			if (entity.tick()) {
				somethingHappened = true;
			}
		}
		
		// Go across all the entities of the world
		for (LivingEntity entity : entities) {
			// Get everything that the entity can spread around
			for (Propagable p : entity.getPropagables()) {
				// Check that the entity is able to propagate it
				if (entity.isPropagating(p) ) {
					List<LivingEntity> neighbors = getNeighbors(entity);
					for (LivingEntity neighbor : neighbors) {
						
						if(!neighbor.canReceive(p)) {
							continue ;
						}
						
						//if (p instanceof Habit) sb.append("Previous value of Habits : "+neighbor.getHabits()+"\n");
						
						//sb.append("Habits : "+neighbor.getHabits()+"\n");
						// Attempt to propagate
						PropagationEvent event = p.tryToPropagateTo(entity,
								neighbor);
						// Feedback to world via the event data.
						if (event.isImportant()) {
							somethingHappened = true;
						}
						
						if (event.isLogged()) {
							sb.append(event.getMessage()).append("\n");
						}
					}
				}
			}
		}

		// Final result of the world tick :
		
		if (!somethingHappened) {
			ticksWithoutEvents++;
		} else {
			ticksWithoutEvents = 0; // reset the counter
		}

		// TODO : End the simulation if everybody's healthy or dead.
		// End the simulation after 5 days without activity
		if (ticksWithoutEvents >= 5) {
			simulationFinished = true;
		}
		
		day++;

		return sb.toString();
	}

	public boolean isFinished() {
		for(LivingEntity entity : entities){
			if( entity.getHealth().getType() != HealthState.Healthy
					&& entity.getHealth().getType() != HealthState.Dead) {
				return false ;
			}
		}
		return true ;
		//return simulationFinished;
	}
	
	public List<LivingEntity> getEntitiesWithHealth(int health){
		List<LivingEntity> result = new ArrayList<LivingEntity>();
		for(LivingEntity entity : entities){
			if (entity.getHealth().getType() == health) {
				result.add(entity);
			}
		}
		return result ;
	}

	public Dimension getSize() {
		return size;
	}
}
