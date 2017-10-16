package in.risk.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import in.risk.utility.Continent;

public class TestContinent {

	Continent continent;
	String name;
	int value;

	@Before
	public void Before() {
		name = "Asia";
		value = 15;
		continent = new Continent(name, value);
	}

	@Test
	public void testSetValue() {
		continent.setValue(value);
		assertEquals(15, continent.value);
	}

}
