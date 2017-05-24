package GUIcomponent;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import Object.Data;
import Object.MySelector;
import Object.Student;
import SortAlgorithm.SelectionSort;
import SortAlgorithm.SortAlgorithm;

public class GUI extends JFrame{

	private static Data data = new Data("autoSave.save");
	private StudentTable studentTable = new StudentTable(data);
	private JPanel upperPanel = new JPanel();
	private ButtonListener buttonListener = new ButtonListener();
	private JComboBox<String> SubjectComboBox = new JComboBox<>();
	private JComboBox<String> sortOrderComboBox = new JComboBox<>();
	private JComboBox<String> sortComboBox = new JComboBox<>();
	private Comparator<Student> cmp;
	private SortAlgorithm sortAlgorithm = new SelectionSort();
	private JMenuBar menuBar = new JMenuBar();
	private JMenu menuFile = new JMenu("檔案");
	private JPanel rightPanel = new JPanel();
	private JTextField splitText = new JTextField();
	
	public GUI(){

		/*設定大小*/
		java.awt.Dimension scr_size = java.awt.Toolkit.getDefaultToolkit().getScreenSize();	//取得螢幕解析度
		this.setSize(scr_size.width * 4 / 5 , scr_size.height * 4 / 5);	//將視窗大小設為螢幕解析度的五分之四 才能支援高解析度
		this.setLocation(	//視窗置中
				   (scr_size.width - this.getWidth()) / 2,
				   (scr_size.height - this.getHeight()) / 2);
		this.setMinimumSize(new Dimension(scr_size.width * 4 / 5 , scr_size.height * 1 / 2));	//設定視窗最小值
		
		/*視窗設定*/
		this.setTitle("成績排序系統");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setUIFont(new FontUIResource("PMingLiU",Font.CENTER_BASELINE,18));
		
		/*功能列*/
		String[] stringMenuFile = {"開啟新檔","開啟","儲存"};
		for(int i = 0 ; i < stringMenuFile.length ; i++){
			JMenuItem newItem = new JMenuItem(stringMenuFile[i]);
			menuFile.add(newItem);
			newItem.addActionListener(new MenuListener());
		}
		menuBar.add(menuFile);
		this.setJMenuBar(menuBar);
		
		/*上方欄位*/
		this.add(upperPanel , BorderLayout.NORTH);
		initUpperPanel();
		
		/*設定table*/
		JScrollPane jsp = new JScrollPane();
		jsp.setViewportView(studentTable);
		this.add(new JScrollPane(studentTable));
		tableSet();
		
		/*右方功能列*/
		this.add(rightPanel,BorderLayout.EAST);
		initRightPanel();
	}
	
	/*選擇comparator*/
	private void chooseCMP(int n){
		cmp = MySelector.chooseCMP(n, (String) SubjectComboBox.getSelectedItem());
	}
	
	/*排序建立table*/
	private int[] tableSort(){
		if(cmp == null)
			chooseCMP(0);
		sortAlgorithm = switchSortAlgorithm(sortComboBox.getSelectedIndex());
		studentTable.setTable(splitText.getText());
		return studentTable.sortTable(sortAlgorithm, cmp);
	}
	
	/*設定table model*/
	private void tableSet(){
		studentTable.setData(data);
		tableSort();
	}
	
	/*設定全域UI字形*/
	public void setUIFont (FontUIResource fui){
		 Enumeration keys=UIManager.getDefaults().keys();
		 while (keys.hasMoreElements()) {
			 Object key=keys.nextElement();
			 Object value=UIManager.get(key);
			 if (value != null && value instanceof FontUIResource) {
				 UIManager.put(key, fui);
			 }
		 } 
	}
	 
