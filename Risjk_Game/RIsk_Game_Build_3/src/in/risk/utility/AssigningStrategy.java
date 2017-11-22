package in.risk.utility;

import java.io.IOException;

/** Class configured with a ConcreteStrategy object and maintains
*   a reference to a Strategy object. Depending on which strategy is 
*   plugged in, it will execute a different operation.  
*/

public class AssigningStrategy {
	
    private Strategy strategy;
    
    /**
     * Plugs in a specific strategy to be used 
     */
    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }
    
    /**
     * Method that executes a different strategy depending on what strategy was 
     * plugged in upon instantiation. 
     * @throws IOException 
     */
   
    public void executeStrategy(PlayerToPlay playerName) throws IOException {
        this.strategy.placeReinforcementArmies(playerName);
//        this.strategy.attackPhase(playerName);//(playerName);
//        this.strategy.fortifyPhase(playerName);//(playerName);

        
    }
    
    

}
