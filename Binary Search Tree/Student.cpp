#include <iostream>

#include "Student.h"


using namespace std;


Student::Student()
{
	strcpy(name, " ");
	id = 0;
	strcpy(address, " ");
	strcpy(DOB, " ");
	strcpy(course, " ");
	strcpy(phone_no, " ");
	cgpa = 0.0;

}


void Student::print(ostream &out)
{
	out << "\nName: " << name;
	out << "\nID: " << id;
	out << "\nAddress: " << address;
	out << "\nDate of Birth: " << DOB;
	out << "\nPhone No: " << phone_no;
	out << "\nCourse: " << course;
	out << "\nCGPA: " << cgpa;
	out << "\n";

}


bool Student::compare1(Student p2)
{
	if (id > p2.id)
		return true;
	return false;

}


bool Student::compare2(Student p2)
{
	if (id == p2.id)
		return true;
	return false;
}



bool Student::compare3(int id2)
{
	if (id == id2)
		return true;
	return false;
}
