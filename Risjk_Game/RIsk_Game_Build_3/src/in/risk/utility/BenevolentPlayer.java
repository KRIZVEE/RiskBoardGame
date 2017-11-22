package in.risk.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Map.Entry;


/**
 * This class implements the Reinforcement, Attack and Fortify game phases.
 * @author Mohit Rana, Kashif Rizvee, Ishan Kansara, Charanpreet Singh
 *
 */
public class BenevolentPlayer implements Strategy {

	// variables for reinforce phase
	
	public static HashMap<String, List<String>> playersCards = new HashMap<String, List<String>>();
	public static List<String> cardsInTheDeck = new ArrayList<String>();
	public static ArrayList<String> deck = new ArrayList<String>();
	public static ArrayList<String> cardType = new ArrayList<String>();
	public static int cardFlag = 1;

	public static String cardTypeA = "A";
	public static String cardTypeB = "B";
	public static String cardTypeC = "C";
	public static int cardInTheDeck;
	
	// variables for attack phase 
	
	public static HashMap<String, Integer> countriesArmiesObserver = new HashMap<String, Integer>();
	public static HashMap<String, ArrayList<String>> initialPlayerCountryObserver = new HashMap<String, ArrayList<String>>();

	public static String attackerCountry = "";
	public static String defenderCountry = "";

	public static int noOfArmiesFromCountries = 0;
	public static int noOfArmiesFromContinents = 0;
	public static int noOfArmiesFromCards = 0;
	public static int noOfAttackerDice = 0;
	public static int attacker, defender;
	public static int attackerDiceArray[];
	public static int defenderDiceArray[];

	public static int noOfDefenderDice;
	public static int flagCheckDice = 0;
	public static int indx = 0;// = value.
	public static int oldCOuntryListSize = 0;
	public static int newCOuntryListSize = 0;
	public static String newDefenderCountry = "";
	public static int updateArmyOfAttacker;
	public static int updateArmyOfDefender;
	public static int countNoOfAttack = 1;
	
	// variables for fortify phase

	public static String from = "";
	public static String to = "";
	
	
	/**
	 * This method is used to get reinforcement armies from the countries player own.
	 * @param playerName used to specify the name of the current player
	 * @return true is all goes well.
	 */
	public int getArmiesFromCountries(String playerName){
		int noOfReinforcementArmiesForCountry = 0;
		if(StartUpPhase.initialPlayerCountry.get(playerName).size() < 9){
			noOfReinforcementArmiesForCountry = noOfReinforcementArmiesForCountry + 3;
		}else{
			noOfReinforcementArmiesForCountry = noOfReinforcementArmiesForCountry + StartUpPhase.initialPlayerCountry.get(playerName).size()/3;
		}
		System.out.println(playerName + " got " + noOfReinforcementArmiesForCountry + " armies from owned countries.");
		return noOfReinforcementArmiesForCountry;
	}

	/**
	 * This method is used to get reinforcement armies from owned continents.
	 * @param playerName used to specify the name of current player.
	 * @return true if everything goes well.
	 */
	public int getArmiesaFromContinet(String playerName){
		int noOfReinforcementArmiesForContinent = 0;
		ArrayList<Boolean> resultOfContinentCountry = new ArrayList<Boolean>();
		for (Entry<String, List<String>> entry : LoadMap.continentCountries.entrySet()) {
			String Key = entry.getKey();
			List<String> value = entry.getValue();
			for (int i = 0; i < value.size(); i++) {
				resultOfContinentCountry.add(StartUpPhase.initialPlayerCountry.get(playerName).contains(value.get(i)));
			}
			if (resultOfContinentCountry.contains(false)) {
				noOfReinforcementArmiesForContinent = noOfReinforcementArmiesForContinent + 0;
			} else if (resultOfContinentCountry.contains(true)) {
				noOfReinforcementArmiesForContinent = noOfReinforcementArmiesForContinent + LoadMap.continentValue.get(Key);
			}
			resultOfContinentCountry.clear();
		}
		System.out.println(playerName + " got " + noOfReinforcementArmiesForContinent + " from owned continents.");
		return noOfReinforcementArmiesForContinent;
	}

