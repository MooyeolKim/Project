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
        // 유저정보초기화 
        Init(users); 
 
        while ( bContinue ) 
        { 
               // ID를입력받는다 
               printf("아이디를 입력하세요. : "); 
               scanf("%s", &user_id); 

               // ID를찾는다 
               bFound = 0; 
               for ( i = 0; i < USER_NUM; i++ ) 
               { 
                       // id 문자열을비교해서같으면 
                       if ( strcmp(users[i].id, user_id) == 0 ) 
                       { 
                              // 찾았음 
                              bFound = 1; 							  
                              // for 루프탈출 
                              break; 
                       } 
               } 
               // ID를찾았으면 
               if ( bFound ) 
               { 
                       // 패스워드를입력받는다 
                       printf("비밀번호를 입력하세요. : "); 
                       scanf("%s", &user_pw); 

                       // 패스워드까지맞으면 
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
                       // 패스워드가틀렸으면 
                       else 
                       { 
                              printf("비밀번호가 잘못되었습니다.\n");
					   } 
                } 
  

               // ID를못찾았으면 
               else 
               { 
                       printf("아이디를 찾지 못했습니다.\n"); 
               } 

               printf("\n"); 
        } 
        return 0;



	return 0;
}

void Init(User u[]) 
{ 
        // 필요한숫자만큼초기화 
        strcpy(u[0].name, "이경숙"); 
        strcpy(u[0].id, "lks"); 
        strcpy(u[0].password, "lks123"); 
        strcpy(u[1].name, "김준완"); 
        strcpy(u[1].id, "kjw"); 
        strcpy(u[1].password, "kjw123"); 
        strcpy(u[2].name, "김무열"); 
        strcpy(u[2].id, "kmy"); 
        strcpy(u[2].password, "kmy123"); 
}

int PhoneBook::start()		// display 함수
{
	int select;

	cout << "☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆" << endl;
	cout << "☆●●●●●☆●☆☆☆☆●☆☆☆●☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆" << endl;
	cout << "☆☆☆●☆●●●☆☆●●●●●☆●☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆" << endl;
	cout << "☆☆●☆●☆☆●☆☆☆●●●☆☆●☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆" << endl;
	cout << "☆●☆☆☆●☆●☆☆●●●●●☆●●●☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆" << endl;
	cout << "☆☆☆☆●☆☆☆☆☆☆●●●☆☆●☆☆☆☆☆☆☆☆☆☆☆●☆☆☆☆●☆●☆☆" << endl;
	cout << "☆☆☆☆●●●●☆☆☆☆●☆☆☆●☆☆☆☆☆☆☆☆☆●●●●●☆☆●☆●☆☆" << endl;
	cout << "☆☆☆☆☆☆☆☆☆☆●●●●●☆●☆●☆●☆☆●☆☆☆●●●☆☆☆●●●☆☆" << endl;
	cout << "☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆●☆●●●●☆☆●●●●●☆☆●●●☆☆" << endl;
	cout << "☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆●●●☆☆●☆☆☆●●●☆☆☆☆☆☆☆☆" << endl;
	cout << "☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆●●●☆☆●☆☆☆☆●☆☆☆●●●●●☆" << endl;
	cout << "☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆●☆☆☆☆●●●●●☆☆☆●☆☆☆" << endl;
	cout << "☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆●●●☆☆☆☆☆☆☆☆☆☆●☆☆☆" << endl;
	cout << "☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆\n" << endl;
	cout << setw(42) << "① 전화번호 입력    " << endl;
	cout << setw(43) << "② 모든 전화번호 보기" << endl;
	cout << setw(42) << "③ 전화번호 검색    " << endl;
	cout << setw(42) << "④ 이름으로 검색    " <<  endl;
	cout << setw(41) << "⑤ 나가기         \n" << endl;
	cout << setw(60) << "원하시는 기능의 번호를 입력해주세요 : ";

	while (1)
	{
		cin >> select;
		if (select < 1 || select >5)
		{
			cout << setw(53) << "---------------------------------------\n";
			cout << setw(47) << "잘못입력하셨습니다!\n";
			cout << setw(49) <<	"원하시는 기능의 번호를 입력해주세요 : ";
		}
		else
			break;
	}

	cin.ignore();	// 엔터키가 다른 곳에 영향 주는 것 방지

	return select;
}
// 저장된 번호 전체 보기
void PhoneBook::AllSearch()
{
	string line;
	ifstream Filein;

	Filein.open(FileName.c_str());	//file open

	if (Filein.fail())
	{
		cout << "파일이 성공적으로 열리지 않았습니다.." << endl;
		exit(1);
	}

	Filein.seekg(0L,ios::beg);	//파일에서 커서 위치 가장 앞으로
	
	// save file display
	while (Filein.good())
	{
		getline(Filein,line);
		cout << line << endl;
	}

	Filein.close();

	cout << "'엔터'키를 눌러주세요!";
	getchar();

	return;
}
//이름으로 찾기
void PhoneBook::NameSearch()
{
	string name, line;
	int i,count=0;
	bool nosearch=true;
	ifstream search_istr;

	cout << "찾고자 하는 이름을 입력하세요 : ";
	getline(cin, name);

	search_istr.open(FileName.c_str());

	if (search_istr.fail())
	{
		cout << "파일이 성공적으로 열리지 않았습니다." << endl;
		exit(1);
	}

	search_istr.seekg(0L,ios::beg);	//파일에서 커서 위치 가장 앞으로

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
		cout << "\n결과값이 존재하지 않습니다.\n" << endl;
	}
	else
	{
		cout << "\n" << line << "\n" << endl;
	}

	cout << "'엔터'키를 눌러주세요!";
	getchar();

	return;
}
//전화번호로 찾기
void PhoneBook::NumberSearch()
{
	const int MAX = 200;
	string num, display[MAX], line;
	ifstream num_search_istr;
	int leng, i, j;
	bool nosearch=true;

	cout << "찾고자 하는 번호의 뒷 네자리를 입력하세요 : ";
	getline(cin, num);

	num_search_istr.open(FileName.c_str());

	if (num_search_istr.fail())
	{
		cout << "파일이 성공적으로 열리지 않았습니다." << endl;
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
		cout << "\n결과값이 존재하지 않습니다.\n" << endl;
	}
	else
	{
		for (j=0;j<i;j++)
		{
			cout << "\n" << display[j] << endl;
		}
	}

	cout << "'엔터'키를 눌러주세요!";
	getchar();

	return;
}
//전화번호 저장하기
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
		cout << "입력 파일이 성공적으로 열리지 않았습니다." << endl;
		exit(1);
	}

	if (ostr.fail())
	{
		cout << "출력 파일이 성공적으로 열리지 않았습니다." << endl;
		exit(1);
	}

	cout << "이름과 번호를 입력하세요 :\n"
		<< "(ex. 홍길동 010-1234-4567)\n";
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

	cout << "번호가 성공적으로 저장되었습니다." << endl;
	cout << "'엔터'키를 눌러주세요!";

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
		cout << "입력 파일이 성공적으로 열리지 않았습니다." << endl;
		exit(1);
	}

	if (ofile.fail())
	{
		cout << "출력 파일이 성공적으로 열리지 않았습니다." << endl;
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