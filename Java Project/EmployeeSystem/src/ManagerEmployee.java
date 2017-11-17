
public class ManagerEmployee {
	Employee[] em;
	int index;
	int find_idx;
	
	public ManagerEmployee(){
		this(100);
	}
	
	public ManagerEmployee(int size){
		em = new Employee[size];
	}
	
	public void addEmployee(Employee employee){
		int count = findEmployee(employee.getId());
		if(count < 0){
			em[index] = employee;
			index++;
			System.out.printf("\n%s ��ϵǾ����ϴ�.\n", employee.getName());
		} else{
			System.out.println("\n�ߺ��Ǵ� ����� �ֽ��ϴ�.");
		}
	}
	
	public int findEmployee(String input){
		boolean check = false;
		
		for(int i=0 ; i<index ; i++){
			System.out.println();
			if(em[i].getName().equals(input)){
				check = true;
				em[i].printAll();
			}
			
			else if(em[i].getId().equals(input)){
				check = true;
				em[i].printAll();
			}
			
			else if(em[i].getAddress().equals(input)){
				check = true;
				em[i].printAll();
			}
			
			else if(em[i].getTel().equals(input)){
				check = true;
				em[i].printAll();
			}
		}
		
		if(!check)
			find_idx = -1;
		else
			find_idx = 1;
				
		return find_idx;
	}
	
	public void removeEmployee(String name){
		int count = findEmployee(name);
		
		if(count >= 0){
			System.out.println();
			
			for(int i=count ; i<index ; i++){
				em[i] = em[i+1];
			}
			index--;
			System.out.println("\n�����Ǿ����ϴ�.");
		}else{
			System.out.println("\n������ ����� �����ϴ�.");
		}
	}
	
	public void displayEmployee(){
		for(int i=0 ; i<index ; i++){
			System.out.println("\n[" + i + "]");
			em[i].printAll();
		}
	}
}




