package in.risk.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import in.risk.impl.AggresivePlayer;
import in.risk.impl.HumanPlayer;
import in.risk.impl.MapLoader;
import in.risk.impl.StartUpPhase;

public class TestAggressivePlayer {

	static StartUpPhase obj = new StartUpPhase();
	static AggresivePlayer objAggressive = new AggresivePlayer();
	
	@BeforeClass
	public static void startUp() throws IOException{
		MapLoader.loadMap("3D Cliff Test.map");
		MapLoader.removeSpcaes();
		obj.addPlayerName("Aggressive");
		obj.addPlayerName("Random");
		obj.addPlayerName("Cheater");
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
		int actualValue = HumanPlayer.getArmiesFromCountries("Aggressive");
		assertEquals(expectedValue, actualValue);
	}
	
	/**
	 * This method is used to test get armies from continent method.
	 * @throws IOException 
	 */
	@Test
	public void testBGetArmiesFromContinent() throws IOException{
		int expectedValue = 0;
		int actualValue = HumanPlayer.getArmiesaFromContinet("Aggressive");
		assertEquals(expectedValue, actualValue);
	}
	
	/**
	 * This method is used to test the unique combination method.
	 * @throws IOException exception.
	 */
	@Test
	public void testCCheckUniqueCombination() throws IOException{
		int expectedValue = 0;
		int actualValue = HumanPlayer.checkUniqueCombination(0, "Aggressive");
		assertEquals(expectedValue, actualValue);
	}
	
	/**
	 * This method is used to test the discrete combination method.
	 * @throws IOException 
	 */
	@Test
	public void testCheckDiscreteCombination() throws IOException{
		int expectedValue = 0;
		int actualValue = HumanPlayer.checkDiscreteCombination("Aggressive");
		assertEquals(expectedValue, actualValue);
	}
	
	/**
	 * This method is used to test the placing reinforcement armies method.
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
	 * This method is used to test the testGetStrongestMethod in Aggressive player class.
	 * @throws IOException
	 */
	@Test
	public void testGetStrongestCountry() throws IOException{
		int totalNumberOfCountries = MapLoader.countryFilter.size();
		for (int i = 0; i < totalNumberOfCountries; i++) {
			obj.countriesArmies.put(MapLoader.countryFilter.get(i), 5);
		}
		String expectedValue = "toprightc";
		String actualValue = objAggressive.getStrongestCountry("Aggressive");
		assertEquals(expectedValue, actualValue);
	}
	
	/**
	 * This method test the adjacent of the strongest country.
	 */
	@Test
	public void testGetAdjacentToStrongest(){
		List<String> expected = MapLoader.adj.get("toprightc");
		System.out.println(obj.initialPlayerCountry.get("Aggressive"));
		System.out.println(objAggressive.getAdjacentOfStroongest("Aggressive", "secondright"));
	}
	@AfterClass
	public static void tearDown(){
		obj.countriesArmies.clear();
		MapLoader.countryFilter.clear();
		MapLoader.continentFilterNew.clear();
		StartUpPhase.players.removeAllElements();
	}

}
