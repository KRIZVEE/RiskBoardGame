package in.risk.utility;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class testTerritory {
	Territory territory;
	String nameTerritory;

	@Before
	public void Before() {
		nameTerritory = "India";
		territory = new Territory(nameTerritory, 10, 20, "Asia");
	}

	@Test
	public void testSetNameTerritory() {
		territory.setNameTerritory(nameTerritory);
		assertEquals("India", territory.nameTerritory);
	}
}
