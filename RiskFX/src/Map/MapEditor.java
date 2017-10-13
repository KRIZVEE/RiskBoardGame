package Map;

public class MapEditor extends MapEditorMethods{
	public static void main(String args[]){
		MapEditorMethods l = new MapEditorMethods();
		l.LoadingMap("Data/world.map");
		l.delteContinent("Asia");
		l.deleteCountry("Alaska");
	}
}