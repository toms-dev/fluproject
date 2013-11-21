/**
 * Classe de tests de la classe LogManager .
 * @author Bénédicte Lagouge
 */
package Test.Simulation;

import static org.junit.Assert.*;
import Simulation.LogManager;

import org.junit.Test;
import org.junit.Before;

public class LogManagerTest {
	LogManager log;

	@Before
	public void before() {
		this.log = LogManager.getInstance();
	}
	@Test
	public void testGetInstance() {
		assertNotNull(LogManager.getInstance());
	}

	@Test
	public void testLog() {
		log.log("doudah");
		assertNotNull(log.poll());
	}

	@Test
	public void testPoll() {
		log.log("doudah");
		assertEquals(log.poll(),"doudah");
	}

	@Test
	public void testClear() {
		log.log("doudah");
		log.clear();
		assertNull(log.poll());
	}

}
