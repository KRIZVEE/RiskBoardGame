package in.risk.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Vector;

import in.risk.gui.RiskInterface;

/**
 * This class is used to implement the intial starting of the game.
 * @author mohitrana
 *
 */
public class StartUpPhase {

	public static String css = "file:resources/css/application.css";
	public static String logoPath = "file:resources/logo/Risk_logo.png";


	static public Vector<PlayerToPlay> players = new Vector<PlayerToPlay>();
	static public Vector<PlayerToPlay> playersForTournament = new Vector<PlayerToPlay>();
	static MapLoader obj = new MapLoader();
	static int noOfPlayer = 0;
	static int noOfPlayerForTournament = 0;
	public static PlayerToPlay currentPlayer;
	public static HashMap<String, ArrayList<String>> initialPlayerCountry = new HashMap<String, ArrayList<String>>();
	public static ArrayList<String> initialCountries = new ArrayList<String>();
	public static HashMap<String, Integer> countriesArmies = new HashMap<String, Integer>();
	public static int iter = 0;
	public static int conqueredMapCounterTURN;

	public static String playerNameAggresive = "";
	public static String playerNameRandom = "";
	public static String playerNameBenevolent = "";
	public static String playerNameCheater = "";

	public static String tournamentPlayerName[] = new String[4];
	/**
	 * This method is used to initially start the game.
	 * @param args arguments.
	 * @throws IOException Throw excetion.
	 */
	public static void gamePlay(){

		try{
			Scanner scForGameMode = new Scanner(System.in);
			int result = 0;
			System.out.println("Please choose the type of play from following options.");
			System.out.println("1.Single Game Mode.");
			System.out.println("2.Tournament Gam Mode");
			result = scForGameMode.nextInt();
			if(result == 1) {
				MapLoader.loadMap(RiskInterface.pathMap);
				askUserToSelectPlayers();
				initiallyPlaceArmies();
				initialPlayerCountry(1);
				initiallyPlaceArmies();
				placeArmies(1);
				// do while loopp to be implemented for choosing winner.
				AssigningStrategy objAssigningStrategy = new AssigningStrategy();
				if(currentPlayer.getName().equals("Aggressive")) {
					objAssigningStrategy.setStrategy(new AggresivePlayer());
					objAssigningStrategy.executeStrategy(currentPlayer);
					nextPlayer(1);
				}else if(currentPlayer.getName().equals("Benovalent")) {
					objAssigningStrategy.setStrategy(new BenevolentPlayer());
					objAssigningStrategy.executeStrategy(currentPlayer);
					nextPlayer(1);
				}else if(currentPlayer.getName().equals("Cheater")) {
					objAssigningStrategy.setStrategy(new CheaterPlayer());
					objAssigningStrategy.executeStrategy(currentPlayer);
					nextPlayer(1);
				}else if(currentPlayer.getName().equals("Random")) {
					objAssigningStrategy.setStrategy(new RandomPlayer());
					objAssigningStrategy.executeStrategy(currentPlayer);
					nextPlayer(1);
				}else if(currentPlayer.getName().equals("Human")) {
					objAssigningStrategy.setStrategy(new HumanPlayer());
					objAssigningStrategy.executeStrategy(currentPlayer);
					nextPlayer(1);
				}
				
			}else {
				System.out.println("##########################################Tournament Mode#########################################");

				String map1 = "3D Cliff.map";
				String map2 = "D-Day.map";
				String map3 = "Drak.map";
				String map4 = "Earth.map";
				String map5 = "Europe.map";

				ArrayList<String> mapList = new ArrayList<>();

				Scanner scForTournament = new Scanner(System.in);

				System.out.println("Please enter the number of maps you want to play with.");
				int numOfMaps = scForTournament.nextInt();

				for(int i = 0; i < numOfMaps; i++) {
					System.out.println("Please select the map from below give maps to play with.");
					System.out.println("1. " + map1);
					System.out.println("2. " + map2);
					System.out.println("3. " + map3);
					System.out.println("4. " + map4);
					System.out.println("5. " + map5);
					int resultForMap = scForTournament.nextInt();
					if(resultForMap == 1) {
						mapList.add(map1);
					}if(resultForMap == 2) {
						mapList.add(map2);
					}if(resultForMap == 3) {
						mapList.add(map3);
					}if(resultForMap == 4) {
						mapList.add(map4);
					}if(resultForMap == 5) {
						mapList.add(map5);
					}
				}
				askUserToSelectPlayersForTournament();

				System.out.println("Please enter the number fo games you want to play.");
				int numOfGames = scForTournament.nextInt();

				System.out.println("Please enter the maximum number of turns you want to play in the tournament.");
				int maxTurns = scForTournament.nextInt();

				for(int i =0; i< numOfMaps; i++) {
					String mapPath = mapList.get(i);
					MapLoader.loadMap(mapPath);
					initialPlayerCountry(2);
					initiallyPlaceArmies();
					placeArmies(2);

					for(int j=0; j< numOfGames; j++) {

						for(int l =0; l < maxTurns ; l++) {
							AssigningStrategy objAssigningStrategy = new AssigningStrategy();
							for(int k=0; k< noOfPlayerForTournament; k++) {
								if(currentPlayer.getName().equals("Aggressive")) {
									objAssigningStrategy.setStrategy(new AggresivePlayer());
									objAssigningStrategy.executeStrategy(currentPlayer);
									nextPlayer(2);
								}else if(currentPlayer.getName().equals("Benovalent")) {
									objAssigningStrategy.setStrategy(new BenevolentPlayer());
									objAssigningStrategy.executeStrategy(currentPlayer);
									nextPlayer(2);
								}else if(currentPlayer.getName().equals("Cheater")) {
									objAssigningStrategy.setStrategy(new CheaterPlayer());
									objAssigningStrategy.executeStrategy(currentPlayer);
									nextPlayer(2);
								}else if(currentPlayer.getName().equals("Random")) {
									objAssigningStrategy.setStrategy(new RandomPlayer());
									objAssigningStrategy.executeStrategy(currentPlayer);
									nextPlayer(2);
								}
							}
							maxTurns--;
						}
					}
					MapLoader.clearAll();
					initialPlayerCountry.clear();
					countriesArmies.clear();
				}
				//				AssigningStrategy objAssigningStrategy = new AssigningStrategy();
				//
				//				// Three contexts following different strategies
				//				objAssigningStrategy.setStrategy(new BenevolentPlayer());
				//				objAssigningStrategy.executeStrategy(currentPlayer);
				//
				//
				//				objAssigningStrategy.setStrategy(new AggresivePlayer());
				//				objAssigningStrategy.executeStrategy(currentPlayer);
				//
				//				objAssigningStrategy.setStrategy(new RandomPlayer());
				//				objAssigningStrategy.executeStrategy(currentPlayer);
			}
			//placeArmies();
			//			Player.placeReinforcementArmies(currentPlayer);
			//			Player.attackPhase(currentPlayer);// @author Kashif
			//			Player.fortifyPhase(currentPlayer);// @author Kashif
		}catch(Exception e){
			e.printStackTrace();
		}

	}

