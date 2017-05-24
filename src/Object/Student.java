package Object;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Student implements Comparable<Student>{
	/**
	 * 學生的ID<br>
	 * 學生的ID必須要是大於零的正整數
	 */
	private int ID;
	
	/**
	 * 學生的名字<br>
	 * 現在還沒用到未來可能會用到 所以就先寫起來放了
	 */
	private String name;
	
	/**
	 * 用來儲存學生各科成績的Map<p>
	 * 	key 是 科目名<br>
	 * 	value 是 分數
	 */
	private Map<String , Integer> subject = new HashMap<>();
	
	/**
	 * 因為每個學生都一定要有一個ID 所以不提供沒有ID的建構子<p>
	 * <font color="#FF0000">ID一旦建立就不能更改</font>
	 * @param ID 學生的ID
	 */
	public Student(int ID){
		this(ID , null);
	}
	
	/**
	 * 除了ID 還可以在建構的時候加入名字<p>
	 * <font color="#FF0000">ID一旦建立就不能更改</font>
	 * @param ID 學生的ID
	 * @param name 學生的名字
	 */
	public Student(int ID , String name){
		this.ID = ID;
		this.name = name;
	}
	
	/**
	 * 取得這個學生物件的ID
	 * @return 學生的ID
	 */
	public int getID(){
		return ID;
	}
	
	/**
	 * 設定這個學生物件的名字
	 * @param name 要更改的學生名字
	 */
	public void setName(String name){
		this.name = name;
	}
	
	/**
	 * 取得這個學生物件的名字
	 * @return 學生的名字
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * 新增學生的某個科目
	 * @param name 要新增的科目名
	 */
	public void addSubject(String name){
		subject.put(name , null);
	}
	
	/**
	 * 新增學生的某個科目 並加入分數
	 * @param name 要新增的科目名
	 * @param grade 分數
	 */
	public void addSubject(String name , int grade){
		setGrade(name , grade);
	}
	
	/**
	 * 刪除學生的某個科目資料
	 * @param name 要刪除的科目資料
	 * @return true 刪除成功 false 沒有這個科目
	 */
	public boolean deleteSubject(String name){
		return subject.remove(name) != null ? true : false;
	}
	
	/**
	 * 取得科目的成績
	 * @param subjectName 要取得成績的科目名
	 * @return 分數 {@link Integer }
	 */
	public Integer getGrade(String subjectName){
		return subject.get(subjectName);
	}
	
	/**
	 * 取得學生的所有科目的資料
	 * @return 所有科目的Set<{@link Entry Entry}> <p>
	 * Entry.key 是 科目名<br>
	 * Entry.value 是 成績
	 * 
	 * @see Set
	 * @see Entry
	 */
	public Set<Entry<String,Integer>> getSubjects(){
		return subject.entrySet();
	}
	
	/**
	 * 設定某個科目的成績
	 * @param name 科目名
	 * @param grade 成績
	 */
	public void setGrade(String name , int grade){
		subject.put(name, grade);
	}
	
	/**
	 * 更改某個科目的名字
	 * @param nowName 現在的名字
	 * @param newName 要改成的名字
	 * @return true更改成功 false 失敗或沒有這個科目
	 */
	public boolean changeSubjectName(String nowName , String newName){
		Integer grade = subject.remove(nowName);
		if(grade == null)	//沒有這個科目
			return false;
		subject.put(newName, grade);
		return true;
	}
	
	@Override 
	public int hashCode(){
		return 0;
	}
	
	@Override
	public boolean equals(Object o){
		if(o.getClass() != this.getClass())
			return false;
		
		Student s = (Student) o;
		if(this.getID() != s.getID())	//以ID比較 相同ID代表是相同的學生
			return false;
		
		return true;
	}
	
	@Override
	public int compareTo(Student o) {
		return getID() - o.getID();
	}
}
