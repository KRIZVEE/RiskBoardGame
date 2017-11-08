package in.risk.junittest;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import in.risk.utility.RiskGame;

public class mapJunit {

	private RiskGame riskgame;
	@Before
	public void startUp() throws IOException{
		riskgame = new RiskGame();
		riskgame.startGame("World3.map");
	}
	
	@Test
	public void testAdjacency() {
	}
	
	@Test
	public void testCountryHasContinent(){
	}

}