	/**
	 * This method is used to ask the user to select the number of players he wants to play with.
	 * @throws IOException exception.
	 */
	public static void askUserToSelectPlayers() throws IOException{
		Scanner sc = new Scanner(System.in);
		int result;
		System.out.println("Please enter the number of player you want to play with");
		result = sc.nextInt();
		noOfPlayer = result;
		selectPlayer(noOfPlayer);
	}


	public static void askUserToSelectPlayersForTournament() throws IOException{
		Scanner sc1 = new Scanner(System.in);
		int result;
		System.out.println("Please enter the number of player you want to play with");
		result = sc1.nextInt();
		noOfPlayerForTournament = result;
		selectTournamentPlayer(noOfPlayerForTournament);
	}

	/**
	 * This method is used to select the number of player user wants to play with.
	 * @param noOfPlayerToPlay user selected number of players.
	 * @return result string
	 * @throws IOException exception.
	 */
	public static String selectPlayer(int noOfPlayerToPlay) throws IOException{
		String result = null;
		if(noOfPlayerToPlay < 3){
			result = "You cannot play the game with less than 3 players.";
			System.out.println(result);
		}else if(noOfPlayerToPlay > 6){
			result = "You cannot play the game with more than 6 players.";
			System.out.println(result);
		}else if(noOfPlayerToPlay >= 3 && noOfPlayer <= 6){
			playerName(noOfPlayerToPlay);
			result = noOfPlayerToPlay + " no of players are selected to play the game";
		}
		return result;
	}

