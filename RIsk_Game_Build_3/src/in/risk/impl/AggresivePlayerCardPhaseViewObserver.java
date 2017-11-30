package in.risk.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

//import in.risk.utility.Player;

//import in.risk.utility.RiskGame;

public class AggresivePlayerCardPhaseViewObserver implements Observer{

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
		System.out.println("@@@@@@@@@@@@@@@   INSIDE Aggresive Player@@@@@@@@ CARD@@@@@@ PHASE VIEW OBSERVER PATTERN   @@@@@@@@@@@@@@@@@@@@@");
		
		// 	Card Exchange View
		
		int s = ((AggresivePlayer) o).trackVariable;
		String name = (((AggresivePlayer)o).trackPlayer);
		
		int indexOfCardToBeGet = (int) (Math.random() * ((AggresivePlayer)o).cardsInTheDeck.size() - 0);
		if(s == 1){
			try {
				((AggresivePlayer)o).initialCardDistribution(1);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			try {
				((AggresivePlayer)o).initialCardDistribution(2);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		}
		try {
			((AggresivePlayer)o).placeCardsInTheDeck();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("hello " +((AggresivePlayer)o).playersCards);
		List<String> temp = new ArrayList<String>(((AggresivePlayer)o).playersCards.get(name));
		System.out.println(((AggresivePlayer)o).cardsInTheDeck);
		temp.add(((AggresivePlayer)o).cardsInTheDeck.get(indexOfCardToBeGet));
		System.out.println("temp " + temp);
		System.out.println("cards in the deck " + ((AggresivePlayer)o).cardsInTheDeck);
		((AggresivePlayer)o).playersCards.put(name, temp);
		System.out.println(" temp : " +temp);
		System.out.println(" playersCards :"+((AggresivePlayer)o).playersCards);
		System.out.println("Player : " + name + " received "+ ((AggresivePlayer)o).playersCards.get(name) +" card.");
//		temp.clear();
		((AggresivePlayer)o).cardsInTheDeck.remove(indexOfCardToBeGet);
		System.out.println("Player : " + name + " received "+ ((AggresivePlayer)o).playersCards.get(name) +" card.");
		
		return;
	
	}
}
