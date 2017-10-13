package mapEditor_Ishan;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class secondScreen extends Application{
	
	public void start(Stage primaryStage) throws FileNotFoundException
	{
		
		//Reading or creating image file from folder
		FileInputStream inputstream = new FileInputStream("C:/Users/ishan/workspace/RiskGame/RiskLogo.png");
		Image img = new Image(inputstream);
		
		//Setting image view
		ImageView view = new ImageView(img);
		view.setX(200.0);
		view.setY(200.0);
		view.setFitHeight(200); 
	    view.setFitWidth(300);
	    view.setPreserveRatio(true);
		
		//Setting up text
		Text text1 = new Text("Number of Maps: ");
	
		//Setting up dropdown boxfor number of players
		ObservableList<String> numOfMaps = FXCollections.observableArrayList("Map1","Map2");
		ComboBox box1 = new ComboBox(numOfMaps);
		
		//Setting up button for starting game
		Button button1 = new Button("Start Game");
		
		//Creating event handler
		EventHandler<MouseEvent> eventhandler1 = new EventHandler<MouseEvent>()
				{
					@Override
					public void handle(MouseEvent event) {
						// TODO Auto-generated method stub	
						
						//Reading or creating image file from folder
						FileInputStream inputstream1 = null;
						try {
							inputstream1 = new FileInputStream("C:/Users/ishan/workspace/RiskGame/RiskLogo.png");
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						Image img1 = new Image(inputstream1);
						
						//Setting image view
						ImageView view1 = new ImageView(img1);
						view1.setX(200.0);
						view1.setY(200.0);
						view1.setFitHeight(200); 
					    view1.setFitWidth(300);
					    view1.setPreserveRatio(true);
						
					    //Setting text
					    Text text2 = new Text("Number of Players: ");
					    
					    Button button2 = new Button("Number of Players");
						
						GridPane grid2 = new GridPane();
						grid2.setMinSize(700, 800);
						grid2.setVgap(6);
						grid2.setHgap(6);
						grid2.setAlignment(Pos.CENTER);
						//grid2.add(text2, 0, 0);
						grid2.add(button2, 0, 0);
						
					Group root1 = new Group();
					ObservableList list1 = root1.getChildren();
					list1.add(view1);
					list1.add(grid2);
						
						Scene scene2 = new Scene(root1,800,900);
						primaryStage.setTitle("Risk Game");
						primaryStage.setScene(scene2);
						primaryStage.show();
						
					}
				};
				
				//Register event 
				button1.addEventFilter(MouseEvent.MOUSE_CLICKED, eventhandler1);
		
		//Setting up grid layout for screen
		GridPane grid1 = new GridPane();
		grid1.setMinSize(700, 800);
		grid1.setVgap(6);
		grid1.setHgap(6);
		grid1.setAlignment(Pos.CENTER);
		grid1.add(text1, 0, 1);
		grid1.add(box1, 1, 1);
		grid1.add(button1, 1, 2);
		
		//Setting up the group
		Group root = new Group();
		ObservableList list = root.getChildren();
		
		list.add(view);

		list.add(grid1);
		
		Scene scene1 = new Scene(root,800,900);
		primaryStage.setTitle("Risk Game");
		primaryStage.setScene(scene1);
		primaryStage.show();
		
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		launch(args);

	}

}
