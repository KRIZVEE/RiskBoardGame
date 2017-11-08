package in.risk.gui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import in.risk.utility.AttackPhaseViewObserverPattern;
import in.risk.utility.CardExchangeObserver;
import in.risk.utility.FortifyPhaseView;
import in.risk.utility.MapLoader;

import in.risk.utility.Player;
import in.risk.utility.ReinforcePhaseViewObserverPattern;
import in.risk.utility.RiskGame;
import in.risk.utility.StartUpPhaseObserver;
import in.risk.utility.WorldDominanceObserver;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

// team file///
public class RiskInterface extends Application {

	RiskGame obj_RI_WDO;
	RiskGame obj_RI_SUPO;
	Player obj_P_RPVO;
	Player obj_P_CEO;
	Player obj_P_APV;
	Player obj_P_FPV;

	WorldDominanceObserver objWorldDominance;
	CardExchangeObserver objCardExchangeObserver;
	StartUpPhaseObserver objStartUpPhaseObserver;
	ReinforcePhaseViewObserverPattern objReinforcePhaseViewObserverPattern;
	AttackPhaseViewObserverPattern objAttackPhaseViewObserverPattern;
	FortifyPhaseView objFortifyPhaseView;

	public RiskInterface() {
		// TODO Auto-generated constructor stub
		obj_RI_WDO = new RiskGame();
		obj_RI_SUPO = new RiskGame();
		obj_P_CEO = new Player();
		obj_P_RPVO = new Player();
		obj_P_APV = new Player();
		obj_P_FPV = new Player();

		objWorldDominance = new WorldDominanceObserver();
		objCardExchangeObserver = new CardExchangeObserver();
		objStartUpPhaseObserver = new StartUpPhaseObserver();
		objReinforcePhaseViewObserverPattern = new ReinforcePhaseViewObserverPattern();
		objAttackPhaseViewObserverPattern = new AttackPhaseViewObserverPattern();
		objFortifyPhaseView = new FortifyPhaseView();

		// rNew1 = new RiskGame();
		// rNew2 = new RiskGame();
		// objPlayer = new Player();
		// Attack = new AttackObserver();
		// placeArmiesObject = new PlaceArmiesObserver();

		obj_RI_WDO.addObserver(objWorldDominance);
		obj_P_CEO.addObserver(objCardExchangeObserver);
		obj_RI_SUPO.addObserver(objStartUpPhaseObserver);
		obj_P_RPVO.addObserver(objReinforcePhaseViewObserverPattern);
		obj_P_APV.addObserver(objAttackPhaseViewObserverPattern);
		obj_P_FPV.addObserver(objFortifyPhaseView);

		// rNew1.addObserver(placeArmiesObject);
		// rNew2.addObserver(Attack);
		// objPlayer.addObserver(objCardExchangeObserver);
	}

	MapEditorInterface objMapEditorInterface = new MapEditorInterface();
	RiskGame objRiskGame = new RiskGame();
	MapLoader objMapLoader = new MapLoader();

	public static String pathMap;

	Button mapEditor;
	Button startGame;

	Image logo = new Image(RiskGame.logoPath);
	ImageView imgView = new ImageView();

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
		startGame = new Button("Start Game");

		Text selectmap = new Text("Select Maps: ");

		// Setting up combo box for selecting maps
		ObservableList<String> maplist = FXCollections.observableArrayList(path);
		ComboBox<String> maplist2 = new ComboBox<String>(maplist);
		maplist2.setVisible(true);
		maplist2.setMinWidth(200);

		VBox vbox = new VBox();
		vbox.getStyleClass().add("vbox");
		vbox.getChildren().addAll(imgView, selectmap, maplist2, mapEditor, startGame);

		Group root = new Group();

		root.getChildren().add(vbox);
		Scene scene = new Scene(root, 600, 500);

		scene.getStylesheets().add(RiskGame.css);

		primaryStage.setTitle("Risk Game");
		primaryStage.setScene(scene);
		primaryStage.sizeToScene();
		primaryStage.centerOnScreen();
		primaryStage.show();

		startGame.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				primaryStage.close();
				MapEditorInterface.continentEditorStage.close();
				MapEditorInterface.countryEditorStage.close();
				MapEditorInterface.mapEditorStage.close();
//				ObserverDemo obj = new ObserverDemo();
				pathMap = maplist2.getValue();
				try {
					// objRiskGame.startGame(pathMap);
					obj_RI_WDO.startGame(pathMap);
				} catch (IOException e) {
					e.printStackTrace();
				}
//				MapLoader.mapLoader(pathMap);

			}
		});

		mapEditor.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				pathMap = maplist2.getValue();
				MapLoader.mapLoader(pathMap);
				if (MapLoader.connectedCountry() == false) {
					System.out.println("It is an unconnected map.");
					primaryStage.close();
				} else if (MapLoader.connectedContinent() == false) {
					System.out.println("Map has unconnected continent.");
					primaryStage.close();
				} else if (MapLoader.emptyContinent() == false) {
					System.out.println("Map has a continent with no countries");
					primaryStage.close();
				} else {
					objMapEditorInterface.mapEditorInterface();
				}

			}
		});
	}

	/**
	 * This method launches the main method
	 */

	public static void main(String[] args) {
		launch(args);

	}

	public void demoWorldDominance() throws IOException {
		obj_RI_WDO.getWorldDominance();
	}

	public void demoCardExchange() throws IOException {
		obj_P_CEO.getArmiesFromCards();
	}

	public void demoStartUpPhase() throws IOException {
		 obj_RI_SUPO.placeArmies(); // mohit change
	}

	public void demoReinforcePhaseView() throws IOException {
		obj_P_RPVO.reinforcementPhase();
	}

	public void demoAttackPhaseView() throws IOException {
		obj_P_APV.attackPhase();
	}

	public void demoFortifyPhaseView() throws IOException {
		obj_P_FPV.fortify(RiskGame.currentPlayer);
	}
}