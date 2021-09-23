#include <iostream>
#include <stdlib.h>
#include <fstream>
#include <cstring>
#include <string>
#include <iomanip>
#include <cmath>
using namespace std;

//Global variable
const int SIZE = 100;
const int amtSubjects = 7;

void design();

// (1) Insert
void insert(string[], string[], int[], int[][amtSubjects], int&);

// (2) Filter & Search
void filter(string[], string[], int[], int[][amtSubjects], int&);
void search(string[], string[], int[], int[][amtSubjects], int&);

// (3) Modify
void showList(string[], string[], int[], int&);
void edit(string[], string[], int[], int[][amtSubjects], int&, string&, int&);
void updatedList(string[], string[], int[], int[][amtSubjects], int&);
void deleteRecord(string[], string[], int[], int[][amtSubjects], int&, string&);
void reload(string[], string[], int[], int[][amtSubjects], int&);

// (4) View Result
void calculation(int[][amtSubjects], int&, double[amtSubjects], double[amtSubjects], int[amtSubjects], string[], string[amtSubjects]);
void display_result(int[][amtSubjects], double[amtSubjects], double[amtSubjects], int[amtSubjects], string[], string[]);
void view_result();

int main(void) {
	string ID[SIZE], name[SIZE];
	int form[SIZE], subject[SIZE][amtSubjects];
	int z = 0, count = -1;
	string choice, subChoice, editID;
	bool error = false;

	//Load data from text file to arrays if the file is successfully installed
	ifstream inFile("student.txt");
	if (inFile.is_open()) {
		while (!inFile.eof()) {
			inFile >> ID[z];
			inFile.ignore();

			getline(inFile, name[z]);
			inFile >> form[z];
			for (int j = 0; j < amtSubjects; j++) {
				inFile >> subject[z][j];
			}
			count += 1;
			z++;
		}
		inFile.close();
	}
	else {
		design();
		cout << "__________________________________________________________" << endl;
		cout << "\nFailed to load the file due to the file is not existing.\nPlease try to reinstall it again.\n" << endl;
		error = true;
		system("PAUSE");
	}
	//Main Program 
	if (!error) {
		do {
			error = false;
			design();
			cout << "_________________________________________________________ " << endl;
			cout << "|                                                        |" << endl;
			cout << "|   1. Insert                                            |" << endl;
			cout << "|   2. Filter / Search                                   |" << endl;
			cout << "|   3. Modify                                            |" << endl;
			cout << "|   4. View Results                                      |" << endl;
			cout << "|                                                        |" << endl;
			cout << "|   0. Exit                                              | " << endl;
			cout << "|________________________________________________________| " << endl;
			cout << "\n Choice: ";
			cin >> choice;

			// (1) Insert Function
			if (choice == "1") {
				insert(ID, name, form, subject, count);
				system("CLS");
			}
			// (2) Filter & Search Function
			else if (choice == "2") {
				while (!error) {
					system("CLS");
					design();
					cout << "\n (2) Filter / Search" << endl;
					cout << "_________________________________________________________" << endl;
					cout << "|                                                        |" << endl;
					cout << "|   1.  Filter                                           |" << endl;
					cout << "|   2.  Search                                           |" << endl;
					cout << "|                                                        |" << endl;
					cout << "|   0.  Back to Main Menu                                |" << endl;
					cout << "|________________________________________________________|" << endl;
					cout << "\n Choice: ";
					cin >> subChoice;

					// (a) Filter
					if (subChoice == "1") {
						filter(ID, name, form, subject, count);
						system("PAUSE");
					}
					// (b) Search
					else if (subChoice == "2") {
						cin.ignore();
						search(ID, name, form, subject, count);
						system("PAUSE");
					}

					// (c) Back to main menu
					else if (subChoice == "0") {
						error = true;
						system("CLS");
					}
					else {
						cout << "\nInvalid input. Please try it again." << endl;
						system("PAUSE");
					}
				}
			}
			// (3) Modify Function
			else if (choice == "3") {
				while (!error) {
					system("CLS");
					design();
					cout << "\n (3) Modify" << endl;
					cout << "_________________________________________________________" << endl;
					cout << "|                                                        |" << endl;
					cout << "|   1.  Edit                                             |" << endl;
					cout << "|   2.  Delete                                           |" << endl;
					cout << "|                                                        |" << endl;
					cout << "|   0.  Back to Main Menu                                |" << endl;
					cout << "|________________________________________________________|" << endl;
					cout << "\n Choice: ";
					cin >> subChoice;

					// (a) Edit
					if (subChoice == "1") {
						showList(ID, name, form, count);
						cin >> editID;
						error = true;

						//Validation of student ID
						for (int b = 0; b < count; b++) {
							if (ID[b] == editID) {
								edit(ID, name, form, subject, count, editID, b);
								error = false;
							}
						}
						if (error) {
							cout << "\nInvalid Student ID. Please Select the Choice again and Input Valid ID that appear on the list." << endl;
							error = false;
							system("PAUSE");
						}
						else {
							updatedList(ID, name, form, subject, count);
							system("PAUSE");
						}
					}
					// (b) Delete
					else if (subChoice == "2") {
						showList(ID, name, form, count);
						cin >> editID;
						error = true;

						//Validation of student ID
						for (int i = 0; i < count; i++) {
							if (ID[i] == editID) {
								error = false;
							}
						}
						if (error) {
							cout << "\nInvalid Student ID. Please Select the Choice again and Input Valid ID that appear on the list." << endl;
							error = false;
							system("PAUSE");
						}
						else {
							deleteRecord(ID, name, form, subject, count, editID);
							reload(ID, name, form, subject, count);
							cout << " Successfully deleted." << endl;
							updatedList(ID, name, form, subject, count);
							system("PAUSE");
						}
					}
					// (c) Back to Main Menu
					else if (subChoice == "0") {
						error = true;
						system("CLS");
					}
					else {
						cout << "\nInvalid input. Please try it again." << endl;
						system("PAUSE");
					}
				}
			}
			// (4) View Results Function
			else if (choice == "4") {
				view_result();
				system("CLS");
			}
			// (0) Close Program
			else if (choice == "0") {
				system("CLS");
				design();
				cout << "__________________________________________________________" << endl;
				cout << "\nThank you for Visiting our program today!\nEnjoy your Good Day!\n" << endl;
				system("PAUSE");
			}
			else {
				cout << "\nInvalid input. Please try it again." << endl;
				system("PAUSE");
				system("CLS");
			}
		} while (choice != "0");
	}
	return 0;
}

