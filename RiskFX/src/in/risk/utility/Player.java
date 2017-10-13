package in.risk.utility;

import java.util.Vector;

public class Player {
	
	private String name;
	private int armies;
	private Vector<Territory> occupiedTerritories;
	private Vector<Card> cards;
	private int id;
	
	 Player(String nm, int i) {
		 name  =nm;
		 id = i;
		 occupiedTerritories = new Vector<Territory>();
		 cards = new Vector<Card>();
		 
	 }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getArmies() {
		return armies;
	}

	public void setArmies(int armies) {
		this.armies = armies;
	}

	public Vector<Territory> getOccupiedTerritories() {
		return occupiedTerritories;
	}

	public void setOccupiedTerritories(Vector<Territory> occupiedTerritories) {
		this.occupiedTerritories = occupiedTerritories;
	}

	public Vector<Card> getCards() {
		return cards;
	}

	public void setCards(Card c) {
		cards.add(c);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	 public int getNumberOfTerritories(){
		 return occupiedTerritories.size(); 
	 }
	 
	 public void occupyTerritory(Territory t){
		 occupiedTerritories.add(t);
	 }
	 
	 public void looseTerritory(Territory t){
		 occupiedTerritories.remove(t);
		 occupiedTerritories.trimToSize();
	 }
	 
	 public void addArmy(){
		 armies++;
	 }
	 
	 public void looseArmy(){
		 armies--;
	 }
	 
	 public void addArmies(int a){
		 armies += a;
	 }
	 
	 public void loosArmies(int a){
		 armies -= a;
	 }
}
