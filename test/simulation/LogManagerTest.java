/**
 * Classe de tests de la classe LogManager .
 * @author Bénédicte Lagouge
 */
package simulation;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;

import simulation.LogManager;

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
