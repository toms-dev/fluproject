/**
 * Classe de tests de la classe World .
 * @author Bénédicte Lagouge
 */
package simulation;

import static org.junit.Assert.*;

import org.junit.Test;


import org.junit.Before;

import simulation.beings.*;
import simulation.propagation.Neighbourhood;
public class WorldTest {

	private World world;
	private Human entity;

	@Before
	public void before() {
		this.world=World.getInstance();
		world.setup(10, 10, Neighbourhood.EIGHT);
		this.entity= new Human(0,0);
	}
	@Test
	public void testWorld() {
		assertNotNull(world);
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
