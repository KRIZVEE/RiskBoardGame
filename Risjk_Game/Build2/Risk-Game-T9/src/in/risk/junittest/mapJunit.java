package in.risk.junittest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import in.risk.utility.RiskGame;

public class mapJunit {

	private RiskGame riskgame;
	@Before
	public void startUp(){
		RiskGame riskgame = new RiskGame();
		riskgame.mapLoader("World3.map");
	}
	
	@Test
	public void testAdjacency() {
		String adjacent1 = "Alaska";
		String adjacent2 = "Ontario";
		assertTrue(riskgame.adj.get("Alberta").contains(adjacent1));
		assertTrue(riskgame.adj.get("Alberta").contains(adjacent2));
	}
	
	@Test
	public void testCountryHasContinent(){
		assertNotNull(riskgame.countryContinent.get("Argentina"));
	}

}
