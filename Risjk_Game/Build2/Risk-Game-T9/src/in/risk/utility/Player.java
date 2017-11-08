//line no 98 to line 119 being commented purposefully
//line no 35 being commented purposefully

package in.risk.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.Scanner;

import in.risk.gui.RiskInterface;

// team file//
public class Player extends Observable {

	RiskGame rg;

	public static HashMap<String, List<String>> playersCards = new HashMap<String, List<String>>();
	public static HashMap<String, Integer> countriesArmiesObserver = new HashMap<String, Integer>();
	public static HashMap<String, ArrayList<String>> initialPlayerCountryObserver = new HashMap<String, ArrayList<String>>();

	String attackerCountry = "";
	String defenderCountry = "";

	int noOfArmiesFromCountries = 0;
	int noOfArmiesFromContinents = 0;
	int noOfArmiesFromCards = 0;
	int noOfAttackerDice = 0;
	int attacker, defender;
	int attackerDiceArray[];
	int defenderDiceArray[];

	int noOfDefenderDice;
	int flagCheckDice = 0;
	int indx = 0;// = value.
	int oldCOuntryListSize = 0;
	int newCOuntryListSize = 0;
	String newDefenderCountry = "";
	int updateArmyOfAttacker;
	int updateArmyOfDefender;
	int countNoOfAttack = 0;

	String from = "";
	String to = "";
	static Scanner sc = new Scanner(System.in);

	/**
	 * This method executes sub method for reinforcement phase of the game
	 */
	public void reinforcementPhase() throws IOException {
		rg = new RiskGame();
		rg.loggingString("=====================Reinforment phase started=====================");
		placeReinforcementArmies();
	}

	/**
	 * This method places armies in their countries as per reinforcement phase
	 * of the game.
	 */
	public void placeReinforcementArmies() throws IOException {

		// Start Of counting reinforcement armies from continet value.
		// commented for time being
		getArmiesaFromContinet();
		noOfArmiesFromContinents = RiskGame.noOfReinforcementArmies;
		System.out.println("You have " + RiskGame.initialPlayerCountry.get(RiskGame.currentPlayer.getName()));
		rg.loggingString("You have " + RiskGame.initialPlayerCountry.get(RiskGame.currentPlayer.getName()));
		System.out.println("You got " + noOfArmiesFromContinents + " from you continets.");
		rg.loggingString("You got " + noOfArmiesFromContinents + " from you continets.");
		// end of counting reinforcement armies from continet value.

		// Start Of counting reinforcement armies from number of countries owned
		// by player.
		getArmiesFromCountries();
		noOfArmiesFromCountries = RiskGame.noOfReinforcementArmies - noOfArmiesFromContinents;
		System.out.println("You have " + RiskGame.initialPlayerCountry.get(RiskGame.currentPlayer.getName()).size()
				+ " no of countries.");
		rg.loggingString("You have " + RiskGame.initialPlayerCountry.get(RiskGame.currentPlayer.getName()).size()
				+ " no of countries.");
		System.out.println("Your got " + noOfArmiesFromCountries + " from your countris.");
		rg.loggingString("Your got " + noOfArmiesFromCountries + " from your countries.");
		// end of counting reinforcement armies from number of countries owned
		// by player.

		// Start of counting number of reinforcement armies on the basis of card
		// trade.

		RiskInterface objRI4 = new RiskInterface();
		objRI4.demoCardExchange();
		// getArmiesFromCards();
		noOfArmiesFromCards = RiskGame.noOfReinforcementArmies - (noOfArmiesFromCountries + noOfArmiesFromContinents);
		System.out.println("You got " + noOfArmiesFromCards + " armies from tradin cards");
		// End of counting number of reinforcement armies on the basis of card
		// trade.

		System.out.println("Cards in the deck" + RiskGame.cardsInTheDeck);
		System.out.println(RiskGame.currentPlayer.getName() + " has " + RiskGame.noOfReinforcementArmies
				+ " number of reinforcement armies");
		Scanner sc = new Scanner(System.in);
		// Scanner sc1 = new Scanner(System.in);
		String countryNameToEnterArmies;
		int noOfArmiesWantToPlace;

		RiskGame.beforeA = RiskGame.currentPlayer.getArmies();// 17
		int newSize = RiskGame.noOfReinforcementArmies;
		RiskGame.currentPlayer.addArmies(newSize);
		RiskGame.currentA = RiskGame.currentPlayer.getArmies();// 23
		while (RiskGame.currentPlayer.getArmies() > 0) {
			System.out.println(RiskGame.currentPlayer.getName() + " You have " + RiskGame.currentPlayer.getArmies()
					+ " armies." + "\n");
			System.out.println(
					"And you own " + RiskGame.initialPlayerCountry.get(RiskGame.currentPlayer.getName()) + "\n");
			System.out.println("Enter the name of country where you wan to place armies" + "\n");
			countryNameToEnterArmies = sc.nextLine();
			if (!RiskGame.initialPlayerCountry.get(RiskGame.currentPlayer.getName())
					.contains(countryNameToEnterArmies)) {
				System.out.println("You dont own this country" + "\n");
			} else {
				System.out.println("Enter thhe number of armies you want to add on " + countryNameToEnterArmies + "\n");
				noOfArmiesWantToPlace = sc.nextInt();// 10
				placeReinforcementArmies(countryNameToEnterArmies, noOfArmiesWantToPlace);
				// placeReinforcementArmies(countryNameToEnterArmies,
				// noOfArmiesWantToPlace);
				// gamePhase();
			}
		}

		countriesArmiesObserver.putAll(RiskGame.countriesArmies);
		System.out.println("countriesArmies :" + RiskGame.countriesArmies);
		System.out.println("countriesArmiesObserver : " + countriesArmiesObserver);
		setChanged();
		notifyObservers(this);
		// RiskGame objRGLocal = new RiskGame();
		RiskInterface objRILocalAttack = new RiskInterface();
		objRILocalAttack.demoAttackPhaseView();
		// attackPhase();
	}

