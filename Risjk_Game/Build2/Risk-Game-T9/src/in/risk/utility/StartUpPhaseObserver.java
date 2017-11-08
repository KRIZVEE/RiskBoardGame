package in.risk.utility;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Observable;
import java.util.Observer;

public class StartUpPhaseObserver implements Observer {

	@Override
	public void update(Observable o, Object arg) {

		PrintWriter writer = null;
		try {
			// writer = new
			// PrintWriter("C:\\Users\\Kashif_Rizvee\\Desktop\\Risk.txt",
			// "UTF-8");
			writer = new PrintWriter("C:\\Users\\Kashif_Rizvee\\Desktop\\GamePhaseView.txt", "UTF-8");

		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		writer.println("Initially assigned countries to each individual players");
		writer.println(((RiskGame) o).initialPlayerCountry);

		writer.println("Each individual players placed number of armies to his/her own countries");
		writer.println(((RiskGame) o).countriesArmies);
		// writer.println("The second line");

		writer.close();
	}
}
