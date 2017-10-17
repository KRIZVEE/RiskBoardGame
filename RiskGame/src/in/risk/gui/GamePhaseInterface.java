package in.risk.gui;

import in.risk.utility.RiskGame;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GamePhaseInterface {

	
	public void gamePhaseInteface()
	{
		
		Image logo = new Image(RiskGame.logoPath);
		ImageView imageView = new ImageView(logo);
		
		Stage gamePhaseStage = new Stage();
		RiskGame objRiskGame = new RiskGame();
		
		//Setting text labels for players
		Text player1 = new Text("Player 1");
		Text numofarmy = new Text("Number of Armies: ");
		
		Text player2 = new Text("Player 2");
		Text numofarmy2 = new Text("Number of Armies: ");
		
		Text player3 = new Text("Player 3");
		Text numofarmy3 = new Text("Number of Armies: ");
		Text phase = new Text("Phases: ");
		Text countrylistp1 = new Text("Country Owned by Player1: ");
		Text countrylistp2 = new Text("Country Owned by Player2: ");
		Text countrylistp3 = new Text("Country Owned by Player3: ");
		
		//setting up buttons for pahses of game
		Button reinforce = new Button("Reinforcement");
		Button attack = new Button("Attack");
		Button fortify = new Button("Fortify");
		
		//Setting up combo box
		ObservableList<String> countryplayer1 = FXCollections.observableArrayList(objRiskGame.continentList);
		ComboBox<String> countryp1 = new ComboBox<String>(countryplayer1);
		countryp1.setVisible(true);
		
		ObservableList<String> countryplayer2 = FXCollections.observableArrayList(objRiskGame.continentList);
		ComboBox<String> countryp2 = new ComboBox<String>(countryplayer2);
		countryp2.setVisible(true);
		
		ObservableList<String> countryplayer3 = FXCollections.observableArrayList(objRiskGame.continentList);
		ComboBox<String> countryp3 = new ComboBox<String>(countryplayer3);
		countryp3.setVisible(true);
		
		//setting up gridpanes
		GridPane pane1 = new GridPane();
		pane1.setMinSize(600, 400);
		pane1.setVgap(5);
		pane1.setHgap(5);
		pane1.setAlignment(Pos.CENTER);
		pane1.add(player1, 0, 0);
		pane1.add(numofarmy, 0, 1);
		pane1.add(countrylistp1, 2, 1);
		pane1.add(countryp1, 4, 1);
		pane1.add(player2, 0, 2);
		pane1.add(numofarmy2, 0, 3);
		pane1.add(countrylistp2, 2, 3);
		pane1.add(countryp2, 4, 3);
		pane1.add(player3, 0, 4);
		pane1.add(numofarmy3, 0, 5);
		pane1.add(countrylistp3, 2, 5);
		pane1.add(countryp3, 4, 5);
		pane1.add(phase, 0, 9);
		pane1.add(reinforce, 0, 10);
		pane1.add(attack, 0, 11);
		pane1.add(fortify, 0, 12);
		
		
		VBox  vBox = new VBox();
		vBox.getStyleClass().add("vbox");
		vBox.getChildren().addAll(imageView,pane1);
		
		//creating group
		Group root = new Group();		
		root.getChildren().add(vBox);
		
		//Creating a scene
		Scene gamePhaseScene = new Scene(root,600,600);
		gamePhaseStage.setTitle("Risk Game Phases");
		gamePhaseStage.setScene(gamePhaseScene);
		gamePhaseStage.centerOnScreen();
		gamePhaseStage.setOnHidden(e -> System.exit(1));
		gamePhaseStage.show();
		

	}
	
}