	/**
	 * This method is about trading of cards and getting armies in return.
	 * Armies added will be as per the game logic.
	 * @param playerName name of the player
	 * @return no of reinforcement armies from the cards
	 * @throws IOException exception
	 */
	public int getArmiesFromCards(String playerName) throws IOException {
		int noOfReinforcementArmiesForCards = 0;
		initialCardDistribution();
		int tempSize = playersCards.get(playerName).size();
		if (tempSize >= 5) {
			noOfReinforcementArmiesForCards = checkUniqueCombination(tempSize, playerName) + checkDiscreteCombination(playerName);
		} else {
			if(tempSize < 3){
				System.out.println("You dont have enough cards to play.");
			}else{
				String result;
				Scanner sc = new Scanner(System.in);
				System.out.println("Now you have less than 5 cards. Do you want to play cards now? Y/N");
				result = sc.nextLine();
				if (result.equals("Y")) {
					noOfReinforcementArmiesForCards = checkUniqueCombination(playersCards.get(playerName).size(), playerName) + checkDiscreteCombination(playerName);
				}
				sc.close();
			}
		}
		System.out.println(playerName + " got " + noOfReinforcementArmiesForCards + " reinforcement armies from trading the cards.");
		return noOfReinforcementArmiesForCards;
	}
	
	/**
	 * This method is used to distribute catds in the deck intially.
	 * @throws IOException exception
	 */
	public void initialCardDistribution() throws IOException {
		int size = StartUpPhase.players.size();
		for (int i = 0; i < size; i++) {
			playersCards.put(StartUpPhase.players.get(i).getName(), deck.subList(0, 0));
		}
	}

	/**
	 * This method is used to place cards in the deck of player initially.
	 * @return the deck
	 * @throws IOException exception.
	 */
	public List<String> placeCardsInTheDeck() throws IOException {
		cardType.add(cardTypeA);
		cardType.add(cardTypeB);
		cardType.add(cardTypeC);
		int j = 0;
		cardInTheDeck = LoadMap.countryFilter.size();
		for (int i = 0; i < cardInTheDeck; i++) {
			cardsInTheDeck.add(cardType.get(j));
			j++;
			if (j == 3) {
				j = 0;
			}
		}
		return cardsInTheDeck;
	}

	/**
	 * This method is used to check the combination of players cards and then give reinforcemtn armies.
	 * @param playerName name of the current player.
	 * @return reinforcement armies.
	 * @throws IOException exception
	 */
	public int checkDiscreteCombination(String playerName) throws IOException {
		initialCardDistribution();
		placeCardsInTheDeck();
		int noOfReinforcementArmiesForDiscrete = 0;
		List<Integer> tempListABCFirst = new ArrayList<Integer>();
		List<Integer> tempListABCSecond = new ArrayList<Integer>();
		int flagForA = 0;
		int flagForB = 0;
		int flagForC = 0;
		String value;
		for (int i = 0; i < playersCards.get(playerName).size(); i++) {
			value = playersCards.get(playerName).get(i);
			if (value.equals("A")) {
				if (flagForA <= 1) {
					if (flagForA == 0) {
						tempListABCFirst.add(i);
					} else if (flagForA == 1) {
						tempListABCSecond.add(i);
					}
					flagForA += 1;
				}
			} else if (value.equals("B")) {
				if (flagForB <= 1) {
					if (flagForB == 0) {
						tempListABCFirst.add(i);
					} else if (flagForB == 1) {
						tempListABCSecond.add(i);
					}
					flagForB += 1;
				}

			} else if (value.equals("C")) {
				if (flagForC <= 1) {
					if (flagForC == 0) {
						tempListABCFirst.add(i);
					} else if (flagForC == 1) {
						tempListABCSecond.add(i);
					}
					flagForC += 1;
				}

			}
		}
		if (tempListABCFirst.size() >= 3) {
			int firstIndexToRemove = tempListABCFirst.get(0);
			int secondIndexToRemove = tempListABCFirst.get(1);
			int thirdIndexToRemove = tempListABCFirst.get(2);
			cardsInTheDeck.add(playersCards.get(playerName).get(firstIndexToRemove));
			playersCards.get(playerName).remove(firstIndexToRemove);
			cardsInTheDeck.add(playersCards.get(playerName).get(secondIndexToRemove - 1));
			playersCards.get(playerName).remove(secondIndexToRemove - 1);
			cardsInTheDeck.add(playersCards.get(playerName).get(thirdIndexToRemove - 2));
			playersCards.get(playerName).remove(thirdIndexToRemove - 2);
			noOfReinforcementArmiesForDiscrete += 5 * cardFlag;
			cardFlag += 1;
		}
		if (tempListABCSecond.size() >= 3) {
			int fourthIndexToRemove = tempListABCSecond.get(0);
			int fifthIndexToRemove = tempListABCSecond.get(1);
			int sixthINdexToRemove = tempListABCSecond.get(2);
			cardsInTheDeck.add(playersCards.get(playerName).get(fourthIndexToRemove - 3));
			playersCards.get(playerName).remove(fourthIndexToRemove - 3);
			cardsInTheDeck.add(playersCards.get(playerName).get(fifthIndexToRemove - 4));
			playersCards.get(playerName).remove(fifthIndexToRemove - 4);
			cardsInTheDeck.add(playersCards.get(playerName).get(sixthINdexToRemove - 5));
			playersCards.get(playerName).remove(sixthINdexToRemove - 5);
			noOfReinforcementArmiesForDiscrete += 5 * cardFlag;
			cardFlag += 1;
		}
		return noOfReinforcementArmiesForDiscrete;
	}
	
