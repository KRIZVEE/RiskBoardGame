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
public class RandomPlayer implements Strategy{

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
	public static int conqueredMapCounter = 0;

	
	//variables for the fortify phase

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
	public static int getArmiesaFromContinet(String playerName){
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
	
	public void placeReinforcementArmies(PlayerToPlay playerName) throws IOException {
		Scanner sc = new Scanner(System.in);
		String countryNameToEnterArmies;
		int noOfArmiesWantToPlace;
		int noOfReinforcementArmiesFromCountrie = getArmiesFromCountries(playerName.getName());
		int noOfReinforcementArmiesFromContinents = getArmiesaFromContinet(playerName.getName());
		int noOfReinforcementArmiesFromCards = getArmiesFromCards(playerName.getName());
		
		int noOfRinforcementArmies = noOfReinforcementArmiesFromCards + noOfReinforcementArmiesFromContinents + noOfReinforcementArmiesFromCountrie;
		System.out.println(playerName.getName() + " you have " + noOfRinforcementArmies + " number of reinforcement armies.");
		playerName.addArmies(noOfRinforcementArmies);
		StartUpPhase.countriesArmies.get(StartUpPhase.currentPlayer.getName());
		String randomCountry="";
		int armyOfRandomCountry = 0;
		int updatedarmyOfRandomCountry = 0;
		
		
		Random r = new Random();
		int index  = r.nextInt(StartUpPhase.initialPlayerCountry.get(playerName.getName()).size());
		randomCountry =  StartUpPhase.initialPlayerCountry.get(playerName.getName()).get(index);
		
		
		System.out.println("Random country name is: " + randomCountry);
//		StartUpPhase.countriesArmies.put("Test", 5);// for checking
//		StartUpPhase.countriesArmies.put("Greenland", 5);// for checking
//		StartUpPhase.countriesArmies.put("Alaska", 5);// for checking
		
		countryNameToEnterArmies = randomCountry;
		
		if (StartUpPhase.initialPlayerCountry.get(playerName.getName()).contains(randomCountry)){
			
			int randomNoOfArmies =0;
			
			for (Entry<String, Integer> entry : StartUpPhase.countriesArmies.entrySet()) {
				String key = entry.getKey();
				Integer value = entry.getValue();
				
				if(StartUpPhase.initialPlayerCountry.get(playerName.getName()).contains(randomCountry)){				
					randomNoOfArmies=value;
				}
			}
			System.out.println("country armies: for all player " + StartUpPhase.countriesArmies);
			noOfArmiesWantToPlace = noOfRinforcementArmies;
			System.out.println( " randomNoOfArmies : " + randomNoOfArmies);
			System.out.println( " noOfRinforcementArmies : " + noOfRinforcementArmies);
			System.out.println( " noOfArmiesWantToPlace : " + noOfArmiesWantToPlace);			
			placeReinforcementArmies(countryNameToEnterArmies, noOfArmiesWantToPlace, playerName);
			attackPhase(playerName);
		}
	}// end of Reinforcement Phase
	
	/**
	 * This method is used to update the countries armies according to user inputed data.
	 * @param countryNameToEnterArmies Name of the country to add armies.
	 * @param noOfArmiesWantToPlace Number of armies player wants to add to the selected country.
	 * @param playerName Player who wants to add the armies.
	 * @return true if everything goes well.
	 */
		public static boolean placeReinforcementArmies(String countryNameToEnterArmies, int noOfArmiesWantToPlace , PlayerToPlay playerName) {
		int initialAriesCountryOwn = StartUpPhase.countriesArmies.get(countryNameToEnterArmies);
		int finalArmiesCOuntryHave = initialAriesCountryOwn + noOfArmiesWantToPlace;
		StartUpPhase.countriesArmies.put(countryNameToEnterArmies, finalArmiesCOuntryHave);
		playerName.loosArmies(noOfArmiesWantToPlace);
		System.out.println("country armies: for all player " + StartUpPhase.countriesArmies);
		return true;
		
	}
	/**
	 * 	
	 * This method used to capture the attack phase information
	 * of each player.
	 * @param currentPlayer
	 * @throws IOException
	 */

	public void attackPhase(PlayerToPlay playerName) throws IOException {
		
	
		String attackTurnOn = "hello";
		int randomCountAttack = 0;
		System.out.println("Current Player : " + playerName.getName());		
		System.out.println(StartUpPhase.initialPlayerCountry);	
		
//		StartUpPhase.countriesArmies.put("Test", 5);// for checking
//		StartUpPhase.countriesArmies.put("Greenland", 5);// for checking
//		StartUpPhase.countriesArmies.put("Alaska", 5);// for checking		

		System.out.println("Initailly player country list before any attack : "				+ StartUpPhase.initialPlayerCountry.get(playerName.getName()));		
		System.out.println("Initailly player country list with initial army : "				+ StartUpPhase.countriesArmies.get(playerName.getName()));
		oldCOuntryListSize = StartUpPhase.initialPlayerCountry.get(playerName.getName()).size();
		System.out.println("===========oldCOuntryListSize :========== " + oldCOuntryListSize);		
		System.out.println("Current Player owning ciuntries : "
				+ StartUpPhase.initialPlayerCountry.get(playerName.getName()));
		
		// choosing random country as a attacker country
		System.out.println("the name of  *RANDOM* country from where you want to attack");
		String randomAttackingCountry="";
		Random r = new Random();
		int index  = r.nextInt(StartUpPhase.initialPlayerCountry.get(playerName.getName()).size());
		randomAttackingCountry =  StartUpPhase.initialPlayerCountry.get(playerName.getName()).get(index);
		attackerCountry = randomAttackingCountry;
		System.out.println("No of armies in the attacker country : " + StartUpPhase.countriesArmies.get(attackerCountry));
		
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
				+ attackerCountryAdjacent);
		randomCountAttack = r.nextInt(5 - 1 + 1) + 1;	// attacker country can attack between 1 to 5 times
		if(attackerCountryAdjacent.isEmpty()){
			String result123;
			System.out.println("You cannot attack because your country is not adjacent to any enemy country.");			
			
				newCOuntryListSize = StartUpPhase.initialPlayerCountry.get(playerName.getName()).size();
				System.out.println("===========newCOuntryListSize :========== " + newCOuntryListSize);
				if (newCOuntryListSize > oldCOuntryListSize) {



					//=================================================//
					//=========Do Card Distribution Logic on===========//
					//=========winning a country during attack=========//
					//=================================================//
					// NOT WOKRING NOW	//
					// NOT WOKRING NOW	//
					// NOT WOKRING NOW	//
					
					// code for card need to be done here
					int indexOfCardToBeGet = (int) (Math.random() * (cardsInTheDeck.size() - 0));
					List<String> temp = new ArrayList<String>(playersCards.get(playerName.getName()));
					System.out.println(cardsInTheDeck);
					temp.add(cardsInTheDeck.get(indexOfCardToBeGet));
					System.out.println("temp " + temp);
					System.out.println("cards in the deck " + cardsInTheDeck);
					playersCards.put(playerName.getName(), temp);
					temp.clear();
					cardsInTheDeck.remove(indexOfCardToBeGet);					
				}
				if(randomCountAttack!=0)
					{randomCountAttack++;
					attackPhase(playerName);}
				else
					fortifyPhase(playerName);// move to fortify phase if attacked happened random no of time
				}
	
//		StartUpPhase.countriesArmies.put("Test", 5);// for checking
//		StartUpPhase.countriesArmies.put("Iceland", 5);// for checking
//		StartUpPhase.countriesArmies.put("Venezuala", 5);// for checking
//		StartUpPhase.countriesArmies.put("Argentina", 5);// for checking
		
		// selection of defender country
		System.out.println("the name of  *RANDOM* country in which you want to attack to");
		String randomDefenderCountry="";
		Random rNew = new Random();
		int indexNew  = rNew.nextInt(attackerCountryAdjacent.size());//.get(playerName.getName()).size());
		randomDefenderCountry = attackerCountryAdjacent.get(indexNew);
		
		defenderCountry = randomDefenderCountry;
		
		System.out.println(" attackerCountry :NAME " + attackerCountry);
		System.out.println(" defenderCountry :NAME " + defenderCountry);
	
	System.out.println("No of armies in the defender country : " + StartUpPhase.countriesArmies.get(defenderCountry));

	if (StartUpPhase.countriesArmies.get(attackerCountry) >= 2) {
		System.out.println("As, You have minimum of 2 armies, you can attack");
		if (StartUpPhase.countriesArmies.get(attackerCountry) > 3) {
			System.out.println("Attacker Country Player, can opt among 1, 2 or 3 dice for rolling");
			noOfAttackerDice = r.nextInt(3 - 1 + 1) + 1;
			System.out.println("No of Dice chosen by attacker is :"+noOfAttackerDice);
		} else if (StartUpPhase.countriesArmies.get(attackerCountry) == 3) {
			System.out.println("Attacker Country Player, can opt among 1 or 2  dice for rolling");
			noOfAttackerDice = r.nextInt(2 - 1 + 1) + 1;
			System.out.println("No of Dice chosen by attacker is :"+noOfAttackerDice);			
		} else if (StartUpPhase.countriesArmies.get(attackerCountry) == 2) {
			System.out.println(
					"Attacker Country Player, can have only 1 dice to roll, as you have only 2 army on country : "
							+ attackerCountry);			
			noOfAttackerDice = 1;
		}
		if (noOfAttackerDice == 2 || noOfAttackerDice == 3)
			flagCheckDice = 1;
		attackerDiceArray = new int[noOfAttackerDice];
		for (int i = 0; i < attackerDiceArray.length; i++) {
			attackerDiceArray[i] = randomNumberGenerator();
		}
		if (StartUpPhase.countriesArmies.get(defenderCountry) >= 2) {
			System.out.println("Defender Country Player, can opt among 1 or 2 dice to roll");
			noOfDefenderDice = r.nextInt(2 - 1 + 1) + 1;
			System.out.println(noOfDefenderDice);
		} else {
			System.out.println(
					"Defender Country Player, can have only 1 dice to roll, as you have only 1 army on country : "
							+ defenderCountry);			
			noOfDefenderDice = 1;
		}
		defenderDiceArray = new int[noOfDefenderDice];
		for (int i = 0; i < defenderDiceArray.length; i++) {
			defenderDiceArray[i] = randomNumberGenerator();
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
				"Number of armies in Attacker Country is : " + StartUpPhase.countriesArmies.get(attackerCountry));		
		System.out.println(
				"Number of armies in Defender Country is : " + StartUpPhase.countriesArmies.get(defenderCountry));		
		//	Condition for defender dice as 1
		if (noOfDefenderDice == 1) {

			if (attackerDiceArray[attackerDiceArray.length - 1] > defenderDiceArray[defenderDiceArray.length - 1]) {
				// IF Condition for ======ACD(0) > DCD(0)==== for defender dice == 1
				System.out.println(" Defender country loose 1 army");				
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
					//ON WINNING A GAME LOGIC
					if(newCOuntryListSize == LoadMap.countryFilter.size()){
						System.out.println("WooHooo you conquered the whole world map");
						conqueredMapCounter = 1;
					}

				} else {
					StartUpPhase.countriesArmies.put(defenderCountry, updateArmyOfDefender);
				}
				System.out.println("new list of initial player country" + StartUpPhase.initialPlayerCountry);				
				System.out.println("Initailly player country list with initial army : " + StartUpPhase.countriesArmies);
			} else {// ELSE Condition for ======ACD(0) < DCD(0)==== for defender dice == 1
				System.out.println(" Attacker country loose 1 army");				
				updateArmyOfAttacker = StartUpPhase.countriesArmies.get(attackerCountry) - 1;
				StartUpPhase.countriesArmies.put(attackerCountry, updateArmyOfAttacker);
				System.out.println("new list of initial player country" + StartUpPhase.initialPlayerCountry);				
				System.out.println("Initailly player country list with initial army : " + StartUpPhase.countriesArmies);				
			}
		} // end of defenderdice ==1
		// Condition starting for defender dice value chosen as 2
		else {

			if (attackerDiceArray[attackerDiceArray.length - 1] > defenderDiceArray[defenderDiceArray.length - 1] && conqueredMapCounter ==0) {
				// IF Condition for ======ACD(1) > DCD(1)==== for defender dice == 2				
				System.out.println(" Defender country loose 1 army");				
				updateArmyOfDefender = StartUpPhase.countriesArmies.get(defenderCountry) - 1;
				StartUpPhase.countriesArmies.put(defenderCountry, updateArmyOfDefender);

				if (attackerDiceArray[attackerDiceArray.length - 2] > defenderDiceArray[defenderDiceArray.length - 2]) {
					// IF Condition for ======ACD(0) > DCD(0)==== for defender dice == 2
					System.out.println(" Defender country loose 1 more army");
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
						//ON WINNING A GAME LOGIC
						if(newCOuntryListSize == LoadMap.countryFilter.size()){
							System.out.println("WooHooo you conquered the whole world map");
							conqueredMapCounter = 1;
						}
					} else {
						StartUpPhase.countriesArmies.put(defenderCountry, updateArmyOfDefender);
					}
					System.out.println("new list of initial player country" + StartUpPhase.initialPlayerCountry);
					System.out.println(
							"Initailly player country list with initial army : " + StartUpPhase.countriesArmies);
				} else {// ELSE Condition for ======ACD(0) <= DCD(0)==== for defender dice == 2
					System.out.println(" Attacker country loose 1 army");					
					updateArmyOfAttacker = StartUpPhase.countriesArmies.get(attackerCountry) - 1;
					StartUpPhase.countriesArmies.put(attackerCountry, updateArmyOfAttacker);
					System.out.println("new list of initial player country" + StartUpPhase.initialPlayerCountry);					
					System.out.println(
							"Initailly player country list with initial army : " + StartUpPhase.countriesArmies);
				}
			} else {
				if (attackerDiceArray[attackerDiceArray.length - 1] <= defenderDiceArray[defenderDiceArray.length
						- 1]) {
					// IF Condition for ======ACD(1) <= DCD(1)==== for defender dice == 2
					System.out.println(" Attacker country loose 1 more army");					
					updateArmyOfAttacker = StartUpPhase.countriesArmies.get(attackerCountry) - 1;
					StartUpPhase.countriesArmies.put(attackerCountry, updateArmyOfAttacker);
										
					if (flagCheckDice ==1 && attackerDiceArray[attackerDiceArray.length- 2] <= defenderDiceArray[defenderDiceArray.length - 2]) {
						// IF 
						// flagCheckDice on attacker chooses either 2 or 3 dice for attack
						//	Condition for ======ACD(0) on choosing dice as 2
						//	Condition for ======ACD(1) on choosing dice as 3
						//	ACD(0)/ACD(1) <= DCD(0)==== for defender dice == 2
						System.out.println(" Attacker country loose 1 more army");						
						updateArmyOfAttacker -= 1;
						StartUpPhase.countriesArmies.put(attackerCountry, updateArmyOfAttacker);

					} else if(flagCheckDice==1 && attackerDiceArray[attackerDiceArray.length- 2] >= defenderDiceArray[defenderDiceArray.length - 2]) {
						//	Else condition ACD(0)/ACD(1) > DCD(0)==== for defender dice == 2
						System.out.println(" Defender country loose 1 more army");
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

							//ON WINNING A GAME LOGIC
							if(newCOuntryListSize == LoadMap.countryFilter.size()){
								System.out.println("WooHooo you conquered the whole world map");
								conqueredMapCounter = 1;
							}
							
						} else {
							StartUpPhase.countriesArmies.put(defenderCountry, updateArmyOfDefender);
						}

					}
					StartUpPhase.countriesArmies.put(attackerCountry, updateArmyOfAttacker);
					System.out.println("new list of initial player country" + StartUpPhase.initialPlayerCountry);
					System.out.println(
							"Initailly player country list with initial army : " + StartUpPhase.countriesArmies);				
				}

			}
		}
		randomCountAttack--;
	} else
		System.out.println("As you are having only 1 army, you can't attack");
		randomCountAttack--;
	if(randomCountAttack == 0)
		{
		
		newCOuntryListSize = StartUpPhase.initialPlayerCountry.get(playerName.getName()).size();
		System.out.println("===========newCOuntryListSize :========== " + newCOuntryListSize);
		//loggingString("===========newCOuntryListSize :========== " + newCOuntryListSize);		

		if (newCOuntryListSize > oldCOuntryListSize) {
			


			//=================================================//
			//=========Do Card Distribution Logic on===========//
			//=========winning a country during attack=========//
			//=================================================//
			// NOT WOKRING NOW	//
			// NOT WOKRING NOW	//
			// NOT WOKRING NOW	//
			
			// code for card need to be done here
			
			int indexOfCardToBeGet = (int) (Math.random() * (cardsInTheDeck.size() - 0));
			List<String> temp = new ArrayList<String>(playersCards.get(playerName.getName()));
			System.out.println(cardsInTheDeck);
			temp.add(cardsInTheDeck.get(indexOfCardToBeGet));
			System.out.println("temp " + temp);
			System.out.println("cards in the deck " + cardsInTheDeck);
			playersCards.put(playerName.getName(), temp);
			temp.clear();
			cardsInTheDeck.remove(indexOfCardToBeGet);
			}

//		countriesArmiesObserver.putAll(StartUpPhase.countriesArmies);
//		initialPlayerCountryObserver.putAll(StartUpPhase.initialPlayerCountry);
		fortifyPhase(playerName);
		}
	else
		attackPhase(playerName);
}// end of attackPhase

	 
	/** This is a random generator method
	 * for generating random number in a dice
	 * @return
	 */
	public static int randomNumberGenerator() {
		int randomNumber;
		Random random = new Random(); /* <-- this is a constructor */
		randomNumber = random.nextInt(6)
				+ 1; 
		return randomNumber;
	}
	
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
			System.out.println("The name of a RANDOM country from which you want to move armies.");
			Random r = new Random();
			int indexFROM  = r.nextInt(StartUpPhase.initialPlayerCountry.get(currentPlayer.getName()).size());
			from =  StartUpPhase.initialPlayerCountry.get(currentPlayer.getName()).get(indexFROM);
			System.out.println("Choosen Country as FROM : " + from);
			System.out.println("No of armies in the FROM country : " + StartUpPhase.countriesArmies.get(from));		

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
			System.out.println("Select the countries from your owned countries  and adjacent to " + from
					+ " where you want to move your armies");
			//loggingString("Select the countries from your owned countries  and adjacent to " + from					+ " where you want to move your armies");
			int indexTO  = r.nextInt(tempAdjCountryToWhichWeCanMOve.size());
			to =  tempAdjCountryToWhichWeCanMOve.get(indexTO);
			System.out.println(" TO country Name : " + to);
			int numberOfArmiesToMove = 0;
			System.out.println(currentPlayer.getName() + " you have " + StartUpPhase.countriesArmies.get(from)
			+ " armies on " + from + ".");
			if (StartUpPhase.countriesArmies.get(from) > 1) {
				System.out.println("The randomly choosen number of armies you want to move to " + "" + to + " " + "country.s");				
				for (Entry<String, Integer> entry : StartUpPhase.countriesArmies.entrySet()) {
					String key = entry.getKey();
					Integer value = entry.getValue();
					if(from.equals(key)){
						numberOfArmiesToMove = value-1;	
						numberOfArmiesToMove = r.nextInt(numberOfArmiesToMove - 1 + 1) + 1;
					}
				}
				int currentArmiesOfToCountry = StartUpPhase.countriesArmies.get(to);
				int newArmiesToAdd = currentArmiesOfToCountry + numberOfArmiesToMove;
				StartUpPhase.countriesArmies.put(to, newArmiesToAdd);
				int currentArmiesOfFromCountry = StartUpPhase.countriesArmies.get(from);
				int newArmiesToDelete = currentArmiesOfFromCountry - numberOfArmiesToMove;
				StartUpPhase.countriesArmies.put(from, newArmiesToDelete);
				System.out.println(from + " = " + StartUpPhase.countriesArmies.get(from));
				System.out.println(to + " = " + StartUpPhase.countriesArmies.get(to) + "\n");
				StartUpPhase.nextPlayer();
				placeReinforcementArmies(currentPlayer);
			}
			else
			{
				System.out.println("You dont have sufficient number of armies to move from " + from	+ " country.");
				StartUpPhase.nextPlayer();
				placeReinforcementArmies(currentPlayer);				
			}
		}// end of fortify phase	
}