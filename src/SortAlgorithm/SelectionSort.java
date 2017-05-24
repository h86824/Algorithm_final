package SortAlgorithm;
import java.util.Comparator;

public class SelectionSort implements SortAlgorithm {
	public final String NAME = "Selection Sort";

    @Override
    public <T>  int[] sort(T[] table , Comparator<T> cmp) {
    	int[] counter = new int[2];
        int n = table.length;
        for (int fill = 0; fill < n - 1; fill++) {
            
            int posMin = fill;
            for (int next = fill + 1; next < n; next++) {
                if (cmp.compare(table[next],(table[posMin])) < 0) {
                    posMin = next;
                }
                counter[0]++;
            }
            T temp = table[fill];
            table[fill] = table[posMin];
            table[posMin] = temp;
            counter[1]++;
        }
        return counter;
    }

	@Override
	public String getName() {
		return NAME;
	}
}
