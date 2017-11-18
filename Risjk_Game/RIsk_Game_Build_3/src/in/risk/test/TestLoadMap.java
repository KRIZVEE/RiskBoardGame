package in.risk.test;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import in.risk.utility.LoadMap;
/**
 * THis class test load map class.
 * @author mohitrana
 *
 */
public class TestLoadMap {

	/**
	 * This method loads at startup.
	 */
	@BeforeClass
	public static void startup(){
		String path = "3D Cliff.map";
		LoadMap.loadContinent(path);
		LoadMap.loadCountries(path);
		LoadMap.getCountriesAdjacency();
		LoadMap.removeSpcaes();
	}
	
	/**
	 * This method is used to test load continent method. 
	 */
	@Test
	public void testLoadContinents() {
		String expected  = "bottom";
		assertTrue(LoadMap.continentFilterNew.contains(expected));
	}
	
	/**
	 * This method is used to test load countries method.
	 */
	@Test
	public void testLoadCountries(){
		String expected = "topleft";
		assertTrue(LoadMap.countryFilter.contains(expected));
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
		assertTrue(LoadMap.adj.get(expectedCountry).contains(expected));
		assertTrue(LoadMap.adj.get(expectedCountry).contains(expected1));
		assertTrue(LoadMap.adj.get(expectedCountry).contains(expected2));
		assertTrue(LoadMap.adj.get(expectedCountry).contains(expected3));
	}
	
	@AfterClass
	public static void tearDown(){
		LoadMap.countryFilter.clear();
		LoadMap.continentFilterNew.clear();
		LoadMap.adj.clear();
	}
}
