package Test.Simulation.Beings;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;
import Simulation.Beings.*
;public class AnimalTest {
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
