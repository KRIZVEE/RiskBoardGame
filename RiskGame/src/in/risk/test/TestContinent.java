package in.risk.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import in.risk.utility.Continent;
/**
 * This class contains test cases for testing continent details
 * @author Kashif Rizvee
 *
 */
public class TestContinent {

	Continent continent;
	String name;
	int value;

	@Before
	/*
	 * This method declare variable which will be executed at beginning of every test method
	 */
	public void Before() {
		name = "Asia";
		value = 15;
		continent = new Continent(name, value);
	}

	@Test
	/*
	 * This method is the test method where actual test case will run
	 */
	public void testSetValue() {
		continent.setValue(value);
		assertEquals(15, continent.value);
	}

}
