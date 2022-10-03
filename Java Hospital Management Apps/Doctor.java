
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Doctor extends Person {
	private String specialist, workTime, qualification;
	private int room;
	
	//Constructor
	public Doctor() {

	}
	public Doctor(String id, String name, String sex, String specialist, String workTime, String qualification, int room) {
		super(id, name, sex);
		this.specialist = specialist;
		this.workTime = workTime;
		this.qualification = qualification;
		this.room = room;
	}
	
	//Getter and Setter
	public String getSpecialist() {
		return specialist;
	}
	public void setSpecialist(String specialist) {
		this.specialist = specialist;
	}
	
	public String getWorkTime() {
		return workTime;
	}
	public void setWorkTime(String workTime) {
		this.workTime = workTime;
	}
	
	public String getQualification() {
		return qualification;
	}
	public void setQualification(String qualification) {
		this.qualification = qualification;
	}
	
	public int getRoom() {
		return room;
	}
	public void setRoom(int room) {
		this.room = room;
	}
	
	@Override
	public void newRecord(Person[] doctors, int[] doctorSize, BorderPane mainPane) {
		//Create a GridPane to contain form
		GridPane doctorForm = new GridPane();
		doctorForm.setAlignment(Pos.CENTER);
		doctorForm.setHgap(10);
		doctorForm.setVgap(10);
		doctorForm.setPadding(new Insets(5));
		
		//Create the elements and put them into Doctor Form
		Text formTitle = new Text("Add New Record");
		formTitle.setFont(Font.font("Serif", FontWeight.BOLD, 28));
		GridPane.setHalignment(formTitle, HPos.CENTER);
		doctorForm.add(formTitle, 0, 0, 2, 1);

		Label doctorId = new Label("ID:");
		doctorId.setFont(Font.font("Serif", 20));
		TextField doctorIdInput = new TextField();
		doctorForm.add(doctorId, 0, 1);
		doctorForm.add(doctorIdInput, 1, 1);
		
		Label doctorName = new Label("Name:");
		doctorName.setFont(Font.font("Serif", 20));
		TextField doctorNameInput = new TextField();
		doctorForm.add(doctorName, 0, 2);
		doctorForm.add(doctorNameInput, 1, 2);
		
		Label doctorSex = new Label("Sex:");
		doctorSex.setFont(Font.font("Serif", 20));
		RadioButton doctorMale = new RadioButton("Male");
		RadioButton doctorFemale = new RadioButton("Female");
		ToggleGroup doctorSexGroup = new ToggleGroup();
		doctorMale.setToggleGroup(doctorSexGroup);
		doctorFemale.setToggleGroup(doctorSexGroup);
		doctorForm.add(doctorSex, 0, 3);
		HBox genderButtons = new HBox(40, doctorMale, doctorFemale);
		doctorForm.add(genderButtons, 1, 3);
		
		Label doctorSpecialist = new Label("Specialist:");
		doctorSpecialist.setFont(Font.font("Serif", 20));
		TextField doctorSpecialistInput = new TextField();
		doctorForm.add(doctorSpecialist, 0, 4);
		doctorForm.add(doctorSpecialistInput, 1, 4);
		
		Label doctorWorkTime = new Label("Work Time:");
		doctorWorkTime.setFont(Font.font("Serif", 20));
		TextField doctorWorkTimeInput = new TextField();
		doctorForm.add(doctorWorkTime, 0, 5);
		doctorForm.add(doctorWorkTimeInput, 1, 5);
		
		Label doctorQualification = new Label("Qualification:");
		doctorQualification.setFont(Font.font("Serif", 20));
		TextField doctorQualificationInput = new TextField();
		doctorForm.add(doctorQualification, 0, 6);
		doctorForm.add(doctorQualificationInput, 1, 6);
		
		Label doctorRoom = new Label("Room No.:");
		doctorRoom.setFont(Font.font("Serif", 20));
		TextField doctorRoomInput = new TextField();
		doctorForm.add(doctorRoom, 0, 7);
		doctorForm.add(doctorRoomInput, 1, 7);
		
		Button doctorAddBtn = new Button("Add");
		doctorForm.add(doctorAddBtn, 1, 8);
		
		Button doctorResetBtn = new Button("Reset");
		doctorForm.add(doctorResetBtn, 1, 9);
		
		//New Record Message
		Alert success = new Alert(AlertType.INFORMATION);
		success.setContentText("New Record Added!");
				
		
		//Add Button On-click Event Handling
		doctorAddBtn.setOnAction(e3->{
			boolean validId = false;
			boolean validRoom = true;
		
			//Check whether is a valid input
			Alert a = new Alert(AlertType.WARNING);
			a.getDialogPane().setPrefSize(340, 200);
			try {
				Integer.parseInt(doctorIdInput.getText()); //Validation for digit ID
				validId = true;
				
				for(int i = 0; i < doctorSize[0]; i ++) {
					if(doctors[i].getId().equals(doctorIdInput.getText())) {
						validId = false;
					}
					if(((Doctor)doctors[i]).getRoom() == Integer.parseInt((doctorRoomInput.getText()))) {
						validRoom = false;
					}
				}
				if(validId == false) {
					a.setContentText("The Doctor ID Already Exist!\nPlease Fill In Again!");
					a.show();
					doctorIdInput.setText("");
				}
				else if(validRoom == false) {
					a.setContentText("The Room Already Exist!\nPlease Fill In Again!");
					a.show();
					doctorRoomInput.setText("");
				}
				else if(doctorIdInput.getText().length() != 3) {
					a.setContentText("The Doctor ID Must = 3 Digits!\nPlease Insert According To Format (xxx).");
			       	a.show();
			       	doctorIdInput.setText("");
				}
				
				else if(doctorNameInput.getText().trim().isEmpty() || (!doctorMale.isSelected() && !doctorFemale.isSelected())||
						doctorSpecialistInput.getText().trim().isEmpty() || doctorWorkTimeInput.getText().trim().isEmpty() ||
						doctorQualificationInput.getText().trim().isEmpty()) {
					a.setContentText("Please Complete The Form!");
					a.show();
				}
				else {
					//Add into the record 
					if(doctorMale.isSelected()) {
						doctors[doctorSize[0]]= new Doctor(doctorIdInput.getText(), doctorNameInput.getText(), "Male", 
								doctorSpecialistInput.getText(), doctorWorkTimeInput.getText(), doctorQualificationInput.getText(), 
								Integer.parseInt(doctorRoomInput.getText()));
					}
					else if(doctorFemale.isSelected()) {
						doctors[doctorSize[0]]= new Doctor(doctorIdInput.getText(), doctorNameInput.getText(), "Female", 
								doctorSpecialistInput.getText(), doctorWorkTimeInput.getText(), doctorQualificationInput.getText(), 
								Integer.parseInt(doctorRoomInput.getText()));
					}
					
					//Clear the text field area
					doctorIdInput.setText("");
					doctorNameInput.setText("");
					doctorMale.setSelected(false);
					doctorFemale.setSelected(false);
					doctorSpecialistInput.setText("");
					doctorWorkTimeInput.setText("");
					doctorQualificationInput.setText("");
					doctorRoomInput.setText("");
					doctorSize[0]++;
					success.show();
				}
			}
			catch(NumberFormatException e4) {
				if (!validId){ //Check the id
					a.setContentText("Invalid ID.\nPlease Insert Integer Number.");
					a.show();		
					doctorIdInput.setText("");
					
					for(int i = 0; i < doctorRoomInput.getText().length(); i++) { //Check the id and room at the same time
						if(Character.isAlphabetic(doctorRoomInput.getText().charAt(i))) {
							a.setContentText("Invalid ID and Room No.\nPlease Insert Integer Number.");
							a.show();
							doctorRoomInput.setText("");
							break;
						}
					}
				}
				else { //room error
					a.setContentText("Invalid Room No.\nPlease Insert Integer Number.");
					a.show();		
					doctorRoomInput.setText("");
				}
			}
		});
		
		//Reset Button On-Click Event Handling
		doctorResetBtn.setOnAction(e3 -> {
			
			//Clear the text field area
			doctorIdInput.setText("");
			doctorNameInput.setText("");
			doctorMale.setSelected(false);
			doctorFemale.setSelected(false);
			doctorSpecialistInput.setText("");
			doctorWorkTimeInput.setText("");
			doctorQualificationInput.setText("");
			doctorRoomInput.setText("");
		});
		
		mainPane.setCenter(doctorForm);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void showInfo(Person[] doctors, BorderPane mainPane) {
		//Convert Array into ArrayList for displaying the table
		ArrayList<Person> doctorList = new ArrayList<Person>(Arrays.asList(doctors));
		ObservableList<Person> doctor = FXCollections.observableArrayList(doctorList);

		//Create Table
		TableView<Person> dTable = new TableView<>();
		dTable.setEditable(false);
		
		//Create Table Columns
		TableColumn dIDCol = new TableColumn("Doctor ID");
		dIDCol.setMinWidth(100);
		dIDCol.setCellValueFactory(new PropertyValueFactory<Person, String>("id"));
		dIDCol.setStyle( "-fx-alignment: CENTER;");
		
		TableColumn dNameCol = new TableColumn("Name");
		dNameCol.setMinWidth(140);
		dNameCol.setCellValueFactory(new PropertyValueFactory<Person, String>("name"));
		dNameCol.setStyle( "-fx-alignment: CENTER;");

		TableColumn dSexCol = new TableColumn("Sex");
		dSexCol.setMinWidth(100);
		dSexCol.setCellValueFactory(new PropertyValueFactory<Person, String>("sex"));
		dSexCol.setStyle( "-fx-alignment: CENTER;");
		
		TableColumn dSpecialistCol = new TableColumn("Specialist");
		dSpecialistCol.setMinWidth(120);
		dSpecialistCol.setCellValueFactory(new PropertyValueFactory<Person, String>("specialist"));
		dSpecialistCol.setStyle( "-fx-alignment: CENTER;");

		TableColumn dWorkTimeCol = new TableColumn("Work Time");
		dWorkTimeCol.setMinWidth(100);
		dWorkTimeCol.setCellValueFactory(new PropertyValueFactory<Person, String>("workTime"));
		dWorkTimeCol.setStyle( "-fx-alignment: CENTER;");
		
		TableColumn dQualificationCol = new TableColumn("Qualification");
		dQualificationCol.setMinWidth(110);
		dQualificationCol.setCellValueFactory(new PropertyValueFactory<Person, String>("qualification"));
		dQualificationCol.setStyle( "-fx-alignment: CENTER;");
		
		TableColumn dRoomCol = new TableColumn("Room No.");
		dRoomCol.setMinWidth(100);
		dRoomCol.setCellValueFactory(new PropertyValueFactory<Person, Integer>("room"));
		dRoomCol.setStyle( "-fx-alignment: CENTER;");
		
		dTable.getColumns().addAll(dIDCol, dNameCol, dSexCol, dSpecialistCol, dWorkTimeCol, dQualificationCol, dRoomCol);
		dTable.setItems(doctor);
		
		
		//Create vertical box to contain the whole table
		VBox dTableBox = new VBox();
		dTableBox.setPadding(new Insets(5));
		dTableBox.setSpacing(5);
		dTableBox.getChildren().add(dTable);
		
		mainPane.setCenter(dTableBox);
	}
	
	@Override
	public boolean search(String search) {
		if(super.getName().equalsIgnoreCase(search)) {
			return true;
		}
		else if(super.getSex().equalsIgnoreCase(search)) {
			return true;
		}
		else if(specialist.equalsIgnoreCase(search)) {
			return true;
		}
		else if(super.getId().equals(search) || Integer.toString(room).equals(search)) {
			return true; //the id and room can be same, so use or
		}
		return false;
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void searchGUI(Person[] doctors, int[] doctorStorage, int[] doctorSize, BorderPane mainPane) {
		//Search Bar
		Label searchlabel = new Label("Search Record");
		TextField search = new TextField();
		search.setPromptText("Search in 'ID, Name, Sex, Specialist, Room No.' and Press Enter.");
		search.setPrefColumnCount(31);
		HBox searchbar = new HBox(5);
		searchbar.getChildren().addAll(searchlabel, search);
		
		//Create Table
		TableView<Person> dTable = new TableView<>();
		dTable.setEditable(false);
		
		//Create Table Column
		TableColumn dIDCol = new TableColumn("Doctor ID");
		dIDCol.setMinWidth(100);
		dIDCol.setCellValueFactory(new PropertyValueFactory<Person, String>("id"));
		dIDCol.setStyle( "-fx-alignment: CENTER;");
		
		TableColumn dNameCol = new TableColumn("Name");
		dNameCol.setMinWidth(140);
		dNameCol.setCellValueFactory(new PropertyValueFactory<Person, String>("name"));
		dNameCol.setStyle( "-fx-alignment: CENTER;");

		TableColumn dSexCol = new TableColumn("Sex");
		dSexCol.setMinWidth(100);
		dSexCol.setCellValueFactory(new PropertyValueFactory<Person, String>("sex"));
		dSexCol.setStyle( "-fx-alignment: CENTER;");
		
		TableColumn dSpecialistCol = new TableColumn("Specialist");
		dSpecialistCol.setMinWidth(120);
		dSpecialistCol.setCellValueFactory(new PropertyValueFactory<Person, String>("specialist"));
		dSpecialistCol.setStyle( "-fx-alignment: CENTER;");

		TableColumn dWorkTimeCol = new TableColumn("Work Time");
		dWorkTimeCol.setMinWidth(100);
		dWorkTimeCol.setCellValueFactory(new PropertyValueFactory<Person, String>("workTime"));
		dWorkTimeCol.setStyle( "-fx-alignment: CENTER;");

		TableColumn dQualificationCol = new TableColumn("Qualification");
		dQualificationCol.setMinWidth(110);
		dQualificationCol.setCellValueFactory(new PropertyValueFactory<Person, String>("qualification"));
		dQualificationCol.setStyle( "-fx-alignment: CENTER;");
		
		TableColumn dRoomCol = new TableColumn("Room No.");
		dRoomCol.setMinWidth(100);
		dRoomCol.setCellValueFactory(new PropertyValueFactory<Person, Integer>("room"));
		dRoomCol.setStyle( "-fx-alignment: CENTER;");
		
		dTable.getColumns().addAll(dIDCol, dNameCol, dSexCol, dSpecialistCol, dWorkTimeCol, dQualificationCol, dRoomCol);

		//Create a vertical box to contains search bar & table
		VBox dTableBox = new VBox();
		dTableBox.setPadding(new Insets(5));
		dTableBox.setSpacing(5);
		dTableBox.getChildren().addAll(searchbar, dTable);
		
		mainPane.setCenter(dTableBox);
		
		//SearchBtn Key Event Handling
		search.setOnKeyPressed(e3 -> {
			switch(e3.getCode()) {
			case ENTER: 
				String searchInput = search.getText();
				int count = 0;
				Person[] doctorsSearch = new Doctor[doctorStorage[0]];
				
				for(int i = 0; i < doctorSize[0]; i++) {
					if(doctors[i].search(searchInput)) {
						doctorsSearch[count] = new Doctor(doctors[i].getId(), doctors[i].getName(), doctors[i].getSex(),
								((Doctor)doctors[i]).getSpecialist(), ((Doctor)doctors[i]).getWorkTime(), 
								((Doctor)doctors[i]).getQualification(), ((Doctor)doctors[i]).getRoom());
						count++;
					}
				}

				ArrayList<Person> searchList = new ArrayList<Person>(Arrays.asList(doctorsSearch));
				ObservableList<Person> searchedDoctor = FXCollections.observableArrayList(searchList);
				dTable.setItems(searchedDoctor);
				break;
				
			default:
				break;
			}
		});
	}
}
