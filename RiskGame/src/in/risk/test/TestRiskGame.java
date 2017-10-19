package in.risk.test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import in.risk.utility.RiskGame;

/**
 * This class tests different methods.</br>
 * 
 * @author Charanpreet Singh, Ishan Kansara, Kashif Rizvee, Mohit Rana
 * @version 1.0.0
 */
public class TestRiskGame {
	RiskGame rg;

	/**
	 * This methods initializes the different parameters before.
	 * 
	 * @throws IOException
	 */
	@Before
	public void Before() throws IOException {
		rg = new RiskGame();
	}

	/**
	 * This methods tests the add Player.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testAddPlayer() throws IOException {
		boolean value = rg.addPlayer("Player 0");
		assertEquals(value, true);

		value = rg.addPlayer("Player 1");
		assertEquals(value, true);

		value = rg.addPlayer("Player 2");
		assertEquals(value, true);

		value = rg.addPlayer("Player 3");
		assertEquals(value, true);

		value = rg.addPlayer("Player 4");
		assertEquals(value, true);

		value = rg.addPlayer("Player 5");
		assertEquals(value, true);

		// Failure case
		value = rg.addPlayer("Player 6");
		assertEquals(value, true);

		value = rg.addPlayer("Player 7");
		assertEquals(value, false);
	}

	/**
	 * This methods tests the PlaceReinforcementArmies method.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testPlaceReinforcementArmies() throws IOException {
		rg.addPlayer("Player 9");
		rg.initialPlayer();
		rg.placeReinforcementArmies("India", 23);
		// System.out.println("rg.beforeA:" + rg.beforeA + ", rg.currentA: " +
		// rg.currentA + ", rg.afterA:" + rg.afterA);
		assertEquals((rg.beforeA + rg.currentA), rg.afterA);
	}

	/*
	 * @Test public void testPlaceReinforcementArmies() throws IOException {
	 * rg.placeReinforcementArmies(); assertEquals(rg.beforeA - rg.currentA,
	 * rg.afterA); }
	 */
}
