package Map;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

//import Risk.Continent;
//import Risk.Territory;

public class MapEditor {
	@SuppressWarnings("unused")
	public static void main(String args[]) {
		try {
			FileInputStream fin = new FileInputStream("Data/world.map");
			int i = 0;
			// String key = "";
			/*
			 * while ((i = fin.read()) != -1) { // key += i;
			 * System.out.print((char) i); // System.out.print(key); }
			 */

			@SuppressWarnings("resource")
			Scanner mapfile = new Scanner(fin);
			boolean done = false;
			
			ArrayList<String> continentFilter = new ArrayList<String>();
			
			ArrayList<String> countryFilter = new ArrayList<String>();
			
			ArrayList<String> adjacencyFilter = new ArrayList<String>();
			
			List<String> newList;
			List<String> newList1;
			
			Map<String, List<String>> map = new HashMap<String, List<String>>();
			
			String[] arr1 = new String[500];

			String next = null;
			String s = "=";
			char ch;
			int l = 0;

			while (mapfile.hasNextLine()) {
				done = false;
				next = mapfile.nextLine();
				String[] arr;
				if (next.equals("[Continents]")) {
					next = mapfile.nextLine();
					do {
						String[] parts = next.split("=");
						String part1 = parts[0];
						continentFilter.add(part1);
						//System.out.println(part1);
						next = mapfile.nextLine();

					} while (!next.equals(""));
					
					int totalNumbeOfContinents = continentFilter.size();
					
					System.out.println("Continent in Array List : " + continentFilter + "\n");
				} // end of continent

				if (next.equals("[Territories]")) {
					next = mapfile.nextLine();
					do {
						String[] parts = next.split(",");
						newList = Arrays.asList(parts);
						// if (parts[0].equals(countryFilter.get(l))) {
						// System.out.println("our new list is : " + newList);
						map.put(newList.get(1), newList);
						
						
						//adjacencyFilter.addAll(newList);
						String part1 = parts[0];
						countryFilter.add(part1);
					
						List<String> l1 = newList.subList(4, newList.size());
						System.out.println(part1);
						System.out.println(l1 + "\n");
						
						
						//System.out.println(part1);
						next = mapfile.nextLine();
					} while (!next.equals(";;"));
					//System.out.println("Countries in Array List : " + countryFilter + "\n");
					
					// System.out.println("our latest adjacency list is: " +
					// adjacencyFilter.get(5));

					/*System.out.println("Adjacency Countries in Array List : ");
					System.out.println("::::::::::::::::::starts::::::::::::::::");
					System.out.println(map.size());
					System.out.println("Fetching Keys and corresponding [Multiple] Values n");
					for (Map.Entry<String, List<String>> entry : map.entrySet()) {
						String key = entry.getKey();
						List<String> values = entry.getValue();
						
						
						
						System.out.println("Key = " + key);
						
						System.out.println("Values = " + values + "\n");
					}
					//System.out.println(map.values());
					if(map.containsKey("")){
						map.remove("");
					}*/
					
					System.out.println("::::::::::::::::::ends::::::::::::::::");

				}

				fin.close();
			}
		} catch (

		Exception e) {
			System.out.println(e);
		}
	}
}