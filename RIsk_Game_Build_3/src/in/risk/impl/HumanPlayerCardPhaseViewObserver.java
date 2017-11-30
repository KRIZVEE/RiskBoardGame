package in.risk.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

//import in.risk.utility.Player;

//import in.risk.utility.RiskGame;

public class HumanPlayerCardPhaseViewObserver implements Observer{

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
		System.out.println("@@@@@@@@@@@@@@@   INSIDE HUMAN Player@@@@@@@@ CARD@@@@@@ PHASE VIEW OBSERVER PATTERN   @@@@@@@@@@@@@@@@@@@@@");
		
		// 	Card Exchange View
		
		String t = ((HumanPlayer)o).trackPlayer;

			//	need to uncomment this after implementing observer pattern 
			int indexOfCardToBeGet = (int) (Math.random() * ((HumanPlayer)o).cardsInTheDeck.size() - 0);
			try {
				((HumanPlayer)o).initialCardDistribution();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				((HumanPlayer)o).placeCardsInTheDeck();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			List<String> temp = new ArrayList<String>(((HumanPlayer)o).playersCards.get(t));
			//List<String> temp = new ArrayList<String>();
			System.out.println(((HumanPlayer)o).cardsInTheDeck);
			temp.add(((HumanPlayer)o).cardsInTheDeck.get(indexOfCardToBeGet));
			System.out.println("temp " + temp);
			System.out.println("cards in the deck " + ((HumanPlayer)o).cardsInTheDeck);
			((HumanPlayer)o).playersCards.put(t, temp);
			System.out.println(" temp : " +temp);
			System.out.println(" playersCards :"+((HumanPlayer)o).playersCards);
			System.out.println("Player : " + t + " received "+ ((HumanPlayer)o).playersCards.get(t) +" card.");
//			temp.clear();
			HumanPlayer.cardsInTheDeck.remove(indexOfCardToBeGet);
			System.out.println("Player : " + t + " received "+ ((HumanPlayer)o).playersCards.get(t) +" card.");
			
			
		
		}
}
