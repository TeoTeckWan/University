#include	<iostream>
#include	<fstream>
#include	<cstdlib>
#include	<cstdio>
#include	<ctime>
#include	"BST.h"
#include    "Student.h"

using namespace std;
#define SIZE 40 // Number of student(s) info that can be stored (Array Size)

bool readFile(char *, BST *);
int menu();

int main() {
	BST treeList;
	char fileName[30] = "student.txt";
	int order = 0, source = 0;
	BST treeList2;
	Student student;
	int choose;

	while ((choose = menu()) != 7) {
		// (1) Read Data to BST
		if (choose == 1) {
			if (readFile(fileName, &treeList)) {
				cout << treeList.count << " of students' records successfully read." << endl;
			}
			else {
				cout << "Cannot open the file. Please reupload it again." << endl;
			}
			system("pause");
		}

		// (2) Print Deepest Nodes
		else if (choose == 2) {
			if (!treeList.deepestNodes()) {
				cout << "\nThe tree is currently empty." << endl;
			}
			cout << endl;
			system("pause");
		}

		// (3) Display Student
		else if (choose == 3) {
			if (!treeList.display(order, source)) {
				cout << "\nThe tree is currently empty.\n" << endl;
			}
			system("pause");
		}
		
		// (4) Clone Subtree
		else if (choose == 4) {
			cout << "\nEnter the student ID to create the subtree with the root of that ID: ";
			cin >> student.id;

			treeList2.root = NULL;
			if (treeList2.CloneSubtree(treeList, student)) {
				cout << "\n--------------------------------------------------------------------" << endl;
				cout << "Tree List 1\n" << endl;
				treeList.preOrderPrint();
				cout << "\n--------------------------------------------------------------------" << endl;
				cout << "Tree List 2\n" << endl;
				treeList2.preOrderPrint();
			}
			else {
				cout << "\nThe tree is currently empty or the student ID cannot be found in the tree.\n" << endl;
			}
			system("pause");
		}

		// (5) Print Ancestor
		else if (choose == 5) {
			cout << "\nEnter the student ID to find the ancestor(s) of that ID: ";
			cin >> student.id;

			if (!treeList.printAncestor(student)) {
				cout << "None" << endl;
				cout << "The tree is currently empty or the student ID cannot be found in the tree.";
			}
			cout << "\n\n";
			system("pause");
		}

		// (6) Print Spiral
		else if (choose == 6) {
			if (!treeList.printSpiral()) {
				cout << "The tree is currently empty.\n" << endl;
			}
			system("pause");
		}
		else {
			cout << "\nInvalid input.\n" << endl;
			system("pause");
		}
	}
	return 0;
}

bool readFile(char *filename, BST *t1) {
	Student studentList[SIZE];
	ifstream file;
	char skip[50]; // To skip the unnecessary character from the text file (e.g. StudentID =, Name =, etc.)
	int i = 0; // For insert data into studentList
	int countStudent = 0;

	cout << "\n READ FILE\n" << endl;
	file.open(filename);
	if (!file) {
		return false;
	}
	else {
		while (!file.eof()) { // Take data from text file and store in Student structure array
			file >> skip >> skip;
			file >> studentList[i].id;

			file >> skip >> skip;
			file.ignore();
			file.getline(studentList[i].name, 30);

			file >> skip >> skip;
			file.ignore();
			file.getline(studentList[i].address, 100);

			file >> skip >> skip;
			file >> studentList[i].DOB;

			file >> skip >> skip;
			file >> studentList[i].phone_no;

			file >> skip >> skip;
			file >> studentList[i].course;

			file >> skip >> skip;
			file >> studentList[i].cgpa;

			i++;
			countStudent++;
		}
		file.close();

		// Assign data into BST
		for (int j = 0; j < countStudent; j++) {
			t1->insert(studentList[j]);
		}
		return true;
	}
}

int menu() {
	int choice;

	system("cls");
	cout << "\n Binary Search Tree (BST)\n" << endl;
	cout << " 1. Read Data to BST" << endl;
	cout << " 2. Print Deepest Nodes" << endl;
	cout << " 3. Display Student" << endl;
	cout << " 4. Clone Subtree" << endl;
	cout << " 5. Print Ancestor" << endl;
	cout << " 6. Compute Print Spiral" << endl;
	cout << " 7. Exit" << endl;
	cout << "\n Choice: ";
	cin >> choice;

	return choice;



}