	/**
	 * This method is used to get reinforcement armies from owned continents.
	 * @throws IOException 
	 */
	// commented for time being
	public void getArmiesaFromContinet() throws IOException {
		for (Entry<String, List<String>> entry : RiskGame.continentCountries.entrySet()) {
			String Key = entry.getKey();
			List<String> value = entry.getValue();
			// System.out.println(value);
			for (int i = 0; i < value.size(); i++) {
				RiskGame.resultOfContinentCountry.add(
						
						RiskGame.initialPlayerCountry.get(RiskGame.currentPlayer.getName()).contains(value.get(i)));
	
			}
			// System.out.println(initialPlayerCountry.get(currentPlayer.getName()));
			if (RiskGame.resultOfContinentCountry.contains(false)) {
				RiskGame.noOfReinforcementArmies = RiskGame.noOfReinforcementArmies + 0;
			} else if (RiskGame.resultOfContinentCountry.contains(true)) {
				RiskGame.noOfReinforcementArmies = RiskGame.noOfReinforcementArmies + RiskGame.continentValue.get(Key);
			}
			rg.loggingString("Number of reinforcement armies for player from continent: " +rg.noOfReinforcementArmies);
			RiskGame.resultOfContinentCountry.clear();
		}
	}

	/**
	 * This method is about trading of cards and getting armies in return.
	 * Armies added will be as per the game logic.
	 */
	public void getArmiesFromCards() throws IOException {

		// System.out.println(RiskGame.playersCards);
		// int tempSize =
		// playersCards.get(RiskGame.currentPlayer.getName()).size();
		//
		// System.out.println(RiskGame.playersCards.get(RiskGame.currentPlayer.getName()));
		// int tempSize =
		// RiskGame.playersCards.get(RiskGame.currentPlayer.getName()).size();

		System.out.println(playersCards.get(RiskGame.currentPlayer.getName()));
//		rg.loggingString("Cards with each player: " +playersCards.get(RiskGame.currentPlayer.getName()));
		int tempSize = playersCards.get(RiskGame.currentPlayer.getName()).size();

		// System.out.println(playersCards.get(RiskGame.currentPlayer.getName()));
		if (tempSize >= 5) {
			checkUniqueCombination(tempSize);
			checkDiscreteCombination();
			System.out.println(
					"==============i am going with observer pattern for card exchange view with card size > = 5==========");
			setChanged();
			notifyObservers(this);
		} else {
			String result;
			Scanner sc = new Scanner(System.in);
			System.out.println("Now you have less than 5 cards. Do you want to play cards now? Y/N");
			result = sc.nextLine();
			if (result.equals("Y")) {
				checkUniqueCombination(playersCards.get(RiskGame.currentPlayer.getName()).size());
				checkDiscreteCombination();
				System.out.println(
						"==============i am going with observer pattern for card exchange view with card size < = 5==========");
				setChanged();
				notifyObservers(this);
			}
		}
	}

	/**
	 * This method is used to get reinforcement armies from owned countries.
	 * @throws IOException 
	 */
	public void getArmiesFromCountries() throws IOException {
		
		int sizeOfCountriesRiskGame = RiskGame.initialPlayerCountry.get(RiskGame.currentPlayer.getName()).size();

		if (sizeOfCountriesRiskGame < 9) {
			
			RiskGame.noOfReinforcementArmies = RiskGame.noOfReinforcementArmies + 3;
		} else {
			RiskGame.noOfReinforcementArmies = RiskGame.noOfReinforcementArmies + sizeOfCountriesRiskGame / 3;
		}
		rg.loggingString("Reinforcement armies from country: " +rg.noOfReinforcementArmies);
	}

	/**
	 * This method is called in the placeREinforcementArmies to place
	 * reinforcement armies in the given country by the use.
	 */
	public static void placeReinforcementArmies(String countryNameToEnterArmies, int noOfArmiesWantToPlace)
			throws IOException {
		if (noOfArmiesWantToPlace > RiskGame.currentPlayer.getArmies()) {
			System.out.println("You don't have suffecient armies" + "\n");
		} else {
			int initialAriesCountryOwn = RiskGame.countriesArmies.get(countryNameToEnterArmies);
			int finalArmiesCOuntryHave = initialAriesCountryOwn + noOfArmiesWantToPlace;
			RiskGame.countriesArmies.put(countryNameToEnterArmies, finalArmiesCOuntryHave);
			System.out.println(noOfArmiesWantToPlace + "\n");
			RiskGame.currentPlayer.loosArmies(noOfArmiesWantToPlace);
			RiskGame.afterA = RiskGame.currentPlayer.getArmies();// 13
		}

	}

