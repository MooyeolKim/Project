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
		System.out.println("���Ϸκ��� �����͸� �о�Խ��ϴ�..\n");
	}
	
	private PhoneInfo readFriendInfo(){
		System.out.print("�̸� : ");
		String name = keyboard.nextLine();
		System.out.print("��ȭ��ȣ : ");
		String phone = keyboard.nextLine();
		return new PhoneInfo(name, phone);
	}
	
	private PhoneInfo readUnivFriendInfo(){
		System.out.print("�̸� : ");
		String name = keyboard.nextLine();
		System.out.print("��ȭ��ȣ : ");
		String phone = keyboard.nextLine();
		System.out.print("���� : ");
		String major = keyboard.nextLine();
		System.out.print("�г� : ");
		int year = keyboard.nextInt();
		keyboard.nextLine();//���ִ°���
		
		return new PhoneUnivInfo(name, phone, major, year);
	}
	
	private PhoneInfo readCompanyInfo(){
		System.out.print("�̸� : ");
		String name = keyboard.nextLine();
		System.out.print("��ȭ��ȣ : ");
		String phone = keyboard.nextLine();
		System.out.print("ȸ�� : ");
		String company = keyboard.nextLine();
				
		return new PhoneCompanyInfo(name, phone, company);
	}
	
	public void inputData() throws MenuChoiceException{
		System.out.println("������ �Է��� �����մϴ�..");
		System.out.println("1. �Ϲ�, 2. ����, 3. ȸ��");
		System.out.print("���� >> ");
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
			System.out.println("������ �Է��� �Ϸ�Ǿ����ϴ�. \n");
		else
			System.out.println("�̹� ����� �������Դϴ�. \n");
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
		
			System.out.println("�����Ͱ� ����Ǿ����ϴ�. \n");
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
