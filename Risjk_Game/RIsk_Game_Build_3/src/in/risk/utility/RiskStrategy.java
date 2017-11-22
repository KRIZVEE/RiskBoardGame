package in.risk.utility;

public interface RiskStrategy {
	
	public void placeReinforcementArmies(PlayerToPlay playerName);
	public void attackPhase(PlayerToPlay playerName);
	public void fortify(PlayerToPlay playerName);

}