	/**
	 * This method is used to select the number of player user wants to play within Tournament MOde.
	 * @param noOfPlayerToPlay user selected number of players.
	 * @return result string
	 * @throws IOException exception.
	 */
	public static String selectTournamentPlayer(int noOfPlayerToPlay) throws IOException{
		String result = null;
		if(noOfPlayerToPlay < 2){
			result = "You cannot play the game with less than 2 players.";
			System.out.println(result);
		}else if(noOfPlayerToPlay > 4){
			result = "You cannot play the game with more than 4 players.";
			System.out.println(result);
		}else if(noOfPlayerToPlay >= 2 && noOfPlayerToPlay <= 4){
			playerTournamentName(noOfPlayerToPlay);
			result = noOfPlayerToPlay + " no of players are selected to play the game";
		}
		return result;
	}

	/**
	 * This method is used to ask the user to enter the tournament player name he want to give to the player.
	 * @param noOfPlayers total no of players.
	 * @throws IOException exception
	 */
	public static void playerTournamentName(int noOfPlayers) throws IOException{
		Scanner sc1 = new Scanner(System.in);
		int result = 0;
		String playerName = null;
		for (int i = 0; i < noOfPlayers; i++) {
			System.out.println("You have following types of players. Choose the tpe of player you want to playe with.");
			//System.out.println("1. Human Player");
			System.out.println("1. Aggressive Player");
			System.out.println("2. Random Player");
			System.out.println("3. Benovalent Player");
			System.out.println("4. Cheater Player");
			result = sc1.nextInt();
			if(result == 1){
				//Scanner scForHumanPlayerName = new Scanner(System.in);
				playerName = "Aggressive";
				playerNameAggresive = playerName;
				System.out.println(" playerNameAggresive : " + playerNameAggresive);
				System.out.println("You have selected aggresive player");
				//playerName = scForHumanPlayerName.nextLine();
			}else if(result == 2){
				playerName = "Random";
				playerNameRandom = playerName;
				System.out.println(" playerNameRandom : " + playerNameRandom);
				System.out.println("You have selected random player");
			}else if(result == 3){
				playerName = "Benovalent";
				playerNameBenevolent = playerName;
				System.out.println(" playerNameBenavolent : " + playerNameBenevolent);
				System.out.println("You have selected benevolent player");
			}else if(result == 4){
				playerName = "Cheater";
				playerNameCheater = playerName;
				System.out.println(" playerNameCheater : " + playerNameCheater);
				System.out.println("You have selected cheater player");
			}else{
				System.out.println("Please choose the correct player");
			}
			addTournamentPlayerName(playerName);
		}
	}


