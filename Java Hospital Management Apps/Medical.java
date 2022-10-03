import java.util.ArrayList;
import java.util.Arrays;

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

public class Medical {
	private String name, manufacturer, expiryDate;
	private int cost, count;

	//Constructor
	public Medical() {
		
	}
	public Medical(String name, String manufacturer, String expiryDate, int cost, int count) {
		this.name = name;
		this.manufacturer = manufacturer;
		this.expiryDate = expiryDate;
		this.cost = cost;
		this.count = count;
	}
	
	//Getter and Setter
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	
	public String getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
	
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	

	public void newMedical(Medical[] medicals, int[] medicalSize, BorderPane mainPane) {
		//Create a Grid Pane to contain form
		GridPane medicalForm = new GridPane();
		medicalForm.setAlignment(Pos.CENTER);
		medicalForm.setHgap(10);
		medicalForm.setVgap(10);
		medicalForm.setPadding(new Insets(5));
		
		//Create the elements and put them into Medical Form
		Text formTitle = new Text("Add New Record");
		formTitle.setFont(Font.font("serif", FontWeight.BOLD, 28));
		medicalForm.add(formTitle, 0, 0, 2, 1);
		GridPane.setHalignment(formTitle, HPos.CENTER);

		Label medicalName = new Label("Medical Name:");
		medicalName.setFont(Font.font("serif", 20));
		TextField medicalNameInput = new TextField();
		medicalForm.add(medicalName, 0, 1);
		medicalForm.add(medicalNameInput, 1, 1);
		
		Label manufacturer = new Label("Manufacturer:");
		manufacturer.setFont(Font.font("serif", 20));
		TextField manufacturerInput = new TextField();
		medicalForm.add(manufacturer, 0, 2);
		medicalForm.add(manufacturerInput, 1, 2);
		
		Label expiryDate = new Label("Expiry Date:");
		expiryDate.setFont(Font.font("serif", 20));
		TextField expiryDateInput = new TextField();
		medicalForm.add(expiryDate, 0, 3);
		medicalForm.add(expiryDateInput, 1, 3);
		
		Label cost = new Label("Cost:");
		cost.setFont(Font.font("serif", 20));
		TextField costInput = new TextField();
		medicalForm.add(cost, 0, 4);
		medicalForm.add(costInput, 1, 4);
		
		Label count = new Label("Count:");
		count.setFont(Font.font("serif", 20));
		TextField countInput = new TextField();
		medicalForm.add(count, 0, 5);
		medicalForm.add(countInput, 1, 5);
		
		Button medicalAddBtn = new Button("Add");
		medicalForm.add(medicalAddBtn, 1, 7);
		
		Button medicalResetBtn = new Button("Reset");
		medicalForm.add(medicalResetBtn, 1, 8);
		
		//New Record Message
		Alert success = new Alert(AlertType.INFORMATION);
		success.setContentText("New Record Added!");
		
		
		//Add Button On-Click Event Handling
		medicalAddBtn.setOnAction(e3 -> {
			//Check whether is a valid input
			Alert a = new Alert(AlertType.WARNING);
			a.getDialogPane().setPrefSize(340, 200);
			boolean valid = false;
			try {
				Integer.parseInt(costInput.getText());
				valid = true;
				
				for(int i = 0; i < medicalSize[3]; i ++) {
					if(medicals[i].getName().equalsIgnoreCase(medicalNameInput.getText())) {
						valid = false;
					}
				}
				if(valid == false) {
					a.setContentText("The Name Already Exist!\nPlease Fill In Again!");
					a.show();
					medicalNameInput.setText("");
				}
				else if(medicalNameInput.getText().trim().isEmpty() || manufacturerInput.getText().trim().isEmpty() || 
				expiryDateInput.getText().trim().isEmpty()) {
					a.setContentText("Please Fill In The Form!");
					a.show();
				}
				else {
					//Set the value inputed from the user
					medicals[medicalSize[3]]= new Medical(medicalNameInput.getText(), 
														   manufacturerInput.getText(),
														   expiryDateInput.getText(),
														   Integer.parseInt(costInput.getText()),
														   Integer.parseInt(countInput.getText()));
					
					//Clear the text field area
					medicalNameInput.setText("");
					manufacturerInput.setText("");
					expiryDateInput.setText("");
					costInput.setText("");
					countInput.setText("");
					medicalSize[3]++;
					success.show();
				}
			}
			catch(NumberFormatException event) {
				if (!valid){ //Check the id
					a.setContentText("Invalid Medical's Cost.\nPlease Insert Integer Number.");
					a.show();		
					costInput.setText("");
					
					for(int i = 0; i < countInput.getText().length(); i++) { //Check the id and room at the same time
						if(Character.isAlphabetic((countInput.getText().charAt(i)))) {
							a.setContentText("Invalid Medical's Count and Cost.\nPlease Insert Integer Number.");
							a.show();
							countInput.setText("");	
							break;
						}
					}
				}
				else {
					a.setContentText("Invalid Medical's Count.\nPlease Insert Integer Number.");
					a.show();
					countInput.setText("");
				}
			}
		});
		
		//Reset Button On-Click Event Handling
		medicalResetBtn.setOnAction(e3 -> {
			//Clear the text field area
			medicalNameInput.setText("");
			manufacturerInput.setText("");
			expiryDateInput.setText("");
			costInput.setText("");
			countInput.setText("");
		});
		
		mainPane.setCenter(medicalForm);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void findMedical(Medical[] medicals, BorderPane mainPane) {
		//Convert Array into ArrayList for displaying the table
		ArrayList<Medical> medicalList = new ArrayList<Medical>(Arrays.asList(medicals));
		ObservableList<Medical> medical = FXCollections.observableArrayList(medicalList);
		
		//Create Table
		TableView<Medical> mTable = new TableView<>();
		mTable.setEditable(false);
		
		//Create Table Columns
		TableColumn mNameCol = new TableColumn("Name");
		mNameCol.setMinWidth(150);
		mNameCol.setCellValueFactory(new PropertyValueFactory<Medical, String>("name"));
		mNameCol.setStyle( "-fx-alignment: CENTER;");

		TableColumn mManufacturerCol = new TableColumn("Manufacturer");
		mManufacturerCol.setMinWidth(150);
		mManufacturerCol.setCellValueFactory(new PropertyValueFactory<Medical, String>("manufacturer"));
		mManufacturerCol.setStyle( "-fx-alignment: CENTER;");
		
		TableColumn mExpiredCol = new TableColumn("Expiry Date");
		mExpiredCol.setMinWidth(150);
		mExpiredCol.setCellValueFactory(new PropertyValueFactory<Medical, String>("expiryDate"));
		mExpiredCol.setStyle( "-fx-alignment: CENTER;");
		
		TableColumn mCostCol = new TableColumn("Cost");
		mCostCol.setMinWidth(100);
		mCostCol.setCellValueFactory(new PropertyValueFactory<Medical, Integer>("cost"));
		mCostCol.setStyle( "-fx-alignment: CENTER;");
		
		TableColumn mCountCol = new TableColumn("Count");
		mCountCol.setMinWidth(100);
		mCountCol.setCellValueFactory(new PropertyValueFactory<Medical, Integer>("count"));
		mCountCol.setStyle( "-fx-alignment: CENTER;");
		
		mTable.getColumns().addAll(mNameCol, mManufacturerCol, mExpiredCol, mCostCol, mCountCol);
		mTable.setItems(medical);

		//Create vertical box to contain the whole table
		VBox mTableBox = new VBox();
		mTableBox.setPadding(new Insets(5));
		mTableBox.setSpacing(5);
		mTableBox.getChildren().add(mTable);
		
		mainPane.setCenter(mTableBox);
	}
	
	public boolean search(String search) {
		if(name.equalsIgnoreCase(search)) {
			return true;
		}
		else if(manufacturer.equalsIgnoreCase(search)) {
			return true;
		}
		return false;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void searchGUI(Medical[] medicals, int[] medicalStorage, int[] medicalSize, BorderPane mainPane) {
		//Search Bar
		Label searchlabel = new Label("Search Record");
		TextField search = new TextField();
		search.setPrefColumnCount(30);
		search.setPromptText("Search in 'Name, Manufacturer' and Press Enter.");
		HBox searchbar = new HBox(5);
		searchbar.getChildren().addAll(searchlabel, search);

		//Create Table
		TableView<Medical> mTable = new TableView<>();
		mTable.setEditable(false);
		
		//Create Table Columns
		TableColumn mNameCol = new TableColumn("Name");
		mNameCol.setMinWidth(150);
		mNameCol.setCellValueFactory(new PropertyValueFactory<Medical, String>("name"));
		mNameCol.setStyle( "-fx-alignment: CENTER;");

		TableColumn mManufacturerCol = new TableColumn("Manufacturer");
		mManufacturerCol.setMinWidth(150);
		mManufacturerCol.setCellValueFactory(new PropertyValueFactory<Medical, String>("manufacturer"));
		mManufacturerCol.setStyle( "-fx-alignment: CENTER;");
		
		TableColumn mExpiredCol = new TableColumn("Expiry Date");
		mExpiredCol.setMinWidth(150);
		mExpiredCol.setCellValueFactory(new PropertyValueFactory<Medical, String>("expiryDate"));
		mExpiredCol.setStyle( "-fx-alignment: CENTER;");
		
		TableColumn mCostCol = new TableColumn("Cost");
		mCostCol.setMinWidth(100);
		mCostCol.setCellValueFactory(new PropertyValueFactory<Medical, Integer>("cost"));
		mCostCol.setStyle( "-fx-alignment: CENTER;");
		
		TableColumn mCountCol = new TableColumn("Count");
		mCountCol.setMinWidth(100);
		mCountCol.setCellValueFactory(new PropertyValueFactory<Medical, Integer>("count"));
		mCountCol.setStyle( "-fx-alignment: CENTER;");
		
		mTable.getColumns().addAll(mNameCol, mManufacturerCol, mExpiredCol, mCostCol, mCountCol);

		//Create a vertical box to contains search bar & table
		VBox mTableBox = new VBox();
		mTableBox.setPadding(new Insets(5));
		mTableBox.setSpacing(5);
		mTableBox.getChildren().addAll(searchbar,mTable);
		
		mainPane.setCenter(mTableBox);
		
		//SearchBtn Key Event Handling
		search.setOnKeyPressed(e3 -> {
			switch(e3.getCode()) {
			case ENTER: 
				String searchInput = search.getText();
				int count = 0;
				Medical[] medicalsSearch = new Medical[medicalStorage[3]];
				for(int i = 0; i < medicalSize[3]; i++) {
					if(medicals[i].search(searchInput)) {
						medicalsSearch[count] = new Medical(medicals[i].getName(), medicals[i].getManufacturer(),
								medicals[i].getExpiryDate(), medicals[i].getCost(), medicals[i].getCount());
						
						count++;
					}
				}

				ArrayList<Medical> searchList = new ArrayList<Medical>(Arrays.asList(medicalsSearch));
				ObservableList<Medical> searchedMedical = FXCollections.observableArrayList(searchList);
				mTable.setItems(searchedMedical);
				break;
				
			default:
				break;
			}
		});
	}
}
