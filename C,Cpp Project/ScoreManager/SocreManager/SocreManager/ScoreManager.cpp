#include "ScoreManager.h"

ScoreManager::ScoreManager(void)
{
	std_index = sbj_index = 0;
}

void ScoreManager::gotoxy(int x, int y)
{
	COORD CursorPosition = {x, y};
	SetConsoleCursorPosition(GetStdHandle(STD_OUTPUT_HANDLE), CursorPosition);
}

void ScoreManager::LinePrint(void)
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

int ScoreManager::ShowMenu(void)
{
	int nKey, i, nCount;
	static int nMenu = 0;
	char *g_strMainMenu[6] = {"학생 등록", "교과목 등록", "성적 입력", "성적 조회", "성적 파일 출력", "종료"};
	system("cls");

	LinePrint();
	gotoxy(18, 3);
	printf("★ 성적 관리 프로그램 ★");

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

void ScoreManager::InputStudent(void)
{
	system("cls");
	int n;
	char s[20];

	cout << "\n--- 학생 등록 ---" << endl;
	cout << "학번 : ";
	cin >> n;
	cout << "이름 : ";
	cin >> s;

	pSc[std_index++] = new Score(n, s);
	cout << s << " 학생이 등록되었습니다." << endl;
	Sleep(600);
}

void ScoreManager::InputSubject(void)
{
	system("cls");
	int n;
	char s[20];

	cout << "\n--- 교과목 입력 ---" << endl;
	cout << "과목 수 : ";
	cin >> n;

	for(int i=0 ; i<n ; i++)
	{
		cout << "교과목 명 " << i+1 << " : ";
		cin >> s;
		subject[i] = new char[strlen(s)+1];
		strcpy(subject[i], s);
	}
	sbj_index = n;
	cout << n << "개의 교과목이 입력되었습니다." << endl;
	Sleep(600);
}

void ScoreManager::InputScore(void)
{
	system("cls");

	int n, sc, total = 0;
	
	cout << "\n--- 성적 입력 ---" << endl;
	cout << "학번 : ";
	cin >> n;

	for(int i=0 ; i<std_index ; i++)
	{
		if(pSc[i]->GetNum() == n)
		{
			if(pSc[i]->GetIsScore() == true)
			{
				cout << "!!! 이미 성적이 입력 되었습니다." << endl;
				Sleep(600);
				return;
			}
			cout << "> 이름 : " << pSc[i]->GetName() << endl;

			for(int j=0 ; j<sbj_index ; j++)
			{
				cout << "> " << subject[j] << " : ";
				cin >> sc;
				pSc[i]->SetScore(j, sc);
				total += sc;
			}

			pSc[i]->SetAverage((double)total / sbj_index);
			pSc[i]->SetIsScore(true);
			cout << pSc[i]->GetName() << " 학생의 성적이 입력되었습니다." << endl;
			Sleep(600);
			return;
		}
	}
	cout << "!!! 학번을 잘못 입력하셨습니다." << endl;
	Sleep(600);
}

void ScoreManager::OutputScore(void)
{
	system("cls");
	int i, j;
	int nKey = 0;

	cout.setf(ios::left | ios::fixed);

	cout << "\n----- 성적 조회 -----" << endl;
	cout << setw(8) << "학번";
	cout << setw(8) << "이름";

	for(j=0 ; j<sbj_index ; j++)
	{
		cout << setw(8) << subject[j];
	}
	cout << setw(8) << "평균" << endl;

	for(i=0 ; i<sbj_index+3 ; i++)
	{
		cout << "========";
	}
	cout << endl;

	for(i=0 ; i<std_index ; i++)
	{
		if(pSc[i]->GetIsScore() == true)
		{
			cout << setw(8) << pSc[i]->GetNum();
			cout << setw(8) << pSc[i]->GetName();

			for(j=0 ; j<sbj_index ; j++)
			{
				cout << setw(8) << pSc[i]->GetScore(j);
			}

			cout.precision(2);
			cout << setw(8) << pSc[i]->GetAverage() << endl;
		}
	}

	while(nKey != 13)
	{
		cout << "엔터를 누르면 메인 메뉴로 돌아갑니다.";
		nKey = _getch();
	}
}

void ScoreManager::FileScore()
{
	system("cls");
	int i, j;
	char filename[20];

	cout << "\n출력 파일 이름 : ";
	cin >> filename;

	ofstream fout(filename);
	fout.setf(ios::left | ios::fixed);
	
	fout << "----- 성적 조회 -----\n";
	fout << setw(8) << "학번";
	fout << setw(8) << "이름";

	for(j=0 ; j<sbj_index ; j++)
	{
		fout << setw(8) << subject[j];
	}

	fout << setw(8) << "평균" << endl;

	for(i=0 ; i<std_index ; i++)
	{
		if(pSc[i]->GetIsScore() == true)
		{
			fout << setw(8) << pSc[i]->GetNum();
			fout << setw(8) << pSc[i]->GetName();

			for(j=0 ; j<sbj_index ; j++)
			{
				fout << setw(8) << pSc[i]->GetScore(j);
			}

			fout.precision(2);
			fout << setw(8) << pSc[i]->GetAverage() << endl;
		}
	}
	fout.close();
	cout << filename << " 파일이 저장되었습니다." << endl;
	Sleep(600);
}