	/**
	 * This method provide number of reinforcement armies on the basis of unique card combination.
	 * @param tempSize no of cards player have
	 * @param playerName name of the player 
	 * @return no of reinforcement armies
	 * @throws IOException exception.
	 */
	public int checkUniqueCombination(int tempSize, String playerName) throws IOException {
		int noOfReinforcementArmiesForUnique = 0;
		List<Integer> tempListA = new ArrayList<Integer>();
		List<Integer> tempListB = new ArrayList<Integer>();
		List<Integer> tempListC = new ArrayList<Integer>();
		for (int i = 0; i < tempSize; i++) {
			if (playersCards.get(playerName).get(i).contains("A")) {
				tempListA.add(i);
			}
		}
		for (int i = 0; i < tempSize; i++) {
			if (playersCards.get(playerName).get(i).contains("B")) {
				tempListB.add(i);
			}
		}
		for (int i = 0; i < tempSize; i++) {
			if (playersCards.get(playerName).get(i).contains("C")) {
				tempListC.add(i);
			}
		}
		if (tempListA.size() >= 3) {
			int firstIndexToRemove = tempListA.get(0);
			int secondIndexToRemove = tempListA.get(1);
			int thirdIndexToRemove = tempListA.get(2);
			cardsInTheDeck.add("A");
			cardsInTheDeck.add("A");
			cardsInTheDeck.add("A");
			playersCards.get(playerName).remove(firstIndexToRemove);
			playersCards.get(playerName).remove(secondIndexToRemove - 1);
			playersCards.get(playerName).remove(thirdIndexToRemove - 2);
			noOfReinforcementArmiesForUnique = noOfReinforcementArmiesForUnique + 5 * cardFlag;
			cardFlag += 1;
			tempListA.clear();
		} else if (tempListB.size() >= 3) {
			int firstIndexToRemove = tempListB.get(0);
			int secondIndexToRemove = tempListB.get(1);
			int thirdIndexToRemove = tempListB.get(2);
			cardsInTheDeck.add("B");
			cardsInTheDeck.add("B");
			cardsInTheDeck.add("B");
			playersCards.get(playerName).remove(firstIndexToRemove);
			playersCards.get(playerName).remove(secondIndexToRemove - 1);
			playersCards.get(playerName).remove(thirdIndexToRemove - 2);
			noOfReinforcementArmiesForUnique = noOfReinforcementArmiesForUnique + 5 * cardFlag;
			cardFlag += 1;
			tempListB.clear();
		} else if (tempListC.size() >= 3) {
			int firstIndexToRemove = tempListC.get(0);
			int secondIndexToRemove = tempListC.get(1);
			int thirdIndexToRemove = tempListC.get(2);
			cardsInTheDeck.add("C");
			cardsInTheDeck.add("C");
			cardsInTheDeck.add("C");
			playersCards.get(playerName).remove(firstIndexToRemove);
			playersCards.get(playerName).remove(secondIndexToRemove - 1);
			playersCards.get(playerName).remove(thirdIndexToRemove - 2);
			noOfReinforcementArmiesForUnique = noOfReinforcementArmiesForUnique + 5 * cardFlag;
			cardFlag += 1;
		}
		return noOfReinforcementArmiesForUnique;
	}

