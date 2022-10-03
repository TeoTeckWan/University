import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Lab extends Facilities{
	private int cost;
	
	//Constructor
	public Lab() {
		
	}
	public Lab(String name, int cost) {
		super(name);
		this.cost = cost;
	}
	
	//Getter and Setter
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}

	Scanner input = new Scanner(System.in);

	@Override
	public void newRecord(Facilities[] lab, int[] labSize, BorderPane mainPane) {
		//Create a Grid Pane to contain form
		GridPane labForm = new GridPane();
		labForm.setAlignment(Pos.CENTER);
		labForm.setHgap(10);
		labForm.setVgap(10);
		labForm.setPadding(new Insets(5));
		
		//Create the elements and put them into Medical Form
		Text formTitle = new Text("Add New Record");
		formTitle.setFont(Font.font("serif", FontWeight.BOLD, 28));
		labForm.add(formTitle, 0, 0, 2, 1);
		GridPane.setHalignment(formTitle, HPos.CENTER);

		Label labName = new Label("Lab Name:");
		labName.setFont(Font.font("serif", 20));
		TextField labNameInput = new TextField();
		labForm.add(labName, 0, 1);
		labForm.add(labNameInput, 1, 1);
		
		Label labCost = new Label("Lab Cost:");
		labCost.setFont(Font.font("serif", 20));
		TextField labCostInput = new TextField();
		labForm.add(labCost, 0, 2);
		labForm.add(labCostInput, 1, 2);
		
		Button labAddBtn = new Button("Add");
		labForm.add(labAddBtn, 1, 3);
		
		Button labResetBtn = new Button("Reset");
		labForm.add(labResetBtn, 1, 4);
		
		//New Record Message
		Alert success = new Alert(AlertType.INFORMATION);
		success.setContentText("New Record Added!");
		
		
		//Add Button On-Click Event Handling
		labAddBtn.setOnAction(ef -> {
			//Check whether is a valid input
			Alert a = new Alert(AlertType.WARNING);
			a.getDialogPane().setPrefSize(340, 200);

			try {
				if(labNameInput.getText().trim().isEmpty()) {
					a.setContentText("Please Fill In The Name!");
					a.show();
				}
				else {
					//Set the value inputed from the user
					lab[labSize[4]]= new Lab(labNameInput.getText(), Integer.parseInt(labCostInput.getText()));
					
					//Clear the text field area
					labNameInput.setText("");
					labCostInput.setText("");
					labSize[4]++;
					success.show();
				}
			}
			catch(NumberFormatException event) {
				a.setContentText("Invalid Lab's Cost.\nPlease Insert Integer Number.");
				a.show();		
				labCostInput.setText("");
			}
		});

		
		//Reset Button On-Click Event Handling
		labResetBtn.setOnAction(el3 -> {
			//Clear the text field area
			labNameInput.setText("");
			labCostInput.setText("");
		});
		
		mainPane.setCenter(labForm);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void showInfo(Facilities[] lab, BorderPane mainPane) {
		//Convert Array into ArrayList for displaying the table
		ArrayList<Facilities> labList = new ArrayList<Facilities>(Arrays.asList(lab));
		ObservableList<Facilities> laboratories = FXCollections.observableArrayList(labList);
		
		//Create Table
		TableView<Facilities> lTable = new TableView<>();
		lTable.setEditable(false);
		
		//Create Table Columns
		TableColumn lNameCol = new TableColumn("Name");
		lNameCol.setMinWidth(150);
		lNameCol.setCellValueFactory(new PropertyValueFactory<Facilities, String>("name"));
		lNameCol.setStyle( "-fx-alignment: CENTER;");
		
		TableColumn lCostCol = new TableColumn("Cost");
		lCostCol.setMinWidth(100);
		lCostCol.setCellValueFactory(new PropertyValueFactory<Facilities, String>("cost"));
		lCostCol.setStyle( "-fx-alignment: CENTER;");
		
		lTable.getColumns().addAll(lNameCol, lCostCol);
		lTable.setItems(laboratories);

		//Create vertical box to contain the whole table
		VBox lTableBox = new VBox();
		lTableBox.setPadding(new Insets(5));
		lTableBox.setSpacing(5);
		lTableBox.getChildren().add(lTable);
		
		mainPane.setCenter(lTableBox);
	}
	
	@Override
	public boolean search(String search) {
		if(super.getName().equalsIgnoreCase(search)) {
			return true;
		}
		return false;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void searchGUI(Facilities[] lab, int[] labStorage, int[] labSize, BorderPane mainPane) {
		//Search Bar
		Label searchlabel = new Label("Search Record");
		TextField search = new TextField();
		search.setPrefColumnCount(30);
		search.setPromptText("Search in 'Name' and Press Enter.");
		HBox searchbar = new HBox(5);
		searchbar.getChildren().addAll(searchlabel, search);

		//Create Table
		TableView<Facilities> lTable = new TableView<>();
		lTable.setEditable(false);
		
		//Create Table Columns
		TableColumn lNameCol = new TableColumn("Name");
		lNameCol.setMinWidth(150);
		lNameCol.setCellValueFactory(new PropertyValueFactory<Facilities, String>("name"));
		lNameCol.setStyle( "-fx-alignment: CENTER;");
		
		TableColumn lCostCol = new TableColumn("Cost");
		lCostCol.setMinWidth(100);
		lCostCol.setCellValueFactory(new PropertyValueFactory<Facilities, String>("cost"));
		lCostCol.setStyle( "-fx-alignment: CENTER;");
		
		lTable.getColumns().addAll(lNameCol, lCostCol);

		//Create a vertical box to contains search bar & table
		VBox lTableBox = new VBox();
		lTableBox.setPadding(new Insets(5));
		lTableBox.setSpacing(5);
		lTableBox.getChildren().addAll(searchbar, lTable);
		
		mainPane.setCenter(lTableBox);
		
		//SearchBtn Key Event Handling
		search.setOnKeyPressed(e3 -> {
			switch(e3.getCode()) {
			case ENTER: 
				String searchInput = search.getText();
				int count = 0;
				Facilities[] labSearch = new Facilities[labStorage[4]];
				
				for(int i = 0; i < labSize[4]; i++) {
					if(lab[i].search(searchInput)) {
						labSearch[count] = new Lab(lab[i].getName(), ((Lab)lab[i]).getCost());
						count++;
					}
				}

				ArrayList<Facilities> searchList = new ArrayList<Facilities>(Arrays.asList(labSearch));
				ObservableList<Facilities> searchedLab = FXCollections.observableArrayList(searchList);
				lTable.setItems(searchedLab);
				break;
				
			default:
				break;
			}
		});
	}
}