	public void placeCardsInTheDeck() throws IOException {
		
		rg = new RiskGame();
		
		RiskGame.cardType.add(RiskGame.cardTypeA);
		RiskGame.cardType.add(RiskGame.cardTypeB);
		RiskGame.cardType.add(RiskGame.cardTypeC);
		int j = 0;
		RiskGame.cardInTheDeck = RiskGame.countryFilter.size();
		for (int i = 0; i < RiskGame.cardInTheDeck; i++) {
			RiskGame.cardsInTheDeck.add(RiskGame.cardType.get(j));
			j++;
			if (j == 3) {
				j = 0;
			}
		}
		rg.loggingString("Total cards in the deck: " +rg.cardInTheDeck);
		rg.loggingString("Card types: " +rg.cardType);
	}

	// public static void placeCardsInTheDeck2() {
	// RiskGame.cardType.add(RiskGame.cardTypeA);
	// RiskGame.cardType.add(RiskGame.cardTypeB);
	// RiskGame.cardType.add(RiskGame.cardTypeC);
	// int j = 0;
	// RiskGame.cardInTheDeck = RiskGame.countryFilter.size();
	// for (int i = 0; i < RiskGame.cardInTheDeck; i++) {
	// RiskGame.cardsInTheDeck.add(RiskGame.cardType.get(j));
	// j++;
	// if (j == 3) {
	// j = 0;
	// }
	// }
	// }

	public void initialCardDistribution() throws IOException {
		int size = RiskGame.players.size();
		for (int i = 0; i < size; i++) {
			playersCards.put(RiskGame.players.get(i).getName(), RiskGame.deck.subList(0, 0));
		}
		rg.loggingString("Cards in the deck: " +rg.deck);
		rg.loggingString("Cards with each player: " +playersCards);
	}

	public static void checkUniqueCombination(int tempSize) throws IOException {
		List<Integer> tempListA = new ArrayList<Integer>();
		List<Integer> tempListB = new ArrayList<Integer>();
		List<Integer> tempListC = new ArrayList<Integer>();
		for (int i = 0; i < tempSize; i++) {
			if (playersCards.get(RiskGame.currentPlayer.getName()).get(i).contains("A")) {
				tempListA.add(i);
			}
		}
		for (int i = 0; i < tempSize; i++) {
			if (playersCards.get(RiskGame.currentPlayer.getName()).get(i).contains("B")) {
				tempListB.add(i);
			}
		}
		for (int i = 0; i < tempSize; i++) {
			if (playersCards.get(RiskGame.currentPlayer.getName()).get(i).contains("C")) {
				tempListC.add(i);
			}
		}
		if (tempListA.size() >= 3) {
			int firstIndexToRemove = tempListA.get(0);
			int secondIndexToRemove = tempListA.get(1);
			int thirdIndexToRemove = tempListA.get(2);
			RiskGame.cardsInTheDeck.add("A");
			RiskGame.cardsInTheDeck.add("A");
			RiskGame.cardsInTheDeck.add("A");
			playersCards.get(RiskGame.currentPlayer.getName()).remove(firstIndexToRemove);
			playersCards.get(RiskGame.currentPlayer.getName()).remove(secondIndexToRemove - 1);
			playersCards.get(RiskGame.currentPlayer.getName()).remove(thirdIndexToRemove - 2);
			RiskGame.noOfReinforcementArmies += 5 * RiskGame.cardFlag;
			RiskGame.cardFlag += 1;
			tempListA.clear();
			RiskInterface objRICE = new RiskInterface();
			objRICE.demoCardExchange();
			// getArmiesFromCards();
		} else if (tempListB.size() >= 3) {
			int firstIndexToRemove = tempListB.get(0);
			int secondIndexToRemove = tempListB.get(1);
			int thirdIndexToRemove = tempListB.get(2);
			RiskGame.cardsInTheDeck.add("B");
			RiskGame.cardsInTheDeck.add("B");
			RiskGame.cardsInTheDeck.add("B");
			playersCards.get(RiskGame.currentPlayer.getName()).remove(firstIndexToRemove);
			playersCards.get(RiskGame.currentPlayer.getName()).remove(secondIndexToRemove - 1);
			playersCards.get(RiskGame.currentPlayer.getName()).remove(thirdIndexToRemove - 2);
			RiskGame.noOfReinforcementArmies += 5 * RiskGame.cardFlag;
			RiskGame.cardFlag += 1;
			tempListB.clear();
			RiskInterface objRICE2 = new RiskInterface();
			objRICE2.demoCardExchange();
			// getArmiesFromCards();
		} else if (tempListC.size() >= 3) {
			int firstIndexToRemove = tempListC.get(0);
			int secondIndexToRemove = tempListC.get(1);
			int thirdIndexToRemove = tempListC.get(2);
			RiskGame.cardsInTheDeck.add("C");
			RiskGame.cardsInTheDeck.add("C");
			RiskGame.cardsInTheDeck.add("C");
			playersCards.get(RiskGame.currentPlayer.getName()).remove(firstIndexToRemove);
			playersCards.get(RiskGame.currentPlayer.getName()).remove(secondIndexToRemove - 1);
			playersCards.get(RiskGame.currentPlayer.getName()).remove(thirdIndexToRemove - 2);
			RiskGame.noOfReinforcementArmies += 5 * RiskGame.cardFlag;
			RiskGame.cardFlag += 1;
			tempListC.clear();

			RiskInterface objRICE3 = new RiskInterface();
			objRICE3.demoCardExchange();
			// getArmiesFromCards();
		}
	}

