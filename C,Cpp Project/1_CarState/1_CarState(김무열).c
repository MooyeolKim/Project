//14.05.31
#include <stdio.h>
#include <conio.h>
#include <windows.h>
#include "color.h"

int g_nDriver;
int g_nEngine;

typedef struct _OPTION_
{
	int nHeadLight;
	int nCDPlayer;
	int nSpeed;
} OPTION;

void gotoxy(int x, int y)
{
	COORD CursorPosition = {x, y};
	SetConsoleCursorPosition(GetStdHandle(STD_OUTPUT_HANDLE), CursorPosition);
}

//─ │ ┌ ┐ ┘ └ ├ ┬ ┤ ┴ ┼

void CarPrint()
{
	gotoxy(0, 4);
	printf("   ┌◎──◎┐  \n");
	printf(" ■│        │■\n");
	printf(" ■│ ┌─┐ │■\n");
	printf("   │ │  │ │  \n");
	printf(" ■│ └─┘ │■\n");
	printf(" ■│        │■\n");
	printf("   └────┘\n");
}

void DrivePrint()
{
	gotoxy(0, 5);
	printf(" ◆│        │◆\n");
	printf(" ◆│ ┌─┐ │◆\n");
	printf("   │ │○│ │  \n");
	printf(" ◆│ └─┘ │◆\n");
	printf(" ◆│        │◆\n");
	printf("   └────┘\n");
}

void EngineOn()
{
	gotoxy(0, 11);
	printf("    ||      ||\n");
	printf("    ♨      ♨\n");
}

void HeadLightOnState()
{
	gotoxy(5, 4);
	printf("●");
	
	gotoxy(11, 4);
	printf("●");
	
	gotoxy(3, 3);
	printf("↖  ↗↖  ↗");
}
void HeadLightOn()
{
	gotoxy(5, 4);
	printf("●");

	gotoxy(11, 4);
	printf("●");
	
	gotoxy(3, 3);
	printf("  ↑    ↑  ");
	
	Sleep(500);
	
	gotoxy(5, 4);
	printf("●");

	gotoxy(11, 4);
	printf("●");

	gotoxy(3, 3);
	printf("  ↑    ↑  ");

	gotoxy(3, 2);
	printf("↖  ↗↖  ↗");
	
	Sleep(500);

}

void CDPlayerOn()
{
	gotoxy(18, 8);
	printf("♬♪~~~");
}

void DriverRide()
{
	gotoxy(8, 7);
	printf("○");
}

void Drive(int s)
{
	int i = 0;
	while(i < 2)
	{
		DrivePrint();
		Sleep(500 - 5*s);
		gotoxy(0, 5);
		printf(" ■│        │■\n");
		printf(" ■│ ┌─┐ │■\n");
		printf("   │ │○│ │  \n");
		printf(" ■│ └─┘ │■\n");
		printf(" ■│        │■\n");
		printf("   └────┘\n");
		Sleep(500 - 5*s);
		i++;
	}
}

void Break(int nSpeed)
{
	while(nSpeed != 0)
	{
		--nSpeed;
		Sleep(300);
		gotoxy(31, 9);
		printf("속도 : %3d", nSpeed);
	}
}

void Booster(int nHeadLight)
{
	int i = 9;
	gotoxy(0, 3);
	printf("\t\t\n");
	printf("\t\t\n");
	printf("\t\t\n");
	printf("\t\t\n");
	printf("\t\t\n");
	printf("\t\t\n");
	printf("\t\t\n");
	while(i > 0)
	{
		gotoxy(0, i);
		if(nHeadLight == 0)
			printf("   ┌◎──◎┐  \n");
		else
		{
			printf("   ↖  ↗↖  ↗\n");
			printf("   ┌●──●┐  \n");
		}
		printf(" ■│        │■\n");
		printf(" ■│ ┌─┐ │■\n");
		printf("   │ │○│ │  \n");
		printf(" ■│ └─┘ │■\n");
		printf(" ■│        │■\n");
		printf("   └────┘  \n");
		if(i%2 == 0)
		{	
			printf("    ★  ★  ★     \n");
			printf("      ★  ★     \n");
		}
		else
		{
			printf("      ★  ★     \n");
			printf("    ★  ★  ★     \n");
		}
		
		Sleep(300 + 30*i);
		i--;
	}
}

