#include <stdio.h>
#include <conio.h>
#include <Windows.h>

typedef struct _TRAINING_INFO_
{
	int nTrainingTime;
	int nRestTime;
	int nRepeat;
} TRAINING_INFO;

TRAINING_INFO g_nTrainingInfo;
char *g_strMainMenu[7] = {"운동 시간 설정", "휴식 시간 설정", "반복 수 설정", "운동 시작", "운동 정보 출력", "스톱 워치", "종료"};
char g_strTime[10][5][10] = {
						{"■■■",
						 "■  ■",
						 "■  ■",
						 "■  ■",
						 "■■■"
						},
						{"  ■  ",
						 "  ■  ",
						 "  ■  ",
						 "  ■  ",
						 "  ■  "
						},
						{"■■■",
						 "    ■",
						 "■■■",
						 "■    ",
						 "■■■"
						},
						{"■■■",
						 "    ■",
						 "■■■",
						 "    ■",
						 "■■■"
						},
						{"■  ■",
						 "■  ■",
						 "■■■",
						 "    ■",
						 "    ■"
						},
						{"■■■",
						 "■    ",
						 "■■■",
						 "    ■",
						 "■■■"
						},
						{"■■■",
						 "■    ",
						 "■■■",
						 "■  ■",
						 "■■■"
						},
						{"■■■",
						 "    ■",
						 "    ■",
						 "    ■",
						 "    ■",
						},
						{"■■■",
						 "■  ■",
						 "■■■",
						 "■  ■",
						 "■■■"
						},
						{"■■■",
						 "■  ■",
						 "■■■",
						 "    ■",
						 "■■■"
						}
};

void gotoxy(int x, int y)
{
	COORD CursorPosition = {x, y};
	SetConsoleCursorPosition(GetStdHandle(STD_OUTPUT_HANDLE), CursorPosition);
}

void LinePrint()
{
	printf("┌──────────────────────────┐\n");
	printf("│                                                    │\n");
	printf("│                                                    │\n");
	printf("│                                                    │\n");
	printf("│                                                    │\n");
	printf("│                                                    │\n");
	printf("│                                                    │\n");
	printf("│                                                    │\n");
	printf("│                                                    │\n");
	printf("│                                                    │\n");
	printf("│                                                    │\n");
	printf("│                                                    │\n");
	printf("│                                                    │\n");
	printf("│                                                    │\n");
	printf("│                                                    │\n");
	printf("│                                                    │\n");
	printf("│                                                    │\n");
	printf("│                                                    │\n");
	printf("│                                                    │\n");
	printf("└──────────────────────────┘\n");
}

int MainMenu()
{
	int nKey, i, nCount;
	static int nMenu = 0;
	system("cls");

	LinePrint();
	gotoxy(18, 3);
	printf("★ 트레이닝 타이머 ★");

	nCount = sizeof(g_strMainMenu) / sizeof(char*);

	while(1)
	{
		for(i=0 ; i<nCount ; i++)
		{
			gotoxy(12, i*2+5);
			printf("         %s                  ", g_strMainMenu[i]);
		}

		gotoxy(17, nMenu*2+5);
		printf("☞");
		gotoxy(37, nMenu*2+5);
		printf("☜");

		nKey = _getch();
		switch(nKey)
		{
		case 72: //위쪽 키
			nMenu--;
			if(nMenu < 0)
				nMenu = nCount - 1;
			break;
		case 80:
			nMenu++;
			nMenu = nMenu % nCount;
			break;
		case 13:
			return nMenu;
		}
	}
}

void SetTrainingTime()
{
	system("cls");
	printf("┌──────────────────────────┐\n");
	printf("│                                                    │\n");
	printf("│  운동 시간 설정(분 단위) :                         │\n");
	printf("│                                                    │\n");
	printf("└──────────────────────────┘\n");
	
	gotoxy(30, 2);
	scanf("%d", &g_nTrainingInfo.nTrainingTime);
	fflush(stdin);
}

void SetRestTime()
{
	system("cls");	
	printf("┌──────────────────────────┐\n");
	printf("│                                                    │\n");
	printf("│  휴식 시간 설정(초 단위) :                         │\n");
	printf("│                                                    │\n");
	printf("│  ※ 60초 이하 설정                                 │\n");
	printf("└──────────────────────────┘\n");

	gotoxy(30, 2);
	scanf("%d", &g_nTrainingInfo.nRestTime);
	fflush(stdin);
}

void SetRepeat()
{
	system("cls");
	printf("┌──────────────────────────┐\n");
	printf("│                                                    │\n");
	printf("│  반복 수 설정 :                                    │\n");
	printf("│                                                    │\n");
	printf("└──────────────────────────┘\n");
	
	gotoxy(18, 2);
	scanf("%d", &g_nTrainingInfo.nRepeat);
	fflush(stdin);
}