	/*上方欄位設定*/
	private void initUpperPanel(){
		upperPanel.setBounds(5, 5, 5, 5);
		
		/*加入功能按鈕*/
		JPanel panelData = new JPanel();
		String [] buttonCommand = {"新增學生" , "新增科目" , "修改科目" , "删除科目" , "修改成績" , "刪除學生" };
		JButton[] arrayButton = new JButton[buttonCommand.length];
		
		IntStream.range(0, buttonCommand.length).forEach(i -> {
			arrayButton[i] = new JButton(buttonCommand[i]);
			arrayButton[i].addActionListener(buttonListener);
			panelData.add(arrayButton[i]);
		});
		
		upperPanel.add(panelData);
		
		/*排序設定*/
		JPanel panelSortOption = new JPanel(); 
		//排序方法
		panelSortOption.add(new JLabel("排序方法："));
		String [] sortAlgorithm = MySelector.ALGORITHM_LIST;
		for(String s : sortAlgorithm)
			sortComboBox.addItem(s);
		panelSortOption.add(sortComboBox);
		
		//排序科目
		panelSortOption.add(new JLabel("排序方式："));
		updateSubjectComboBox();
		panelSortOption.add(SubjectComboBox);
		
		sortOrderComboBox.addItem("遞增");
		sortOrderComboBox.addItem("遞減");
		panelSortOption.add(sortOrderComboBox);
		
		//篩選條件
		JPanel splitPanel = new JPanel(new GridLayout(1,2));
		splitPanel.add(new JLabel("篩選ID："));
		splitPanel.add(splitText);
		
		//排序確認按鈕
		JButton buttonEnterSortOption = new JButton("重新排序");
		buttonEnterSortOption.addActionListener(buttonListener);
		panelSortOption.add(buttonEnterSortOption);
		upperPanel.add(splitPanel);
		upperPanel.add(panelSortOption);
	}
	
	/*更新選單科目*/
	private void updateSubjectComboBox(){
		SubjectComboBox.removeAllItems();
		String [] arraySubject = data.getSubjectName();
		SubjectComboBox.addItem("ID");
		for(String s : arraySubject)
			SubjectComboBox.addItem(s);
		initRightPanel();
	}
	
