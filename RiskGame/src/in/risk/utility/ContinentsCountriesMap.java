package in.risk.utility;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ContinentsCountriesMap {
	
	public static ArrayList<Integer> valueList = new ArrayList<Integer>();
	public static ArrayList<String> continentNameList = new ArrayList<String>();
	public static ArrayList<String> countryNamelist = new ArrayList<String>();
	
	public static ArrayList<ArrayList<String>> listOfCountries = new ArrayList<ArrayList<String>>();
	public static HashMap<String, ArrayList<String>> returnMap = new HashMap<String,ArrayList<String>>();
	
	public static HashMap<String, ArrayList<String>> includingMap(String path){
		
		boolean done = false;
		String next;
		int value;
		
		String continentName ;
		String countryName;
		
		
		try{
			File file = new File(path);
			Scanner scanner = new Scanner(file);
			
			while(scanner.hasNextLine()){
				done = false;
				next = scanner.nextLine();
				
				if(next.equals("[Continents]")){
					next = scanner.next();
					
					do{
						String[] lineArray = next.split("=");
						continentName = lineArray[0].replace("_", " ");
						continentNameList.add(continentName);
						value = Integer.parseInt(lineArray[1]);
						valueList.add(value);
						next = scanner.next();
						if(next.equals(";;")) done = true;
					}while(done == false);
				}
				
				if(next.equals("[Territories]")){
					next = scanner.nextLine();
					do{
						String[] line = next.split(" ");
						countryName = line[0].replaceAll("_", " ");
						countryNamelist.add(countryName);
						next = scanner.nextLine();
						if(next.equals(";;")) done = true;
					}while(done == false);
				}countryNamelist.removeIf(String::isEmpty);
			}
			scanner.close();	
			ArrayList<String> out;
			
			for(int i=0; i<valueList.size();i++){
					out = new ArrayList<String>(countryNamelist.subList(0, valueList.get(i)));
					if(!countryNamelist.isEmpty()){
						for(int k=0; k < out.size();k++){
							countryNamelist.remove(0);
						}	
					}									
					listOfCountries.add(out);
			}	
			for(int i=0;i< continentNameList.size();i++){
				returnMap.put(continentNameList.get(i), listOfCountries.get(i));
			}
		}catch (Exception e) {
			e.printStackTrace();
		}		
		return returnMap;
	}
 }
