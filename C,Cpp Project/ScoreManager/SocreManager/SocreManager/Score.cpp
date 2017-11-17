#include "Score.h"

Score::Score(void)
{
}

Score::Score(int num, char *name) : Student(num, name), isScore(false)
{
	//isScore = false;
}

void Score::SetScore(int i, int sc)
{
	score[i] = sc;
}

void Score::SetIsScore(bool b)
{
	isScore = b;
}

void Score::SetAverage(double ave)
{
	average = ave;
}

int Score::GetScore(int i)
{
	return score[i];
}

bool Score::GetIsScore(void)
{
	return isScore;
}

double Score::GetAverage(void)
{
	return average;
}