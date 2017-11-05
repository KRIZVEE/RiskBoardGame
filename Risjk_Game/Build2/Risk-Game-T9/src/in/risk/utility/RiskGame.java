package in.risk.utility;

import java.io.BufferedReader;

//package in.risk.utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

/**
 * This class contains all the methods related to game start up phase,
 * reinforcement phase and fortify phase
 * 
 * @author Charanpreet Singh, Ishan Kansara, Kashif Rizvee, Mohit Rana
 * @version 1.0.0
 */
public class RiskGame extends Observable {

	public static String css = "file:///E:/Git/RiskGame/src/in/risk/gui/application.css";
	public static String logoPath = "file:///E:/Git/RiskGame/resources/Risk_logo.png";

	public static ArrayList<String> continentFilterNew = new ArrayList<String>();
	public static List<String> newList;
	public static ArrayList<String> countryFilter = new ArrayList<String>();
	public static ArrayList<String> adjFilter = new ArrayList<String>();
	public static HashMap<String, List<String>> map = new HashMap<String, List<String>>();
	public static HashMap<String, List<String>> adj = new HashMap<String, List<String>>();
	public static HashMap<String, String> countryContinent = new HashMap<String, String>();
	public static HashMap<String, List<String>> continentCountries = new HashMap<String, List<String>>();
	public static ArrayList<String> mapDetail = new ArrayList<String>();
	public static ArrayList<String> valueList = new ArrayList<String>();
	public static ArrayList<String> xPoints = new ArrayList<String>();
	public static ArrayList<String> yPoints = new ArrayList<String>();
	public static ArrayList<Boolean> resultOfContinentCountry = new ArrayList<Boolean>();
	public static HashMap<String, Integer> continentValue = new HashMap<String , Integer>();
	public static ArrayList<String> cardType = new ArrayList<String>();
	public static ArrayList<String> deck = new ArrayList<String>();
	public static HashMap<String, List<String>> playersCards = new HashMap<String, List<String>>();
	public static HashMap<String, String> playersContinent = new HashMap<String, String>();
	public static HashMap<String, List<String>> countryCoordinates = new HashMap<String, List<String>>();
	public static List<String> cardsInTheDeck = new ArrayList<String>();

	Scanner sc = new Scanner(System.in);

	// @SuppressWarnings("unused")
	static public Vector<Player> players = new Vector<Player>();

	public ArrayList<String> continentList = new ArrayList<String>();
	// public ArrayList<String> countryFilter = new ArrayList<String>();
	public static int nuberOfCardsInDeck = 0;

	public HashMap<String, List<String>> continentsMap = new HashMap<String, List<String>>();
	public HashMap<String, List<String>> adjacentsMap = new HashMap<String, List<String>>();

	public ArrayList<String> initialCountries = new ArrayList<String>();

	public Vector<String> adjacents;
	public HashMap<String, ArrayList<String>> initialPlayerCountry = new HashMap<String, ArrayList<String>>();
	public HashMap<String, Integer> countriesArmies = new HashMap<String, Integer>();
	public HashMap<String, Integer> playerTurn = new HashMap<String, Integer>();

	public Player currentPlayer;
	public Player active;

	public static String cardTypeA = "A";
	public static String cardTypeB = "B";
	public static String cardTypeC = "C";
	public static int cardInTheDeck;
	public static int cardFlag = 1;
	int noOfPlayers = 0;
	public int card = 0;
	public int c = 0;
	public int iter = 0;
	public int noOfReinforcementArmies = 0;
	public boolean drawn;
	public int beforeA, currentA, afterA;
	
	//Added global variables for attack phase to find old and new country list size
	int oldCOuntryListSize = 0;
	int newCOuntryListSize = 0;
	
	//Added global variables for observer pattern for showing world dominance
	
	int currentPlayerCountrySize = 0;
	double playerDominanceInPercent = 0;
	

	

	/**
	 * // * This is the main method // * // * @throws IOException //
	 */
	// public static void main(String[] args) throws IOException {
	// System.out.println("Hello to Risk Game");
	// RiskGame risk = new RiskGame();
	// risk.startGame("C:\\Users\\mohit\\Desktop\\Risjk_Game\\World.map");
	// }

	/**
	 * This method is called to play the game.
	 */
	public void startGame(String Path) throws IOException {
		mapLoader(Path);
		selectPlayers();
		initialPlayer();
		initialPlayerCountries();
		distributeArmies();
		placeCardsInTheDeck();
		placeCardsInTheDeck2();
		initialCardDistribution();
		initiallyPlaceArmies();
//		System.out.println(playersCards.get(currentPlayer.getName()));
//		getArmiesFromCards();
		// placeArmies();
		gamePhase();
		// distributeCards();
		// playCards();
		// fortify();
	}

