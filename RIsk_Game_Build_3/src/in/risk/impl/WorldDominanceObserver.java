package in.risk.impl;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Map.Entry;

//import com.sun.xml.internal.ws.encoding.soap.SOAP12Constants;

/**
 * This class is used to implement Observer interface and apply Observer
 * pattern on the World Dominance of a current player.
 * 
 * 
 * @author Charanpreet Singh, Ishan Kansara, Kashif Rizvee, Mohit Rana
 * @version 1.0.0
 */
public class WorldDominanceObserver implements Observer {
// current project
	int currentPlayerCountrySize = 0;
	double playerDominanceInPercent = 0;
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
		System.out.println("++++++++");
		System.out.println("::::::::World Domination::::::::");		
		System.out.println("Starting WORLD DOMINANCE OBSERVER");
		
		
		for (Entry<String, ArrayList<String>> entry : ( (StartUpPhase) o).initialPlayerCountry.entrySet()) {
			String key = entry.getKey();
			ArrayList<String> value = entry.getValue();
			double totalCountryListSize = MapLoader.countryFilter.size();
			currentPlayerCountrySize = ((StartUpPhase)o).initialPlayerCountry.get(key).size();
			playerDominanceInPercent = (currentPlayerCountrySize / totalCountryListSize) * 100;
			System.out.println(
					"Player: " + key + " " + "No of countries for players: " + ( (StartUpPhase) o).initialPlayerCountry.get(key).size());
			System.out.println("Player: " + key + " " + "World dominance in percentage: " + playerDominanceInPercent);

		}
		
		System.out.println("++++++++");
		System.out.println("::::::::World Domination::::::::");
		
		System.out.println();
		
		System.out.println("Ending WORLD DOMINANCE OBSERVER");
		return;
		
//		System.out.println(
//				"Player: " + key + " " + "No of countries for players: " + initialPlayerCountry.get(key).size());
//		System.out.println("Player: " + key + " " + "World dominance in percentage: " + playerDominanceInPercent);
		
		
	}

}
