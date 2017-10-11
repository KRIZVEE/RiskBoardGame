package in.risk.gui;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MapLoad {
    public static void main(String args[]) {
   
        try{
        	@SuppressWarnings("resource")
			BufferedReader in = new BufferedReader(new FileReader("Data/world.map"));
        	
        	String line = null;
        	
        	while((line = in.readLine()) != null){
        		if(line.contains("Continent"))
        		{
        			System.out.println(in.readLine());
        		}
        		
        	}
        		
        }catch(IOException e){
        	e.printStackTrace();
        }
    }
}