	/**
	 * This method is used to ask the user to enter the player name he want to give to the player.
	 * @param noOfPlayers total no of players.
	 * @throws IOException exception
	 */
	public static void playerName(int noOfPlayers) throws IOException{
		Scanner sc1 = new Scanner(System.in);
		int result = 0;
		String playerName = null;
		for (int i = 0; i < noOfPlayers; i++) {
			System.out.println("You have following types of players. Choose the tpe of player you want to playe with.");
			System.out.println("1. Human Player");
			System.out.println("2. Aggressive Player");
			System.out.println("3. Random Player");
			System.out.println("4. Benovalent Player");
			System.out.println("5. Cheater Player");
			result = sc1.nextInt();
			if(result == 1){
				Scanner scForHumanPlayerName = new Scanner(System.in);
				System.out.println("Please enter the name of the human player.");
				playerName = scForHumanPlayerName.nextLine();
			}else if(result == 2){
				playerName = "Aggressive";
			}else if(result == 3){
				playerName = "Random";
			}else if(result == 4){
				playerName = "Benovalent";
			}else if(result == 5){
				playerName = "Cheater";
			}else{
				System.out.println("Please choose the correct player");
			}
			addPlayerName(playerName);
		}
		//		initialPlayerCountry();
	}

	/**
	 * This method is used to implement the logic of addin player name to player vector.
	 * @param playerName name of the player.
	 * @return true if everything goes well.
	 * @throws IOException exception.
	 */
	public static boolean addPlayerName(String playerName) throws IOException {
		int size = players.size();
		PlayerToPlay p = new PlayerToPlay(playerName, size);
		players.add(p);
		initialPlayer();
		return true;
	}

	/**
	 * This method is used to implement the logic of addin player name to player vector.
	 * @param playerName name of the player.
	 * @return true if everything goes well.
	 * @throws IOException exception.
	 */
	public static boolean addTournamentPlayerName(String playerName) throws IOException {
		int size = players.size();
		PlayerToPlay p = new PlayerToPlay(playerName, size);
		playersForTournament.add(p);
		initialPlayerForTournament();
		return true;
	}

	/**
	 * This method is used to initialize the initial player.
	 * @throws IOException exception.
	 */
	public static void initialPlayer() throws IOException {
		currentPlayer = players.elementAt(0);
	}

	/**
	 * This method is used to initialize the initial player.
	 * @throws IOException exception.
	 */
	public static void initialPlayerForTournament() throws IOException {
		currentPlayer = playersForTournament.elementAt(0);
	}

	/**
	 * This method is used to distribute initial countries to every player
	 * @return true if everything goes well.
	 * @throws IOException exception.
	 */
	public static boolean initialPlayerCountry(int typeOfGame) throws IOException{

		if(typeOfGame == 1) {
			int i = 0;
			int j = MapLoader.countryFilter.size() - 1;
			while (j >= 0) {
				initialCountries.add(MapLoader.countryFilter.get(j));
				players.get(i);

				ArrayList<String> list;
				if (initialPlayerCountry.containsKey(players.get(i).getName())) {
					list = initialPlayerCountry.get(players.get(i).getName());
					list.add(MapLoader.countryFilter.get(j));
				} else {
					list = new ArrayList<String>();
					list.add(MapLoader.countryFilter.get(j));
					initialPlayerCountry.put(players.get(i).getName(), list);
				}
				i++;
				j--;
				if (i == players.size()) {
					i = 0;
				}
			}
			distributeArmies(noOfPlayer, 1);
		}else if(typeOfGame == 2) {
			int i = 0;
			int j = MapLoader.countryFilter.size() - 1;
			while (j >= 0) {
				initialCountries.add(MapLoader.countryFilter.get(j));
				playersForTournament.get(i);

				ArrayList<String> list;
				if (initialPlayerCountry.containsKey(playersForTournament.get(i).getName())) {
					list = initialPlayerCountry.get(playersForTournament.get(i).getName());
					list.add(MapLoader.countryFilter.get(j));
				} else {
					list = new ArrayList<String>();
					list.add(MapLoader.countryFilter.get(j));
					initialPlayerCountry.put(playersForTournament.get(i).getName(), list);
				}
				i++;
				j--;
				if (i == playersForTournament.size()) {
					i = 0;
				}
			}
			noOfPlayer = playersForTournament.size();
			distributeArmies(noOfPlayer, 2);
		}
		return true;
	}

