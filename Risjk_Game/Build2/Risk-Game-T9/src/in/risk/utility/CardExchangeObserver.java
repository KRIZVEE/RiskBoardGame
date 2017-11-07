package in.risk.utility;

import java.util.Observable;
import java.util.Observer;

public class CardExchangeObserver implements Observer {

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		System.out.println("i am inside card exchange observer update method");
		// System.out.println("i am in card exchange method " + ((RiskGame)
		// o).playersCards);// +
		// ((Player)
		System.out.println("i am in card exchange method " + ((Player) o).x);// +
		// o).getArmiesFromCards());

	}

}
