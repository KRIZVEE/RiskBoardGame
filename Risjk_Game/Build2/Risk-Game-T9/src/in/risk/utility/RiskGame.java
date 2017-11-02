package in.risk.utility;

import java.io.BufferedWriter;
import java.io.BufferedReader;


//package in.risk.utility;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

//import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;
//
//import jdk.internal.org.objectweb.asm.tree.IntInsnNode;

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
public class RiskGame {

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
	public static HashMap<String, List<String>> countryCoordinates = new HashMap<String, List<String>>();
	public static HashMap<String, Integer> continentValue = new HashMap<String , Integer>();
	public static HashMap<String, Integer> playersCards = new HashMap<String, Integer>();
	public static HashMap<String, Integer> flagForCards = new HashMap<String, Integer>();
	

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

	public int card = 0;
	public int c = 0;
	public int iter = 0;
	public boolean drawn;
	public int beforeA, currentA, afterA, flag = 0;
	public int noOfReinforcementArmies = 0;

	/**
	 * This is the main method
	 * 
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		System.out.println("Hello to Risk Game");
		RiskGame risk = new RiskGame();
		risk.startGame("C:\\Users\\mohit\\Desktop\\Risjk_Game\\World2.map");
	}

	/**
	 * This method is called to play the game.
	 */
	public void startGame(String Path) throws IOException {
		mapLoader(Path);
		selectPlayers();
		initialPlayer();
		initialPlayerCountries();
//		distributeArmies();
		initiallyPlaceArmies();
		initiallyPlaceCards();
		initialFlagsForPlayer();
		placeArmies();
//		reinforcementPhase();
		gamePhase();
		// distributeCards();
		// playCards();
		// fortify();
	}
	
