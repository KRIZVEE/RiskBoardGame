package in.risk.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import in.risk.utility.Territory;
/**
 * This class contains test case for validating territory details
 * @author Kashif Rizvee
 *
 */
public class TestTerritory {
	Territory territory;
	String nameTerritory;

	@Before
	/**
	 * This method declares variable which will run before every test method
	 */
	public void Before() {
		nameTerritory = "India";
		territory = new Territory(nameTerritory);
	}

	@Test
	/**
	 * This method test territory details
	 */
	public void testSetNameTerritory() {
		territory.setNameTerritory(nameTerritory);
		assertEquals("India", territory.nameTerritory);
	}
}
