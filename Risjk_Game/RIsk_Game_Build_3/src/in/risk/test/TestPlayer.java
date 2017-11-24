package in.risk.test;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import in.risk.impl.HumanPlayer;
import in.risk.impl.MapLoader;
import in.risk.impl.StartUpPhase;
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
		MapLoader.loadContinent("3D Cliff Test.map");
		MapLoader.loadCountries("3D Cliff Test.map");
		MapLoader.removeSpcaes();
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
		int expectedValue = 3;
		int actualValue = HumanPlayer.getArmiesFromCountries("Mohit");
		assertEquals(expectedValue, actualValue);
	}
	
	/**
	 * This method is used to test get armies from continent method.
	 * @throws IOException 
	 */
	@Test
	public void testBGetArmiesFromContinent() throws IOException{
		int expectedValue = 0;
		int actualValue = HumanPlayer.getArmiesaFromContinet("Mohit");
		assertEquals(expectedValue, actualValue);
	}
	
	/**
	 * This method is used to test the unique combination method.
	 * @throws IOException exception.
	 */
	@Test
	public void testCCheckUniqueCombination() throws IOException{
		int expectedValue = 0;
		int actualValue = HumanPlayer.checkUniqueCombination(0, "Mohit");
		assertEquals(expectedValue, actualValue);
	}
	
	/**
	 * This method is used to test the discrete combination method.
	 * @throws IOException 
	 */
	@Test
	public void testCheckDiscreteCombination() throws IOException{
		int expectedValue = 0;
		int actualValue = HumanPlayer.checkDiscreteCombination("Mohit");
		assertEquals(expectedValue, actualValue);
	}
	
	/**
	 * This method is used to testr the placing reinforcement armies method.
	 */
	@Test
	public void testPlaceReinforcementArmies() {
		StartUpPhase.countriesArmies.put("fifthright", 0);
		HumanPlayer.placeReinforcementArmies("fifthright", 4, StartUpPhase.players.get(0));
		int expectedValue = 4;
		int actualValue = StartUpPhase.countriesArmies.get("fifthright");
		assertEquals(expectedValue, actualValue);
	}
	
	@AfterClass
	public static void tearDown(){
		MapLoader.countryFilter.clear();
		MapLoader.continentFilterNew.clear();
		StartUpPhase.players.removeAllElements();
	}

}
