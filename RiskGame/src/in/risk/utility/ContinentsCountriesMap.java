package in.risk.utility;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class ContinentsCountriesMap {
	
	public static HashMap<String, List<String>> includingMap(String path){
		
		boolean done = false;
		String next;
		int value;
		List<Integer> valueList = new ArrayList<Integer>();
		List<String> continentNameList = new ArrayList<String>();
		List<String> countryNamelist = new ArrayList<String>();
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
						countryName = line[0];
						countryNamelist.add(countryName);
						next = scanner.nextLine();
						if(next.equals(";;")) done = true;
					}while(done == false);
				}countryNamelist.removeIf(String::isEmpty);
			}
			scanner.close();
			
			System.out.println(countryNamelist);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return null;
	}
 }
