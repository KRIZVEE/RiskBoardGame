package in.risk.utility;

import java.util.Observable;
import java.util.Observer;

public class PlaceArmiesObserver implements Observer {
	private RiskGame objRG = null;

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		// String result = (RiskGame(o)).inint
		System.out.println("trying to print this : " + ((RiskGame) o).initialPlayerCountry);
	}

}
