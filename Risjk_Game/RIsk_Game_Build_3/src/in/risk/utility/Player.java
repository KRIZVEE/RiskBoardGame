package in.risk.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Map.Entry;

//import in.risk.gui.RiskInterface;

//import in.risk.gui.RiskInterface;

/**
 * This class implements the Reinforcement, Attack and Fortify game phases.
 * @author Mohit Rana, Kashif Rizvee, Ishan Kansara, Charanpreet Singh
 *
 */
public class Player extends StartUpPhase {

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

	public static String from = "";
	public static String to = "";
	
	
	
	
	
	
	
	/**
	 * This method is used to get reinforcement armies from the countries player own.
	 * @param playerName used to specify the name of the current player
	 * @return true is all goes well.
	 */
	public static int getArmiesFromCountries(String playerName){
		int noOfReinforcementArmiesForCountry = 0;
		if(initialPlayerCountry.get(playerName).size() < 9){
			noOfReinforcementArmiesForCountry = noOfReinforcementArmiesForCountry + 3;
		}else{
			noOfReinforcementArmiesForCountry = noOfReinforcementArmiesForCountry + initialPlayerCountry.get(playerName).size()/3;
		}
		System.out.println(playerName + " got " + noOfReinforcementArmiesForCountry + " armies from owned countries.");
		return noOfReinforcementArmiesForCountry;
	}