	public static void checkDiscreteCombination() {
		List<Integer> tempListABCFirst = new ArrayList<Integer>();
		List<Integer> tempListABCSecond = new ArrayList<Integer>();
		int flagForA = 0;
		int flagForB = 0;
		int flagForC = 0;
		String value;
		for (int i = 0; i < playersCards.get(RiskGame.currentPlayer.getName()).size(); i++) {
			value = playersCards.get(RiskGame.currentPlayer.getName()).get(i);
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
			RiskGame.cardsInTheDeck.add(playersCards.get(RiskGame.currentPlayer.getName()).get(firstIndexToRemove));
			playersCards.get(RiskGame.currentPlayer.getName()).remove(firstIndexToRemove);
			RiskGame.cardsInTheDeck
					.add(playersCards.get(RiskGame.currentPlayer.getName()).get(secondIndexToRemove - 1));
			playersCards.get(RiskGame.currentPlayer.getName()).remove(secondIndexToRemove - 1);
			RiskGame.cardsInTheDeck.add(playersCards.get(RiskGame.currentPlayer.getName()).get(thirdIndexToRemove - 2));
			playersCards.get(RiskGame.currentPlayer.getName()).remove(thirdIndexToRemove - 2);
			RiskGame.noOfReinforcementArmies += 5 * RiskGame.cardFlag;
			RiskGame.cardFlag += 1;
		}
		// i am having doubt in this whether copy paste done correctly
		if (tempListABCSecond.size() >= 3) {
			int fourthIndexToRemove = tempListABCSecond.get(0);
			int fifthIndexToRemove = tempListABCSecond.get(1);
			int sixthINdexToRemove = tempListABCSecond.get(2);
			RiskGame.cardsInTheDeck
					.add(playersCards.get(RiskGame.currentPlayer.getName()).get(fourthIndexToRemove - 3));
			playersCards.get(RiskGame.currentPlayer.getName()).remove(fourthIndexToRemove - 3);
			RiskGame.cardsInTheDeck.add(playersCards.get(RiskGame.currentPlayer.getName()).get(fifthIndexToRemove - 4));
			playersCards.get(RiskGame.currentPlayer.getName()).remove(fifthIndexToRemove - 4);
			RiskGame.cardsInTheDeck.add(playersCards.get(RiskGame.currentPlayer.getName()).get(sixthINdexToRemove - 5));
			playersCards.get(RiskGame.currentPlayer.getName()).remove(sixthINdexToRemove - 5);
			RiskGame.noOfReinforcementArmies += 5 * RiskGame.cardFlag;
			RiskGame.cardFlag += 1;
		}
	}

