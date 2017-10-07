package Risk;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Vector;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ContinentTest {

	/*
	 * @Test public void testAssertEquals() { assertEquals("ABC", "ABCD");// ,
	 * actuals); }
	 */
	// Continent continent;
	@BeforeClass
	public static void beforeClass() {
		System.out.println("At the beginnig of the class");
	}

	@AfterClass
	public static void afterClass() {
		System.out.println("At the end of the class");
	}

	@Before
	public void before() {
		System.out.println("Before every mentioned test method");
	}

	@After
	public void After() {
		System.out.println("After every mentioned test method");
	}

	@Test
	public void testgetName() {
		System.out.println("**testgetName**");

		String nm = "Asia";
		Vector<Integer> territories = new Vector<Integer>();
		int v = 5;
		Continent continent = new Continent(nm, territories, v);
		String expectedOutput = "Asia";
		String actualOutput = continent.getName();
		assertEquals(expectedOutput, actualOutput);
		// assertTrue(actualOutput);
	}
	/*
	 * public Vector<Integer> getTerritories(){ return territories; }
	 */

	@Test
	public void testgetTerritories() {
		assertTrue(true);
		String nm = "Asia";
		Vector<Integer> territories = new Vector<Integer>();
		int v = 5;
		Continent continent = new Continent(nm, territories, v);
		int expectedOutput = 5;
		Vector<Integer> actualOutput = continent.getTerritories();
		assertEquals(expectedOutput, actualOutput);
	}

	@Test
	public void testgetValue() {
		System.out.println("**testgetValue**");

		String nm = "Asia";
		Vector<Integer> territories = new Vector<Integer>();
		int v = 5;
		Continent continent = new Continent(nm, territories, v);
		int expectedOutput = 5;
		int actualOutput = continent.getValue();
		// assertTrue(true);
		assertTrue(expectedOutput == actualOutput);
		// assertEquals(expectedOutput, actualOutput);
	}

	/*
	 * * public boolean isTerritoryOf(Territory t){ return
	 * (territories.contains(t.getId())); }
	 */

	@Test
	public void testisTerritoryOf(Territory t) {
		String nm = "Asia";
		Vector<Integer> territories = new Vector<Integer>();
		int v = 5;
		Continent continent = new Continent(nm, territories, v);
		int expectedOutput = 5;
		int actualOutput = continent.getValue();
		// assertTrue(true);
		assertTrue(expectedOutput == actualOutput);
	}

	/*
	 * * public boolean isContinentCaptured(Player p){ Vector<Integer> t1 = new
	 * Vector<Integer>(); Vector<Territory> t2 = p.getOccupiedTerritories();
	 * 
	 * for (int c = 0; c < t2.size(); c++) t1.add(t2.elementAt(c).getId()); for
	 * (int i = 0; i < territories.size(); i++){
	 * if(!t1.contains(territories.elementAt(i))) return false; }//end for loop
	 * return true; }
	 */

	@Test
	public void testisContinentCaptured(Player p) {

	}

}

/*
 * public Vector<Integer> getTerritories(){ return territories; }
 * 
 * public int getValue(){ return value; }
 * 
 * public boolean isTerritoryOf(Territory t){ return
 * (territories.contains(t.getId())); }
 * 
 * public boolean isContinentCaptured(Player p){ Vector<Integer> t1 = new
 * Vector<Integer>(); Vector<Territory> t2 = p.getOccupiedTerritories();
 * 
 * for (int c = 0; c < t2.size(); c++) t1.add(t2.elementAt(c).getId()); for (int
 * i = 0; i < territories.size(); i++){
 * if(!t1.contains(territories.elementAt(i))) return false; }//end for loop
 * return true; }
 * 
 */