	/**
	 * This method is used to distribute armies to each player.
	 * @param noOfPlayers no of players playing the game.
	 * @return true is everything goes well.
	 * @throws IOException exception
	 */
	public static boolean distributeArmies(int noOfPlayers , int typeOfGame) throws IOException{
		if(typeOfGame == 1) {
			int armies = 0;
			if (noOfPlayers == 3)
				armies = 35;
			// armies = 35;
			if (noOfPlayers == 4)
				armies = 30;
			// armies = 30;
			if (noOfPlayers == 5)
				armies = 25;
			if (noOfPlayers == 6)
				armies = 20;
			for (int i = 0; i < noOfPlayers; i++) {
				players.elementAt(i).addArmies(armies);
			}
		}else if(typeOfGame == 2) {
			int armies = 0;
			if (noOfPlayers == 3)
				armies = 35;
			// armies = 35;
			if (noOfPlayers == 4)
				armies = 30;
			// armies = 30;
			if (noOfPlayers == 5)
				armies = 25;
			if (noOfPlayers == 6)
				armies = 20;
			for (int i = 0; i < noOfPlayers; i++) {
				playersForTournament.elementAt(i).addArmies(armies);
			}
		}
		return true;
	}

	/**
	 * This method on call directs the next player.
	 * @throws IOException excpetion.
	 */
	public static void nextPlayer(int typeOfGame) throws IOException {
		if(typeOfGame == 1) {
			if (currentPlayer == players.lastElement()) {
				currentPlayer = players.elementAt(0);
				iter = 0;
			} else
				currentPlayer = players.elementAt(++iter);
		}else if(typeOfGame == 2) {
			if (currentPlayer == playersForTournament.lastElement()) {
				currentPlayer = playersForTournament.elementAt(0);
				iter = 0;
			} else
				currentPlayer = playersForTournament.elementAt(++iter);
		}

	}

	/**
	 * This method is used to place initial armies to every country that is 0.
	 * @throws IOException exception.
	 */
	public static void initiallyPlaceArmies() throws IOException {
		int totalNumberOfCountries = MapLoader.countryFilter.size();
		for (int i = 0; i < totalNumberOfCountries; i++) {
			countriesArmies.put(MapLoader.countryFilter.get(i), 0);
		}
	}

