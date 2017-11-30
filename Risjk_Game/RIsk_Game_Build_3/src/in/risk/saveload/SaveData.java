package in.risk.saveload;

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
	public Vector<PlayerToPlay> namesOfplayers;
	public HashMap<String, Integer> countriesArmies;
	public HashMap<String, ArrayList<String>> playerCountries;
	public HashMap<String, Integer> playerArmies;
	
}
