//14.06.03
#include <stdio.h>
#include <Windows.h>
#include <conio.h>

typedef struct _CAR_INFO_
{
	int nType;
	int nMonth;
	int nDay;
	int nSaleCount;
} CAR_INFO;

CAR_INFO g_sCar;
char g_strCarName[5][20] = {"에쿠스", "제네시스", "그랜저", "소나타", "아반떼"};
int g_nCarSale[5][12][31];

void gotoxy(int x, int y)
{
	COORD CursorPosition = {x, y};
	SetConsoleCursorPosition(GetStdHandle(STD_OUTPUT_HANDLE), CursorPosition);
}

int MainMenu()
{
	int nKey;

	system("cls");
	printf("┌──────────────────────────┐\n");
	printf("│            자동차 영업점 관리 프로그램             │\n");
	printf("│                                                    │\n");
	printf("│                                                    │\n");
	printf("│                  1. 입력                           │\n");
	printf("│                                                    │\n");
	printf("│                  2. 수정                           │\n");
	printf("│                                                    │\n");
	printf("│                  3. 검색                           │\n");
	printf("│                                                    │\n");
	printf("│                  4. 출력                           │\n");
	printf("│                                                    │\n");
	printf("│                  5. 종료                           │\n");
	printf("│                                                    │\n");
	printf("│            메뉴를 선택하세요 [       ]             │\n");
	printf("└──────────────────────────┘\n");
	gotoxy(36, 14);
	scanf("%d", &nKey);
	fflush(stdin);
	return nKey;
}

void CarDataDisplay()
{
	system("cls");
	printf("┌──────────────────────────┐\n");
	printf("│                                                    │\n");
	printf("│                                                    │\n");
	printf("│  [ 차종 코드 ]                                     │\n");
	printf("│                                                    │\n");
	printf("│  1: 에쿠스 2: 제네시스 3: 그랜저 4: 소나타         │\n");
	printf("│  5: 아반떼                                         │\n");
	printf("│                                                    │\n");
	printf("│  차종 :                                            │\n");
	printf("│                                                    │\n");
	printf("│  월 :      일 :         판매수 :                   │\n");
	printf("│                                                    │\n");
	printf("│                                                    │\n");
	printf("└──────────────────────────┘\n");
}

void Input()
{
	CarDataDisplay();
	gotoxy(15, 1);
	printf("자동차 영업 실적 입력");
	
	gotoxy(11, 8);
	scanf("%d", &g_sCar.nType); fflush(stdin);
	gotoxy(11, 8);
	printf("%s", g_strCarName[g_sCar.nType-1]);
	gotoxy(9, 10);
	scanf("%d", &g_sCar.nMonth); fflush(stdin);
	gotoxy(19, 10);
	scanf("%d", &g_sCar.nDay); fflush(stdin);
	gotoxy(36, 10);
	scanf("%d", &g_sCar.nSaleCount); fflush(stdin);
}

void Search()
{
	CarDataDisplay();
	gotoxy(15, 1);
	printf("자동차 영업 실적 검색");
	
	gotoxy(11, 8);
	scanf("%d", &g_sCar.nType); fflush(stdin);
	gotoxy(11, 8);
	printf("%s", g_strCarName[g_sCar.nType-1]);
	gotoxy(9, 10);
	scanf("%d", &g_sCar.nMonth); fflush(stdin);
	gotoxy(19, 10);
	scanf("%d", &g_sCar.nDay); fflush(stdin);
	gotoxy(36, 10);
	printf("%d", g_nCarSale[g_sCar.nType-1][g_sCar.nMonth-1][g_sCar.nDay-1]);
}

void DrawGraph()
{
	int i, j;

	system("cls");
	gotoxy(28, 0);
	printf("차종 : %s   월 : %d", g_strCarName[g_sCar.nType-1], g_sCar.nMonth);
	gotoxy(0, 2);
	printf("20│\n");
	printf("19│\n");
	printf("18│\n");
	printf("17│\n");
	printf("16│\n");
	printf("15│\n");
	printf("14│\n");
	printf("13│\n");
	printf("12│\n");
	printf("11│\n");
	printf("10│\n");
	printf(" 9│\n");
	printf(" 8│\n");
	printf(" 7│\n");
	printf(" 6│\n");
	printf(" 5│\n");
	printf(" 4│\n");
	printf(" 3│\n");
	printf(" 2│\n");
	printf(" 1│\n");
	printf("  └────────────────────────────────\n");
	printf("    1 2 3 4 5 6 7 8 910 1 2 3 4 5 6 7 8 920 1 2 3 4 5 6 7 8 930 1\n");
	gotoxy(0, 25);
	printf("q키 누르면 나가기");

	for(i=0 ; i<32 ; i++)
	{
		for(j=0 ; j<g_nCarSale[g_sCar.nType-1][g_sCar.nMonth-1][i] ; j++)
		{
			gotoxy(i*2+4, 21-j);
			printf("*");
		}
	}
}