//Function for design main menu
void design() {
	cout << "__________________________________________________________" << endl;
	cout << "       =========== ===   ===    =========== ===========" << endl;
	cout << "           | |     | |   | |        | |         ||" << endl;
	cout << "          | |     | |  | |         | |         ||" << endl;
	cout << "         | |     | ||||           | |         ||" << endl;
	cout << "        | |     | |  | |         | |         ||" << endl;
	cout << "   | || |      | |    | |   | || |          ||" << endl;
	cout << "   ======     ==========    ======         ==" << endl;
	cout << "__________________________________________________________" << endl;
}

// (1) Insert Function 
void insert(string ID[], string name[], int form[], int subject[][amtSubjects], int& count) {
	int addSubjectList[amtSubjects] = { 0,0,0,0,0,0,0 };
	string subjectList[amtSubjects] = { "BM       ", "BI       ", "BC       ", "Math     ", "Sci      ", "Sejarah  ", "Geo      " };
	string addName, repeat;
	char addID[20];
	int addForm, currentCount;
	bool exist = false, loop = false, invalid = false, invalid2 = false;
	ofstream addFile("student.txt", ios::app);

	do {
		system("CLS");
		design();
		cout << "\n (1) Insert" << endl;
		cout << "__________________________________________________________" << endl;
		cout << "\n Please Enter Student Information and Marks for New Student.\n" << endl;
		cout << "Student ID (xxACBxxxxx)  : ";
		cin >> addID;

		//Validation to student ID format
		if (strlen(addID) != 10) { // Length = 10
			invalid = true;
		}
		else if (isdigit(addID[0]) && isdigit(addID[1])) { //For digits before ACB
			for (int j = 2; j < 5; j++) {
				if (isalpha(addID[j])) { //For alphabets (ACB)
					invalid = false;
				}
				else {
					invalid = true;
					break;
				}
			}
			for (int i = 5; i < 10; i++) {
				if (isdigit(addID[i])) { //For digits after ACB
					invalid2 = false;
				}
				else {
					invalid2 = true;
					break;
				}
			}
		}
		else {
			invalid = true;
		}

		if (invalid || invalid2) {
			cout << "\nInvalid student ID due to the wrong format (xxACBxxxxx)." << endl;
			system("PAUSE");
		}
		else {
			//Uppercase the middle "ACB"
			for (int a = 0; a < 10; a++) {
				addID[a] = toupper(addID[a]);
			}

			//Check Student ID exist or not
			for (int b = 0; b < count; b++) {
				if (ID[b] == addID) {
					exist = true;
				}
			}
			if (exist) {
				cout << "\nThe Student ID is Duplicated, Please key in Another ID" << endl;
				system("PAUSE");
				exist = false;
			}
			else {
				cout << "Student Name : ";
				cin.ignore();
				getline(cin, addName);

				cout << "Form (1 - 3) : ";
				cin >> addForm;

				//Validation to form
				if (cin.fail()) {
					cout << "\nThe Form should be Integer type. Please try it all again start from Student ID." << endl;
					system("PAUSE");
					cin.clear();
					cin.ignore(50, '\n');
					continue;
				}
				else if (addForm < 1 || addForm >3) {
					cout << "\nThe Range of Form must be in 1 to 3. Please try it all again start from Student ID." << endl;
					system("PAUSE");
					continue;
				}

				cout << "Subjects mark (0 to 100)" << endl;
				for (int c = 0; c < amtSubjects; c++) {
					cout << subjectList[c] << ": ";
					cin >> addSubjectList[c];

					//Validation to subjects' marks
					if (cin.fail()) {
						invalid = true;
						cout << "\nThe Subject's Marks should be Integer type. Please try it all again start from Student ID." << endl;
						system("PAUSE");
						cin.clear();
						cin.ignore(50, '\n');
						break;
					}
					else if (addSubjectList[c] < 0 || addSubjectList[c] > 100) {
						invalid = true;
						cout << "\nThe Range of subject's Mark must be in 0 to 100. Please try it all again start from Student ID." << endl;
						system("PAUSE");
						break;
					}
				}
				if (!invalid) {
					currentCount = count;

					//Assign new student to array
					ID[currentCount] = addID;
					name[currentCount] = addName;
					form[currentCount] = addForm;
					for (int d = 0; d < amtSubjects; d++) {
						subject[currentCount][d] = addSubjectList[d];
					}

					//Write it in text file
					addFile << ID[currentCount] << endl;
					addFile << name[currentCount] << endl;
					addFile << form[currentCount] << endl;
					for (int e = 0; e < amtSubjects; e++) {
						addFile << subject[currentCount][e] << " ";
					}
					addFile << endl;
					count++;
					loop = true;
				}
			}
			while (loop) { //For user repeat to key in if the input is wrong (For validation)
				system("CLS");
				design();
				cout << "__________________________________________________________" << endl;
				cout << "\nDo You Want To Add Another Record? (Yes / No)" << endl;
				cin >> repeat;

				//Validation to ask for another record
				if (repeat == "no" || repeat == "NO" || repeat == "No" || repeat == "nO") {
					addFile.close();
					repeat = "No";
					cout << "__________________________________________________________" << endl;
					cout << "Information Updated Successfully!" << endl;
					system("PAUSE");
					loop = false;
				}
				else if (repeat == "yes" || repeat == "YES" || repeat == "Yes" || repeat == "YEs"
					|| repeat == "yES" || repeat == "yEs" || repeat == "yeS" || repeat == "YeS") {
					loop = false;
				}
				else {
					cout << "\nInvalid input. Please correct it the input format (Yes / No)." << endl;
					system("PAUSE");
				}
			}
		}
	} while (repeat != "No");
}