	/*功能按鈕的Listener*/
	private class ButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(!e.getActionCommand().equals("重新排序")){	//不是重新排序就清空篩選ID
				splitText.setText("");
			}
			switch(e.getActionCommand()){
			case "新增學生":
				addStudent();
				break;
			case "新增科目":
				addSubject();
				break;
			case "修改成績":
				editGrade();
				break;
			case "刪除學生":
				deleteStudent();
				break;
			case "删除科目":
				deleteSubject();
				break;
			case "重新排序":
				reSorting();
				break;
			case "修改科目":
				editSubject();
				break;
			}
		}
		
		/*新增學生*/
		private void addStudent(){
			int newID = -1;
			newID = MyOptionPane.getNewID(data);
			if(newID > 0){
				data.addStudent(newID);
				tableSet();
			}
		}
		
		/*新增科目*/
		private void addSubject(){
			String newSubject = "";
			newSubject = MyOptionPane.getSubject();
			
			if(newSubject != null){
				data.addSubject(newSubject);
				updateSubjectComboBox();
				initRightPanel();
				tableSet();
			}
		}
		
		/*刪除學生*/
		private void deleteStudent(){
			int newID = -1;
			newID = MyOptionPane.getDataID(data);
			if(newID > 0 && JOptionPane.showConfirmDialog(null, "真的要刪除 "+newID+" 嗎" , "警告", JOptionPane.YES_OPTION) == 0){
				if(data.deleteStudent(newID)){
					JOptionPane.showMessageDialog(null , "刪除成功");
					tableSet();
				}
				else{
					JOptionPane.showMessageDialog(null , "刪除失敗，沒有這個學生");
				}	
			}
		}
		
		/*重新排序*/
		private void reSorting(){
			String SortSubject = (String) SubjectComboBox.getSelectedItem();
			if(SortSubject.equals("ID")){
				if(sortOrderComboBox.getSelectedItem().equals("遞增"))
					chooseCMP(0);
				else
					chooseCMP(1);
			}
			else{
				if(sortOrderComboBox.getSelectedItem().equals("遞增")){
					chooseCMP(2);
				}
				else{
					chooseCMP(3);
				}
			}
			int[] counter = tableSort();
			JOptionPane.showMessageDialog(null, "使用" + sortAlgorithm.getName() +"排序，系統完成排序共比較"
							+ counter[0] +"次，交換元素" + counter[1] + "次");
		}
		
		/*修改成績*/
		private void editGrade(){
			int ID;
			int grade;
			
			if((ID = MyOptionPane.getDataID(data)) < 0)//取得學號 回傳值<0 代表成失敗
				return; 
			
			//選擇科目
			String subject;
			if((subject = MyOptionPane.chooseSubject(data)) == null)	//null 代表取消
				return;
			
			//選擇分數
			if((grade = MyOptionPane.getGrade(data)) < 0)	//小於零取消
				return;
			
			//更新資料
			data.setStudentGrade(ID, subject , grade);
			if(!subject.equals(SubjectComboBox.getSelectedItem())){	//如果要更新的科目不是排列好的科目 可以直接修改
				studentTable.setStudentGrade(ID, subject, grade);
			}
			else
				tableSet();	//重新設定
		}
		
		private void deleteSubject(){
			//選擇科目
			String subject;
			subject = MyOptionPane.chooseSubject(data);	//取得科目
			
			data.deleteSubject(subject);
			tableSet(); //重新設定
		}
		
		/*修改科目*/
		private void editSubject(){
			String before;	//更改前
			String after;	//更改後
			if((before =  MyOptionPane.chooseSubject(data)) == null)
				return; //取消就結束
			if((after = MyOptionPane.getSubject()) == null)
				return;	//取消就結束
			data.changeSubjectName(before, after);
			tableSet();
		}
	}
	
	/*選單的Listener*/
	private class MenuListener implements ActionListener{
		JFileChooser fileChooser = new JFileChooser("."+File.separator+"");
		
		@Override
		public void actionPerformed(ActionEvent e) {
			fileChooser.setFileFilter(new MyFileFilter(".save","save")); //儲存的附檔名為save
			
			switch(e.getActionCommand()){
			case"開啟新檔":
				data = new Data();
				studentTable.setData(data);
				studentTable.sortTable(sortAlgorithm, cmp);
				updateSubjectComboBox();
				break;
			case"開啟":
				load();
				break;
			case "儲存":
				save();
				break;
			}
		}
		
		private void load(){
			int returnValue = fileChooser.showOpenDialog(null);
			if (returnValue == JFileChooser.APPROVE_OPTION){
				data = new Data(fileChooser.getSelectedFile().getPath());
				SubjectComboBox.removeAllItems();
				SubjectComboBox.addItem("ID");
				for(String s : data.getSubjectName()){
					SubjectComboBox.addItem(s);
				}
				tableSet();
				data.save("autoSave.save");
			}
		}
		
		private void save(){
			int returnValue = fileChooser.showSaveDialog(null);
			if (returnValue == JFileChooser.APPROVE_OPTION){
				String path = fileChooser.getSelectedFile().getPath();
				if(data.save(path + (path.endsWith("save")?"" : ".save"))){
					data.save("autoSave.save");
					JOptionPane.showMessageDialog(null, "儲存成功");
					}
				else
					JOptionPane.showMessageDialog(null, "儲存失敗");
			}
		}
	}
	
	/*選擇排序方法*/
	private static SortAlgorithm switchSortAlgorithm(int sortAlgorithm){
		return MySelector.switchSortAlgorithm(sortAlgorithm);
	}
	
	/*右邊欄位設定*/
	private void initRightPanel(){
		rightPanel.removeAll();
		rightPanel.setLayout(new BoxLayout(rightPanel,BoxLayout.Y_AXIS));
		
		JPanel filterSubjectPanel = new JPanel();
		filterSubjectPanel.setLayout(new BoxLayout(filterSubjectPanel,BoxLayout.Y_AXIS));
		filterSubjectPanel.add(new JLabel("過濾科目"));
		
		Stream.of(data.getSubjectName()).forEach(s -> {
			JCheckBox subjectCheckBox = new JCheckBox(s);
			subjectCheckBox.setSelected(true);
			filterSubjectPanel.add(subjectCheckBox);
			
			subjectCheckBox.addActionListener(e -> {
				JCheckBox checkbox = (JCheckBox)e.getSource();
				studentTable.setSubjectVisible(checkbox.getText(), checkbox.isSelected());	//如果checkbox沒被勾選就隱藏
			});
			
		});
		rightPanel.add(new JScrollPane(filterSubjectPanel));
		
	}
}
