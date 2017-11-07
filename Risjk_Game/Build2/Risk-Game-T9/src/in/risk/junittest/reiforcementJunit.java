package in.risk.junittest;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import in.risk.utility.Player;
import in.risk.utility.RiskGame;

public class reiforcementJunit {

	private RiskGame riskgame;
	private Player player;
	
	@Before
	public void startUp() throws IOException{
		RiskGame riskgame = new RiskGame();
		Player player = new Player();
		riskgame.mapLoader("World3.map");
		riskgame.selectPlayers();
		riskgame.initialPlayer();
		riskgame.initialPlayerCountries();
		riskgame.distributeArmies();
		
	}
	
	@Test
	public void testReinforcementArmiesFromCountries() {
		System.out.println(riskgame.initialPlayerCountry);
		String playerName = riskgame.currentPlayer.getName();
		int actualNumberOfReinforcemtnArmiesFromCountries = riskgame.initialPlayerCountry.get(playerName).size();
		if(actualNumberOfReinforcemtnArmiesFromCountries < 9){
			int expectedNumberOfReinforcemtnArmiesFromCountries = 3;
		}else{
			int expectedNumberOfReinforcemtnArmiesFromCountries = actualNumberOfReinforcemtnArmiesFromCountries/3;
		}
		assertEquals(actualNumberOfReinforcemtnArmiesFromCountries, actualNumberOfReinforcemtnArmiesFromCountries);
	}
}
