package CommandLine;
import Object.Data;

import java.util.Scanner;

public class CommandLine {
	private Data data = new Data("autoSave.save");	//預設讀取autoSave
	private CommandLineMethod commandLineMethod;
	
	public CommandLine(){
		commandLineMethod = new CommandLineMethod(data);
	}
	
	/*執行*/
	public void run() {
		Scanner scInput = new Scanner(System.in);
		
		printMessage();
		while(scInput.hasNext()){
			
			int choose;
			try{
				choose = Integer.parseInt(scInput.nextLine());	//取得選項
			}
			catch(NumberFormatException e){
				choose = 0;
			}
			if(choose == 10)
				break;
			switchAction(choose);
			printMessage();
		}
		
		scInput.close();
	}
	
	private static void printMessage(){
		System.out.println("\n功能代號如下:\n"
				+"1.查詢學生單科成績\n"
				+"2.查詢學生所有成績\n"
				+"3.新增學生成績\n"
				+"4.刪除學生成績\n"
				+"5.排序學生成績\n"
				+"6.學生、科目列表\n"
				+"7.開啟新檔\n"
				+"8.讀取資料\n"
				+"9.儲存資料\n"
				+"10.離開系統\n"
				
				+"請輸入功能代號: ");
	}
	
	private void switchAction(int n){
		switch(n){
		case 1: //查詢學生單科成績
			commandLineMethod.inquireOneGrade();
			break;
		case 2: //查詢學生所有成績
			commandLineMethod.inquireGrades();
			break;
		case 3:	//新增學生成績
			commandLineMethod.addStudentGrade();
			break;
		case 4:	//刪除學生成績
			commandLineMethod.deleteStudentGrade();
			break;
		case 5: //排序學生成績
			commandLineMethod.sortStudentGrade();
			break;
		case 6:	//印出所有學生
			commandLineMethod.printAllStudent();
			break;
		case 7://開啟新檔
			Data newData;
			if(( newData = CommandLineIO.newFile()) != null){
				data = newData;
				commandLineMethod = new CommandLineMethod(data);
			}
			break;
		case 8:	//開啟
			Data openData;
			if(( openData = CommandLineIO.read()) != null){
				data = openData;
				commandLineMethod = new CommandLineMethod(data);
			}
			break;
		case 9:	//儲存
			CommandLineIO.save(data);
			break;
		default:
			System.out.println("不合法的字元或數字");
		}
	}
}
