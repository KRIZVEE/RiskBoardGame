package in.risk.utility;

import java.util.Vector;

public class Territory {
	
	private String nameTerritory;
	private String nameContinent;
	private int xAxis;
	private int yAxis;
	private Vector<String> adjacents;
	
	
	public Territory(String t_nm,int x,int y, String c_nm) {
		
		nameTerritory = t_nm;
		nameContinent = c_nm;
		xAxis = x;
		yAxis = y;
		adjacents = new Vector<String>();
		
	}

	public String getNameTerritory() {
		return nameTerritory;
	}

	public void setNameTerritory(String nameTerritory) {
		this.nameTerritory = nameTerritory;
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
	
	public boolean isAdjacent(Territory t){
		return adjacents.contains(t.getNameTerritory());
	}
	
	/*public boolean isOccupied(){
		
	}*/
	

}
