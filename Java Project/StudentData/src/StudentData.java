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
		System.out.println("���� ���� ���α׷�.");
		while(true){
			System.out.print("����:0, ����:1, ����:2, ã��:3, ��ü����:4, ����:5>>");
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
				System.out.println("���α׷��� �����մϴ�..."); return;
			default:
				System.out.println("�Է��� Ʋ�Ƚ��ϴ�. �ٽ� �Է��ϼ���."); continue;
			}
		}
	}
	
	private void insert(){
		System.out.print("�̸�>>");
		String name = scan.next();
		if(map.get(name)!=null){
			System.out.println("�̹� �����ϴ� ����Դϴ�.");
			return;
		}
		
		System.out.print("�а�>>");
		String major = scan.next();
		System.out.print("�й�>>");
		String num = scan.next();
		System.out.print("����>>");
		double score = scan.nextDouble();
		sum += score;
		cnt++;
		
		map.put(name, new Student(name, major, num, score));
	}
	
	private void delete(){
		System.out.print("�̸�>>");
		String name = scan.next();
		Student st = map.remove(name);
		if(st==null)
			System.out.println(name + "�� ��ϵ��� ���� �л��Դϴ�.");
		else
			System.out.println(name + "�� �����Ǿ����ϴ�.");
	}
	
	private void update(){
		System.out.print("�̸�>>");
		String name = scan.next();
		Student st = map.get(name);
		if(st == null)
			System.out.println(name + "�� ��ϵ��� ���� ����Դϴ�.");
		else{
			System.out.println(st.toString());
			System.out.print("����>>");
			double score = scan.nextDouble();
			map.remove(name);
			st = new Student(name, st.getMajor(), st.getNum(), score);
			map.put(name, st);
			System.out.println(st.toString());
			System.out.println("������ �Ϸ� �Ǿ����ϴ�.");
		}
		
		
	}
	
	private void search(){
		System.out.print("�̸�>>");
		String name = scan.next();
		Student st = map.get(name);
		if(st == null)
			System.out.println(name + "�� ��ϵ��� ���� �л��Դϴ�.");
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
		System.out.println("������� : " + StudentData.getAverage());
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