	/**
	 * This method is used to place reinforcement armies and then place them in the countries where player want to place them
	 * @param playerName name of the player.
	 * @throws IOException exception
	 */
	public void placeReinforcementArmies(PlayerToPlay playerName) throws IOException {
		//Scanner sc = new Scanner(System.in);
		String countryNameToEnterArmies;
		int noOfArmiesWantToPlace;
		int noOfReinforcementArmiesFromCountries = getArmiesFromCountries(playerName.getName());
		int noOfReinforcementArmiesFromContinents = getArmiesaFromContinet(playerName.getName());
		int noOfReinforcementArmiesFromCards = getArmiesFromCards(playerName.getName());
		
		int noOfRinforcementArmies = noOfReinforcementArmiesFromCards + noOfReinforcementArmiesFromContinents + noOfReinforcementArmiesFromCountries;
		System.out.println(playerName.getName() + " you have " + noOfRinforcementArmies + " number of reinforcement armies.");
		playerName.addArmies(noOfRinforcementArmies);
		
		
		//my code @ kashif, do changes here
		int iteratorForPlayerArmies = playerName.getArmies();
		System.out.println(playerName.getName() + " You have " + iteratorForPlayerArmies + " armies.");
		System.out.println("And you own " + StartUpPhase.initialPlayerCountry.get(playerName.getName()));
		
		System.out.println("country armies: for current player " + StartUpPhase.countriesArmies);
		String weakestCountry="";
		int armyOfWeakestCountry = 0;
		int updatedarmyOfWeakestCountry = 0;
		
		for (Entry<String, Integer> entry : StartUpPhase.countriesArmies.entrySet()) {
			String key = entry.getKey();
			Integer value = entry.getValue();
			
			if(StartUpPhase.initialPlayerCountry.get(playerName.getName()).contains(key)){
				if(armyOfWeakestCountry > value) {
					weakestCountry = key;
					armyOfWeakestCountry = value;
					updatedarmyOfWeakestCountry = value;
				}
				armyOfWeakestCountry = value;

			}
		}
		
		System.out.println("weakest country name is: " + weakestCountry);
		System.out.println("weakest country army is: " + updatedarmyOfWeakestCountry);
		
		countryNameToEnterArmies = weakestCountry;
		noOfArmiesWantToPlace = noOfRinforcementArmies;
		placeReinforcementArmies(countryNameToEnterArmies, noOfArmiesWantToPlace, playerName);
		fortifyPhase(playerName);
	}// end of reinforcement phase	

	/**
	 * This method is used to update the countries armies according to user inputed data.
	 * @param countryNameToEnterArmies Name of the country to add armies.
	 * @param noOfArmiesWantToPlace Number of armies player wants to add to the selected country.
	 * @param playerName Player who wants to add the armies.
	 * @return true if everything goes well.
	 */
	public boolean placeReinforcementArmies(String countryNameToEnterArmies, int noOfArmiesWantToPlace , PlayerToPlay playerName) {
		int initialAriesCountryOwn = StartUpPhase.countriesArmies.get(countryNameToEnterArmies);
		int finalArmiesCOuntryHave = initialAriesCountryOwn + noOfArmiesWantToPlace;
		StartUpPhase.countriesArmies.put(countryNameToEnterArmies, finalArmiesCOuntryHave);
		playerName.loosArmies(noOfArmiesWantToPlace);
		return true;
		
	}
	/**
	 * This method used to capture the attack phase information
	 * of each player.
	 * @param playerName 
	 * @throws IOException
	 */
	public void attackPhase(PlayerToPlay playerName)throws IOException {
		System.out.println("Attac Phase nothing to do with Benevolent Startegy");
	}// end of attack phase
		
