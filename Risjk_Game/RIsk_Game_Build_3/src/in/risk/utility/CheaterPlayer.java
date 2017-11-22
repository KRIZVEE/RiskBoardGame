package in.risk.utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CheaterPlayer implements RiskStrategy {
	
	public  HashMap<String, List<String>> playersCards = new HashMap<String, List<String>>();
	public  List<String> cardsInTheDeck = new ArrayList<String>();
	public  ArrayList<String> deck = new ArrayList<String>();
	public  ArrayList<String> cardType = new ArrayList<String>();
	public  int cardFlag = 1;

	public  String cardTypeA = "A";
	public  String cardTypeB = "B";
	public  String cardTypeC = "C";
	public  int cardInTheDeck;
	
	// variables for attack phase 
	
	public  HashMap<String, Integer> countriesArmiesObserver = new HashMap<String, Integer>();
	public  HashMap<String, ArrayList<String>> initialPlayerCountryObserver = new HashMap<String, ArrayList<String>>();

	public  String attackerCountry = "";
	public  String defenderCountry = "";

	public  int noOfArmiesFromCountries = 0;
	public  int noOfArmiesFromContinents = 0;
	public  int noOfArmiesFromCards = 0;
	public  int noOfAttackerDice = 0;
	public  int attacker, defender;
	public  int attackerDiceArray[];
	public  int defenderDiceArray[];

	public  int noOfDefenderDice;
	public  int flagCheckDice = 0;
	public  int indx = 0;// = value.
	public  int oldCOuntryListSize = 0;
	public  int newCOuntryListSize = 0;
	public  String newDefenderCountry = "";
	public  int updateArmyOfAttacker;
	public  int updateArmyOfDefender;
	public  int countNoOfAttack = 1;

	public  String from = "";
	public  String to = "";
	
	public static void getReinforcementArmies(PlayerToPlay playerName){
		System.out.println(StartUpPhase.countriesArmies.get(0));
		
	}

	public void placeReinforcementArmies(PlayerToPlay playerName) {
		
	}

	public void attackPhase(PlayerToPlay playerName) {
	
	}

	public void fortify(PlayerToPlay playerName) {
		
	}

}
