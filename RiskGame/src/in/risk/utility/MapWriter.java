package in.risk.utility;

import java.io.FileOutputStream;
import java.io.PrintStream;

import in.risk.gui.MapEditorInterface;

/**
 * This class contains all the methods related to editing the map and writing
 * the mapfile.
 * 
 * @author Charanpreet Singh, Ishan Kansara, Kashif Rizvee, Mohit Rana
 * @version 1.0.0
 */
public class MapWriter {
	/**
	 * This method is called in mapEditorInterface for writing the new map file.
	 */
	public static void mapWriter(String path) {
		try {
			String pathName = "resources/maps/" + path;
			PrintStream outFile = new PrintStream(new FileOutputStream(pathName));
			outFile.println("[Map]");
			for (int i = 0; i < MapLoader.mapDetail.size(); i++) {
				outFile.println(MapLoader.mapDetail.get(i));
			}
			outFile.println("");
			outFile.println("[Continents]");
			if (MapEditorInterface.editedContinents.isEmpty()) {
				for (int i = 0; i < MapLoader.continentFilterNew.size(); i++) {
					outFile.println(MapLoader.continentFilterNew.get(i).replace(" ", "_") + "="
							+ (long) Math.floor(Math.random() * 10));
				}
			}
			for (int i = 0; i < MapEditorInterface.editedContinents.size(); i++) {
				outFile.println(MapEditorInterface.editedContinents.get(i).replace(" ", "_") + "="
						+ (long) Math.floor(Math.random() * 10));

			}

			outFile.println("");
			outFile.println("[Territories]");
			if (MapEditorInterface.editedCountries.isEmpty()) {
				for (int i = 0; i < MapLoader.countryFilter.size(); i++) {
					String s = MapLoader.adj.get(MapLoader.countryFilter.get(i)).toString();
					String t = s.substring(1, s.length() - 1);
					String z = t.replace(", ", ",");

					outFile.println(MapLoader.countryFilter.get(i) + ","
							+ MapLoader.countryCoordinates.get(MapLoader.countryFilter.get(i)).get(0) + ","
							+ MapLoader.countryCoordinates.get(MapLoader.countryFilter.get(i)).get(1) + ","
							+ MapLoader.countryContinent.get(MapLoader.countryFilter.get(i)) + "," + z);
				}
			} else {
				for (int i = 0; i < MapEditorInterface.editedCountries.size(); i++) {
					String s = MapEditorInterface.editedAdj.get(MapEditorInterface.editedCountries.get(i)).toString();
					String t = s.substring(1, s.length() - 1);
					String z = t.replace(", ", ",");

					outFile.println(MapEditorInterface.editedCountries.get(i) + ","
							+ MapEditorInterface.editedCountryCoordinates.get(MapEditorInterface.editedCountries.get(i))
									.get(0)
							+ ","
							+ MapEditorInterface.editedCountryCoordinates.get(MapEditorInterface.editedCountries.get(i))
									.get(1)
							+ ","
							+ MapEditorInterface.editedCountryContinents.get(MapEditorInterface.editedCountries.get(i))
							+ "," + z);
				}

			}
			outFile.println(" ");
			System.setOut(outFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}