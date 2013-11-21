package Simulation.Configuration;

/**
 * This is a default SimulationConfiguration with some predefined values.
 * @author Tom GUILLERMIN
 *
 */
public class DefaultSimulationConfiguration extends SimulationConfiguration {
	public DefaultSimulationConfiguration() {
		setWorldSize(10,10);
		setPopulation(30,10,5,10);
	}
}
