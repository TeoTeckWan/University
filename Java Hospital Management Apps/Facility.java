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

public class Facility extends Facilities {

	//Constructor
	public Facility() {
		
	}
	public Facility(String name) {
		super(name);
	}
	
	Scanner input = new Scanner(System.in);
	
	@Override
	public void newRecord(Facilities[] facilities, int[] facilitySize, BorderPane mainPane) {
		//Create a Grid Pane to contain form
		GridPane facilityForm = new GridPane();
		facilityForm.setAlignment(Pos.CENTER);
		facilityForm.setHgap(10);
		facilityForm.setVgap(10);
		facilityForm.setPadding(new Insets(5));
		
		//Create the elements and put them into Medical Form
		Text formTitle = new Text("Add New Record");
		formTitle.setFont(Font.font("serif", FontWeight.BOLD, 28));
		facilityForm.add(formTitle, 0, 0, 2, 1);
		GridPane.setHalignment(formTitle, HPos.CENTER);

		Label facilityName = new Label("Facility Name:");
		facilityName.setFont(Font.font("serif", 20));
		TextField facilityNameInput = new TextField();
		facilityForm.add(facilityName, 0, 1);
		facilityForm.add(facilityNameInput, 1, 1);
		
		
		Button facilityAddBtn = new Button("Add");
		facilityForm.add(facilityAddBtn, 1, 2);
		
		Button facilityResetBtn = new Button("Reset");
		facilityForm.add(facilityResetBtn, 1, 3);
		
		//New Record Message
		Alert success = new Alert(AlertType.INFORMATION);
		success.setContentText("New Record Added!");
		
		
		//Add Button On-Click Event Handling
		facilityAddBtn.setOnAction(ef -> {
			//Check whether is a valid input
			Alert a = new Alert(AlertType.WARNING);
			a.getDialogPane().setPrefSize(340, 200);

			if(facilityNameInput.getText().trim().isEmpty()) {
				a.setContentText("Please Fill In The Name!");
				a.show();
			}
			else {
				//Set the value inputed from the user
				facilities[facilitySize[5]]= new Facility(facilityNameInput.getText());
				
				//Clear the text field area
				facilityNameInput.setText("");
				facilitySize[5]++;
				success.show();
			}
		});

		
		//Reset Button On-Click Event Handling
		facilityResetBtn.setOnAction(ef3 -> {
			//Clear the text field area
			facilityNameInput.setText("");
		});
		
		mainPane.setCenter(facilityForm);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void showInfo(Facilities[] faciliti, BorderPane mainPane) {
		//Convert Array into ArrayList for displaying the table
		ArrayList<Facilities> facilityList = new ArrayList<Facilities>(Arrays.asList(faciliti));
		ObservableList<Facilities> facility = FXCollections.observableArrayList(facilityList);
		
		//Create Table
		TableView<Facilities> fTable = new TableView<>();
		fTable.setEditable(false);
		
		//Create Table Columns
		TableColumn fNameCol = new TableColumn("Name");
		fNameCol.setMinWidth(150);
		fNameCol.setCellValueFactory(new PropertyValueFactory<Facilities, String>("name"));
		fNameCol.setStyle( "-fx-alignment: CENTER;");

		
		fTable.getColumns().add(fNameCol);
		fTable.setItems(facility);

		//Create vertical box to contain the whole table
		VBox fTableBox = new VBox();
		fTableBox.setPadding(new Insets(5));
		fTableBox.setSpacing(5);
		fTableBox.getChildren().add(fTable);
		
		mainPane.setCenter(fTableBox);
	}
	
	@Override
	public boolean search(String search) {
		if(super.getName().equalsIgnoreCase(search)) {
			return true;
		}
		return false;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void searchGUI(Facilities[] facilities, int[] facilitiesStorage, int[] facilitiesSize, BorderPane mainPane) {
		//Search Bar
		Label searchlabel = new Label("Search Record");
		
		TextField search = new TextField();
		search.setPrefColumnCount(30);
		search.setPromptText("Search in 'Name' and Press Enter.");
		
		HBox searchbar = new HBox(5);
		searchbar.getChildren().addAll(searchlabel, search);

		
		//Create Table
		TableView<Facilities> fTable = new TableView<>();
		fTable.setEditable(false);
		
		//Create Table Columns
		TableColumn fNameCol = new TableColumn("Name");
		fNameCol.setMinWidth(150);
		fNameCol.setCellValueFactory(new PropertyValueFactory<Facilities, String>("name"));
		fNameCol.setStyle( "-fx-alignment: CENTER;");
		
		fTable.getColumns().addAll(fNameCol);

		//Create a vertical box to contains search bar & table
		VBox fTableBox = new VBox();
		fTableBox.setPadding(new Insets(5));
		fTableBox.setSpacing(5);
		fTableBox.getChildren().addAll(searchbar, fTable);
		
		mainPane.setCenter(fTableBox);
		
		//SearchBtn Key Event Handling
		search.setOnKeyPressed(e3 -> {
			switch(e3.getCode()) {
			case ENTER: 
				String searchInput = search.getText();
				int count = 0;
				Facilities[] facilitiesSearch = new Facilities[facilitiesStorage[5]];
				
				for(int i = 0; i < facilitiesSize[5]; i++) {
					if(facilities[i].search(searchInput)) {
						facilitiesSearch[count] = new Facility(facilities[i].getName());
						count++;
					}
				}

				ArrayList<Facilities> searchList = new ArrayList<Facilities>(Arrays.asList(facilitiesSearch));
				ObservableList<Facilities> searchedFacility = FXCollections.observableArrayList(searchList);
				fTable.setItems(searchedFacility);
				break;
				
			default:
				break;
			}
		});
	}
}
