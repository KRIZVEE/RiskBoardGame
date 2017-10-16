package in.risk.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import in.risk.utility.Territory;

public class TestTerritory {
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
