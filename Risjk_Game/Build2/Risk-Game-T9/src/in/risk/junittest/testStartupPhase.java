package in.risk.junittest;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import in.risk.utility.Player;
import in.risk.utility.RiskGame;

public class testStartupPhase {

	private RiskGame riskgame;
	
	@Before
	public void startUp() throws IOException{
		RiskGame riskgame = new RiskGame();
		riskgame.mapLoader("World3.map");
		riskgame.selectPlayers();
		riskgame.initialPlayer();
		riskgame.initialPlayerCountries();
		riskgame.distributeArmies();
	}
	
	@Test
	public void testSelectPlayerPhase() {
		System.out.println(riskgame.currentPlayer.getName());
		int expectedValue = riskgame.noOfPlayers;
		int actualVaue = riskgame.players.size();
		assertEquals(expectedValue, actualVaue);
		assertFalse(expectedValue >= 6 || expectedValue <=2);
	}
	
	@Test
	public void testCurrentPlayer(){
	}

}
