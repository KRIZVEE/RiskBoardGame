package in.risk.utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Map.Entry;

import com.sun.javafx.PlatformUtil;


/**
 * This class implements the Reinforcement, Attack and Fortify game phases.
 * @author Mohit Rana, Kashif Rizvee, Ishan Kansara, Charanpreet Singh
 *
 */
public class Player extends StartUpPhase {

	public static HashMap<String, List<String>> playersCards = new HashMap<String, List<String>>();
	public static List<String> cardsInTheDeck = new ArrayList<String>();
	public static ArrayList<String> deck = new ArrayList<String>();
	public static ArrayList<String> cardType = new ArrayList<String>();
	public static int cardFlag = 1;

	public static String cardTypeA = "A";
	public static String cardTypeB = "B";
	public static String cardTypeC = "C";
	public static int cardInTheDeck;

	/**
	 * This method is used to get reinforcement armies from the countries player own.
	 * @param playerName used to specify the name of the current player
	 * @return true is all goes well.
	 */
	public static int getArmiesFromCountries(String playerName){
		int noOfReinforcementArmiesForCountry = 0;
		if(initialPlayerCountry.get(playerName).size() < 3){
			noOfReinforcementArmiesForCountry = noOfReinforcementArmiesForCountry + 3;
		}else{
			noOfReinforcementArmiesForCountry = noOfReinforcementArmiesForCountry + initialPlayerCountry.get(playerName).size()/3;
		}
		System.out.println(playerName + " got " + noOfReinforcementArmiesForCountry + " armies from owned countries.");
		return noOfReinforcementArmiesForCountry;
	}

	/**
	 * This method is used to get reinforcement armies from owned continents.
	 * @param playerName used to specify the name of current player.
	 * @return true if everything goes well.
	 */
	public static int getArmiesaFromContinet(String playerName){
		int noOfReinforcementArmiesForContinent = 0;
		ArrayList<Boolean> resultOfContinentCountry = new ArrayList<Boolean>();
		for (Entry<String, List<String>> entry : LoadMap.continentCountries.entrySet()) {
			String Key = entry.getKey();
			List<String> value = entry.getValue();
			for (int i = 0; i < value.size(); i++) {
				resultOfContinentCountry.add(initialPlayerCountry.get(playerName).contains(value.get(i)));
			}
			if (resultOfContinentCountry.contains(false)) {
				noOfReinforcementArmiesForContinent = noOfReinforcementArmiesForContinent + 0;
			} else if (resultOfContinentCountry.contains(true)) {
				noOfReinforcementArmiesForContinent = noOfReinforcementArmiesForContinent + LoadMap.continentValue.get(Key);
			}
			resultOfContinentCountry.clear();
		}
		System.out.println(playerName + " got " + noOfReinforcementArmiesForContinent + " from owned continents.");
		return noOfReinforcementArmiesForContinent;
	}

	/**
	 * This method is about trading of cards and getting armies in return.
	 * Armies added will be as per the game logic.
	 */
	public static int getArmiesFromCards(String playerName) throws IOException {
		int noOfReinforcementArmiesForCards = 0;
		initialCardDistribution();
		int tempSize = playersCards.get(playerName).size();
		if (tempSize >= 5) {
			noOfReinforcementArmiesForCards = checkUniqueCombination(tempSize, playerName) + checkDiscreteCombination(playerName);
		} else {
			if(tempSize < 3){
				System.out.println("You dont have enough cards to play.");
			}else{
				String result;
				Scanner sc = new Scanner(System.in);
				System.out.println("Now you have less than 5 cards. Do you want to play cards now? Y/N");
				result = sc.nextLine();
				if (result.equals("Y")) {
					noOfReinforcementArmiesForCards = checkUniqueCombination(playersCards.get(playerName).size(), playerName) + checkDiscreteCombination(playerName);
				}
				sc.close();
			}
		}
		System.out.println(playerName + " got " + noOfReinforcementArmiesForCards + " reinforcement armies from trading the cards.");
		return noOfReinforcementArmiesForCards;
	}
	
	/**
	 * This method is used to distribute catds in the deck intially.
	 * @throws IOException exception
	 */
	public static void initialCardDistribution() throws IOException {
		int size = StartUpPhase.players.size();
		for (int i = 0; i < size; i++) {
			playersCards.put(StartUpPhase.players.get(i).getName(), deck.subList(0, 0));
		}
	}

	/**
	 * This method is used to place cards in the deck of player intially.
	 * @return the deck
	 * @throws IOException exception.
	 */
	public static List<String> placeCardsInTheDeck() throws IOException {
		cardType.add(cardTypeA);
		cardType.add(cardTypeB);
		cardType.add(cardTypeC);
		int j = 0;
		cardInTheDeck = LoadMap.countryFilter.size();
		for (int i = 0; i < cardInTheDeck; i++) {
			cardsInTheDeck.add(cardType.get(j));
			j++;
			if (j == 3) {
				j = 0;
			}
		}
		return cardsInTheDeck;
	}

