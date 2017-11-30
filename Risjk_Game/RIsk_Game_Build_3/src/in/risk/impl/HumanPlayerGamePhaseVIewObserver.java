package in.risk.impl;

import java.util.Observable;
import java.util.Observer;

//import in.risk.utility.Player;

//import in.risk.utility.RiskGame;

public class HumanPlayerGamePhaseVIewObserver implements Observer{

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
		System.out.println("@@@@@@@@@@@@@@@INSIDE Game PHASE VIEW OBSERVER PATTERN@@@@@@@@@@@@@@@@@@@@@");
		
//		// start up phase
//		
//		System.out.println("Initially assigned countries to player");
//		System.out.println( ( (StartUpPhase) o).initialPlayerCountry);
//		
//		System.out.println("Each individual players placed number of armies to his/her own countries");
//		System.out.println(((StartUpPhase) o).countriesArmies);
		
		//reinforcement
		
		
		
		System.out.println("Armies assigned from Risk Rule based on country owning");
		System.out.println(((HumanPlayer) o).noOfReinforcementArmiesFromCountries);

		System.out.println("Armies assigned from Risk RUle based on continent owning");
		System.out.println(((HumanPlayer) o).noOfReinforcementArmiesFromContinents);

		System.out.println("Armies assigned from Risk RUle based on card owning");
		System.out.println(((HumanPlayer) o).noOfReinforcementArmiesFromCards);

		System.out.println("Updated Army List");
		System.out.println(((HumanPlayer) o).countriesArmiesObserver);
		
		
//		//attack
		
		System.out.println("Player chose below country to attack FROM : ");
		System.out.println(((HumanPlayer) o).attackerCountry);

		System.out.println("Player chose below country to attack TO : ");
		System.out.println(((HumanPlayer) o).defenderCountry);

		System.out.println("Attacker Dice Chosen Value");
		System.out.println(((HumanPlayer) o).noOfAttackerDice);

		System.out.println("Defender Dice Chosen Value");
		System.out.println(((HumanPlayer) o).noOfDefenderDice);

		System.out.println("Updated Country list and corresponding army values :");
		System.out.println(((HumanPlayer) o).initialPlayerCountryObserver + "	" + ((HumanPlayer) o).countriesArmiesObserver);
////
////		//System.out.println((RandomPlayer) o).initialPlayerCountryObserver + "   " + (HumanPlayer) o).countriesArmiesObserver);

		System.out.println("How many time attacked happened");
		System.out.println(((HumanPlayer) o).countNoOfAttack);
		
		//fortify
		
		System.out.println("chosen country FROM where to move army/armies :  ");
		System.out.println(((HumanPlayer) o).from);

		System.out.println("chosen country TO where to move army/armies :  ");
		System.out.println(((HumanPlayer) o).to);

		System.out.println("Updated Country list and corresponding army values :");
		System.out.println(((HumanPlayer) o).countriesArmiesObserver);
		
		return;
	
	}
}
