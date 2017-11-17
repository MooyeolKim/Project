//14.06.01
#include <stdio.h>
#include <conio.h>
#include <Windows.h>

typedef struct _CAN_
{
	int nType;
	int nAccount;
	int nOpen;
}CAN;

CAN g_sCan[9];
char *g_CanShape[9] = {"¢Ã", "¢Ç", "¢Ë", "¢Ì", "¢Í", "¢Æ", "¢Â", "¢È", "¢Ê"};
char *g_CanName[9] = {"»çÀÌ´Ù", "ÄÝ¶ó", "È¯Å¸", "ºÀºÀ", "½ÄÇý", "¸¶¿îÆ¾", "°ÔÅä·¹ÀÌ", "·¹½ººñ", "¿À·»ÁöÁÖ½º"};
int g_nCanAccount[] = {500, 500, 500, 700, 700, 600, 600, 500, 800};
int g_nAccountUnit[] = {1000, 500, 100, 50, 10};
int g_nCanOutIndex[6] = {0};

int g_nAccount; //ÇöÀç ±Ý¾×
int g_nRemainAccount; //ÀÜ¾×
int g_nRemainAccountOut; //ÀÜµ· ³ª¿À´Â ¾×¼ö
int g_nExtractCanIndex; // »Ì´Â ÄµÀÇ ÀÎµ¦½º
int g_nExtractCount; // »ÌÀ»¼ö ÀÖ´Â ÄµÀÇ °³¼ö
int g_nCanOutCnt = 0;

void gotoxy(int x, int y)
{
	COORD CursorPosition = {x, y};
	SetConsoleCursorPosition(GetStdHandle(STD_OUTPUT_HANDLE), CursorPosition);
}

void CanMachine()
{
	printf("¦£¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¨¦¡¦¡¦¡¦¡¦¡¦¡¦¤\n");
	printf("¦¢                                        ¦¢ Äµ ÀÚÆÇ±â  ¦¢\n");
	printf("¦¢                                        ¦¢            ¦¢\n");
	printf("¦¢                                        ¦¢ ÇöÀç ±Ý¾×  ¦¢\n");
	printf("¦¢     [1]          [2]           [3]     ¦¢            ¦¢\n");
	printf("¦¢                                        ¦¢            ¦¢\n");
	printf("¦¢                                        ¦¢            ¦¢\n");
	printf("¦¢                                        ¦¢            ¦¢\n");
	printf("¦¢                                        ¦¢    ÀÜ¾×    ¦¢\n");
	printf("¦¢     [4]          [5]           [6]     ¦¢            ¦¢\n");
	printf("¦¢                                        ¦¢            ¦¢\n");
	printf("¦¢                                        ¦¢            ¦¢\n");
	printf("¦¢                                        ¦¢            ¦¢\n");
	printf("¦¢                                        ¦¢            ¦¢\n");
	printf("¦¢     [7]          [8]           [9]     ¦¢            ¦¢\n");
	printf("¦§¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦ª¦¡¦¡¦¡¦¡¦¡¦¡¦©\n");
	printf("¦¢                   ¦£¦¡¦¡¦¡¦¡¦¡¦¡¦¤   ¦£¦¡¦¡¦¡¦¡¦¡¦¡¦¤¦¢\n");
	printf("¦¢                   ¦¢Äµ ³ª¿À´Â °÷¦¢   ¦¢ÀÜµ·³ª¿À´Â°÷¦¢¦¢\n");
	printf("¦¢                   ¦¢            ¦¢   ¦¢            ¦¢¦¢\n");
	printf("¦¢                   ¦¢            ¦¢   ¦¢            ¦¢¦¢\n");
	printf("¦¢                   ¦¦¦¡¦¡¦¡¦¡¦¡¦¡¦¥   ¦¦¦¡¦¡¦¡¦¡¦¡¦¡¦¥¦¢\n");
	printf("¦¦¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¥\n");
}

