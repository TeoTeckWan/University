#ifndef Student_type
#define Student_type


using namespace std;

//student info
struct Student{

	char name[30];	
    int id;
	char address[100];
	char DOB[20];
	char course[5];
	char phone_no[10];
	double cgpa;
	
	Student();
	void print(ostream &);
	bool compare1(Student); //using > to compare 2 students struct variable
	bool compare2(Student); //using == to compare 2 students struct variable
	bool compare3(int); //using == to compare student id with the id passed in
	
};






#endif