void CarStatusPrint(int nHeadLight, int nCDPlayer, int nSpeed)
{
	gotoxy(31, 4);
	printf("[ 자동차 상태 ]");

	gotoxy(31, 5);
	printf("운전자 탑승 : ");
	if(g_nDriver)
		printf("On");
	else
		printf("Off");

	gotoxy(31, 6);
	printf("엔진 시동 : ");
	if(g_nEngine)
		printf("On");
	else
		printf("Off");

	gotoxy(31, 7);
	printf("헤드 라이트 : ");
	if(nHeadLight)
		printf("On");
	else
		printf("Off");

	gotoxy(31, 8);
	printf("CD 플레이어 : ");
	if(nCDPlayer)
		printf("On");
	else
		printf("Off");

	gotoxy(31, 9);
	printf("속도 : %3d", nSpeed);

	gotoxy(31, 14);
	printf("프로그램 종료는 q키 입력");
}

int main(void)
{
	OPTION sOption;
	int nKey;

	sOption.nHeadLight = 0;
	sOption.nCDPlayer = 0;
	sOption.nSpeed = 0;
	

	CarPrint();
	CarStatusPrint(sOption.nHeadLight, sOption.nCDPlayer, sOption.nSpeed);

	while(1)
	{
		if(_kbhit())
		{
			nKey = _getch();
			if(nKey == 'q')
				break;

			//위-72 아래-80 왼쪽-75 오른쪽-77
			switch(nKey)
			{
			case 75:
				g_nDriver = !g_nDriver;
				if(!g_nDriver)
				{
					g_nEngine = 0;
					sOption.nHeadLight = 0;
					sOption.nCDPlayer = 0;
					sOption.nSpeed = 0;
				}
				break;
			case '1':
				if(g_nDriver != 0)
				{
					g_nEngine = !g_nEngine;
					if(!g_nEngine)
					{
						sOption.nHeadLight = 0;
						sOption.nCDPlayer = 0;
						sOption.nSpeed = 0;
					}
				}
				break;
			case '2':
				if(g_nDriver && g_nEngine)
				{
					sOption.nHeadLight = !sOption.nHeadLight;
					if(sOption.nHeadLight)
						HeadLightOn();
				}
				break;
			case '3':
				if(g_nDriver && g_nEngine)
					sOption.nCDPlayer = !sOption.nCDPlayer;
				break;
			case 72:
				if(g_nDriver && g_nEngine)
				{
					sOption.nSpeed += 5;
					Drive(sOption.nSpeed);
				}
				break;
			case 80:
				if(g_nDriver && g_nEngine)
				{
					if(sOption.nSpeed == 0)
						break;
					else
					{	
						sOption.nSpeed -= 5;
						Drive(sOption.nSpeed);
					}
				}
				break;
			case 77:
				if(sOption.nSpeed != 0)
				{
					Break(sOption.nSpeed);
					sOption.nSpeed = 0;
				}
				break;
			case '4':
				if(g_nDriver && g_nEngine)
					Booster(sOption.nHeadLight);
					break;
			}

			system("cls");

			CarPrint();
			if(g_nDriver)
				DriverRide();
			if(g_nEngine)
				EngineOn();
			if(sOption.nHeadLight)
				HeadLightOnState();
			if(sOption.nCDPlayer)
				CDPlayerOn();
			CarStatusPrint(sOption.nHeadLight, sOption.nCDPlayer, sOption.nSpeed);
		}
	}
	return 0;
}	