package in.risk.gui;
	
import in.risk.utility.Map;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class RiskInterface extends Application {
	
	Label lbl1;
	Button btn1;
	
	public void start(Stage primaryStage) throws Exception {
		try {
			
			lbl1 = new Label("Risk Game");
			btn1 =  new Button("START");
			
			btn1.setOnAction(new EventHandler<ActionEvent>() {
				
				
				public void handle(ActionEvent event) {
					Stage stage = new Stage();
					//buttons
					Button btn1 = new Button("PLAY");
					Button btn2 =  new Button("EDIT MAP");					
					// comboBox
					final ComboBox<String> comboBox = new ComboBox<String>();
					comboBox.getItems().addAll(
							"Map 1","Map2"
							);
					comboBox.setValue("select a map");
					
					//placing of buttons and comboBox
					HBox hbox = new HBox();
					hbox.getChildren().addAll(btn1,btn2);
					
					VBox vbox = new VBox();
					vbox.getChildren().addAll(comboBox,hbox);
					Group root = new Group();
					root.getChildren().addAll(vbox);
					Scene scene = new Scene(root,500,500);
					
					stage.setTitle("Map Selector");
					stage.setScene(scene);
					stage.show();
					primaryStage.close();
					
				}
			});
			
			Group root = new Group();
			
			// vertical box
			VBox vbox = new VBox();
			vbox.getChildren().addAll(lbl1,btn1); 
			root.getChildren().addAll(vbox);
			
			//scene 
			Scene scene = new Scene(root,500,500);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			//Stage configuration
			primaryStage.setTitle("Risk 1.0");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		//launch(args);
		Map demo = new Map();
		demo.loadMap();
	}
}

/*Image img = new Image("file:///E:/workSpace/RiskFX/map.jpg");
ImageView imageView = new ImageView(img);
imageView.setPickOnBounds(true);	
imageView.setOnMouseClicked((MouseEvent e) ->{	
	System.out.println("clicked..!!");
});

root.getChildren().add(imageView);*/
