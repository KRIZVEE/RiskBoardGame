package in.risk.test;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import in.risk.impl.MapLoader;
/**
 * THis class test load map class.
 * @author mohitrana
 *
 */
public class TestMapLoader {

	/**
	 * This method loads at startup.
	 */
	@BeforeClass
	public static void startup(){
		String path = "3D Cliff Test.map";
		MapLoader.loadContinent(path);
		MapLoader.loadCountries(path);
		MapLoader.getCountriesAdjacency();
		MapLoader.removeSpcaes();
	}
	
	/**
	 * This method is used to test load continent method. 
	 */
	@Test
	public void testLoadContinents() {
		String expected  = "bottom";
		assertTrue(MapLoader.continentFilterNew.contains(expected));
	}
	
	/**
	 * This method is used to test load countries method.
	 */
	@Test
	public void testLoadCountries(){
		String expected = "topleft";
		assertTrue(MapLoader.countryFilter.contains(expected));
	}
	
	/**
	 * This method is used to test the adjacency method of load map.
	 */
	@Test
	public void testAdjacency(){
		String expectedCountry = "topleft";
		String expected = "topleftc";
		String expected1 = "fourthleft";
		String expected2 = "thirdleft";
		String expected3 = "ledgeleft";
		assertTrue(MapLoader.adj.get(expectedCountry).contains(expected));
		assertTrue(MapLoader.adj.get(expectedCountry).contains(expected1));
		assertTrue(MapLoader.adj.get(expectedCountry).contains(expected2));
		assertTrue(MapLoader.adj.get(expectedCountry).contains(expected3));
	}
	
	/**
	 * This method is used to test the unconnectedContinents method in MapLoader class.
	 */
	@Test
	public void testUnconnectedContinents(){
		boolean expectedValueForConnectedGraph = true;
		boolean expectedValueForUnconnectedGraph = false;
		MapLoader.loadMap("3D Cliff.map");
		boolean actualValueForConnectedContinent = MapLoader.unconnectedContinent();
		assertEquals(expectedValueForConnectedGraph, actualValueForConnectedContinent);
		MapLoader.loadMap("UnconnectedContinent.map");
		boolean actualValueForUnconnectedGraph = MapLoader.unconnectedContinent();
		assertEquals(expectedValueForUnconnectedGraph, actualValueForUnconnectedGraph);
	}
	
	@AfterClass
	public static void tearDown(){
		MapLoader.countryFilter.clear();
		MapLoader.continentFilterNew.clear();
		MapLoader.adj.clear();
	}
}
