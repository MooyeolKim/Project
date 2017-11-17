#ifndef _SCOREMANAGER_H_
#define _SCOREMANAGER_H_

#include "Score.h"

class ScoreManager
{
public:
	ScoreManager(void);
	void LinePrint(void);
	void gotoxy(int x, int y);
	int ShowMenu(void);
	void InputStudent(void);
	void InputSubject(void);
	void InputScore(void);
	void OutputScore(void);
	void FileScore(void);
private:
	Score *pSc[MAX_STD];
	char *subject[MAX_SBJ];
	int std_index;
	int sbj_index;
};

#endif