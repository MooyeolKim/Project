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

int ScoreManager::ShowMenu(void)
{
	int nKey, i, nCount;
	static int nMenu = 0;
	char *g_strMainMenu[6] = {"�л� ���", "������ ���", "���� �Է�", "���� ��ȸ", "���� ���� ���", "����"};
	system("cls");

	LinePrint();
	gotoxy(18, 3);
	printf("�� ���� ���� ���α׷� ��");

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

void ScoreManager::InputStudent(void)
{
	system("cls");
	int n;
	char s[20];

	cout << "\n--- �л� ��� ---" << endl;
	cout << "�й� : ";
	cin >> n;
	cout << "�̸� : ";
	cin >> s;

	pSc[std_index++] = new Score(n, s);
	cout << s << " �л��� ��ϵǾ����ϴ�." << endl;
	Sleep(600);
}

void ScoreManager::InputSubject(void)
{
	system("cls");
	int n;
	char s[20];

	cout << "\n--- ������ �Է� ---" << endl;
	cout << "���� �� : ";
	cin >> n;

	for(int i=0 ; i<n ; i++)
	{
		cout << "������ �� " << i+1 << " : ";
		cin >> s;
		subject[i] = new char[strlen(s)+1];
		strcpy(subject[i], s);
	}
	sbj_index = n;
	cout << n << "���� �������� �ԷµǾ����ϴ�." << endl;
	Sleep(600);
}

void ScoreManager::InputScore(void)
{
	system("cls");

	int n, sc, total = 0;
	
	cout << "\n--- ���� �Է� ---" << endl;
	cout << "�й� : ";
	cin >> n;

	for(int i=0 ; i<std_index ; i++)
	{
		if(pSc[i]->GetNum() == n)
		{
			if(pSc[i]->GetIsScore() == true)
			{
				cout << "!!! �̹� ������ �Է� �Ǿ����ϴ�." << endl;
				Sleep(600);
				return;
			}
			cout << "> �̸� : " << pSc[i]->GetName() << endl;

			for(int j=0 ; j<sbj_index ; j++)
			{
				cout << "> " << subject[j] << " : ";
				cin >> sc;
				pSc[i]->SetScore(j, sc);
				total += sc;
			}

			pSc[i]->SetAverage((double)total / sbj_index);
			pSc[i]->SetIsScore(true);
			cout << pSc[i]->GetName() << " �л��� ������ �ԷµǾ����ϴ�." << endl;
			Sleep(600);
			return;
		}
	}
	cout << "!!! �й��� �߸� �Է��ϼ̽��ϴ�." << endl;
	Sleep(600);
}

void ScoreManager::OutputScore(void)
{
	system("cls");
	int i, j;
	int nKey = 0;

	cout.setf(ios::left | ios::fixed);

	cout << "\n----- ���� ��ȸ -----" << endl;
	cout << setw(8) << "�й�";
	cout << setw(8) << "�̸�";

	for(j=0 ; j<sbj_index ; j++)
	{
		cout << setw(8) << subject[j];
	}
	cout << setw(8) << "���" << endl;

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
		cout << "���͸� ������ ���� �޴��� ���ư��ϴ�.";
		nKey = _getch();
	}
}

void ScoreManager::FileScore()
{
	system("cls");
	int i, j;
	char filename[20];

	cout << "\n��� ���� �̸� : ";
	cin >> filename;

	ofstream fout(filename);
	fout.setf(ios::left | ios::fixed);
	
	fout << "----- ���� ��ȸ -----\n";
	fout << setw(8) << "�й�";
	fout << setw(8) << "�̸�";

	for(j=0 ; j<sbj_index ; j++)
	{
		fout << setw(8) << subject[j];
	}

	fout << setw(8) << "���" << endl;

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
	cout << filename << " ������ ����Ǿ����ϴ�." << endl;
	Sleep(600);
}