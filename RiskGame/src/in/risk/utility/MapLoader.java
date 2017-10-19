package in.risk.utility;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;

/**
 * This class contains all the methods used to load a map file and then read its
 * all </br>
 * element to use them in editing the map file.
 * 
 * @author Charanpreet Singh, Ishan Kansara, Kashif Rizvee, Mohit Rana
 * @version 1.0.0
 */
public class MapLoader {

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

	/**
	 * This method is called to load the map.
	 * 
	 * @param pathMap
	 *            This argument is used get the path of the selected map file.
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
}