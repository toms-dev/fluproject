/**
 * Classe de tests de la classe Duck .
 * @author Bénédicte Lagouge
 */
package Test.Simulation.Beings;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;
import Simulation.Beings.*;
public class DuckTest {

	private Duck donald;
	@Before
	public void before(){
		donald = new Duck(0,0);
	}
	@Test
	public void testChicken() {
		assertNotNull(donald);
	}

}