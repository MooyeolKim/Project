import java.util.Scanner;

public class EmployeeSystem {
	Scanner sc = new Scanner(System.in);
	ManagerEmployee me;
	
	public EmployeeSystem(){
		me = new ManagerEmployee(100);
	}
	
	public static void main(String[] args){
		EmployeeSystem ms = new EmployeeSystem();
		ms.startMenu();
	}
	
	public void startMenu(){
		while(true){
			try{
				System.out.println("\n----- 사원관리프로그램 -----");
				System.out.println("1. 신규등록");
				System.out.println("2. 검    색");
				System.out.println("3. 삭    제");
				System.out.println("4. 전체보기");
				System.out.println("5. 종    료");
				System.out.println("----------------------------");
				System.out.print("번호 선택 : ");
				
				int no = sc.nextInt();
				
				switch(no){
				case 1: registMenu(); break;
				case 2: searchMenu(); break;
				case 3: deleteMenu(); break;
				case 4: me.displayEmployee(); break;
				case 5: System.out.println("\n프로그램 종료"); System.exit(0);
				default : return;
				}
			}
			catch(Exception e){
				System.out.println("Message : " + e.getMessage());
				e.getStackTrace();
			}
		}
	}
	
	public void registMenu(){
		System.out.println();
		System.out.println("----- 신규등록 메뉴 -----");
		System.out.println("1. 관리직");
		System.out.println("2. 생산직");
		System.out.println("3. 임시직");
		System.out.println("4. 아르바이트");
		System.out.println("5. 상위메뉴");
		System.out.println("-------------------------");
		System.out.print("번호 선택 : ");
		
		int no = sc.nextInt();
		
		switch(no){
		case 1: makeManager(); break;
		case 2: makeProduct(); break;
		case 3: makeTempJob(); break;
		case 4: makePartJob(); break;
		case 5:
		default : return;
		}
	}
	
	public void makeManager(){
		String name, id, address, tel, dept, position;
		
		while(true){
			System.out.println();
			System.out.print("이    름 : "); name = sc.next();
			System.out.print("주민번호 : "); id = sc.next();
			System.out.print("주    소 : "); address = sc.next();
			System.out.print("전화번호 : "); tel = sc.next();
			System.out.print("근무부서 : "); dept = sc.next();
			System.out.print("직    급 : "); position = sc.next();
			break;
			
		}
		
		MngEmployee mng = new MngEmployee(name, id, address, tel, dept, position);
		me.addEmployee(mng);
	}
	
	public void makeProduct(){
		String name, id, address, tel, factory, work;
		
		while(true){
			System.out.println();
			System.out.print("이    름 : "); name = sc.next();
			System.out.print("주민번호 : "); id = sc.next();
			System.out.print("주    소 : "); address = sc.next();
			System.out.print("전화번호 : "); tel = sc.next();
			System.out.print("작업공장 : "); factory = sc.next();
			System.out.print("담당작업 : "); work = sc.next();
			break;
			
		}
		
		PrtEmployee prt = new PrtEmployee(name, id, address, tel, factory, work);
		me.addEmployee(prt);
	}
	
	public void makeTempJob(){
		String name, id, address, tel, dept, jobClass;
		
		while(true){
			System.out.println();
			System.out.print("이    름 : "); name = sc.next();
			System.out.print("주민번호 : "); id = sc.next();
			System.out.print("주    소 : "); address = sc.next();
			System.out.print("전화번호 : "); tel = sc.next();
			System.out.print("근무부서 : "); dept = sc.next();
			System.out.print("담당업무 : "); jobClass = sc.next();
			break;
		}
		
		TmpEmployee tmp = new TmpEmployee(name, id, address, tel, dept, jobClass);
		me.addEmployee(tmp);
	}
	
	public void makePartJob(){
		String name, id, address, tel, dept;
		
		while(true){
			System.out.println();
			System.out.print("이    름 : "); name = sc.next();
			System.out.print("주민번호 : "); id = sc.next();
			System.out.print("주    소 : "); address = sc.next();
			System.out.print("전화번호 : "); tel = sc.next();
			System.out.print("근무부서 : "); dept = sc.next();
			break;
		}
		
		PartJobEmployee pj = new PartJobEmployee(name, id, address, tel, dept);
		me.addEmployee(pj);
	}
	
	public void searchMenu(){
		System.out.print("\n검색할 정보를 입력 : ");
		String name = sc.next();
		
		if(me.findEmployee(name)<0){
			System.out.println("찾는 사람이 없습니다.");
		}
	}
	
	public void deleteMenu(){
		System.out.println("\n삭제할 이름을 입력 : ");
		String name = sc.next();
		me.removeEmployee(name);
	}
}
