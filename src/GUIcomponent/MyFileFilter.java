package GUIcomponent;
import java.io.File;

/**
 * 自定義的檔案選擇器<p>
 * 設定附檔名篩選用的
 * @author 普皓群
 * @see javax.swing.JFileChooser
 * @see javax.swing.filechooser.FileFilter
 */
public class MyFileFilter extends javax.swing.filechooser.FileFilter{
	/**
	 * 附檔名的名字
	 */
	private String extension;
	/**
	 * 檔案的描述<br>
	 * 描述這個附檔名是做什麼的
	 */
	private String description;
	
	public MyFileFilter(String extension, String description) {
		this.extension = extension;
		this.description = description;
	}

	@Override
	public boolean accept(File pathname) {
		if(pathname.getName().endsWith(extension))	//如果是設定的附檔名就顯示
			return true;
		else if(pathname.isDirectory()){	//如果是資料夾就顯示
			return true;
		}
		return false;	//不是設定的附檔名就不顯示
	}

	@Override
	public String getDescription() {
		return this.description;
	}
	
	
}
