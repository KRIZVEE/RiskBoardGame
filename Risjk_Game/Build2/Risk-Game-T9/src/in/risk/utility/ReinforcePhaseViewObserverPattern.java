package in.risk.utility;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Observable;
import java.util.Observer;

public class ReinforcePhaseViewObserverPattern implements Observer {

	@Override
	public void update(Observable o, Object arg) {
		System.out.println("printing here for reinforce phase");

		PrintWriter writer = null;
		try {
			// writer = new
			// PrintWriter("C:\\Users\\Kashif_Rizvee\\Desktop\\RiskRenforce.txt",
			// "UTF-8");
			writer = new PrintWriter("E:/Risk_Game/Risjk_Game/phaseviewlog.txt", "UTF-8");

		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// writer.println("");
		writer.println("Armies assigned from Risk RUle based on country owning");
		writer.println(((Player) o).noOfArmiesFromCountries);

		writer.println("Armies assigned from Risk RUle based on continent owning");
		writer.println(((Player) o).noOfArmiesFromContinents);

		writer.println("Armies assigned from Risk RUle based on card owning");
		writer.println(((Player) o).noOfArmiesFromCards);

		writer.println("Updated Army List");

		writer.println(((Player) o).countriesArmiesObserver);

		writer.close();
	}

}

/*
 
 */