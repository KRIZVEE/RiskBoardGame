package in.risk.utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;
import java.util.Map.Entry;

/**
 * This class is used to implement the intial starting of the game.
 * @author mohitrana
 *
 */
public class StartUpPhase {
	static public Vector<PlayerToPlay> players = new Vector<PlayerToPlay>();
	static LoadMap obj = new LoadMap();
	static int noOfPlayer = 0;
	public static PlayerToPlay currentPlayer;
	public static HashMap<String, ArrayList<String>> initialPlayerCountry = new HashMap<String, ArrayList<String>>();
	public static ArrayList<String> initialCountries = new ArrayList<String>();
	public static HashMap<String, Integer> countriesArmies = new HashMap<String, Integer>();
	public static int iter = 0;
	public static int conqueredMapCounterTURN = 0;

	/**
	 * This method is used to initially start the game.
	 * @param args arguments.
	 * @throws IOException Throw excetion.
	 */
	public static void main(String args[]) throws IOException{
		obj.loadMap("World2005.map");
		askUserToSelectPlayers();
		initiallyPlaceArmies();
		//placeArmies();
		
		System.out.println(currentPlayer.getName());
		System.out.println(initialPlayerCountry);
		System.out.println(countriesArmies);
		
		int tournamentLoop = 0;
		int worldWON = 0;
		AssigningStrategy objAssigningStrategy = new AssigningStrategy();
		
		

		
		do{
			System.out.println("**************tournamentLoop : VALUE*****************" + tournamentLoop);
			
			System.out.println(tournamentLoop);
			
//			if(conqueredMapCounterTURN==0)
//			{
//			objAssigningStrategy.setStrategy(new BenevolentPlayer());
//			System.out.println("========Current Player Name is: =========" + currentPlayer.getName());
//			objAssigningStrategy.executeStrategy(currentPlayer);}
			
			nextPlayer();
//			if(conqueredMapCounterTURN==0)
//			{
//			objAssigningStrategy.setStrategy(new AggresivePlayer());
//			System.out.println("========Current Player Name is: =========" + currentPlayer.getName());
//			objAssigningStrategy.executeStrategy(currentPlayer);}
			
			nextPlayer();
			
			if(conqueredMapCounterTURN==0)
			{
			objAssigningStrategy.setStrategy(new RandomPlayer());
			System.out.println("========Current Player Name is: =========" + currentPlayer.getName());
			objAssigningStrategy.executeStrategy(currentPlayer);}
			System.out.println("I AM HERE");
			nextPlayer();
			
			
			tournamentLoop++;
//			System.out.println(tournamentLoop);
		}while(tournamentLoop <= 2||conqueredMapCounterTURN==1);
		
		System.out.println(" tournamentLoop " + tournamentLoop);
		System.out.println(" conqueredMapCounterTURN "+ conqueredMapCounterTURN);
		if(conqueredMapCounterTURN == 0)
			System.out.println("Match Result is: "+ "DRAW");
		else
			System.out.println("WINNER NAME is: "+ currentPlayer.getName());
		
        System.out.println("Final Initial Player COuntry List" + initialPlayerCountry);
        System.out.println("Final COuntry Armies List" + countriesArmies);

        // Three contexts following different strategies



        

		
		
		
//		Player.placeReinforcementArmies(currentPlayer);
//		Player.attackPhase(currentPlayer);// @author Kashif
//		Player.fortifyPhase(currentPlayer);// @author Kashif


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
		String result = null;
		for (int i = 0; i < noOfPlayers; i++) {
			System.out.println("Enter the name of the player you want to add.");
			result = sc1.nextLine();
			addPlayerName(result);
		}
		initialPlayerCountry();
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
		int j = LoadMap.countryFilter.size() - 1;
		while (j >= 0) {
			initialCountries.add(LoadMap.countryFilter.get(j));
			players.get(i);

			ArrayList<String> list;
			if (initialPlayerCountry.containsKey(players.get(i).getName())) {
				list = initialPlayerCountry.get(players.get(i).getName());
				list.add(LoadMap.countryFilter.get(j));
			} else {
				list = new ArrayList<String>();
				list.add(LoadMap.countryFilter.get(j));
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
		int totalNumberOfCountries = LoadMap.countryFilter.size();
		for (int i = 0; i < totalNumberOfCountries; i++) {
			countriesArmies.put(LoadMap.countryFilter.get(i), 2);
		}
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
						System.out.println("Next Phase");
				}
				if (noOfArmiesPlayerContains == countriesWith0Armies) {
					System.out.println(currentPlayer.getArmies());
					System.out.println(countriesArmies);
					
					if (temp.contains(result)){
						updatedArmies = countriesArmies.get(result) + 1;
						updateArmies(updatedArmies, result, currentPlayer);
						System.out.println(result + " armies has been updated. New armies of " + result + " are "
								+ countriesArmies.get(result));
						temp.remove(result);
						System.out.println(currentPlayer.getName() + " has Countries with 0 number of armies " + temp);
						temp.clear();
						nextPlayer();
					}else{
						System.out.println("You are not allowed to add armies to other countries except " + temp
								+ " countries. \n Because you have minimum number of armies to place ermies in each countyr");
						System.out.println(
								"Please enter the name of country from given list where you want to placce armies \n"
										+ temp);
						result = sc.nextLine();
						if (temp.contains(result)) {
							updatedArmies = countriesArmies.get(result) + 1;
							updateArmies(updatedArmies, result, currentPlayer);
							System.out.println(result + " armies has been updated. New armies of " + result + " are "
									+ countriesArmies.get(result));
							temp.remove(result);
							System.out.println(
									currentPlayer.getName() + " has Countries with 0 number of armies " + temp);
							temp.clear();
							nextPlayer();
						}
					}
				}else{
					updatedArmies = countriesArmies.get(result) + 1;
					updateArmies(updatedArmies, result, currentPlayer);
					System.out.println(result + " armies has been updated. New armies of " + result + " are "
							+ countriesArmies.get(result));
					temp.remove(result);
					System.out.println((currentPlayer.getName() + " has Countries with 0 number of armies " + temp));
					temp.clear();
					nextPlayer();
				}
			}else{
				System.out.println("Please enter correct name of the country");
				i--;
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
