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
	static MapLoader obj = new MapLoader();
	static int noOfPlayer = 0;
	public static PlayerToPlay currentPlayer;
	public static HashMap<String, ArrayList<String>> initialPlayerCountry = new HashMap<String, ArrayList<String>>();
	public static ArrayList<String> initialCountries = new ArrayList<String>();
	public static HashMap<String, Integer> countriesArmies = new HashMap<String, Integer>();
	public static int iter = 0;
	/**
	 * This method is used to initially start the game.
	 * @param args arguments.
	 * @throws IOException Throw exception
	 */

	public static void gamePlay(){
		
		try{
			MapLoader.loadMap(RiskInterface.pathMap);
			askUserToSelectPlayers();
			System.out.println(players.get(0).getName());
			System.out.println(players.get(1).getName());
			System.out.println(players.get(2).getName());
			System.out.println(initialPlayerCountry.get(players.get(0).getName()).size() + initialPlayerCountry.get(players.get(2).getName()).size() + initialPlayerCountry.get(players.get(1).getName()).size());
			System.out.println(MapLoader.countryFilter.size());
			initiallyPlaceArmies();

			placeArmies();
			
			//variables declared for tournament start up phase
			String [] maplist = new String[5];
			maplist[0] = "resources/maps/";
			maplist[1] = "resources/maps/";
			maplist[2] = "resources/maps/";
			maplist[3] = "resources/maps/";
			maplist[4] = "resources/maps/";
			
			int [] numberOfGamesToPlay = new int[4];
			int numOfMaps = 0;
			int numOfPlayersStrategies = 0;
			
			int maxTurns = 0;
			int numOfGames = 0;
			
			//Tournament rules declaration
			System.out.println("Please read tournament rules first:  ");
			System.out.println("Number of maps = 1 to 5" +" " +"number of players = 2 to 4" +" " +"number of games = 1 to 5" +" " +"Number of maximum turns = 10 to 50");
			
			Scanner tournamentscan = new Scanner(System.in);
			
			System.out.println(" Enter number of maps to play: ");
			numOfMaps = tournamentscan.nextInt();
			
			System.out.println(" Enter number of players to play with: ");
			numOfPlayersStrategies = tournamentscan.nextInt();
			
			System.out.println("Enter number of games to be played: ");
			numOfGames = tournamentscan.nextInt();
			
			System.out.println("Enter number of maximum turns:  ");
			maxTurns = tournamentscan.nextInt();
			
			
			//logic for start up playing tournament mode
			for(int i = 0; i < maplist.length; i++ )
			{
				for (int j = 0; j <numberOfGamesToPlay.length; j++)
				{
					do{
						
						AssigningStrategy objAssigningStrategy = new AssigningStrategy();
				        
				        // Three contexts following different strategies
						objAssigningStrategy.setStrategy(new BenevolentPlayer());
						objAssigningStrategy.executeStrategy(currentPlayer);

						nextPlayer();
						
						objAssigningStrategy.setStrategy(new AggresivePlayer());
						objAssigningStrategy.executeStrategy(currentPlayer);
						
						nextPlayer();


						objAssigningStrategy.setStrategy(new RandomPlayer());
						objAssigningStrategy.executeStrategy(currentPlayer);
						
						nextPlayer();

						
					}while(maxTurns == 30 );
					
				}
			}


			
			AssigningStrategy objAssigningStrategy = new AssigningStrategy();
	        
	        // Three contexts following different strategies
			objAssigningStrategy.setStrategy(new BenevolentPlayer());
			objAssigningStrategy.executeStrategy(currentPlayer);


			objAssigningStrategy.setStrategy(new AggresivePlayer());
			objAssigningStrategy.executeStrategy(currentPlayer);

			objAssigningStrategy.setStrategy(new RandomPlayer());
			objAssigningStrategy.executeStrategy(currentPlayer);
			
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
		sc.close();
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
		}else if(noOfPlayerToPlay >= 3 && noOfPlayerToPlay <= 6){
			playerName(noOfPlayerToPlay);
			result = noOfPlayerToPlay + " no of players are selected to play the game";
		}
		return result;
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
			System.out.println("You have fooloeing types of players. Choose the tpe of player you want to playe with.");
			System.out.println("1. Human Player");
			System.out.println("2. Aggressive Player");
			System.out.println("3. Random Player");
			System.out.println("4. Bonavalent Player");
			System.out.println("5. Cheater Player");
			result = sc1.nextInt();
			if(result == 1){
				Scanner scForHumanPlayerName = new Scanner(System.in);
				System.out.println("Please enter the name of the human player.");
				playerName = scForHumanPlayerName.nextLine();
				scForHumanPlayerName.close();
			}else if(result == 2){
				playerName = "Aggressive";
			}else if(result == 3){
				playerName = "Random";
			}else if(result == 4){
				playerName = "Bonavalent";
			}else if(result == 5){
				playerName = "Cheater";
			}else{
				System.out.println("Please choose the correct player");
			}
			addPlayerName(playerName);
		}
		initialPlayerCountry();
		sc1.close();
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
	 * This method is used to initialize the intial player.
	 * @throws IOException exception.
	 */
	public static void initialPlayer() throws IOException {
		currentPlayer = players.elementAt(0);
	}
	
	/**
	 * This method is used to distribute initial countries to every player
	 * @return true if everything goes well.
	 * @throws IOException exception.
	 */
	public static boolean initialPlayerCountry() throws IOException{
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
		distributeArmies(noOfPlayer);
		return true;
	}

	/**
	 * This method is used to distribute armies to each player.
	 * @param noOfPlayers no of players playing the game.
	 * @return true is everything goes well.
	 * @throws IOException exception
	 */
	public static boolean distributeArmies(int noOfPlayers) throws IOException{
		int armies = 0;
		if (noOfPlayers == 3)
			armies = 4;
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
		return true;
	}

	/**
	 * This method on call directs the next player.
	 * @throws IOException excpetion.
	 */
	public static void nextPlayer() throws IOException {
		if (currentPlayer == players.lastElement()) {
			currentPlayer = players.elementAt(0);
			iter = 0;
		} else
			currentPlayer = players.elementAt(++iter);
	}
	
	/**
	 * This method is used to place intial armies to every country that is 0.
	 * @throws IOException exception.
	 */
	public static void initiallyPlaceArmies() throws IOException {
		int totalNumberOfCountries = MapLoader.countryFilter.size();
		for (int i = 0; i < totalNumberOfCountries; i++) {
			countriesArmies.put(MapLoader.countryFilter.get(i), 0);
		}
		placeArmies();
	}
	
	/**
	 * Method for asking the user to place armies on each country they own in round robin fashion.
	 * @return true if everything goes well
	 * @throws IOException excpetion
	 */
	public static boolean placeArmies() throws IOException{
		int loopSize = players.size() * currentPlayer.getArmies();
		int updatedArmies;
		Scanner sc = new Scanner(System.in);
		ArrayList<String> temp = new ArrayList<>();
		System.out.println("start here  :  ");
		int iteratorForAggressive = 0;
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
				iteratorForAggressive = iteratorForAggressive +1;
				if(iteratorForAggressive == initialPlayerCountry.get(currentPlayer.getName()).size()){
					iteratorForAggressive = 0;
				}
				updatedArmies = countriesArmies.get(resultForAgressive) + 1;
				System.out.println(temp);
				nextPlayer();
			}else{
				nextPlayer();
				System.out.println(currentPlayer.getName());
			}
		}
		sc.close();
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
