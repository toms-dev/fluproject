/**
 * Classe de tests de la classe Human .
 * @author Bénédicte Lagouge
 */

package simulation.Beings;
import org.junit.*;
import static org.junit.Assert.*;

import org.junit.Test;

import simulation.beings.*;
import simulation.propagation.illness.*;

public class HumanTest {
	private Human human;

	@Before
	public void before() {
		human = new Human(0,0);
	}

	@After
	public void after() {
		human = null;
	}
	@Test
	public void testHuman() {
		assertNotNull(human);
	}

	@Test
	public void testSetPosition() {
		human.setPosition(1, 2);
		assertEquals(human.getPosX(),1);
		assertEquals(human.getPosY(),2);
	}

	@Test
	public void testGetPosX() {
		assertEquals(human.getPosX(),0);
	}

	@Test
	public void testGetPosY(){
		assertEquals(human.getPosY(),0);
	}

	@Test
	public void testGetType() {
		assertEquals(human.getType(),1);
	}

	@Test
	public void testIsContagious() {
		assertEquals(human.isContagious(),false);
		human.setRecovering();
		assertEquals(human.isContagious(),false);
		//sick ?
		human.setContagious();
		assertEquals(human.isContagious(),true);
	}
	
	@Test
	public void testHasIllness() {
	    assertEquals(human.hasIllness(), false);
		Illness illness = new Illness("H5N1");
	    human.setSick(illness);
	    assertEquals(human.hasIllness(), true);
	}


}
