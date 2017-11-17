#ifndef _SCORE_H_
#define _SCORE_H_

#include "Student.h"

class Score : public Student
{
public:
	Score(void);
	Score(int num, char *name);
	void SetScore(int i, int sc);
	void SetIsScore(bool b);
	void SetAverage(double ave);
	int GetScore(int i);
	bool GetIsScore(void);
	double GetAverage(void);
private:
	int score[MAX_SBJ];
	bool isScore;
	double average;
};

#endif