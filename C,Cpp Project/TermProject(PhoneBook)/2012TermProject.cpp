#include <iostream>
#include <iomanip>
#include <fstream>
#include <string>
#include <cstdlib>
#define USER_NUM 3

using namespace std;

string FileName = "PhoneNumber.txt";
string BackUpName = "PhoneNumber_backup.txt";

typedef struct tagUser 
{ 
        char name[11]; 
        char id[9]; 
        char password[9]; 
} User; 

User users[USER_NUM]; 

void Init(User u[]); 

class PhoneBook
{
public:
	int start();
	void AllSearch();
	void NameSearch();
	void NumberSearch();
	void Save();
};
int main()
{
	PhoneBook PB;
	int selection;

	 char user_id[9]; 
        char user_pw[9]; 
        int bContinue = 1; 
        int bFound; 
        int i; 
        // ���������ʱ�ȭ 
        Init(users); 
 
        while ( bContinue ) 
        { 
               // ID���Է¹޴´� 
               printf("���̵� �Է��ϼ���. : "); 
               scanf("%s", &user_id); 

               // ID��ã�´� 
               bFound = 0; 
               for ( i = 0; i < USER_NUM; i++ ) 
               { 
                       // id ���ڿ������ؼ������� 
                       if ( strcmp(users[i].id, user_id) == 0 ) 
                       { 
                              // ã���� 
                              bFound = 1; 							  
                              // for ����Ż�� 
                              break; 
                       } 
               } 
               // ID��ã������ 
               if ( bFound ) 
               { 
                       // �н����带�Է¹޴´� 
                       printf("��й�ȣ�� �Է��ϼ���. : "); 
                       scanf("%s", &user_pw); 

                       // �н�������������� 
                       if ( strcmp(users[i].password, user_pw) == 0 ) 
                       { 
                              while(1)
							  {
								  system("cls");
								  selection = PB.start();
									  if (selection==1)
									  {
										  system("cls");		// screen clear
										  PB.Save();
									  }
									  if (selection==2)
									  {
										  system("cls");
										  PB.AllSearch();
									  }
									  if (selection==3)
									  {
										  system("cls");
										  PB.NumberSearch();
									  }
									  if (selection==4)
									  {
										  system("cls");
										  PB.NameSearch();
									  }
									  if (selection==5)
									  {
										  return 0;
									  }
							  } 
                       } 
                       // �н����尡Ʋ������ 
                       else 
                       { 
                              printf("��й�ȣ�� �߸��Ǿ����ϴ�.\n");
					   } 
                } 
  

               // ID����ã������ 
               else 
               { 
                       printf("���̵� ã�� ���߽��ϴ�.\n"); 
               } 

               printf("\n"); 
        } 
        return 0;



	return 0;
}

void Init(User u[]) 
{ 
        // �ʿ��Ѽ��ڸ�ŭ�ʱ�ȭ 
        strcpy(u[0].name, "�̰��"); 
        strcpy(u[0].id, "lks"); 
        strcpy(u[0].password, "lks123"); 
        strcpy(u[1].name, "���ؿ�"); 
        strcpy(u[1].id, "kjw"); 
        strcpy(u[1].password, "kjw123"); 
        strcpy(u[2].name, "�蹫��"); 
        strcpy(u[2].id, "kmy"); 
        strcpy(u[2].password, "kmy123"); 
}

