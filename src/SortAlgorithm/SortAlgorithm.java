package SortAlgorithm;
import java.util.Comparator;

public interface SortAlgorithm {
    public <T>  int[] sort(T[] table , Comparator<T> cmp);
    public String getName();
}
