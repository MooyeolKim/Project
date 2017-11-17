#include "Student.h"

Student::Student(void)
{
}

Student::Student(int n, char *name)
{
	num = n;
	SetName(name);
}

void Student::SetNum(int n)
{
	num = n;
}

void Student::SetName(char *str)
{
	name = new char[strlen(str)+1];
	strcpy(name, str);
}

int Student::GetNum(void)
{
	return num;
}

char *Student::GetName(void)
{
	return name;
}