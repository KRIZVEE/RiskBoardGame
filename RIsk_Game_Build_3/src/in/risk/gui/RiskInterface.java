package in.risk.gui;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import in.risk.impl.MapLoader;
import in.risk.impl.GamePhaseVIewObserver;
import in.risk.impl.HumanPlayer;
import in.risk.impl.RandomPlayer;
import in.risk.impl.StartUpPhase;
import in.risk.impl.WorldDominanceObserver;

import in.risk.saveload.ResourceManager;
import in.risk.saveload.SaveData;
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
public class RiskInterface extends Application {

	StartUpPhase obj = new StartUpPhase();
	
	//StartUpPhase objStartUpPhase;
	WorldDominanceObserver objWorldDominanceObserver;
	
	RandomPlayer objRandomPlayer;
	
	GamePhaseVIewObserver objPhaseVIewObserver;
	
	
	public RiskInterface() {
		// TODO Auto-generated constructor stub
		
		//objStartUpPhase = new StartUpPhase();
		objWorldDominanceObserver = new WorldDominanceObserver();
		
		objRandomPlayer = new RandomPlayer();
		
		objPhaseVIewObserver = new GamePhaseVIewObserver();
		
		obj.addObserver(objWorldDominanceObserver);
		
		objRandomPlayer.addObserver(objPhaseVIewObserver);
		
		
		
	}
	
	MapEditorInterface objMapEditorInterface = new MapEditorInterface();
	MapLoader objMapLoader = new MapLoader();

	public static String pathMap;

	Button mapEditor;
	Button startGame;
	Button loadGame;

	 Image logo = new Image(StartUpPhase.logoPath);
	 ImageView imgView = new ImageView();

	public ArrayList<String> path;

	/**
	 * This method invokes the initial stage.
	 * 
	 * @throws Exception
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {

		//risk game main interface
		File dir = new File("resources/maps");

		path = new ArrayList<String>(Arrays.asList(dir.list()));

		mapEditor = new Button("Map Editor");
		startGame = new Button("Start Game");
		loadGame  = new Button("load Game");

		Text selectmap = new Text("Select Maps: ");

		// Setting up combo box for selecting maps
		ObservableList<String> maplist = FXCollections.observableArrayList(path);
		ComboBox<String> maplist2 = new ComboBox<String>(maplist);
		maplist2.setVisible(true);
		maplist2.setMinWidth(200);

		VBox vbox = new VBox();
		vbox.getStyleClass().add("vbox");
		vbox.getChildren().addAll(imgView,selectmap, maplist2, mapEditor, startGame,loadGame);

		Group root = new Group();

		root.getChildren().add(vbox);
		Scene scene = new Scene(root, 600, 500);

		scene.getStylesheets().add(StartUpPhase.css);

		primaryStage.setTitle("Risk Game");
		primaryStage.setScene(scene);
		primaryStage.sizeToScene();
		primaryStage.centerOnScreen();
		primaryStage.show();

		//game starting method on startGame button click
		startGame.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				pathMap = maplist2.getValue();			
				MapLoader.loadMap(pathMap);
				primaryStage.close();
				MapEditorInterface.continentEditorStage.close();
				MapEditorInterface.countryEditorStage.close();
				MapEditorInterface.mapEditorStage.close();
				if(MapLoader.connectedCountry() == false){
					System.out.println("It is an unconnected map.");
					primaryStage.close();
				}else if(MapLoader.unconnectedContinent() == false){
					System.out.println("Map has unconnected continent.");
					primaryStage.close();
				}else{
					obj.gamePlay();
				}				
			}
		});

		//event handler on load game button
		loadGame.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				try {
					HumanPlayer objHumanPlayer = new HumanPlayer();
					primaryStage.close();
					MapEditorInterface.continentEditorStage.close();
					MapEditorInterface.countryEditorStage.close();
					MapEditorInterface.mapEditorStage.close();
					SaveData data = (SaveData) ResourceManager.load("Resources/data.txt");
					
					//loading the values into exciting data structures.
					StartUpPhase.currentPlayer = data.currentPlayer;
					StartUpPhase.noOfPlayer =data.noOfPlayers;
					StartUpPhase.players  = data.players;
					StartUpPhase.countriesArmies = data.countriesArmies;
					StartUpPhase.initialPlayerCountry = data.playerCountries;
					
					MapLoader.countryFilter = data.countryFilter;
					MapLoader.continentCountries = data.continentCountries;
					MapLoader.countryContinent = data.countryContinent;
					MapLoader.continentFilterNew = data.continentFilterNew;
					MapLoader.continentValue = data.continentValue;
					MapLoader.valueList = data.valueList;
					MapLoader.adj = data.adjacent;
					MapLoader.countryCoordinates = data.countryCoordinates;
					
					String nextMethod = data.nextMethod;
					if(nextMethod.equalsIgnoreCase("attackphase")){
						objHumanPlayer.attackPhase(StartUpPhase.currentPlayer);
					}else if(nextMethod.equalsIgnoreCase("fortifyPhase")){
						objHumanPlayer.fortifyPhase(StartUpPhase.currentPlayer);
					}
					
					
				} catch (Exception e) {
					
					e.printStackTrace();
				}
				
			}
		});
		
		//launching map editor by clicking on mapEditor button
		mapEditor.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				pathMap = maplist2.getValue();
				MapLoader.loadMap(pathMap);
				if(MapLoader.connectedCountry() == false){
					System.out.println("It is an unconnected map.");
					primaryStage.close();
				}else if(MapLoader.unconnectedContinent() == false){
					System.out.println("Map has unconnected continent.");
					primaryStage.close();
				}
				else{
					objMapEditorInterface.mapEditorInterface();
				}
				
			}
		});
	}

	/**
	 * This method launches the main method
	 */

	public static void main(String[] args){
		launch(args);		
	
	}
}