// (2) Filter & Search Function
void filter(string ID[], string name[], int form[], int subject[][amtSubjects], int& count) {
	string subjectList[amtSubjects] = { "BM ", "BI ", "BC ", "Math", "Sci", "Sejarah", "Geo" };
	int indexSubject[amtSubjects] = { 0, 0, 0, 0, 0, 0, 0 };
	int filterForm, found;
	int countSubject = 0, n = 0;
	char filterSubject[20];
	bool invalid = false, invalid2 = false, noFound = true;

	while (invalid == false) {
		invalid = true;
		invalid2 = true;
		system("CLS");
		design();
		cout << "__________________________________________________________" << endl;
		cout << " Please key in the Information that you want to Filter." << endl;
		cout << "\nForm (1 - 3) : ";
		cin >> filterForm;

		if (cin.fail()) { //Wrong input data type
			cin.clear();
			cin.ignore(50, '\n');
			break;
		}
		//Validation to form
		else if (filterForm > 0 && filterForm < 4) {
			cout << "\n Subjects List: " << endl;
			cout << " [ BM, BI, BC, Math, Sci, Sejarah, Geo ]" << endl;

			do { //User able to key in again if wrong input (For validation)
				cout << "\nHow many Subject(s) that you want to Filter? (1 to 7) : ";
				cin >> countSubject;

				if (cin.fail()) { //Wrong input data type
					cin.clear();
					cin.ignore(50, '\n');
					break;
				}
				//Validation to the amount of filter subject(s)
				else if (countSubject > 0 && countSubject < 8) {
					while (n < countSubject) { //User can keep input the subject since the user is asked "how many subjects that want to filter" before
						cout << "Subject(s) : ";
						cin >> filterSubject;

						//Uppercase character to fit in the subjectList above
						if (strlen(filterSubject) == 2) {
							for (int o = 0; o < 20; o++) {
								filterSubject[o] = toupper(filterSubject[o]);
							}
						}
						else {
							filterSubject[0] = toupper(filterSubject[0]);
						}

						//Find the subject(s)
						for (int p = 0; p < amtSubjects; p++) {
							found = subjectList[p].find(filterSubject);
							if (found != string::npos) {
								indexSubject[p]++;
								invalid = false;
								invalid2 = false;
							}
						}

						//Exit the while loop "(n < countSubject)"
						if (invalid) {
							countSubject = 0;
						}
						else {
							n++;
						}
					}

					//Validation to the subject whether exist in the list or not
					if (invalid) {
						cout << "\nInvalid Subject due to the Subject may Not be in the List.\nPlease Select the Amount and try it again." << endl;
					}
					else {          //Print out the filtered list
						cout << "\n__________________________________________________________" << endl;
						cout << "Filtered List: " << endl;
						cout << "____________________________________________________________________________________" << endl;
						cout << "|     No.     |                  Student Name                  |Form |  Student ID  | " << endl;
						cout << "|-----------------------------------------------------------------------------------|" << endl;
						int q = 0;
						for (int r = 0; r < count; r++) {
							if (filterForm == form[r]) {
								noFound = false;
								cout << "|      " << left << setw(3) << (q + 1) << "    | " << setw(46) << name[r] << " |  " << form[r] << "  |  " << ID[r] << "  | " << endl;
								cout << "|___________________________________________________________________________________|" << endl;
								for (int s = 0; s < amtSubjects; s++) {
									if (indexSubject[s] > 0) {
										cout << "| " << setw(11) << subjectList[s] << " : " << setw(3) << subject[r][s] <<
											"                                                                 |" << endl;
									}
								}
								cout << "|___________________________________________________________________________________|" << endl;
								q++;
							}
						}
						if (noFound) { //If not record found
							cout << "|                           This Form is currently Empty                            |" << endl;
							cout << "|___________________________________________________________________________________|" << endl;
						}
					}
				}
				else { //Message of invalid filter range
					cout << "\nThe Amount of Subjects that you want to Filter must be in the Range of 1 to 7." << endl;
					cout << "Please try it again." << endl;
				}
			} while (invalid == true);
		}
		else { //Message of invalid form range
			cout << "\nThe Form must be in Form 1 until Form 3. Please try it again." << endl;
			system("PAUSE");
			invalid = false;
		}
		if (invalid) { //Validation to the "countSubject" that need to be integer data type
			invalid = false;
			cout << "\nThe Amount of Subjects that you want to Filter should be Integer." << endl;
			cout << "Please Select the Form and try it again." << endl;
			system("PAUSE");
		}
		if (invalid2 == false) {
			invalid = true; //Exit main loop of this function;
		}
	}
	if (invalid2) { //Validation to the "filterForm" that need to be integer data type
		cout << "\nThe Form should be Integer. Please Select the Choice and try it again." << endl;
		invalid = false;
	}
}
void search(string ID[], string name[], int form[], int subject[][amtSubjects], int& count) {
	// This search only supports 10 similar name from the list and up to down
	int indexName[10] = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, };

	int foundName;
	char searchName[25];
	bool invalid = false, invalid2 = false;

	while (invalid == false) {
		invalid = true;
		invalid2 = true;
		system("CLS");
		design();
		cout << "\n-This Search only supports 10 Similar Names from the List-" << endl;
		cout << "__________________________________________________________" << endl;
		cout << "Please key in the keyword or surname that you want to search." << endl;
		cout << "                             OR\nPress Enter to View All Student if any." << endl;
		cin.getline(searchName, 25);

		//Find the similar name
		for (int u = 0; u < 10; u++) {
			foundName = name[u].find(searchName);
			if (foundName != string::npos) {
				indexName[u]++;
				invalid2 = false;
			}
		}

		//This part is for those user key in all lowercase name but the 1st alphabet of name in the list is uppercase.
		//Uppercase 1st character
		searchName[0] = toupper(searchName[0]);
		//Find the similar name 
		for (int u = 0; u < 10; u++) {
			foundName = name[u].find(searchName);
			if (foundName != string::npos) {
				indexName[u]++;
				invalid2 = false;
			}
		}
		//Validation to the name appear on the list or not
		if (invalid2 == false) {   //Print out the result
			int y = 0;
			cout << "\n__________________________________________________________" << endl;
			cout << "Search Result:" << endl;
			cout << "_________________________________________________________________________________________________________________" << endl;
			cout << "| No. |                  Student Name                  |Form |  Student ID  | BM   BI   BC   Math Sci  Sej  Geo  |" << endl;
			cout << "|----------------------------------------------------------------------------------------------------------------|" << endl;
			for (int v = 0; v < count; v++) {
				if (indexName[v] > 0) {
					cout << "| " << left << setw(3) << (y + 1) << " | " << setw(46) << name[v] << " |  " << form[v] << "  |  " << ID[v] << "  | ";
					for (int z = 0; z < amtSubjects; z++) {
						cout << setw(3) << subject[v][z] << "  ";
					}
					cout << "|" << endl;
					y++;
				}
			}
			cout << "|_____|________________________________________________|_____|______________|____________________________________|" << endl;
		}
		else {
			cout << "\nThe keyword (name) is not appear on the list. Please try it again." << endl;
			system("PAUSE");
			invalid = false;
		}
	}
}

