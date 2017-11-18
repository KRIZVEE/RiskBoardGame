package in.risk.test;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import in.risk.utility.LoadMap;
import in.risk.utility.Player;
import in.risk.utility.StartUpPhase;
/**
 * This class test the Player class.
 * @author mohitrana
 *
 */
public class TestPlayer {

	/**
	 * This method loads at start.
	 * @throws IOException exception.
	 */
	@BeforeClass
	public static void startUp() throws IOException{
		LoadMap.loadContinent("3D Cliff.map");
		LoadMap.loadCountries("3D Cliff.map");
		LoadMap.removeSpcaes();
		StartUpPhase.addPlayerName("Mohit");
		StartUpPhase.addPlayerName("Rana");
		StartUpPhase.addPlayerName("Preet");
		StartUpPhase.initialPlayerCountry();
		StartUpPhase.distributeArmies(3);
	}
	
	/**
	 * This method is used to test get armies from countries method.
	 * @throws IOException 
	 */
	@Test
	public void testCGetArmiesFromCountries() throws IOException {
		int expectedValue = 2;
		int actualValue = Player.getArmiesFromCountries("Mohit");
		assertEquals(expectedValue, actualValue);
	}
	
	/**
	 * This method is used to test get armies from continent method.
	 * @throws IOException 
	 */
	@Test
	public void testBGetArmiesFromContinent() throws IOException{
		int expectedValue = 0;
		int actualValue = Player.getArmiesaFromContinet("Mohit");
		assertEquals(expectedValue, actualValue);
	}
	
	/**
	 * This method is used to test the unique combination method.
	 * @throws IOException exception.
	 */
	@Test
	public void testCCheckUniqueCombination() throws IOException{
		int expectedValue = 0;
		int actualValue = Player.checkUniqueCombination(0, "Mohit");
		assertEquals(expectedValue, actualValue);
	}
	
	/**
	 * This method is used to test the discrete combination method.
	 */
	@Test
	public void testCheckDiscreteCombination(){
		int expectedValue = 0;
		int actualValue = Player.checkDiscreteCombination("Mohit");
		assertEquals(expectedValue, actualValue);
	}
	
	@AfterClass
	public static void tearDown(){
		LoadMap.countryFilter.clear();
		LoadMap.continentFilterNew.clear();
		StartUpPhase.players.removeAllElements();
	}

}
