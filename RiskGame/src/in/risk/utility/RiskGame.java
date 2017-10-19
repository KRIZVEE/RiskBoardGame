package in.risk.utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
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
	public static HashMap<String, List<String>> countryCoordinates = new HashMap<String, List<String>>();

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
	public HashMap<String, Integer> playersCards = new HashMap<String, Integer>();
	public HashMap<String, Integer> playerTurn = new HashMap<String, Integer>();

	public Player currentPlayer;
	public Player active;

	public int card = 0;
	public int c = 0;
	public int iter = 0;
	public boolean drawn;
	public int beforeA, currentA, afterA;

	/**
	 * This method is called to play the game.
	 */
	public void startGame(String Path) throws IOException {
		mapLoader(Path);
		selectPlayers();
		initialPlayer();
		initialPlayerCountries();
		distributeArmies();
		initiallyPlaceArmies();
		distributeCards();
		playCards();
		fortify();
	}

	/**
	 * This method loads the map selected by the user.
	 * <ul>
	 * This method gives all the important elements needed from map to play the
	 * game.
	 */
	public static void mapLoader(String pathMap) {

		try {
			String path = "resources/maps/" + pathMap;
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
			armies = 35;
		if (numberOfPlayers == 4)
			armies = 30;
		if (numberOfPlayers == 5)
			armies = 25;
		if (numberOfPlayers == 6)
			armies = 20;
		for (int i = 0; i < numberOfPlayers; i++) {
			players.elementAt(i).addArmies(armies);
		}
	}

	/**
	 * 
	 * /** This method distributes cards to all players as per game logic.
	 * Distribution of cards will be ratio of number of countries to number of
	 * players
	 */
	public void distributeCards() throws IOException {
		int noOfCardsForEachPlayer = countryFilter.size() / players.size();
		int i = players.size();
		while (i > 0) {
			playersCards.put(currentPlayer.getName(), noOfCardsForEachPlayer);
			nextPlayer();
			i--;
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
		if (card == 1)
			playCards();
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
	 * This method is about initally placing armies. Armies will be added one by
	 * one in the game logic.
	 */
	public void initiallyPlaceArmies() throws IOException {
		int interator = 0;
		String result;
		int p = players.size();
		do {
			int k = initialPlayerCountry.get(currentPlayer.getName()).size();
			int h = currentPlayer.getArmies();
			while (k > 0) {
				countriesArmies.put(initialCountries.get(interator), 1);
				currentPlayer.looseArmy();
				interator++;
				k--;
			}
			p--;
			nextPlayer();
		} while (p > 0);
	}

	/**
	 * This method is about trading of cards and getting armies in return.
	 * Armies added will be as per the game logic.
	 */
	public void getArmiesFromCards() throws IOException {
		System.out.println("Right now the current player is: " + currentPlayer.getName());
		System.out.println(currentPlayer.getName() + "  has " + playersCards.get(currentPlayer.getName())
				+ " cards. Do you want to trade in cards? Y/N");
		String result;
		Scanner sc2 = new Scanner(System.in);
		result = sc2.nextLine();
		if (result.equals("Y")) {
			int currentArmies = currentPlayer.getArmies();
			currentPlayer.addArmies(5);
			int noOfCards = playersCards.get(currentPlayer.getName());
			int remainingCards = noOfCards - 3;
			nuberOfCardsInDeck = nuberOfCardsInDeck + 3;
			System.out.println(nuberOfCardsInDeck);
			playersCards.put(currentPlayer.getName(), remainingCards);

		} else {
		}
	}

	/**
	 * This method asks player to trade their cards in return of the armies as
	 * per game's logic.
	 */
	public void playCards() throws IOException {
		int noOfCardsPlayerHave = playersCards.get(currentPlayer.getName());
		if (noOfCardsPlayerHave > 5) {
			Scanner sc = new Scanner(System.in);
			String result;
			System.out.println(currentPlayer.getName() + " is having these many cards "
					+ playersCards.get(currentPlayer.getName()));
			System.out.println(currentPlayer.getName() + " do you want to trade in from your " + noOfCardsPlayerHave
					+ " cards? Y/N");
			result = sc.nextLine();
			if (result.equals("Y")) {
				int turn = playerTurn.get(currentPlayer.getName());
				if (turn == 1) {
					int noOfArmiesToIncrease = 5;
					System.out.println("Armies player own earlier = " + currentPlayer.getArmies());
					currentPlayer.addArmies(noOfArmiesToIncrease);
					int newCrads = noOfCardsPlayerHave - 3;
					playersCards.put(currentPlayer.getName(), newCrads);
					nuberOfCardsInDeck += 3;
					System.out.println("Player turn ENDS!!!!!");
					System.out.println("Player Name = " + currentPlayer.getName());
					System.out.println("Player Cards = " + playersCards.get(currentPlayer.getName()));
					System.out.println("Cards in deck = " + nuberOfCardsInDeck);
					System.out.println("Armies player own now = " + currentPlayer.getArmies() + "\n");
					card = 1;
					int newTurn = turn + 1;
					playerTurn.put(currentPlayer.getName(), newTurn);
					nextPlayer();
				} else if (turn == 2) {
					int noOfArmiesToIncrease = 5 * 2;
					System.out.println("Armies player own earlier = " + currentPlayer.getArmies() + "\n");
					currentPlayer.addArmies(noOfArmiesToIncrease);
					int newCrads = noOfCardsPlayerHave - 3;
					playersCards.put(currentPlayer.getName(), newCrads);
					nuberOfCardsInDeck += 3;
					System.out.println("Player turn ENDS!!!!!");
					System.out.println("Player Name = " + currentPlayer.getName());
					System.out.println("Player Cards = " + playersCards.get(currentPlayer.getName()));
					System.out.println("Cards in deck = " + nuberOfCardsInDeck);
					System.out.println("Armies player own now = " + currentPlayer.getArmies() + "\n");
					card = 1;
					int newTurn = turn + 1;
					playerTurn.put(currentPlayer.getName(), newTurn);
					nextPlayer();
				} else if (turn == 3) {
					int noOfArmiesToIncrease = 5 * 3;
					System.out.println("Armies player own earlier = " + currentPlayer.getArmies() + "\n");
					currentPlayer.addArmies(noOfArmiesToIncrease);
					int newCrads = noOfCardsPlayerHave - 3;
					playersCards.put(currentPlayer.getName(), newCrads);
					nuberOfCardsInDeck += 3;
					System.out.println("Player turn ENDS!!!!!");
					System.out.println("Player Name = " + currentPlayer.getName());
					System.out.println("Player Cards = " + playersCards.get(currentPlayer.getName()));
					System.out.println("Cards in deck = " + nuberOfCardsInDeck);
					System.out.println("Armies player own now = " + currentPlayer.getArmies() + "\n");
					card = 1;
					int newTurn = turn + 1;
					playerTurn.put(currentPlayer.getName(), newTurn);
					nextPlayer();
				} else if (turn == 4) {
					int noOfArmiesToIncrease = 5 * 4;
					System.out.println("Armies player own earlier = " + currentPlayer.getArmies() + "\n");
					currentPlayer.addArmies(noOfArmiesToIncrease);
					int newCrads = noOfCardsPlayerHave - 3;
					playersCards.put(currentPlayer.getName(), newCrads);
					nuberOfCardsInDeck += 3;
					System.out.println("Player turn ENDS!!!!!");
					System.out.println("Player Name = " + currentPlayer.getName());
					System.out.println("Player Cards = " + playersCards.get(currentPlayer.getName()));
					System.out.println("Cards in deck = " + nuberOfCardsInDeck);
					System.out.println("Armies player own now = " + currentPlayer.getArmies() + "\n");
					card = 1;
					int newTurn = turn + 1;
					playerTurn.put(currentPlayer.getName(), newTurn);
					nextPlayer();
				} else if (turn == 5) {
					int noOfArmiesToIncrease = 5 * 5;
					System.out.println("Armies player own earlier = " + currentPlayer.getArmies() + "\n");
					currentPlayer.addArmies(noOfArmiesToIncrease);
					int newCrads = noOfCardsPlayerHave - 3;
					playersCards.put(currentPlayer.getName(), newCrads);
					nuberOfCardsInDeck += 3;
					System.out.println("Player turn ENDS!!!!!");
					System.out.println("Player Name = " + currentPlayer.getName());
					System.out.println("Player Cards = " + playersCards.get(currentPlayer.getName()));
					System.out.println("Cards in deck = " + nuberOfCardsInDeck);
					System.out.println("Armies player own now = " + currentPlayer.getArmies() + "\n");
					card = 1;
					int newTurn = turn + 1;
					playerTurn.put(currentPlayer.getName(), newTurn);
					nextPlayer();
				} else if (turn == 6) {
					int noOfArmiesToIncrease = 5 * 6;
					System.out.println("Armies player own earlier = " + currentPlayer.getArmies() + "\n");
					currentPlayer.addArmies(noOfArmiesToIncrease);
					int newCrads = noOfCardsPlayerHave - 3;
					playersCards.put(currentPlayer.getName(), newCrads);
					nuberOfCardsInDeck += 3;
					System.out.println("Player turn ENDS!!!!!");
					System.out.println("Player Name = " + currentPlayer.getName());
					System.out.println("Player Cards = " + playersCards.get(currentPlayer.getName()));
					System.out.println("Cards in deck = " + nuberOfCardsInDeck);
					System.out.println("Armies player own now = " + currentPlayer.getArmies() + "\n");
					card = 1;
					int newTurn = turn + 1;
					playerTurn.put(currentPlayer.getName(), newTurn);
					nextPlayer();

				}
			} else {
				System.out.println("Player turn ENDS!!!!!");
				System.out.println("Armies player own earlier = " + currentPlayer.getArmies());
				System.out.println("Player Name = " + currentPlayer.getName());
				System.out.println("Player Cards = " + playersCards.get(currentPlayer.getName()));
				System.out.println("Cards in deck = " + nuberOfCardsInDeck);
				System.out.println("Armies player own now = " + currentPlayer.getArmies() + "\n");
				card = 1;
				int j = players.size();
				c++;
				// System.out.println(c);
				if (c == j) {
					System.out.println("Player turn ENDS!!!!!");
					System.out.println("Player Name = " + currentPlayer.getName());
					System.out.println("Player Cards = " + playersCards.get(currentPlayer.getName()));
					System.out.println("Cards in deck = " + nuberOfCardsInDeck);
					System.out.println("Armies player own now = " + currentPlayer.getArmies() + "\n");
					card = 0;
					reinforcementPhase();
				}
				nextPlayer();
			}
		} else {
			System.out.println("Player turn ENDS!!!!!");
			System.out.println("Armies player own earlier = " + currentPlayer.getArmies());
			System.out.println("Player Name = " + currentPlayer.getName());
			System.out.println("Player Cards = " + playersCards.get(currentPlayer.getName()));
			System.out.println("Cards in deck = " + nuberOfCardsInDeck);
			System.out.println("Armies player own now = " + currentPlayer.getArmies() + "\n");
			card = 0;
			reinforcementPhase();
		}
	}

	/**
	 * This method places armies in their countries as per reinforcement phase
	 * of the game.
	 */
	public void placeReinforcementArmies() throws IOException {
		Scanner sc = new Scanner(System.in);
		Scanner sc1 = new Scanner(System.in);
		String countryNameToEnterArmies;
		int noOfArmiesWantToPlace;

		beforeA = currentPlayer.getArmies();// 17
		int newSize = initialPlayerCountry.get(currentPlayer.getName()).size() / players.size();
		currentPlayer.addArmies(newSize);
		currentA = currentPlayer.getArmies();// 23
		// System.out.println(newSize + " " + currentPlayer.getName());
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
			}
		}
	}

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
		int i = players.size();
		while (i > 0) {
			placeReinforcementArmies();
			nextPlayer();
			i--;
		}
	}

	/**
	 * This method is related to fortification phase of the game where player
	 * can transfer his armies from one country to another country.
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