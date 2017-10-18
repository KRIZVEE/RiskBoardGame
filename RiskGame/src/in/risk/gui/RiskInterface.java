package in.risk.gui;
	
import in.risk.utility.RiskGame;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class RiskInterface extends Application {
    
    
	
	MapEditorInterface objMapEditorInterface = new MapEditorInterface();
	GamePhaseInterface objGamePhaseInterface = new GamePhaseInterface();

	Button mapEditor ;
	Button addPlayer ;
	Button startGame ;
	
	Image logo  = new Image(RiskGame.logoPath);
	ImageView imgView = new ImageView();
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		mapEditor = new Button("Map Editor");
		addPlayer = new Button("Add Players");
		startGame  = new Button("Start Game");
		
		imgView.setImage(logo);
		imgView.setFitWidth(300);
		imgView.setPreserveRatio(true);
		imgView.setSmooth(true);
		imgView.setCache(true);
		
		VBox vbox = new VBox();
		vbox.getStyleClass().add("vbox");
		vbox.getChildren().addAll(imgView,mapEditor,addPlayer,startGame);
		
		
		Group root = new Group();
		root.getChildren().add(vbox);
		Scene scene = new Scene(root,600,500);
		
		scene.getStylesheets().add(RiskGame.css);
	
		primaryStage.setTitle("Risk Game");
		primaryStage.setScene(scene);
		primaryStage.sizeToScene();
		primaryStage.centerOnScreen();
		primaryStage.show();
		
		startGame.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				objGamePhaseInterface.gamePhaseInteface();
				primaryStage.close();
				MapEditorInterface.continentEditorStage.close();
				MapEditorInterface.countryEditorStage.close();
				MapEditorInterface.mapEditorStage.close();
			}
		});
		
		mapEditor.setOnAction(new EventHandler<ActionEvent>() {			
			@Override
			public void handle(ActionEvent event) {
				
				objMapEditorInterface.mapEditorInterface();
			}
		});
	}	
	
	public static void main(String[] args) {
		RiskGame objRiskGame =  new RiskGame();
		launch(args);
		
	}
}

