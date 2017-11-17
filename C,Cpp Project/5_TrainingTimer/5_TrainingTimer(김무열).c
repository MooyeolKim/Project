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
char *g_strMainMenu[7] = {"� �ð� ����", "�޽� �ð� ����", "�ݺ� �� ����", "� ����", "� ���� ���", "���� ��ġ", "����"};
char g_strTime[10][5][10] = {
						{"����",
						 "��  ��",
						 "��  ��",
						 "��  ��",
						 "����"
						},
						{"  ��  ",
						 "  ��  ",
						 "  ��  ",
						 "  ��  ",
						 "  ��  "
						},
						{"����",
						 "    ��",
						 "����",
						 "��    ",
						 "����"
						},
						{"����",
						 "    ��",
						 "����",
						 "    ��",
						 "����"
						},
						{"��  ��",
						 "��  ��",
						 "����",
						 "    ��",
						 "    ��"
						},
						{"����",
						 "��    ",
						 "����",
						 "    ��",
						 "����"
						},
						{"����",
						 "��    ",
						 "����",
						 "��  ��",
						 "����"
						},
						{"����",
						 "    ��",
						 "    ��",
						 "    ��",
						 "    ��",
						},
						{"����",
						 "��  ��",
						 "����",
						 "��  ��",
						 "����"
						},
						{"����",
						 "��  ��",
						 "����",
						 "    ��",
						 "����"
						}
};

void gotoxy(int x, int y)
{
	COORD CursorPosition = {x, y};
	SetConsoleCursorPosition(GetStdHandle(STD_OUTPUT_HANDLE), CursorPosition);
}

void LinePrint()
{
	printf("��������������������������������������������������������\n");
	printf("��                                                    ��\n");
	printf("��                                                    ��\n");
	printf("��                                                    ��\n");
	printf("��                                                    ��\n");
	printf("��                                                    ��\n");
	printf("��                                                    ��\n");
	printf("��                                                    ��\n");
	printf("��                                                    ��\n");
	printf("��                                                    ��\n");
	printf("��                                                    ��\n");
	printf("��                                                    ��\n");
	printf("��                                                    ��\n");
	printf("��                                                    ��\n");
	printf("��                                                    ��\n");
	printf("��                                                    ��\n");
	printf("��                                                    ��\n");
	printf("��                                                    ��\n");
	printf("��                                                    ��\n");
	printf("��������������������������������������������������������\n");
}

int MainMenu()
{
	int nKey, i, nCount;
	static int nMenu = 0;
	system("cls");

	LinePrint();
	gotoxy(18, 3);
	printf("�� Ʈ���̴� Ÿ�̸� ��");

	nCount = sizeof(g_strMainMenu) / sizeof(char*);

	while(1)
	{
		for(i=0 ; i<nCount ; i++)
		{
			gotoxy(12, i*2+5);
			printf("         %s                  ", g_strMainMenu[i]);
		}

		gotoxy(17, nMenu*2+5);
		printf("��");
		gotoxy(37, nMenu*2+5);
		printf("��");

		nKey = _getch();
		switch(nKey)
		{
		case 72: //���� Ű
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
	printf("��������������������������������������������������������\n");
	printf("��                                                    ��\n");
	printf("��  � �ð� ����(�� ����) :                         ��\n");
	printf("��                                                    ��\n");
	printf("��������������������������������������������������������\n");
	
	gotoxy(30, 2);
	scanf("%d", &g_nTrainingInfo.nTrainingTime);
	fflush(stdin);
}

void SetRestTime()
{
	system("cls");	
	printf("��������������������������������������������������������\n");
	printf("��                                                    ��\n");
	printf("��  �޽� �ð� ����(�� ����) :                         ��\n");
	printf("��                                                    ��\n");
	printf("��  �� 60�� ���� ����                                 ��\n");
	printf("��������������������������������������������������������\n");

	gotoxy(30, 2);
	scanf("%d", &g_nTrainingInfo.nRestTime);
	fflush(stdin);
}

void SetRepeat()
{
	system("cls");
	printf("��������������������������������������������������������\n");
	printf("��                                                    ��\n");
	printf("��  �ݺ� �� ���� :                                    ��\n");
	printf("��                                                    ��\n");
	printf("��������������������������������������������������������\n");
	
	gotoxy(18, 2);
	scanf("%d", &g_nTrainingInfo.nRepeat);
	fflush(stdin);
}

void TrainingInfoPrint()
{
	int nMin, nSec;

	system("cls");
	printf("��������������������������������������������������������\n");
	printf("��                                                    ��\n");
	printf("��                  � ���� ���                    ��\n");
	printf("��                                                    ��\n");
	printf("��  � �ð� :                                       ��\n");
	printf("��                                                    ��\n");
	printf("��  �޽� �ð� :                                       ��\n");
	printf("��                                                    ��\n");
	printf("��  �ݺ� �� :                                         ��\n");
	printf("��                                                    ��\n");
	printf("��  �� � �ð� :                                    ��\n");
	printf("��                                                    ��\n");
	printf("��������������������������������������������������������\n");
	
	gotoxy(19, 4);
	printf("%d ��", g_nTrainingInfo.nTrainingTime);
	gotoxy(19, 6);
	printf("%d ��", g_nTrainingInfo.nRestTime);
	gotoxy(19, 8);
	printf("%d", g_nTrainingInfo.nRepeat);
	
	nMin = (g_nTrainingInfo.nTrainingTime*g_nTrainingInfo.nRepeat) + 
		   (g_nTrainingInfo.nRestTime*(g_nTrainingInfo.nRepeat-1)) / 60;
	nSec = (g_nTrainingInfo.nRestTime*(g_nTrainingInfo.nRepeat-1)) % 60;

	gotoxy(24, 10);
	printf("%d �� %d ��", nMin, nSec);

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
	printf(" �ٽ� ���� �Ϸ��� �ƹ�Ű�� �Է��ϼ���");
	gotoxy(7, 16);
	scanf("%d", &nKey);
	fflush(stdin);
		
	gotoxy(7, 16);
	printf("                                        \n��");
	
	return nKey;
}

void StopWatch()
{
	int min=0, sec=0, milisec=0;
	int nKey = 0;
	
	system("cls");
	LinePrint();
	gotoxy(9, 2);
	printf("�ߡߡߡߡߡ�   ���� ��ġ   �ߡߡߡߡߡ�");

	while(1)
	{
		TimePrint(3, 5, min);

		gotoxy(17, 6);
		printf("��");
		gotoxy(17, 8);
		printf("��");
	
		TimePrint(20, 5, sec);

		gotoxy(34, 6);
		printf("��");
		gotoxy(34, 8);
		printf("��");

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
				printf("�ߡߡߡߡߡ�   � �غ�   �ߡߡߡߡߡ�");
				
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
				printf("�͢� � �ð� �͢�");
				gotoxy(5, 13);
				printf("� ���� �ð� : %d ��  ���� �ݺ� �� : %d", 
						g_nTrainingInfo.nTrainingTime, i+1);

				while(1)
				{
					if(nRunningMin == g_nTrainingInfo.nTrainingTime)
						break;

					TimePrint(13, 5, nRunningMin);

					gotoxy(27, 6);
					printf("��");
					gotoxy(27, 8);
					printf("��");

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
				printf("�ߡ� �޽� �ð� �ߡ�");
				gotoxy(5, 13);
				printf("�޽� ���� �ð� : %d ��", g_nTrainingInfo.nRestTime);
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
