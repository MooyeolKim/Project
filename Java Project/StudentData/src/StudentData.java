import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

class Student{
	private String name;
	private String major;
	private String num;
	private double score;
	
	
	Student(String name, String major, String num, double score){
		setName(name);
		setMajor(major);
		setNum(num);
		setScore(score);
	}
	
	String getName(){
		return name;	
	}
	String getMajor(){
		return major;
	}
	String getNum(){
		return num;
	}
	double getScore(){
		return score;
	}
	
	void setName(String name){
		this.name = name;
	}
	void setMajor(String major){
		this.major = major;	
	}
	void setNum(String num){
		this.num = num;
	}
	void setScore(double score){
		this.score = score;
	}	
	
	public String toString(){
		return " " + name + " | " + major + " | " + num + " | " + score;
	}
}



public class StudentData {
	public static final int INSERT = 0;
	public static final int DELETE = 1;
	public static final int UPDATE = 2;
	public static final int SEARCH = 3;
	public static final int SHOWALL = 4;
	public static final int EXIT = 5;
	
	public static double average;
	public static double sum = 0;
	public static int cnt = 0;
	
	public static double getAverage(){
		return average = sum/cnt;
	}
	
	private Scanner scan;
	private HashMap<String, Student> map = new HashMap<String, Student>();
	
	public StudentData(){
		scan = new Scanner(System.in);
	}
	
	public void run(){
		System.out.println("성적 관리 프로그램.");
		while(true){
			System.out.print("삽입:0, 삭제:1, 수정:2, 찾기:3, 전체보기:4, 종료:5>>");
			int menu = readStudent();
			switch(menu){
			case INSERT:
				insert(); break;
			case DELETE:
				delete(); break;
			case UPDATE:
				update(); break;
			case SEARCH:
				search(); break;
			case SHOWALL:
				showAll(); break;
			case EXIT:
				System.out.println("프로그램을 종료합니다..."); return;
			default:
				System.out.println("입력이 틀렸습니다. 다시 입력하세요."); continue;
			}
		}
	}
	
	private void insert(){
		System.out.print("이름>>");
		String name = scan.next();
		if(map.get(name)!=null){
			System.out.println("이미 존재하는 사람입니다.");
			return;
		}
		
		System.out.print("학과>>");
		String major = scan.next();
		System.out.print("학번>>");
		String num = scan.next();
		System.out.print("학점>>");
		double score = scan.nextDouble();
		sum += score;
		cnt++;
		
		map.put(name, new Student(name, major, num, score));
	}
	
	private void delete(){
		System.out.print("이름>>");
		String name = scan.next();
		Student st = map.remove(name);
		if(st==null)
			System.out.println(name + "는 등록되지 않은 학생입니다.");
		else
			System.out.println(name + "은 삭제되었습니다.");
	}
	
	private void update(){
		System.out.print("이름>>");
		String name = scan.next();
		Student st = map.get(name);
		if(st == null)
			System.out.println(name + "는 등록되지 않은 사람입니다.");
		else{
			System.out.println(st.toString());
			System.out.print("학점>>");
			double score = scan.nextDouble();
			map.remove(name);
			st = new Student(name, st.getMajor(), st.getNum(), score);
			map.put(name, st);
			System.out.println(st.toString());
			System.out.println("수정이 완료 되었습니다.");
		}
		
		
	}
	
	private void search(){
		System.out.print("이름>>");
		String name = scan.next();
		Student st = map.get(name);
		if(st == null)
			System.out.println(name + "는 등록되지 않은 학생입니다.");
		else
			System.out.println(st.toString());
	}
	
	private void showAll(){
		Set<String> keys = map.keySet();
		Iterator<String> it = keys.iterator();
		while(it.hasNext()){
			String name = it.next();
			Student st = map.get(name);
			System.out.println(st.toString());
		}
		System.out.println("평균학점 : " + StudentData.getAverage());
	}
	
	private int readStudent(){
		int n=-1;
		try{
			n = scan.nextInt();
		}catch(InputMismatchException e){
			return -1;
		}
		return n;
	}
	
	public static void main(String [] args){
		HashMap<String, Student> dept = new HashMap<String, Student>();
		StudentData st = new StudentData();
		st.run();
	}
}
