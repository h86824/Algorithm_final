package CommandLine;

import java.io.File;
import java.util.Scanner;

import Object.Data;

public class CommandLineIO {
	public static final String PATH = "save\\";	//儲存路徑
	
	/*存檔*/
	public static boolean save(Data data){
		Scanner scanner = new Scanner(System.in);
		System.out.println("請輸入要儲存的檔名");
		
		if(!scanner.hasNext()){	//如果讀到EOF ctrl+z
			System.out.println("取消");
			return false;
		}
		
		String fileName = scanner.nextLine();
		new File(PATH).mkdirs();	//沒有資料夾就建立新的
		File file = new File(PATH+fileName+".save");
		if(file.exists()){	//檔案重複
			System.out.println("檔案已經存在 是否覆蓋檔案(Y/N)");
			 while(scanner.hasNext()){
				 
				 String flag = scanner.nextLine();
				 if(flag.split("\\s+")[0].equals("N"))
					 return false;
				 else if(flag.split("\\s+")[0].equals("Y"))
					 break;
				 System.out.println("輸入錯誤，請重新輸入(Y/N)");
			 }
		}
		data.save("autoSave.save");	//自動儲存的
		
		return data.save(file.getPath());
	}
	
	/*讀檔*/
	public static Data read(){
		Scanner scanner = new Scanner(System.in);
		File file = new File(PATH);
		file.mkdir();	//沒有資料夾就建立新的
		String[] arrayFile = file.list();
		
		if(arrayFile.length == 0){
			System.out.println("沒有可以開啟的檔案");
			return null;
		}
		
		System.out.println("檔案列表：");
		for(String f : arrayFile)
			if(f.endsWith(".save"))
				System.out.println(f.substring(0, f.lastIndexOf(".")));

		System.out.println("請輸入想開啟的檔案標號");
		if(!scanner.hasNext()){
			System.out.println("取消");
			return null;
		}
		
		String fileName = scanner.nextLine();
		if(!new File(PATH+fileName+".save").exists()){
			System.out.println("找不到檔案");
			return null;
		}
		
		System.out.println("開啟成功");
		return new Data(file.getPath()+ "\\" + fileName + ".save");
	}
	
	/*開啟新檔*/
	public static Data newFile(){
		Scanner scanner = new Scanner(System.in);
		System.out.println("所有沒有儲存的資料將丟棄，要開啟新的檔案嗎?(Y/N)");
		
		while(scanner.hasNext()){
			String check = scanner.nextLine();
			if(check.equals("Y"))
				break;
			else if(check.equals("N"))
				return null;
			System.out.println("輸入錯誤，請重新輸入(Y/N)");
		}
		return new Data();
	}
}
