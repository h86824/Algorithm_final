package Object;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
/**
 * 儲存資料的 class<p>
 * 學生資料的儲存格式:<p>
 * 編碼格式：UTF-8<br>
 * 附檔名: .save<p>
 * 格式:<br>
 * 開頭空一行<br>
 * ID=<br>
 * name=<br>
 * 科目1=<br>
 * 科目2=<br>
 * 科目.....<br>
 * 結束空一行
 * 
 * @author 普皓群
 *
 */
public class Data {
	/**
	 * 檔案的路徑
	 */
	private String path;
	
	/**
	 * 學生的Set<br>
	 * 用來儲存{@link Student}的資料
	 * 
	 * @see Student
	 */
	private Set<Student> studentSet = new TreeSet<>();
	
	/**
	 * 用來儲存科目名字的Set
	 */
	private Set<String> subjectNameSet = new TreeSet<>();
	
	/**
	 * 空的建構子 不想一開始就讀取資料的時候用的
	 */
	public Data(){
		this(null);
	}
	
	/**
	 * 填入path 一開始就開啟儲存的資料
	 * @param path 檔案的路徑
	 */
	public Data(String path){
		this.path = path;
		initial();
	}
	
	/*初始讀取和設定*/
	private void initial(){
		readFile();
		setSubjectNameSet();
	}
	
	/**
	 * 用來新增學生
	 * @param ID 學生的ID
	 */
	public void addStudent(int ID){
		studentSet.add(new Student(ID));
	}
	
	/**
	 * 新增科目
	 * @param s 科目的名稱
	 */
	public void addSubject(String s){
		subjectNameSet.add(s);
	}
	
	/**
	 * 删除科目
	 * @param s 科目的名稱
	 */
	public void deleteSubject(String s){
		if(s == null)	//防呆
			return;
		studentSet.forEach(student -> {
			student.deleteSubject(s);
		});
		subjectNameSet.remove(s);
	}
	
	/**
	 * 更改科目名字
	 * @param before 要更改的科目
	 * @param after 要更改成的科目名
	 */
	public void changeSubjectName(String before , String after){
		studentSet.forEach(student -> student.changeSubjectName(before, after));
		setSubjectNameSet();
	}
	
	/**
	 * 刪除學生
	 * @param ID 要刪除的學生的ID
	 * @return true 刪除成功
	 */
	public boolean deleteStudent(int ID){
		return studentSet.remove(new Student(ID));
	}
	
	/**
	 * 設定指定student成績
	 * @param ID 學生的ID
	 * @param name 科目的名字
	 * @param grade 科目的成績
	 */
	public void setStudentGrade(int ID, String name , int grade){
		Student student = getStudent(ID);	//用ID先拿到Student
		if(student != null){	//防呆處理如果沒有這個學生的ID就不要直接加
			student.setGrade(name, grade);	//如果有的話就直接更改
		}
		//如果沒有這個學生資料 建立新的學生資料
		subjectNameSet.add(name);	//避免科目set沒有這個科目 所以每次都加進去
		student = new Student(ID);
		student.addSubject(name , grade);
		studentSet.add(student);
	}
	
	/**
	 * 刪除student成績
	 * @param ID 要刪除成績的學生ID
	 * @param name 要刪除的科目名
	 * @return true 如果刪除成功
	 */
	public boolean deleteStudentGrade(int ID , String name){
		Student student = getStudent(ID);	//用ID拿到Student
		boolean flag = student.deleteSubject(name);
		setSubjectNameSet(); //可能刪除學生後 某個科目就沒有學生成績了 那個科目應該要被刪除 所以每次都檢查一次
		return flag;
	}
	
	/**
	 * 取得指定student
	 * @param ID 學生ID
	 * @return Student 如果有這個學生的資料<br>null 如果沒有這個學生的資料
	 */
	private Student getStudent(int ID){
		for(Student student : studentSet){
			if(student.getID() == ID)
				return student;
		}
		return null;
	}
	
	/**
	 * 查詢是否有這個學生
	 * @param ID 學生ID
	 * @return true 有這個學生 false 沒有這個學生
	 */
	public boolean haveStudent(int ID){
		return getStudent(ID) != null ? true:false;
	}
	
	/**
	 * 取得單科成績
	 * @param ID 學生的ID
	 * @param name 科目名
	 * @return 分數 如果有的話<br>
	 * -1 如果查詢不到
	 */
	public int getStudentOneSubject(int ID , String name){
		Integer returnInt = getStudent(ID).getGrade(name);
		return returnInt != null ? returnInt : -1;
	}
	
