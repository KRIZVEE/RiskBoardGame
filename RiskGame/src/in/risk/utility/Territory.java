package in.risk.utility;

import java.util.Vector;

public class Territory {
	
	public String nameTerritory;
	private String nameContinent;
	private int xAxis;
	private int yAxis;
	private Vector<String> adjacents;
	private int armies;
	private Player player;
	
	
	
	public Territory(String t_nm) {
		
		
		nameTerritory = t_nm;
//		nameContinent = c_nm;
//		xAxis = x;
//		yAxis = y;
		adjacents = new Vector<String>();
		armies = 0;
		player = new Player(null, -1);
		
	}


	public String getNameTerritory() {
		return nameTerritory;
	}


	public void setNameTerritory(String nameTerritory) {
		this.nameTerritory = nameTerritory;
	}


	public Player getPlayer() {
		return player;
	}


	public void setPlayer(Player player) {
		this.player = player;
	}


	public String getNameContinent() {
		return nameContinent;
	}


	public void setNameContinent(String nameContinent) {
		this.nameContinent = nameContinent;
	}


	public int getxAxis() {
		return xAxis;
	}


	public void setxAxis(int xAxis) {
		this.xAxis = xAxis;
	}


	public int getyAxis() {
		return yAxis;
	}


	public void setyAxis(int yAxis) {
		this.yAxis = yAxis;
	}


	public Vector<String> getAdjacents() {
		return adjacents;
	}


	public void setAdjacents(Vector<String> adjacents) {
		this.adjacents = adjacents;
	}
	
/*	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}*/


	public int getArmies() {
		return armies;
	}


	public void setArmies(int armies) {
		this.armies = armies;
	}

	public boolean isAdjacent(Territory t){
		return adjacents.contains(t.getNameTerritory());
	}
	
	public boolean isOccupied(){
		if(player.getId() == -1)
			return false;
		return true;
	}
	
	public void addArmies(int n){
		armies += n;
	}
	
	public void loosArmies(int n){
		armies -= n;
	}
	
	public void addArmy(){
		armies++;
	}
	
	public void looseArmy(){
		armies--;
	}
	
	public void printAdjacents(){
		for(int i=0; i< adjacents.size();i++){
			System.out.println("Print "+ adjacents.elementAt(i));
		}
	}

}
