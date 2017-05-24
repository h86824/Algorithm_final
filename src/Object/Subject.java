package Object;

public class Subject {
	private String name;
	private int grade;
	
	public Subject(String name){
		this(name , -1);
	}
	
	public Subject(String name , int grade){
		this.name = name;
		this.grade = grade;
	}
	
	public String getName(){
		return name;
	}
	
	public int getGrade(){
		return grade;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setGrade(int grade){
		this.grade = grade;
	}
}
