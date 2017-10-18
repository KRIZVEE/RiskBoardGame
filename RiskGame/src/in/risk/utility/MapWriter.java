package in.risk.utility;

import java.io.FileOutputStream;
import java.io.PrintStream;

public class MapWriter {
	
	public static void mapWriter(){
		try{
			PrintStream outFile = new PrintStream(new FileOutputStream("resources/newMapFile.map"));
			outFile.println("[Map]");
			for(int i = 0 ; i < RiskGame.mapDetail.size();i++){
				outFile.println(RiskGame.mapDetail.get(i));
			}	
			outFile.println(";;");
			outFile.println("[Continents]");
				for(int i=0;i<RiskGame.continentList.size();i++){
					outFile.println(RiskGame.continentList.get(i).replace(" ", "_")+"="+ContinentsCountriesMap.valueList.get(i));
				
			}			
			outFile.println(";;");
			outFile.println("[Territories]");
			for(int i =0; i < RiskGame.countryList.size();i++){
				outFile.println(RiskGame.countryList.get(i)+" "+RiskGame.xValues.get(i)+" "+RiskGame.yValues.get(i)+" "+RiskGame.continentList.get(0)+" "+RiskGame.adjacentsMap.get(RiskGame.countryList.get(i))+";");
			}
			outFile.println(";;");
			System.setOut(outFile);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
