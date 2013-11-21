/**
 * Classe de tests de la classe HealthstateFactory .
 * @author Bénédicte Lagouge
 */
package simulation.beings.health;

import static org.junit.Assert.*;
import org.junit.Test;

public class HealthStateFactoryTest {

	@Test
	public void testHealty() {
assertNotNull(HealthStateFactory.Healty());
	}

	@Test
	public void testSick() {
		assertNotNull(HealthStateFactory.Sick());
	}

	@Test
	public void testContagious() {
		assertNotNull(HealthStateFactory.Contagious());
	}

	@Test
	public void testRecovering() {
		assertNotNull(HealthStateFactory.Recovering());
	}

	@Test
	public void testDead() {
		assertNotNull(HealthStateFactory.Dead());
	}

}