	/**
	 * This method is used to check the combination of players cards and then give reinforcemtn armies.
	 * @param playerName name of the current player.
	 * @return reinforcement armies.
	 */
	public static int checkDiscreteCombination(String playerName) {
		int noOfReinforcementArmiesForDiscrete = 0;
		List<Integer> tempListABCFirst = new ArrayList<Integer>();
		List<Integer> tempListABCSecond = new ArrayList<Integer>();
		int flagForA = 0;
		int flagForB = 0;
		int flagForC = 0;
		String value;
		for (int i = 0; i < playersCards.get(playerName).size(); i++) {
			value = playersCards.get(playerName).get(i);
			if (value.equals("A")) {
				if (flagForA <= 1) {
					if (flagForA == 0) {
						tempListABCFirst.add(i);
					} else if (flagForA == 1) {
						tempListABCSecond.add(i);
					}
					flagForA += 1;
				}
			} else if (value.equals("B")) {
				if (flagForB <= 1) {
					if (flagForB == 0) {
						tempListABCFirst.add(i);
					} else if (flagForB == 1) {
						tempListABCSecond.add(i);
					}
					flagForB += 1;
				}

			} else if (value.equals("C")) {
				if (flagForC <= 1) {
					if (flagForC == 0) {
						tempListABCFirst.add(i);
					} else if (flagForC == 1) {
						tempListABCSecond.add(i);
					}
					flagForC += 1;
				}

			}
		}
		if (tempListABCFirst.size() >= 3) {
			int firstIndexToRemove = tempListABCFirst.get(0);
			int secondIndexToRemove = tempListABCFirst.get(1);
			int thirdIndexToRemove = tempListABCFirst.get(2);
			cardsInTheDeck.add(playersCards.get(playerName).get(firstIndexToRemove));
			playersCards.get(playerName).remove(firstIndexToRemove);
			cardsInTheDeck.add(playersCards.get(playerName).get(secondIndexToRemove - 1));
			playersCards.get(playerName).remove(secondIndexToRemove - 1);
			cardsInTheDeck.add(playersCards.get(playerName).get(thirdIndexToRemove - 2));
			playersCards.get(playerName).remove(thirdIndexToRemove - 2);
			noOfReinforcementArmiesForDiscrete += 5 * cardFlag;
			cardFlag += 1;
		}
		if (tempListABCSecond.size() >= 3) {
			int fourthIndexToRemove = tempListABCSecond.get(0);
			int fifthIndexToRemove = tempListABCSecond.get(1);
			int sixthINdexToRemove = tempListABCSecond.get(2);
			cardsInTheDeck.add(playersCards.get(playerName).get(fourthIndexToRemove - 3));
			playersCards.get(playerName).remove(fourthIndexToRemove - 3);
			cardsInTheDeck.add(playersCards.get(playerName).get(fifthIndexToRemove - 4));
			playersCards.get(playerName).remove(fifthIndexToRemove - 4);
			cardsInTheDeck.add(playersCards.get(playerName).get(sixthINdexToRemove - 5));
			playersCards.get(playerName).remove(sixthINdexToRemove - 5);
			noOfReinforcementArmiesForDiscrete += 5 * cardFlag;
			cardFlag += 1;
		}
		return noOfReinforcementArmiesForDiscrete;
	}
	
	/**
	 * This method provide number of reinforcement armies on the basis of unique card combination.
	 * @param tempSize no of cards player have
	 * @param playerName name of the player 
	 * @return no of reinforcement armies
	 * @throws IOException exception.
	 */
	public static int checkUniqueCombination(int tempSize, String playerName) throws IOException {
		int noOfReinforcementArmiesForUnique = 0;
		List<Integer> tempListA = new ArrayList<Integer>();
		List<Integer> tempListB = new ArrayList<Integer>();
		List<Integer> tempListC = new ArrayList<Integer>();
		for (int i = 0; i < tempSize; i++) {
			if (playersCards.get(playerName).get(i).contains("A")) {
				tempListA.add(i);
			}
		}
		for (int i = 0; i < tempSize; i++) {
			if (playersCards.get(playerName).get(i).contains("B")) {
				tempListB.add(i);
			}
		}
		for (int i = 0; i < tempSize; i++) {
			if (playersCards.get(playerName).get(i).contains("C")) {
				tempListC.add(i);
			}
		}
		if (tempListA.size() >= 3) {
			int firstIndexToRemove = tempListA.get(0);
			int secondIndexToRemove = tempListA.get(1);
			int thirdIndexToRemove = tempListA.get(2);
			cardsInTheDeck.add("A");
			cardsInTheDeck.add("A");
			cardsInTheDeck.add("A");
			playersCards.get(playerName).remove(firstIndexToRemove);
			playersCards.get(playerName).remove(secondIndexToRemove - 1);
			playersCards.get(playerName).remove(thirdIndexToRemove - 2);
			noOfReinforcementArmiesForUnique = noOfReinforcementArmiesForUnique + 5 * cardFlag;
			cardFlag += 1;
			tempListA.clear();
		} else if (tempListB.size() >= 3) {
			int firstIndexToRemove = tempListB.get(0);
			int secondIndexToRemove = tempListB.get(1);
			int thirdIndexToRemove = tempListB.get(2);
			cardsInTheDeck.add("B");
			cardsInTheDeck.add("B");
			cardsInTheDeck.add("B");
			playersCards.get(playerName).remove(firstIndexToRemove);
			playersCards.get(playerName).remove(secondIndexToRemove - 1);
			playersCards.get(playerName).remove(thirdIndexToRemove - 2);
			noOfReinforcementArmiesForUnique = noOfReinforcementArmiesForUnique + 5 * cardFlag;
			cardFlag += 1;
			tempListB.clear();
		} else if (tempListC.size() >= 3) {
			int firstIndexToRemove = tempListC.get(0);
			int secondIndexToRemove = tempListC.get(1);
			int thirdIndexToRemove = tempListC.get(2);
			cardsInTheDeck.add("C");
			cardsInTheDeck.add("C");
			cardsInTheDeck.add("C");
			playersCards.get(playerName).remove(firstIndexToRemove);
			playersCards.get(playerName).remove(secondIndexToRemove - 1);
			playersCards.get(playerName).remove(thirdIndexToRemove - 2);
			noOfReinforcementArmiesForUnique = noOfReinforcementArmiesForUnique + 5 * cardFlag;
			cardFlag += 1;
		}
		return noOfReinforcementArmiesForUnique;
	}
}
