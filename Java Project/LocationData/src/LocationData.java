import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

class Location{
	private String name;
	private double latitude;
	private double longitude;
	
	Location(String name, double latitude, double longitude){
		setName(name);
		setLatitude(latitude);
		setLongitude(longitude);
	}
	
	String getName(){
		return name;	
	}
	double getLatitude(){
		return latitude;
	}
	double getLongitude(){
		return longitude;
	}
		
	void setName(String name){
		this.name = name;
	}
	void setLatitude(double latitude){
		this.latitude = latitude;	
	}
	void setLongitude(double longitude){
		this.longitude = longitude;
	}
	
	public String toString(){
		return " " + name + " | " + latitude + " | " + longitude;
	}
	
}

public class LocationData {
	public static final int INSERT = 0;
	public static final int DELETE = 1;
	public static final int SEARCH = 2;
	public static final int SHOWALL = 3;
	public static final int EXIT = 4;
	
	private Scanner scan;
	private HashMap<String, Location> map = new HashMap<String, Location>();
	
	public LocationData(){
		scan = new Scanner(System.in);
	}
	
	public void run(){
		System.out.println("위치정보 관리 프로그램.");
		while(true){
			System.out.print("삽입:0, 삭제:1, 찾기:2, 전체보기:3, 종료:4>>");
			int menu = readLocation();
			switch(menu){
			case INSERT:
				insert(); break;
			case DELETE:
				delete(); break;
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
		System.out.print("지명>>");
		String name = scan.next();
		if(map.get(name)!=null){
			System.out.println("이미 존재하는 지명입니다.");
			return;
		}
		
		System.out.print("위도>>");
		double latitude = scan.nextDouble();
		System.out.print("경도>>");
		double longitude = scan.nextDouble();
		
		map.put(name, new Location(name, latitude, longitude));
	}
	
	private void delete(){
		System.out.print("지명>>");
		String name = scan.next();
		Location lct = map.remove(name);
		if(lct==null)
			System.out.println(name + "는 등록되지 않은 지명입니다.");
		else
			System.out.println(name + "은 삭제되었습니다.");
	}
	
	private void search(){
		System.out.print("지명>>");
		String name = scan.next();
		Location lct = map.get(name);
		if(lct == null)
			System.out.println(name + "는 등록되지 않은 지명입니다.");
		else
			System.out.println(lct.toString());
	}
	
	private void showAll(){
		Set<String> keys = map.keySet();
		Iterator<String> it = keys.iterator();
		while(it.hasNext()){
			String name = it.next();
			Location lct = map.get(name);
			System.out.println(lct.toString());
		}
	}
	
	private int readLocation(){
		int n=-1;
		try{
			n = scan.nextInt();
		}catch(InputMismatchException e){
			return -1;
		}
		return n;
	}

	public static void main(String [] args){
		HashMap<String, Location> dept = new HashMap<String, Location>();
		LocationData lct = new LocationData();
		lct.run();
	}
}
