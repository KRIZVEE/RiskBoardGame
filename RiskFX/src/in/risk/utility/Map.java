package in.risk.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Scanner;
import java.util.Vector;

public class Map {
	
	public Vector<Continent> continents = new Vector<Continent>();

	public void loadMap(){
		
		boolean done = false;
		//String next;
		//String name;
		
		//Vector<String> contains;
		
		try{

			File mapFile =  new File("Data/world.map");
			InputStream file = new FileInputStream(mapFile);			
			Scanner  scanner = new Scanner(file);
			
			while(scanner.hasNext()){				

				
				while(done == false);
				scanner.close();
			}

		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	

}
