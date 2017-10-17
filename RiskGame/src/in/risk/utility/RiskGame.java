package in.risk.utility;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

import org.hamcrest.core.CombinableMatcher.CombinableBothMatcher;

import in.risk.utility.*;

public class RiskGame {
	
	//paths
	
	public static String css = "file:///E:/Git/RiskGame/src/in/risk/gui/application.css";
    public static String logoPath = "file:///E:/Git/RiskGame/resources/Risk_logo.png";
    public static String mapPath = "resources/world.map";
	
	//Game states
	
	public static final int NEW_GAME = 0;
	public static final int INITIAL_REINFORCEMENT = 1;
	public static final int ACTIVE_TURN = 2;
	public static final int TURN_BONUS = 3;
	public static final int REINFORCE = 4;
	public static final int TRADE_CARDS = 5;
	public static final int START_TURN = 6;
	public static final int FORTIFY = 7;
	public static final int FORTIFYING = 8;
	public static final int FORTIFY_PHASE  = 9;
	
	
	public static final int GAME_OVER = 99;
	
	@SuppressWarnings("unused")
	static private int game_state;
	
	static public Vector<Territory> territories = new Vector<Territory>();
	static public Vector<Player> players  = new Vector<Player>();
	public Vector<Card> deck = new Vector<Card>();
	public Vector<Continent> continents = new Vector<Continent>();
	
	public ArrayList<String> continentList = new ArrayList<String>();
	public ArrayList<String> countryList = new ArrayList<String>();

<<<<<<< HEAD
	public HashMap<String, ArrayList<String>> continentsMap;
	
=======
	public HashMap<String, List<String>> continentsMap = new HashMap<String,List<String>>();
>>>>>>> 1298676d3ed1b6bc00dde1aec30f7bf305cacb02
	public HashMap<String, List<String>> adjacentsMap = new HashMap<String, List<String>>();

	public ArrayList<String> initialCountries = new ArrayList<String>();
	
	public Vector<String> adjacents;
	public HashMap<String, List<String>> adj = new HashMap<String, List<String>>();
<<<<<<< HEAD
	public HashMap<Player, ArrayList<String>> initialPlayerCountry = new HashMap<Player, ArrayList<String>>();
	

=======
	public HashMap<String, ArrayList<String>> initialPlayerCountry = new HashMap<String, ArrayList<String>>();
	
>>>>>>> 1298676d3ed1b6bc00dde1aec30f7bf305cacb02
	public Player currentPlayer;
	public Player active;
	Territory t;
	
	public int iter = 0;
	public boolean drawn;
	
	public RiskGame(){
		
		game_state = NEW_GAME;
		addPlayer("Preet");
		addPlayer("Mohit");
		addPlayer("Ishan");
		
		loadMap();
		initialPlayer();
		initialPlayerCountries();
		distributeArmies();
		placeArmies();
		
	}
	
	public static boolean addPlayer(String nm){
		int size = players.size();
		if(size>6){
			return false;
		}
		Player p = new Player(nm, size);
		players.add(p);
		return true;
	}
	 
	public void initialPlayer(){
		currentPlayer = players.elementAt(0);
	}
	
	public void distributeArmies(){
		int numberOfPlayers = players.size();
		int armies = 0;
		
		if(numberOfPlayers == 2)
			armies = 40;
		if(numberOfPlayers == 3)
			armies = 35;
		if(numberOfPlayers == 4)
			armies = 30;
		if(numberOfPlayers == 5)
			armies = 25;
		if(numberOfPlayers == 6)
			armies = 20;
		for(int i = 0;i <numberOfPlayers;i++){
			players.elementAt(i).addArmies(armies);
			//System.out.println(players.elementAt(i).getName() + "+" + players.elementAt(i).getArmies());
		}
	}
	
	public void nextPlayer(){
        if (currentPlayer == players.lastElement()){
            currentPlayer = players.elementAt(0);
             iter = 0;
        }
        else
            currentPlayer = players.elementAt(++iter);
    }
	
	public void initialPlayerCountries(){
		int i = 0;
		int j = countryList.size()-1;
		while (j >= 0 ) {
			initialCountries.add(countryList.get(j));
			players.get(i);

			ArrayList<String> list;
			if(initialPlayerCountry.containsKey(players.get(i).getName())) {
				list = initialPlayerCountry.get(players.get(i).getName());
				list.add(countryList.get(j));
			}
			else {
				list = new ArrayList<String>();
				list.add(countryList.get(j));
				initialPlayerCountry.put(players.get(i).getName(), list);
			}
			i++; j--;
			if(i == 3) {
				i = 0;
			}
		}
//		System.out.println("Ishna = " + initialPlayerCountry.get("Ishan") + "\n");
//		System.out.println("Preet = " +initialPlayerCountry.get("Preet") + "\n");
//		System.out.println("Mohit = " +initialPlayerCountry.get("Mohit"));
//		System.out.println(initialPlayerCountry.get("Ishan").size());
//		System.out.println(initialPlayerCountry.get("Preet").size());
//		System.out.println(initialPlayerCountry.get("Mohit").size());
//		System.out.println(initialPlayerCountry + "\n");
	}
	
	public void placeArmies() {
		if(currentPlayer.getNumberOfArmies() > 0) {
			for (int i = 0; i < countryList.size(); i++) {
				if(initialPlayerCountry.get(currentPlayer.getName()).contains(countryList.get(i))) {
					 t = new Territory(countryList.get(i));
					t.setPlayer(currentPlayer);
					t.addArmy();
					currentPlayer.looseArmy();
					//System.out.println(t.getNameTerritory() + " " + t.getArmies());
				}else {
					nextPlayer();
					i--;
				}
			}
//			System.out.println(players.elementAt(0).getName() + "+" + players.elementAt(0).getArmies());
//			System.out.println(players.elementAt(1).getName() + "+" + players.elementAt(1).getArmies());
//			System.out.println(players.elementAt(2).getName() + "+" + players.elementAt(2).getArmies());
			System.out.println(t.getNameTerritory()+ " " + t.getArmies());
		}
	}
	public void initializeDeck(){
		for(int i=0;i < territories.size();i++){
			deck.add(new Card(i%3, i));
		}
	}

	public void loadMap(){
		
		boolean done = false;
		String next;
		String name;
		int value;
		String continent;
		int x;
		int y;		
		
		try{
		File file = new File(mapPath);
		Scanner scanner = new Scanner(file);
		
		while(scanner.hasNextLine()){
			done = false;
			next = scanner.nextLine();
			
			if(next.equals("[Continents]")){
				next = scanner.next();
			
				do{
					String c[] = next.split("=");
					name = c[0].replace("_", " ");
					value = Integer.parseInt(c[1]);
					continentList.add(name);
					continents.add(new Continent(name, value));
					next = scanner.next();
					if(next.equals(";;")) done = true;	
			}while(done == false);
			}
			
			if(next.equals("[Territories]")){
				next = scanner.next();
				
				do{
					name = next.replace("_", " ");
					x = Integer.parseInt(scanner.next());
					y = Integer.parseInt(scanner.next());
					countryList.add(name);
					continent =scanner.next().replace("_", " ");
					adjacents = new Vector<String>();
					next = scanner.next();
					while(!next.equals(";")){
						adjacents.add(next);
						adjacentsMap.put(name, adjacents);
						next = scanner.next();
					}
//					territories.add(new Territory(name, x, y, continent));
					next = scanner.next();
					if(next.equals(";;")) done = true;
				}while(done == false);
			}
		}
		continentsMap = new HashMap<String,ArrayList<String>>(ContinentsCountriesMap.includingMap(mapPath));
		scanner.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
