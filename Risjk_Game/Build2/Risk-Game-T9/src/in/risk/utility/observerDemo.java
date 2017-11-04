package in.risk.utility;

import java.io.IOException;

public class observerDemo extends Object {
	
	RiskGame rNew = new RiskGame();
	AttackObserver Attack = new AttackObserver();
	
	public observerDemo() 
	{
		rNew.addObserver(Attack);
	}
	
	public static void main(String []args) throws IOException {
		observerDemo od = new observerDemo();
		od.demo();
	}
	
	public void demo()throws IOException{
		rNew.startGame("C:\\Users\\mohit\\Desktop\\Risjk_Game\\World.map");
	
	}
	

};
