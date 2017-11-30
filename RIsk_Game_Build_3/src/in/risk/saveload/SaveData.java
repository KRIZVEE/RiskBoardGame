package in.risk.saveload;

import java.util.List;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import in.risk.impl.PlayerToPlay;

public class SaveData implements Serializable {

	private static final long serialVersionUID = 1L;
	
	//data structures for storing the game data on save command
	public  PlayerToPlay currentPlayer;
	public int noOfPlayers;
	public Vector<PlayerToPlay> players;
	public HashMap<String, Integer> countriesArmies;
	public HashMap<String, ArrayList<String>> playerCountries;
	public ArrayList<String> countryFilter;
	public  ArrayList<String> continentFilterNew;
	
	public  HashMap<String, List<String>> adjacent;
	public  HashMap<String, String> countryContinent;
	public  HashMap<String, List<String>> continentCountries;
	public  HashMap<String, List<String>> countryCoordinates;
	public  ArrayList<String> valueList;
	public  HashMap<String, Integer> continentValue;
	
	public String nextMethod;

}