	/**
	 * This method is used to get reinforcement armies from owned continents.
	 * @param playerName used to specify the name of current player.
	 * @return true if everything goes well.
	 */
	public static int getArmiesaFromContinet(String playerName){
		int noOfReinforcementArmiesForContinent = 0;
		ArrayList<Boolean> resultOfContinentCountry = new ArrayList<Boolean>();
		for (Entry<String, List<String>> entry : LoadMap.continentCountries.entrySet()) {
			String Key = entry.getKey();
			List<String> value = entry.getValue();
			for (int i = 0; i < value.size(); i++) {
				resultOfContinentCountry.add(initialPlayerCountry.get(playerName).contains(value.get(i)));
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
	public static int getArmiesFromCards(String playerName) throws IOException {
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
	public static void initialCardDistribution() throws IOException {
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
	public static List<String> placeCardsInTheDeck() throws IOException {
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
	public static int checkDiscreteCombination(String playerName) throws IOException {
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
	public static int checkUniqueCombination(int tempSize, String playerName) throws IOException {
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
	public static void placeReinforcementArmies(PlayerToPlay playerName) throws IOException {
		Scanner sc = new Scanner(System.in);
		String countryNameToEnterArmies;
		int noOfArmiesWantToPlace;
		int noOfReinforcementArmiesFromCountrie = getArmiesFromCountries(playerName.getName());
		int noOfReinforcementArmiesFromContinents = getArmiesaFromContinet(playerName.getName());
		int noOfReinforcementArmiesFromCards = getArmiesFromCards(playerName.getName());
		
		int noOfRinforcementArmies = noOfReinforcementArmiesFromCards + noOfReinforcementArmiesFromContinents + noOfReinforcementArmiesFromCountrie;
		System.out.println(playerName.getName() + " you have " + noOfRinforcementArmies + " number of reinforcement armies.");
		playerName.addArmies(noOfRinforcementArmies);
		
		int iteratorForPlayerArmies = playerName.getArmies();
		while (iteratorForPlayerArmies < 0) {
			System.out.println(playerName.getName() + " You have " + iteratorForPlayerArmies + " armies.");
			System.out.println("And you own " + StartUpPhase.initialPlayerCountry.get(playerName.getName()));
			System.out.println("Enter the name of country where you wan to place armies");
			countryNameToEnterArmies = sc.nextLine();
			if (!StartUpPhase.initialPlayerCountry.get(playerName.getName()).contains(countryNameToEnterArmies)){
				System.out.println("You dont own this country");
			}else {
				System.out.println("Enter thhe number of armies you want to add on " + countryNameToEnterArmies);
				noOfArmiesWantToPlace = sc.nextInt();
				placeReinforcementArmies(countryNameToEnterArmies, noOfArmiesWantToPlace, playerName);
			}
		}
	}

	/**
	 * This method is used to update the countries armies according to user inputed data.
	 * @param countryNameToEnterArmies Name of the country to add armies.
	 * @param noOfArmiesWantToPlace Number of armies player wants to add to the selected country.
	 * @param playerName Player who wants to add the armies.
	 * @return true if everything goes well.
	 */
	public static boolean placeReinforcementArmies(String countryNameToEnterArmies, int noOfArmiesWantToPlace , PlayerToPlay playerName) {
		if (noOfArmiesWantToPlace > playerName.getArmies()) {
			System.out.println("You don't have suffecient armies");
		}else {
			int initialAriesCountryOwn = StartUpPhase.countriesArmies.get(countryNameToEnterArmies);
			int finalArmiesCOuntryHave = initialAriesCountryOwn + noOfArmiesWantToPlace;
			StartUpPhase.countriesArmies.put(countryNameToEnterArmies, finalArmiesCOuntryHave);
			playerName.loosArmies(noOfArmiesWantToPlace);
		}
		return true;
		
	}

	/**
	 * 	
	 * This method used to capture the attack phase information
	 * of each player.
	 * @param currentPlayer
	 * @throws IOException
	 */

	public static void attackPhase(PlayerToPlay playerName) throws IOException {
	
		Scanner sc = new Scanner(System.in);
		String attackTurnOn = "hello";

		System.out.println(StartUpPhase.initialPlayerCountry);
		//loggingString("Initial countries with player: " + StartUpPhase.initialPlayerCountry);

		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

		//StartUpPhase.countriesArmies.put("Peru", 1);// for checking
		

		System.out.println("Initailly player country list before any attack : "
				+ StartUpPhase.initialPlayerCountry.get(playerName.getName()));
		//loggingString("Initailly player country list before any attack : "+ StartUpPhase.initialPlayerCountry.get(playerName.getName()));
		
		System.out.println("Initailly player country list with initial army : "
				+ StartUpPhase.countriesArmies.get(playerName.getName()));
		//loggingString("Initailly player country list with initial army : "+ StartUpPhase.countriesArmies.get(playerName.getName()));

		System.out.println("Current Player : " + playerName.getName());
		//loggingString("Current Player : " + playerName.getName());
		
		oldCOuntryListSize = StartUpPhase.initialPlayerCountry.get(playerName.getName()).size();
		System.out.println("===========oldCOuntryListSize :========== " + oldCOuntryListSize);
		//loggingString("===========oldCOuntryListSize :========== " + oldCOuntryListSize);
		
		System.out.println("Current Player owning ciuntries : "
				+ StartUpPhase.initialPlayerCountry.get(playerName.getName()));
		//loggingString("Current Player owning ciuntries : " + StartUpPhase.initialPlayerCountry.get(playerName.getName()));
		System.out.println("Please enter the name of country from where you want to attack");
		attackerCountry = input.readLine();
		//loggingString(bdsbs);
		// countriesArmies.put(attackerCountry, 4);

		System.out.println("No of armies in the attacker country : " + StartUpPhase.countriesArmies.get(attackerCountry));
		//loggingString("No of armies in the attacker country : " + StartUpPhase.countriesArmies.get(attackerCountry));

		List<String> attackerCountryAdjacent = new ArrayList<String>();
		int size = StartUpPhase.initialPlayerCountry.get(playerName.getName()).size();
		int size2 = LoadMap.adj.get(attackerCountry).size();
		for (int i = 0; i < size2; i++) {
			if (StartUpPhase.initialPlayerCountry.get(playerName.getName())
					.contains(LoadMap.adj.get(attackerCountry).get(i))) {
				continue;
			} else {
				attackerCountryAdjacent.add(LoadMap.adj.get(attackerCountry).get(i));
			}
		}

		
		System.out.println("Based on this country name, you can attack to corresponding countries only : "
				+ attackerCountryAdjacent); // need to work here
		//loggingString("Based on this country name, you can attack to corresponding countries only : " + attackerCountryAdjacent);
		
		
		if(attackerCountryAdjacent.isEmpty()){
			Scanner sc123 = new Scanner(System.in);
			String result123;
			System.out.println("You cannot attack because the country is not adjacent to any enemy country. Do you want to attack more? Y/N");
			result123 = sc123.nextLine();
	//	loggingString("You cannot attack because the country is not adjacent to any enemy country. Do you want to attack more? Y/N" + " your chosen result : " + result123);
		if (attackTurnOn.equals("Y")) {

			attackPhase(currentPlayer);

//			RiskInterface objRIAPV = new RiskInterface();
//			objRIAPV.demoAttackPhaseView();
		} else {

			// System.out.println(RiskGame.initialPlayerCountry.get(RiskGame.currentPlayer));

			newCOuntryListSize = StartUpPhase.initialPlayerCountry.get(playerName.getName()).size();
			System.out.println("===========newCOuntryListSize :========== " + newCOuntryListSize);
			//loggingString("===========newCOuntryListSize :========== " + newCOuntryListSize);

			if (newCOuntryListSize > oldCOuntryListSize) {
				//System.out.println("asljdhdsfjkldkafhhsjdkgfv");
				// code for card need to be done here
				int indexOfCardToBeGet = (int) (Math.random() * (cardsInTheDeck.size() - 0));
				List<String> temp = new ArrayList<String>(playersCards.get(playerName.getName()));
				// System.out.println(temp);
				System.out.println(cardsInTheDeck);
				//loggingString((" " + (cardInTheDeck)));
				temp.add(cardsInTheDeck.get(indexOfCardToBeGet));
				System.out.println("temp " + temp);
				//loggingString("temp " + temp);
				System.out.println("cards in the deck " + cardsInTheDeck);
				//loggingString("cards in the deck " + cardsInTheDeck);
				playersCards.put(playerName.getName(), temp);
				temp.clear();
				// playersCards.get(RiskGame.currentPlayer.getName()).add(RiskGame.cardsInTheDeck.get(indexOfCardToBeGet));
				cardsInTheDeck.remove(indexOfCardToBeGet);
				
				//observer pattern for world dominance
//				RiskInterface objRIWD = new RiskInterface();
//				objRIWD.demoWorldDominance();

				
			}

			countriesArmiesObserver.putAll(StartUpPhase.countriesArmies);
			initialPlayerCountryObserver.putAll(StartUpPhase.initialPlayerCountry);
			//observer pattern
//			setChanged();
//			notifyObservers(this);
			
			
			//=========================================================
			//=======// didn't get this part===========================
			//=======fortifyPhase(currentPlayer);	===================	
			//=======StartUpPhase.noOfReinforcementArmies = 0;=========
			//=======RiskInterface objRIFPV = new RiskInterface();=====
			//=======objRIFPV.demoFortifyPhaseView();==================
			//=========================================================

		}
	}
	
	
	System.out.println("Please enter the name of country, to which you want to attack");
	defenderCountry = input.readLine();
	
	System.out.println("No of armies in the defender country : " + StartUpPhase.countriesArmies.get(defenderCountry));
	//loggingString("No of armies in the defender country : " + StartUpPhase.countriesArmies.get(defenderCountry));


	if (StartUpPhase.countriesArmies.get(attackerCountry) >= 2) {
		System.out.println("As, You have minimum of 2 armies, you can attack");
		//loggingString("As, You have minimum of 2 armies, you can attack");

		if (StartUpPhase.countriesArmies.get(attackerCountry) > 3) {
			System.out.println("Attacker Country Player, can opt among 1, 2 or 3 dice to be rolled");
			noOfAttackerDice = sc.nextInt();
			
		} else if (StartUpPhase.countriesArmies.get(attackerCountry) == 3) {
			// flagCheckDice = 1;
			System.out.println("Attacker Country Player, can opt among 1 or 2 dice to be rolled");
			noOfAttackerDice = sc.nextInt();
			
		} else if (StartUpPhase.countriesArmies.get(attackerCountry) == 2) {
			System.out.println(
					"Attacker Country Player, can have only 1 dice to roll, as you have only 2 army on country : "
							+ attackerCountry);
			//loggingString("Attacker Country Player, can have only 1 dice to roll, as you have only 2 army on country : "	+ attackerCountry);
			
			noOfAttackerDice = 1;
		}
		if (noOfAttackerDice == 2 || noOfAttackerDice == 3)
			flagCheckDice = 1;

		attackerDiceArray = new int[noOfAttackerDice];

		for (int i = 0; i < attackerDiceArray.length; i++) {
			attackerDiceArray[i] = StartUpPhase.randomNumberGenerator();
		}

		if (StartUpPhase.countriesArmies.get(defenderCountry) >= 2) {

			System.out.println("Defender Country Player, can opt among 1 or 2 dice to roll");
			noOfDefenderDice = sc.nextInt();
			

		} else {
			System.out.println(
					"Defender Country Player, can have only 1 dice to roll, as you have only 1 army on country : "
							+ defenderCountry);
			//loggingString("Defender Country Player, can have only 1 dice to roll, as you have only 1 army on country : "+ defenderCountry);
			
			noOfDefenderDice = 1;
		}

		defenderDiceArray = new int[noOfDefenderDice];

		for (int i = 0; i < defenderDiceArray.length; i++) {
			defenderDiceArray[i] = StartUpPhase.randomNumberGenerator();
		}

		System.out.println("Attacker Dice value are as follow : ");
		//loggingString("Attacker Dice value are as follow : ");

		Arrays.sort(attackerDiceArray);
		for (int i = 0; i < attackerDiceArray.length; i++) {
			System.out.println("Attacker dice for position " + (i + 1) + " is " + " " + attackerDiceArray[i]);
			//loggingString("Attacker dice for position " + (i + 1) + " is " + " " + attackerDiceArray[i]);
		}
		
		System.out.println("");
		//loggingString(" " );
		Arrays.sort(defenderDiceArray);

		System.out.println("Defender Dice value are as follow : ");
		//loggingString("Defender Dice value are as follow : ");

		for (int i = 0; i < defenderDiceArray.length; i++) {
			System.out.println("Defender dice for position " + (i + 1) + " is " + " " + defenderDiceArray[i]);
			//loggingString("Defender dice for position " + (i + 1) + " is " + " " + defenderDiceArray[i]);
		}
		
		System.out.println("");
		//loggingString(" " );


		System.out.println(
				"Number of armies in Attacker Country is : " + StartUpPhase.countriesArmies.get(attackerCountry));
		//loggingString("Number of armies in Attacker Country is : " + StartUpPhase.countriesArmies.get(attackerCountry));
		
		System.out.println(
				"Number of armies in Defender Country is : " + StartUpPhase.countriesArmies.get(defenderCountry));
		//loggingString("Number of armies in Defender Country is : " + StartUpPhase.countriesArmies.get(defenderCountry));
		

		if (noOfDefenderDice == 1) {

			if (attackerDiceArray[attackerDiceArray.length - 1] > defenderDiceArray[defenderDiceArray.length - 1]) {
				System.out.println(" Defender country loose 1 army");
				//loggingString(" Defender country loose 1 army");
				
				updateArmyOfDefender = StartUpPhase.countriesArmies.get(defenderCountry) - 1;
				if (updateArmyOfDefender == 0) {

					for (Entry<String, ArrayList<String>> entry : StartUpPhase.initialPlayerCountry.entrySet()) {
						String key = entry.getKey();
						ArrayList<String> value = entry.getValue();

						if ((StartUpPhase.initialPlayerCountry.get(key).contains(defenderCountry))
								&& key != playerName.getName()) {

							for (int i = 0; i < value.size(); i++) {
								if (value.get(i).equals(defenderCountry)) {
									indx = i;
									break;
								}
							}
							entry.getValue().remove(indx);
							StartUpPhase.initialPlayerCountry.get(playerName.getName())
									.add(defenderCountry);

							newCOuntryListSize = StartUpPhase.initialPlayerCountry.get(playerName.getName())
									.size();
						}
					}

					StartUpPhase.countriesArmies.put(defenderCountry, noOfAttackerDice);

				} else {
					StartUpPhase.countriesArmies.put(defenderCountry, updateArmyOfDefender);
				}
				System.out.println("new list of initial player country" + StartUpPhase.initialPlayerCountry);
				//loggingString("new list of initial player country" + StartUpPhase.initialPlayerCountry);
				
				System.out.println("Initailly player country list with initial army : " + StartUpPhase.countriesArmies);
				//loggingString("Initailly player country list with initial army : " + StartUpPhase.countriesArmies);
				

			} else {//// DCD(0) > ACD(0)// for defender dice == 1
				System.out.println(" Attacker country loose 1 army");
				//loggingString(" Attacker country loose 1 army");
				
				updateArmyOfAttacker = StartUpPhase.countriesArmies.get(attackerCountry) - 1;

				StartUpPhase.countriesArmies.put(attackerCountry, updateArmyOfAttacker);

				System.out.println("new list of initial player country" + StartUpPhase.initialPlayerCountry);
				//loggingString("new list of initial player country" + StartUpPhase.initialPlayerCountry);
				
				System.out.println("Initailly player country list with initial army : " + StartUpPhase.countriesArmies);
				//loggingString("Initailly player country list with initial army : " + StartUpPhase.countriesArmies);
				
			}
		} // end of defenderdice ==1

		else {

			if (attackerDiceArray[attackerDiceArray.length - 1] > defenderDiceArray[defenderDiceArray.length - 1]) {
				System.out.println(" Defender country loose 1 army");
				//loggingString(" Defender country loose 1 army");
				
				updateArmyOfDefender = StartUpPhase.countriesArmies.get(defenderCountry) - 1;
				StartUpPhase.countriesArmies.put(defenderCountry, updateArmyOfDefender);

				if (attackerDiceArray[attackerDiceArray.length - 2] > defenderDiceArray[defenderDiceArray.length
						- 2]) {
					System.out.println(" Defender country loose 1 more army");
					//loggingString(" Defender country loose 1 more army");

					updateArmyOfDefender = updateArmyOfDefender - 1;
					StartUpPhase.countriesArmies.put(defenderCountry, updateArmyOfDefender);

					if (updateArmyOfDefender == 0) {

						for (Entry<String, ArrayList<String>> entry : StartUpPhase.initialPlayerCountry.entrySet()) {
							String key = entry.getKey();
							ArrayList<String> value = entry.getValue();

							if ((StartUpPhase.initialPlayerCountry.get(key).contains(defenderCountry))
									&& key != playerName.getName()) {

								for (int i = 0; i < value.size(); i++) {
									if (value.get(i).equals(defenderCountry)) {
										indx = i;
										break;
									}
								}
								entry.getValue().remove(indx);
								StartUpPhase.initialPlayerCountry.get(playerName.getName())
										.add(defenderCountry);

								newCOuntryListSize = StartUpPhase.initialPlayerCountry
										.get(playerName.getName()).size();
							}
						}

						StartUpPhase.countriesArmies.put(defenderCountry, noOfAttackerDice);

					} else {
						StartUpPhase.countriesArmies.put(defenderCountry, updateArmyOfDefender);
					}
					System.out.println("new list of initial player country" + StartUpPhase.initialPlayerCountry);
					//loggingString("new list of initial player country" + StartUpPhase.initialPlayerCountry);

					System.out.println(
							"Initailly player country list with initial army : " + StartUpPhase.countriesArmies);
					//loggingString("Initailly player country list with initial army : " + StartUpPhase.countriesArmies);
				} else {
					System.out.println(" Attacker country loose 1 army");
					
					updateArmyOfAttacker = StartUpPhase.countriesArmies.get(attackerCountry) - 1;

					StartUpPhase.countriesArmies.put(attackerCountry, updateArmyOfAttacker);

					System.out.println("new list of initial player country" + StartUpPhase.initialPlayerCountry);
					//loggingString("new list of initial player country" + StartUpPhase.initialPlayerCountry);
					
					System.out.println(
							"Initailly player country list with initial army : " + StartUpPhase.countriesArmies);
					//loggingString("Initailly player country list with initial army : " + StartUpPhase.countriesArmies);
					

				}

			} else {
				if (attackerDiceArray[attackerDiceArray.length - 1] <= defenderDiceArray[defenderDiceArray.length
						- 1]) {
					System.out.println(" Attacker country loose 1 army");
			//		loggingString(" Attacker country loose 1 army");
					
					updateArmyOfAttacker = StartUpPhase.countriesArmies.get(attackerCountry) - 1;
					StartUpPhase.countriesArmies.put(attackerCountry, updateArmyOfAttacker);

					if (attackerDiceArray[attackerDiceArray.length
							- 2] <= defenderDiceArray[defenderDiceArray.length - 2]) {
						System.out.println(" Attacker country loose 1 more army");
						//loggingString(" Attacker country loose 1 more army");
						
						updateArmyOfAttacker -= 1;
						StartUpPhase.countriesArmies.put(attackerCountry, updateArmyOfAttacker);

					} else {
						System.out.println(" Defender country loose 1 more army");
						//loggingString(" Defender country loose 1 more army");

						updateArmyOfDefender = StartUpPhase.countriesArmies.get(defenderCountry) - 1;
						StartUpPhase.countriesArmies.put(defenderCountry, updateArmyOfDefender);

						if (updateArmyOfDefender == 0) {

							for (Entry<String, ArrayList<String>> entry : StartUpPhase.initialPlayerCountry
									.entrySet()) {
								String key = entry.getKey();
								ArrayList<String> value = entry.getValue();

								if ((StartUpPhase.initialPlayerCountry.get(key).contains(defenderCountry))
										&& key != playerName.getName()) {

									for (int i = 0; i < value.size(); i++) {
										if (value.get(i).equals(defenderCountry)) {
											indx = i;
											break;
										}
									}
									entry.getValue().remove(indx);
									StartUpPhase.initialPlayerCountry.get(playerName.getName())
											.add(defenderCountry);

									newCOuntryListSize = StartUpPhase.initialPlayerCountry
											.get(playerName.getName()).size();
								}
							}

							StartUpPhase.countriesArmies.put(defenderCountry, noOfAttackerDice);

						} else {
							StartUpPhase.countriesArmies.put(defenderCountry, updateArmyOfDefender);
						}

					}

					StartUpPhase.countriesArmies.put(attackerCountry, updateArmyOfAttacker);

					System.out.println("new list of initial player country" + StartUpPhase.initialPlayerCountry);
					//loggingString("new list of initial player country" + StartUpPhase.initialPlayerCountry);

					System.out.println(
							"Initailly player country list with initial army : " + StartUpPhase.countriesArmies);
					//loggingString("Initailly player country list with initial army : " + StartUpPhase.countriesArmies);
					
				}

			}
		}
	} else
		System.out.println("As you are having only 1 army, you can't attack");
	//loggingString("As you are having only 1 army, you can't attack");


	System.out.println("Do you want to still attack to other countries, press Y/N");
	//loggingString("Do you want to still attack to other countries, press Y/N");

	
	attackTurnOn = input.readLine();
	//loggingString("Do you want to still attack to other countries, press Y/N " + " your chosen result : "+ attackTurnOn);

	if (attackTurnOn.equals("Y")) {

		countNoOfAttack++;
		
		//observer pattern attack phase
		attackPhase(currentPlayer);

//		RiskInterface objRIAPV = new RiskInterface();
//		objRIAPV.demoAttackPhaseView();
	} else {


		newCOuntryListSize = StartUpPhase.initialPlayerCountry.get(playerName.getName()).size();
		System.out.println("===========newCOuntryListSize :========== " + newCOuntryListSize);
		//loggingString("===========newCOuntryListSize :========== " + newCOuntryListSize);

		

		if (newCOuntryListSize > oldCOuntryListSize) {
			//System.out.println("asljdhdsfjkldkafhhsjdkgfv");
			// code for card need to be done here
			int indexOfCardToBeGet = (int) (Math.random() * (cardsInTheDeck.size() - 0));
			List<String> temp = new ArrayList<String>(playersCards.get(playerName.getName()));
			// System.out.println(temp);
			System.out.println(cardsInTheDeck);
			//loggingString(" "+cardsInTheDeck);

			temp.add(cardsInTheDeck.get(indexOfCardToBeGet));
			System.out.println("temp " + temp);
			//loggingString("temp " + temp);

			System.out.println("cards in the deck " + cardsInTheDeck);
			//loggingString("cards in the deck " + cardsInTheDeck);

			playersCards.put(playerName.getName(), temp);
			temp.clear();
			cardsInTheDeck.remove(indexOfCardToBeGet);
			
			//obsever pattern for world dominance
//			RiskInterface objRIWD = new RiskInterface();
//			objRIWD.demoWorldDominance();
			}

		countriesArmiesObserver.putAll(StartUpPhase.countriesArmies);
		initialPlayerCountryObserver.putAll(StartUpPhase.initialPlayerCountry);
		// observer pattern
//		setChanged();
//		notifyObservers(this);
		
		//=========================================================
		//=======// didn't get this part===========================
		//=======fortifyPhase(currentPlayer);	===================	
		//=======StartUpPhase.noOfReinforcementArmies = 0;=========
		//=======RiskInterface objRIFPV = new RiskInterface();=====
		//=======objRIFPV.demoFortifyPhaseView();==================
		//=========================================================
		
//		RiskGame.noOfReinforcementArmies = 0;
//		
//		//observer pattern for fortify phase
//		RiskInterface objRIFPV = new RiskInterface();
//		objRIFPV.demoFortifyPhaseView();

	}

}// end of attackPhase
	
	
	/**
	 * This method used to capture the fortify phase information
	 * of each player.
	 * @param currentPlayer
	 * @throws IOException
	 */
		public static void fortifyPhase(PlayerToPlay currentPlayer) throws IOException {
			Scanner scFrom = new Scanner(System.in);
			Scanner sc = new Scanner(System.in);
			
		
			System.out.println(currentPlayer.getName());
			
			System.out.println("You have these countries under your control "
					+ StartUpPhase.initialPlayerCountry.get(currentPlayer.getName()));
			//loggingString("You have these countries under your control "					+ StartUpPhase.initialPlayerCountry.get(currentPlayer.getName()));
			System.out.println("Enter the name of country from which you want to move armies.");
			//loggingString("Enter the name of country from which you want to move armies.");
			String from;
			from = scFrom.nextLine();
			System.out.println(from);
			

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
			System.out.println("Select the countries from your owned countries  and adjacent to " + from
					+ " where you want to move your armies");
			//loggingString("Select the countries from your owned countries  and adjacent to " + from					+ " where you want to move your armies");

			
			String to;
			int numberOfArmiesToMove;
			to = scto.nextLine();

			if (tempAdjCountryToWhichWeCanMOve.contains(to)) {
				System.out.println(currentPlayer.getName() + " you have " + StartUpPhase.countriesArmies.get(from)
						+ " armies on " + from + ".");
				//loggingString(currentPlayer.getName() + " you have " + StartUpPhase.countriesArmies.get(from)				+ " armies on " + from + ".");// doubt here
				
				System.out.println("Please Enter the number of armies you want to move to " + "" + to + " " + "country.s");
				numberOfArmiesToMove = sc.nextInt();
				
				if (StartUpPhase.countriesArmies.get(from) >= numberOfArmiesToMove) {
					System.out.println("Yes");
					if (StartUpPhase.countriesArmies.get(from) > 1) {
						int currentArmiesOfToCountry = StartUpPhase.countriesArmies.get(to);
						int newArmiesToAdd = currentArmiesOfToCountry + numberOfArmiesToMove;
						StartUpPhase.countriesArmies.put(to, newArmiesToAdd);
						int currentArmiesOfFromCountry = StartUpPhase.countriesArmies.get(from);
						int newArmiesToDelete = currentArmiesOfFromCountry - numberOfArmiesToMove;
						StartUpPhase.countriesArmies.put(from, newArmiesToDelete);
						countriesArmiesObserver.putAll(StartUpPhase.countriesArmies);
						System.out.println(from + " = " + StartUpPhase.countriesArmies.get(from));
						//loggingString(from + " = " + StartUpPhase.countriesArmies.get(from));
						System.out.println(to + " = " + StartUpPhase.countriesArmies.get(to) + "\n");
						//loggingString(to + " = " + StartUpPhase.countriesArmies.get(to) + "\n");
						//observer pattern
//						setChanged();
//						notifyObservers(this);
						StartUpPhase.nextPlayer();
						
						//observer pattern for reinforcement pahse view
//						RiskInterface objRIRPV = new RiskInterface();
//						objRIRPV.demoReinforcePhaseView();
					} else {
						//Scanner sc = new Scanner(System.in);
						String result;
						System.out.println("You dont have sufficient number of armies to move from " + from
								+ "Do you want to play fortify again? Y/N");
						result = sc.nextLine();
						//loggingString("You dont have sufficient number of armies to move from " + from + "Do you want to play fortify again? Y/N" + " your chosen result " +result);
						
						if (result.equals("N")) {
							//calling of fortify phase
							//RiskInterface objRILocalFortify2 = new RiskInterface();
							//objRILocalFortify2.demoFortifyPhaseView();
							fortifyPhase(currentPlayer);
						} else {
							
						//observer pattern
//							setChanged();
//							notifyObservers(this);
							StartUpPhase.nextPlayer();
							placeReinforcementArmies(currentPlayer);
							//RiskInterface objRILocalNew1 = new RiskInterface();
							//objRILocalNew1.demoReinforcePhaseView();
						}

					}
				} else {
					System.out.println(currentPlayer.getName() + " you can move " + numberOfArmiesToMove);
					
					//Scanner sc = new Scanner(System.in);
					String result;
					System.out.println("Do you want to stop? Y/N");
					result = sc.nextLine();
					//loggingString("Do you want to stop? Y/N " + "your chosen result : " + result);

					
					if (result.equals("N")) {
						
					// calling of fortify phase
//						RiskInterface objRILocalFortify3 = new RiskInterface();
//						objRILocalFortify3.demoFortifyPhaseView();
						fortifyPhase(currentPlayer);
					} else {
//						setChanged();
//						notifyObservers(this);
						StartUpPhase.nextPlayer();
						placeReinforcementArmies(currentPlayer);
						//						RiskInterface objRILocalNew2 = new RiskInterface();
//						objRILocalNew2.demoReinforcePhaseView();
						// reinforcementPhase();
					}
				}

			} else {
				System.out.println(currentPlayer.getName() + " doesnt contain the country where you want to place armies.");
			//loggingString(currentPlayer.getName() + " doesnt contain the country where you want to place armies.");

			
			//Scanner sc = new Scanner(System.in);
			String result;
			System.out.println("Do you want to stop? Y/N");
			result = sc.nextLine();
			//loggingString("Do you want to stop? Y/N " + "your chosen result : " + result);

			
			if (result.equals("N")) {
//				RiskInterface objRILocalFortify4 = new RiskInterface();
//				objRILocalFortify4.demoFortifyPhaseView();
				fortifyPhase(currentPlayer);
			} else {
//				setChanged();
//				notifyObservers(this);
				StartUpPhase.nextPlayer();
				placeReinforcementArmies(currentPlayer);
//				RiskInterface objRILocalNewNew1 = new RiskInterface();
//				objRILocalNewNew1.demoReinforcePhaseView();
			}
		}
	}
				
			
			
				
}
