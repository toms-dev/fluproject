/**
 * Classe de tests de la classe Animal .
 * @author Bénédicte Lagouge
 */

package simulation.Beings;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;

import simulation.beings.*;
public class AnimalTest {
private Animal animal;

@Before
public void before() {
	animal = new Animal(2,0,0);
}
	@Test
	public void testAnimal() {
		assertNotNull(animal);
	}

}