void CanOutput(int nIndex)
{
	int i;
	int x = 23;
	
	g_nCanOutIndex[g_nCanOutCnt] = nIndex; 
		
	for(i=0 ; i<=g_nCanOutCnt ; i++)
	{
		gotoxy(x, 19);
		printf("%s", g_CanShape[g_nCanOutIndex[i]]);
		x+=2;
	}

	g_nCanOutCnt++;

	if(g_nCanOutCnt == 6)
		g_nCanOutCnt = 0;
}

void CanRemove()
{
	int i, j=10;
	for(i=g_nCanOutCnt ; i>=0 ; i--)
	{
		gotoxy(23+j, 19);
		printf("  ");
		j-=2;
		Sleep(500);
	}
	g_nCanOutCnt = 0;
}

int MainMenu()
{
	int nKey;
	gotoxy(0, 22);
	printf("1. µ·³Ö±â 2. Äµ ¼±ÅÃ 3. ÀÜµ· ¹Þ±â 4. Äµ ¹Þ±â 5. Á¾·á  [          ]");
	gotoxy(60, 22);
	scanf("%d", &nKey);
	return nKey;
}

void SubMenu1()
{
	int num = 0, i, j;
	while(num != 6)
	{
		gotoxy(0, 22);
		printf("±Ý¾×À» ÅõÀÔÇÏ¼¼¿ä![   ]                                                 \n");
		printf("(1.1000¿ø 2.500¿ø 3.100¿ø 4. 50¿ø 5.10¿ø 6.ÅõÀÔ¿Ï·á)");
		gotoxy(20, 22); 
		scanf("%d", &num);
		switch(num)
		{
		case 1:
			g_nAccount += g_nAccountUnit[0];
			break;
		case 2:
			g_nAccount += g_nAccountUnit[1];
			break;
		case 3:
			g_nAccount += g_nAccountUnit[2];
			break;
		case 4:
			g_nAccount += g_nAccountUnit[3];
			break;
		case 5:
			g_nAccount += g_nAccountUnit[4];
			break;
		default:
			break;
		}
		for(i=0 ; i<3 ; i++)
		{
			for(j=0 ; j<3 ; j++)
			{
				if(g_sCan[i*3 + j].nAccount <= (g_nRemainAccount + g_nAccount))
				{
					g_sCan[i*3 + j].nOpen = 1;
					g_nExtractCount++;
				}
				else
					g_sCan[i*3 + j].nOpen = 0;

				if(g_sCan[i*3 + j].nOpen)
				{
					gotoxy(j*13 + 6 - strlen(g_CanName[g_sCan[i*3 + j].nType])/2, 
							i*5 + 2);
					printf("%s", "¡Ú");
					gotoxy(j*13 + 8 + strlen(g_CanName[g_sCan[i*3 + j].nType])/2,
							i*5 + 2);
					printf("%s", "¡Ú");
				}
			}
		}
		
		gotoxy(47, 4);
		printf("%-5d", g_nAccount);
	}
}

int SubMenu2()
{
	int nKey;
	gotoxy(0, 22);
	printf("ÄµÀ» ¼±ÅÃÇØ ÁÖ¼¼¿ä! [             ]                                ");
	gotoxy(23, 22);
	scanf("%d", &nKey);
	return nKey;
}

void SubMenu3()
{
	int i;
	g_nRemainAccountOut = 0;

	for(i=0 ; i<5 ; i++)
	{
		while(g_nRemainAccount >= g_nAccountUnit[i])
		{
			g_nRemainAccount = g_nRemainAccount - g_nAccountUnit[i];
			g_nRemainAccountOut = g_nRemainAccountOut + g_nAccountUnit[i];  
		
			gotoxy(47, 9);
			printf("%-5d", g_nRemainAccount);

			Sleep(600);

			gotoxy(43, 19);
			printf("%-5d", g_nRemainAccountOut);
		}
	}

	for(i=0 ; i<5 ; i++)
	{
		while(g_nAccount >= g_nAccountUnit[i])
		{
			g_nAccount = g_nAccount - g_nAccountUnit[i];
			g_nRemainAccountOut =  g_nRemainAccountOut + g_nAccountUnit[i];  
			
			gotoxy(47, 4);
			printf("%-5d", g_nAccount);

			Sleep(600);

			gotoxy(43, 19);
			printf("%-5d", g_nRemainAccountOut);
		}
	}
}


