package CommandLine;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import java.util.Map.Entry;

import Object.Data;
import Object.MySelector;
import Object.Student;
import SortAlgorithm.SortAlgorithm;

public class CommandLineMethod {
	private Data data;
	
	public CommandLineMethod(Data data){
		this.data = data;
	}
	
	/*查詢學生單科成績*/
	public void inquireOneGrade(){
		System.out.println("請輸入要查詢的學號+科目名(空白隔開,0離開):");
		Scanner scInput = new Scanner(System.in);
		while(scInput.hasNext()){
			try{
				String[] command = scInput.nextLine().split("\\s+");
				int ID = Integer.parseInt(command[0]);
				if(ID == 0)
					break;
				
				boolean flag = false;
				for(int i = 1 ; i < command.length ; i++){
					int grade = data.getStudentOneSubject(ID, command[i]);
					
					if(grade >= 0){
						System.out.println(command[i] + " " + grade);
						flag = true;
					}
				}
				if(!flag){
					System.out.println("查無資料");
				}
			}
			catch(NumberFormatException e){
				System.out.println("輸入非合法字元");
			}
			System.out.println("請輸入要查詢的學號+科目名(空白隔開,0離開):");
		}
	}
	
	/*查詢學生所有成績*/
	public void inquireGrades(){
		System.out.println("請輸入要查詢的學號(0離開):");
		Scanner scInput = new Scanner(System.in);
		while(scInput.hasNext()){
			try{
				int ID = Integer.parseInt(scInput.nextLine());
				if(ID == 0)
					break;
				
				boolean flag = false;
				for(Entry<String,Integer> s : data.getStudentAllSubject(ID)){
					flag = true;
					System.out.println(s.getKey() + " " + s.getValue());
				}
				if(!flag){
					System.out.println("查無資料");
				}
			}
			catch(NumberFormatException e){
				System.out.println("輸入非合法字元");
			}
			System.out.println("請輸入要查詢的學號(0離開):");
		}
	}
	
	/*新增學生成績*/
	public void addStudentGrade(){
		Scanner scInput = new Scanner(System.in);
		System.out.println("請輸入要新增的學號+科目名+成績(空白隔開,0離開):");
		while(scInput.hasNext()){
			try{
				String[] command = scInput.nextLine().split("\\s+");
				int ID = Integer.parseInt(command[0]);
				if(ID == 0)
					break;
				String subjectName = command[1];
				int subjectGrade = Integer.parseInt(command[2]);
				data.setStudentGrade(ID, subjectName, subjectGrade);
				System.out.println("更新成功");
			}
			catch(NumberFormatException e){
				System.out.println("輸入非合法字元");
			}
			catch(ArrayIndexOutOfBoundsException e){
				System.out.println("格式錯誤");
			}
			System.out.println("請輸入要新增的學號+科目+成績(空白隔開,0離開):");
		}
	}
	
	/*刪除學生成績*/
	public void deleteStudentGrade(){
		Scanner scInput = new Scanner(System.in);
		System.out.println("請輸入要刪除的學號+科目名(空白隔開,0離開):");
		while(scInput.hasNext()){
			try{
				String[] command = scInput.nextLine().split("\\s+");
				int ID = Integer.parseInt(command[0]);
				if(ID == 0)
					break;
				String subjectName = command[1];
				if(data.deleteStudentGrade(ID, subjectName)){
					System.out.println("刪除成功");
				}
				else{
					System.out.println("刪除失敗，" + ID + "沒有此成績");
				}
			}
			catch(NumberFormatException e){
				System.out.println("輸入非合法字元");
			}
			catch(ArrayIndexOutOfBoundsException e){
				System.out.println("格式錯誤");
			}
			System.out.println("請輸入要刪除的學號+科目+成績(空白隔開,0離開):");
		}
	}
	
	/*排序學生成績*/
	public void sortStudentGrade(){
		Scanner scInput = new Scanner(System.in);
		System.out.println("請輸入科目名稱(0離開):");
		try{
			while(scInput.hasNext()){
				String subject = scInput.nextLine().split("\\s+")[0];
				if(subject.equals("0"))
					break;
				System.out.println("請選擇排序方法：");
				String sortAlgorithmString = "";
				for(int i = 0 ; i < MySelector.ALGORITHM_LIST.length ; i++){
					sortAlgorithmString += i + "." + MySelector.ALGORITHM_LIST[i] + "\n";
				}
				System.out.print(sortAlgorithmString);
				
				int sortType = Integer.parseInt(scInput.nextLine().split("\\s+")[0]);
				
				ArrayList<Student> arrayStudent = new ArrayList<>();
				for(Student st : data.getAllStudent()){
					if(st.getGrade(subject) != null){
						arrayStudent.add(st);
					}
				}
				
				Student[] studentTable = new Student[arrayStudent.size()];
				arrayStudent.toArray(studentTable);
				SortAlgorithm sortAlgorithm = MySelector.switchSortAlgorithm(sortType);
				
				int[] counter = sortAlgorithm.sort(studentTable , new Comparator<Student>(){
					@Override
					public int compare(Student o1, Student o2) {
						return o1.getGrade(subject) - o2.getGrade(subject);
					}
				});
				
				for(Student st : studentTable){
					System.out.println(st.getID() + "\t" + subject +" " + st.getGrade(subject));
				}
				if(arrayStudent.size() == 0){
					System.out.println("查無資料");
				}
				else{
					System.out.println("使用" + sortAlgorithm.getName() +"排序，系統完成排序共比較"
							+ counter[0] +"次，交換元素" + counter[1] + "次");
				}
				System.out.println("請輸入科目名稱(0離開):");
			}
		}
		catch(NumberFormatException e){
			System.out.println("輸入非合法字元");
		}
		catch(ArrayIndexOutOfBoundsException e){
			System.out.println("格式錯誤");
		}
	}
	
	/*列出所有學生*/
	public void printAllStudent(){
		Student[] student = data.getAllStudent();
		//列出學生
		System.out.println("\n學生:");
		for(Student s : student)
			System.out.println(s.getID());
		
		if(student.length == 0)
			System.out.println("沒有學生資料");
		//列出科目
		System.out.println("\n科目:");
		String[] subjects = data.getSubjectName();
		for(String s : subjects)
			System.out.println(s);
		
		if(subjects.length == 0)
			System.out.println("沒有科目");
		//確認
		Scanner scanner = new Scanner(System.in);
		System.out.print("\n請按下 enter 繼續:");
		scanner.nextLine();
	}
}
