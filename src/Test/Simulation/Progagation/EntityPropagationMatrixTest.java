package Test.Simulation.Progagation;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import Simulation.Propagation.*;
import Simulation.Beings.*;
public class EntityPropagationMatrixTest {

	EntityPropagationMatrix matrix;
	Human hannibal;
	Chicken run;

	@Before
	public void before() {
		matrix = new EntityPropagationMatrix();
		this.hannibal=new Human(0,0);
		this.run=new Chicken(1,1);
	}

	@After
	public void after() {
		matrix = null;
	}

	@Test
	public void testEntityPropagationMatrix() {
		assertNotNull(matrix);
	}

	@Test
	public void testPropagationMatrix() {
		assertNotNull((PropagationMatrix)matrix);
	}

	@Test
	public void testGetPropagation() {
		matrix.addPropagationLink(1,2,0.5);
		
	}

	@Test
	public void testAddPropagationLink() {
		assertTrue(true);
	}

	@Test
	public void testAddSourceGlobalRate() {
		assertTrue(true);
	}

	@Test
	public void testAddTargetGlobalRate() {
		assertTrue(true);
	}

	@Test
	public void testToString() {
		assertTrue(true);
	}

}
