package SortAlgorithm;
import java.util.Comparator;

public class QuickSort implements SortAlgorithm{
	
	public static final String NAME = "Quick Sort";
	
	private int[] returnArray = new int[2];
	
	@Override
	public <T> int[] sort(T[] table, Comparator<T> cmp) {
		returnArray[0] = 0;
		returnArray[1] = 0;
		
		quickSort(table, 0, table.length - 1, cmp);
		return returnArray;
	}
	
	protected <T>  void quickSort(T[] table, int first, int last , Comparator<T> cmp) {
        if (first < last) { // There is data to be sorted.
            // Partition the table.
            int pivIndex = partition(table, first, last, cmp);
            // Sort the left half.
            quickSort(table, first, pivIndex - 1, cmp);
            // Sort the right half.
            quickSort(table, pivIndex + 1, last, cmp);
        }
    }

    private <T>  int partition(T[] table, int first, int last , Comparator<T> cmp) {
        // Select the first item as the pivot value.
        T pivot = table[first];
        int up = first;
        int down = last;
        do {
            // Invariant:
            // All items in table[first . . . up - 1] <= pivot
            // All items in table[down + 1 . . . last] > pivot
            while (++returnArray[0] >= 0 && (up < last) && (cmp.compare(pivot, table[up]) >= 0)) {
                up++;
            }
            // assert: up equals last or table[up] > pivot.
            while (++returnArray[0] >= 0 && cmp.compare(pivot, table[down]) < 0) {
                down--;
            }
            // assert: down equals first or table[down] <= pivot.
            if (up < down) { // if up is to the left of down.
                // Exchange table[up] and table[down].
            	
            	swap(table, up, down);
            }
        } while (up < down); // Repeat while up is left of down.

        // Exchange table[first] and table[down] thus putting the
        // pivot value where it belongs.
        
        swap(table, first, down);

        // Return the index of the pivot value.
        return down;
    }
	
	private <T> void swap(T[] table,
            int i, int j) {
		++returnArray[1];
        T temp = table[i];
        table[i] = table[j];
        table[j] = temp;
    }
	
	@Override
	public String getName() {
		return NAME;
	}
	
}
