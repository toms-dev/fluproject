/**
 * Classe de tests de la classe Chicken .
 * @author Bénédicte Lagouge
 */

package simulation.Beings;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;

import simulation.beings.*;
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
