package in.risk.utility;

import java.io.IOException;

public class ObserverDemo extends Object {

	RiskGame rNew;// = new RiskGame();
	AttackObserver Attack;// = new AttackObserver();
	PlaceArmiesObserver placeArmiesObject;// = new PlaceArmiesObserver();

	public ObserverDemo() {
		placeArmiesObject = new PlaceArmiesObserver();
		rNew = new RiskGame();
		Attack = new AttackObserver();
		rNew.addObserver(placeArmiesObject);
		rNew.addObserver(Attack);
	}

	/*
	 * public static void main(String[] args) throws IOException { observerDemo
	 * od = new observerDemo(); od.demo();
	 * 
	 * }
	 */

	public void demo(String path) throws IOException {
		rNew.startGame(path);

	}

};
