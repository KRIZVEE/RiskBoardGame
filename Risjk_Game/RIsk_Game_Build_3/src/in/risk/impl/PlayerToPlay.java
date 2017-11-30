package in.risk.impl;
/**
 * This class is used to implement the playerTOPlay class.
 * @author mohitrana
 *
 */
public class PlayerToPlay {

	//stores player's name, armies and ID
	private String name;
	private int armies;
	private int playerId;

	public PlayerToPlay(String nm, int id) {
		name = nm;
		playerId = id;
	}

	/**
	 * This method returns the name of the player
	 * @return name of the palyer
	 */
	public String getName() {
		return name;
	}

	/**
	 * This method returns the number of armies own by the player
	 * @return armies of the player
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
	 * @param a this is the number of armies to be increase
	 */
	public void addArmies(int a) {
		armies += a;
	}

	/**
	 * This method used decrease number of armies by b
	 * @param a for the no of armies to loose
	 */
	public void loosArmies(int a) {
		armies -= a;
	}
}