// (3) Modify Function
void showList(string ID[], string name[], int form[], int& count) {
	system("CLS");
	design();
	if (count != 0) {
		cout << "\nCurrent list:" << endl;
		cout << "_________________________________________________________________________________________________________________" << endl;
		cout << "| No. |                          Student Name                             |   Form.   |        Student ID        |" << endl;
		cout << "|----------------------------------------------------------------------------------------------------------------|" << endl;
		for (int a = 0; a < count; a++) {
			cout << "| " << left << setw(3) << (a + 1) << " | " << setw(65) << name[a] << " |     " << form[a] << "     |        " << ID[a] << "        |" << endl;
		}
		cout << "|_____|___________________________________________________________________|___________|__________________________|" << endl;
		cout << " Please key in the Student ID that you want to edit: ";
	}
	else {
		cout << "\nCurrent list:" << endl;
		cout << "_________________________________________________________________________________________________________________" << endl;
		cout << "| No. |                          Student Name                             |   Form.   |        Student ID        |" << endl;
		cout << "|----------------------------------------------------------------------------------------------------------------|" << endl;
		cout << "|                             No Record is Found                                                                 |" << endl;
		cout << "|________________________________________________________________________________________________________________|" << endl;
		cout << " Please Press any Alphabet(s) or Number(s) to Continue..." << endl;
	}
}
void edit(string ID[], string name[], int form[], int subject[][amtSubjects], int& count, string& editID, int& b) {
	string subjectList[amtSubjects] = { "BM", "BI", "BC", "Math", "Sci", "Sejarah", "Geo" };
	string attChoice, Continue;
	char editSubject[20];
	int editForm, editMark;
	bool invalid = false;
	ofstream writeFile("student.txt");

	do {
		invalid = true;
		system("CLS");
		design();
		cout << "\n  Student ID : " << editID << endl;
		cout << "_________________________________________________________" << endl;
		cout << "|                                                        |" << endl;
		cout << "|   1.  Name                                             |" << endl;
		cout << "|   2.  Form                                             |" << endl;
		cout << "|   3.  Marks                                            |" << endl;
		cout << "|________________________________________________________|" << endl;
		cout << "\n Please Select the Attribute that you want to Edit: ";
		cin >> attChoice;

		//Attribute: Name
		if (attChoice == "1") {
			cout << "\nPlease Update the Name: ";
			cin.ignore();
			getline(cin, name[b]);
			cout << "Information Updated Successfully!" << endl;
			invalid = false;
		}
		//Attribute: Form
		else if (attChoice == "2") {
			while (invalid) { //If user wrong input, able to key in again (For validation purpose)
				cout << "\nPlease Update the Form (1 - 3) : ";
				cin >> editForm;

				if (cin.fail()) { //Wrong input data type
					cin.clear();
					cin.ignore(50, '\n');
					break;
				}
				//Validation of form
				else if (editForm > 0 && editForm < 4) {
					form[b] = editForm;
					cout << "Information Updated Successfully!" << endl;
					cin.clear();
					cin.ignore(50, '\n');
					invalid = false;
				}
				else {
					cout << "\nThe Form must be in Form 1 until Form 3. Please try it again." << endl;
				}
			}
		}
		//Attribute: Marks
		else if (attChoice == "3") {
			cout << "\n Subjects List: " << endl;
			cout << " [ BM, BI, BC, Math, Sci, Sejarah, Geo ]" << endl;
			cout << "\nPlease Enter the Subject that you want to Edit: ";
			cin >> editSubject;

			//Uppercase to Subjects
			if (strlen(editSubject) == 2) {
				for (int c = 0; c < 20; c++) {
					editSubject[c] = toupper(editSubject[c]);
				}
			}
			else {
				editSubject[0] = toupper(editSubject[0]);
			}
			//Matching subject and Input marks
			for (int d = 0; d < amtSubjects; d++) {
				if (subjectList[d] == editSubject) {
					cout << "Please Update the Marks for " << editSubject << " (0 to 100) : ";
					cin >> editMark;
					if (cin.fail()) { //Wrong input data type
						cin.clear();
						cin.ignore(50, '\n');
						break;
					}
					//Validation of marks
					else if (editMark >= 0 && editMark < 101) {
						subject[b][d] = editMark;
						cout << "Information Updated Successfully!" << endl;
						cin.clear();
						cin.ignore(50, '\n');
						invalid = false;
					}
				}
			}
		}
		if (invalid) { //Message of wrong input data type
			cout << "\nInvalid Form / Subject / Score. Please Select the Attribute and Try it again." << endl;
			system("PAUSE");
			invalid = false;
		}
		else {
			//Ask user to continue editing with this student or not?
			while (invalid != true) { //For user key in again if input is wrong (For validation)
				cout << "\nDo you still have anything to Edit for this Student? (Yes / No)" << endl;
				cin >> Continue;

				if (Continue == "no" || Continue == "NO" || Continue == "No" || Continue == "nO") {
					Continue = "No";
					//Update information to the students
					for (int e = 0; e < count; e++) {
						writeFile << ID[e] << endl;
						writeFile << name[e] << endl;
						writeFile << form[e] << endl;
						for (int f = 0; f < amtSubjects; f++) {
							writeFile << subject[e][f] << " ";
						}
						writeFile << endl;
					}
					writeFile.close();
					invalid = true;
				}
				else if (Continue == "yes" || Continue == "YES" || Continue == "Yes" || Continue == "YEs"
					|| Continue == "yES" || Continue == "yEs" || Continue == "yeS" || Continue == "YeS") {
					invalid = true;
				}
				else { //Message of wrong input format
					cout << "\nInvalid Input. Please Correct it the Input Format (Yes / No)." << endl;
				}
			}
			invalid = false;
		}
	} while (Continue != "No");
}
void updatedList(string ID[], string name[], int form[], int subject[][amtSubjects], int& count) {
	cout << "\n__________________________________________________________" << endl;
	cout << "Successfully updated!" << endl;
	cout << "_________________________________________________________________________________________________________________" << endl;
	cout << "| No. |                  Student Name                  |Form |  Student ID  | BM   BI   BC   Math Sci  Sej  Geo  |" << endl;
	cout << "|----------------------------------------------------------------------------------------------------------------|" << endl;
	for (int g = 0; g < count; g++) {
		cout << "| " << setw(3) << (g + 1) << " | " << setw(46) << name[g] << " |  " << form[g] << "  |  " << ID[g] << "  | ";
		for (int h = 0; h < amtSubjects; h++) {
			cout << setw(3) << subject[g][h] << "  ";
		}
		cout << "|" << endl;
	}
	cout << "|_____|________________________________________________|_____|______________|____________________________________|" << endl;
}
void deleteRecord(string ID[], string name[], int form[], int subject[][amtSubjects], int& count, string& editID) {
	ofstream rewriteFile("student.txt");
	//Delect Function
	for (int j = 0; j < count; j++) {
		if (ID[j] == editID) {
			continue;
		}
		else {
			rewriteFile << ID[j] << endl;
			rewriteFile << name[j] << endl;
			rewriteFile << form[j] << endl;
			for (int k = 0; k < amtSubjects; k++) {
				rewriteFile << subject[j][k] << " ";
			}
			rewriteFile << endl;
		}
	}
	rewriteFile.close();
	count--;
}
void reload(string ID[], string name[], int form[], int subject[][amtSubjects], int& count) {
	ifstream loadFile("student.txt");
	int l = 0;
	//Take the data from text file and store it into arrays (Update the arrays)
	while (!loadFile.eof()) {
		loadFile >> ID[l];

		loadFile.ignore();
		getline(loadFile, name[l]);
		loadFile >> form[l];
		for (int m = 0; m < amtSubjects; m++) {
			loadFile >> subject[l][m];
		}
		l++;
	}
	loadFile.close();
}

