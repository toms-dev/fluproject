/**
 * Classe de tests de la classe HealthState .
 * @author Bénédicte Lagouge
 */

package simulation.beings.health;

import static org.junit.Assert.*;
import org.junit.Before;

import org.junit.Test;

import simulation.beings.health.*;
public class HealthStateTest {

	private HealthState healthstate;
	private HealthState healthstate2;

	@Before
	public void before() {
		this.healthstate= new HealthState(0,false,true);
		this.healthstate2=new HealthState(1,false,true,2);
	}
	@Test
	public void testHealthStateIntBooleanBoolean() {
		assertNotNull(healthstate);
	}

	@Test
	public void testHealthStateIntBooleanBooleanInt() {
		assertNotNull(healthstate2);
	}

	@Test
	public void testIsContagious() {
		assertEquals(healthstate.isContagious(),false);
	}

	@Test
	public void testGetType() {
		assertEquals(healthstate.getType(),0);
	}

	@Test
	public void testGetRemainingDays() {
		assertEquals(healthstate2.getRemainingDays(),2);
	}

	@Test
	public void testMustBeUpdated() {
		assertEquals(healthstate.mustBeUpdated(),false);
		assertEquals(healthstate2.mustBeUpdated(),false);
	}

	@Test
	public void testTick() {
		healthstate2.tick();
		assertEquals(healthstate2.getRemainingDays(),1);
	}

	@Test
	public void testToReducedString() {
		assertEquals(healthstate2.toReducedString(),'!');
	}

}