void Init()
{
	int i;
	for(i=0 ; i<9 ; i++)
	{
		g_sCan[i].nType = i;
		g_sCan[i].nAccount = g_nCanAccount[i];
		g_sCan[i].nOpen = 0;
	}
	
	g_nExtractCanIndex = -1;
	g_nExtractCount = 0;
	g_nAccount = 0;
	g_nRemainAccount = 0;
}

void CanContent()
{
	int i, j;
	for(i=0 ; i<3 ; i++)
	{
		for(j=0 ; j<3 ; j++)
		{
			gotoxy(j*13 + 8, i*5 +1);
			printf("%s", g_CanShape[g_sCan[i*3 + j].nType]);
			gotoxy(j*13+8 - strlen(g_CanName[g_sCan[i*3 + j].nType])/2,
					i*5 +2);
			printf("%s", g_CanName[g_sCan[i*3 + j].nType]);

			if(g_sCan[i*3 + j].nOpen)
			{
				gotoxy(j*13 + 6 - strlen(g_CanName[g_sCan[i*3 + j].nType])/2,
						i*5 + 2);
				printf("%s", "¡Ú");
				gotoxy(j*13 + 8 + strlen(g_CanName[g_sCan[i*3 + j].nType])/2,
						i*5 + 2);
				printf("%s", "¡Ú");
			}
			
			gotoxy(j*13 + 5, i*5 + 3);
			printf("(%d¿ø)", g_sCan[i*3 + j].nAccount);
		}
	}

	gotoxy(47, 4);
	printf("%-5d", g_nAccount);
	gotoxy(47, 9);
	printf("%-5d", g_nRemainAccount);
}
			
int main(void)
{
	int i, nKey;

	Init();
	
	while(1)
	{
		system("cls");
		CanMachine();
		CanContent();

		if(g_nExtractCanIndex > -1)
			CanOutput(g_nExtractCanIndex);

		nKey = MainMenu();

		if(nKey == 5)
			break;

		switch(nKey)
		{
		case 1:
			SubMenu1();
			g_nExtractCount = 0;
			for(i=0 ; i<9 ; i++)
			{
				if(g_sCan[i].nAccount <= g_nAccount + g_nRemainAccount)
				{
					g_sCan[i].nOpen = 1;
					g_nExtractCount++;
				}
				else
					g_sCan[i].nOpen = 0;
			}

			if(g_nCanOutCnt != 0)
				g_nCanOutCnt--;

			break;
		case 2:
			if(g_nAccount > 0)
			{
				g_nRemainAccount += g_nAccount;
				g_nAccount = 0;
			}

			if(g_nExtractCount < 1)
			{
				gotoxy(0, 22);
				printf("µ·À» ÅõÀÔÇØÁÖ¼¼¿ä.                                                                ");
				Sleep(1300);
				break;
			}

			while(1)
			{
				nKey = SubMenu2();
				if(nKey >= 1 && nKey <= 9)
				{
					g_nExtractCanIndex = nKey - 1;
					if(!g_sCan[g_nExtractCanIndex].nOpen)
						continue;
					g_nRemainAccount -= g_sCan[g_nExtractCanIndex].nAccount;
					g_nExtractCount = 0;

					for(i=0 ; i<9 ; i++)
					{
						if(g_sCan[i].nAccount <= g_nRemainAccount)
						{
							g_sCan[i].nOpen = 1;
							g_nExtractCount++;
						}
						else
							g_sCan[i].nOpen = 0;
					}
					break;
				}
			}
			break;
		case 3:
			if(g_nCanOutCnt != 0)
				g_nCanOutCnt--;
			for(i=0 ; i<9 ; i++)
			{
				g_sCan[i].nOpen = 0;
			}
			SubMenu3();
			break;
		case 4:
			g_nExtractCanIndex = -1;
			CanRemove();
			break;
		}
	}

	return 0;
}