	public void attackPhase() throws IOException {
		// noOfAttackerDice;
		// attacker = 0; defender = 0;
		// attackerDiceArray[];
		// defenderDiceArray[];
		// attackerCountry;
		// int noOfDefenderDice;
		// defenderCountry;
		// flagCheckDice = 0;
		// int indx = 0;// = value.
		// int oldCOuntryListSize = 0;
		// int newCOuntryListSize = 0;
		// String newDefenderCountry = "";
		// int updateArmyOfAttacker;
		// int updateArmyOfDefender;

		String attackTurnOn = "hello";

		System.out.println(RiskGame.initialPlayerCountry);

		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

		// Scanner sc1 = new Scanner(System.in);
		// Scanner sc2 = new Scanner(System.in);

		/*
		 * Alaska,70,126,South America,Alberta Alberta,147,163,North
		 * America,Alaska,Ontario Greenland,324,76,South
		 * America,Ontario,Quebec,Iceland Ontario,199,167,North
		 * America,Alberta,Greenland,Quebec Quebec,253,166,North
		 * America,Greenland,Ontario
		 * 
		 * Venezuala,259,303,South America,Central America,Peru,Brazil
		 * Peru,262,349,South America,Venezuala,Brazil,Argentina
		 * Argentina,263,407,South America,Peru,Brazil Brazil,308,337,South
		 * America,Venezuala,Peru,Argentina
		 */

		// RiskGame.countriesArmies.put("India", 3);
		// RiskGame.countriesArmies.put("China", 1);

		// countriesArmies.put("Ontario", 2);
		// countriesArmies.put("Quebec", 2);
		// countriesArmies.put("Venezuala", 1);
		RiskGame.countriesArmies.put("Peru", 1);
		// countriesArmies.put("Argentina", 1);
		// RiskGame.countriesArmies.put("Brazil", 4);
		// countriesArmies.put("Alberta", 2);

		System.out.println("Initailly player country list before any attack : "
				+ RiskGame.initialPlayerCountry.get(RiskGame.currentPlayer.getName()));
		System.out.println("Initailly player country list with initial army : "
				+ RiskGame.countriesArmies.get(RiskGame.currentPlayer.getName()));

		System.out.println("Current Player : " + RiskGame.currentPlayer.getName());
		oldCOuntryListSize = RiskGame.initialPlayerCountry.get(RiskGame.currentPlayer.getName()).size();
		System.out.println("===========oldCOuntryListSize :========== " + oldCOuntryListSize);
		System.out.println("Current Player owning ciuntries : "
				+ RiskGame.initialPlayerCountry.get(RiskGame.currentPlayer.getName()));
		System.out.println("Please enter the name of country from where you want to attack");
		attackerCountry = input.readLine();
		// countriesArmies.put(attackerCountry, 4);

		System.out.println("No of armies in the attacker country : " + RiskGame.countriesArmies.get(attackerCountry));
		// HashMap<String, List<String>> attckerCountryAdjacent = new
		// HashMap<String, List<String>>();
		List<String> attackerCountryAdjacent = new ArrayList<String>();
		int size = RiskGame.initialPlayerCountry.get(RiskGame.currentPlayer.getName()).size();
		// System.out.println(initialPlayerCountry.get(currentPlayer.getName()).size());
		int size2 = RiskGame.adj.get(attackerCountry).size();
		for (int i = 0; i < size2; i++) {
			if (RiskGame.initialPlayerCountry.get(RiskGame.currentPlayer.getName())
					.contains(RiskGame.adj.get(attackerCountry).get(i))) {
				continue;
			} else {
				attackerCountryAdjacent.add(RiskGame.adj.get(attackerCountry).get(i));
			}
		}

		// for (Entry<String, List<String>> entry : adj.entrySet()) {
		// String key = entry.getKey();
		// List<String> value = entry.getValue();
		//
		// System.out.println("Valu is " + value);
		// // if (key.equals(attackerCountry)) {
		// // for (int i = 0; i < value.size(); i++) {
		// // // System.out.println(attackerCountry.contains(value.get(i)));
		// // if (key.contains(value.get(i)))
		// // continue;
		// // else
		// // attackerCountryAdjacent.add(value.get(i));
		// //
		// // }
		// // }
		//
		// }

		// System.out.println("Based on this country name, you can attack to
		// corresponding countries only : "
		// + adj.get(attackerCountry)); // need to work here
		System.out.println("Based on this country name, you can attack to corresponding countries only : "
				+ attackerCountryAdjacent); // need to work here
		System.out.println("Please enter the name of country, to which you want to attack");
		defenderCountry = input.readLine();
		System.out.println("No of armies in the defender country : " + RiskGame.countriesArmies.get(defenderCountry));

		// countriesArmies.put(defenderCountry, 1);

		if (RiskGame.countriesArmies.get(attackerCountry) >= 2) {
			System.out.println("As, You have minimum of 2 armies, you can attack");

			if (RiskGame.countriesArmies.get(attackerCountry) > 3) {
				System.out.println("Attacker Country Player, can opt among 1, 2 or 3 dice to be rolled");
				noOfAttackerDice = sc.nextInt();
			} else if (RiskGame.countriesArmies.get(attackerCountry) == 3) {
				// flagCheckDice = 1;
				System.out.println("Attacker Country Player, can opt among 1 or 2 dice to be rolled");
				noOfAttackerDice = sc.nextInt();
			} else if (RiskGame.countriesArmies.get(attackerCountry) == 2) {
				System.out.println(
						"Attacker Country Player, can have only 1 dice to roll, as you have only 2 army on country : "
								+ attackerCountry);
				noOfAttackerDice = 1;
			}
			if (noOfAttackerDice == 2 || noOfAttackerDice == 3)
				flagCheckDice = 1;

			attackerDiceArray = new int[noOfAttackerDice];

			for (int i = 0; i < attackerDiceArray.length; i++) {
				attackerDiceArray[i] = RiskGame.randomNumberGenerator();
			}

			if (RiskGame.countriesArmies.get(defenderCountry) >= 2) {

				System.out.println("Defender Country Player, can opt among 1 or 2 dice to roll");
				noOfDefenderDice = sc.nextInt();

			} else {
				System.out.println(
						"Defender Country Player, can have only 1 dice to roll, as you have only 1 army on country : "
								+ defenderCountry);
				noOfDefenderDice = 1;
			}

			defenderDiceArray = new int[noOfDefenderDice];

			for (int i = 0; i < defenderDiceArray.length; i++) {
				defenderDiceArray[i] = RiskGame.randomNumberGenerator();
			}

			System.out.println("Attacker Dice value are as follow : ");

			Arrays.sort(attackerDiceArray);
			for (int i = 0; i < attackerDiceArray.length; i++) {
				System.out.println("Attacker dice for position " + (i + 1) + " is " + " " + attackerDiceArray[i]);
			}
			System.out.println("");
			Arrays.sort(defenderDiceArray);

			System.out.println("Defender Dice value are as follow : ");

			for (int i = 0; i < defenderDiceArray.length; i++) {
				System.out.println("Defender dice for position " + (i + 1) + " is " + " " + defenderDiceArray[i]);
			}
			System.out.println("");

			System.out.println(
					"Number of armies in Attacker Country is : " + RiskGame.countriesArmies.get(attackerCountry));
			System.out.println(
					"Number of armies in Defender Country is : " + RiskGame.countriesArmies.get(defenderCountry));

			if (noOfDefenderDice == 1) {

				if (attackerDiceArray[attackerDiceArray.length - 1] > defenderDiceArray[defenderDiceArray.length - 1]) {
					System.out.println(" Defender country loose 1 army");
					updateArmyOfDefender = RiskGame.countriesArmies.get(defenderCountry) - 1;
					if (updateArmyOfDefender == 0) {

						for (Entry<String, ArrayList<String>> entry : RiskGame.initialPlayerCountry.entrySet()) {
							String key = entry.getKey();
							ArrayList<String> value = entry.getValue();

							if ((RiskGame.initialPlayerCountry.get(key).contains(defenderCountry))
									&& key != RiskGame.currentPlayer.getName()) {

								for (int i = 0; i < value.size(); i++) {
									if (value.get(i).equals(defenderCountry)) {
										indx = i;
										break;
									}
								}
								entry.getValue().remove(indx);
								RiskGame.initialPlayerCountry.get(RiskGame.currentPlayer.getName())
										.add(defenderCountry);

								newCOuntryListSize = RiskGame.initialPlayerCountry.get(RiskGame.currentPlayer.getName())
										.size();
							}
						}

						RiskGame.countriesArmies.put(defenderCountry, noOfAttackerDice);

					} else {
						RiskGame.countriesArmies.put(defenderCountry, updateArmyOfDefender);
					}
					System.out.println("new list of initial player country" + RiskGame.initialPlayerCountry);
					System.out.println("Initailly player country list with initial army : " + RiskGame.countriesArmies);

				} else {//// DCD(0) > ACD(0)// for defender dice == 1
					System.out.println(" Attacker country loose 1 army");
					updateArmyOfAttacker = RiskGame.countriesArmies.get(attackerCountry) - 1;

					RiskGame.countriesArmies.put(attackerCountry, updateArmyOfAttacker);

					System.out.println("new list of initial player country" + RiskGame.initialPlayerCountry);
					System.out.println("Initailly player country list with initial army : " + RiskGame.countriesArmies);
				}
			} // end of defenderdice ==1

			else {

				if (attackerDiceArray[attackerDiceArray.length - 1] > defenderDiceArray[defenderDiceArray.length - 1]) {
					System.out.println(" Defender country loose 1 army");
					updateArmyOfDefender = RiskGame.countriesArmies.get(defenderCountry) - 1;
					RiskGame.countriesArmies.put(defenderCountry, updateArmyOfDefender);

					if (attackerDiceArray[attackerDiceArray.length - 2] > defenderDiceArray[defenderDiceArray.length
							- 2]) {
						System.out.println(" Defender country loose 1 more army");
						updateArmyOfDefender = updateArmyOfDefender - 1;
						RiskGame.countriesArmies.put(defenderCountry, updateArmyOfDefender);

						if (updateArmyOfDefender == 0) {

							for (Entry<String, ArrayList<String>> entry : RiskGame.initialPlayerCountry.entrySet()) {
								String key = entry.getKey();
								ArrayList<String> value = entry.getValue();

								if ((RiskGame.initialPlayerCountry.get(key).contains(defenderCountry))
										&& key != RiskGame.currentPlayer.getName()) {

									for (int i = 0; i < value.size(); i++) {
										if (value.get(i).equals(defenderCountry)) {
											indx = i;
											break;
										}
									}
									entry.getValue().remove(indx);
									RiskGame.initialPlayerCountry.get(RiskGame.currentPlayer.getName())
											.add(defenderCountry);

									newCOuntryListSize = RiskGame.initialPlayerCountry
											.get(RiskGame.currentPlayer.getName()).size();
								}
							}

							RiskGame.countriesArmies.put(defenderCountry, noOfAttackerDice);

						} else {
							RiskGame.countriesArmies.put(defenderCountry, updateArmyOfDefender);
						}
						System.out.println("new list of initial player country" + RiskGame.initialPlayerCountry);
						System.out.println(
								"Initailly player country list with initial army : " + RiskGame.countriesArmies);
					} else {
						System.out.println(" Attacker country loose 1 army");
						updateArmyOfAttacker = RiskGame.countriesArmies.get(attackerCountry) - 1;

						RiskGame.countriesArmies.put(attackerCountry, updateArmyOfAttacker);

						System.out.println("new list of initial player country" + RiskGame.initialPlayerCountry);
						System.out.println(
								"Initailly player country list with initial army : " + RiskGame.countriesArmies);

					}

				} else {
					if (attackerDiceArray[attackerDiceArray.length - 1] <= defenderDiceArray[defenderDiceArray.length
							- 1]) {
						System.out.println(" Attacker country loose 1 army");
						updateArmyOfAttacker = RiskGame.countriesArmies.get(attackerCountry) - 1;
						RiskGame.countriesArmies.put(attackerCountry, updateArmyOfAttacker);

						if (attackerDiceArray[attackerDiceArray.length
								- 2] <= defenderDiceArray[defenderDiceArray.length - 2]) {
							System.out.println(" Attacker country loose 1 more army");
							updateArmyOfAttacker -= 1;
							RiskGame.countriesArmies.put(attackerCountry, updateArmyOfAttacker);

						} else {
							System.out.println(" Defender country loose 1 more army");
							updateArmyOfDefender = RiskGame.countriesArmies.get(defenderCountry) - 1;
							RiskGame.countriesArmies.put(defenderCountry, updateArmyOfDefender);

							if (updateArmyOfDefender == 0) {

								for (Entry<String, ArrayList<String>> entry : RiskGame.initialPlayerCountry
										.entrySet()) {
									String key = entry.getKey();
									ArrayList<String> value = entry.getValue();

									if ((RiskGame.initialPlayerCountry.get(key).contains(defenderCountry))
											&& key != RiskGame.currentPlayer.getName()) {

										for (int i = 0; i < value.size(); i++) {
											if (value.get(i).equals(defenderCountry)) {
												indx = i;
												break;
											}
										}
										entry.getValue().remove(indx);
										RiskGame.initialPlayerCountry.get(RiskGame.currentPlayer.getName())
												.add(defenderCountry);

										newCOuntryListSize = RiskGame.initialPlayerCountry
												.get(RiskGame.currentPlayer.getName()).size();
									}
								}

								RiskGame.countriesArmies.put(defenderCountry, noOfAttackerDice);

							} else {
								RiskGame.countriesArmies.put(defenderCountry, updateArmyOfDefender);
							}

						}

						RiskGame.countriesArmies.put(attackerCountry, updateArmyOfAttacker);

						System.out.println("new list of initial player country" + RiskGame.initialPlayerCountry);
						System.out.println(
								"Initailly player country list with initial army : " + RiskGame.countriesArmies);
					}

				}
			}
		} else
			System.out.println("As you are having only 1 army, you can't attack");

		System.out.println("Do you want to still attack to other countries, press Y/N");
		attackTurnOn = input.readLine();
		if (attackTurnOn.equals("Y")) {

			countNoOfAttack++;

			RiskInterface objRIAPV = new RiskInterface();
			objRIAPV.demoAttackPhaseView();
			// attackPhase();
		} else {

			// System.out.println(RiskGame.initialPlayerCountry.get(RiskGame.currentPlayer));

			newCOuntryListSize = RiskGame.initialPlayerCountry.get(RiskGame.currentPlayer.getName()).size();
			System.out.println("===========newCOuntryListSize :========== " + newCOuntryListSize);

			if (newCOuntryListSize > oldCOuntryListSize) {
				System.out.println("asljdhdsfjkldkafhhsjdkgfv");
				// code for card need to be done here
				int indexOfCardToBeGet = (int) (Math.random() * (RiskGame.cardsInTheDeck.size() - 0));
				List<String> temp = new ArrayList<String>(playersCards.get(RiskGame.currentPlayer.getName()));
				// System.out.println(temp);
				System.out.println(RiskGame.cardsInTheDeck);
				temp.add(RiskGame.cardsInTheDeck.get(indexOfCardToBeGet));
				System.out.println("temp " + temp);
				System.out.println("cards in the deck " + RiskGame.cardsInTheDeck);
				playersCards.put(RiskGame.currentPlayer.getName(), temp);
				temp.clear();
				// playersCards.get(RiskGame.currentPlayer.getName()).add(RiskGame.cardsInTheDeck.get(indexOfCardToBeGet));
				RiskGame.cardsInTheDeck.remove(indexOfCardToBeGet);
				RiskInterface objRIWD = new RiskInterface();
				objRIWD.demoWorldDominance();

				// setChanged();
				// notifyObservers(this);
			}

			countriesArmiesObserver.putAll(RiskGame.countriesArmies);
			initialPlayerCountryObserver.putAll(RiskGame.initialPlayerCountry);
			setChanged();
			notifyObservers(this);
			RiskGame.noOfReinforcementArmies = 0;
			RiskInterface objRIFPV = new RiskInterface();
			objRIFPV.demoFortifyPhaseView();

			// fortify(RiskGame.currentPlayer);
		}

	}// end of attackPhase
		// need to keep global variable to collect armies

