package in.risk.utility;

import java.io.BufferedWriter;

//package in.risk.utility;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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

	Player obj = new Player();

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
	public static HashMap<String, Integer> continentValue = new HashMap<String, Integer>();
	public static ArrayList<String> cardType = new ArrayList<String>();
	public static ArrayList<String> deck = new ArrayList<String>();
	public static HashMap<String, List<String>> playersCards = new HashMap<String, List<String>>();
	public static HashMap<String, String> playersContinent = new HashMap<String, String>();
	public static HashMap<String, List<String>> countryCoordinates = new HashMap<String, List<String>>();
	public static List<String> cardsInTheDeck = new ArrayList<String>();

	static Scanner sc = new Scanner(System.in);

	// @SuppressWarnings("unused")
	static public Vector<PlayerToPlay> players = new Vector<PlayerToPlay>();

	public ArrayList<String> continentList = new ArrayList<String>();
	// public ArrayList<String> countryFilter = new ArrayList<String>();
	public static int nuberOfCardsInDeck = 0;

	public HashMap<String, List<String>> continentsMap = new HashMap<String, List<String>>();
	public HashMap<String, List<String>> adjacentsMap = new HashMap<String, List<String>>();

	public ArrayList<String> initialCountries = new ArrayList<String>();

	public Vector<String> adjacents;
	public static HashMap<String, ArrayList<String>> initialPlayerCountry = new HashMap<String, ArrayList<String>>();
	public static HashMap<String, Integer> countriesArmies = new HashMap<String, Integer>();
	public HashMap<String, Integer> playerTurn = new HashMap<String, Integer>();

	public static PlayerToPlay currentPlayer;
	public PlayerToPlay active;

	public static String cardTypeA = "A";
	public static String cardTypeB = "B";
	public static String cardTypeC = "C";
	public static int cardInTheDeck;
	public static int cardFlag = 1;
	int noOfPlayers = 0;
	public int card = 0;
	public int c = 0;
	public static int iter = 0;
	public static int noOfReinforcementArmies = 0;
	public boolean drawn;
	public static int beforeA;

	public static int currentA;

	public static int afterA;

	// Added global variables for attack phase to find old and new country list
	// size
	int oldCOuntryListSize = 0;
	int newCOuntryListSize = 0;

	// Added global variables for observer pattern for showing world dominance

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
		obj.placeCardsInTheDeck();
		obj.placeCardsInTheDeck2();
		obj.initialCardDistribution();
		initiallyPlaceArmies();

		// System.out.println(playersCards.get(currentPlayer.getName()));
		// getArmiesFromCards();
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
			String path = "Resources/World2.map";// my new path
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
						continentValue.put(parts[0], Integer.parseInt(parts[1]));
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

	public void loggingString(String whatToLog) throws IOException {
		FileWriter fw = new FileWriter("Resources/log.txt", true);
		BufferedWriter bw = new BufferedWriter(fw);
		PrintWriter pw = new PrintWriter(bw);
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		pw.print("\n" + dateFormat.format(date) + " " + whatToLog);
		pw.flush();
		pw.close();
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

		loggingString(noOfPlayers + " players are choosen by the user to play the game.");
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
		PlayerToPlay p = new PlayerToPlay(nm, size);
		players.add(p);
		PrintWriter writer = new PrintWriter("Resources/log.txt");
		writer.print("");
		writer.close();
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
	public static void nextPlayer() throws IOException {
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
	 * 
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
	 * 
	 * @throws IOException
	 */
	public void gamePhase() throws IOException {

		obj.reinforcementPhase();

		// int x = selectPhase();
		// System.out.println(x);
		//
		// if (x == 1)
		// reinforcementPhase();
		// else if (x == 2) {
		// obj.attackPhase();
		// } else
		// System.out.println("====");
		// // obj.fortify(currentPlayer);
	}

	/**
	 * This method is regarding implementation of observer pattern for world
	 * domination percentage
	 * 
	 * @return This method returns number of countries owned by a player after
	 *         loosing/winning a country in form of percentage and also numbers
	 */
	public HashMap<String, ArrayList<String>> getWorldDominance() {
		System.out.println("Total country size: " + countryFilter.size());
		for (Entry<String, ArrayList<String>> entry : initialPlayerCountry.entrySet()) {
			String key = entry.getKey();
			ArrayList<String> value = entry.getValue();
			double totalCountryListSize = countryFilter.size();
			currentPlayerCountrySize = initialPlayerCountry.get(key).size();
			playerDominanceInPercent = (currentPlayerCountrySize / totalCountryListSize) * 100;
			System.out.println(
					"Player: " + key + " " + "No of countries for players: " + initialPlayerCountry.get(key).size());
			System.out.println("Player: " + key + " " + "World dominance in percentage: " + playerDominanceInPercent);

		}
		return initialPlayerCountry;

	}

	/**
	 * This method is used to generate the random number.
	 */
	public static int randomNumberGenerator() {
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

}