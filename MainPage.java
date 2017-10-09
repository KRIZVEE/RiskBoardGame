package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class MainPage extends Application {
	@Override
	public void start(Stage primaryStage) throws FileNotFoundException {

		// Setting title to the Stage
		primaryStage.setTitle("Risk Board Game");

		// Passing FileInputStream object as a parameter
		// C:\Users\Kashif_Rizvee\Desktop\Risk\RiskLogo.png
		FileInputStream inputstream = new FileInputStream("C:/Users/Kashif_Rizvee/Desktop/Risk/RiskLogo.png");
		Image image = new Image(inputstream);

		// Loading image from URL
		// Image image = new Image(new FileInputStream("url for the image));

		ImageView view1 = new ImageView(image);
		view1.setX(200.0);
		view1.setY(200.0);
		view1.setFitHeight(200);
		view1.setFitWidth(300);
		view1.setPreserveRatio(true);

		Button button1 = new Button("Select Map");
		Button button2 = new Button("Map Editor");
		Button button3 = new Button("Start Game");

		// Creating a Grid Pane
		GridPane gridPane = new GridPane();

		// Setting size for the pane
		gridPane.setMinSize(400, 300);

		// Setting the padding
		// gridPane.setPadding(new Insets(10, 10, 10, 10));

		// Setting the vertical and horizontal gaps between the columns
		gridPane.setVgap(6);
		gridPane.setHgap(6);

		// Setting the Grid alignment
		gridPane.setAlignment(Pos.CENTER);

		// Arranging all the nodes in the grid
		gridPane.add(view1, 0, 0);
		gridPane.add(button1, 0, 1);
		gridPane.add(button2, 0, 2);
		gridPane.add(button3, 0, 3);

		// Styling nodes
		button1.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;");
		button2.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;");
		button3.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;");

		button1.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("Hello World<-- Button 1");
			}
		});

		button2.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("Hello World!<-- Button 2");
			}
		});

		button3.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("Hello World!<-- Button 3");
			}
		});

		gridPane.setStyle("-fx-background-color: BEIGE;");

		javafx.scene.Group root = new javafx.scene.Group();
		// ObservableList list = root.getChildren();
		// list.add(view1);
		//
		// list.add(gridPane);

		// Creating a sene object
		Scene scene1 = new Scene(gridPane);
		// Scene scene2 = new Scene(view1);

		// Adding scene to the stage
		primaryStage.setScene(scene1);

		// Displaying the contents of the stage
		primaryStage.show();

	}

	private void setScene(Scene scene) {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) {
		launch(args);
	}
}
