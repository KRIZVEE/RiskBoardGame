package in.risk.utility;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

public class RiskGame {

	// Game states

	public static final int NEW_GAME = 0;
	public static final int INITIAL_REINFORCEMENT = 1;
	public static final int ACTIVE_TURN = 2;
	public static final int TURN_BONUS = 3;
	public static final int REINFORCE = 4;
	public static final int TRADE_CARDS = 5;
	public static final int START_TURN = 6;
	public static final int FORTIFY = 7;
	public static final int FORTIFYING = 8;
	public static final int FORTIFY_PHASE = 9;

	public static final int GAME_OVER = 99;

	Scanner sc = new Scanner(System.in);

	// @SuppressWarnings("unused")
	static private int game_state;

	static public Vector<Territory> territories = new Vector<Territory>();
	static public Vector<Player> players = new Vector<Player>();
	public Vector<Card> deck = new Vector<Card>();
	public Vector<Continent> continents = new Vector<Continent>();

	public ArrayList<String> continentList = new ArrayList<String>();
	public ArrayList<String> countryList = new ArrayList<String>();
	public int nuberOfCardsInDeck = 0;

	public HashMap<String, List<String>> continentsMap = new HashMap<String, List<String>>();
	public HashMap<String, List<String>> adjacentsMap = new HashMap<String, List<String>>();

	public ArrayList<String> initialCountries = new ArrayList<String>();

	public Vector<String> adjacents;
	public HashMap<String, List<String>> adj = new HashMap<String, List<String>>();
	public HashMap<String, ArrayList<String>> initialPlayerCountry = new HashMap<String, ArrayList<String>>();
	public HashMap<String, Integer> countriesArmies = new HashMap<String, Integer>();
	public HashMap<String, Integer> playersCards = new HashMap<String, Integer>();

	public Player currentPlayer;
	public Player active;
	Territory t;

	public int iter = 0;
	public boolean drawn;

	public static void main(String args[]) {
		RiskGame rg = new RiskGame();
	}

	public RiskGame() {
		game_state = NEW_GAME;
		loadMap();
		selectPlayers();
		initialPlayer();
		initialPlayerCountries();
		distributeArmies();
		initiallyPlaceArmies();
		distributeCards();
		reinforcementPhase();
		// askPlayerToPlaceArmies();
		// reinforcementPhase();
		// getArmiesFromCards();
		// System.out.println(countriesArmies);
		// System.out.println(countriesArmies);
		// placeArmies();
		// System.out.println(countriesArmies);
		// System.out.println(currentPlayer.getArmies());
		// System.out.println(initialPlayerCountry);
		// fortify();
		// System.out.println(countriesArmies);
	}

	public void loadMap() {

		boolean done = false;
		String next;
		String name;
		int value;
		// @SuppressWarnings("unused")
		String continent;
		// @SuppressWarnings("unused")
		int x;
		int y;

		try {
			File file = new File("resources/world.map");
			Scanner scanner = new Scanner(file);

			while (scanner.hasNextLine()) {
				done = false;
				next = scanner.nextLine();

				if (next.equals("[Continents]")) {
					next = scanner.next();

					do {
						String c[] = next.split("=");
						name = c[0].replace("_", " ");
						value = Integer.parseInt(c[1]);
						continentList.add(name);
						continents.add(new Continent(name, value));
						next = scanner.next();
						if (next.equals(";;"))
							done = true;
					} while (done == false);
				}

				if (next.equals("[Territories]")) {
					next = scanner.next();

					do {
						name = next.replace("_", " ");
						x = Integer.parseInt(scanner.next());
						y = Integer.parseInt(scanner.next());
						countryList.add(name);
						continent = scanner.next().replace("_", " ");
						adjacents = new Vector<String>();
						next = scanner.next();
						while (!next.equals(";")) {
							adjacents.add(next);
							adjacentsMap.put(name, adjacents);
							next = scanner.next();
						}
						// territories.add(new Territory(name, x, y,
						// continent));
						next = scanner.next();
						if (next.equals(";;"))
							done = true;
					} while (done == false);
				}
			}
			scanner.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void selectPlayers() {
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
			}
		} else if (noOfPlayers == 4) {
			for (int i = 0; i < 4; i++) {
				System.out.println("Please enter Name for your player no :" + (i + 1));
				playerName = sc2.nextLine();
				addPlayer(playerName);
			}
		} else if (noOfPlayers == 5) {
			for (int i = 0; i < 5; i++) {
				System.out.println("Please enter Name for your player no :" + (i + 1));
				playerName = sc2.nextLine();
				addPlayer(playerName);
			}
		} else if (noOfPlayers == 6) {
			for (int i = 0; i < 6; i++) {
				System.out.println("Please enter Name for your player no :" + (i + 1));
				playerName = sc2.nextLine();
				addPlayer(playerName);
			}
		} else {
			System.out.println("Please enter a valid no of players to play with");
		}
	}

