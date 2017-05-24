package SortAlgorithm;

import java.util.Comparator;

public class MergeSort implements SortAlgorithm{
	private static int[] returnArray = new int[2];
	
	public static final String NAME = "Merge Sort";
	
	@Override
	public <T> int[] sort(T[] table, Comparator<T> cmp) {
		returnArray[0] = 0;
		returnArray[1] = 0;
		if (table.length > 1) {
            // Split table into halves.
            int halfSize = table.length / 2;
            T[] leftTable = (T[])new Object[halfSize];
            T[] rightTable = (T[]) new Object[table.length - halfSize];
            
            System.arraycopy(table, 0, leftTable, 0, halfSize);
            System.arraycopy(table, halfSize, rightTable, 0,
                    table.length - halfSize);

            // Sort the halves.
            sort(leftTable ,cmp);
            sort(rightTable ,cmp);

            // Merge the halves.
            merge(table, leftTable, rightTable ,cmp);
        }
		
		return returnArray;
	}
	
	private static <T>  void merge(T[] outputSequence,
            T[] leftSequence,
            T[] rightSequence,
            Comparator<T> cmp) {
        int i = 0; // Index into the left input sequence.
        int j = 0; // Index into the right input sequence.
        int k = 0; // Index into the output sequence.

        // While there is data in both input sequences
        while (i < leftSequence.length && j < rightSequence.length) {
            // Find the smaller and
            // insert it into the output sequence.
            if (++returnArray[0] > 0 && cmp.compare(leftSequence[i],rightSequence[j]) < 0) {
            	++returnArray[1];
                outputSequence[k++] = leftSequence[i++];
            } else {
            	++returnArray[1];
                outputSequence[k++] = rightSequence[j++];
            }
        }
        // assert: one of the sequences has more items to copy.
        // Copy remaining input from left sequence into the output.
        while (i < leftSequence.length) {
        	++returnArray[1];
            outputSequence[k++] = leftSequence[i++];
        }
        // Copy remaining input from right sequence into output.
        while (j < rightSequence.length) {
        	++returnArray[1];
            outputSequence[k++] = rightSequence[j++];
        }
    }
	
	@Override
	public String getName() {
		return NAME;
	}

}
