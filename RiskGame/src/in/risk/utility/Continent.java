package in.risk.utility;

public class Continent {

	private String name;
	private int value;
	//private Vector<String> territories;
	Territory t;
	
	 Continent(String name,int v) {
		this.name = name;
		value = v;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
	/*public Vector<String> getTerritories(){
		return territories;
	}
	
	public boolean isTerritoryOf(Territory t){
		return(territories.contains(t.getId()));
	}*/
	
	/*public boolean isContinentcaptured(Player p){
		Vector<Integer> t1 = new Vector<Integer>();
		Vector<Territory> t2 = p.getOccupiedTerritories();
		
		for(int c=0 ;c<t2.size();c++){
			t1.add(t2.elementAt(c).getId());
			
			for(int i=0; i<territories.size();i++){
				if(!t1.contains(territories.elementAt(i))){
					return false;
				}
			}
		}
		return true;
	}*/
	
}
