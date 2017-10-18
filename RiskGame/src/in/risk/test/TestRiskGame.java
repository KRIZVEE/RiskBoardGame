package in.risk.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import in.risk.utility.RiskGame;

public class TestRiskGame {

	RiskGame rg;
	
	@Before
	public void Before(){
		rg = new RiskGame();
	}
	
	@Test
	public void TestAddPlayer(){
		assertEquals("Kashif",rg.addPlayer("Kashif"));
	}
}
