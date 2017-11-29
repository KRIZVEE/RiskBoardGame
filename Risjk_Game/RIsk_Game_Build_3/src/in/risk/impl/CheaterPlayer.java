package in.risk.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Map.Entry;

public class CheaterPlayer implements RiskStrategy {
	
	public  HashMap<String, List<String>> playersCards = new HashMap<String, List<String>>();
	public  List<String> cardsInTheDeck = new ArrayList<String>();
	public  ArrayList<String> deck = new ArrayList<String>();
	public  ArrayList<String> cardType = new ArrayList<String>();
	public  int cardFlag = 1;

	public  String cardTypeA = "A";
	public  String cardTypeB = "B";
	public  String cardTypeC = "C";
	public  int cardInTheDeck;
	
	// variables for attack phase 
	
	public  HashMap<String, Integer> countriesArmiesObserver = new HashMap<String, Integer>();
	public  HashMap<String, ArrayList<String>> initialPlayerCountryObserver = new HashMap<String, ArrayList<String>>();

	public  String attackerCountry = "";
	public  String defenderCountry = "";

	public  int noOfArmiesFromCountries = 0;
	public  int noOfArmiesFromContinents = 0;
	public  int noOfArmiesFromCards = 0;
	public  int noOfAttackerDice = 0;
	public  int attacker, defender;
	public  int attackerDiceArray[];
	public  int defenderDiceArray[];

	public  int noOfDefenderDice;
	public  int flagCheckDice = 0;
	public  int indx = 0;// = value.
	public  int oldCOuntryListSize = 0;
	public  int newCOuntryListSize = 0;
	public  String newDefenderCountry = "";
	public  int updateArmyOfAttacker;
	public  int updateArmyOfDefender;
	public  int countNoOfAttack = 1;

	public  String from = "";
	public  String to = "";
	
	public static void getReinforcementArmies(PlayerToPlay playerName){
		System.out.println(StartUpPhase.countriesArmies.get(0));
		
	}

	public void placeReinforcementArmies(PlayerToPlay playerName) {
		
	}

