import java.io.File;
import java.io.FileWriter;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.Map.Entry;

import Object.Student;

public class RandomDataBuilder {
	private static Set<Student> studentSet = new TreeSet<>();
	
	public static void main(String[] args){
		Random ran = new Random();
		String[] arraySubject = {"DS","DM","PE","Math","English","Chinese"};
		for(int i = 97501 ; i < 97520 ; i++){
			Student newStudent = new Student(i);
			for(String s : arraySubject){
				int range = ran.nextInt(101);
				if(range < 80){
					newStudent.addSubject(s, ran.nextInt(21) + 60);
				}
				else if (range < 95){
					newStudent.addSubject(s , ran.nextInt(21) + 80);
				}
				else{
					newStudent.addSubject(s , ran.nextInt(41) + 19);
				}
			}
				
			studentSet.add(newStudent);
		}
		writeFile("ran"+System.currentTimeMillis()+".save");
	}
	
	private static boolean writeFile(String path){
		File file = new File(path);
		FileWriter fileWriter;
		try {
			fileWriter=new FileWriter(file);
			fileWriter.write("\r\n");

			for(Student s : studentSet){
				fileWriter.write("ID="+s.getID()+"\r\n");
				fileWriter.write("name="+s.getName()+"\r\n");
				for(Entry<String,Integer> subject : s.getSubjects()){
					fileWriter.write(subject.getKey()+"="+subject.getValue()+"\r\n");
				}
				fileWriter.write("\r\n");
			}
			fileWriter.flush();
			fileWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("存檔失敗");
			
			return false;
		}
		return true;
	}
}
