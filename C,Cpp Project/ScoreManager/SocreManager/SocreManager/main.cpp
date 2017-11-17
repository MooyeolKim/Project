#include "ScoreManager.h"

enum select {STUDENT = 0, SUBJECT, IN_SCORE, OUT_SCORE, FILE_SCORE, EXIT};

int main(void)
{
	int sel;
	ScoreManager sm;

	while(true)
	{
		sel = sm.ShowMenu();

		switch(sel)
		{
		case STUDENT:
			sm.InputStudent();
			break;
		case SUBJECT:
			sm.InputSubject();
			break;
		case IN_SCORE:
			sm.InputScore();
			break;
		case OUT_SCORE:
			sm.OutputScore();
			break;
		case FILE_SCORE:
			sm.FileScore();
			break;
		case EXIT:
			return 0;
		}
	}
	return 0;
}