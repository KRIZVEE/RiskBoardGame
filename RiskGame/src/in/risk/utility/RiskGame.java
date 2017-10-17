package in.risk.utility;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

public class RiskGame {
	
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
	public HashMap<String, List<String>> continentsMap = new HashMap<String,List<String>>();

	public Vector<String> adjacents;
	public HashMap<String, List<String>> adjacentsMap = new HashMap<String, List<String>>();
	public Player currentPlayer;
	public Player active;
	
	public int iter = 0;
	public boolean drawn;
	
	public RiskGame(){
		
		game_state = NEW_GAME;
		loadMap();
		
	}
	
	static public boolean addPlayer(String nm){
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
	
	public void nextPlayer(){
		if(currentPlayer == players.lastElement()){
			currentPlayer = players.elementAt(0);
			iter = 0;
		}else 
			currentPlayer = players.elementAt(++iter);
	}
	
	public void removePlayer(Player p){
		players.remove(p);
		players.trimToSize();
		iter--;
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
		}
	}
	
	public void initializeDeck(){
		for(int i=0;i < territories.size();i++){
			deck.add(new Card(i%3, i));
		}
	}
	
	public void drawCard(Player p){
		Random draw = new Random();
		int card = draw.nextInt(deck.size());
		
		Card c = deck.elementAt(card);
		deck.remove(deck.elementAt(card));
		deck.trimToSize();
		p.setCards(c);
		
	}
	
	public int collectReinforcements(){
		
		int territoryAmount = currentPlayer.getNumberOfTerritories();
		double bonus;
		if(territoryAmount < 9)
			bonus = 9;
		else
			bonus = Math.floor(territoryAmount/3);
		return (int)bonus;
	}
	
	/*public int collectReinforcementsFromContinents(){
		
		int continentBonus = 0;
		int numberOfContinents = continents.size();
		
		for(int i =0 ;i <numberOfContinents;i++){
			boolean captured = continents.elementAt(i).isContinentcaptured(currentPlayer);
			if(captured)
				continentBonus += continents.elementAt(i).getValue();
			System.out.println("Bonus "+continentBonus+" for "+continents.elementAt(i).getName());
		}
		return continentBonus;
	}*/
	
	public int turnBonus(){
		
		int bonus = 0;
		bonus += collectReinforcements();
		//bonus += collectReinforcementsFromContinents();
		
		return bonus;
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
		File file = new File("resources/world.map");
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
					territories.add(new Territory(name, x, y, continent));
					next = scanner.next();
					if(next.equals(";;")) done = true;
				}while(done == false);
			}
		}
		scanner.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
