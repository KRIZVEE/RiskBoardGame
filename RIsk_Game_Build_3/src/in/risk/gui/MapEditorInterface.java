package in.risk.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import in.risk.impl.MapLoader;
import in.risk.impl.MapWriter;
import in.risk.impl.StartUpPhase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MapEditorInterface {

	//data structures for storing the map elements.
	
	public static List<String> editedContinents = new ArrayList<String>();
	public static List<String> editedCountries = new ArrayList<String>();

	public static HashMap<String, String> pairedContinentsCountries = new HashMap<String, String>();

	public static Stage mapEditorStage = new Stage();
	public static Stage continentEditorStage = new Stage();
	public static Stage countryEditorStage = new Stage();

	public static ObservableList<String> countryList = FXCollections.observableArrayList();
	public static HashMap<String, List<String>> editedContinentCountries = new HashMap<String, List<String>>();
	public static HashMap<String, List<String>> editedCountryCoordinates = new HashMap<String, List<String>>();
	public static HashMap<String, List<String>> editedAdj = new HashMap<String, List<String>>();
	public static HashMap<String, String> editedCountryContinents = new HashMap<String, String>();
	public static ArrayList<String> editedAdjacents = new ArrayList<String>();
	public static List<List<String>> listOfLists = new ArrayList<List<String>>();
	public static List<List<String>> listOfListsNew = new ArrayList<List<String>>();
	public static List<String> keyList = new ArrayList<String>();
	Button editContinents;
	Button editCountries;
	Button back;
	Button saveMap;

	public void mapEditorInterface() {
		
		//putting map elements from Map loader class to edited data structures
		editedContinentCountries.putAll(MapLoader.continentCountries);
		editedCountryContinents.putAll(MapLoader.countryContinent);
		editedCountryCoordinates.putAll(MapLoader.countryCoordinates);
		editedAdj.putAll(MapLoader.adj);

		Image logo = new Image(StartUpPhase.logoPath);
		ImageView imgview = new ImageView(logo);

		editContinents = new Button("Edit Continents");

		editContinents.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				editContinentsInterface();
			}
		});
		editCountries = new Button("Edit Countries");

		editCountries.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				editCountriesInterface();

			}
		});

		back = new Button("Back");

		back.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				mapEditorStage.close();
			}
		});

		saveMap = new Button("Save Map");

		saveMap.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				mapEditorStage.close();
				MapWriter.mapWriter(RiskInterface.pathMap);

			}
		});

		VBox vbox = new VBox();
		vbox.getStyleClass().add("vbox");
		HBox hbox = new HBox();
		hbox.getStyleClass().add("hbox");
		hbox.getChildren().addAll(editContinents, editCountries);
		vbox.getChildren().addAll(imgview, hbox, back, saveMap);

		Group mapEdiorRoot = new Group();
		mapEdiorRoot.getChildren().addAll(vbox);
		Scene mapEditorScene = new Scene(mapEdiorRoot, 600, 500);
		mapEditorScene.getStylesheets().add(StartUpPhase.css);
		mapEditorStage.setTitle("Risk Game: Map Editor");
		mapEditorStage.setScene(mapEditorScene);
		mapEditorStage.centerOnScreen();
		mapEditorStage.show();

	}

	public static void editContinentsInterface() {

		// method for editing continents in map editor
		Image logo = new Image(StartUpPhase.logoPath);
		ImageView imageView = new ImageView(logo);

		Label lblAdd = new Label("Add a new continent: ");
		TextField fieldAdd = new TextField();
		TextField fieldAdd1 = new TextField();
		fieldAdd.setPromptText("Enter new continent");
		fieldAdd1.setPromptText("Enter new country");
		Label lblDel = new Label("Delete a continent: ");
		Button add = new Button("Add");
		Button del = new Button("Delete");
		Button submit = new Button("Submit");

		ObservableList<String> continentsList = FXCollections.observableArrayList(MapLoader.continentFilterNew);
		ComboBox<String> conListBox = new ComboBox<>(continentsList);
		conListBox.setPromptText("Select Continent");

		HBox hbox1 = new HBox();
		hbox1.getStyleClass().add("hbox");
		hbox1.getChildren().addAll(lblAdd, fieldAdd, fieldAdd1, add);

		HBox hbox2 = new HBox();
		hbox2.getStyleClass().add("hbox");
		hbox2.getChildren().addAll(lblDel, conListBox, del);
		VBox vbox = new VBox();
		vbox.getStyleClass().add("vbox");
		vbox.getChildren().addAll(imageView, hbox1, hbox2, submit);
		Group continentEditorRoot = new Group();
		continentEditorRoot.getChildren().addAll(vbox);
		
		add.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (!fieldAdd.getText().isEmpty()) {
					continentsList.add(fieldAdd.getText());
					String name = fieldAdd1.getText();
					countryList.add(name);
					MapLoader.countryFilter.add(name);
					Long x = (long) Math.floor(Math.random() * 150);
					Long y = (long) Math.floor(Math.random() * 200);
					String x_ = x.toString();
					String y_ = y.toString();
					List<String> points = new ArrayList<>();
					points.add(x_);
					points.add(y_);
					MapLoader.countryCoordinates.put(name, points);
					MapLoader.countryContinent.put(fieldAdd1.getText(), fieldAdd.getText());
					List<String> newAdj = new ArrayList<String>();
					newAdj.add(MapLoader.countryFilter.get(25));

					// editedAdj.add(MapLoader.countryFilter.get(24),editedAdj.get(MapLoader.countryFilter.get(24)).add(fieldAdd1.getText()));
					MapLoader.adj.put(fieldAdd1.getText(), newAdj);
					List<String> value = MapLoader.adj.get(name);
					List<String> value2 = new ArrayList<String>(value);
					value2.add(name);
					MapLoader.adj.put(MapLoader.countryFilter.get(25), value2);
					// System.out.println(editedAdj);
				}
				fieldAdd.clear();
			}
		});

		del.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				String s = conListBox.getValue();
				editedContinentCountries.remove(conListBox.getValue());
				continentsList.remove(conListBox.getValue());
				for (Entry<String, List<String>> entry : editedContinentCountries.entrySet()) {
					List<String> value = entry.getValue();
					for (int i = 0; i < value.size(); i++) {
						editedCountries.add(value.get(i));
					}
				}
				for (int i = 0; i <= MapLoader.continentCountries.get(s).size() - 1; i++) {
					editedCountryCoordinates.remove(MapLoader.continentCountries.get(s).get(i));
					editedCountryContinents.remove(MapLoader.continentCountries.get(s).get(i));
					editedAdj.remove(MapLoader.continentCountries.get(s).get(i));
				}
				for (Entry<String, List<String>> entry : editedAdj.entrySet()) {
					List<String> value = entry.getValue();
					listOfLists.add(value);
				}
				for (int i = 0; i < listOfLists.size(); i++) {

					for (int j = 0; j < MapLoader.continentCountries.get(s).size(); j++) {
						if (listOfLists.get(i).contains(MapLoader.continentCountries.get(s).get(j))) {
							int index = listOfLists.get(i).indexOf(MapLoader.continentCountries.get(s).get(j));
							List<String> newList = new ArrayList<String>(listOfLists.get(i));
							newList.remove(index);
							int indexofCurrent = listOfLists.indexOf(listOfLists.get(i));
							listOfLists.remove(i);

							listOfLists.add(indexofCurrent, newList);

						}
					}

				}
				List<String> value = new ArrayList<>();
				for (Entry<String, List<String>> entry : editedAdj.entrySet()) {
					String Key = entry.getKey();
					keyList.add(Key);
				}

				for (int i = 0; i < listOfLists.size(); i++) {
					value = listOfLists.get(i);
					editedAdj.put(keyList.get(i), value);
				}
			}
		});

		submit.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				for (int i = 0; i < continentsList.size(); i++) {
					editedContinents.add(continentsList.get(i));

				}
				continentEditorStage.close();

			}
		});

		Scene conEditorScene = new Scene(continentEditorRoot, 600, 500);
		conEditorScene.getStylesheets().add(StartUpPhase.css);
		continentEditorStage.setTitle("Risk Game: Edit Continents");
		continentEditorStage.setScene(conEditorScene);
		continentEditorStage.centerOnScreen();
		continentEditorStage.show();

	}

	public static void editCountriesInterface() {

		//method for editing countries in map editor
		Image logo = new Image(StartUpPhase.logoPath);
		ImageView imgview = new ImageView(logo);
		ObservableList<String> continentList;

		if (!editedContinents.isEmpty()) {
			continentList = FXCollections.observableArrayList(editedContinents);
		} else
			continentList = FXCollections.observableArrayList(MapLoader.continentFilterNew);

		ComboBox<String> continentBox = new ComboBox<String>(continentList);
		continentBox.setPromptText("Select Continent");
		if (editedCountries.isEmpty()) {
			countryList.addAll(MapLoader.countryFilter);
		} else {
			countryList.addAll(editedCountries);
		}
		ComboBox<String> countryBox = new ComboBox<String>(countryList);
		countryBox.setPromptText("Select Country");

		Label lblAdd = new Label("Add Country: ");
		TextField fieldAdd = new TextField();
		fieldAdd.setPromptText("Enter Country Name");
		Label lblDel = new Label("Delete Country: ");

		Button add = new Button("Add");
		Button del = new Button("Delete");
		Button submit = new Button("Submit");

		HBox hbox1 = new HBox();
		hbox1.getStyleClass().add("hbox");
		hbox1.getChildren().addAll(lblAdd, fieldAdd, continentBox, add);

		HBox hbox2 = new HBox();
		hbox2.getStyleClass().add("hbox");
		hbox2.getChildren().addAll(lblDel, countryBox, del);

		VBox vbox = new VBox();
		vbox.getStyleClass().add("vbox");
		vbox.getChildren().addAll(imgview, hbox1, hbox2, submit);

		try {

			add.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					if (!fieldAdd.getText().isEmpty()) {
						String name = fieldAdd.getText();
						countryList.add(name);
						Long x = (long) Math.floor(Math.random() * 150);
						Long y = (long) Math.floor(Math.random() * 200);
						String x_ = x.toString();
						String y_ = y.toString();
						List<String> points = new ArrayList<>();
						points.add(x_);
						points.add(y_);
						editedCountryCoordinates.put(name, points);
						editedCountryContinents.put(name, continentBox.getValue());
						List<String> newAdj = new ArrayList<String>();
						newAdj.add(MapLoader.countryFilter.get(17));
						editedAdj.put(name, newAdj);
						List<String> value = editedAdj.get(name);
						List<String> value2 = new ArrayList<String>(value);
						value2.add(name);
						editedAdj.put(MapLoader.countryFilter.get(17), value2);
					}
					fieldAdd.clear();
				}
			});

			del.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {

					String name = countryBox.getValue();
					editedCountryCoordinates.remove(name);
					editedCountryContinents.remove(name);
					editedAdj.remove(name);
					for (Entry<String, List<String>> entry : editedAdj.entrySet()) {
						List<String> value = entry.getValue();
						String key = entry.getKey();
						if (value.contains(name)) {
							int index = value.indexOf(name);
							List<String> newList = new ArrayList<String>(value);
							newList.remove(index);
							editedAdj.put(key, newList);

						}
					}
					countryList.remove(countryBox.getValue());
				}
			});

			submit.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {

					for (int i = 0; i < countryList.size(); i++) {
						editedCountries.add(countryList.get(i));
					}
					countryEditorStage.close();

				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

		Group countryEditorRoot = new Group();
		countryEditorRoot.getChildren().addAll(vbox);
		Scene countryEditorscene = new Scene(countryEditorRoot, 600, 500);
		countryEditorscene.getStylesheets().add(StartUpPhase.css);
		countryEditorStage.setTitle("Risk Game: Edit Countries");
		countryEditorStage.setScene(countryEditorscene);
		countryEditorStage.centerOnScreen();
		countryEditorStage.show();

	}

}