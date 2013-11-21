/**
 * Classe de tests de la classe Chicken .
 * @author Bénédicte Lagouge
 */

package Test.Simulation.Beings;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;
import Simulation.Beings.*;
public class ChickenTest {

	private Chicken run;
	@Before
	public void before(){
		run = new Chicken(0,0);
	}
	@Test
	public void testChicken() {
		assertNotNull(run);
	}

}
