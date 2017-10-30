//package in.risk.utility;

/**
 * This method gives all the information abotu the player.
 * 
 * @author Charanpreet Singh, Ishan Kansara, Kashif Rizvee, Mohit Rana
 * @version 1.0.0
 */
public class Player {

	private String name;
	private int armies;
	private int playerId;

	Player(String nm, int id) {
		name = nm;
		playerId = id;
	}

	/**
	 * This method returns the name of the player
	 */
	public String getName() {
		return name;
	}

	/**
	 * This method returns the number of armies own by the player
	 */
	public int getArmies() {
		return armies;
	}

	/**
	 * This method used increase number of armies of user by 1
	 */
	public void addArmy() {
		armies++;
	}

	/**
	 * This method used decrease number of armies of user by 1
	 */
	public void looseArmy() {
		armies--;
	}

	/**
	 * This method used increase number of armies by a
	 * 
	 * @param a
	 *            this is the number of armies to be increase
	 */
	public void addArmies(int a) {
		armies += a;
	}

	/**
	 * This method used decrease number of armies by b
	 * 
	 * @param b
	 *            this is the number of armies to be decrease
	 */
	public void loosArmies(int a) {
		armies -= a;
	}
}
