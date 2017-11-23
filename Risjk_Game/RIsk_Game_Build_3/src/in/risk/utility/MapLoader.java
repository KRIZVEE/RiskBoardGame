package in.risk.utility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Map.Entry;
/**
 * This is the main method for 
 * @author Mohit Rana, Kashif Rizvee, Ishan Kansara, Charanpreet Singh
 *
 */
public class MapLoader {

	public static ArrayList<String> continentFilterNew = new ArrayList<String>();
	public static List<String> newList;
	public static ArrayList<String> countryFilter = new ArrayList<String>();
	public static HashMap<String, List<String>> map = new HashMap<String, List<String>>();
	public static HashMap<String, List<String>> adj = new HashMap<String, List<String>>();
	public static HashMap<String, String> countryContinent = new HashMap<String, String>();
	public static HashMap<String, List<String>> continentCountries = new HashMap<String, List<String>>();
	public static HashMap<String, List<String>> countryCoordinates = new HashMap<String, List<String>>();
	public static ArrayList<String> valueList = new ArrayList<String>();
	public static HashMap<String, Integer> continentValue = new HashMap<String, Integer>();
	public static ArrayList<String> mapDetail = new ArrayList<String>();

	private static int flag;

	/**
	 * Method for calling the LoadMap class at once.
	 * @param path for the path fo the map.
	 */
	public static void loadMap(String path){
		loadContinent(path);
		loadCountries(path);
		removeSpcaes();
		getCountriesAdjacency();
		getContinentCountries();
		emptyContinent();
	}

	/**
	 * Method for finding out the number of continents the map has.
	 * @param mapPath for the path of the map want to use
	 * @return true if method is able to find the continents correctly
	 */
	public static boolean loadContinent(String mapPath){
		try {
			String path = "resources/maps/" + mapPath;
			FileInputStream file = new FileInputStream(path);
			String next = null;
			Scanner mapfile = new Scanner(file);
			while (mapfile.hasNextLine()) {
				next = mapfile.nextLine();
				
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
						continentValue.put(parts[0], Integer.parseInt(parts[1]));
						valueList.add(parts[1]);
						next = mapfile.nextLine();
					} while (!next.equals(""));
				}
			}
			mapfile.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Method for loading the countries of the map.
	 * @param mapPath for passing the path of the map to be used.
	 * @return trues if all done correctly.
	 */
	public static boolean loadCountries(String mapPath){
		try {
			String path = "resources/maps/" + mapPath;
			FileInputStream file = new FileInputStream(path);

			//			boolean done = false;
			String next = null;

			Scanner mapfile = new Scanner(file);

			while (mapfile.hasNextLine()) {
				next = mapfile.nextLine();
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
			}
			mapfile.close();
		}catch (Exception e) {
		}
		return true;
	} 

	/**
	 * This method is used to get the countries adjacency.
	 * @return true if all goes weel.
	 */
	public static boolean getCountriesAdjacency(){

		if (map.containsKey("")) {
			map.remove("");
		}

		for (Entry<String, List<String>> entry : map.entrySet()) {
			String key = entry.getKey();
			List<String> value = entry.getValue();
			List<String> tempList = new ArrayList<String>();
			tempList.add(value.get(1).toString());
			tempList.add(value.get(2).toString());
			countryCoordinates.put(key, tempList);

			List<String> subList = value.subList(4, value.size());
			String continentName = value.get(3).toString();
			adj.put(key, subList);
			countryContinent.put(key, continentName);
		}
		return true;
	}

	/**
	 * This method is used to get the countries of the continent.
	 * @return true if all goes well.
	 */
	public static boolean getContinentCountries(){
		for (Entry<String, String> entry : countryContinent.entrySet()) {
			String Key = entry.getKey();
			String value = entry.getValue();
			fun(value, Key);
		} 
		return true;
	}

	/**
	 * this method is used to iterate over the hashmap to obtain particular
	 * values
	 * and then use them to make another hasmap with same key.
	 * 
	 * @param key for returning the Key of the hashamp
	 * @param value for returning the values of the hashamp.
	 * @return return the values.
	 */
	public static HashMap<String, List<String>> fun(String key, String value) {

		if (continentCountries.containsKey(key)) {
			List<String> values = continentCountries.get(key);
			values.add(value);
		} else {
			List<String> values = new ArrayList<String>();
			values.add(value);
			continentCountries.put(key, values);
		}
		return continentCountries;
	}

	/**
	 * This method is used to stop loading the map if the map has empty continents.
	 * @return true is all goes well.
	 */
	public static boolean emptyContinent(){

		if(continentFilterNew.size() != continentCountries.size()){
			return false;
		}
		return true;
	}

	/**
	 * This method is used to stop loading the map if the map does'nt have the connected continents.
	 * @return true if everything goes well.
	 */
	
public static boolean unconnectedContinent(){
		HashMap<String, String> temp = new HashMap<String, String>();
		ArrayList<String> flags = new ArrayList<String>();
		adj.forEach((k,v)->{			
			if(!Collections.disjoint(continentCountries.get(countryContinent.get(k)), v)){
				temp.put(k, "V");
			}else{
				temp.put(k, "I");
			}			
		});
		temp.forEach((key,value)->{
			flags.add(value);
		});
		if(flags.contains("I")){
			return false;
		}else{
			return true;
		}		
	}
	
	public static boolean connectedContinent(){

		HashMap<String, String> temp = new HashMap<String, String>();
		adj.forEach((k,v)->{
			if(continentCountries.get(countryContinent.get(k)).containsAll(v)){	
				temp.put(k, "Invalid");
			}
			else {
				temp.put(k, "Valid");
			}
		});
		List<String> checkFinal = new ArrayList<String>();
		continentCountries.forEach((key,value) ->{
			List<String> temp1 = new ArrayList<String>();
			for(int i = 0; i < value.size(); i++){
				String value1 = temp.get(value.get(i));
				temp1.add(value1);
			}
			if(temp1.contains("Valid")){				
				checkFinal.add("valid");
			}else{				
				checkFinal.add("Invalid");
			}
		});

		if(checkFinal.contains("Invalid")){
			return false;
		}else{
			return true;
		}

	}

	/**
	 * This method is used to remove spaces from the country continent hashmap.
	 * @return true if everything goes well
	 */
	public static boolean removeSpcaes(){
		for (int i = countryFilter.size(); i > 0; i--) {
			if (countryFilter.contains("")) {
				countryFilter.remove("");
			}
		}
		return true;
	}
	
	/**
	 * This method is used to stop loading map if map does not contains the connected countries.
	 * @return true if everything goes well.
	 */
	public static boolean connectedCountry(){

		adj.forEach((key,value)->{
			if(value.isEmpty()){				
				flag= 1;
			}
		});	
		if(flag==1){
			return false;
		}else{
			return true;
		}		
	}
}