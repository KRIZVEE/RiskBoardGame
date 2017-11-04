package in.risk.utility;

import java.util.Observable;
import java.util.Observer;

public class AttackObserver implements Observer {

	RiskGame risk1 = new RiskGame();
	
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		System.out.println("Old country list: " +risk1.oldCOuntryListSize);
		System.out.println("New country list: " +risk1.newCOuntryListSize);
		System.out.println("Attacked a country and conquered it !!!! WHoooooooAAAAAAA");
		
	}
}
