import java.util.Scanner;


public class PhoneBook {
	public static final int INPUT = 1;
	public static final int SAVE = 2;
	public static final int EXIT = 3;
	public static Scanner keyboard = new Scanner(System.in);
	
	public static void main(String [] args){
		PhoneBookManager manager = PhoneBookManager.createManagerInst();
		SearchDelFrame winFrame = new SearchDelFrame("PhoneBook");
		
		int choice;
		
		while(true){
			try{
				System.out.println("�����ϼ���...");
				System.out.println("1. ������ �Է�");
				System.out.println("2. ������ ����(����)");
				System.out.println("3. ���α׷� ����");
				System.out.print("���� : ");				
				choice = keyboard.nextInt();
				keyboard.nextLine();
				
				if(choice < INPUT || choice > EXIT)
					throw new MenuChoiceException(choice);
				
				switch(choice){
				case INPUT:
					manager.inputData();
					break;
				case SAVE:
					manager.saveToFile();
					break;
				case EXIT:
					System.out.println("���α׷��� �����մϴ�.");
					System.exit(0);
					return;
				}
			} catch(MenuChoiceException e){
				e.showWrongChoice();
				System.out.println("�޴� ������ ó������ �ٽ� �����մϴ�. \n");
			}
		}
	}
}