	/**
	 * Method for asking the user to place armies on each country they own in round robin fashion.
	 * @return true if everything goes well
	 * @throws IOException excpetion
	 */
	public static boolean placeArmies(int typeOfGame) throws IOException{
		int loopSize = 0;
		if(typeOfGame == 1) {
			loopSize = players.size() * currentPlayer.getArmies();
		}else if(typeOfGame == 2) {
			loopSize = playersForTournament.size() * currentPlayer.getArmies();
		}
		int updatedArmies;
		Scanner sc = new Scanner(System.in);
		ArrayList<String> temp = new ArrayList<>();
		int iteratorForAggressive = 0;
		int iteratorForBonavalent = 0;
		int iteratorForRandom = 0;
		int iteratorForCheater = 0;
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
			if(currentPlayer.getName().equals("Aggressive")){
				String resultForAgressive = initialPlayerCountry.get(currentPlayer.getName()).get(iteratorForAggressive);
				if(currentPlayer.getArmies() != 0) {
					updatedArmies = countriesArmies.get(resultForAgressive) + 1;
					updateArmies(updatedArmies, resultForAgressive, currentPlayer);
					iteratorForAggressive = iteratorForAggressive +1;
					if(iteratorForAggressive == initialPlayerCountry.get(currentPlayer.getName()).size()){
						iteratorForAggressive = 0;
					}
					if(typeOfGame == 1)
						nextPlayer(1);
					else if(typeOfGame == 2)
						nextPlayer(2);
					temp.clear();
				}else {
					System.out.println("You dont  have enough armies to move");
					if(typeOfGame == 1)
						nextPlayer(1);
					else if(typeOfGame == 2)
						nextPlayer(2);
					temp.clear();
				}
				
			}else if(currentPlayer.getName().equals("Benovalent")){
				String resultForBenovalent = initialPlayerCountry.get(currentPlayer.getName()).get(iteratorForBonavalent);
				if(currentPlayer.getArmies() != 0) {
					updatedArmies = countriesArmies.get(resultForBenovalent) + 1;
					updateArmies(updatedArmies, resultForBenovalent, currentPlayer);
					iteratorForBonavalent = iteratorForBonavalent +1;
					if(iteratorForBonavalent == initialPlayerCountry.get(currentPlayer.getName()).size()){
						iteratorForBonavalent = 0;
					}
					if(typeOfGame == 1)
						nextPlayer(1);
					else if(typeOfGame == 2)
						nextPlayer(2);
					temp.clear();
				}else {
					System.out.println("You dont  have enough armies to move");
					if(typeOfGame == 1)
						nextPlayer(1);
					else if(typeOfGame == 2)
						nextPlayer(2);
					temp.clear();
				}
			}else if(currentPlayer.getName().equals("Random")){
				String resultForRandom = initialPlayerCountry.get(currentPlayer.getName()).get(iteratorForRandom);
				if(currentPlayer.getArmies() != 0) {
					updatedArmies = countriesArmies.get(resultForRandom) + 1;
					updateArmies(updatedArmies, resultForRandom, currentPlayer);
					iteratorForRandom = iteratorForRandom +1;
					if(iteratorForRandom == initialPlayerCountry.get(currentPlayer.getName()).size()){
						iteratorForRandom = 0;
					}
					if(typeOfGame == 1)
						nextPlayer(1);
					else if(typeOfGame == 2)
						nextPlayer(2);
					temp.clear();
				}else {
					System.out.println("You dont  have enough armies to move");
					if(typeOfGame == 1)
						nextPlayer(1);
					else if(typeOfGame == 2)
						nextPlayer(2);
					temp.clear();
				}
			}else if(currentPlayer.getName().equals("Cheater")){
				String resultForCheater = initialPlayerCountry.get(currentPlayer.getName()).get(iteratorForCheater);
				if(currentPlayer.getArmies() != 0) {
					updatedArmies = countriesArmies.get(resultForCheater) + 1;
					updateArmies(updatedArmies, resultForCheater, currentPlayer);
					iteratorForCheater = iteratorForCheater +1;
					if(iteratorForCheater == initialPlayerCountry.get(currentPlayer.getName()).size()){
						iteratorForCheater = 0;
					}
					if(typeOfGame == 1)
						nextPlayer(1);
					else if(typeOfGame == 2)
						nextPlayer(2);
					temp.clear();
				}else {
					System.out.println("You dont  have enough armies to move");
					if(typeOfGame == 1)
						nextPlayer(1);
					else if(typeOfGame == 2)
						nextPlayer(2);
					temp.clear();
				}
			}else if(currentPlayer.getName().equals("Human")){
				if(typeOfGame == 1)
					nextPlayer(1);
				else if(typeOfGame == 2)
					nextPlayer(2);
				temp.clear();
			}
		}
		return true;
	}

	/**
	 * This method is logic behind adding armies to every country player own.
	 * @param updatedArmies no of armies player want to add.
	 * @param countryName name of the country where to add.
	 * @param playerName name of player want to add.
	 * @return true if all goes well.
	 */
	public static boolean updateArmies(int updatedArmies, String countryName, PlayerToPlay playerName) {
		countriesArmies.put(countryName, updatedArmies);
		playerName.looseArmy();
		return true;
	}


}
