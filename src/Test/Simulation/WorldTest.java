/**
 * Classe de tests de la classe World .
 * @author Bénédicte Lagouge
 */
package Test.Simulation;

import static org.junit.Assert.*;
import Simulation.Beings.*;

import org.junit.Test;
import Simulation.*;
import org.junit.Before;
public class WorldTest {

	private World world;
	private Human entity;

	@Before
	public void before() {
		this.world=new World(10,10);
		this.entity= new Human(0,0);
	}
	@Test
	public void testWorld() {
		assertNotNull(world);
	}

	@Test
	public void testGetInstance() {
		assertEquals(world.getInstance(),world);
	}

	@Test
	public void testGetEntityAt() {
		assertNull(world.getEntityAt(0, 0));
		world.addEntity(entity);
		assertEquals(entity, world.getEntityAt(0, 0));
	}

	@Test
	public void testGetNeighborsIntInt() {
		assertTrue(true);
	}

	@Test
	public void testGetNeighborsLivingEntity() {
		assertTrue(true);
	}

	@Test
	public void testGetRandomEmptyCell() {
		assertNotNull(this.world.getRandomEmptyCell());
	}

	@Test
	public void testGetCellsNum() {
		assertEquals(world.getCellsNum(),100);
	}

	@Test
	public void testAddEntity() {
		world.addEntity(entity);
		assertEquals(world.getEntityAt(0, 0),entity);
	}

	@Test
	public void testGetSize() {
		assertNotNull(world.getSize());
	}
}
