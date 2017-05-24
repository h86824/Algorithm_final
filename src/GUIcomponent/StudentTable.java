package GUIcomponent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Object.Data;
import Object.Student;
import SortAlgorithm.SortAlgorithm;

/**
 * 自己新增方法改寫的學生的Table
 * @author 普皓群
 * @see JTable
 */
@SuppressWarnings("serial")
public class StudentTable extends JTable{
	/**
	 * 學生Table的Model
	 * @see DefaultTableModel
	 */
	private DefaultTableModel studentTableModel;
	
	/**
	 * 資料來源
	 * @see Data
	 */
	private Data data;
	
	/**
	 * 儲存學生陣列
	 * @see Student
	 */
	private Student[] student;
	
	/**
	 * 提供一個不用輸入資料的建構子
	 */
	public StudentTable(){
		studentTableModel = new DefaultTableModel() {
		    @Override
		    public boolean isCellEditable(int row, int column) {	//表單是否可以更改
		       return false;	//全部都不能更改
		    }
		};
		this.setModel(studentTableModel);
	}
	
	/**
	 * 提供一個有預設資料的建構子
	 * @param data 想要一開始就初始化的資料
	 * @see Data
	 */
	public StudentTable(Data data){
		this();
		this.data = data;
		setTable();
	}
	
	/**
	 * 自動重新設定table的identifiers<br>
	 * 通常在設定完之後還需要{@link sortTable}才會更新table
	 * @see sortTable
	 */
	public void setTable(){
		setTable("");
	}
	
	/**
	 * 自動重新設定table的identifiers<br>
	 * 在參數欄內可選擇要顯示出來的ID 用空白或是','隔開可選擇多個<br>
	 * '~'可以選定一個範圍<br>
	 * 空白或null代表選擇全部<br>
	 * 通常在設定完之後還需要{@link sortTable}才會更新table
	 * @see sortTable
	 */
	public void setTable(String name){
		List<String> names;
		if(name == null){ //防呆
			names = new ArrayList<>();
		}
		else{
			names = Arrays.asList(split(name));
		}
		
		student = data.getAllStudent();
		if(names.size() > 0 && !names.get(0).equals("")){	//有輸入才有
			List<Student> temp = new ArrayList<>(); 
			temp = Stream.of(student).filter(n -> names.indexOf(n.getID()+"") >= 0).collect(Collectors.toList());	//符合條件的加入temp
			student = new Student[temp.size()];
			temp.toArray(student);
		}
		
		Object[] subjectArray = data.getSubjectName();	//從data取得所有科目的名字
		Object[] identifiers = new Object[subjectArray.length+1];	//長度+1是因為還要加入ID欄位
		identifiers[0] = "ID";	//第一個欄位固定是ID
		for(int i = 1 ; i < subjectArray.length + 1 ; i++){	
			identifiers[i] = subjectArray[i-1];
		}
		studentTableModel.setColumnIdentifiers(identifiers);
	}
	
	/**
	 * 分解字串 分割字串',' 範圍'~'
	 * @param s
	 * @return
	 */
	private String[] split(String s){
		List<String> names = Arrays.asList(s.split("\\s+|,"));
		List<String> temp = new ArrayList<>();
		
		names.forEach(n -> {
			if(n.indexOf('~') > 0){
				String[] between = n.split("~");
				int upper;
				int lower;
				try{
					lower = Integer.parseInt(between[0]);
					upper = Integer.parseInt(between[1]);
				}
				catch(NumberFormatException e){
					upper = -2;
					lower = -1;
				}
				IntStream.rangeClosed(lower , upper).forEach(number ->{
					temp.add(number+"");
				});
			}
			else{
				temp.add(n);
			}
		});
		String[] returnArray = new String[temp.size()];
		temp.toArray(returnArray);
		return returnArray;
	}
	
	/**
	 * 排序table
	 * @param sortAlgorithm 排序演算法{@link SortAlgorithm}
	 * @param cmp 排序的Comparator{@link Comparator}
	 * @return int陣列<br> 
	 * index 0 比較次數<br> 
	 * index 1 交換次數
	 * 
	 * @see SortAlgorithm
	 * @see Comparator
	 */
	public int[] sortTable(SortAlgorithm sortAlgorithm , Comparator<Student> cmp){
		int[] count = sortAlgorithm.sort(student, cmp);
		
		studentTableModel.setRowCount(student.length);
		for(int i = 0 ; i < student.length ; i++){
			studentTableModel.setValueAt(student[i].getID() , i , 0);
			for(int j = 1 ; j < studentTableModel.getColumnCount() ; j++){
				String subject = studentTableModel.getColumnName(j);
				//如果學生是null的話
				studentTableModel.setValueAt(student[i].getGrade(subject) != null ? student[i].getGrade(subject) : "--" , i, j);
			}
		}
		return count;
	}
	
	/**
	 * 設定新的資料<p>
	 * 通常在設定完之後還需要{@link sortTable}才會更新table
	 * @param data 要設定的資料{@link Data}
	 */
	public void setData(Data data){
		this.data = data;
		setTable();
	}
	
	/**
	 * 更改table上 某個學生的成績<br>
	 * <font color="#FF0000">這裡只會更改table上的資料 應該還要更改Data裡的資料</font>
	 * @param ID 學生的ID
	 * @param subject 要更改的科目名
	 * @param grade 成績
	 * @see Data
	 */
	public void setStudentGrade(int ID, String subject ,int grade){
		int row = -1;
		int column = -1;
		for(int i = 1 ; i < studentTableModel.getColumnCount() ; i++){
			if(studentTableModel.getColumnName(i).equals(subject)){	//找到科目
				column = i;
				for(int j = 0 ; j < studentTableModel.getRowCount() ; j++){
					if(studentTableModel.getValueAt(j, 0).equals(ID)){	//找到學生
						row = j;
						break;
					}
				}
				break;
			}
		}
		studentTableModel.setValueAt(grade, row , column);	//更改找到欄位的值
	}
	
	/**
	 * 設定某個科目欄位是否可見
	 * @param subject 要設定欄位的科目名
	 * @param visible
	 * true 顯示<br> false 隱藏
	 */
	public void setSubjectVisible(String subject , boolean visible){
		if(visible){	//顯示
			for(int i = 1 ; i < studentTableModel.getColumnCount() ; i++){
				if(subject.equals(studentTableModel.getColumnName(i))){	//找到欄位
					javax.swing.table.TableColumn Column = this.getColumnModel().getColumn(i);	//從model中找到那個Column
					javax.swing.table.TableColumn defaultColumn = this.getColumnModel().getColumn(0); //以第一個欄位當要顯示欄位的基準
					Column.setMinWidth(defaultColumn.getMinWidth());	//把顯示的欄位寬度最小值設定的和基準欄位一樣
					Column.setMaxWidth(defaultColumn.getMaxWidth());	//把顯示的欄位寬度最大值設定的和基準欄位一樣
					Column.setPreferredWidth(75);
					break;
				}
			}
		}
		else{	//隱藏 只是把寬度變成0 就看不到了 沒有實際刪除
			for(int remove = 0 ; remove < this.getColumnCount() ; remove++){
				if(subject.equals(this.getColumnName(remove))){
					javax.swing.table.TableColumn Column = this.getColumnModel().getColumn(remove);
					Column.setMinWidth(0);	//把隱藏的欄位寬度最小值設定為0
					Column.setMaxWidth(0);	//把隱藏的欄位寬度最大值設定為0
					Column.setPreferredWidth(0);
					break;
				}
			}
		}
	}
}
