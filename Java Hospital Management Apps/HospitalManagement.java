import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HospitalManagement extends Application {
	@Override
	public void start(Stage primaryStage) {
		
		//Doctor Patient Staff Medical Laboratory Facility
		int[] arraySize = {25, 100, 100, 100, 20, 20};
		
		//Insert size of array
		Person[] doctors = new Doctor[arraySize[0]];
		Person[] patients = new Patient[arraySize[1]];
		Person[] staffs = new Staff[arraySize[2]];
		Medical[] medicals = new Medical[arraySize[3]];
		Facilities[] laboratories = new Lab[arraySize[4]];
		Facilities[] facilities = new Facility[arraySize[5]];
		
		//Initialize the array (Question required at least 5 data)
		doctors[0] = new Doctor("750", "Dr.Teo Teck Wan", "Male", "Physiatrist", "08-11AM", "MBBS,MD", 1);
		doctors[1] = new Doctor("599", "Dr.Chen Si Yang", "Male", "Plastic Surgeon", "02-05PM", "MBBS,MS", 2);
		doctors[2] = new Doctor("195", "Dr.Ooi Si-Yang", "Male", "Physician", "08-11PM", "MBBS,MD", 3);
		doctors[3] = new Doctor("123", "Dr.Jesmin Leong", "Female", "Neurologist", "09-12PM", "BMedSc", 4);
		doctors[4] = new Doctor("577", "Dr.Jolene Lee", "Female", "Adult Nurse", "02-06PM", "BN", 5);
		
		patients[0] = new Patient("750", "Teo Teck Wan", "Male", 20, "Fever", "Yes");
		patients[1] = new Patient("599", "Chen Si Yang", "Male", 20, "Asthma", "Yes");
		patients[2] = new Patient("195", "Ooi Si-Yang", "Male", 21, "Fever", "Yes");
		patients[3] = new Patient("1", "John Paw", "Female", 22, "Cancer", "Yes");
		patients[4] = new Patient("201", "Winnie Xuan", "Female", 15, "Stroke", "Yes");
		
		staffs[0] = new Staff("750", "Teo Teck Wan", "Male", "Pharmacists", 12000);
		staffs[1] = new Staff("599", "Chen Si Yang", "Male", "Therapist",20000);
		staffs[2] = new Staff("195", "Ooi Si-Yang", "Male", "Social Workers", 9000);
		staffs[3] = new Staff("600", "Poh Hui Wen", "Female", "Specialist", 2000);
		staffs[4] = new Staff("200", "Annie Tan", "Female", "Dietitian", 8000);
		
		medicals[0] = new Medical("Atenolol", "White Heron", "12-07-2024", 55, 250);
		medicals[1] = new Medical("Codeine", "MepharmY", "01-12-2025", 90, 100);
		medicals[2] = new Medical("Dactinomycin", "OncogenX", "31-12-2022", 2, 50);
		medicals[3] = new Medical("Fidaxomicin", "Teraju Pharma", "25-03-2023", 60, 104);
		medicals[4] = new Medical("Oxycodone", "Unipharma FZC", "22-09-2022", 67, 53);
		
		laboratories[0] = new Lab("X-Ray Scan", 300);
		laboratories[1] = new Lab("CT Scan", 500);
		laboratories[2] = new Lab("IV Therapy", 900);
		laboratories[3] = new Lab("Ultrasound", 1000);
		laboratories[4] = new Lab("Blood Bank", 750);
		
		facilities[0] = new Facility("Pharmacy");
		facilities[1] = new Facility("Ambulans");
		facilities[2] = new Facility("Emergency");
		facilities[3] = new Facility("Cafeteria");
		facilities[4] = new Facility("Clinical");
		
		//Doctor Patient Staff Medical Laboratory Facility
		int[] countElement = {5, 5, 5, 5, 5, 5};
		
		//Initialize Pane
		BorderPane hospitalPane = new BorderPane();
		
		HBox hospitalTop = new HBox(20);
		hospitalTop.setPadding(new Insets(5));
		hospitalTop.setStyle("-fx-background-color: lightskyblue;");
		hospitalTop.setAlignment(Pos.CENTER);
		
		HBox hospitalBottom = new HBox(20);
		hospitalBottom.setPadding(new Insets(5));
		hospitalBottom.setStyle("-fx-background-color: lightskyblue;");
		hospitalBottom.setAlignment(Pos.CENTER);
		
		GridPane hospitalNavBar = new GridPane(); //Middle
		hospitalNavBar.setPadding(new Insets(5));
		hospitalNavBar.setVgap(20);
		hospitalNavBar.setHgap(20);
		
		//Hospital GUI Top
		Text TopText = new Text("Welcome to Hospital Menu");
		TopText.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR, 30));
		TopText.setFill(Color.BLACK);
		hospitalTop.getChildren().add(TopText);
		hospitalPane.setTop(hospitalTop);
		
		//Hospital GUI Bottom
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Text dateText = new Text(formatter.format(date));
		Button homeBtn = new Button("Home");
		hospitalBottom.getChildren().addAll(dateText, homeBtn);
		hospitalBottom.setPadding(new Insets(5));
		hospitalPane.setBottom(hospitalBottom);
		
		//Hospital GUI Center
		Button doctorBtn = new Button("Doctor");
		doctorBtn.setMinWidth(100);
		doctorBtn.setMinHeight(100);
		
		Button patientBtn = new Button("Patient");
		patientBtn.setMinWidth(100);
		patientBtn.setMinHeight(100);
		
		Button staffBtn = new Button("Staff");
		staffBtn.setMinWidth(100);
		staffBtn.setMinHeight(100);
		
		Button labBtn = new Button("Lab");
		labBtn.setMinWidth(100);
		labBtn.setMinHeight(100);
		
		Button facilityBtn = new Button("Facility");
		facilityBtn.setMinWidth(100);
		facilityBtn.setMinHeight(100);
		
		Button medicalBtn = new Button("Medical");
		medicalBtn.setMinWidth(100);
		medicalBtn.setMinHeight(100);
		
		Text mainTitle = new Text("Choose the Function");
		mainTitle.setFont(Font.font("serif", FontWeight.BOLD, 40));
		hospitalNavBar.add(mainTitle, 0, 0, 3, 1);
		hospitalNavBar.add(doctorBtn, 0, 1);
		hospitalNavBar.add(patientBtn, 1, 1);
		hospitalNavBar.add(staffBtn, 2, 1);
		hospitalNavBar.add(labBtn, 0, 2);
		hospitalNavBar.add(facilityBtn, 1, 2);
		hospitalNavBar.add(medicalBtn, 2, 2);
		hospitalNavBar.setAlignment(Pos.CENTER);
		hospitalPane.setCenter(hospitalNavBar);
		
		//Home Button On-Click Event Handling
		homeBtn.setOnAction(e -> {
			hospitalPane.setCenter(hospitalNavBar);
			TopText.setText("Welcome To Hospital Menu");
			primaryStage.setTitle("Hospital Management Application");
			hospitalBottom.getChildren().clear();
			hospitalBottom.getChildren().addAll(dateText, homeBtn);
		});
		
		
		// (1) Doctor Button On-Click Event Handling (Doctor Pane)
		doctorBtn.setOnAction(ed -> {
			//Doctor GUI
			primaryStage.setTitle("Doctor Program");
			TopText.setText("Welcome To Doctor Page");
			
			GridPane doctorNavBar = new GridPane();
			doctorNavBar.setPadding(new Insets(5));
			doctorNavBar.setVgap(20);
			doctorNavBar.setHgap(20);
			
			Button addDoctorBtn = new Button("Add New Doctor");
			addDoctorBtn.setMinWidth(200);
			addDoctorBtn.setMinHeight(50);
			
			Button displayDoctorBtn = new Button("Display Doctor List");
			displayDoctorBtn.setMinWidth(200);
			displayDoctorBtn.setMinHeight(50);
			
			Button searchDoctorBtn = new Button("Search Record");
			searchDoctorBtn.setMinWidth(200);
			searchDoctorBtn.setMinHeight(50);
			
			Text doctorTitle = new Text("Select The Function");
			doctorTitle.setFont(Font.font("serif", FontWeight.BOLD, 40));
			doctorNavBar.add(doctorTitle, 0, 0);
			doctorNavBar.add(addDoctorBtn, 0, 1);
			doctorNavBar.add(displayDoctorBtn, 0, 2);
			doctorNavBar.add(searchDoctorBtn, 0, 3);
			
			doctorNavBar.setAlignment(Pos.CENTER);
			GridPane.setHalignment(addDoctorBtn, HPos.CENTER);
			GridPane.setHalignment(displayDoctorBtn, HPos.CENTER);
			GridPane.setHalignment(searchDoctorBtn, HPos.CENTER);
			
			Button backDoctorBtn = new Button("Back");
			hospitalBottom.getChildren().clear();
			hospitalBottom.getChildren().addAll(dateText, homeBtn, backDoctorBtn);
			backDoctorBtn.setVisible(false);
			
			hospitalPane.setCenter(doctorNavBar);
			
			//Back Doctor Page Button On-Click Event Handling
			backDoctorBtn.setOnAction(ed0 -> {
				hospitalPane.setCenter(doctorNavBar);
				backDoctorBtn.setVisible(false);
			});
			
			//Add Doctor Record Button On-Click Event Handling
			addDoctorBtn.setOnAction(ed1 -> {
				backDoctorBtn.setVisible(true);
				doctors[countElement[0] - 1].newRecord(doctors, countElement, hospitalPane);
			});
			
			//Display Doctor Record Button On-Click Event Handling
			displayDoctorBtn.setOnAction(ed2 -> {
				backDoctorBtn.setVisible(true);
				doctors[countElement[0] - 1].showInfo(doctors, hospitalPane);
			});
			
			//Search Doctor Record Button On-Click Event Handling
			searchDoctorBtn.setOnAction(ed3 -> {
				backDoctorBtn.setVisible(true);
				doctors[countElement[0] - 1].searchGUI(doctors, arraySize, countElement, hospitalPane);
			});
		});
		
		
		// (2) Patient Button On-Click Event Handling (Patient Pane)
		patientBtn.setOnAction(ep -> {
			//Patient GUI
			primaryStage.setTitle("Patient Program");
			TopText.setText("Welcome To Patient Page");
			
			GridPane patientNavBar = new GridPane();
			patientNavBar.setPadding(new Insets(5));
			patientNavBar.setVgap(20);
			patientNavBar.setHgap(20);
			
			Button addPatientBtn = new Button("Add New Patient");
			addPatientBtn.setMinWidth(200);
			addPatientBtn.setMinHeight(50);
			
			Button displayPatientBtn = new Button("Display Patient List");
			displayPatientBtn.setMinWidth(200);
			displayPatientBtn.setMinHeight(50);
			
			Button searchPatientBtn = new Button("Search Record");
			searchPatientBtn.setMinWidth(200);
			searchPatientBtn.setMinHeight(50);
			
			Text patientTitle = new Text("Select The Function");
			patientTitle.setFont(Font.font("serif", FontWeight.BOLD, 40));
			patientNavBar.add(patientTitle, 0, 0);
			patientNavBar.add(addPatientBtn, 0, 1);
			patientNavBar.add(displayPatientBtn, 0, 2);
			patientNavBar.add(searchPatientBtn, 0, 3);
			
			patientNavBar.setAlignment(Pos.CENTER);
			GridPane.setHalignment(addPatientBtn, HPos.CENTER);
			GridPane.setHalignment(displayPatientBtn, HPos.CENTER);
			GridPane.setHalignment(searchPatientBtn, HPos.CENTER);
			
			Button backPatientBtn = new Button("Back");
			hospitalBottom.getChildren().clear();
			hospitalBottom.getChildren().addAll(dateText, homeBtn, backPatientBtn);
			backPatientBtn.setVisible(false);
			
			hospitalPane.setCenter(patientNavBar);
			
			//Back Patient Page Button On-Click Event Handling
			backPatientBtn.setOnAction(ep0 -> {
				hospitalPane.setCenter(patientNavBar);
				backPatientBtn.setVisible(false);
			});
			
			//Add Patient Record Button On-Click Event Handling
			addPatientBtn.setOnAction(ep1 -> {
				backPatientBtn.setVisible(true);
				patients[countElement[1] - 1].newRecord(patients, countElement, hospitalPane);
			});
			
			//Display Patient Record Button On-Click Event Handling
			displayPatientBtn.setOnAction(ep2 -> {
				backPatientBtn.setVisible(true);
				patients[countElement[1] - 1].showInfo(patients, hospitalPane);
			});
			
			//Search Patient Record Button On-Click Event Handling
			searchPatientBtn.setOnAction(ep3 -> {
				backPatientBtn.setVisible(true);
				patients[countElement[1] - 1].searchGUI(patients, arraySize, countElement, hospitalPane);
			});
		});
		
		
		// (3) Staff Button On-Click Event Handling (Staff Pane)
		staffBtn.setOnAction(es -> {
			//Staff GUI
			primaryStage.setTitle("Staff Program");
			TopText.setText("Welcome to Staff Page");
			
			GridPane staffNavBar = new GridPane();
			staffNavBar.setPadding(new Insets(5));
			staffNavBar.setVgap(20);
			staffNavBar.setHgap(20);
			
			Button addStaffBtn = new Button("Add New Staff");
			addStaffBtn.setMinWidth(200);
			addStaffBtn.setMinHeight(50);
			
			Button displayStaffBtn = new Button("Display Staff List");
			displayStaffBtn.setMinWidth(200);
			displayStaffBtn.setMinHeight(50);
			
			Button searchStaffBtn = new Button("Search Record");
			searchStaffBtn.setMinWidth(200);
			searchStaffBtn.setMinHeight(50);
			
			
			Text staffTitle = new Text("Select The Function");
			staffTitle.setFont(Font.font("serif", FontWeight.BOLD, 40));
			staffNavBar.add(staffTitle, 0, 0);
			staffNavBar.add(addStaffBtn, 0, 1);
			staffNavBar.add(displayStaffBtn, 0, 2);
			staffNavBar.add(searchStaffBtn, 0, 3);
			
			staffNavBar.setAlignment(Pos.CENTER);
			GridPane.setHalignment(addStaffBtn, HPos.CENTER);
			GridPane.setHalignment(displayStaffBtn, HPos.CENTER);
			GridPane.setHalignment(searchStaffBtn, HPos.CENTER);
			
			hospitalPane.setCenter(staffNavBar);
			
			Button backStaffBtn = new Button("Back");
			hospitalBottom.getChildren().clear();
			hospitalBottom.getChildren().addAll(dateText, homeBtn,backStaffBtn);
			backStaffBtn.setVisible(false);
			
			//Back Staff Page Button On-Click Event Handling
			backStaffBtn.setOnAction(es0->{
				hospitalPane.setCenter(staffNavBar);
				backStaffBtn.setVisible(false);
			});
			
			//Add Staff Record Button On-Click Event Handling
			addStaffBtn.setOnAction(es1 -> {
				backStaffBtn.setVisible(true);
				staffs[countElement[2] - 1].newRecord(staffs, countElement, hospitalPane);
			});
			
			//Display Staff Record Button On-Click Event Handling
			displayStaffBtn.setOnAction(es2 -> {
				backStaffBtn.setVisible(true);
				staffs[countElement[2] - 1].showInfo(staffs, hospitalPane);
			});
			
			//Search Staff Record Button On-Click Event Handling
			searchStaffBtn.setOnAction(es3 -> {
				backStaffBtn.setVisible(true);
				staffs[countElement[2] - 1].searchGUI(staffs, arraySize, countElement, hospitalPane);
			});
		});
		
		// (4) Medical Button On-Click Event Handling
		medicalBtn.setOnAction(em -> {
			primaryStage.setTitle("Medical Program");
			TopText.setText("Welcome To Medical Page");
			GridPane medicalNavBar = new GridPane();
			medicalNavBar.setPadding(new Insets(5));
			medicalNavBar.setVgap(20);
			medicalNavBar.setHgap(20);
			
			Button addMedicalBtn = new Button("Add New Medical");
			addMedicalBtn.setMinWidth(200);
			addMedicalBtn.setMinHeight(50);
			
			Button displayMedicalBtn = new Button("Display Medical List");
			displayMedicalBtn.setMinWidth(200);
			displayMedicalBtn.setMinHeight(50);
			
			Button searchMedicalBtn = new Button("Search Record");
			searchMedicalBtn.setMinWidth(200);
			searchMedicalBtn.setMinHeight(50);
			
			Text medicalTitle = new Text("Select The Function");
			medicalTitle.setFont(Font.font("serif", FontWeight.BOLD, 40));
			
			medicalNavBar.add(medicalTitle, 0, 0);
			medicalNavBar.add(addMedicalBtn, 0, 1);
			medicalNavBar.add(displayMedicalBtn, 0, 2);
			medicalNavBar.add(searchMedicalBtn, 0, 3);
			
			medicalNavBar.setAlignment(Pos.CENTER);
			GridPane.setHalignment(addMedicalBtn, HPos.CENTER);
			GridPane.setHalignment(displayMedicalBtn, HPos.CENTER);
			GridPane.setHalignment(searchMedicalBtn, HPos.CENTER);
			
			hospitalPane.setCenter(medicalNavBar);
			
			Button backMedicalBtn = new Button("Back");
			hospitalBottom.getChildren().clear();
			hospitalBottom.getChildren().addAll(dateText, homeBtn,backMedicalBtn);
			backMedicalBtn.setVisible(false);

			//Back Medical Page Button On-Click Event Handling
			backMedicalBtn.setOnAction(em0 -> {
				backMedicalBtn.setVisible(false);
				hospitalPane.setCenter(medicalNavBar);
			});
			
			//Add Medical Record Button On-Click Event Handling
			addMedicalBtn.setOnAction(em1 -> {
				backMedicalBtn.setVisible(true);
				medicals[countElement[3] - 1].newMedical(medicals, countElement, hospitalPane);
			});
			
			//Display Medical Record Button On-Click Event Handling
			displayMedicalBtn.setOnAction(em2 -> {
				backMedicalBtn.setVisible(true);
				medicals[countElement[3] - 1].findMedical(medicals, hospitalPane);
			});
			
			//Search Medical Record Button On-Click Event Handling
			searchMedicalBtn.setOnAction(em3 -> {
				backMedicalBtn.setVisible(true);
				medicals[countElement[3] - 1].searchGUI(medicals, arraySize, countElement, hospitalPane);
			});
		});
		
		// (5) Lab Button On-Click Event Handling
		labBtn.setOnAction(el -> {
			primaryStage.setTitle("Laboratory Program");
			TopText.setText("Welcome To Laboratory Page");
			GridPane labNavBar = new GridPane();
			labNavBar.setPadding(new Insets(5));
			labNavBar.setVgap(20);
			labNavBar.setHgap(20);
			
			Button addLabBtn = new Button("Add New Lab");
			addLabBtn.setMinWidth(200);
			addLabBtn.setMinHeight(50);
			
			Button displayLabBtn = new Button("Display Lab List");
			displayLabBtn.setMinWidth(200);
			displayLabBtn.setMinHeight(50);
			
			Button searchLabBtn = new Button("Search Record");
			searchLabBtn.setMinWidth(200);
			searchLabBtn.setMinHeight(50);
			
			Text labTitle = new Text("Select The Function");
			labTitle.setFont(Font.font("serif", FontWeight.BOLD, 40));
			
			labNavBar.add(labTitle, 0, 0);
			labNavBar.add(addLabBtn, 0, 1);
			labNavBar.add(displayLabBtn, 0, 2);
			labNavBar.add(searchLabBtn, 0, 3);
			
			labNavBar.setAlignment(Pos.CENTER);
			GridPane.setHalignment(addLabBtn, HPos.CENTER);
			GridPane.setHalignment(displayLabBtn, HPos.CENTER);
			GridPane.setHalignment(searchLabBtn, HPos.CENTER);
			
			hospitalPane.setCenter(labNavBar);
			
			Button backLabBtn = new Button("Back");
			hospitalBottom.getChildren().clear();
			hospitalBottom.getChildren().addAll(dateText, homeBtn, backLabBtn);
			backLabBtn.setVisible(false);

			//Back Lab Page Button On-Click Event Handling
			backLabBtn.setOnAction(ef0 -> {
				backLabBtn.setVisible(false);
				hospitalPane.setCenter(labNavBar);
			});
			
			//Add Lab Record Button On-Click Event Handling
			addLabBtn.setOnAction(ef1 -> {
				backLabBtn.setVisible(true);
				laboratories[countElement[4] - 1].newRecord(laboratories, countElement, hospitalPane);
			});
			
			//Display Lab Record Button On-Click Event Handling
			displayLabBtn.setOnAction(em2 -> {
				backLabBtn.setVisible(true);
				laboratories[countElement[4] - 1].showInfo(laboratories, hospitalPane);
			});
			
			//Search Facility Record Button On-Click Event Handling
			searchLabBtn.setOnAction(em3 -> {
				backLabBtn.setVisible(true);
				laboratories[countElement[4] - 1].searchGUI(laboratories, arraySize, countElement, hospitalPane);
			});
		});
		
		// (6) Facility Button On-Click Event Handling
		facilityBtn.setOnAction(ef -> {
			primaryStage.setTitle("Facility Program");
			TopText.setText("Welcome To Facility Page");
			GridPane facilityNavBar = new GridPane();
			facilityNavBar.setPadding(new Insets(5));
			facilityNavBar.setVgap(20);
			facilityNavBar.setHgap(20);
			
			Button addFacilityBtn = new Button("Add New Facility");
			addFacilityBtn.setMinWidth(200);
			addFacilityBtn.setMinHeight(50);
			
			Button displayFacilityBtn = new Button("Display Facility List");
			displayFacilityBtn.setMinWidth(200);
			displayFacilityBtn.setMinHeight(50);
			
			Button searchFacilityBtn = new Button("Search Record");
			searchFacilityBtn.setMinWidth(200);
			searchFacilityBtn.setMinHeight(50);
			
			Text facilityTitle = new Text("Select The Function");
			facilityTitle.setFont(Font.font("serif", FontWeight.BOLD, 40));
			
			facilityNavBar.add(facilityTitle, 0, 0);
			facilityNavBar.add(addFacilityBtn, 0, 1);
			facilityNavBar.add(displayFacilityBtn, 0, 2);
			facilityNavBar.add(searchFacilityBtn, 0, 3);
			
			facilityNavBar.setAlignment(Pos.CENTER);
			GridPane.setHalignment(addFacilityBtn, HPos.CENTER);
			GridPane.setHalignment(displayFacilityBtn, HPos.CENTER);
			GridPane.setHalignment(searchFacilityBtn, HPos.CENTER);
			
			hospitalPane.setCenter(facilityNavBar);
			
			Button backFacilityBtn = new Button("Back");
			hospitalBottom.getChildren().clear();
			hospitalBottom.getChildren().addAll(dateText, homeBtn,backFacilityBtn);
			backFacilityBtn.setVisible(false);

			//Back Facility Page Button On-Click Event Handling
			backFacilityBtn.setOnAction(ef0 -> {
				backFacilityBtn.setVisible(false);
				hospitalPane.setCenter(facilityNavBar);
			});
			
			//Add Facility Record Button On-Click Event Handling
			addFacilityBtn.setOnAction(ef1 -> {
				backFacilityBtn.setVisible(true);
				facilities[countElement[5] - 1].newRecord(facilities, countElement, hospitalPane);
			});
			
			//Display Facility Record Button On-Click Event Handling
			displayFacilityBtn.setOnAction(em2 -> {
				backFacilityBtn.setVisible(true);
				facilities[countElement[5] - 1].showInfo(facilities, hospitalPane);
			});
			
			//Search Facility Record Button On-Click Event Handling
			searchFacilityBtn.setOnAction(em3 -> {
				backFacilityBtn.setVisible(true);
				facilities[countElement[5] - 1].searchGUI(facilities, arraySize, countElement, hospitalPane);
			});
		});
		
		//Scene
		Scene hospitalManagement = new Scene(hospitalPane, 800, 500);
		primaryStage.setTitle("Hospital Management Application");
		primaryStage.setScene(hospitalManagement);
		primaryStage.setMinWidth(500);
		primaryStage.setMinHeight(540);
		primaryStage.show();

	}
	public static void main(String[] args) {
		launch(args);
	}
}