package Object;

import java.util.Comparator;

import SortAlgorithm.*;
/**
 * 用來選擇排序方法或是Comparator的物件<p>
 * 如果要新增新的排序法需要更改 {@link ALGORITHM_LIST}<br>
 * 以及更改{@link switchSortAlgorithm}<p>
 * {@link switchSortAlgorithm}的case編號要和 {@link ALGORITHM_LIST}的index相同
 * 
 * @author 普皓群
 *
 */
public class MySelector {
	/**
	 * 排序方法的清單
	 * {@link switchSortAlgorithm}的case編號要和 {@link ALGORITHM_LIST}的index相同
	 */
	public static final String[] ALGORITHM_LIST = {"Selection Sort" , "Bubble Sort" , "Quick Sort" ,"Merge Sort"};
	
	/**
	 * 選擇排序方法
	 * {@link switchSortAlgorithm}的case編號要和 {@link ALGORITHM_LIST}的index相同
	 * 
	 * @param sortAlgorithm 0.Selection Sort 1.Bubble Sort 2.Quick Sort
	 * @return 排序方法的物件
	 */
	public static SortAlgorithm switchSortAlgorithm(int sortAlgorithm){
		SortAlgorithm sa = null;
		switch(sortAlgorithm){
		case 0:
			sa = new SelectionSort();
			break;
		case 1:
			sa = new BubbleSort();
			break;
		case 2:
			sa = new QuickSort();
			break;
		case 3:
			sa = new MergeSort();
			break;
		}
		return sa;
	}
	
	/**
	 * 選擇Comparator
	 * @param n 0.依照ID升序排列 1.依照ID降序排列 2.依照科目升序排列 3.依照科目降序排列
	 * @param SortSubject 選擇要排序的科目
	 * @return 選擇的{@link Comparator}
	 * 
	 * @see Comparator
	 */
	public static Comparator<Student> chooseCMP(int n , String SortSubject){
		Comparator<Student> cmp = null;
		switch(n){
		case 0:	//以ID升序比較
			cmp = new Comparator<Student>(){
				@Override
				public int compare(Student o1, Student o2) {
					return  o1.getID() - o2.getID();
				}
			};
			break;
		case 1:	//以ID降序比較
			cmp = new Comparator<Student>(){
				@Override
				public int compare(Student o1, Student o2) {
					return  o2.getID() - o1.getID();
				}
			};
			break;
		case 2:	//以科目升序比較
			cmp = new Comparator<Student>(){
				@Override
				public int compare(Student o1, Student o2) {
					if(o1.getGrade(SortSubject) == o2.getGrade(SortSubject))
						return 0;
					if(o1.getGrade(SortSubject) == null)
						return 1;
					if(o2.getGrade(SortSubject) == null)
						return 1;
					return o1.getGrade(SortSubject) - o2.getGrade(SortSubject);
				}
			};
			break;
		case 3:	//以科目降序比較
			cmp = new Comparator<Student>(){
				@Override
				public int compare(Student o1, Student o2) {
					if(o1.getGrade(SortSubject) == o2.getGrade(SortSubject))
						return 0;
					if(o1.getGrade(SortSubject) == null)
						return -1;
					if(o2.getGrade(SortSubject) == null)
						return -1;
					return o2.getGrade(SortSubject) - o1.getGrade(SortSubject);
				}
			};
			break;
		}
		return cmp;
	}
}
