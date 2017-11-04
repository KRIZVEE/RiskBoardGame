package in.risk.utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

public class AttackObserver implements Observer {

	RiskGame risk1 = new RiskGame();
	
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		System.out.println("Obserever pattern starts: ");
		HashMap<String,ArrayList<String>> currentWorldCountries = ((RiskGame)o).getWorldDominance();
		System.out.println("Current number of countries owned by all players: " +currentWorldCountries);
		
	}
}
