package in.risk.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import in.risk.utility.RiskGame;
/**
 * This method tests add player functionality of risk game
 * @author Kashif Rizvee
 *
 */
public class TestRiskGame {

	RiskGame rg;
	
	@Before
	/**
	 * This method declares variable which will run before every test method 
	 */
	public void Before(){
		rg = new RiskGame();
	}
	
	@Test
	/**
	 * This method executes test case for adding player
	 */
	public void TestAddPlayer(){
		assertEquals("Kashif",rg.addPlayer("Kashif"));
	}
}
