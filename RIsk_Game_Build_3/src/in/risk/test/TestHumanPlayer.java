package in.risk.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Map;

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
public class TestHumanPlayer {

	static StartUpPhase obj = new StartUpPhase();
	static HumanPlayer objHuman = new HumanPlayer();
	
	/**
	 * This method loads at start.
	 * @throws IOException exception.
	 */
	@BeforeClass
	public static void startUp() throws IOException{
		MapLoader.loadMap("3D Cliff Test.map");
		MapLoader.removeSpcaes();
		obj.addPlayerName("Mohit");
		obj.addPlayerName("Rana");
		obj.addPlayerName("Preet");
		obj.initialPlayerCountry(1);
		obj.distributeArmies(3,1);
	}
	
	/**
	 * This method is used to test get armies from countries method.
	 * @throws IOException 
	 */
	@Test
	public void testGetArmiesFromCountries() throws IOException {
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
	 * @throws IOException 
	 */
	@Test
	public void testPlaceReinforcementArmies() throws IOException {
		StartUpPhase.countriesArmies.put("fifthright", 0);
		HumanPlayer.placeReinforcementArmies("fifthright", 4, StartUpPhase.players.get(0));
		int expectedValue = 4;
		int actualValue = StartUpPhase.countriesArmies.get("fifthright");
		assertEquals(expectedValue, actualValue);
	}

	/**
	 * This methos is used to test the testGetAdjacentOfAttackerCountrie method in human palyer class.
	 */
	@Test
	public void testGetAdjacentOfAttackerCountrie(){
		assertFalse(objHuman.getAdjacentOfAttackerCountrie("Mohit", "ledgecentre").contains("bottomleft"));
	}
	
	/**
	 * This methos is used to test testAdjacentCountriesToFortify methos in the human player class.
	 */
	@Test
	public void testAdjacentCountriesToFortify(){
		assertTrue(objHuman.testAdjacentCountriesToFortify("Mohit", "ledgecentre").contains("bottomleft"));
		assertFalse(objHuman.testAdjacentCountriesToFortify("Mohit", "ledgecentre").contains("ledgefront"));
	}
	
	/**
	 * This method is used to test AddFortifyArmies of human player class.
	 */
	@Test
	public void testAddFortifyArmies(){
		obj.countriesArmies.put("secondright", 3);
		objHuman.addFortifyArmies("secondright", 2);
		int expectedValue = 5;
		int actualValue = obj.countriesArmies.get("secondright");
		assertEquals(expectedValue, actualValue);
	}
	
	@Test
	public void testDeleteFortifyArmies(){
		obj.countriesArmies.put("thirdright", 3);
		objHuman.deleteFortifyArmies("thirdright", 2);
		int expectedValue = 1;
		int actualValue = obj.countriesArmies.get("thirdright");
		assertEquals(expectedValue, actualValue);
	}
	
	/**
	 * This method teardown each initalized value.
	 */
	@AfterClass
	public static void tearDown(){
		obj.countriesArmies.clear();
		MapLoader.countryFilter.clear();
		MapLoader.continentFilterNew.clear();
		StartUpPhase.players.removeAllElements();
	}

}
