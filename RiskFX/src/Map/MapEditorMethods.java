package Map;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MapEditorMethods {
	ArrayList<String> continentFilter = new ArrayList<String>();			
	ArrayList<String> countryFilter = new ArrayList<String>();
	List<String> newList;
	List<String> l1;
	String part1;
	Map<String, List<String>> map = new HashMap<String, List<String>>();
	Map<String, String> continentoftheCountry = new HashMap<String ,String>();
	String next = null;
	String continent = null;
	
	@SuppressWarnings("resource")
	public void LoadingMap(String location){
		try{
			FileInputStream fn = new FileInputStream(location);
			
			Scanner mapFile = new Scanner(fn);
			/*ArrayList<String> continentFilter = new ArrayList<String>();			
			ArrayList<String> countryFilter = new ArrayList<String>();
			List<String> newList;
			Map<String, List<String>> map = new HashMap<String, List<String>>();
			
			String next = null;*/
			while(mapFile.hasNextLine()){
				next = mapFile.nextLine();
				if (next.equals("[Continents]")) {
					next = mapFile.nextLine();
					do{
						String[] parts = next.split("=");
						String part1 = parts[0];
						continentFilter.add(part1);
						next = mapFile.nextLine();
					}while(!next.equals(""));
					//System.out.println("Continent in Array List : " + continentFilter + "\n");
				}
				if (next.equals("[Territories]")){
					next = mapFile.nextLine();
					do{
						String[] parts = next.split(",");
						newList = Arrays.asList(parts);
						String part1 = parts[0];
						countryFilter.add(part1);
						List<String> l1 = newList.subList(4, newList.size());
						continent = newList.get(3);
						//System.out.println(part1);
						//System.out.println(l1 + "\n");
						map.put(part1, l1);
						continentoftheCountry.put(part1, continent);
						next = mapFile.nextLine();
					}while(!next.equals(";;"));
				}
				fn.close();
				
			}
		}catch (Exception e){
			System.out.println(e);
		}
		
	}
	public void delteContinent(String ContinentName){
		/*int indexOfContinent = continentFilter.indexOf(ContinentName);
		continentFilter.remove(indexOfContinent);
		System.out.println(continentFilter);
		System.out.println(countryFilter);
		System.out.println(map.get("Alberta"));
		System.out.println(continentoftheCountry.get("Alberta"));
		System.out.println(continent);*/
	}
	public void deleteCountry(String countryName){
	}
	public void addContinent(String coninentName){
	}
	public void addCountry(String countryName){
	}
}
