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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Patient extends Person {
	private int age;
	private String disease, admitStatus;
	
	//Constructor
	public Patient() {
		
	}
	public Patient(String id, String name, String sex, int age, String disease, String admitStatus) {
		super(id, name, sex);
		this.age = age;
		this.disease = disease;
		this.admitStatus = admitStatus;
	}
	
	//Getter and Setter
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	public String getDisease() {
		return disease;
	}
	public void setDisease(String disease) {
		this.disease = disease;
	}
	
	public String getAdmitStatus() {
		return admitStatus;
	}
	public void setAdmitStatus(String admitStatus) {
		this.admitStatus = admitStatus;
	}

	Scanner input = new Scanner(System.in);

	@Override
	public void newRecord(Person[] patients, int[] patientSize, BorderPane mainPane) {
		//Create a GridPane to contain form
		GridPane patientForm = new GridPane();
		patientForm.setAlignment(Pos.CENTER);
		patientForm.setHgap(10);
		patientForm.setVgap(10);
		patientForm.setPadding(new Insets(5));
		
		//Create the elements and put them into Medical Form
		Text formTitle = new Text("Add New Record");
		formTitle.setFont(Font.font("Serif", FontWeight.BOLD, 28));
		patientForm.add(formTitle, 0, 0, 2, 1);
		GridPane.setHalignment(formTitle, HPos.CENTER);

		Label patientId = new Label("Patient ID:");
		patientId.setFont(Font.font("Serif", 20));
		TextField patientIdInput = new TextField();
		patientForm.add(patientId, 0, 1);
		patientForm.add(patientIdInput, 1, 1);
		
		Label patientName = new Label("Patient Name:");
		patientName.setFont(Font.font("Serif", 20));
		TextField patientNameInput = new TextField();
		patientForm.add(patientName, 0, 2);
		patientForm.add(patientNameInput, 1, 2);
		
		Label patientSex = new Label("Sex:");
		patientSex.setFont(Font.font("Serif", 20));
		RadioButton patientMale = new RadioButton("Male");
		RadioButton patientFemale = new RadioButton("Female");
		ToggleGroup patientSexGroup = new ToggleGroup();
		patientMale.setToggleGroup(patientSexGroup);
		patientFemale.setToggleGroup(patientSexGroup);
		patientForm.add(patientSex, 0, 3);
		HBox genderButtons = new HBox(40, patientMale, patientFemale);
		patientForm.add(genderButtons, 1, 3);
		
		Label patientAge = new Label("Patient Age:");
		patientAge.setFont(Font.font("Serif", 20));
		TextField patientAgeInput = new TextField();
		patientForm.add(patientAge, 0, 4);
		patientForm.add(patientAgeInput, 1, 4);
		
		Label patientDisease = new Label("Disease:");
		patientDisease.setFont(Font.font("Serif", 20));
		TextField patientDiseaseInput = new TextField();
		patientForm.add(patientDisease, 0, 5);
		patientForm.add(patientDiseaseInput, 1, 5);
		
		Label patientStatus = new Label("Admit Status:");
		patientStatus.setFont(Font.font("Serif", 20));
		RadioButton patientAdmitYes = new RadioButton("Yes");
		RadioButton patientAdmitNo = new RadioButton("No");
		ToggleGroup patientAdmitGroup = new ToggleGroup();
		patientAdmitYes.setToggleGroup(patientAdmitGroup);
		patientAdmitNo.setToggleGroup(patientAdmitGroup);
		patientForm.add(patientStatus, 0, 6);
		HBox admitButtons = new HBox(50, patientAdmitYes, patientAdmitNo);
		patientForm.add(admitButtons, 1, 6);
		
		Button patientAddBtn = new Button("Add");
		patientForm.add(patientAddBtn, 1, 7);
		
		Button patientResetBtn = new Button("Reset");
		patientForm.add(patientResetBtn, 1, 8);
		
		//New Record Message
		Alert success = new Alert(AlertType.INFORMATION);
		success.setContentText("New Record Added!");
		
		
		//Add Button On-Click Event Handling
		patientAddBtn.setOnAction(e -> {
			//Validation of User Input
			Alert a = new Alert(AlertType.WARNING);
			a.getDialogPane().setPrefSize(340, 200);
			boolean valid = false;
			
			try {
				Integer.parseInt(patientIdInput.getText()); //Check for number
				valid = true;
				
				for(int i = 0; i < patientSize[1]; i ++) {
					if(patients[i].getId().equals(patientIdInput.getText())) {
						valid = false;
					}
				}
				if(valid == false) {
					a.setContentText("The Patient ID Already Exist!\nPlease Fill In Again!");
					a.show();
					patientIdInput.setText("");
				}
				else if(patientIdInput.getText().length() != 3) {
					a.setContentText("The Patient ID Must = 3 Digits!\nPlease Insert According To Format (xxx).");
			       	a.show();
					patientIdInput.setText("");
				}
				else if(Integer.parseInt(patientAgeInput.getText()) <= 0) {
					a.setContentText("Invalid Age!\nPlease Fill In Again!");
					a.show();
					patientAgeInput.setText("");
				}
				else if(patientNameInput.getText().trim().isEmpty() || (!patientMale.isSelected() && !patientFemale.isSelected()) ||
						patientDiseaseInput.getText().trim().isEmpty() || (!patientAdmitYes.isSelected() && !patientAdmitNo.isSelected())) {
					a.setContentText("Please Complete The Form!");
					a.show();
				}
				else {
					//Add into the record 
					if(patientMale.isSelected()) {
						if(patientAdmitYes.isSelected()) {
							patients[patientSize[1]]= new Patient(patientIdInput.getText(), patientNameInput.getText(), 
									"Male", Integer.parseInt(patientAgeInput.getText()), patientDiseaseInput.getText(), "Yes");
						}
						else {
							patients[patientSize[1]]= new Patient(patientIdInput.getText(), patientNameInput.getText(), 
								"Male", Integer.parseInt(patientAgeInput.getText()), patientDiseaseInput.getText(), "No");
						}
					}
					else if(patientFemale.isSelected()) {
						if(patientAdmitYes.isSelected()) {
							patients[patientSize[1]]= new Patient(patientIdInput.getText(), patientNameInput.getText(), 
									"Female", Integer.parseInt(patientAgeInput.getText()), patientDiseaseInput.getText(), "Yes");
						}
						else {
							patients[patientSize[1]]= new Patient(patientIdInput.getText(), patientNameInput.getText(), 
									"Female", Integer.parseInt(patientAgeInput.getText()), patientDiseaseInput.getText(), "No");
						}
					}
					
					//Clear the text field area
					patientIdInput.setText("");
					patientNameInput.setText("");
					patientMale.setSelected(false);
					patientFemale.setSelected(false);
					patientAgeInput.setText("");
					patientDiseaseInput.setText("");
					patientAdmitYes.setSelected(false);
					patientAdmitNo.setSelected(false);
					patientSize[1]++;
					success.show();
				}
			}
			catch(NumberFormatException event) {
				if (!valid){ //Check the id
					a.setContentText("Invalid ID.\nPlease Insert Integer Number.");
					a.show();		
					patientIdInput.setText("");
					
					for(int i = 0; i < patientAgeInput.getText().length(); i++) { //Check the id and room at the same time
						if(Character.isAlphabetic(patientAgeInput.getText().charAt(i))) {
							a.setContentText("Invalid ID and Age.\nPlease Insert Integer Number.");
							a.show();
							patientAgeInput.setText("");
							break;
						}
					}
				}
				else {
					a.setContentText("Invalid Age.\nPlease Insert Integer Number.");
					a.show();
					patientAgeInput.setText("");
				}
			}
		});
		
		//Reset Button Onlick Event Handling
		patientResetBtn.setOnAction(e3 -> {
			
			//Clear the text field area
			patientIdInput.setText("");
			patientNameInput.setText("");
			patientMale.setSelected(false);
			patientFemale.setSelected(false);
			patientAgeInput.setText("");
			patientDiseaseInput.setText("");
			patientAdmitYes.setSelected(false);
			patientAdmitNo.setSelected(false);
		});
		
		mainPane.setCenter(patientForm);
		
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void showInfo(Person[] patients, BorderPane mainPane) {
		//Convert Array into ArrayList for displaying the table
		ArrayList<Person> patientList = new ArrayList<Person>(Arrays.asList(patients));
		ObservableList<Person> patient = FXCollections.observableArrayList(patientList);

		//Create Table
		TableView<Person> pTable = new TableView<>();
		pTable.setEditable(false);

		//Create Table Columns
		TableColumn pIDCol = new TableColumn("Patient ID");
		pIDCol.setMinWidth(100);
		pIDCol.setCellValueFactory(new PropertyValueFactory<Person, String>("id"));
		pIDCol.setStyle( "-fx-alignment: CENTER;");
		
		TableColumn pNameCol = new TableColumn("Name");
		pNameCol.setMinWidth(120);
		pNameCol.setCellValueFactory(new PropertyValueFactory<Person, String>("name"));
		pNameCol.setStyle( "-fx-alignment: CENTER;");

		TableColumn pSexCol = new TableColumn("Sex");
		pSexCol.setMinWidth(100);
		pSexCol.setCellValueFactory(new PropertyValueFactory<Person, String>("sex"));
		pSexCol.setStyle( "-fx-alignment: CENTER;");
		
		TableColumn pAgeCol = new TableColumn("Age");
		pAgeCol.setMinWidth(100);
		pAgeCol.setCellValueFactory(new PropertyValueFactory<Person, Integer>("age"));
		pAgeCol.setStyle( "-fx-alignment: CENTER;");
		
		TableColumn pDiseaseCol = new TableColumn("Disease");
		pDiseaseCol.setMinWidth(100);
		pDiseaseCol.setCellValueFactory(new PropertyValueFactory<Person, String>("disease"));
		pDiseaseCol.setStyle( "-fx-alignment: CENTER;");

		TableColumn pStatusCol = new TableColumn("Admit Status");
		pStatusCol.setMinWidth(110);
		pStatusCol.setCellValueFactory(new PropertyValueFactory<Person, String>("admitStatus"));
		pStatusCol.setStyle( "-fx-alignment: CENTER;");
		
		pTable.getColumns().addAll(pIDCol, pNameCol, pSexCol, pAgeCol, pDiseaseCol, pStatusCol);
		pTable.setItems(patient);
		
		//Create vertical box to contain the whole table
		VBox pTableBox = new VBox();
		pTableBox.setPadding(new Insets(5));
		pTableBox.setSpacing(5);
		pTableBox.getChildren().add(pTable);
		
		mainPane.setCenter(pTableBox);
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
		else if(disease.equalsIgnoreCase(search)) {
			return true;
		}
		else if(admitStatus.equalsIgnoreCase(search)) {
			return true;
		}
		return false;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void searchGUI(Person[] patients, int[] patientStorage ,int[] patientSize, BorderPane mainPane) {		
		//Search Bar
		Label searchlabel = new Label("Search Record");
		TextField search = new TextField();
		search.setPromptText("Search in 'ID, Name, Sex, Disease, Admit Status' and Press Enter.");
		search.setPrefColumnCount(31);
		HBox searchbar = new HBox(5);
		searchbar.getChildren().addAll(searchlabel, search);
		
		//Create Table
		TableView<Person> pTable = new TableView<>();
		pTable.setEditable(false);
		
		//Create Table Column
		TableColumn pIDCol = new TableColumn("Patient ID");
		pIDCol.setMinWidth(100);
		pIDCol.setCellValueFactory(new PropertyValueFactory<Person, String>("id"));
		pIDCol.setStyle( "-fx-alignment: CENTER;");
		
		TableColumn pNameCol = new TableColumn("Name");
		pNameCol.setMinWidth(120);
		pNameCol.setCellValueFactory(new PropertyValueFactory<Person, String>("name"));
		pNameCol.setStyle( "-fx-alignment: CENTER;");

		TableColumn pSexCol = new TableColumn("Sex");
		pSexCol.setMinWidth(100);
		pSexCol.setCellValueFactory(new PropertyValueFactory<Person, String>("sex"));
		pSexCol.setStyle( "-fx-alignment: CENTER;");
		
		TableColumn pAgeCol = new TableColumn("Age");
		pAgeCol.setMinWidth(100);
		pAgeCol.setCellValueFactory(new PropertyValueFactory<Person, Integer>("age"));
		pAgeCol.setStyle( "-fx-alignment: CENTER;");
		
		TableColumn pDiseaseCol = new TableColumn("Disease");
		pDiseaseCol.setMinWidth(100);
		pDiseaseCol.setCellValueFactory(new PropertyValueFactory<Person, String>("disease"));
		pDiseaseCol.setStyle( "-fx-alignment: CENTER;");

		TableColumn pStatusCol = new TableColumn("Admit Status");
		pStatusCol.setMinWidth(110);
		pStatusCol.setCellValueFactory(new PropertyValueFactory<Person, String>("admitStatus"));
		pStatusCol.setStyle( "-fx-alignment: CENTER;");
		
		pTable.getColumns().addAll(pIDCol, pNameCol, pSexCol, pAgeCol, pDiseaseCol, pStatusCol);

		//Create a vertical box to contains search bar & table
		VBox pTableBox = new VBox();
		pTableBox.setPadding(new Insets(5));
		pTableBox.setSpacing(5);
		pTableBox.getChildren().addAll(searchbar, pTable);
		
		mainPane.setCenter(pTableBox);
		
		//SearchBtn Key Event Handling
		search.setOnKeyPressed(e3 -> {
			switch(e3.getCode()) {
			case ENTER: 
				String searchInput = search.getText();
				int count = 0;
				Person[] patientsSearch = new Patient[patientStorage[1]];
				
				for(int i = 0; i < patientSize[1]; i++) {
					if(patients[i].search(searchInput)) {
						patientsSearch[count] = new Patient(patients[i].getId(), patients[i].getName(), patients[i].getSex(), 
								((Patient)patients[i]).getAge(), ((Patient)patients[i]).getDisease(), ((Patient)patients[i]).getAdmitStatus());
						count++;
					}
				}

				ArrayList<Person> searchList = new ArrayList<Person>(Arrays.asList(patientsSearch));
				ObservableList<Person> searchedPatient = FXCollections.observableArrayList(searchList);
				pTable.setItems(searchedPatient);
				break;
				
			default:
				break;
			}
		});
	}
}
