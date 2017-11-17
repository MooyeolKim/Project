import java.io.Serializable;

public class PhoneInfo implements Serializable{
	String name;
	String phoneNumber;
	
	public PhoneInfo(String name, String num){
		this.name = name;
		phoneNumber = num;
	}
	
	public void showPhoneInfo(){
		System.out.println("name : " + name);
		System.out.println("phone : " + phoneNumber);
	}
	
	public String toString(){
		return "name : " + name + '\n' + "phone : " + phoneNumber + '\n';
	}
	
	public int hashCode(){
		return name.hashCode();
	}
	
	public boolean equals(Object obj){
		PhoneInfo cmp = (PhoneInfo)obj;
		if(name.compareTo(cmp.name) == 0)
			return true;
		else
			return false;
	}
}
