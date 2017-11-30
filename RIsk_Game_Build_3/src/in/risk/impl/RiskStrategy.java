package in.risk.impl;

public interface RiskStrategy {
	
	//defining risk strategies
	
	public void placeReinforcementArmies(PlayerToPlay playerName);
	public void attackPhase(PlayerToPlay playerName);
	public void fortify(PlayerToPlay playerName);

}
