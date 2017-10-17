package in.risk.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import in.risk.utility.RiskGame;
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
	
	static RiskGame objRiskGame = new RiskGame();
	
	public static List<String> editedContinents = new ArrayList<String>();
	public static List<String> editedCountries = new ArrayList<String>();
	
	public static HashMap<String, String> pairedContinentsCountries = new HashMap<String,String>();
	
	Button editContinents;
	Button editCountries;
	Button back;
	Button saveMap;
	
	public  void mapEditorInterface(){
		
		Stage mapEditorStage = new Stage();
		
		Image logo = new Image(RiskInterface.logoPath);
		ImageView imgview  = new ImageView(logo);
		
		editContinents = new Button("Edit Continents");
		
		editContinents.setOnAction(new EventHandler<ActionEvent>(){
			
			@Override
			public void handle(ActionEvent event){
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
		
		VBox vbox = new VBox();
		vbox.getStyleClass().add("vbox");
		HBox hbox = new HBox();
		hbox.getStyleClass().add("hbox");
		hbox.getChildren().addAll(editContinents,editCountries);
		vbox.getChildren().addAll(imgview,hbox,back);
		
		
		Group mapEdiorRoot = new Group();
		mapEdiorRoot.getChildren().addAll(vbox);
		Scene mapEditorScene = new Scene(mapEdiorRoot,500,400);
		mapEditorScene.getStylesheets().add(RiskInterface.css);
		mapEditorStage.setTitle("Risk Game: Map Editor");
		mapEditorStage.setScene(mapEditorScene);
		mapEditorStage.centerOnScreen();
		mapEditorStage.show();
		
		
	}
	
	public static void editContinentsInterface(){
				
		Stage continentEditorStage = new Stage();
		
		Image logo = new Image(RiskInterface.logoPath);
		ImageView imageView = new ImageView(logo);	
		
		Label lblAdd = new Label("Add a new continent: ");
		TextField fieldAdd = new TextField();
		fieldAdd.setPromptText("Enter Continent Name");
		Label lblDel = new Label("Delete a continent: ");
		Button add = new Button("Add");
		Button del = new Button("Delete");
		Button submit = new Button("Submit");
		
		ObservableList<String> continentsList = FXCollections.observableArrayList(objRiskGame.continentList);
		ComboBox<String> conListBox = new ComboBox<>(continentsList);
		conListBox.setPromptText("select Continent");
		
		HBox hbox1 = new HBox();
		hbox1.getStyleClass().add("hbox");
		hbox1.getChildren().addAll(lblAdd,fieldAdd,add);
		
		HBox hbox2 = new HBox();
		hbox2.getStyleClass().add("hbox");
		hbox2.getChildren().addAll(lblDel,conListBox,del);
		VBox vbox = new VBox();
		vbox.getStyleClass().add("vbox");
		vbox.getChildren().addAll(imageView,hbox1,hbox2,submit);
		Group continentEditorRoot  = new Group();
		continentEditorRoot.getChildren().addAll(vbox);
		
		add.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event){
				if(!fieldAdd.getText().isEmpty()){					
					continentsList.add(fieldAdd.getText());					
				}					
					fieldAdd.clear();
			}
		});
		
		del.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				continentsList.remove(conListBox.getValue());
				
			}
		});
		
		submit.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				
				for(int i = 0;i <continentsList.size();i++){
					editedContinents.add(continentsList.get(i));
				}
				continentEditorStage.close();
				
			}
		});
		
		Scene conEditorScene = new Scene(continentEditorRoot,500,400);
		conEditorScene.getStylesheets().add(RiskInterface.css);
		continentEditorStage.setTitle("Risk Game: Edit Continents");
		continentEditorStage.setScene(conEditorScene);
		continentEditorStage.centerOnScreen();
		continentEditorStage.show();		
		
	}
	
	public static void editCountriesInterface(){
		
		Stage countryEditorStage = new Stage();
		
		Image logo = new Image(RiskInterface.logoPath);
		ImageView imgview = new ImageView(logo);
		ObservableList<String> continentList;
		
		if(!editedContinents.isEmpty()){
			continentList = FXCollections.observableArrayList(editedContinents);
		}else
			continentList = FXCollections.observableArrayList(objRiskGame.continentList);
		
		ComboBox<String> continentBox =new ComboBox<String>(continentList);
		continentBox.setPromptText("Select Continent");
		
		ObservableList<String> countryList = FXCollections.observableArrayList(objRiskGame.countryList);
		ComboBox<String> countryBox = new ComboBox<String>(countryList);
		countryBox.setPromptText("Select Country");
		
		Label lblAdd = new Label("Add Country: ");
		TextField fieldAdd = new TextField(); 
		fieldAdd.setPromptText("Enter Country Name");
		Label lblDel  = new Label("Delete Country: ");
		
		Button add = new Button("Add");
		Button del = new Button("Delete");
		Button submit = new Button("Submit");
		
		HBox hbox1 = new HBox();
		hbox1.getStyleClass().add("hbox");
		hbox1.getChildren().addAll(lblAdd,fieldAdd,continentBox,add);
		
		HBox hbox2 = new HBox();
		hbox2.getStyleClass().add("hbox");
		hbox2.getChildren().addAll(lblDel,countryBox,del);
		
		VBox vbox = new VBox();
		vbox.getStyleClass().add("vbox");
		vbox.getChildren().addAll(imgview,hbox1,hbox2,submit);
		
		try{
		
		add.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				if(!fieldAdd.getText().isEmpty()){
						countryList.add(fieldAdd.getText());	
						pairedContinentsCountries.put(continentBox.getValue(),fieldAdd.getText());
				}
				fieldAdd.clear();
			}
		});
		
		del.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				countryList.remove(countryBox.getValue());
				
			}
		});
		
		submit.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				for(int i=0;i<countryList.size();i++){
					editedCountries.add(countryList.get(i));
				}
				countryEditorStage.close();
				
			}
		});
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		Group countryEditorRoot = new Group();
		countryEditorRoot.getChildren().addAll(vbox);
		Scene countryEditorscene = new Scene(countryEditorRoot,500,400);
		countryEditorscene.getStylesheets().add(RiskInterface.css);
		countryEditorStage.setTitle("Risk Game: Edit Countries");
		countryEditorStage.setScene(countryEditorscene);
		countryEditorStage.centerOnScreen();
		countryEditorStage.show();
		
	}

}