	public static boolean addPlayer(String nm) {
		int size = players.size();
		if (size > 6) {
			return false;
		}
		Player p = new Player(nm, size);
		players.add(p);
		System.out.println(players.get(0).getName());
		return true;
	}

	public void initialPlayer() {
		currentPlayer = players.elementAt(0);
	}

	public void distributeArmies() {
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
			// System.out.println(players.elementAt(i).getName() + "+" +
			// players.elementAt(i).getArmies());
		}
	}

	public void initializeDeck() {
		for (int i = 0; i < territories.size(); i++) {
			deck.add(new Card(i % 3, i));
		}
	}

	public void distributeCards() {
		int noOfCardsForEachPlayer = countryList.size() / players.size();
		int i = players.size();
		while (i > 0) {
			playersCards.put(currentPlayer.getName(), noOfCardsForEachPlayer);
			nextPlayer();
			i--;
		}
	}

	public void nextPlayer() {
		if (currentPlayer == players.lastElement()) {
			currentPlayer = players.elementAt(0);
			iter = 0;
		} else
			currentPlayer = players.elementAt(++iter);
	}

	public void initialPlayerCountries() {
		int i = 0;
		int j = countryList.size() - 1;
		while (j >= 0) {
			initialCountries.add(countryList.get(j));
			players.get(i);

			ArrayList<String> list;
			if (initialPlayerCountry.containsKey(players.get(i).getName())) {
				list = initialPlayerCountry.get(players.get(i).getName());
				list.add(countryList.get(j));
			} else {
				list = new ArrayList<String>();
				list.add(countryList.get(j));
				initialPlayerCountry.put(players.get(i).getName(), list);
			}
			i++;
			j--;
			if (i == players.size()) {
				i = 0;
			}
		}
		// System.out.println("Ishna = " + initialPlayerCountry.get("Ishan") +
		// "\n");
		// System.out.println("Preet = " +initialPlayerCountry.get("Preet") +
		// "\n");
		// System.out.println("Mohit = " +initialPlayerCountry.get("Mohit"));
		// System.out.println(initialPlayerCountry.get("Ishan").size());
		// System.out.println(initialPlayerCountry.get("Preet").size());
		// System.out.println(initialPlayerCountry.get("Mohit").size());
		// System.out.println(initialPlayerCountry + "\n");
	}

	public void initiallyPlaceArmies() {
		int interator = 0;
		String result;
		int p = players.size();
		do {
			int k = initialPlayerCountry.get(currentPlayer.getName()).size();
			int h = currentPlayer.getArmies();
			while (k > 0) {
				// System.out.println(interator + " " + currentPlayer.getName()
				// + " " + "Do you want to add armies here " +
				// initialCountries.get(interator) + " yes or no");
				// result= sc.next();
				// if(result.equals("q"))
				// {
				// countriesArmies.put(initialCountries.get(interator), 2);
				// currentPlayer.looseArmy();
				// }else{
				// countriesArmies.put(initialCountries.get(interator), 0);
				// }

				countriesArmies.put(initialCountries.get(interator), 1);
				currentPlayer.looseArmy();
				// System.out.println(interator + " " + countriesArmies);
				interator++;
				k--;
			}
			p--;
			nextPlayer();
			// System.out.println(countriesArmies);
		} while (p > 0);
		// System.out.println(countriesArmies);
	}

	public void getArmiesFromCards() {
		System.out.println(currentPlayer.getName() + "  has " + playersCards.get(currentPlayer.getName())
				+ " cards. Do you want to trade in cards? Y/N");
		String result;
		Scanner sc2 = new Scanner(System.in);
		result = sc2.nextLine();
		if (result.equals("Y")) {
			int currentArmies = currentPlayer.getArmies();
			// System.out.println("Current Armies " +
			// currentPlayer.getArmies());
			// int armiesToBeAdded = currentArmies + 5;
			currentPlayer.addArmies(5);
			int noOfCards = playersCards.get(currentPlayer.getName());
			int remainingCards = noOfCards - 3;
			nuberOfCardsInDeck = nuberOfCardsInDeck + 3;
			System.out.println(nuberOfCardsInDeck);
			playersCards.put(currentPlayer.getName(), remainingCards);

			// System.out.println("Crads " +
			// playersCards.get(currentPlayer.getName()));
			// System.out.println("Updated Armies " +
			// currentPlayer.getArmies());
		} else {
		}
	}

	public void placeInitialArmies() {
		Scanner sc = new Scanner(System.in);
		Scanner sc1 = new Scanner(System.in);
		String countryNameToEnterArmies;
		int noOfArmiesWantToPlace;
		System.out.println(currentPlayer.getName());
		while (currentPlayer.getArmies() > 0) {
			System.out.println("You have " + currentPlayer.getArmies() + " armies.");
			System.out.println("And you own " + initialPlayerCountry.get(currentPlayer.getName()));
			System.out.println("Enter the name of country where you wan to place armies");
			countryNameToEnterArmies = sc.nextLine();
			System.out.println("Enter thhe number of armies you want to add on " + countryNameToEnterArmies);
			noOfArmiesWantToPlace = sc1.nextInt();
			if (noOfArmiesWantToPlace > currentPlayer.getArmies()) {
				System.out.println("Dont be over smart");
			} else {
				int initialAriesCountryOwn = countriesArmies.get(countryNameToEnterArmies);
				int finalArmiesCOuntryHave = initialAriesCountryOwn + noOfArmiesWantToPlace;
				countriesArmies.put(countryNameToEnterArmies, finalArmiesCOuntryHave);
				System.out.println(noOfArmiesWantToPlace);
				currentPlayer.loosArmies(noOfArmiesWantToPlace);
			}
		}
	}

	public void placeReinforcementArmies() {
		Scanner sc = new Scanner(System.in);
		Scanner sc1 = new Scanner(System.in);
		String countryNameToEnterArmies;
		int noOfArmiesWantToPlace;
		int newSize = initialPlayerCountry.get(currentPlayer.getName()).size() / players.size();
		currentPlayer.addArmies(newSize);
		System.out.println(newSize + " " + currentPlayer.getName());
		while (currentPlayer.getArmies() > 0) {
			System.out.println("You have " + currentPlayer.getArmies() + " armies.");
			System.out.println("And you own " + initialPlayerCountry.get(currentPlayer.getName()));
			System.out.println("Enter the name of country where you wan to place armies");
			countryNameToEnterArmies = sc.nextLine();
			System.out.println("Enter thhe number of armies you want to add on " + countryNameToEnterArmies);
			noOfArmiesWantToPlace = sc1.nextInt();
			if (noOfArmiesWantToPlace > currentPlayer.getArmies()) {
				System.out.println("Dont be over smart");
			} else {
				int initialAriesCountryOwn = countriesArmies.get(countryNameToEnterArmies);
				int finalArmiesCOuntryHave = initialAriesCountryOwn + noOfArmiesWantToPlace;
				countriesArmies.put(countryNameToEnterArmies, finalArmiesCOuntryHave);
				System.out.println(noOfArmiesWantToPlace);
				currentPlayer.loosArmies(noOfArmiesWantToPlace);
			}
		}
	}

	public void askPlayerToPlaceArmies() {
		int i = players.size();
		while (i > 0) {
			placeInitialArmies();
			nextPlayer();
			i--;
		}
	}

	public void reinforcementPhase() {
		int i = players.size();
		while (i > 0) {
			placeReinforcementArmies();
			nextPlayer();
			i--;
		}
	}

	public void fortify() {
		Scanner scFrom = new Scanner(System.in);
		System.out.println(currentPlayer.getName());
		System.out.println(
				"You have these countries under your control " + initialPlayerCountry.get(currentPlayer.getName()));
		System.out.println("Enter the name of country from which you want to move armies ");
		String from;
		from = scFrom.nextLine();
		System.out.println(from);
		Scanner scto = new Scanner(System.in);
		System.out.println("Select the countries from you owned countries whre you want to move your armies");
		System.out.println(initialPlayerCountry.get(currentPlayer.getName()));
		String to;
		int numberOfArmiesToMove;
		to = scto.nextLine();
		System.out.println(to);
		System.out.println(adjacentsMap.get(from));
		if (adjacentsMap.get(from).contains(to)) {
			System.out.println("Please Enter the number of armies you want to move to " + "" + to + " " + "country");
			numberOfArmiesToMove = sc.nextInt();
			System.out.println(numberOfArmiesToMove);

			if (countriesArmies.get(from) == 1) {
				System.out.println("You cant move");
			} else {
				int currentArmiesOfToCountry = countriesArmies.get(to);
				int newArmiesToAdd = currentArmiesOfToCountry + numberOfArmiesToMove;
				countriesArmies.put(to, newArmiesToAdd);
				int currentArmiesOfFromCountry = countriesArmies.get(from);
				int newArmiesToDelete = currentArmiesOfFromCountry - numberOfArmiesToMove;
				countriesArmies.put(from, newArmiesToDelete);

				System.out.println(from + " " + countriesArmies.get(from));
				System.out.println(to + " " + countriesArmies.get(to));

			}
		} else {
			System.out.println("both are not adjacent");
		}
	}

}