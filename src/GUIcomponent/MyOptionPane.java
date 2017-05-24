package GUIcomponent;

import javax.swing.JOptionPane;

import Object.Data;

public class MyOptionPane {
	/**
	 * 轉換ID<br>
	 * 如果輸入的字串不是數字回傳-1
	 * 如果字串是null 回傳-100
	 * */
	private static int stringToID(String ID){
		int returnNumber = -1;
		try{
			if(ID == null)
				return -100;
			returnNumber = Integer.parseInt(ID);
		}catch(Exception e){
			return -1;
		}
		return returnNumber;
	}
	
	/**
	 * 取得新的ID
	 * @param data
	 * @return 大於零學號 , -1 已存在相同ID
	 */
	public static int getNewID(Data data){
		int ID;
		while((ID = stringToID(JOptionPane.showInputDialog("請輸入學號"))) <= 0 && ID != -100 || ID == -1){
			JOptionPane.showMessageDialog(null , "請輸入正確學號 大於0的正整數");	//防呆
		}
		if(data.haveStudent(ID)){
			JOptionPane.showMessageDialog(null, "已存在相同ID");
			return -1;
		}
			
		return ID;
	}
	
	/**
	 * 取得在Data中的學號
	 * @param Data 資料
	 * @return >0 學號 ,-1 取消或沒有這個學生
	 */
	public static int getDataID(Data data){
		int ID;
		
		while((ID = stringToID(JOptionPane.showInputDialog("請輸入學號"))) <= 0 && ID != -100 || ID == -1){
			JOptionPane.showMessageDialog(null , "請輸入正確學號 大於0的正整數");	//防呆
		}
		if(ID == -100){	//ID是-100代表使用者取消
			return -1;
		}
		else if(!data.haveStudent(ID)){	//查詢不到這個ID
			JOptionPane.showMessageDialog(null , "輸入錯誤或沒有這個ID");
			return -1;
		}
		return ID;
	}
	
	/**
	 * 從選單中選取科目 , 選單科目來自data
	 * @param Data 資料
	 * @return 科目 String , null 使用者取消或沒有可以刪除的科目
	 */
	public static String chooseSubject(Data data){
		if(data.getSubjectName().length == 0){ //沒有可以選擇的科目
			JOptionPane.showMessageDialog(null, "沒有可選擇的科目");
			return null;
		}
		String subject = (String) JOptionPane.showInputDialog(null , "請選擇科目" , "科目"  , JOptionPane.INFORMATION_MESSAGE 
				, null , data.getSubjectName() , data.getSubjectName()[0]);
		return subject;
	}
	
	/**
	 * 取得科目名
	 * @return 科目名 ,null 取消
	 */
	public static String getSubject(){
		String newSubject;
		while((newSubject = JOptionPane.showInputDialog("請輸入科目")) != null && newSubject.equals("")){
			JOptionPane.showMessageDialog(null , "請輸入科目");
		}
		return newSubject;
	}
	
	/**
	 * 取得分數
	 * @param Data
	 * @return	>0分數 , -100取消
	 */
	public static int getGrade(Data data){
		int grade;
		while((grade = stringToID(JOptionPane.showInputDialog("請輸入分數"))) < 0 && grade != -100 || grade == -1){
			JOptionPane.showMessageDialog(null , "請輸入分數");	//防呆
		}
		return grade;
	}
}