	public void fortify(PlayerToPlay currentPlayer) throws IOException {
		Scanner scFrom = new Scanner(System.in);
		System.out.println(currentPlayer.getName());
		System.out.println("You have these countries under your control "
				+ RiskGame.initialPlayerCountry.get(currentPlayer.getName()));
		System.out.println("Enter the name of country from which you want to move armies.");
		String from;
		from = scFrom.nextLine();
		System.out.println(from);
		// System.out.println("You can move armies to these many countries only
		// from your chosen country.");
		// System.out.println(RiskGame.adj.get(from));

		// System.out.println(RiskGame.adj.get(from));
		// System.out.println(RiskGame.initialPlayerCountry.get(currentPlayer.getName()));
		int tempCountrySize = RiskGame.initialPlayerCountry.get(currentPlayer.getName()).size();
		int tempAdjSize = RiskGame.adj.get(from).size();
		// System.out.println(tempAdjSize);
		// System.out.println(tempCountrySize);
		ArrayList<String> tempAdjCountryToWhichWeCanMOve = new ArrayList<String>();
		for (int i = 0; i < tempAdjSize; i++) {
			for (int j = 0; j < tempCountrySize; j++) {
				if (RiskGame.initialPlayerCountry.get(currentPlayer.getName()).get(j)
						.contains(RiskGame.adj.get(from).get(i))) {
					tempAdjCountryToWhichWeCanMOve.add(RiskGame.adj.get(from).get(i));
				}
			}
		}
		// System.out.println(tempAdjCountryToWhichWeCanMOve);
		Scanner scto = new Scanner(System.in);
		System.out.println("Select the countries from your owned countries  and adjacent to " + from
				+ " where you want to move your armies");
		// System.out.println(RiskGame.adj.get(from));
		System.out.println(tempAdjCountryToWhichWeCanMOve);
		String to;
		int numberOfArmiesToMove;
		to = scto.nextLine();
		// System.out.println(to);
		// System.out.println(RiskGame.adj.get(from));
		// System.out.println("adjacentsMap: " + RiskGame.adj);
		// System.out.println(RiskGame.initialPlayerCountry);
		if (tempAdjCountryToWhichWeCanMOve.contains(to)) {
			System.out.println(currentPlayer.getName() + " you have " + RiskGame.countriesArmies.get(from)
					+ " armies on " + from + ".");
			System.out.println("Please Enter the number of armies you want to move to " + "" + to + " " + "country.s");
			numberOfArmiesToMove = sc.nextInt();
			if (RiskGame.countriesArmies.get(from) >= numberOfArmiesToMove) {
				System.out.println("Yes");
				if (RiskGame.countriesArmies.get(from) > 1) {
					int currentArmiesOfToCountry = RiskGame.countriesArmies.get(to);
					int newArmiesToAdd = currentArmiesOfToCountry + numberOfArmiesToMove;
					RiskGame.countriesArmies.put(to, newArmiesToAdd);
					int currentArmiesOfFromCountry = RiskGame.countriesArmies.get(from);
					int newArmiesToDelete = currentArmiesOfFromCountry - numberOfArmiesToMove;
					RiskGame.countriesArmies.put(from, newArmiesToDelete);
					countriesArmiesObserver.putAll(RiskGame.countriesArmies);
					System.out.println(from + " = " + RiskGame.countriesArmies.get(from));
					System.out.println(to + " = " + RiskGame.countriesArmies.get(to) + "\n");
					setChanged();
					notifyObservers(this);
					RiskGame.nextPlayer();
					RiskInterface objRIRPV = new RiskInterface();
					objRIRPV.demoReinforcePhaseView();
				} else {
					Scanner sc = new Scanner(System.in);
					String result;
					System.out.println("You dont have sufficient number of armies to move from " + from
							+ "Do you want to play fortify again? Y/N");
					result = sc.nextLine();
					if (result.equals("N")) {
						RiskInterface objRILocalFortify2 = new RiskInterface();
						objRILocalFortify2.demoFortifyPhaseView();
						// fortify(currentPlayer);
					} else {
						setChanged();
						notifyObservers(this);
						RiskGame.nextPlayer();
						RiskInterface objRILocalNew1 = new RiskInterface();
						objRILocalNew1.demoReinforcePhaseView();
						// reinforcementPhase();
					}
					// System.out.println(from + " " +
					// RiskGame.countriesArmies.get(from));
					// System.out.println(to);
				}
			} else {
				System.out.println(currentPlayer.getName() + " you can move " + numberOfArmiesToMove);
				Scanner sc = new Scanner(System.in);
				String result;
				System.out.println("Do you want to stop? Y/N");
				result = sc.nextLine();
				if (result.equals("N")) {
					RiskInterface objRILocalFortify3 = new RiskInterface();
					objRILocalFortify3.demoFortifyPhaseView();
					// fortify(currentPlayer);
				} else {
					setChanged();
					notifyObservers(this);
					RiskGame.nextPlayer();
					RiskInterface objRILocalNew2 = new RiskInterface();
					objRILocalNew2.demoReinforcePhaseView();
					// reinforcementPhase();
				}
			}

		} else {
			System.out.println(currentPlayer.getName() + " doesnt contain the country where you want to place armies.");
			Scanner sc = new Scanner(System.in);
			String result;
			System.out.println("Do you want to stop? Y/N");
			result = sc.nextLine();
			if (result.equals("N")) {
				RiskInterface objRILocalFortify4 = new RiskInterface();
				objRILocalFortify4.demoFortifyPhaseView();
				// fortify(currentPlayer);
			} else {
				setChanged();
				notifyObservers(this);
				RiskGame.nextPlayer();
				RiskInterface objRILocalNewNew1 = new RiskInterface();
				objRILocalNewNew1.demoReinforcePhaseView();
				// reinforcementPhase();
			}
		}
	}
}