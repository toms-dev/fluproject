

package Test.Simulation.Beings;
import Simulation.Beings.*;
import Simulation.Beings.Health.*;
import Simulation.Propagation.Illness.*;
import org.junit.*;
import static org.junit.Assert.*;

import org.junit.Test;

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

	@Test//GNEUH
	public void testGetHealth() {
		HealthState healthy = new HealthState(0,false,true);
		assertEquals(human.getHealth(),healthy);
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
		Illness illness= new Illness("H5N1",4);
	}
	
	
	@Test //GNEUUUUUUUUUUUH
	public void testUpdateToNextHealthState() {
		human.setRecovering();
		human.updateToNextHealthState();
		assertEquals(human.getHealth(),HealthStateFactory.Recovering());
	}

	@Test
	public void testTick() {

	}

	@Test
	public void testEndOfTick() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetHealthy() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetSick() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetContagious() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetRecovering() {
		fail("Not yet implemented");
	}

	@Test
	public void testDie() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsDead() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetIllness() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddVaccine() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsVaccinedAgainst() {
		fail("Not yet implemented");
	}

	@Test
	public void testHasVaccine() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetVaccines() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetRandomVaccine() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddHabit() {
		fail("Not yet implemented");
	}

	@Test
	public void testHasHabit() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetStuborn() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsStubborn() {
		fail("Not yet implemented");
	}

	@Test
	public void testToString() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddPropagable() {
		fail("Not yet implemented");
	}

	@Test
	public void testRemovePropagable() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetPropagables() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetResistanceBonuses() {
		fail("Not yet implemented");
	}

	@Test
	public void testCanReceive() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsPropagating() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetHabits() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetResistanceBase() {
		fail("Not yet implemented");
	}

}