	public void attackPhase(PlayerToPlay playerName) {
		
		Scanner sc = new Scanner(System.in);
		String attackTurnOn = "hello";

		System.out.println(StartUpPhase.initialPlayerCountry);
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

		System.out.println("Initailly player country list before any attack : "
				+ StartUpPhase.initialPlayerCountry.get(playerName.getName()));
		
		System.out.println("Initailly player country list with initial army : "
				+ StartUpPhase.countriesArmies.get(playerName.getName()));
		
		System.out.println("Current Player : " + playerName.getName());
		
		oldCOuntryListSize = StartUpPhase.initialPlayerCountry.get(playerName.getName()).size();
		System.out.println("===========oldCOuntryListSize :========== " + oldCOuntryListSize);
		
		System.out.println("Current Player owning ciuntries : "
				+ StartUpPhase.initialPlayerCountry.get(playerName.getName()));
		System.out.println("Please enter the name of country from where you want to attack");
		attackerCountry = input.readLine();

		System.out.println("No of armies in the attacker country : " + StartUpPhase.countriesArmies.get(attackerCountry));

		List<String> attackerCountryAdjacent = new ArrayList<String>();
		int size = StartUpPhase.initialPlayerCountry.get(playerName.getName()).size();
		int size2 = MapLoader.adj.get(attackerCountry).size();
		for (int i = 0; i < size2; i++) {
			if (StartUpPhase.initialPlayerCountry.get(playerName.getName())
					.contains(MapLoader.adj.get(attackerCountry).get(i))) {
				continue;
			} else {
				attackerCountryAdjacent.add(MapLoader.adj.get(attackerCountry).get(i));
			}
		}

		System.out.println("Based on this country name, you can attack to corresponding countries only : "
				+ attackerCountryAdjacent); // need to work here
		
		if(attackerCountryAdjacent.isEmpty()){
			Scanner sc123 = new Scanner(System.in);
			String result123;
			System.out.println("You cannot attack because the country is not adjacent to any enemy country. Do you want to attack more? Y/N");
			result123 = sc123.nextLine();
		if (attackTurnOn.equals("Y")) {

			attackPhase(StartUpPhase.currentPlayer);

		} else {

			newCOuntryListSize = StartUpPhase.initialPlayerCountry.get(playerName.getName()).size();
			System.out.println("===========newCOuntryListSize :========== " + newCOuntryListSize);

			if (newCOuntryListSize > oldCOuntryListSize) {
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

			countriesArmiesObserver.putAll(StartUpPhase.countriesArmies);
			initialPlayerCountryObserver.putAll(StartUpPhase.initialPlayerCountry);

		}
	}
	
	
	System.out.println("Please enter the name of country, to which you want to attack");
	defenderCountry = input.readLine();
	
	System.out.println("No of armies in the defender country : " + StartUpPhase.countriesArmies.get(defenderCountry));

	if (StartUpPhase.countriesArmies.get(attackerCountry) >= 2) {
		System.out.println("As, You have minimum of 2 armies, you can attack");
		
		if (StartUpPhase.countriesArmies.get(attackerCountry) > 3) {
			System.out.println("Attacker Country Player, can opt among 1, 2 or 3 dice to be rolled");
			noOfAttackerDice = sc.nextInt();
			
		} else if (StartUpPhase.countriesArmies.get(attackerCountry) == 3) {
			System.out.println("Attacker Country Player, can opt among 1 or 2 dice to be rolled");
			noOfAttackerDice = sc.nextInt();
			
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
			attackerDiceArray[i] = StartUpPhase.randomNumberGenerator();
		}

		if (StartUpPhase.countriesArmies.get(defenderCountry) >= 2) {

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
		System.out.println("Number of armies in Attacker Country is : " + StartUpPhase.countriesArmies.get(attackerCountry));	
		System.out.println("Number of armies in Defender Country is : " + StartUpPhase.countriesArmies.get(defenderCountry));
		
		if (noOfDefenderDice == 1) {

			if (attackerDiceArray[attackerDiceArray.length - 1] > defenderDiceArray[defenderDiceArray.length - 1]) {
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

				} else {
					StartUpPhase.countriesArmies.put(defenderCountry, updateArmyOfDefender);
				}
				System.out.println("new list of initial player country" + StartUpPhase.initialPlayerCountry);
				
				System.out.println("Initailly player country list with initial army : " + StartUpPhase.countriesArmies);
				

			} else {
				System.out.println(" Attacker country loose 1 army");
				
				updateArmyOfAttacker = StartUpPhase.countriesArmies.get(attackerCountry) - 1;

				StartUpPhase.countriesArmies.put(attackerCountry, updateArmyOfAttacker);

				System.out.println("new list of initial player country" + StartUpPhase.initialPlayerCountry);
				
				System.out.println("Initailly player country list with initial army : " + StartUpPhase.countriesArmies);
				
			}
		}

		else {

			if (attackerDiceArray[attackerDiceArray.length - 1] > defenderDiceArray[defenderDiceArray.length - 1]) {
				System.out.println(" Defender country loose 1 army");				
				updateArmyOfDefender = StartUpPhase.countriesArmies.get(defenderCountry) - 1;
				StartUpPhase.countriesArmies.put(defenderCountry, updateArmyOfDefender);

				if (attackerDiceArray[attackerDiceArray.length - 2] > defenderDiceArray[defenderDiceArray.length
						- 2]) {
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

					} else {
						StartUpPhase.countriesArmies.put(defenderCountry, updateArmyOfDefender);
					}
					System.out.println("new list of initial player country" + StartUpPhase.initialPlayerCountry);

					System.out.println(
							"Initailly player country list with initial army : " + StartUpPhase.countriesArmies);
				} else {
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
					System.out.println(" Attacker country loose 1 army");
					
					updateArmyOfAttacker = StartUpPhase.countriesArmies.get(attackerCountry) - 1;
					StartUpPhase.countriesArmies.put(attackerCountry, updateArmyOfAttacker);

					if (attackerDiceArray[attackerDiceArray.length
							- 2] <= defenderDiceArray[defenderDiceArray.length - 2]) {
						System.out.println(" Attacker country loose 1 more army");
						
						updateArmyOfAttacker -= 1;
						StartUpPhase.countriesArmies.put(attackerCountry, updateArmyOfAttacker);

					} else {
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

						} else {
							StartUpPhase.countriesArmies.put(defenderCountry, updateArmyOfDefender);
						}

					}

					StartUpPhase.countriesArmies.put(attackerCountry, updateArmyOfAttacker);

					System.out.println("new list of initial player country" + StartUpPhase.initialPlayerCountry);
					System.out.println(	"Initailly player country list with initial army : " + StartUpPhase.countriesArmies);
					
				}

			}
		}
	} else
		System.out.println("As you are having only 1 army, you can't attack");

	System.out.println("Do you want to still attack to other countries, press Y/N");
		
	attackTurnOn = input.readLine();

	if (attackTurnOn.equals("Y")) {
		countNoOfAttack++;
		attackPhase(currentPlayer);

	} else {

		newCOuntryListSize = StartUpPhase.initialPlayerCountry.get(playerName.getName()).size();
		System.out.println("===========newCOuntryListSize :========== " + newCOuntryListSize);
			if (newCOuntryListSize > oldCOuntryListSize) {
			
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

		countriesArmiesObserver.putAll(StartUpPhase.countriesArmies);
		initialPlayerCountryObserver.putAll(StartUpPhase.initialPlayerCountry);

	}
	sc.close();
}


	public void fortify(PlayerToPlay playerName) {
		
	}

}