	/**
	 * 取得所有成績
	 * @param ID 學生的ID
	 * @return 一個{@link java.util.Map.Entry Entry}的集合<br>
	 * Entry.key 是科目名<br>
	 * Entry.value 是分數
	 */
	
	public Set<Entry<String,Integer>> getStudentAllSubject(int ID){
		return getStudent(ID).getSubjects();
	}
	
	/**
	 * 取得所有學生
	 * @return 一個{@link Student}的陣列 有所有學生的資料
	 * @see Student
	 */
	public Student[] getAllStudent(){
		Student[] returnArray = new Student[studentSet.size()];
		studentSet.toArray(returnArray);
		return returnArray;
	}
	
	/**
	 * 取得所有有成績的科目
	 * @return 科目名的字串陣列
	 */
	public String[] getSubjectName(){
		String[] returnArray = new String[subjectNameSet.size()];
		subjectNameSet.toArray(returnArray);
		return returnArray;
	}
	
	/**
	 * 讀取檔案  檔案編碼用 UTF-8
	 * 檔案的格式請看Class說明
	 * @return true 開檔成功
	 */
	private boolean readFile(){
		return readFile(path); //因為以後可能會用到沒有引數列的readFile方法 所以先寫好
	}
	
	/**
	 * 讀取檔案 檔案編碼用 UTF-8
	 * 檔案的格式請看Class說明
	 * @param path 檔案路徑
	 * @return true 開檔成功
	 * @see Data
	 */
	private boolean readFile(String path){
		if(path == null)	//沒路徑就不要執行
			return false;
		
		File file = new File(path);
		studentSet = new TreeSet<>();
		Scanner fileScanner;
		try {
			fileScanner = new Scanner(file , "UTF-8");
			fileScanner.nextLine();	//跳過第一行
			
			Student student = new Student(0);
			
			while(fileScanner.hasNext()){
				String nextLine = fileScanner.nextLine();
				
				if(nextLine.split("\\s+|-")[0].equals(""))	//如果是空白的一行就跳過
					continue;
				
				switch(nextLine.split("=")[0]){
				case "ID":	//如果這行是ID
					student = new Student(Integer.parseInt(nextLine.split("=")[1]));	//新增一個新的Student
					studentSet.add(student);
					break;
				case "name"://如果這樣是name
					student.setName(nextLine.split("=")[1].equals(null)? null : nextLine.split("=")[1]);	//設定學生的名字
					break;
				default: //如果不是ID或是name 那就是科目了
					student.addSubject(nextLine.split("=")[0]	//新增一個科目
							, Integer.parseInt(nextLine.split("=")[1]));
				}
				
			}
			
		} catch(FileNotFoundException e){	//找不到這個檔案
			try {
				new File(path).createNewFile();	//新增一個空白的檔案
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return true;
		} catch(java.util.NoSuchElementException e){	//找不到autoSave
			System.out.println("沒有autoSave");
			return false;
		}catch (Exception e) {
			System.out.println("讀檔失敗");
			return false;
		}
		fileScanner.close();
		return true;
	}
	
	/**
	 * 存檔<p>直接存檔 使用預設路徑
	 * @return 存檔成功回傳true <p>存檔失敗回傳false
	 */
	public boolean save(){
		return writeFile(path);
	}
	
	/**
	 * 存檔<p>選擇儲存路徑
	 * @param path 儲存路徑
	 * @return 存檔成功回傳true <p>存檔失敗回傳false
	 */
	public boolean save(String path){
		return writeFile(path);
	}
	
	/*存檔*/
	private boolean writeFile(String path){
		OutputStreamWriter fileWriter;
		try {
			fileWriter=new OutputStreamWriter(new FileOutputStream(path) , "UTF8");
			fileWriter.write("\r\n");	//一開始寫入一行空格 因為如果自行改檔案的話 第一行開頭會有奇怪的東西
			
			for(Student s : studentSet){	//一次儲存一個學生的資料
				fileWriter.write("ID="+s.getID()+"\r\n");
				fileWriter.write("name="+s.getName()+"\r\n");
				for(Entry<String,Integer> subject : s.getSubjects()){	//學生的科目
					fileWriter.write(subject.getKey()+"="+subject.getValue()+"\r\n");
				}
				fileWriter.write("\r\n");	//一個學生資料結束後空一行
			}
			fileWriter.flush();	//清空buffer
			fileWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("存檔失敗");
			return false;
		}
		return true;
	}
	
	/*設定科目Set*/
	private void setSubjectNameSet(){
		subjectNameSet.clear();
		for(Student s: studentSet){
			for(Entry<String , Integer> e : s.getSubjects()){
				subjectNameSet.add(e.getKey());
			}
		}
	}
}
