package extracter;

public class Employee {

	int id;
	String firstname;
	String lastname;
	String nickname;
	int salary;
	
	
	
	public void setId(int id){
		this.id = id;
	}
	
	public void setFirstName(String firstname){
		this.firstname = firstname;
	}
	
	public void setLastName(String lastname){
		this.lastname = lastname;
	}
	
	public void setNickName(String nick){
		this.nickname = nick;
	}
	
	public void setSalary(int sal){
		this.salary = sal;
	}
	
	
	public int getId(){
		return this.id ;
	}
	
	public String getFirstName(){
		return this.firstname;
	}
	
	public String getLastName(){
		return this.lastname;
	}
	
	public String getNickName(){
		return this.nickname;
	}
	
	public int getSalary(){
		return this.salary;
	}
	
		
}
