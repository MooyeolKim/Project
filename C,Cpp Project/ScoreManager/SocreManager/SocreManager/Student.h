#ifndef _STUDENT_H_
#define _STUDENT_H_

#include <iostream>
#include <iomanip>
#include <fstream>
#include <Windows.h>
#include <conio.h>

using namespace std;

const int MAX_STD = 60; 
const int MAX_SBJ = 10;

class Student
{
public:
	Student(void);
	Student(int n, char *name);
	void SetNum(int n);
	void SetName(char *str);
	int GetNum(void);
	char *GetName(void);
private:
	int num;
	char *name;
};

#endif