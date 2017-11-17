import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;

public class PhoneBookManager {
	public static final int NORMAL = 1;
	public static final int UNIV = 2;
	public static final int COMPANY = 3;
	public static Scanner keyboard = new Scanner(System.in);
	
	private final File dataFile = new File("PhoneBook.txt");
	HashSet<PhoneInfo> infoStorage = new HashSet<PhoneInfo>();
	
	static PhoneBookManager inst = null;
	public static PhoneBookManager createManagerInst(){
		if(inst == null)
			inst = new PhoneBookManager();
		return inst;
	}
	
	private PhoneBookManager(){
		readFromFile();
		System.out.println("파일로부터 데이터를 읽어왔습니다..\n");
	}
	
	private PhoneInfo readFriendInfo(){
		System.out.print("이름 : ");
		String name = keyboard.nextLine();
		System.out.print("전화번호 : ");
		String phone = keyboard.nextLine();
		return new PhoneInfo(name, phone);
	}
	
	private PhoneInfo readUnivFriendInfo(){
		System.out.print("이름 : ");
		String name = keyboard.nextLine();
		System.out.print("전화번호 : ");
		String phone = keyboard.nextLine();
		System.out.print("전공 : ");
		String major = keyboard.nextLine();
		System.out.print("학년 : ");
		int year = keyboard.nextInt();
		keyboard.nextLine();//왜있는거지
		
		return new PhoneUnivInfo(name, phone, major, year);
	}
	
	private PhoneInfo readCompanyInfo(){
		System.out.print("이름 : ");
		String name = keyboard.nextLine();
		System.out.print("전화번호 : ");
		String phone = keyboard.nextLine();
		System.out.print("회사 : ");
		String company = keyboard.nextLine();
				
		return new PhoneCompanyInfo(name, phone, company);
	}
	
	public void inputData() throws MenuChoiceException{
		System.out.println("데이터 입력을 시작합니다..");
		System.out.println("1. 일반, 2. 대학, 3. 회사");
		System.out.print("선택 >> ");
		int choice = keyboard.nextInt();
		keyboard.nextLine();
		PhoneInfo info = null;
		
		if(choice < NORMAL || choice > COMPANY)
			throw new MenuChoiceException(choice);
		
		switch(choice){
		case NORMAL:
			info = readFriendInfo();
			break;
		case UNIV:
			info = readUnivFriendInfo();
			break;
		case COMPANY:
			info = readCompanyInfo();
			break;
		}
		
		boolean isAdded = infoStorage.add(info);
		if(isAdded == true)
			System.out.println("데이터 입력이 완료되었습니다. \n");
		else
			System.out.println("이미 저장된 데이터입니다. \n");
	}
	
	public String searchData(String name){
		Iterator<PhoneInfo> itr = infoStorage.iterator();
		while(itr.hasNext()){
			PhoneInfo curInfo = itr.next();
			if(name.compareTo(curInfo.name) == 0)
				return curInfo.toString();
		}
		
		return null;
	}
	
	public boolean deleteData(String name){
		Iterator<PhoneInfo> itr = infoStorage.iterator();
		while(itr.hasNext()){
			PhoneInfo curInfo = itr.next();
			if(name.compareTo(curInfo.name) == 0){
				itr.remove();
				return true;
			}
		}
		
		return false;
	}
	
	public void saveToFile(){
		try{
			FileOutputStream file = new FileOutputStream(dataFile);
			ObjectOutputStream out = new ObjectOutputStream(file);
			
			Iterator<PhoneInfo> itr = infoStorage.iterator();
			while(itr.hasNext())
				out.writeObject(itr.next());
		
			System.out.println("데이터가 저장되었습니다. \n");
			out.close();
			file.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void readFromFile(){
		if(dataFile.exists() == false)
			return;
		
		try{
			FileInputStream file = new FileInputStream(dataFile);
			ObjectInputStream in = new ObjectInputStream(file);
			
			while(true){
				PhoneInfo info = (PhoneInfo)in.readObject();
				if(info == null)
					break;
				infoStorage.add(info);
			}
			in.close();
			file.close();
		} catch(IOException e){
			return;
		} catch(ClassNotFoundException e){
			return;
		}
	}
}