	/**
	 * This method used to capture the fortify phase information
	 * of each player.
	 * @param currentPlayer
	 * @throws IOException
	 */
	public void fortifyPhase(PlayerToPlay currentPlayer) throws IOException {
		
		System.out.println(currentPlayer.getName());		
		System.out.println("You have these countries under your control "
				+ StartUpPhase.initialPlayerCountry.get(currentPlayer.getName()));
		
		//========strongest country to move armies FROM=============
		System.out.println("The name of strongest country from which you need to move armies is : ");		
		String from;		
		String strongestCountry="";
		int armyOfStrongestCountry = 0;
		int updatedarmyOfStrongestCountry = 0;
		
		for (Entry<String, Integer> entry : StartUpPhase.countriesArmies.entrySet()) {
			String key = entry.getKey();
			Integer value = entry.getValue();
			
			if(StartUpPhase.initialPlayerCountry.get(currentPlayer.getName()).contains(key)){
				if(armyOfStrongestCountry > value) {
					strongestCountry = key;
					armyOfStrongestCountry = value;
					updatedarmyOfStrongestCountry = value;
				}
				armyOfStrongestCountry = value;

			}
		}
		
		System.out.println("strongest country name is: " + strongestCountry);
		System.out.println("strongest country army is: " + updatedarmyOfStrongestCountry);
		
		from = strongestCountry;
		System.out.println("Strongest Country from army needs to displace: \"FROM\" " + from);		

		int tempCountrySize = StartUpPhase.initialPlayerCountry.get(currentPlayer.getName()).size();
		int tempAdjSize = LoadMap.adj.get(from).size();

		ArrayList<String> tempAdjCountryToWhichWeCanMOve = new ArrayList<String>();
		
		for (int i = 0; i < tempAdjSize; i++) {
			for (int j = 0; j < tempCountrySize; j++) {
				if (StartUpPhase.initialPlayerCountry.get(currentPlayer.getName()).get(j)
						.contains(LoadMap.adj.get(from).get(i))) {
					tempAdjCountryToWhichWeCanMOve.add(LoadMap.adj.get(from).get(i));
				}
			}
		}
		Scanner scto = new Scanner(System.in);
		System.out.println("The countries which your owned and adjacent to " + from
				+ " where you want to move your armies");
		
		
		//========weakest country to move armies TO=============

		
		
		String weakestCountry="";
		int armyOfWeakestCountry = 0;
		int updatedarmyOfWeakestCountry = 0;
		
		for (Entry<String, Integer> entry : StartUpPhase.countriesArmies.entrySet()) {
			String key = entry.getKey();
			Integer value = entry.getValue();
			
			if(StartUpPhase.initialPlayerCountry.get(currentPlayer.getName()).contains(key)){
				if(armyOfWeakestCountry > value) {
					weakestCountry = key;
					armyOfWeakestCountry = value;
					updatedarmyOfWeakestCountry = value;
				}
				armyOfStrongestCountry = value;

			}
		}
		
		System.out.println("weakest country name is: " + weakestCountry);
		System.out.println("weakest country army is: " + updatedarmyOfWeakestCountry);
		
		String to;
		to = weakestCountry;
		System.out.println("Weakest Country from army needs to displace: \"TO\" " + to);
		
		
		
//		String to;
		int numberOfArmiesToMove;
//		to = scto.nextLine();

		if (tempAdjCountryToWhichWeCanMOve.contains(to)) {
			System.out.println(currentPlayer.getName() + " you have " + StartUpPhase.countriesArmies.get(from)
					+ " armies on " + from + ".");
			
			System.out.println("The number of armies you want to move to " + "" + to + " " + "country.");
			
			//how many armies should be displaced from strongest country to weakest country
			numberOfArmiesToMove = updatedarmyOfStrongestCountry/2;// half armies moved from strongest to weakest
			if (StartUpPhase.countriesArmies.get(from) > 1) {
				int currentArmiesOfToCountry = StartUpPhase.countriesArmies.get(to);
				int newArmiesToAdd = currentArmiesOfToCountry + numberOfArmiesToMove;
				StartUpPhase.countriesArmies.put(to, newArmiesToAdd);
				int currentArmiesOfFromCountry = StartUpPhase.countriesArmies.get(from);
				int newArmiesToDelete = currentArmiesOfFromCountry - numberOfArmiesToMove;
				StartUpPhase.countriesArmies.put(from, newArmiesToDelete);
				countriesArmiesObserver.putAll(StartUpPhase.countriesArmies);
				System.out.println(from + " = " + StartUpPhase.countriesArmies.get(from));
				System.out.println(to + " = " + StartUpPhase.countriesArmies.get(to) + "\n");
				
				StartUpPhase.nextPlayer();
				placeReinforcementArmies(currentPlayer);
			}
			else{
				String result;
				System.out.println("You dont have sufficient number of armies to move from " + from);
				//automatic ends of fortify phase
				StartUpPhase.nextPlayer();
				placeReinforcementArmies(currentPlayer);				
			}
		}
	}//end of fortify phase
}


			
			
			
			
			
				