	/**
	 * This method loads the map selected by the user.
	 * <ul>
	 * This method gives all the important elements needed from map to play the
	 * game.
	 */
	public static void mapLoader(String pathMap) {

		try {
			// String path = "NewRiskGame/resources/maps/" + pathMap; old path
			// for build 1 game
			String path = "Resources/World.map";// my new path
			FileInputStream file = new FileInputStream(path);

			boolean done = false;
			String next = null;

			Scanner mapfile = new Scanner(file);

			while (mapfile.hasNextLine()) {
				done = false;
				next = mapfile.nextLine();
				String[] arr;

				if (next.equals("[Map]")) {
					next = mapfile.nextLine();
					do {
						mapDetail.add(next);
						next = mapfile.nextLine();
					} while (!next.equals(""));
				}
				if (next.equals("[Continents]")) {
					next = mapfile.nextLine();
					do {
						String[] parts = next.split("=");
						continentFilterNew.add(parts[0]);
						valueList.add(parts[1]);
						next = mapfile.nextLine();
					} while (!next.equals(""));
				}

				if (next.equals("[Territories]")) {
					next = mapfile.nextLine();
					do {
						String[] parts = next.split(",");
						newList = Arrays.asList(parts);
						String part1 = parts[0];

						map.put(part1, newList);
						countryFilter.add(part1);

						next = mapfile.nextLine();
					} while (!next.equals(" "));
				}
				file.close();
			}
			for (int i = countryFilter.size(); i > 0; i--) {
				if (countryFilter.contains("")) {
					countryFilter.remove("");
				}
			}
			if (map.containsKey("")) {
				map.remove("");
			}
			for (Entry<String, List<String>> entry : map.entrySet()) {
				String key = entry.getKey();
				List value = entry.getValue();
				List<String> tempList = new ArrayList<String>();
				tempList.add(value.get(1).toString());
				tempList.add(value.get(2).toString());
				countryCoordinates.put(key, tempList);

				List<String> subList = value.subList(4, value.size());
				String continentName = value.get(3).toString();
				adj.put(key, subList);
				countryContinent.put(key, continentName);
			}
			for (Entry<String, String> entry : countryContinent.entrySet()) {
				String Key = entry.getKey();
				String value = entry.getValue();
				fun(value, Key);
			}

			/*
			 * System.out.println(countryCoordinates.get("Northwest Territory"))
			 * ; System.out.println("continent = " + continentFilterNew);
			 * System.out.println("countries = " + countryFilter);
			 * System.out.println("Adjacency = " + adj);
			 * System.out.println("Countries of each continent = " +
			 * continentCountries);
			 */
			mapfile.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * this method is used to iterate over the hashmap to obtain particular
	 * values </br>
	 * and then use them to make another hasmap with same key.
	 */
	public static HashMap<String, List<String>> fun(String Key, String Value) {

		if (continentCountries.containsKey(Key)) {
			List<String> values = continentCountries.get(Key);
			values.add(Value);
		} else {
			List<String> values = new ArrayList<String>();
			values.add(Value);
			continentCountries.put(Key, values);
		}
		return continentCountries;
	}

	/**
	 * This method is about selecting players between 3 and 6
	 */
	public void selectPlayers() throws IOException {
		Scanner sc = new Scanner(System.in);
		Scanner sc2 = new Scanner(System.in);
		int noOfPlayers;
		String playerName;

		System.out.println("Please enter how many players you want to play with, Choose between 3 and 6");
		noOfPlayers = sc.nextInt();
		// SelectPlayerLogic(String sc , String sc2, int numofplyrs, String
		// Plyrname);
		if (noOfPlayers == 3) {
			for (int i = 0; i < 3; i++) {
				System.out.println("Please enter Name for your player no :" + (i + 1));
				playerName = sc2.nextLine();
				addPlayer(playerName);
				playerTurn.put(playerName, 1);
			}
		} else if (noOfPlayers == 4) {
			for (int i = 0; i < 4; i++) {
				System.out.println("Please enter Name for your player no :" + (i + 1));
				playerName = sc2.nextLine();
				addPlayer(playerName);
				playerTurn.put(playerName, 1);
			}
		} else if (noOfPlayers == 5) {
			for (int i = 0; i < 5; i++) {
				System.out.println("Please enter Name for your player no :" + (i + 1));
				playerName = sc2.nextLine();
				addPlayer(playerName);
				playerTurn.put(playerName, 1);
			}
		} else if (noOfPlayers == 6) {
			for (int i = 0; i < 6; i++) {
				System.out.println("Please enter Name for your player no :" + (i + 1));
				playerName = sc2.nextLine();
				addPlayer(playerName);
				playerTurn.put(playerName, 1);
			}
		} else {
			System.out.println("Please enter a valid no of players to play with");
		}
	}

	/**
	 * This method adds player when number of player selected initially during
	 * <ul>
	 * select player method
	 * 
	 * @param nm
	 * @return Returns false if number of player selected is greater than 6
	 */
	public static boolean addPlayer(String nm) throws IOException {
		int size = players.size();
		if (size > 6) {
			return false;
		}
		Player p = new Player(nm, size);
		players.add(p);
		return true;
	}

	/**
	 * This method stores current player which is at index 0
	 */
	public void initialPlayer() throws IOException {
		currentPlayer = players.elementAt(0);
	}

	/**
	 * This method distributes number of armies as per number of players
	 * selected
	 */
	public void distributeArmies() throws IOException {
		int numberOfPlayers = players.size();
		int armies = 0;

		if (numberOfPlayers == 2)
			armies = 40;
		if (numberOfPlayers == 3)
			armies = 15;
		// armies = 35;
		if (numberOfPlayers == 4)
			armies = 30;
		// armies = 30;
		if (numberOfPlayers == 5)
			armies = 25;
		if (numberOfPlayers == 6)
			armies = 20;
		for (int i = 0; i < numberOfPlayers; i++) {
			players.elementAt(i).addArmies(armies);
		}
	}

	/**
	 * This method calls next player in the game and also calls method for play
	 * cards
	 */
	public void nextPlayer() throws IOException {
		if (currentPlayer == players.lastElement()) {
			currentPlayer = players.elementAt(0);
			iter = 0;
		} else
			currentPlayer = players.elementAt(++iter);
		// if (card == 1)
		// playCards();
	}

	/**
	 * This method allocates countries randomly and add it to the player's list
	 */
	public void initialPlayerCountries() throws IOException {
		int i = 0;
		int j = countryFilter.size() - 1;
		while (j >= 0) {
			initialCountries.add(countryFilter.get(j));
			players.get(i);

			ArrayList<String> list;
			if (initialPlayerCountry.containsKey(players.get(i).getName())) {
				list = initialPlayerCountry.get(players.get(i).getName());
				list.add(countryFilter.get(j));
			} else {
				list = new ArrayList<String>();
				list.add(countryFilter.get(j));
				initialPlayerCountry.put(players.get(i).getName(), list);
			}
			i++;
			j--;
			if (i == players.size()) {
				i = 0;
			}
		}
	}

	/**
	 * This method is creating a new hasmap conatining all countries with 0
	 * number of armies. This hashmap is used in placing armies to countries in
	 * next step one in the game logic.
	 * 
	 * @throws IOException
	 */
	public void initiallyPlaceArmies() throws IOException {
		int totalNumberOfCountries = countryFilter.size();
		for (int i = 0; i < totalNumberOfCountries; i++) {
			countriesArmies.put(countryFilter.get(i), 20);
		}
	}

	/**
	 * This method is about placing armies in round robin fashion by asking
	 * every player.
	 * 
	 * @throws IOException
	 */
	public void placeArmies() throws IOException {
		int loopSize = players.size() * currentPlayer.getArmies();
		int updatedArmies;
		Scanner sc = new Scanner(System.in);
		String result;
		ArrayList<String> temp = new ArrayList<>();
		System.out.println("start here  :  ");
		for (int i = 1; i < loopSize + 1; i++) {

			for (Entry<String, Integer> entry : countriesArmies.entrySet()) {
				String Key = entry.getKey();
				Integer value = entry.getValue();
				for (int j = 0; j < initialPlayerCountry.get(currentPlayer.getName()).size(); j++) {
					if (initialPlayerCountry.get(currentPlayer.getName()).contains(Key)) {
						if (value == 0) {

							if (!temp.contains(Key))
								temp.add(Key);
						}
					}
				}
			}
			// System.out.println("Current Player : " + currentPlayer.getName()
			// + " has these many countries with 0 armies " + temp);

			System.out.println(currentPlayer.getName() + " you own these countries "
					+ initialPlayerCountry.get(currentPlayer.getName()) + ".");
			System.out.println("Please enter the name of the country you want to add armies to!!");
			result = sc.nextLine();

			if (initialPlayerCountry.get(currentPlayer.getName()).contains(result)) {
				int noOfArmiesPlayerContains = currentPlayer.getArmies();
				int countriesWith0Armies = temp.size();

				if (noOfArmiesPlayerContains == 0) {

					System.out.println("noOfArmiesPlayerContains == 0");

					if (players.size() != 3) {
						nextPlayer();
						break;
					} else
						gamePhase();
				}

				if (noOfArmiesPlayerContains == countriesWith0Armies) {
					System.out.println(currentPlayer.getArmies());
					System.out.println(countriesArmies);

					if (temp.contains(result))// result entered country is in
					// temp list)
					{
						updatedArmies = countriesArmies.get(result) + 1;
						countriesArmies.put(result, updatedArmies);
						currentPlayer.looseArmy();
						System.out.println(result + " armies has been updated. New armies of " + result + " are "
								+ countriesArmies.get(result));
						temp.remove(result);
						System.out.println(currentPlayer.getName() + " has Countries with 0 number of armies " + temp);
						temp.clear();
						nextPlayer();
					}

					else {

						System.out.println("You are not allowed to add armies to other countries except " + temp
								+ " countries. \n Because you have minimum number of armies to place ermies in each countyr");
						System.out.println(
								"Please enter the name of country from given list where you want to placce armies \n"
										+ temp);
						result = sc.nextLine();
						if (temp.contains(result)) {
							updatedArmies = countriesArmies.get(result) + 1;
							countriesArmies.put(result, updatedArmies);
							currentPlayer.looseArmy();
							System.out.println(result + " armies has been updated. New armies of " + result + " are "
									+ countriesArmies.get(result));
							temp.remove(result);
							System.out.println(
									currentPlayer.getName() + " has Countries with 0 number of armies " + temp);
							temp.clear();
							nextPlayer();
						}
						// here should be else part

					}

				} else {
					updatedArmies = countriesArmies.get(result) + 1;
					countriesArmies.put(result, updatedArmies);
					currentPlayer.looseArmy();
					System.out.println(result + " armies has been updated. New armies of " + result + " are "
							+ countriesArmies.get(result));
					temp.remove(result);
					System.out.println((currentPlayer.getName() + " has Countries with 0 number of armies " + temp));
					temp.clear();
					nextPlayer();
				}

			} else {
				System.out.println("Please enter correct name of the country");
				i--;
			}

			System.out.println("I value is  : " + i);

		}
		// System.out.println(players.size() * currentPlayer.getArmies());
		// System.out.println(currentPlayer.getName() + " " +
		// initialPlayerCountry.get(currentPlayer.getName()) + " "
		// + currentPlayer.getArmies());
		// sc.close();
	}

	/**
	 * This method is used to select the phase by the current player.
	 * @return
	 */
	public int selectPhase() {
		Scanner sc = new Scanner(System.in);
		int result;
		System.out.println(currentPlayer.getName() + ". Please select what you want to do next in the game. \n");
		System.out.println("1. Reinforcement. \n");
		System.out.println("2. Attack \n");
		System.out.println("3. Fortify \n");
		result = sc.nextInt();
		// sc.close();
		return result;
	}

	/**
	 * This method is used to implement the different game phases.
	 * @throws IOException
	 */
	public void gamePhase() throws IOException {
		int x = selectPhase();
		System.out.println(x);

		if (x == 1)
			reinforcementPhase();
		else if (x == 2)
			attackPhase();
		else
			fortify();
	}

	/**
	 * This method is regarding implementation of observer pattern for world domination percentage
	 * @return This method returns number of countries owned by a player after loosing/winning a country 
	 * in form of percentage and also numbers
	 */
	public HashMap<String,ArrayList<String>> getWorldDominance()
	{
		System.out.println("Total country size: " +countryFilter.size());
		for (Entry<String, ArrayList<String>> entry : initialPlayerCountry.entrySet()) 
			{
			String key = entry.getKey();
			ArrayList<String> value = entry.getValue();
			double totalCountryListSize = countryFilter.size();
			currentPlayerCountrySize = initialPlayerCountry.get(key).size();
			playerDominanceInPercent = (currentPlayerCountrySize / totalCountryListSize) * 100;
			System.out.println("Player: " +key +" " +"No of countries for players: " +initialPlayerCountry.get(key).size());
			System.out.println("Player: " +key +" " +"World dominance in percentage: " +playerDominanceInPercent);
		
		}
		return initialPlayerCountry;
		
	}
	
	/**
	 * This method is used to generate the random number.
	 */
	public int randomNumberGenerator() {
		int randomNumber;
		Random random = new Random(); /* <-- this is a constructor */
		randomNumber = random.nextInt(6)
				+ 1; /*
						 * <-- look at the API doc for nextInt() to see why we
						 * give it 6 as and argument, and why we need to add 1
						 * to the result
						 */
		return randomNumber;
	}

	public static void placeCardsInTheDeck(){
		cardType.add(cardTypeA);
		cardType.add(cardTypeB);
		cardType.add(cardTypeC); 
		int j = 0;
		cardInTheDeck = countryFilter.size();
		for(int i = 0; i < cardInTheDeck ; i++){
			deck.add(cardType.get(j));
			j++;
			if(j == 3){
				j = 0;
			}
		}
	}
	
	public static void placeCardsInTheDeck2(){
		cardType.add(cardTypeA);
		cardType.add(cardTypeB);
		cardType.add(cardTypeC); 
		int j = 0;
		cardInTheDeck = countryFilter.size();
		for(int i = 0; i < cardInTheDeck ; i++){
			cardsInTheDeck.add(cardType.get(j));
			j++;
			if(j == 3){
				j = 0;
			}
		}
	}
	
	public void initialCardDistribution(){
		int size = players.size();
		for (int i = 0; i < size; i++) {
			playersCards.put(players.get(i).getName(),deck.subList(0, deck.size()/5));
		}
	}
	
	public void checkUniqueCombination(int tempSize) throws IOException {
		List<Integer> tempListA = new ArrayList<Integer>();
		List<Integer> tempListB = new ArrayList<Integer>();
		List<Integer> tempListC = new ArrayList<Integer>();
		for (int i = 0; i <tempSize ; i++) {
			if(playersCards.get(currentPlayer.getName()).get(i).contains("A")) {
				tempListA.add(i);
			}
		}
		for (int i = 0; i <tempSize ; i++) {
			if(playersCards.get(currentPlayer.getName()).get(i).contains("B")) {
				tempListB.add(i);
			}
		}
		for (int i = 0; i <tempSize ; i++) {
			if(playersCards.get(currentPlayer.getName()).get(i).contains("C")) {
				tempListC.add(i);
			}
		}
		if(tempListA.size() >= 3) {
			int firstIndexToRemove = tempListA.get(0);
			int secondIndexToRemove = tempListA.get(1);
			int thirdIndexToRemove = tempListA.get(2);
			cardsInTheDeck.add("A");
			cardsInTheDeck.add("A");
			cardsInTheDeck.add("A");
			playersCards.get(currentPlayer.getName()).remove(firstIndexToRemove);
			playersCards.get(currentPlayer.getName()).remove(secondIndexToRemove-1);
			playersCards.get(currentPlayer.getName()).remove(thirdIndexToRemove-2);
			noOfReinforcementArmies += 5*cardFlag;
			cardFlag +=1;
			tempListA.clear();
			getArmiesFromCards();
		}
		else if(tempListB.size() >= 3) {
			int firstIndexToRemove = tempListB.get(0);
			int secondIndexToRemove = tempListB.get(1);
			int thirdIndexToRemove = tempListB.get(2);
			cardsInTheDeck.add("B");
			cardsInTheDeck.add("B");
			cardsInTheDeck.add("B");
			playersCards.get(currentPlayer.getName()).remove(firstIndexToRemove);
			playersCards.get(currentPlayer.getName()).remove(secondIndexToRemove-1);
			playersCards.get(currentPlayer.getName()).remove(thirdIndexToRemove-2);
			noOfReinforcementArmies += 5*cardFlag;
			cardFlag +=1;
			tempListB.clear();
			getArmiesFromCards();
		}else if(tempListC.size() >= 3) {
			int firstIndexToRemove = tempListC.get(0);
			int secondIndexToRemove = tempListC.get(1);
			int thirdIndexToRemove = tempListC.get(2);
			cardsInTheDeck.add("C");
			cardsInTheDeck.add("C");
			cardsInTheDeck.add("C");
			playersCards.get(currentPlayer.getName()).remove(firstIndexToRemove);
			playersCards.get(currentPlayer.getName()).remove(secondIndexToRemove-1);
			playersCards.get(currentPlayer.getName()).remove(thirdIndexToRemove-2);
			noOfReinforcementArmies += 5*cardFlag;
			cardFlag +=1;
			tempListC.clear();
			getArmiesFromCards();
		}
	}

	public void checkDiscreteCombination() {
		List<Integer> tempListABCFirst = new ArrayList<Integer>();
		List<Integer> tempListABCSecond = new ArrayList<Integer>();
		int flagForA = 0;
		int flagForB = 0;
		int flagForC = 0;
		String value;
		for(int i = 0; i < playersCards.get(currentPlayer.getName()).size(); i++) {
			value = playersCards.get(currentPlayer.getName()).get(i);
			if(value.equals("A")) {
				if(flagForA <= 1) {
					if(flagForA == 0) {
						tempListABCFirst.add(i);
					}else if(flagForA == 1) {
						tempListABCSecond.add(i);
					}
					flagForA += 1;
				}
			}else if(value.equals("B")) {
				if(flagForB <= 1) {
					if(flagForB == 0) {
						tempListABCFirst.add(i);
					}else if(flagForB == 1) {
						tempListABCSecond.add(i);
					}
					flagForB += 1;
				}
				
			}else if(value.equals("C")) {
				if(flagForC <= 1) {
					if(flagForC == 0) {
						tempListABCFirst.add(i);
					}else if(flagForC == 1) {
						tempListABCSecond.add(i);
					}
					flagForC += 1;
				}
				
			}
	}
		if(tempListABCFirst.size() >= 3) {
			int firstIndexToRemove = tempListABCFirst.get(0);
			int secondIndexToRemove = tempListABCFirst.get(1);
			int thirdIndexToRemove = tempListABCFirst.get(2);
			cardsInTheDeck.add(playersCards.get(currentPlayer.getName()).get(firstIndexToRemove));
			playersCards.get(currentPlayer.getName()).remove(firstIndexToRemove);
			cardsInTheDeck.add(playersCards.get(currentPlayer.getName()).get(secondIndexToRemove-1));
			playersCards.get(currentPlayer.getName()).remove(secondIndexToRemove-1);
			cardsInTheDeck.add(playersCards.get(currentPlayer.getName()).get(thirdIndexToRemove-2));
			playersCards.get(currentPlayer.getName()).remove(thirdIndexToRemove-2);
			noOfReinforcementArmies += 5*cardFlag;
			cardFlag += 1;
		}
		if(tempListABCSecond.size() >= 3) {
			int fourthIndexToRemove = tempListABCSecond.get(0);
			int fifthIndexToRemove = tempListABCSecond.get(1);
			int sixthINdexToRemove = tempListABCSecond.get(2);
			cardsInTheDeck.add(playersCards.get(currentPlayer.getName()).get(fourthIndexToRemove-3));
			playersCards.get(currentPlayer.getName()).remove(fourthIndexToRemove-3);
			cardsInTheDeck.add(playersCards.get(currentPlayer.getName()).get(fifthIndexToRemove-4));
			playersCards.get(currentPlayer.getName()).remove(fifthIndexToRemove-4);
			cardsInTheDeck.add(playersCards.get(currentPlayer.getName()).get(sixthINdexToRemove-5));
			playersCards.get(currentPlayer.getName()).remove(sixthINdexToRemove-5);
			noOfReinforcementArmies += 5*cardFlag;
			cardFlag += 1;
		}
	}
	
	/**
	 * This method is about trading of cards and getting armies in return.
	 * Armies added will be as per the game logic.
	 */
	public void getArmiesFromCards() throws IOException {
		
		System.out.println(playersCards.get(currentPlayer.getName()));
		int tempSize = playersCards.get(currentPlayer.getName()).size();
		
//				System.out.println(playersCards.get(currentPlayer.getName()));
		if(tempSize >= 5) {
			checkUniqueCombination(tempSize);
			checkDiscreteCombination();
		}else {
			String result;
			Scanner sc = new Scanner(System.in);
			System.out.println("Now you have less than 5 cards. Do you want to play cards now? Y/N");
			result = sc.nextLine();
			if(result.equals("Y")) {
				checkUniqueCombination(playersCards.get(currentPlayer.getName()).size());
				checkDiscreteCombination();
			}
		}
	}
	
	/**
	 * This method is used to get reinforcement armies from owned
	 * continents.
	 */
	public void getArmiesaFromContinet() {
		for (Entry<String, List<String>> entry : continentCountries.entrySet()) {
			String Key = entry.getKey();
			List<String> value = entry.getValue();
//			System.out.println(value);
			for (int i = 0; i < initialPlayerCountry.get(currentPlayer.getName()).size(); i++) {
				resultOfContinentCountry.add(value.contains(initialPlayerCountry.get(currentPlayer.getName()).get(i)));
			}
//			System.out.println(initialPlayerCountry.get(currentPlayer.getName()));
			if(resultOfContinentCountry.contains(false)) {
				noOfReinforcementArmies = noOfReinforcementArmies + 0;
			}else if(resultOfContinentCountry.contains(true)) {
				noOfReinforcementArmies = noOfReinforcementArmies + continentValue.get(Key);
			}
			resultOfContinentCountry.clear();
		}
	}
	
	/**
	 * This method is used to get reinforcement armies from owned
	 * countries.
	 */
	public void getArmiesFromCountries() {
		int sizeOfCountriesCurrentPlayerOwn = initialPlayerCountry.get(currentPlayer.getName()).size();

		if(sizeOfCountriesCurrentPlayerOwn < 9) {
			noOfReinforcementArmies = noOfReinforcementArmies + 3;
		}else {
			noOfReinforcementArmies = noOfReinforcementArmies + sizeOfCountriesCurrentPlayerOwn/3;
		}
	}
	
	/**
	 * This method places armies in their countries as per reinforcement phase
	 * of the game.
	 */
	public void placeReinforcementArmies() throws IOException {
		
		
		// Start Of counting reinforcement armies from continet value.
		getArmiesaFromContinet();
		int noOfArmiesFromContinents = noOfReinforcementArmies;
		System.out.println("You have " + initialPlayerCountry.get(currentPlayer.getName()));
		System.out.println("You got " + noOfArmiesFromContinents + " from you continets.");
		// end of counting reinforcement armies from continet value.
		
		// Start Of counting reinforcement armies from number of countries owned by player.
		getArmiesFromCountries();
		int noOfArmiesFromCountries = noOfReinforcementArmies - noOfArmiesFromContinents;
		System.out.println("You have " + initialPlayerCountry.get(currentPlayer.getName()).size()+ " no of countries.");
		System.out.println("Your got " + noOfArmiesFromCountries + " from your countris.");
		// end of counting reinforcement armies from number of countries owned by player.
		
		//Start of counting number of reinforcement armies on the basis of card trade.
		getArmiesFromCards();
		int noOfArmiesFromCards = noOfReinforcementArmies - (noOfArmiesFromCountries+noOfArmiesFromContinents);
		System.out.println("You got " + noOfArmiesFromCards + " armies from tradin cards");
		//End of counting number of reinforcement armies on the basis of card trade.
		
		System.out.println("Cards in the deck" + cardsInTheDeck);
		System.out.println(currentPlayer.getName() + " has " + noOfReinforcementArmies + " number of reinforcement armies");
		Scanner sc = new Scanner(System.in);
		Scanner sc1 = new Scanner(System.in);
		String countryNameToEnterArmies;
		int noOfArmiesWantToPlace;

		beforeA = currentPlayer.getArmies();// 17
		int newSize = noOfReinforcementArmies;
		currentPlayer.addArmies(newSize);
		currentA = currentPlayer.getArmies();// 23
		while (currentPlayer.getArmies() > 0) {
			System.out.println(currentPlayer.getName() + " You have " + currentPlayer.getArmies() + " armies." + "\n");
			System.out.println("And you own " + initialPlayerCountry.get(currentPlayer.getName()) + "\n");
			System.out.println("Enter the name of country where you wan to place armies" + "\n");
			countryNameToEnterArmies = sc.nextLine();
			if (!initialPlayerCountry.get(currentPlayer.getName()).contains(countryNameToEnterArmies)) {
				System.out.println("You dont own this country" + "\n");
			} else {
				System.out.println("Enter thhe number of armies you want to add on " + countryNameToEnterArmies + "\n");
				noOfArmiesWantToPlace = sc1.nextInt();// 10
				placeReinforcementArmies(countryNameToEnterArmies, noOfArmiesWantToPlace);
//				gamePhase();
			}
		}
	}

	/**
	 * This method is called in the placeREinforcementArmies to place reinforcement
	 * armies in the given country by the use.
	 */
	public void placeReinforcementArmies(String countryNameToEnterArmies, int noOfArmiesWantToPlace)
			throws IOException {
		if (noOfArmiesWantToPlace > currentPlayer.getArmies()) {
			System.out.println("You don't have suffecient armies" + "\n");
		} else {
			int initialAriesCountryOwn = countriesArmies.get(countryNameToEnterArmies);
			int finalArmiesCOuntryHave = initialAriesCountryOwn + noOfArmiesWantToPlace;
			countriesArmies.put(countryNameToEnterArmies, finalArmiesCOuntryHave);
			System.out.println(noOfArmiesWantToPlace + "\n");
			currentPlayer.loosArmies(noOfArmiesWantToPlace);
			afterA = currentPlayer.getArmies();// 13

		}
	}

	/**
	 * This method executes sub method for reinforcement phase of the game
	 */
	public void reinforcementPhase() throws IOException {
		placeReinforcementArmies();
		attackPhase();
	}

	public void attackPhase() throws IOException {
		int noOfAttackerDice = 0;
		int attacker, defender;
		int attackerDiceArray[];
		int defenderDiceArray[];
		String attackerCountry;
		int noOfDefenderDice;
		String defenderCountry;
		int flagCheckDice = 0;
		int indx = 0;// = value.
		int oldCOuntryListSize = 0;
		int newCOuntryListSize = 0;
		String newDefenderCountry = "";
		int updateArmyOfAttacker;
		int updateArmyOfDefender;

		String attackTurnOn = "hello";
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
		countriesArmies.put("India", 3);
		countriesArmies.put("China", 1);
//		countriesArmies.put("Ontario", 2);
//		countriesArmies.put("Quebec", 2);
//		countriesArmies.put("Venezuala", 1);
//		countriesArmies.put("Peru", 2);
//		countriesArmies.put("Argentina", 1);
//		countriesArmies.put("Brazil", 4);
//		countriesArmies.put("Alberta", 2);

		System.out.println("Initailly player country list before any attack : " + initialPlayerCountry);
		System.out.println("Initailly player country list with initial army : " + countriesArmies);

		System.out.println("Current Player : " + currentPlayer.getName());
		oldCOuntryListSize = initialPlayerCountry.get(currentPlayer.getName()).size();
		System.out.println("Current Player owning ciuntries : " + initialPlayerCountry.get(currentPlayer.getName()));
		System.out.println("Please enter the name of country from where you want to attack");
		attackerCountry = input.readLine();
		// countriesArmies.put(attackerCountry, 4);

		System.out.println("No of armies in the attacker country : " + countriesArmies.get(attackerCountry));
		// HashMap<String, List<String>> attckerCountryAdjacent = new
		// HashMap<String, List<String>>();
		List<String> attackerCountryAdjacent = new ArrayList<String>();
		int size = initialPlayerCountry.get(currentPlayer.getName()).size();
//		System.out.println(initialPlayerCountry.get(currentPlayer.getName()).size());
		int size2 = adj.get(attackerCountry).size();
		for (int i = 0; i < size2; i++) {
			if (initialPlayerCountry.get(currentPlayer.getName()).contains(adj.get(attackerCountry).get(i))) {
				continue;
			} else {
				attackerCountryAdjacent.add(adj.get(attackerCountry).get(i));
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
		System.out.println("No of armies in the defender country : " + countriesArmies.get(defenderCountry));

		// countriesArmies.put(defenderCountry, 1);

		if (countriesArmies.get(attackerCountry) >= 2) {
			System.out.println("As, You have minimum of 2 armies, you can attack");

			if (countriesArmies.get(attackerCountry) > 3) {
				System.out.println("Attacker Country Player, can opt among 1, 2 or 3 dice to be rolled");
				noOfAttackerDice = sc.nextInt();
			} else if (countriesArmies.get(attackerCountry) == 3) {
				// flagCheckDice = 1;
				System.out.println("Attacker Country Player, can opt among 1 or 2 dice to be rolled");
				noOfAttackerDice = sc.nextInt();
			} else if (countriesArmies.get(attackerCountry) == 2) {
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

			if (countriesArmies.get(defenderCountry) >= 2) {

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

			System.out.println("Number of armies in Attacker Country is : " + countriesArmies.get(attackerCountry));
			System.out.println("Number of armies in Defender Country is : " + countriesArmies.get(defenderCountry));

			if (noOfDefenderDice == 1) {

				if (attackerDiceArray[attackerDiceArray.length - 1] > defenderDiceArray[defenderDiceArray.length - 1]) {
					System.out.println(" Defender country loose 1 army");
					updateArmyOfDefender = countriesArmies.get(defenderCountry) - 1;
					if (updateArmyOfDefender == 0) {

						for (Entry<String, ArrayList<String>> entry : initialPlayerCountry.entrySet()) {
							String key = entry.getKey();
							ArrayList<String> value = entry.getValue();

							if ((initialPlayerCountry.get(key).contains(defenderCountry))
									&& key != currentPlayer.getName()) {

								for (int i = 0; i < value.size(); i++) {
									if (value.get(i).equals(defenderCountry)) {
										indx = i;
										break;
									}
								}
								entry.getValue().remove(indx);
								initialPlayerCountry.get(currentPlayer.getName()).add(defenderCountry);

								newCOuntryListSize = initialPlayerCountry.get(currentPlayer.getName()).size();
							}
						}

						countriesArmies.put(defenderCountry, noOfAttackerDice);

					} else {
						countriesArmies.put(defenderCountry, updateArmyOfDefender);
					}
					System.out.println("new list of initial player country" + initialPlayerCountry);
					System.out.println("Initailly player country list with initial army : " + countriesArmies);

				} else {//// DCD(0) > ACD(0)// for defender dice == 1
					System.out.println(" Attacker country loose 1 army");
					updateArmyOfAttacker = countriesArmies.get(attackerCountry) - 1;

					countriesArmies.put(attackerCountry, updateArmyOfAttacker);

					System.out.println("new list of initial player country" + initialPlayerCountry);
					System.out.println("Initailly player country list with initial army : " + countriesArmies);
				}
			} // end of defenderdice ==1

			else {

				if (attackerDiceArray[attackerDiceArray.length - 1] > defenderDiceArray[defenderDiceArray.length - 1]) {
					System.out.println(" Defender country loose 1 army");
					updateArmyOfDefender = countriesArmies.get(defenderCountry) - 1;
					countriesArmies.put(defenderCountry, updateArmyOfDefender);

					if (attackerDiceArray[attackerDiceArray.length - 2] > defenderDiceArray[defenderDiceArray.length
							- 2]) {
						System.out.println(" Defender country loose 1 more army");
						updateArmyOfDefender = updateArmyOfDefender - 1;
						countriesArmies.put(defenderCountry, updateArmyOfDefender);

						if (updateArmyOfDefender == 0) {

							for (Entry<String, ArrayList<String>> entry : initialPlayerCountry.entrySet()) {
								String key = entry.getKey();
								ArrayList<String> value = entry.getValue();

								if ((initialPlayerCountry.get(key).contains(defenderCountry))
										&& key != currentPlayer.getName()) {

									for (int i = 0; i < value.size(); i++) {
										if (value.get(i).equals(defenderCountry)) {
											indx = i;
											break;
										}
									}
									entry.getValue().remove(indx);
									initialPlayerCountry.get(currentPlayer.getName()).add(defenderCountry);

									newCOuntryListSize = initialPlayerCountry.get(currentPlayer.getName()).size();
								}
							}

							countriesArmies.put(defenderCountry, noOfAttackerDice);

						} else {
							countriesArmies.put(defenderCountry, updateArmyOfDefender);
						}
						System.out.println("new list of initial player country" + initialPlayerCountry);
						System.out.println("Initailly player country list with initial army : " + countriesArmies);
					} else {
						System.out.println(" Attacker country loose 1 army");
						updateArmyOfAttacker = countriesArmies.get(attackerCountry) - 1;

						countriesArmies.put(attackerCountry, updateArmyOfAttacker);

						System.out.println("new list of initial player country" + initialPlayerCountry);
						System.out.println("Initailly player country list with initial army : " + countriesArmies);

					}

				} else {
					if (attackerDiceArray[attackerDiceArray.length - 1] <= defenderDiceArray[defenderDiceArray.length
							- 1]) {
						System.out.println(" Attacker country loose 1 army");
						updateArmyOfAttacker = countriesArmies.get(attackerCountry) - 1;
						countriesArmies.put(attackerCountry, updateArmyOfAttacker);

						if (attackerDiceArray[attackerDiceArray.length
								- 2] <= defenderDiceArray[defenderDiceArray.length - 2]) {
							System.out.println(" Attacker country loose 1 more army");
							updateArmyOfAttacker -= 1;
							countriesArmies.put(attackerCountry, updateArmyOfAttacker);

						} else {
							System.out.println(" Defender country loose 1 more army");
							updateArmyOfDefender = countriesArmies.get(defenderCountry) - 1;
							countriesArmies.put(defenderCountry, updateArmyOfDefender);

							if (updateArmyOfDefender == 0) {

								for (Entry<String, ArrayList<String>> entry : initialPlayerCountry.entrySet()) {
									String key = entry.getKey();
									ArrayList<String> value = entry.getValue();

									if ((initialPlayerCountry.get(key).contains(defenderCountry))
											&& key != currentPlayer.getName()) {

										for (int i = 0; i < value.size(); i++) {
											if (value.get(i).equals(defenderCountry)) {
												indx = i;
												break;
											}
										}
										entry.getValue().remove(indx);
										initialPlayerCountry.get(currentPlayer.getName()).add(defenderCountry);

										newCOuntryListSize = initialPlayerCountry.get(currentPlayer.getName()).size();
									}
								}

								countriesArmies.put(defenderCountry, noOfAttackerDice);

							} else {
								countriesArmies.put(defenderCountry, updateArmyOfDefender);
							}

						}

						countriesArmies.put(attackerCountry, updateArmyOfAttacker);

						System.out.println("new list of initial player country" + initialPlayerCountry);
						System.out.println("Initailly player country list with initial army : " + countriesArmies);
					}

				}
			}
		} else
			System.out.println("As you are having only 1 army, you can't attack");

		System.out.println("Do you want to still attack to other countries, press Y/N");
		attackTurnOn = input.readLine();
		if (attackTurnOn.equals("Y")) {

			attackPhase();
		} else {

			if (newCOuntryListSize > oldCOuntryListSize) {

				// code for card need to be done here
				int indexOfCardToBeGet = (int) (Math.random() * ( cardsInTheDeck.size() - 0 ));
				playersCards.get(currentPlayer.getName()).add(cardsInTheDeck.get(indexOfCardToBeGet));
				cardsInTheDeck.remove(indexOfCardToBeGet);
				setChanged();
				notifyObservers(this);
			}

			fortify();
		}

	}// end of attackPhase
		// need to keep global variable to collect armies
	
	/**
	 * This method is related to fortification phase of the game where player
	 * can transfer his armies from one country to another country.
	 */
	public void fortify() throws IOException {
		Scanner scFrom = new Scanner(System.in);
		System.out.println(currentPlayer.getName());
		System.out.println("You have these countries under your control " + initialPlayerCountry.get(currentPlayer.getName()));
		System.out.println("Enter the name of country from which you want to move armies.");
		String from;
		from = scFrom.nextLine();
		System.out.println(from);
//		System.out.println("You can move armies to these many countries only from your chosen country.");
//		System.out.println(adj.get(from));
		
//		System.out.println(adj.get(from));
//		System.out.println(initialPlayerCountry.get(currentPlayer.getName()));
		int tempCountrySize = initialPlayerCountry.get(currentPlayer.getName()).size();
		int tempAdjSize = adj.get(from).size();
//		System.out.println(tempAdjSize);
//		System.out.println(tempCountrySize);
		ArrayList<String> tempAdjCountryToWhichWeCanMOve = new ArrayList<String>();
		for(int i = 0; i < tempAdjSize; i++){
			for(int j = 0; j < tempCountrySize; j++){
				if(initialPlayerCountry.get(currentPlayer.getName()).get(j).contains(adj.get(from).get(i))){
					tempAdjCountryToWhichWeCanMOve.add(adj.get(from).get(i));
				}
			}
		}
//		System.out.println(tempAdjCountryToWhichWeCanMOve);
		Scanner scto = new Scanner(System.in);
		System.out.println("Select the countries from your owned countries  and adjacent to " + from + " where you want to move your armies");
//		System.out.println(adj.get(from));
		System.out.println(tempAdjCountryToWhichWeCanMOve);
		String to;
		int numberOfArmiesToMove;
		to = scto.nextLine();
//		System.out.println(to);
//		System.out.println(adj.get(from));
//		System.out.println("adjacentsMap: " + adj);
//		System.out.println(initialPlayerCountry);
		if(tempAdjCountryToWhichWeCanMOve.contains(to)){
			System.out.println(currentPlayer.getName() + " you have " + countriesArmies.get(from) + " armies on " + from + ".");
			System.out.println("Please Enter the number of armies you want to move to " + "" + to + " " + "country.s");
            numberOfArmiesToMove = sc.nextInt();
//            System.out.println(from + " " + countriesArmies.get(from));
//            System.out.println(to);
            if(countriesArmies.get(from) > 1){
            	int currentArmiesOfToCountry = countriesArmies.get(to);
                int newArmiesToAdd = currentArmiesOfToCountry + numberOfArmiesToMove;
                countriesArmies.put(to, newArmiesToAdd);
                int currentArmiesOfFromCountry = countriesArmies.get(from);
                int newArmiesToDelete = currentArmiesOfFromCountry - numberOfArmiesToMove;
                countriesArmies.put(from, newArmiesToDelete);
                System.out.println(from + " = " + countriesArmies.get(from));
                System.out.println(to + " = " + countriesArmies.get(to) + "\n");
                nextPlayer();
                reinforcementPhase();
            }else{
            	Scanner sc = new Scanner(System.in);
            	String result;
            	System.out.println("You dont have sufficient number of armies to move from " + from + "Do you want to play fortify again? Y/N");
            	result = sc.nextLine();
            	if(result.equals("N")){
    				fortify();
    			}else{
    				nextPlayer();
    				reinforcementPhase();
    			}
            }
			
		}else{
			System.out.println(currentPlayer.getName() + " doesnt contain the country where you want to place armies.");
			Scanner sc = new Scanner(System.in);
			String result;
			System.out.println("Do you want to stop? Y/N");
			result = sc.nextLine();
			if(result.equals("N")){
				fortify();
			}else{
				nextPlayer();
				reinforcementPhase();
			}
		}
		
	}

}