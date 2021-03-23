import java.util.Comparator;
import java.util.List;

public class RangeComparator implements Comparator<List<Integer>> {

    /**
     * for comparing range-pairs against each other
     * @param o1 a range-pair to compare
     * @param o2 a range-pair to compare
     * @return -1, 0, 1 for comparison
     */
    @Override
    public int compare(List<Integer> o1, List<Integer> o2) {
        int lowerCompare = o1.get(0).compareTo(o2.get(0));
        int upperCompare = o1.get(1).compareTo(o2.get(1));

        if (lowerCompare == 0) {
            return upperCompare == 0 ? lowerCompare : upperCompare;
        } else {
            return lowerCompare;
        }
    }
}
