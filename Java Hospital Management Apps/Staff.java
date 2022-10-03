import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Staff extends Person {
	private String designation;
	private int salary;
	
	//Constructor
	public Staff() {
		
	}
	public Staff(String id, String name, String sex, String designation, int salary) {
		super(id, name, sex);
		this.designation = designation;
		this.salary = salary;
	}
	
	//Getter and Setter
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	
	public int getSalary() {
		return salary;
	}
	public void setSalary(int salary) {
		this.salary = salary;
	}

	Scanner input = new Scanner(System.in);

	@Override
	public void newRecord(Person[] staffs, int[] staffSize, BorderPane mainPane) {
		//Create a GridPane to contain form
		GridPane staffForm = new GridPane();
		staffForm.setAlignment(Pos.CENTER);
		staffForm.setHgap(10);
		staffForm.setVgap(10);
		staffForm.setPadding(new Insets(5));
		
		//Create the elements and put them into Staff Form
		Text formTitle = new Text("Add New Record");
		formTitle.setFont(Font.font("Serif", FontWeight.BOLD, 28));
		staffForm.add(formTitle, 0, 0, 2, 1);
		GridPane.setHalignment(formTitle, HPos.CENTER);

		Label staffId = new Label("Staff ID:");
		staffId.setFont(Font.font("Serif", 20));
		TextField staffIdInput = new TextField();
		staffForm.add(staffId, 0, 1);
		staffForm.add(staffIdInput, 1, 1);
		
		Label staffName = new Label("Staff Name:");
		staffName.setFont(Font.font("Serif", 20));
		TextField staffNameInput = new TextField();
		staffForm.add(staffName, 0, 2);
		staffForm.add(staffNameInput, 1, 2);
		
		Label staffSex = new Label("Sex:");
		staffSex.setFont(Font.font("Serif", 20));
		RadioButton staffMale = new RadioButton("Male");
		RadioButton staffFemale = new RadioButton("Female");
		ToggleGroup staffSexGroup = new ToggleGroup();
		staffMale.setToggleGroup(staffSexGroup);
		staffFemale.setToggleGroup(staffSexGroup);
		staffForm.add(staffSex, 0, 3);
		HBox genderButtons = new HBox(40, staffMale, staffFemale);
		staffForm.add(genderButtons, 1, 3);
		
		Label staffDesignation = new Label("Designation:");
		staffDesignation.setFont(Font.font("Serif", 20));
		TextField staffDesignationInput = new TextField();
		staffForm.add(staffDesignation, 0, 4);
		staffForm.add(staffDesignationInput, 1, 4);
		
		Label staffSalary = new Label("Salary:");
		staffSalary.setFont(Font.font("Serif", 20));
		TextField staffSalaryInput = new TextField();
		staffForm.add(staffSalary, 0, 5);
		staffForm.add(staffSalaryInput, 1, 5);
		
		Button staffAddBtn = new Button("Add");
		staffForm.add(staffAddBtn, 1, 6);
		
		Button staffResetBtn = new Button("Reset");
		staffForm.add(staffResetBtn, 1, 7);
		
		//New Record Message
		Alert success = new Alert(AlertType.INFORMATION);
		success.setContentText("New Record Added!");
		
		
		//Add Button On-Click Event Handling
		staffAddBtn.setOnAction(e -> {
			boolean validId = false;
		
			//Check whether is a valid input
			Alert alert = new Alert(AlertType.WARNING);
			alert.getDialogPane().setPrefSize(340, 200);
			
			try {
				Integer.parseInt(staffIdInput.getText()); //Validation for digit ID
				validId = true;
				
				for(int i = 0; i < staffSize[2]; i ++) {
					if(staffs[i].getId().equals(staffIdInput.getText())) {
						validId = false;
					}
				}
				if(!validId) {
					alert.setContentText("The ID Already Exist!\nPlease Fill In Again!");
					alert.show();
					staffIdInput.setText("");
				}
				else if(staffIdInput.getText().length() != 3) {
					alert.setContentText("The Staff ID Must == 3 Digits!\nPlease Insert According To Format (xxx).");
					alert.show();
			       	staffIdInput.setText("");
				}
				else if(staffNameInput.getText().trim().isEmpty() || ((!staffMale.isSelected() && !staffFemale.isSelected()))
						|| staffDesignationInput.getText().trim().isEmpty()) {
					alert.setContentText("Please Complete The Form!");
					alert.show();
				}
				else {
					if(staffMale.isSelected()) {
						staffs[staffSize[2]] = new Staff(staffIdInput.getText(), staffNameInput.getText(), "Male", 
								staffDesignationInput.getText(), Integer.parseInt(staffSalaryInput.getText()));
					}
					else if(staffFemale.isSelected()) {
						staffs[staffSize[2]] = new Staff(staffIdInput.getText(), staffNameInput.getText(), "Female", 
								staffDesignationInput.getText(), Integer.parseInt(staffSalaryInput.getText()));
					}
					
					//Clear the text field area
					staffIdInput.setText("");
					staffNameInput.setText("");
					staffMale.setSelected(false);
					staffFemale.setSelected(false);
					staffDesignationInput.setText("");
					staffSalaryInput.setText("");
					staffSize[2]++;
					success.show();
				}
			}
			catch(NumberFormatException e1) {
				if (!validId){ //Check the id
					alert.setContentText("Invalid ID.\nPlease Insert Integer Number.");
					alert.show();		
					staffIdInput.setText("");
					
					for(int i = 0; i < staffSalaryInput.getText().length(); i++) { //Check the id and room at the same time
						if(Character.isAlphabetic(staffSalaryInput.getText().charAt(i))) {
							alert.setContentText("Invalid ID and Room No.\nPlease Insert Integer Number.");
							alert.show();
							staffSalaryInput.setText("");
							break;
						}
					}
				}
				else {
					alert.setContentText("Invalid Salary.\nPlease Insert Integer Number.");
					alert.show();
					staffSalaryInput.setText("");
				}
			}
		});
		
		//Reset Button On-Click Event Handling
		staffResetBtn.setOnAction(e4 -> {
			
			//Clear the text field area
			staffIdInput.setText("");
			staffNameInput.setText("");
			staffMale.setSelected(false);
			staffFemale.setSelected(false);
			staffDesignationInput.setText("");
			staffSalaryInput.setText("");
		});
		
		mainPane.setCenter(staffForm);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void showInfo(Person[] staffs, BorderPane mainPane) {
		//Convert Array into ArrayList for displaying the table
		ArrayList<Person> staffList = new ArrayList<Person>(Arrays.asList(staffs));
		ObservableList<Person> staff = FXCollections.observableArrayList(staffList);

		//Create Table
		TableView<Person> sTable = new TableView<>();
		sTable.setEditable(false);
		
		//Create Table Columns
		TableColumn sIDCol = new TableColumn("Staff ID");
		sIDCol.setMinWidth(100);
		sIDCol.setCellValueFactory(new PropertyValueFactory<Person, String>("id"));
		sIDCol.setStyle( "-fx-alignment: CENTER;");
		
		TableColumn sNameCol = new TableColumn("Name");
		sNameCol.setMinWidth(120);
		sNameCol.setCellValueFactory(new PropertyValueFactory<Person, String>("name"));
		sNameCol.setStyle( "-fx-alignment: CENTER;");

		TableColumn sSexCol = new TableColumn("Sex");
		sSexCol.setMinWidth(100);
		sSexCol.setCellValueFactory(new PropertyValueFactory<Person, String>("sex"));
		sSexCol.setStyle( "-fx-alignment: CENTER;");
		
		TableColumn sDesignationCol = new TableColumn("Designation");
		sDesignationCol.setMinWidth(120);
		sDesignationCol.setCellValueFactory(new PropertyValueFactory<Person, String>("designation"));
		sDesignationCol.setStyle( "-fx-alignment: CENTER;");
		
		TableColumn sSalaryCol = new TableColumn("Salary");
		sSalaryCol.setMinWidth(100);
		sSalaryCol.setCellValueFactory(new PropertyValueFactory<Person, Integer>("salary"));
		sSalaryCol.setStyle( "-fx-alignment: CENTER;");
		
		sTable.getColumns().addAll(sIDCol, sNameCol, sSexCol, sDesignationCol, sSalaryCol);
		sTable.setItems(staff);
		
		//Create vertical box to contain the whole table
		VBox sTableBox = new VBox();
		sTableBox.setPadding(new Insets(5));
		sTableBox.setSpacing(5);
		sTableBox.getChildren().add(sTable);
		
		mainPane.setCenter(sTableBox);
	}
	
	@Override
	public boolean search(String search) {
		if(super.getId().equals(search)) {
			return true;
		}
		else if(super.getName().equalsIgnoreCase(search)) {
			return true;
		}
		else if(super.getSex().equalsIgnoreCase(search)) {
			return true;
		}
		else if(designation.equalsIgnoreCase(search)) {
			return true;
		}
		return false;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void searchGUI(Person[] staffs, int[] staffStorage, int[] staffSize, BorderPane mainPane) {
		//Search Bar
		Label searchlabel = new Label("Search Record");
		TextField search = new TextField();
		search.setPrefColumnCount(31);
		search.setPromptText("Search in 'ID, Name, Sex, Designation' and Press Enter.");
		HBox searchbar = new HBox(5);
		searchbar.getChildren().addAll(searchlabel, search);
		
		//Create Table
		TableView<Person> sTable = new TableView<>();
		sTable.setEditable(false);
		
		//Create Table Column
		TableColumn sIDCol = new TableColumn("Staff ID");
		sIDCol.setMinWidth(100);
		sIDCol.setCellValueFactory(new PropertyValueFactory<Person, String>("id"));
		sIDCol.setStyle( "-fx-alignment: CENTER;");
		
		TableColumn sNameCol = new TableColumn("Name");
		sNameCol.setMinWidth(120);
		sNameCol.setCellValueFactory(new PropertyValueFactory<Person, String>("name"));
		sNameCol.setStyle( "-fx-alignment: CENTER;");

		TableColumn sSexCol = new TableColumn("Sex");
		sSexCol.setMinWidth(100);
		sSexCol.setCellValueFactory(new PropertyValueFactory<Person, String>("sex"));
		sSexCol.setStyle( "-fx-alignment: CENTER;");
		
		TableColumn sDesignationCol = new TableColumn("Designation");
		sDesignationCol.setMinWidth(120);
		sDesignationCol.setCellValueFactory(new PropertyValueFactory<Person, String>("designation"));
		sDesignationCol.setStyle( "-fx-alignment: CENTER;");
		
		TableColumn sSalaryCol = new TableColumn("Salary");
		sSalaryCol.setMinWidth(100);
		sSalaryCol.setCellValueFactory(new PropertyValueFactory<Person, Integer>("salary"));
		sSalaryCol.setStyle( "-fx-alignment: CENTER;");
		
		sTable.getColumns().addAll(sIDCol, sNameCol, sSexCol, sDesignationCol, sSalaryCol);

		//Create a vertical box to contains search bar & table
		VBox sTableBox = new VBox();
		sTableBox.setPadding(new Insets(5));
		sTableBox.setSpacing(5);
		sTableBox.getChildren().addAll(searchbar, sTable);
		
		mainPane.setCenter(sTableBox);
			
		//SearchBtn Key Event Handling
		search.setOnKeyPressed(e4 -> {
			switch(e4.getCode()) {
			case ENTER: 
				String searchInput = search.getText();
				int count = 0;
				Person[] staffsSearch = new Staff[staffStorage[2]];
				for(int i = 0; i < staffSize[2]; i++) {
					if(staffs[i].search(searchInput)) {
						staffsSearch[count] = new Staff(staffs[i].getId(), staffs[i].getName(), staffs[i].getSex(), 
								((Staff)staffs[i]).getDesignation(), ((Staff)staffs[i]).getSalary());
						count++;
					}
				}

				ArrayList<Person> searchList = new ArrayList<Person>(Arrays.asList(staffsSearch));
				ObservableList<Person> searchedStaff = FXCollections.observableArrayList(searchList);
				sTable.setItems(searchedStaff);
				break;
				
			default:
				break;
			}
		});
	}
}