void Print()
{
	int nKey;

	system("cls");
	printf("┌──────────────────────────┐\n");
	printf("│                                                    │\n");
	printf("│  [ 차종 코드 ]                                     │\n");
	printf("│                                                    │\n");
	printf("│  1: 에쿠스 2: 제네시스 3: 그랜저 4: 소나타         │\n");
	printf("│  5: 아반떼                                         │\n");
	printf("│                                                    │\n");
	printf("│  차종 :              월 :                          │\n");
	printf("│                                                    │\n");
	printf("└──────────────────────────┘\n");
	gotoxy(11, 7);
	scanf("%d", &g_sCar.nType); fflush(stdin);
	gotoxy(11, 7);
	printf("%s", g_strCarName[g_sCar.nType-1]);
	gotoxy(29, 7);
	scanf("%d", &g_sCar.nMonth); fflush(stdin);

	DrawGraph();

	while(1)
	{
		nKey = _getch();
		if(nKey == 'q')
			break;

		//위-72 아래-80 왼쪽-75 오른쪽-77
		switch(nKey)
		{
		case 72:
			if(g_sCar.nMonth < 12)
				g_sCar.nMonth++;
			else
				g_sCar.nMonth = 1;
			break;
		case 80:
			if(g_sCar.nMonth > 1)
				g_sCar.nMonth--;
			else
				g_sCar.nMonth = 12;
			break;
		case 77:
			if(g_sCar.nType < 5)
				g_sCar.nType++;
			else
				g_sCar.nType = 1;
			break;
		case 75:
			if(g_sCar.nType > 1)
				g_sCar.nType--;
			else
				g_sCar.nType = 5;
			break;
		}
		DrawGraph();
	}
}




int main(void)
{
	int nKey;
	char chYN;
	char strData[10];

	while(1)
	{
		nKey = MainMenu();
		switch(nKey)
		{
		case 1:
			while(1)
			{
				Input();
				gotoxy(11, 12);
				printf("저장 하시겠습니까? y/n [        ]");
				gotoxy(38, 12);
				scanf("%c", &chYN); fflush(stdin);
				if(chYN == 'n' || chYN == 'N')
					break;
				else
					g_nCarSale[g_sCar.nType-1][g_sCar.nMonth-1][g_sCar.nDay-1] = g_sCar.nSaleCount;

				gotoxy(11, 12);
				printf("계속 하시겠습니까? y/n [        ]");
				gotoxy(38, 12);
				scanf("%c", &chYN); fflush(stdin);
				if(chYN == 'n' || chYN == 'N')
					break;
			}
			break;
		case 2:
			while(1)
			{
				Search();
				gotoxy(36, 10);
				gets(strData);
				if(strlen(strData) > 0)
				{
					g_sCar.nSaleCount = atoi(strData);
				}

				gotoxy(11, 12);
				printf("수정 하시겠습니까? y/n [        ]");
				gotoxy(38, 12);
				scanf("%c", &chYN); fflush(stdin);
				if(chYN == 'n' || chYN == 'N')
					break;
				else
					g_nCarSale[g_sCar.nType-1][g_sCar.nMonth-1][g_sCar.nDay-1] = g_sCar.nSaleCount;

				gotoxy(11, 12);
				printf("계속 하시겠습니까? y/n [        ]");
				gotoxy(38, 12);
				scanf("%c", &chYN); fflush(stdin);
				if(chYN == 'n' || chYN == 'N')
					break;
			}
			break;
		case 3:
			while(1)
			{
				Search();
				gotoxy(11, 12);
				printf("검색을 하시겠습니까? y/n [        ]");
				gotoxy(41, 12);
				scanf("%c", &chYN); fflush(stdin);
				if(chYN == 'n' || chYN == 'N')
					break;
			}
			break;
		case 4:
			Print();
			break;
		case 5:
			system("cls");
			gotoxy(20, 10);
			printf("Good Bye!");
			getch();
			exit(1);
		}
	}
	return 0;
}

