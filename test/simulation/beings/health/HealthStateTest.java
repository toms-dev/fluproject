/**
 * Classe de tests de la classe HealthState .
 * @author Bénédicte Lagouge
 */

package simulation.beings.health;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class HealthStateTest {

	private HealthState healthstateHealthy;
	private HealthState healthstateContagious;

	@Before
	public void before() {
		this.healthstateHealthy= HealthStateFactory.Healty();
		this.healthstateContagious=HealthStateFactory.Contagious();
	}
	@Test
	public void testHealthStateIntBooleanBoolean() {
		assertNotNull(healthstateHealthy);
	}

	@Test
	public void testHealthStateIntBooleanBooleanInt() {
		assertNotNull(healthstateContagious);
	}

	@Test
	public void testIsContagious() {
		assertEquals(false, healthstateHealthy.isContagious());
	}

	@Test
	public void testGetType() {
		assertEquals(0, healthstateHealthy.getType());
	}

	@Test
	public void testGetRemainingDays() {
		assertEquals(3, healthstateContagious.getRemainingDays());
	}

	@Test
	public void testMustBeUpdated() {
		assertEquals(false, healthstateHealthy.mustBeUpdated());
		assertEquals(false, healthstateContagious.mustBeUpdated());
	}

	@Test
	public void testTick() {
		healthstateContagious.tick();
		assertEquals(2, healthstateContagious.getRemainingDays());
	}

	@Test
	public void testToReducedString() {
	    assertEquals(' ', healthstateHealthy.toReducedString());
		assertEquals('*', healthstateContagious.toReducedString());
	}

}
