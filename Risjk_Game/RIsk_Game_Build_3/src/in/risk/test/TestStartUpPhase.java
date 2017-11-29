package in.risk.test;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import in.risk.impl.MapLoader;
import in.risk.impl.PlayerToPlay;
import in.risk.impl.StartUpPhase;
/**
 * This class is used to test the start up phase.
 * @author mohitrana
 *
 */
public class TestStartUpPhase {

	@BeforeClass
	public static void Startup() throws IOException{
		MapLoader.loadContinent("3D Cliff Test.map");
		MapLoader.removeSpcaes();
		StartUpPhase.addPlayerName("Mohit");
		StartUpPhase.addPlayerName("Rana");
	}
	
	/**
	 * This method is used to test nextPlayer method.
	 * @throws IOException exception.
	 */
	@Test
	public void testNextPlayer() throws IOException{
		StartUpPhase.nextPlayer(1);
		String expectedValue = "Rana";
		String actualValue = StartUpPhase.currentPlayer.getName();
		assertEquals(expectedValue, actualValue);
	}
	
	/**
	 * This method is used to test selectPlayer method.
	 * @throws IOException excpetion.
	 */
	@Test
	public void testSelectPlayer() throws IOException {
		String expectedValueForLessThan3players = "You cannot play the game with less than 3 players.";
		String expectedValueForMoreThan6players = "You cannot play the game with more than 6 players.";
		assertEquals(expectedValueForLessThan3players, StartUpPhase.selectPlayer(2));
		assertEquals(expectedValueForMoreThan6players, StartUpPhase.selectPlayer(7));
	}
	
	/**
	 * This method is used to test updateArmies method.
	 */
	@Test
	public void testUpdateArmies(){
		StartUpPhase.updateArmies(1, "topleft", StartUpPhase.currentPlayer);
		int expectedValue = 1;
		int actualValue = StartUpPhase.countriesArmies.get("topleft");
		assertEquals(expectedValue, actualValue);
	}
	
	/**
	 * This method is used to test addPlayer method.
	 * @throws IOException exception.
	 */
	@Test
	public void testAddPlayerName() throws IOException{
		StartUpPhase.countriesArmies.put("fifthright", 0);
		String expectedValue = "Preet";
		StartUpPhase.addPlayerName(expectedValue);
		String actualValue = StartUpPhase.players.get(2).getName();
		assertEquals(expectedValue, actualValue);
	}
	
	/**
	 * This method is used to test intialPlayerCountry method.
	 * @throws IOException
	 */
	@Test
	public void testInitialPlayerCountry() throws IOException{
		StartUpPhase.initialPlayerCountry(1);
		int actualValue = StartUpPhase.initialPlayerCountry.get("Mohit").size();
		int expectedValue = 7;
		System.out.println(actualValue);
		assertEquals(expectedValue, actualValue);
	}
	
	/**
	 * This method is used to test intialPlayer method.
	 * @throws IOException exception.
	 */
	@Test
	public void testIntialPlayer() throws IOException
	{
		StartUpPhase.initialPlayer();
		String expectedValue = "Mohit";
		String actualValue = StartUpPhase.currentPlayer.getName();
		assertEquals(expectedValue, actualValue);
	}
	
	/**
	 * This method is used to test distributeArmies method.
	 * @throws IOException exception
	 */
	@Test
	public void testDistributeArmies() throws IOException{
		StartUpPhase.distributeArmies(3,1);
		int expectedValue = 4;
		int actualValue = StartUpPhase.players.get(0).getArmies();
		assertEquals(expectedValue, actualValue);
	}
	
	@AfterClass
	public static void tearDown(){
		MapLoader.countryFilter.clear();
		MapLoader.continentFilterNew.clear();
		StartUpPhase.players.removeAllElements();
		StartUpPhase.initialPlayerCountry.clear();
	}
}
