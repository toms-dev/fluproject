package Test.Simulation;

import static org.junit.Assert.*;
import Simulation.Beings.*;

import org.junit.Test;
import Simulation.*;
import org.junit.Before;
public class WorldTest {

	private World world;
	Human entity;

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
		fail("Not yet implemented");
	}

	@Test
	public void testGetNeighborsLivingEntity() {
		fail("Not yet implemented");
	}

	@Test
	public void testToString() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetRandomEmptyCell() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetCellsNum() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddEntity() {
		fail("Not yet implemented");
	}

	@Test
	public void testTick() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsFinished() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetEntitiesWithHealth() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetSize() {
		fail("Not yet implemented");
	}

}