void TrainingInfoPrint()
{
	int nMin, nSec;

	system("cls");
	printf("┌──────────────────────────┐\n");
	printf("│                                                    │\n");
	printf("│                  운동 정보 출력                    │\n");
	printf("│                                                    │\n");
	printf("│  운동 시간 :                                       │\n");
	printf("│                                                    │\n");
	printf("│  휴식 시간 :                                       │\n");
	printf("│                                                    │\n");
	printf("│  반복 수 :                                         │\n");
	printf("│                                                    │\n");
	printf("│  총 운동 시간 :                                    │\n");
	printf("│                                                    │\n");
	printf("└──────────────────────────┘\n");
	
	gotoxy(19, 4);
	printf("%d 분", g_nTrainingInfo.nTrainingTime);
	gotoxy(19, 6);
	printf("%d 초", g_nTrainingInfo.nRestTime);
	gotoxy(19, 8);
	printf("%d", g_nTrainingInfo.nRepeat);
	
	nMin = (g_nTrainingInfo.nTrainingTime*g_nTrainingInfo.nRepeat) + 
		   (g_nTrainingInfo.nRestTime*(g_nTrainingInfo.nRepeat-1)) / 60;
	nSec = (g_nTrainingInfo.nRestTime*(g_nTrainingInfo.nRepeat-1)) % 60;

	gotoxy(24, 10);
	printf("%d 분 %d 초", nMin, nSec);

	_getch();
}

void TimePrint(int x, int y, int nTime)
{
	int i, j, nNum;
	char cTime[3];

	_itoa(nTime, cTime, 10);
	if(nTime < 10)
	{
		cTime[1] = cTime[0];
		cTime[0] = '0';
	}

	for(i=0 ; i<2 ; i++)
	{
		nNum = cTime[i] - '0';
		if(nNum == 0 && i==0)
		{
			for(j=0 ; j<5; j++)
			{
				gotoxy(x, y+j);
				printf("%s", "      ");
			}
		}
		else
		{
			for(j=0 ; j<5; j++)
			{
				gotoxy(x+i*7 , y+j);
				printf("%s", g_strTime[nNum][j]);
			}
		}
	}
}

int Pause()
{
	int nKey;
	
	gotoxy(7, 16);
	printf(" 다시 시작 하려면 아무키나 입력하세요");
	gotoxy(7, 16);
	scanf("%d", &nKey);
	fflush(stdin);
		
	gotoxy(7, 16);
	printf("                                        \n│");
	
	return nKey;
}

void StopWatch()
{
	int min=0, sec=0, milisec=0;
	int nKey = 0;
	
	system("cls");
	LinePrint();
	gotoxy(9, 2);
	printf("◆◆◆◆◆◆   스톱 워치   ◆◆◆◆◆◆");

	while(1)
	{
		TimePrint(3, 5, min);

		gotoxy(17, 6);
		printf("●");
		gotoxy(17, 8);
		printf("●");
	
		TimePrint(20, 5, sec);

		gotoxy(34, 6);
		printf("●");
		gotoxy(34, 8);
		printf("●");

		TimePrint(37, 5, milisec);

		if(_kbhit())
			nKey = Pause();
		if(nKey == 1)
			break;
		Sleep(10);
		milisec++;
		if(milisec == 100)
		{
			milisec = 0;
			sec++;
		}
		if(sec == 60)
		{
			sec = 0;
			min++;
		}
	}
}

int main(void)
{
	int nMenu, i, nCount;
	int nRunningMin, nRunningSec;
	int nRestSec;
	int nKey = 0;
	
	while(1)
	{
		nMenu = MainMenu();
		switch(nMenu)
		{
		case 0:
			SetTrainingTime();
			break;
		case 1:
			SetRestTime();
			break;
		case 2:
			SetRepeat();
			break;
		case 3:
			for(i=0 ; i<g_nTrainingInfo.nRepeat ; i++)
			{
				nCount = 5;
				system("cls");
				LinePrint();
				gotoxy(9, 2);
				printf("◆◆◆◆◆◆   운동 준비   ◆◆◆◆◆◆");
				
				while(1)
				{
					if(nCount < 0)
						break;

					TimePrint(18, 5, nCount);
					Sleep(1000);
					nCount--;
				}

				nRunningMin = 0;
				nRunningSec = 0;
				system("cls");
				LinePrint();
				gotoxy(18, 2);
				printf("♨♨ 운동 시간 ♨♨");
				gotoxy(5, 13);
				printf("운동 설정 시간 : %d 분  현재 반복 수 : %d", 
						g_nTrainingInfo.nTrainingTime, i+1);

				while(1)
				{
					if(nRunningMin == g_nTrainingInfo.nTrainingTime)
						break;

					TimePrint(13, 5, nRunningMin);

					gotoxy(27, 6);
					printf("●");
					gotoxy(27, 8);
					printf("●");

					TimePrint(30, 5, nRunningSec);

					if(_kbhit())
						nKey = Pause();
					if(nKey == 1)
						return 0;
					Sleep(1000);
					nRunningSec++;
					if(nRunningSec == 60)
					{
						nRunningMin++;
						nRunningSec = 0;
					}
				}


				if(i == (g_nTrainingInfo.nRepeat - 1))
					break;

				system("cls");
				LinePrint();
				gotoxy(18, 2);
				printf("◆◆ 휴식 시간 ◆◆");
				gotoxy(5, 13);
				printf("휴식 설정 시간 : %d 초", g_nTrainingInfo.nRestTime);
				nRestSec = g_nTrainingInfo.nRestTime;

				while(1)
				{
					TimePrint(22, 5, nRestSec);
					Sleep(1000);
					nRestSec--;

					if(nRestSec < 0)
						break;
				}
			}
			break;
		case 4:
			TrainingInfoPrint();
			break;
		case 5:
			StopWatch();
			break;
		case 6:
			system("cls");
			gotoxy(15, 10);
			printf("Good Bye!!");
			_getch();
			exit(1);
		}
	}

	return 0;
}
