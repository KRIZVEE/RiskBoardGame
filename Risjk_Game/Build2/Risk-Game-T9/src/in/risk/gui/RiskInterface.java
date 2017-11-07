package in.risk.gui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import in.risk.utility.AttackObserver;
import in.risk.utility.CardExchangeObserver;
import in.risk.utility.MapLoader;
import in.risk.utility.PlaceArmiesObserver;
import in.risk.utility.Player;
import in.risk.utility.RiskGame;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * This class is main interface for starting map editor.</br>
 * and starting the game phases.
 * 
 * @author Charanpreet Singh, Ishan Kansara, Kashif Rizvee, Mohit Rana
 * @version 1.0.0
 */
public class RiskInterface extends Application {

	RiskGame rNew;// = new RiskGame();
	Player objPlayer;
	AttackObserver Attack;// = new AttackObserver();
	PlaceArmiesObserver placeArmiesObject;// = new PlaceArmiesObserver();
	CardExchangeObserver cardExchangeObject;

	public RiskInterface() {
		// TODO Auto-generated constructor stub
		rNew = new RiskGame();
		objPlayer = new Player();
		Attack = new AttackObserver();
		placeArmiesObject = new PlaceArmiesObserver();
		cardExchangeObject = new CardExchangeObserver();

		rNew.addObserver(placeArmiesObject);
		rNew.addObserver(Attack);
		objPlayer.addObserver(cardExchangeObject);
	}

	MapEditorInterface objMapEditorInterface = new MapEditorInterface();
	RiskGame objRiskGame = new RiskGame();
	MapLoader objMapLoader = new MapLoader();

	public static String pathMap;

	Button mapEditor;
	Button addPlayer;
	Button startGame;

	// Image logo = new Image(RiskGame.logoPath);
	// ImageView imgView = new ImageView();

	public ArrayList<String> path;

	/**
	 * This method invokes the initial stage.
	 * 
	 * @throws Exception
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {

		File dir = new File("resources/maps");

		path = new ArrayList<String>(Arrays.asList(dir.list()));

		mapEditor = new Button("Map Editor");
		addPlayer = new Button("Add Players");
		startGame = new Button("Start Game");

		Text selectmap = new Text("Select Maps: ");

		// Setting up combo box for selecting maps
		ObservableList<String> maplist = FXCollections.observableArrayList(path);
		ComboBox<String> maplist2 = new ComboBox<String>(maplist);
		maplist2.setVisible(true);
		maplist2.setMinWidth(200);

		VBox vbox = new VBox();
		vbox.getStyleClass().add("vbox");
		vbox.getChildren().addAll(selectmap, maplist2, mapEditor, addPlayer, startGame);

		Group root = new Group();

		root.getChildren().add(vbox);
		Scene scene = new Scene(root, 600, 500);

		// scene.getStylesheets().add(RiskGame.css);

		primaryStage.setTitle("Risk Game");
		primaryStage.setScene(scene);
		primaryStage.sizeToScene();
		primaryStage.centerOnScreen();
		primaryStage.show();

		startGame.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				pathMap = maplist2.getValue();
				System.out.println(pathMap);
				try {
					objRiskGame.startGame(pathMap);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				primaryStage.close();

				MapLoader.mapLoader(pathMap);
				MapEditorInterface.continentEditorStage.close();
				MapEditorInterface.countryEditorStage.close();
				MapEditorInterface.mapEditorStage.close();
			}
		});

		mapEditor.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				pathMap = maplist2.getValue();
				MapLoader.mapLoader(pathMap);
				objMapEditorInterface.mapEditorInterface();
			}
		});
	}

	/**
	 * This method launches the main method
	 */

	public static void main(String[] args) throws IOException {
		System.out.println("hi");
		launch(args);
		// observerDemo obj = new observerDemo();

		RiskInterface objRiskInterface = new RiskInterface();
		objRiskInterface.demo();
		RiskGame rg = new RiskGame();
		rg.notifyObservers();
	}

	public void demo() throws IOException {
		// pathMap
		rNew.startGame(RiskInterface.pathMap);
		rNew.initialPlayerCountries();

		// rNew.startGame("C:\\Users\\mohit\\Desktop\\Risjk_Game\\World3.map");

	}
}