package in.risk.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class CheaterPlayer implements Strategy {

	// variables for reinforcement phase 

	public static HashMap<String, List<String>> playersCards = new HashMap<String, List<String>>();
	public static List<String> cardsInTheDeck = new ArrayList<String>();
	public static ArrayList<String> deck = new ArrayList<String>();
	public static ArrayList<String> cardType = new ArrayList<String>();
	public static int cardFlag = 1;

	public static String cardTypeA = "A";
	public static String cardTypeB = "B";
	public static String cardTypeC = "C";
	public static int cardInTheDeck;
	public static HashMap<String, Integer> findingStrongestCOuntry = new HashMap<>();


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
	public static String strongestCountry="";
	public static int armyOfStrongestCountry = 0;
	public static int updatedarmyOfStrongestCountry = 0;
	public static int conqueredMapCounter = 0;
	public static int temp = 0;



	/** This method is used to place reinforcement armies and then place them in the countries where player want to place them
	 * @param playerName name of the player.
	 * @throws IOException exception
	 */
	public void placeReinforcementArmies(PlayerToPlay playerName) throws IOException{

		System.out.println("####################     REINFORCEMENT Phase BEGINS     #################### ");
		StartUpPhase.loggingString("####################     REINFORCEMENT Phase BEGINS     #################### ");
		System.out.println("Player Name on ENTERING REINFORCEMENT Phase ++++++++++++++++++ " +playerName.getName());
		StartUpPhase.loggingString("Player Name on ENTERING REINFORCEMENT Phase ++++++++++++++++++ " +playerName.getName());
		System.out.println("You have these countries under your control "	+ StartUpPhase.initialPlayerCountry.get(playerName.getName()));
		StartUpPhase.loggingString("You have these countries under your control "	+ StartUpPhase.initialPlayerCountry.get(playerName.getName()));
		System.out.println("country armies: for All player " + StartUpPhase.countriesArmies);
		StartUpPhase.loggingString("country armies: for All player " + StartUpPhase.countriesArmies);
		String countryNameToEnterArmies;
		int noOfArmiesWantToPlace;

		int t = 0;
		int loop = StartUpPhase.initialPlayerCountry.get(playerName.getName()).size();
		System.out.println("loop size is : "+ loop);
		
		ArrayList<String> playerCountry = new ArrayList<>();
		for(int i =0; i< loop; i++)
		{
			playerCountry.add(StartUpPhase.initialPlayerCountry.get(playerName.getName()).get(i));
		}
		System.out.println("playerCountry names are "+playerCountry);
		StartUpPhase.loggingString("playerCountry names are "+playerCountry);
		for (Entry<String, Integer> entry : StartUpPhase.countriesArmies.entrySet()) {
			String key = entry.getKey();
			Integer value = entry.getValue();

			for(int i =0; i< playerCountry.size(); i++){
				String str = playerCountry.get(i).toString();
				key = key.toString();

				if(str.equals(key))
				{
					StartUpPhase.countriesArmies.put(key, value*2);
				}
			}
		}

		System.out.println("You have these countries under your control "	+ StartUpPhase.initialPlayerCountry.get(playerName.getName()));
		StartUpPhase.loggingString("You have these countries under your control "	+ StartUpPhase.initialPlayerCountry.get(playerName.getName()));
		System.out.println("country armies: for All player " + StartUpPhase.countriesArmies);
		StartUpPhase.loggingString("country armies: for All player " + StartUpPhase.countriesArmies);
		attackPhase(playerName);
		return;
	}


	public  void attackPhase(PlayerToPlay playerName) throws IOException{
		System.out.println("####################     Attack Phase BEGINS     #################### ");
		StartUpPhase.loggingString("####################     Attack Phase BEGINS     #################### ");
		ArrayList<String> countriesOfCheaterPlayer = StartUpPhase.initialPlayerCountry.get(playerName.getName());
		
		System.out.println("Countries of Cheaeter Player = " + countriesOfCheaterPlayer);
		StartUpPhase.loggingString("Countries of Cheaeter Player = " + countriesOfCheaterPlayer);
		int sizeOfCheaterPlayerCountries = countriesOfCheaterPlayer.size();
		
		System.out.println("Size of cheater player countries = " + sizeOfCheaterPlayerCountries);
		StartUpPhase.loggingString("Size of cheater player countries = " + sizeOfCheaterPlayerCountries);
		for(int iteratorForCountries = 0; iteratorForCountries < sizeOfCheaterPlayerCountries ; iteratorForCountries++){
			String counrtryName = StartUpPhase.initialPlayerCountry.get(playerName.getName()).get(iteratorForCountries);
			System.out.println("Currently testing country = " + counrtryName);
			StartUpPhase.loggingString("Currently testing country = " + counrtryName);
			List<String> adjacentOfCurrentCountry = MapLoader.adj.get(counrtryName);
			System.out.println("Adjacent of " + counrtryName + " are = " + adjacentOfCurrentCountry);
			StartUpPhase.loggingString("Adjacent of " + counrtryName + " are = " + adjacentOfCurrentCountry);
			int sizeOfAdjacent = adjacentOfCurrentCountry.size();
			
			for(int iteratorForAdjacent = 0; iteratorForAdjacent < sizeOfAdjacent ; iteratorForAdjacent++){
				String adjacentCountry = adjacentOfCurrentCountry.get(iteratorForAdjacent);
				
				System.out.println("Adjacent countries to " + counrtryName + "are " + adjacentCountry);
				StartUpPhase.loggingString("Adjacent countries to " + counrtryName + "are " + adjacentCountry);
				if(!countriesOfCheaterPlayer.contains(adjacentCountry)){
					
					countriesOfCheaterPlayer.add(adjacentCountry);
					
					StartUpPhase.initialPlayerCountry.put(playerName.getName(), countriesOfCheaterPlayer);
					
					System.out.println(StartUpPhase.initialPlayerCountry.get(playerName.getName()) + "\n");
					StartUpPhase.loggingString(StartUpPhase.initialPlayerCountry.get(playerName.getName()) + "\n");
				}
			}
		}
		System.out.println("******************     Attack Phase ENDS   *********************** " +playerName.getName());
		StartUpPhase.loggingString("******************     Attack Phase ENDS   *********************** " +playerName.getName());
		System.out.println("You have these countries under your control "	+ StartUpPhase.initialPlayerCountry.get(playerName.getName()));
		StartUpPhase.loggingString("You have these countries under your control "	+ StartUpPhase.initialPlayerCountry.get(playerName.getName()));
		System.out.println("country armies: for All player " + StartUpPhase.countriesArmies);
		StartUpPhase.loggingString("country armies: for All player " + StartUpPhase.countriesArmies);
		fortifyPhase(playerName);
		return;
	}


	/**
	 * This method used to capture the fortify phase information
	 * of each player.
	 * @param currentPlayer
	 * @throws IOException
	 */
	public void fortifyPhase(PlayerToPlay playerName) throws IOException {
		System.out.println("####################     FORTIFY Phase BEGINS     #################### ");
		StartUpPhase.loggingString("####################     FORTIFY Phase BEGINS     #################### ");
		int newNoOfArmies = 0;
		ArrayList<String> countriesOfCheaterPlayer = StartUpPhase.initialPlayerCountry.get(playerName.getName());
		
		System.out.println("Countries of Cheaeter Player = " + countriesOfCheaterPlayer);
		StartUpPhase.loggingString("Countries of Cheaeter Player = " + countriesOfCheaterPlayer);
		int sizeOfCheaterPlayerCountries = countriesOfCheaterPlayer.size();
		
		System.out.println("Size of cheater player countries = " + sizeOfCheaterPlayerCountries);
		StartUpPhase.loggingString("Size of cheater player countries = " + sizeOfCheaterPlayerCountries);
		
		for(int iteratorForFindingTheAdjacent =0; iteratorForFindingTheAdjacent < sizeOfCheaterPlayerCountries ; iteratorForFindingTheAdjacent++){
			String counrtryName = StartUpPhase.initialPlayerCountry.get(playerName.getName()).get(iteratorForFindingTheAdjacent);
			System.out.println("Currently testing country = " + counrtryName);
			StartUpPhase.loggingString("Currently testing country = " + counrtryName);
			List<String> adjacentOfCurrentCountry = MapLoader.adj.get(counrtryName);
			
			System.out.println("Adjacent of " + counrtryName + " are = " + adjacentOfCurrentCountry);
			StartUpPhase.loggingString("Adjacent of " + counrtryName + " are = " + adjacentOfCurrentCountry);
			int sizeOfAdjacent = adjacentOfCurrentCountry.size();
			
			System.out.println("Size of adjacent countries " + sizeOfAdjacent);
			StartUpPhase.loggingString("Size of adjacent countries " + sizeOfAdjacent);
			ArrayList<Boolean> tempForAdjacent = new ArrayList<>();
			for(int iteratorForAdjacent = 0; iteratorForAdjacent < sizeOfAdjacent ; iteratorForAdjacent++){
				String adjacentCountry = adjacentOfCurrentCountry.get(iteratorForAdjacent);
				
				System.out.println("Adjacent countries to " + counrtryName + "are " + adjacentCountry);
				StartUpPhase.loggingString("Adjacent countries to " + counrtryName + "are " + adjacentCountry);
				if(countriesOfCheaterPlayer.contains(adjacentCountry)){
					System.out.println("True");
					StartUpPhase.loggingString("True");
					tempForAdjacent.add(true);
				}else{
					System.out.println("False");
					StartUpPhase.loggingString("False");
					tempForAdjacent.add(false);
				}
			}
			System.out.println("temp for adjacent = " + tempForAdjacent);
			StartUpPhase.loggingString("temp for adjacent = " + tempForAdjacent);
			if(tempForAdjacent.contains(false)){
				System.out.println(counrtryName + " has some adjacent countries belongd to other players.");
				StartUpPhase.loggingString(counrtryName + " has some adjacent countries belongd to other players.");
				int oldArmies = StartUpPhase.countriesArmies.get(counrtryName);
				
				System.out.println(counrtryName + " has " + StartUpPhase.countriesArmies.get(counrtryName));
				StartUpPhase.loggingString(counrtryName + " has " + StartUpPhase.countriesArmies.get(counrtryName));
				int newArmies = oldArmies*2;
				
				newNoOfArmies = newArmies;
				StartUpPhase.countriesArmies.put(counrtryName, newArmies);
			

				System.out.println(counrtryName + " has " + newArmies  + "\n");
				StartUpPhase.loggingString(counrtryName + " has " + newArmies  + "\n");
			}else{
				int oldArmies = StartUpPhase.countriesArmies.get(counrtryName);
				
				newNoOfArmies = oldArmies;
			}
		}
		//return newNoOfArmies;
		
		System.out.println("You have these countries under your control "	+ StartUpPhase.initialPlayerCountry.get(playerName.getName()));
		StartUpPhase.loggingString("You have these countries under your control "	+ StartUpPhase.initialPlayerCountry.get(playerName.getName()));
		System.out.println("country armies: for All player " + StartUpPhase.countriesArmies);
		StartUpPhase.loggingString("country armies: for All player " + StartUpPhase.countriesArmies);
		System.out.println("*******************    FORTIFY Phase ENDS   ******************* " +playerName.getName());
		StartUpPhase.loggingString("*******************    FORTIFY Phase ENDS   ******************* " +playerName.getName());
	}
}