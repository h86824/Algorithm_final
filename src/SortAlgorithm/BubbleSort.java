package SortAlgorithm;
import java.util.Comparator;

public class BubbleSort implements SortAlgorithm {
	public final String NAME = "Bubble Sort";

    @Override
    public <T>  int[] sort(T[] table , Comparator<T> cmp) {
        int pass = 1;
        int[] count = new int[2];
        boolean exchanges = false;
        do {
            // Invariant: Elements after table.length - pass + 1
            // are in place.
            exchanges = false; // No exchanges yet.
            // Compare each pair of adjacent elements.
            for (int i = 0; i < table.length - pass; i++) {
            	count[0]++;
                if (cmp.compare(table[i],(table[i+1])) > 0) {
                    // Exchange pair.
                    T temp = table[i];
                    table[i] = table[i + 1];
                    table[i + 1] = temp;
                    exchanges = true; // Set flag.
                    count[1]++;
                }
            }
            pass++;
        } while (exchanges);
        return count;
    }
    
    @Override
    public String getName(){
    	return NAME;
    }
}