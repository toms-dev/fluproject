/**
 * Classe de tests de la classe ResistanceSet .
 * @author Bénédicte Lagouge
 */
package Test.Simulation.Progagation;

import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.Before;
import org.junit.After;

import org.junit.Test;
import Simulation.Propagation.*;
import Simulation.Propagation.Behaviors.*;
import Simulation.Propagation.Illness.*;
import Simulation.Beings.*;
public class ResistanceSetTest {

	Human human;
	Chicken chicken;
	ResistancesSet resistance;
	Habit habit1;
	Habit habit2;
	Collection<PropagableResistanceBonus> collection;
	Illness illness;
	@Before
	public void before(){
		this.resistance=new ResistancesSet(1.0);
		this.habit1 = new Habit("habitude",1,0.5,1);
		this.habit2 = new Habit("habitude2",2,1.0,1.0);
		this.illness = new Illness("intoxication aux junits",0);
		this.human=new Human(0,0);
		this.chicken=new Chicken(0,1);
	}
	
	@After
	public void after() {
		this.resistance=null;
		this.habit1=null;
		this.habit2=null;
	}
	
	@Test
	public void getResistanceTest() {
		assertNotNull(resistance.getResistance());
	}
	@Test
	public void testResistancesSet() {
		assertNotNull(resistance);
		assertNotNull(habit1);
	}

	@Test
	public void testAddResistance() {
		this.resistance.addResistance(habit1);
		assertEquals(habit1,resistance.getResistance().get(resistance.getResistance().size()-1));
	}
/*
	@Test
	public void testAddAllResistances() {
		collection.add((PropagableResistanceBonus)habit1);
		collection.add((PropagableResistanceBonus)habit2);
		this.resistance.addAllResistances(collection);
		assertEquals(habit1,resistance.getResistance().get(resistance.getResistance().size()-2));
		assertEquals(habit2,resistance.getResistance().get(resistance.getResistance().size()-1));
	}
*/

	@Test
	public void testGetProtectionSuccessMessage() {
		assertNotNull(resistance.getProtectionSuccessMessage(illness,chicken,human)); 
	}

}
