/**
 * Classe de tests de la classe Pig .
 * @author Bénédicte Lagouge
 */
package Test.Simulation.Beings;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;
import Simulation.Beings.*;
public class PigTest {

	private Pig billy;
	@Before
	public void before(){
		billy = new Pig(0,0);
	}
	@Test
	public void testChicken() {
		assertNotNull(billy);
	}

}