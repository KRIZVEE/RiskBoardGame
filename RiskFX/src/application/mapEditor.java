package application;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class mapEditor extends Application {

	public void start(Stage primaryStage) throws IOException {
		// Reading file containig map elements in .map format
		FileInputStream fin = new FileInputStream("C:\\Users\\Kashif_Rizvee\\Desktop\\Risk\\World.map");
		ArrayList<String> continentFilterNew = new ArrayList<String>();
		final ArrayList<String> myObject = new ArrayList<String>();
		List<String> newList;
		ArrayList<String> countryFilter = new ArrayList<String>();
		boolean done = false;
		String next = null;
		String str = null;

		Scanner mapfile = new Scanner(fin);

		while (mapfile.hasNextLine()) {
			done = false;
			next = mapfile.nextLine();
			String[] arr;

			if (next.equals("[Continents]")) {
				next = mapfile.nextLine();
				do {
					String[] parts = next.split("=");
					String part1 = parts[0];
					continentFilterNew.add(part1);
					// System.out.println(part1);
					next = mapfile.nextLine();
				} while (!next.equals(""));
				// System.out.println("Continent in Array List : " +
				// continentFilterNew);
			} // end of continent
			if (next.equals("[Territories]")) {
				next = mapfile.nextLine();
				do {
					String[] parts = next.split(",");
					newList = Arrays.asList(parts);

					// map.put(newList.get(0), newList);

					// adjacencyFilter.addAll(newList);
					String part1 = parts[0];
					countryFilter.add(part1);
					// System.out.println(part1);
					next = mapfile.nextLine();
				} while (!next.equals(";;"));
			}

			fin.close();
		}

		// Reading or creating image file from folder
		FileInputStream inputstream = new FileInputStream("C:/Users/Kashif_Rizvee/Desktop/Risk/RiskLogo.png");
		Image img = new Image(inputstream);

		// Setting image view
		ImageView view = new ImageView(img);
		view.setX(120.0);
		view.setY(120.0);
		view.setFitHeight(100);
		view.setFitWidth(600);
		view.setPreserveRatio(true);

		// Setting up text
		Text text1 = new Text("Continents: ");

		// Setting labels
		Label label1 = new Label();
		Label label2 = new Label();
		Label label3 = new Label();
		Label label4 = new Label();
		Label label5 = new Label();
		Label label6 = new Label();
		Label label7 = new Label();

		////////////////////////////////////////////////////////
		////////////////////////////////////////////////////////
		////////////// see here //////////////
		////////////////////////////////////////////////////////
		////////////////////////////////////////////////////////

		// Setting textbox
		TextField continentname;
		continentname = new TextField();
		continentname.setPromptText("Please enter continent name");
		// continentname.setText("Continent");
		continentname.setVisible(false);
		

		if (continentname.getText().trim().equals("")) {
			System.out.println("textField is empty");
		}
		System.out.println("print before");
		System.out.println(continentname.getText());
		System.out.println("print after");

		// str = continentname.getText().toString();
		// System.out.println(str);
		// myObject.add(str);
		// myObject.add(continentname.getText());
		// System.out.println(myObject);

		TextField countryname = new TextField();
		countryname.setVisible(false);

		fileStreamTest f = new fileStreamTest();

		// Setting up dropdown box
		// Setting up dropdown boxfor number of players
		ObservableList<String> numOfcontinent = FXCollections.observableArrayList(continentFilterNew);
		ComboBox box1 = new ComboBox(numOfcontinent);
		box1.setVisible(false);
		// drop down list for continents for country
		ObservableList<String> numOfcontinent1 = FXCollections.observableArrayList(myObject);

		ComboBox box2 = new ComboBox(numOfcontinent1);
		box2.setVisible(false);
		ObservableList<String> countrylist = FXCollections.observableArrayList(countryFilter);

		ComboBox box3 = new ComboBox(countrylist);
		box3.setVisible(false);

		// Add button
		Button add = new Button("Add");
		Button delete = new Button("Delete");
		Button nextBtn = new Button("Next");
		Button addcoun = new Button("Add");
		addcoun.setVisible(false);
		Button delcoun = new Button("Delete");
		delcoun.setVisible(false);
		Button save = new Button("Save");
		save.setVisible(false);

		// Set actions on Add button
		add.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				label1.setText("Name: ");
				continentname.localToScene(170.0, 170.0);
				continentname.setVisible(true);

			}
		});

		// Set action on Delete button
		delete.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				label2.setText("Continent List: ");
				box1.setVisible(true);
			}
		});

		// Set action on next button
		nextBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				label3.setText("Country: ");
				label7.setText("Adjacency: ");
				addcoun.setVisible(true);
				delcoun.setVisible(true);
				save.setVisible(true);
				// ====> need to work on this====>
				myObject.add(continentname.getText());
				// continentname = (TextField)(continentname.getText());
				// String str = continentname.getText();

				// myObject.set(5, str);
				// continentFilterNew.add(continentname.getText());
				// myObject = new ArrayList<String>(continentFilterNew);

				// continentFilterNew.add(continentname.)
				// System.out.println(continentFilterNew);

			}
		});

		// set action on add country button
		addcoun.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				label4.setText("Name: ");
				box2.setVisible(true);
				countryname.setVisible(true);
				label5.setText("Continent List: ");

			}
		});

		// Set action on delete country button
		delcoun.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				label6.setText("Country List: ");
				box3.setVisible(true);

			}
		});

		GridPane grid1 = new GridPane();
		grid1.setMinSize(700, 800);
		grid1.setVgap(6);
		grid1.setHgap(6);
		grid1.setAlignment(Pos.CENTER);
		grid1.add(text1, 0, 0);
		grid1.add(add, 0, 1);
		grid1.add(label1, 1, 1);
		grid1.add(continentname, 2, 1);
		grid1.add(delete, 0, 4);
		grid1.add(label2, 1, 4);
		grid1.add(box1, 2, 4);
		grid1.add(nextBtn, 0, 5);
		grid1.add(label3, 0, 7);
		grid1.add(addcoun, 0, 8);
		grid1.add(delcoun, 0, 9);
		grid1.add(label4, 1, 8);
		grid1.add(countryname, 2, 8);
		grid1.add(label5, 3, 8);
		grid1.add(box2, 4, 8);
		grid1.add(label6, 1, 9);
		grid1.add(box3, 2, 9);
		grid1.add(label7, 0, 12);
		grid1.add(save, 0, 14);

		Group root = new Group();
		ObservableList list = root.getChildren();

		list.add(view);

		list.add(grid1);

		Scene scene1 = new Scene(root, 600, 600);
		primaryStage.setTitle("Risk Game");
		primaryStage.setScene(scene1);
		primaryStage.show();

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		launch(args);

	}

}

class ObjectEventHandler implements EventHandler<ActionEvent> {
	ArrayList object;

	ObjectEventHandler() {

	}

	@Override
	public void handle(ActionEvent event) {
		// TODO Auto-generated method stub

	}

}

