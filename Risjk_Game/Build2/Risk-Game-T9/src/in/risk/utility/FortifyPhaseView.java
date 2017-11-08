package in.risk.utility;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Observable;
import java.util.Observer;

public class FortifyPhaseView implements Observer {

	@Override
	public void update(Observable o, Object arg) {

		System.out.println("printing here for fortify phase");

		PrintWriter writer = null;
		try {
			// writer = new
			// PrintWriter("C:\\Users\\Kashif_Rizvee\\Desktop\\RiskFortify.txt",
			// "UTF-8");"resources/maps/"
			writer = new PrintWriter("C:\\Users\\Kashif_Rizvee\\Desktop\\GamePhaseView.txt", "UTF-8");

		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// writer.println("");
		// chosen country from where to move army/armies
		// chosen country to where to move army/armies
		// how many number of army decided to move
		// army new value in country

		writer.println("chosen country FROM where to move army/armies :  ");
		writer.println(((Player) o).from);

		writer.println("chosen country TO where to move army/armies :  ");
		writer.println(((Player) o).to);

		writer.println("Updated Country list and corresponding army values :");
		writer.println(((Player) o).countriesArmiesObserver);

		writer.close();

	}

}