int PhoneBook::start()		// display �Լ�
{
	int select;

	cout << "�١١١١١١١١١١١١١١١١١١١١١١١١١١١١١١١١١١١١١�" << endl;
	cout << "�١ܡܡܡܡܡ١ܡ١١١١ܡ١١١ܡ١١١١١١١١١١١١١١١١١١١١�" << endl;
	cout << "�١١١ܡ١ܡܡܡ١١ܡܡܡܡܡ١ܡ١١١١١١١١١١١١١١١١١١١١�" << endl;
	cout << "�١١ܡ١ܡ١١ܡ١١١ܡܡܡ١١ܡ١١١١١١١١١١١١١١١١١١١١�" << endl;
	cout << "�١ܡ١١١ܡ١ܡ١١ܡܡܡܡܡ١ܡܡܡ١١١١١١١١١١١١١١١١١١�" << endl;
	cout << "�١١١١ܡ١١١١١١ܡܡܡ١١ܡ١١١١١١١١١١١ܡ١١١١ܡ١ܡ١�" << endl;
	cout << "�١١١١ܡܡܡܡ١١١١ܡ١١١ܡ١١١١١١١١١ܡܡܡܡܡ١١ܡ١ܡ١�" << endl;
	cout << "�١١١١١١١١١١ܡܡܡܡܡ١ܡ١ܡ١ܡ١١ܡ١١١ܡܡܡ١١١ܡܡܡ١�" << endl;
	cout << "�١١١١١١١١١١١١١١١١١١ܡ١ܡܡܡܡ١١ܡܡܡܡܡ١١ܡܡܡ١�" << endl;
	cout << "�١١١١١١١١١١١١١١١١١١ܡܡܡ١١ܡ١١١ܡܡܡ١١١١١١١�" << endl;
	cout << "�١١١١١١١١١١١١١١١١١١ܡܡܡ١١ܡ١١١١ܡ١١١ܡܡܡܡܡ�" << endl;
	cout << "�١١١١١١١١١١١١١١١١١١١١١ܡ١١١١ܡܡܡܡܡ١١١ܡ١١�" << endl;
	cout << "�١١١١١١١١١١١١١١١١١١١١١ܡܡܡ١١١١١١١١١١ܡ١١�" << endl;
	cout << "�١١١١١١١١١١١١١١١١١١١١١١١١١١١١١١١١١١١١١�\n" << endl;
	cout << setw(42) << "�� ��ȭ��ȣ �Է�    " << endl;
	cout << setw(43) << "�� ��� ��ȭ��ȣ ����" << endl;
	cout << setw(42) << "�� ��ȭ��ȣ �˻�    " << endl;
	cout << setw(42) << "�� �̸����� �˻�    " <<  endl;
	cout << setw(41) << "�� ������         \n" << endl;
	cout << setw(60) << "���Ͻô� ����� ��ȣ�� �Է����ּ��� : ";

	while (1)
	{
		cin >> select;
		if (select < 1 || select >5)
		{
			cout << setw(53) << "---------------------------------------\n";
			cout << setw(47) << "�߸��Է��ϼ̽��ϴ�!\n";
			cout << setw(49) <<	"���Ͻô� ����� ��ȣ�� �Է����ּ��� : ";
		}
		else
			break;
	}

	cin.ignore();	// ����Ű�� �ٸ� ���� ���� �ִ� �� ����

	return select;
}
// ����� ��ȣ ��ü ����
void PhoneBook::AllSearch()
{
	string line;
	ifstream Filein;

	Filein.open(FileName.c_str());	//file open

	if (Filein.fail())
	{
		cout << "������ ���������� ������ �ʾҽ��ϴ�.." << endl;
		exit(1);
	}

	Filein.seekg(0L,ios::beg);	//���Ͽ��� Ŀ�� ��ġ ���� ������
	
	// save file display
	while (Filein.good())
	{
		getline(Filein,line);
		cout << line << endl;
	}

	Filein.close();

	cout << "'����'Ű�� �����ּ���!";
	getchar();

	return;
}
//�̸����� ã��
void PhoneBook::NameSearch()
{
	string name, line;
	int i,count=0;
	bool nosearch=true;
	ifstream search_istr;

	cout << "ã���� �ϴ� �̸��� �Է��ϼ��� : ";
	getline(cin, name);

	search_istr.open(FileName.c_str());

	if (search_istr.fail())
	{
		cout << "������ ���������� ������ �ʾҽ��ϴ�." << endl;
		exit(1);
	}

	search_istr.seekg(0L,ios::beg);	//���Ͽ��� Ŀ�� ��ġ ���� ������

	while(search_istr.good())
	{
		getline(search_istr, line);

		if (line.length()!=0)
		{
			for (i=0;i<name.length();i++)
			{
				if (line.at(i)==name.at(i))
				{
					count++;
				}
				else
				{
					count=0;
					break;
				}
			}
			if (count != 0)
			{
				count = 0;
				nosearch = false;
				break;
			}
		}
	}
	search_istr.close();

	if (nosearch)
	{
		cout << "\n������� �������� �ʽ��ϴ�.\n" << endl;
	}
	else
	{
		cout << "\n" << line << "\n" << endl;
	}

	cout << "'����'Ű�� �����ּ���!";
	getchar();

	return;
}
//��ȭ��ȣ�� ã��
void PhoneBook::NumberSearch()
{
	const int MAX = 200;
	string num, display[MAX], line;
	ifstream num_search_istr;
	int leng, i, j;
	bool nosearch=true;

	cout << "ã���� �ϴ� ��ȣ�� �� ���ڸ��� �Է��ϼ��� : ";
	getline(cin, num);

	num_search_istr.open(FileName.c_str());

	if (num_search_istr.fail())
	{
		cout << "������ ���������� ������ �ʾҽ��ϴ�." << endl;
		exit(1);
	}

	i = 0;

	while(num_search_istr.good())
	{
		getline(num_search_istr,line);

		leng = line.length();

		if (leng != 0 && leng >= 4)
		{
			if (line.at(leng-4)==num.at(0) && line.at(leng-3)==num.at(1) &&
				line.at(leng-2)==num.at(2) && line.at(leng-1)==num.at(3))
			{
				display[i] = line;
				i++;
				nosearch = false;
			}
		}
	}

	num_search_istr.close();

	if (nosearch)
	{
		cout << "\n������� �������� �ʽ��ϴ�.\n" << endl;
	}
	else
	{
		for (j=0;j<i;j++)
		{
			cout << "\n" << display[j] << endl;
		}
	}

	cout << "'����'Ű�� �����ּ���!";
	getchar();

	return;
}
//��ȭ��ȣ �����ϱ�
void PhoneBook::Save()
{
	string line,entered;
	void backup();
	ifstream istr;
	ofstream ostr;

	istr.open(BackUpName.c_str());
	ostr.open(FileName.c_str());

	if (istr.fail())
	{
		cout << "�Է� ������ ���������� ������ �ʾҽ��ϴ�." << endl;
		exit(1);
	}

	if (ostr.fail())
	{
		cout << "��� ������ ���������� ������ �ʾҽ��ϴ�." << endl;
		exit(1);
	}

	cout << "�̸��� ��ȣ�� �Է��ϼ��� :\n"
		<< "(ex. ȫ�浿 010-1234-4567)\n";
	getline(cin,entered);

	istr.seekg(0L,ios::beg);

	// save
	while(istr.good())
	{
		getline(istr,line);
		ostr << line << endl;
	}

	ostr << entered;

	istr.close();
	ostr.close();

	backup();

	cout << "��ȣ�� ���������� ����Ǿ����ϴ�." << endl;
	cout << "'����'Ű�� �����ּ���!";

	getchar();

	return;
}
// text backup
void backup()
{
	ifstream ifile;
	ofstream ofile;
	string line;

	ifile.open(FileName.c_str());
	ofile.open(BackUpName.c_str());

	if (ifile.fail())
	{
		cout << "�Է� ������ ���������� ������ �ʾҽ��ϴ�." << endl;
		exit(1);
	}

	if (ofile.fail())
	{
		cout << "��� ������ ���������� ������ �ʾҽ��ϴ�." << endl;
		exit(1);
	}

	ifile.seekg(0L,ios::beg);
	
	// backup
	while(ifile.good())
	{
		getline(ifile,line);
		ofile << line << endl;
	}

	ifile.close();
	ofile.close();

	return;
}