	public void logging(String data) throws IOException {
		FileWriter fw = new FileWriter("resources/log.txt", true);
		BufferedWriter bw = new BufferedWriter(fw);
		PrintWriter pw = new PrintWriter(bw);

		Date currentDate = new Date();
		
		pw.println(currentDate.toString() + " - " + data);
		pw.flush();
		pw.close();
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
						valueList.add(parts[1]);
						int value = Integer.parseInt(parts[1]);
						continentValue.put(parts[0] , value);
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
		if (noOfPlayers == 3) {
			for (int i = 0; i < 3; i++) {
				System.out.println("Please enter Name for your player no :" + (i + 1));
				playerName = sc2.nextLine();
				addPlayer(playerName);
				playerTurn.put(playerName, 1);
			}
			String logOutput = players.size() + " players are selected by the user.";
			logging(logOutput);
		} else if (noOfPlayers == 4) {
			for (int i = 0; i < 4; i++) {
				System.out.println("Please enter Name for your player no :" + (i + 1));
				playerName = sc2.nextLine();
				addPlayer(playerName);
				playerTurn.put(playerName, 1);
			}
			String logOutput = players.size() + " players are selected by the user.";
			logging(logOutput);
		} else if (noOfPlayers == 5) {
			for (int i = 0; i < 5; i++) {
				System.out.println("Please enter Name for your player no :" + (i + 1));
				playerName = sc2.nextLine();
				addPlayer(playerName);
				playerTurn.put(playerName, 1);
			}
			String logOutput = players.size() + " players are selected by the user.";
			logging(logOutput);
		} else if (noOfPlayers == 6) {
			for (int i = 0; i < 6; i++) {
				System.out.println("Please enter Name for your player no :" + (i + 1));
				playerName = sc2.nextLine();
				addPlayer(playerName);
				playerTurn.put(playerName, 1);
			}
			String logOutput = players.size() + " players are selected by the user.";
			logging(logOutput);
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
	 * @throws IOException
	 */
	public void initiallyPlaceArmies() throws IOException {
		int totalNumberOfCountries = countryFilter.size();
		for (int i = 0; i < totalNumberOfCountries; i++) {
			countriesArmies.put(countryFilter.get(i), 0);
		}
	}
	
	/**
	 * This method is creating a new hasmap conatining all players with 0
	 * number of cards. This hashmap is used incalculating the reinforcement armies.
	 * @throws IOException
	 */
	public void initiallyPlaceCards() throws IOException {
		int totalNumberOfPlayers = players.size();
		for (int i = 0; i < totalNumberOfPlayers; i++) {
			playersCards.put(currentPlayer.getName(), 10);
			nextPlayer();
		}
	}
	
	/**
	 * This method is creating a new hasmap conatining the flag for players turn
	 * which will be used to count number of reinforcement armies accordign to card
	 * trade.
	 * @throws IOException
	 */
	public void initialFlagsForPlayer() throws IOException {
		int totalNumbeOfPlayers = players.size();
		for(int i=0; i< totalNumbeOfPlayers; i++) {
			flagForCards.put(currentPlayer.getName(), 1);
			nextPlayer();
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
		System.out.println("Start here");
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
	 * This method is used to select the phase in which player want to play.
	 * @return integer value used to select the phase.
	 * @throws IOException 
	 */
	public int selectPhase() {
		Scanner sc = new Scanner(System.in);
		int result;
		if(flag == 1) {
			System.out.println(currentPlayer.getName() + ". Please select what you want to do next in the game. \n");
			System.out.println("2. Attack \n");
			System.out.println("3. Fortify \n");
			result = sc.nextInt();
			// sc.close();
			return result;
		}else {
			System.out.println(currentPlayer.getName() + ". Please select what you want to do next in the game. \n");
			System.out.println("1. Reinforcement. \n");
			System.out.println("2. Attack \n");
			System.out.println("3. Fortify \n");
			result = sc.nextInt();
			// sc.close();
			return result;
		}
	}

	/**
	 * This method is used to play the game according to the selecteed phase.
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
	 * This method is used to implement the attack phase
	 * @throws IOException 
	 */
	public void attackPhase() throws IOException {
		int noOfAttackerDice = 0;
		int attacker, defender;
		int attackerDiceArray[];
		int defenderDiceArray[];
		String attackerCountry;
		int noOfDefenderDice;
		String defenderCountry;
		int flagCheckDice = 0;
		String attackTurnOn = "hello";
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

		// Scanner sc1 = new Scanner(System.in);
		// Scanner sc2 = new Scanner(System.in);

		System.out.println("Current Player : " + currentPlayer.getName());
		System.out.println("Current Player owning countries : " + initialPlayerCountry.get(currentPlayer.getName()));
		System.out.println("Please enter the name of country from where you want to attack");
		attackerCountry = input.readLine();
		//System.out.println("Armies with attacking country: " +countriesArmies.get(attackerCountry));
		countriesArmies.put(attackerCountry, 4);
		System.out.println("Based on this country name, you can attack to corresponding countries only : "
				+ adj.get(attackerCountry));
		System.out.println("Please enter the name of country, to which you want to attack");
		defenderCountry = input.readLine();
		//System.out.println("Armies with defending country: " +countriesArmies.get(defenderCountry));
		countriesArmies.put(defenderCountry, 3);
		System.out.println("Number of armies in the Attacker Country is : " + countriesArmies.get(attackerCountry));// +
		// countriesArmies.get(currentPlayer.getArmies()));

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
					int updateArmyOfDefender = countriesArmies.get(defenderCountry) - 1;
					countriesArmies.put(defenderCountry, updateArmyOfDefender);
					System.out.println(
							"New Value of Armies in Defender Country is : " + countriesArmies.get(defenderCountry));
					
					//Code to update list after defender country looses all army
					if(countriesArmies.get(defenderCountry) == 0)
					{
						adj.remove(defenderCountry);
						
						
					}
					
				} else {
					System.out.println(" Attacker country loose 1 army");
					int updateArmyOfAttacker = countriesArmies.get(attackerCountry) - 1;
					countriesArmies.put(attackerCountry, updateArmyOfAttacker);
					System.out.println(
							"New Value of Armies in Attacker Country is : " + countriesArmies.get(attackerCountry));
				}
			} else {
				if (attackerDiceArray[attackerDiceArray.length - 1] > defenderDiceArray[defenderDiceArray.length - 1]) {
					System.out.println(" Defender country loose 1 army");
					int updateArmyOfDefender = countriesArmies.get(defenderCountry) - 1;
					countriesArmies.put(defenderCountry, updateArmyOfDefender);
					System.out.println(
							"New Value of Armies in Defender Country is : " + countriesArmies.get(defenderCountry));
				} else {
					System.out.println(" Attacker country loose 1 army");
					int updateArmyOfAttacker = countriesArmies.get(attackerCountry) - 1;
					countriesArmies.put(attackerCountry, updateArmyOfAttacker);
					System.out.println(
							"New Value of Armies in Attacker Country is : " + countriesArmies.get(attackerCountry));
				}
				if ((flagCheckDice == 1) && (attackerDiceArray[attackerDiceArray.length
						- 2] > defenderDiceArray[defenderDiceArray.length - 2])) {
					System.out.println(" Defender country loose 1 army");
					int updateArmyOfDefender = countriesArmies.get(defenderCountry) - 1;
					countriesArmies.put(defenderCountry, updateArmyOfDefender);
					System.out.println(
							"New Value of Armies in Defender Country is : " + countriesArmies.get(defenderCountry));
				} else if ((flagCheckDice == 1) && (attackerDiceArray[attackerDiceArray.length
						- 2] <= defenderDiceArray[defenderDiceArray.length - 2])) {
					System.out.println(" Attacker country loose 1 army");
					int updateArmyOfAttacker = countriesArmies.get(attackerCountry) - 1;
					countriesArmies.put(attackerCountry, updateArmyOfAttacker);
					System.out.println(
							"New Value of Armies in Attacker Country is : " + countriesArmies.get(attackerCountry));
				}

			}

			System.out.println("Attacker Country " + countriesArmies.get(attackerCountry));
			System.out.println("Defender Country " + countriesArmies.get(defenderCountry));
		} else
			System.out.println("As you are having only 1 army, you can't attack from the selected country, please select another country to attack");

		System.out.println("Do you want to still attack to other countries, press Y/N");
		attackTurnOn = input.readLine();
		if (attackTurnOn.equals("Y")) {

			attackPhase();
		} else {
			fortify();
		}

	}// end of attackPhase
		// need to keep global variable to collect armies

	/**
	 * This method is used to generate the random number.
	 * @throws IOException 
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

	/**
	 * In this method the reinforcement armies are counted in the basis of continet 
	 * the player owns.
	 */
	public void armiesFromContinent() {
		for (Entry<String, List<String>> entry : continentCountries.entrySet()) {
			String Key = entry.getKey();
			List<String> value = entry.getValue();
			for (int i = 0; i < initialPlayerCountry.get(currentPlayer.getName()).size(); i++) {
				resultOfContinentCountry.add(value.contains(initialPlayerCountry.get(currentPlayer.getName()).get(i)));
			}
			if(resultOfContinentCountry.contains(false)) {
				noOfReinforcementArmies = noOfReinforcementArmies + 0;
			}else if(resultOfContinentCountry.contains(true)) {
				noOfReinforcementArmies = noOfReinforcementArmies + continentValue.get(Key);
			}
			resultOfContinentCountry.clear();
		}
		System.out.println(currentPlayer.getName() + " got " + noOfReinforcementArmies + " armies from their owning continents.");
	}
	
	/**
	 * In this method the number of reinforcement armies are calculated on the 
	 * basis of countries player owns.
	 */
	public void armiesFromCountrySize() {
		int sizeOfCountriesCurrentPlayerOwn = initialPlayerCountry.get(currentPlayer.getName()).size();
		if(sizeOfCountriesCurrentPlayerOwn < 9) {
			noOfReinforcementArmies = noOfReinforcementArmies + 3;
			System.out.println(currentPlayer.getName() + " got " + "3" + " armies from his/her owned countries.");
		}else {
			noOfReinforcementArmies = noOfReinforcementArmies + sizeOfCountriesCurrentPlayerOwn/3;
			System.out.println(currentPlayer.getName() + " got " + sizeOfCountriesCurrentPlayerOwn/3 + " armies from his/her owned countries.");
		}
		
	}
	
	/**
	 * This method asks player to trade their cards in return of the armies as
	 * per game's logic.
	 * @throws IOException
	 */
	public void playCards() throws IOException {
		String result;
		Scanner sc = new Scanner(System.in);
		int initialFlagForCards = flagForCards.get(currentPlayer.getName());
		int newFlagForCards = initialFlagForCards +1;
		flagForCards.put(currentPlayer.getName(), newFlagForCards);
		int numberOfCardsPlayerHave = playersCards.get(currentPlayer.getName());
		System.out.println(currentPlayer.getName() + " do you want to trade in your cards? Y/N");
		result =sc.nextLine();
		if(result.equals("Y")) {
			if(numberOfCardsPlayerHave >= 3 ){
				noOfReinforcementArmies += 5 * initialFlagForCards;
				nuberOfCardsInDeck += 3;
				int updatedNumberOFCardsPlayerHave = numberOfCardsPlayerHave - 3;
				playersCards.put(currentPlayer.getName(), updatedNumberOFCardsPlayerHave);
				System.out.println(currentPlayer.getName() + " got " + 5*initialFlagForCards + " armies by trading the cards.");
				System.out.println("Number of cards in the deck are " + nuberOfCardsInDeck);
				playCards();
			}
		}else {
		}
	}
	
	/**
	 * This method places armies in their countries as per reinforcement phase
	 * of the game.
	 * @throws IOException
	 */
	public void placeReinforcementArmies() throws IOException {
		armiesFromContinent();
		armiesFromCountrySize();
		playCards();
		
		System.out.println(currentPlayer.getName() + " you have " + noOfReinforcementArmies + " number of total reinforcement armies");
		Scanner sc = new Scanner(System.in);
		Scanner sc1 = new Scanner(System.in);
		String countryNameToEnterArmies;
		int noOfArmiesWantToPlace;

		beforeA = currentPlayer.getArmies();// 17
		int newSize = noOfReinforcementArmies;
		currentPlayer.addArmies(newSize);
		currentA = currentPlayer.getArmies();// 23
		while (currentPlayer.getArmies() > 0) {
			System.out.println(currentPlayer.getName() + " You have " + currentPlayer.getArmies() + " armies.");
			System.out.println("And you own " + initialPlayerCountry.get(currentPlayer.getName()));
			System.out.println("Enter the name of country where you wan to place armies");
			countryNameToEnterArmies = sc.nextLine();
			if (!initialPlayerCountry.get(currentPlayer.getName()).contains(countryNameToEnterArmies)) {
				System.out.println("You dont own this country");
			} else {
				System.out.println("Enter thhe number of armies you want to add on " + countryNameToEnterArmies);
				noOfArmiesWantToPlace = sc1.nextInt();// 10
				placeReinforcementArmies(countryNameToEnterArmies, noOfArmiesWantToPlace);
//				gamePhase();
			}
		}
	}

	/**
	 * This method takes country name to enter the armies and number of armies want to place from the user and then perform the operation.
	 * of the game.
	 * @param countryNameToEnterArmies is used to define the name of country to enter the armies to.
	 * @param noOfArmiesWantToPlace is used to specify the number of armies player wants to plave in the specified country.
	 * @throws IOException
	 */
	public void placeReinforcementArmies(String countryNameToEnterArmies, int noOfArmiesWantToPlace)
			throws IOException {
		if (noOfArmiesWantToPlace > currentPlayer.getArmies()) {
			System.out.println("You don't have suffecient armies" + "\n");
		} else {
			int initialAriesCountryOwn = countriesArmies.get(countryNameToEnterArmies);
			int finalArmiesCOuntryHave = initialAriesCountryOwn + noOfArmiesWantToPlace;
			countriesArmies.put(countryNameToEnterArmies, finalArmiesCOuntryHave);
//			System.out.println(noOfArmiesWantToPlace + "\n");
			currentPlayer.loosArmies(noOfArmiesWantToPlace);
			afterA = currentPlayer.getArmies();// 13

		}
	}

	/**
	 * This method executes sub method for reinforcement phase of the game
	 * @throws IOException
	 */
	public void reinforcementPhase() throws IOException {
			System.out.println("\n" + "Reinforcement Phase starts for " + currentPlayer.getName());
			placeReinforcementArmies();
			System.out.println("\n" + currentPlayer.getName() + " your attack Phase starts now. Please attack one or more copuntries.");
			attackPhase();
	}

	/**
	 * This method is related to fortification phase of the game where player
	 * can transfer his armies from one country to another country.
	 * @throws IOException
	 */
	public void fortify() throws IOException {
		Scanner scFrom = new Scanner(System.in);
		System.out.println(currentPlayer.getName() + "\n");
		System.out.println("You have these countries under your control "
				+ initialPlayerCountry.get(currentPlayer.getName()) + "\n");
		System.out.println("Enter the name of country from which you want to move armies " + "\n");
		String from;
		from = scFrom.nextLine();
		System.out.println(from + "\n");
		System.out.println("You can move armies to these many countries only from your chosen country" + "\n");
		System.out.println(adj.get(from) + "\n");
		Scanner scto = new Scanner(System.in);
		System.out.println("Select the countries from you owned countries whre you want to move your armies" + "\n");
		System.out.println(initialPlayerCountry.get(currentPlayer.getName()) + "\n");
		String to;
		int numberOfArmiesToMove;
		to = scto.nextLine();
		System.out.println(to + "\n");
		System.out.println(adj.get(from) + "\n");
		System.out.println("adjacentsMap: " + adj + "\n");
		if (adj.get(from).contains(to)) {
			System.out.println(
					"Please Enter the number of armies you want to move to " + "" + to + " " + "country" + "\n");
			numberOfArmiesToMove = sc.nextInt();
			System.out.println(numberOfArmiesToMove + "\n");

			if (countriesArmies.get(from) == 1) {
				System.out.println("You cant move" + "\n");
			} else {
				int currentArmiesOfToCountry = countriesArmies.get(to);
				int newArmiesToAdd = currentArmiesOfToCountry + numberOfArmiesToMove;
				countriesArmies.put(to, newArmiesToAdd);
				int currentArmiesOfFromCountry = countriesArmies.get(from);
				int newArmiesToDelete = currentArmiesOfFromCountry - numberOfArmiesToMove;
				countriesArmies.put(from, newArmiesToDelete);

				System.out.println(from + " " + countriesArmies.get(from) + "\n");
				System.out.println(to + " " + countriesArmies.get(to) + "\n");
			}
		} else {
			System.out.println("both are not adjacent" + "\n");
		}
	}

}