// (4) View Results Function
void calculation(int marks[][amtSubjects], int& countForm, double average[amtSubjects], double SD[amtSubjects], int highest_score[amtSubjects], string name[], string hname[amtSubjects]) {
	//Clear the average and standard deviation to 0
	for (int i = 0; i < 7; i++) {
		average[i] = 0;
		SD[i] = 0;
		highest_score[i] = 0;
	}

	//Calculate average
	for (int i = 0; i < 7; i++) {
		for (int j = 0; j < countForm; j++) {
			average[i] += marks[j][i];
		}
		average[i] = average[i] / countForm;
	}

	//Calculate standard deviation
	for (int i = 0; i < 7; i++) {
		for (int j = 0; j < countForm; j++) {
			SD[i] += pow(marks[j][i] - average[i], 2);
		}
		SD[i] = sqrt(SD[i] / countForm);
	}

	//Detect the highest score and the student's name
	for (int i = 0; i < 7; i++) {
		for (int j = 0; j < countForm; j++) {
			if (marks[j][i] > highest_score[i]) {
				highest_score[i] = marks[j][i];
				hname[i] = name[j];
			}
		}
	}
}
void display_result(int marks[][amtSubjects], double average[amtSubjects], double SD[amtSubjects], int highest_score[amtSubjects], string name[], string hname[]) {
	string course[7] = { "BM","BI","BC","Math","Sci","Sejarah","Geo" };

	cout << setprecision(2) << fixed;
	for (int i = 0; i < 7; i++) {
		cout << " __________________________________________________" << endl;
		cout << "| " << course[i] << endl;
		cout << "| Average            | " << average[i] << endl;
		cout << "| Standard Deviation | " << SD[i] << endl;
		cout << "| Highest score      | " << highest_score[i] << endl;
		cout << "| Student Name       | " << hname[i] << endl;
		cout << endl;
	}
}
void view_result() {

	char repeat;
	int form = 0;
	int countForm = 0;
	string id[SIZE], name[SIZE], choice, hname[amtSubjects];
	int marks[SIZE][amtSubjects], highest_score[amtSubjects] = { 0 };
	double average[amtSubjects] = { 0 }, SD[amtSubjects] = { 0 };
	string choiceFormList[3] = {"1", "2", "3" };
	bool error = true;

	do {
		system("CLS");
		design();
		cout << "\n (4) Students' Results" << endl;
		cout << "__________________________________________________________" << endl;
		cout << " Form List = [ 1 , 2 , 3 , All ]" << endl;
		cout << " Please Select the Form that you wish to View: ";
		cin >> choice;
		error = true;

		for (int a = 0; a < 3; a++) {
			//Display information of form 1 student
			if (choice == choiceFormList[a]) {
				ifstream inFile("student.txt");

				while (!inFile.eof()) {
					inFile >> id[countForm];
					inFile.ignore();
					getline(inFile, name[countForm]);
					inFile >> form;
					for (int i = 0; i < 7; i++) {
						inFile >> marks[countForm][i];
					}

					if (form == stoi(choiceFormList[a]))
						countForm++;
				}
				inFile.close();

				if (form == stoi(choiceFormList[a])) {
					countForm--; //Remove count of the extra spacing in the file
				}
				if (countForm != 0) { //Validation purpose
					calculation(marks, countForm, average, SD, highest_score, name, hname);
					system("CLS");
					design();
					cout << "\n Form : " << choice << endl;
					cout << "__________________________________________________________\n" << endl;
					display_result(marks, average, SD, highest_score, name, hname);
					countForm = 0;
					error = false;
				}
				else {
					cout << "\n Currently No Student in Form " << choice << "." << endl;
					error = false;
				}
			}
		}
		//Display all student information
		if (choice == "All" || choice == "all" || choice == "ALL") {
			ifstream inFile("student.txt");

			while (!inFile.eof()) {
				inFile >> id[countForm];
				inFile.ignore();
				getline(inFile, name[countForm]);
				inFile >> form;
				for (int i = 0; i < 7; i++) {
					inFile >> marks[countForm][i];
				}
				countForm++;
			}
			inFile.close();
			countForm--; //Remove count of the extra spacing in the file

			if (countForm != 0) { //Validation purpose
				calculation(marks, countForm, average, SD, highest_score, name, hname);
				system("CLS");
				design();
				cout << "\n Form : All" << endl;
				cout << "__________________________________________________________\n" << endl;
				display_result(marks, average, SD, highest_score, name, hname);
				countForm = 0;
				error = false;
			}
			else {
				cout << "\n Currently No Student in the list." << endl;
				error = false;
			}
		}
		else if (error){
			cout << "\n Invalid Input." << endl;
		}
		
		cout << "__________________________________________________________" << endl;
		cout << "Do You Want To Check Another Record? ( Y / N )" << endl;
		cin >> repeat;
		repeat = toupper(repeat);
		cout << endl;

		//Validation to repeat
		while (repeat != 'Y' && repeat != 'N') {
			cout << " Invalid Input." << endl;
			cout << "Please try it again. ( Y / N )" << endl;
			cin >> repeat;
			repeat = toupper(repeat);
		}
	} while (repeat == 'Y');
}
