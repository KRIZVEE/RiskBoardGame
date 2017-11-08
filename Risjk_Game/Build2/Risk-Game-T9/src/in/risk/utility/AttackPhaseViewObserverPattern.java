package in.risk.utility;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Observable;
import java.util.Observer;

public class AttackPhaseViewObserverPattern implements Observer {

	@Override
	public void update(Observable o, Object arg) {

		System.out.println("printing here for attack phase");

		PrintWriter writer = null;
		try {
			// writer = new
			// PrintWriter("C:\\Users\\Kashif_Rizvee\\Desktop\\RiskAttack.txt",
			// "UTF-8");
			writer = new PrintWriter("C:\\Users\\Kashif_Rizvee\\Desktop\\GamePhaseView.txt", "UTF-8");

		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// writer.println("");
		// chosen country from where attack
		// chosen country to where attack
		// Attacker Dice chosen value
		// Defender Dice chosen value
		// Attacker updated country list + updated army values
		// Defender updated country list + updated army values
		// how many times he attacked

		writer.println("Player chose below country to attack FROM : ");
		writer.println(((Player) o).attackerCountry);

		writer.println("Player chose below country to attack TO : ");
		writer.println(((Player) o).defenderCountry);

		writer.println("Attacker Dice Chosen Value");
		writer.println(((Player) o).noOfAttackerDice);

		writer.println("Defender Dice Chosen Value");
		writer.println(((Player) o).noOfDefenderDice);

		writer.println("Updated Country list and corresponding army values :");
		writer.println(((Player) o).initialPlayerCountryObserver + "   " + ((Player) o).countriesArmiesObserver);

		writer.println("How many time attacked happened");
		writer.println(((Player) o).countNoOfAttack);

		writer.close();

	}

}
